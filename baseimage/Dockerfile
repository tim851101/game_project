# 使用Alpine Linux 作為基礎映像
FROM alpine:latest AS builder

# 更新Alpine Linux 的軟件庫並安裝所需軟件包
RUN apk update && apk add --no-cache openjdk17 maven

# 設置工作目錄
WORKDIR /project

# 複製Maven配置文件到容器中
COPY pom.xml /project/

# 下載依賴項
RUN mvn dependency:go-offline

