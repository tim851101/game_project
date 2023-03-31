# Dev-Rules-Foreground

注意:此為<font color=yellow>前台</font>的<font color=yellow>前端</font>規範

## Path

若要引用 `rsources/static/foreground/static/` 下 css/, font/, image/, js/, picture/ 資料夾內的檔案需將前述路徑改為 `/foreground/static/`
* 例如: `rsources/static/foreground/static/css/xxx.css` 為 `rsources/static/foreground/static/css/xxx.css` 的映射

若要引用 `rsources/static/foreground/static/` 下的 xxx.html 案需將前述路徑改為 `/foreground/static/`
* 例如: `foreground/xxx.html` 為 `rsources/static/foreground/static/xxx.html` 的映射
```bash
rsources
    └─static
        └─foreground
            └─static
                ├─css
                ├─font
                ├─image
                ├─js
                └─picture
```
