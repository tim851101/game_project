## Spring Boot Architecture

* serice 將 POJO(在 spring 稱為 @Eneity) 包裝為 DTO 用來傳送
```bash
View ⇆ Controller ⇆ Service ⇆ Repository ⇆ Model
```

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
