FROM openjdk:21-slim-bullseye

COPY target/revaluation-0.0.1-SNAPSHOT.jar /app.jar

# 暴露端口
EXPOSE 8080

# 入口，java项目的启动命令 里面的debug 端口可以忽略
ENTRYPOINT ["java","-jar","/app.jar"]