# 使用多阶段构建
# 第一阶段：构建项目
FROM maven:3.9.5-eclipse-temurin-21 AS builder

# 设置工作目录
WORKDIR /app

# 复制pom文件
COPY pom.xml .
COPY owpaicard-common/pom.xml owpaicard-common/
COPY owpaicard-pojo/pom.xml owpaicard-pojo/
COPY owpaicard-server/pom.xml owpaicard-server/

# 复制源代码
COPY owpaicard-common/src owpaicard-common/src
COPY owpaicard-pojo/src owpaicard-pojo/src
COPY owpaicard-server/src owpaicard-server/src

# 构建项目
RUN mvn clean package -DskipTests

# 第二阶段：运行环境
FROM eclipse-temurin:21-jre-jammy

# 设置工作目录
WORKDIR /app

# 从构建阶段复制构建好的jar包
COPY --from=builder /app/owpaicard-server/target/*.jar app.jar

# 暴露端口
EXPOSE 8080

# 启动命令
ENTRYPOINT ["java", "-jar", "app.jar"]