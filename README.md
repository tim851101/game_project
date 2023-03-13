

## Install Spring

透過 [spring initializer](安裝)
1. 選擇 maven, 3.0.4, jar java 17, 
2. dependency: lombok, Spring Data JPA, MySql Driver, Spring Web(設定會出現在 pom.xml 中)
3. 選擇 Generate

直接透過 maven (local 要安裝 maven)
```xml
<dependency>
  <groupId>org.modelmapper</groupId>
  <artifactId>modelmapper</artifactId>
  <version>3.0.0</version>
</dependency>
```
若失敗則下載 [modelmapper](http://modelmapper.org/downloads/) .jar 並引入
* maven 沒辦法直接 add dependency - 應該是官方的問題 [issue](https://github.com/modelmapper/modelmapper/issues/684)

範例 .sql 在 src/main/others

## Config

設定 application.properties
* 目前設定好 MySql 資料庫連線即可
```xml
server.port=8080
spring.datasource.username=root
spring.datasource.password=abcd1234
spring.datasource.url=jdbc:mysql://localhost:3306/boardgame1
#spring.profiles.active=dev
```

## Spring Boot Architecture

* serice 將 POJO(在 spring 稱為 @Eneity) 包裝為 DTO 用來傳送
```bash
View ⇆ Controller ⇆ Service ⇆ Repository ⇆ Model
```

## API Doc

* [JpaRepository](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html)
    * 提供基礎的 CURID
* [stream API](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/stream/package-summary.html)
    * 處理 DB 取出的 collection
* [@Bean](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/Bean.html)
    * Indicates that a method produces a bean to be managed by the Spring container.
* [@Autowired](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/beans/factory/annotation/Autowired.html)
    * DI
    * Marks a constructor, field, setter method, or config method as to be autowired by Spring's dependency injection facilities.
* [ModelMapper.map](https://modelmapper.org/javadoc/#map)
    * 見文件中 ModelMapper 下的 `.map()`
    * 主要使用 modelmapper 將 POJO 對應到 DTO

* [@EnableJpaRepositories](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/config/EnableJpaRepositories.html)(basePackages = {"your.pkg.here"})

## Folder Structure

* config: 放 web config, bean config for DI
* static: 放不需要 server processing 的靜態資源
* templates: 放需要 server processing 的 file，例如: .jsp
* resources: 放 .properties 設定檔
* others: 暫時放一些開發用資源，例如 .sql
```bash
main
├─java
│  └─spring
│      ├─config
│      ├─controller
│      ├─dto
│      ├─model
│      ├─repository
│      └─service
├─others
└─resources
    ├─static
    │  ├─html
    │  ├─css
    │  └─js
    └─templates
```