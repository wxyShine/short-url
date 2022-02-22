# 定义父镜像
FROM java:8
# 维护者信息
MAINTAINER wxy
# 暴漏端口
EXPOSE 8090
# 将jar包添加到容器
ADD short-url-1.0.2.jar /root/short-url.jar
# 定义容器启动执行的命令
CMD java -jar /root/short-url.jar
