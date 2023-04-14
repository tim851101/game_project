$(document).ready(function() {

  let loginData={};
  $('#mem-login').click((e) => {
    e.preventDefault();
    // 取得user輸入的資料
    loginData.memEmail=document.getElementById("loginEmail").value;
    loginData.memPassword=document.getElementById("loginPassword").value;
    console.log(loginData);
    fetch(`/mem/login`, {
        method: 'POST',
        body: JSON.stringify(loginData),
        headers: {'Content-Type': 'application/json'},
    }).then(response =>{
      if (!response.ok) {
        throw new Error('Network response was not ok');
        }
        return response.text();
    })
    .then(data => {
      console.log(data)
      if(data==="帳號或密碼錯誤"){
        document.getElementById("msg").innerText="帳號或密碼錯誤";
      }else{
        // const jwt=require('jsonwebtoken');
        // const payload={'memNo', JSON.parse(data).memNo};
        // const secret="boardgame";
        // const token=jwt.sign(payload,secret,{expiresIn:'24h'});
        // localStorage.setItem('token',token);

        sessionStorage.setItem("username",JSON.parse(data).memName)
        sessionStorage.setItem('memNo', JSON.parse(data).memNo);
        // 轉跳前一次頁面,沒有就到會員中心
        const protocol = window.location.protocol;
        const hostname = window.location.hostname;
        const port = window.location.port;
        const redirectUrl=sessionStorage.getItem("currentUrl")?sessionStorage.getItem("currentUrl"):`${protocol}//${hostname}${port ? `:${port}` : ''}/foreground/my-account.html`;
        window.location.replace(redirectUrl);
      }
    })
    .catch(error => console.error(error));
  });

  $('#login-form').submit(e => {

      e.preventDefault();
      // loginData.memEmail=document.getElementById("loginEmail").value;
      // loginData.memPassword=document.getElementById("loginPassword").value;
      // console.log(JSON.stringify(registerData))

      fetch(`/mem/login`, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(loginData)
        })
        .then(response => {
          if (!response.ok) {
            console.log("start1...");
            throw new Error('Network response was not ok');
          }
          console.log(response.text());
        })
        .then(data => {
          console.log(data);
          if(data) sessionStorage.setItem('memberNO', data.text());
        })
        .catch(error => {
          console.error('There was a problem with the fetch operation:', error);
        });    
  });
});
const app=Vue.createApp({
  data(){
    return{
      showLogin:true,
    }
  }

})
app.mount("#app");
