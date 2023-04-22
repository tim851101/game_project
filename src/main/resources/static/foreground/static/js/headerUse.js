document.addEventListener('DOMContentLoaded', function() {
  // 判斷使用者是否已登入
  var xhr = new XMLHttpRequest();
  xhr.open('POST', '/mem/get-memNo');
  xhr.onreadystatechange = function() {
    if (xhr.readyState === 4 && xhr.status === 200) {
      if (xhr.responseText.trim() === '') {
        // 未登入，隱藏登出按鈕
        let logoutBtns = document.querySelectorAll('.logout-btn');
        console.log(logoutBtns);
        for (let i = 0; i < logoutBtns.length; i++) {
          let liElement = logoutBtns[i].closest('li');
          liElement.style.setProperty('display', 'none', 'important');
        }
        let accountBtns=document.querySelectorAll('.account-header');
        for(let i = 0; i < accountBtns.length; i++) {
          let liElement = accountBtns[i].closest('li');
          liElement.style.setProperty('display', 'none', 'important');
        }
      } else {
        // 已登入，隱藏登入按鈕
        let loginBtns = document.querySelectorAll('.login-btn');
        console.log(loginBtns);
        for (let i = 0; i < loginBtns.length; i++) {
          let liElement = loginBtns[i].closest('li');
          liElement.style.setProperty('display', 'none', 'important');
        }
      }
    }
  };
  xhr.send();

  let logoutBtns = document.querySelectorAll('.logout-btn');
  logoutBtns.forEach((btn) => {
    btn.addEventListener('click', async function(event) {
      event.preventDefault();
      try {
        const logoutResponse = await fetch('/mem/to-logout', {
          method: 'POST'
        });
        console.log(logoutResponse);
        if (logoutResponse.ok) {
          localStorage.removeItem("memNo");
          const responseText = await logoutResponse.text();
          console.log(responseText);
          Swal.fire({
            text: responseText,
            icon: 'success',
          }).then(() => {
            // 登出成功時，隱藏登出按鈕，顯示登入按鈕
            let logoutBtn = btn;
            let liElement = logoutBtn.closest('li');
            liElement.style.display = 'none';
            let loginBtns = document.querySelector('.login-btn');
            let liElement2 = loginBtns.closest('li');
            liElement2.style.display = 'block';
            // 成功登出後重新導向到登入頁面
            const url = new URL(window.location.href);
            const redirectUrl = url.origin + "/foreground/login.html";
            window.location.href = redirectUrl;
          });
  
        } else {
          console.error(logoutResponse.status);
        }
      } catch (error) {
        console.error(error);
      }
    });
  });
  
  let loginBtns = document.querySelectorAll('.login-btn');
  loginBtns.forEach((btn) => {
    btn.addEventListener('click', async function(event) {
      let currentUrl;
      if(location.search===''){
        currentUrl=location.pathname;
      }else{
        currentUrl=location.pathname + location.search;
      }
      sessionStorage.setItem("currentUrl",currentUrl);
    })
  })
});
