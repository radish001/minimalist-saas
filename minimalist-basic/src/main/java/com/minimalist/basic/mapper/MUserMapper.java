package com.minimalist.basic.mapper;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.minimalist.basic.entity.enums.StatusEnum;

import com.minimalist.basic.entity.po.table.MDeptTableDef;
import com.minimalist.basic.entity.po.table.MUserDeptTableDef;
import com.minimalist.basic.entity.po.table.MUserTableDef;
import com.minimalist.basic.entity.vo.user.UserQueryVO;
import com.minimalist.basic.entity.vo.user.UserVO;
import com.minimalist.basic.utils.CommonConstant;
import com.mybatisflex.core.BaseMapper;
import com.minimalist.basic.entity.po.MUser;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.query.QueryMethods;
import com.mybatisflex.core.query.QueryWrapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 用户表 映射层。
 *
 * @author 小太阳
 * @since 2024-10-18
 */
public interface MUserMapper extends BaseMapper<MUser> {

    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return 用户PO
     */
    default MUser selectUserByUsername(String username) {
        return selectOneByQuery(QueryWrapper.create().eq(MUser::getUsername, username));
    }

    /**
     * 根据用户ID查询用户
     * @param userId 用户ID
     * @return 用户PO
     */
    default MUser selectUserByUserId(Long userId) {
        return selectOneByQuery(QueryWrapper.create().eq(MUser::getUserId, userId));
    }

    /**
     * 根据用户ID查询用户
     * @param userIdList 用户ID列表
     * @return 用户PO
     */
    default List<MUser> selectUserByUserIds(List<Long> userIdList) {
        return selectListByQuery(QueryWrapper.create().in(MUser::getUserId, userIdList));
    }

    /**
     * 根据租户ID查询用户 -> 字典查询（区分租户和管理员）
     * @return 用户列表
     */
    default List<MUser> selectUserDict() {
        return selectListByQuery(QueryWrapper.create().eq(MUser::getStatus, StatusEnum.STATUS_1.getCode()));
    }

    /**
     * 根据租户ID查询该租户下的用户数量
     * @param tenantId 租户ID
     * @return 该租户下的用户数量
     */
    default long selectUserCountByTenantId(Long tenantId) {
        return selectCountByQuery(QueryWrapper.create().eq(MUser::getTenantId, tenantId));
    }

    /**
     * 根据手机号查询用户
     * @param phone 手机号
     * @return 用户实体
     */
    default MUser selectUserByPhone(String phone) {
        return selectOneByQuery(QueryWrapper.create().eq(MUser::getPhone, phone));
    }

    /**
     * 根据用户ID删除用户
     * @param userId 用户ID
     */
    default void deleteUserByUserId(Long userId) {
        deleteByQuery(QueryWrapper.create().eq(MUser::getUserId, userId));
    }

    /**
     * 根据用户ID修改用户
     * @param user 用户数据
     */
    default void updateUserByUserId(MUser user) {
        updateByQuery(user, QueryWrapper.create().eq(MUser::getUserId, user.getUserId()));
    }

    /**
     * 根据部门ID列表，查询用户数
     * @param deptIds 部门ID
     * @return 用户数
     */
    default long selectUserCountByDeptIds(List<Long> deptIds) {
        long result = 0;
        for (Long deptId : deptIds) {
            result += selectCountByQuery(QueryWrapper.create().where("FIND_IN_SET("  + deptId +", ancestors)"));
        }
        return result;
    }

    /**
     * 查询用户列表(分页)
     * @param query 查询条件
     * @return 用户分页数据
     */
    default Page<UserVO> selectPageUserList(UserQueryVO query) {
        // 1. 构建基础查询，仅查user_id并分页
        QueryWrapper idQuery = QueryWrapper.create()
                .select(MUserTableDef.MUSER.USER_ID)
                .from(MUserTableDef.MUSER)
                .leftJoin(MUserDeptTableDef.MUSER_DEPT).on(MUserDeptTableDef.MUSER_DEPT.USER_ID.eq(MUserTableDef.MUSER.USER_ID))
                .leftJoin(MDeptTableDef.MDEPT).on(MDeptTableDef.MDEPT.DEPT_ID.eq(MUserDeptTableDef.MUSER_DEPT.DEPT_ID))
                .where(MUserTableDef.MUSER.STATUS.eq(query.getStatus()))
                .and(MUserTableDef.MUSER.PHONE.like(query.getPhone()))
                .and(MUserTableDef.MUSER.USER_REAL_NAME.like(query.getUserRealName()));
        if (ObjectUtil.isNotNull(query.getDeptId()) && CommonConstant.ZERO != query.getDeptId()) {
            idQuery.and(
                    MDeptTableDef.MDEPT.DEPT_ID.eq(query.getDeptId())
                            .or(MDeptTableDef.MDEPT.DEPT_ID.in(
                                    QueryWrapper.create()
                                            .select(MDeptTableDef.MDEPT.DEPT_ID)
                                            .from(MDeptTableDef.MDEPT)
                                            .where("FIND_IN_SET(" + query.getDeptId() + ", ancestors)")
                            ))
            );
        }
        idQuery.groupBy(MUserTableDef.MUSER.USER_ID);

        // 2. 分页查user_id
        Page<Long> idPage = paginateAs(query.getPageNum(), query.getPageSize(), idQuery, Long.class);
        if (idPage.getRecords() == null || idPage.getRecords().isEmpty()) {
            return new Page<>();
        }
        // 3. 用user_id in查询查详情
        QueryWrapper detailQuery = QueryWrapper.create()
                .select(MUserTableDef.MUSER.ALL_COLUMNS)
                .from(MUserTableDef.MUSER)
                .where(MUserTableDef.MUSER.USER_ID.in(idPage.getRecords()));
        List<UserVO> userVOList = selectListByQueryAs(detailQuery, UserVO.class);

        // 4. 保持顺序
        List<Long> idOrder = idPage.getRecords();
        userVOList.sort((a, b) -> idOrder.indexOf(a.getUserId()) - idOrder.indexOf(b.getUserId()));

        // 5. 封装分页结果
        // 直接 new Page<>(pageNum, pageSize, totalRow)
        Page<UserVO> page = new Page<>(idPage.getPageNumber(), idPage.getPageSize(), idPage.getTotalRow());
        page.setRecords(userVOList);
        return page;
    }

}
