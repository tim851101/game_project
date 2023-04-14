window.onload=function(){
    const locBtnList = document.getElementsByClassName("storeLocation");
    Array.from(locBtnList).forEach((locBtn) => {
      locBtn.addEventListener('click', () => {
        var currentUrl = window.location.href;
        sessionStorage.setItem('currentUrl', currentUrl);
        alert('已經將當前網址存儲到 sessionStorage 中');
      });
    });
}
