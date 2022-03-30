# 短链接生成服务

## 基于spring boot + H2 数据库实现的短链接生成服务

## 演示  <a href="http://a-url.tk" target="_blank">https://a-url.tk </a>

## 提供接口

### 请求说明

> 请求方式：POST
> 请求URL ：/

### 请求参数

| 字段     | 字段类型 | 字段说明 |
| -------- | -------- | -------- |
| longUrl    | String      | 需要转换的长链接   | 

### 返回结果正确

```json
{
  "id": 1,
  "shorts": "bjCPD2",
  "longUrl": "https://github.com/wxyShine/short-url",
  "shortUrl": "https://a-url.tk/s/bjCPD2",
  "createTime": "2022-03-30 08:42:40"
}
```  

### 参数校验失败

```json
{
  "timestamp": "2022-03-30T08:32:55.925+00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "",
  "path": "/"
}
```

## 部署方式一

> <a href="https://github.com/wxyShine/short-url/releases" target="_blank">下载jar包</a> 直接启动 默认端口 8090

```shell
# 自行替换版本号
java -jar -Xms128m -Xmx512m -XX:PermSize=128M -XX:MaxPermSize=256M  short-url-版本号.jar

```

## 部署方式二

> 使用docker方式部署 `latest`默认最新版本 可以指定<a href="https://hub.docker.com/r/wxyshine/short-url/tags" target="_blank">版本</a>

```shell
docker run -d -p 8090:8090 -v ~/.short-url:/root/.short-url --name short-url --restart=always wxyshine/short-url:latest
```  

> 如nginx 配置ssl 生成的短链接不带https
> 在nginx 配置location处加上`proxy_set_header X-Forwarded-Scheme  $scheme;` 
