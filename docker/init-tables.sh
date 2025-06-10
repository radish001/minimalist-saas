#!/bin/bash

set -e

DIR=$(dirname $0)

echo "Applying init-tables.sql ..."
mysql -hmysql -uroot -proot < $DIR/init-tables.sql

# 遍历指定目录下按版本号升序排列的所有 SQL 文件
for SQL_FILE in $(ls $DIR/sql-scripts/*.sql | sort -V); do
    # 检查文件是否存在
    if [ -f "$SQL_FILE" ]; then
        # 输出正在应用该文件的信息
        echo "Applying $(basename $SQL_FILE) ..."
        # 执行 SQL 文件中的 SQL 语句到指定的 MySQL 数据库
        mysql -hmysql -uroot -proot -D minimalist < $SQL_FILE
        # 检查 MySQL 命令的返回状态
        if [ $? -ne 0 ]; then
            echo "Error applying $(basename $SQL_FILE). Exiting."
            exit 1
        fi
    fi
done

echo "Database initialized"
