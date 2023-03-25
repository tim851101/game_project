* [README](./README.md)
* [API we used](./doc/API.md)
* [Spring Concept and Install](/doc/Spring-Usage.md)
* [Foreground-Frontend](/doc/Foreground-frontend.md)
* [Background-Frontend](/doc/Background-frontend.md)

# Usage

* 建立範例資料庫: `./src/main/others/boardgame.sql`
* 設定 properties: `./src/main/resources/application.properties.template` 設定為自己的 server 設定，更名為 `application.properties`
* 透過 `localhost:8082/foreground`, `localhost:8082/management` 執行
* 網頁 mapping 規則參考 `./src/main/java/webapp/config/WebConfig.java`

## Config

設定 application.properties
* 目前設定好 MySql 資料庫連線即可
```xml
server.port=8082
spring.datasource.username=xxx
spring.datasource.password=xxx
spring.datasource.url=jdbc:mysql://localhost:3306/CGA106G1
```

# Development Rules

* 根據 .gitmessage 提示 commit
* push 步驟
    ```bash
    git fetch origin <branch-name>
    git pull origin <branch-name>
    git push origin <branch-name>
    ```
* 前端分為前台後台
  * foreground: 前台頁面
  * background: 
    * [Background-Frontend Rules](/doc/Background-frontend.md)
  * .html 直接放在 `foreground/`,  `background/` 下
  * .css, .js 放在 `foreground/static`,  `background/static` 的相應資料夾下
* 後端程式依照功能放在 `./java/webapp/<功能名稱>/` 下
  * 下面會在再分為 pojo, repository, service, dto, controller
  * 分別對應 spring 的 `@Eneity`, `@JpaRepository`, `@Service`, `DTO`, `@RestController`
  * member: 會員功能
  * product: 商城功能
  * event: 賽事功能
  * booking: 預約功能
  * others: 不在上述功能

## Folder Structure

* config: 放 web config, bean config for DI
* resources: 放 .properties 設定檔
* others: 暫時放一些開發用資源，例如 .sql
* static: 放不需要 server processing 的靜態資源
* templates: 放 thymeleaf
```bash
main
  ├─java
  |  └─webapp
  |      ├─config
  |      ├─others
  |      ├─member
  |      ├─event
  |      ├─product
  |      └─booking
  |          ├─controller
  |          ├─dto
  |          ├─pojo
  |          ├─repository
  |          └─service
  ├─others
  └─resources
      └─static
          ├─foreground
          └─background
            └─static
               ├─css
               ├─font
               ├─image
               ├─js
               └─picture
```

# TODO

* [ ] .jsp 檔案加入方式
