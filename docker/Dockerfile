# 使用 JDK 17 的官方 OpenJDK 运行时镜像作为基础镜像
FROM eclipse-temurin:17-jdk-alpine as builder

# 设置工作目录
WORKDIR /app

# 将 jar 包拷贝到容器中
COPY minimalist-application/target/*.jar app.jar

# 使用较小的基础镜像运行 Spring Boot 应用
FROM eclipse-temurin:17-jre-alpine

# 设置环境变量：Java 启动参数可在此配置
ENV JAVA_OPTS=""

# 设置工作目录
WORKDIR /app

# 复制构建阶段的 jar 包
COPY --from=builder /app/app.jar .

# 暴露端口（默认 Spring Boot 应用端口）
EXPOSE 8080

# 启动应用
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]