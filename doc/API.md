
# API Doc

以下為有使用到的 API

## Spring-framework

* [@Bean](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/Bean.html)
    * Indicates that a method produces a bean to be managed by the Spring container.

* [@Autowired](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/beans/factory/annotation/Autowired.html)
    * DI
    * Marks a constructor, field, setter method, or config method as to be autowired by Spring's dependency injection facilities.

## Spring Data

* [JpaRepository](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html)
    * 提供基礎的 CURD

* [@EnableJpaRepositories](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/config/EnableJpaRepositories.html)(basePackages = {"your.pkg.here"})

## Spring Web

[@RequestParam](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/RequestParam.html)
* Annotation which indicates that a method parameter should be bound to a web request parameter.
* [some example](https://matthung0807.blogspot.com/2021/04/spring-mvc-requestparam-url.html)

[@RequestBody](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/RequestBody.html)
* similar to RequestParam

## Others

* [ModelMapper.map](https://modelmapper.org/javadoc/#map)
    * 見文件中 ModelMapper 下的 `.map()`
    * 主要使用 modelmapper 將 POJO 對應到 DTO

* [stream API](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/stream/package-summary.html)
    * 處理 DB 取出的 collection
