### 架构中心父工程
##### 说明：

该工程主要负责Jar版本管理,所有架构中心的项目都必须在pom.xml添加该工程的引用
```
<parent>
    <groupId>net.dgg.core.parent</groupId>
    <artifactId>dgg-core-parent</artifactId>
    <version>RELEASE</version>
</parent>
```

##### 下载：

git clone http://172.16.2.239/technology-architecture-center/technology-department/dgg-core-parent.git