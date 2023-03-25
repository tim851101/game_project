# Dev-Rules-background

注意:此為<font color=yellow>後台</font>的<font color=yellow>前端</font>規範

## Path

若要引用 `rsources/static/background/static/` 下 css/, font/, image/, js/, picture/ 資料夾內的檔案需將前述路徑改為 `/background/static/`
* 例如: `rsources/static/background/static/css/xxx.css` 為 `rsources/static/background/static/css/xxx.css` 的映射

若要引用 `rsources/static/background/static/` 下的 xxx.html 案需將前述路徑改為 `/background/static/`
* 例如: `background/xxx.html` 為 `rsources/static/background/static/xxx.html` 的映射
```bash
rsources
    └─static
        └─background
            └─static
                ├─css
                ├─font
                ├─image
                ├─js
                └─picture
```

## 統一更改

<font color=yellow>下面兩段註解之間的內容不要更動</font>
```html
<!--**********************************
    Sidebar end
***********************************-->
<!--**********************************
    Sidebar end
***********************************-->
```

## Deprecated

在使用 moban5749 時，將以下 comment 內的內容作更改，並在 header import template.js
```html
<head>
  <script src="/background/static/js/template.js"></script>
</head>
```