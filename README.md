# 短链接生成服务

## 基于spring boot + H2 数据库 + 布隆过滤器	+ Vue

## <a href="http://42.193.127.130:8090/" target="_blank">Demo</a>

### 部署方式一

> <a href="https://github.com/wxyShine/short-url/releases" target="_blank">下载jar包</a> 直接启动 默认端口 8090

```shell
java -jar -Xms128m -Xmx512m -XX:PermSize=128M -XX:MaxPermSize=256M  short-url-1.0.0.jar

```



### 部署方式二

> 使用docker方式部署 `latest`默认最新版本 可以指定<a href="https://hub.docker.com/r/wxyshine/short-url/tags" target="_blank">版本</a>

```shell
docker run -d -p 8090:8090 --name short-url --restart=always wxyshine/short-url:latest
```
