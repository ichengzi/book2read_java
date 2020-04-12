# book2read_java
爬取资源网站，去广告，部署于免费版的gae，自用

## 部署
`mvn appengine:deploy`， 可以本地命令行部署到gae, 添加下边的配置。 version需要改变
```xml
<configuration>
    <deploy.projectId>czbookjava</deploy.projectId>
    <deploy.version>1999</deploy.version>
</configuration>
```

## gcloud
`gcloud config list`, show配置

配置proxy后，本地可以正常使用了