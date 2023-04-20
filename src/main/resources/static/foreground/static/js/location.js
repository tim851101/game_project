window.onload=function(){
    const locBtnList = document.getElementsByClassName("storeLocation");
    Array.from(locBtnList).forEach((locBtn) => {
      locBtn.addEventListener('click', () => {
        var currentUrl = window.location.href;
        var baseUrl = window.location.origin;
        var relativeUrl = currentUrl.substring(baseUrl.length);
        sessionStorage.setItem('currentUrl', relativeUrl);
        // alert('已經將當前網址存儲到 sessionStorage 中');
      });
    });
}
