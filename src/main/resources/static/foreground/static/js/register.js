/**
 * Get user input data
 */

/**
 * Get with param
 */
// $('#sent-get').click(e => {
//     e.preventDefault();
//     const empId = $('#empId').val();
//     fetch(`/emp/get-by-id?id=${empId}`, {
//         method: 'GET',
//     }).then((response) => {
//         response.text().then(res => {
//             $('#sent-get-p').text(`id ${empId}'s name is ${res}`);
//         });
//     });
// });
// $(document).ready(function() {


//   /**
//    * Post
//    */
//   let registerData={};
//   $('#mem-reg').click((e) => {
//       e.preventDefault();
//       // 取得user輸入的資料
//       registerData.memName=document.getElementById('mem_name').value;
//       registerData.memEmail=document.getElementById("mem_email").value;
//       registerData.memPassword=document.getElementById("mem_password").value;
//       registerData.memPhone=document.getElementById("mem_phone").value;
//       registerData.memAddress=document.getElementById("mem_address").value;
//       registerData.memBirthday=document.getElementById("mem_birthday").value;
//       if(document.querySelector('input[name="genderOptions"]:checked')==null){
//         document.getElementById("msg").textContent = '性別為必選';
//       }
//       registerData.memGender=document.querySelector('input[name="genderOptions"]:checked').value;
//       console.log(registerData);
//       fetch(`/mem/register`, {
//           method: 'POST',
//           body: JSON.stringify(registerData),
//           headers: {'Content-Type': 'application/json'},
//       }).then(response => response.json())
//       .then(data => {
//         document.getElementById("msg").innerHTML = data;
//         if("會員註冊成功"===data){
//           sessionStorage.setItem("username",registerData.memName);
//           sessionStorage.setItem("email",registerData.memEmail);
//           document.getElementById('mem_name').value="";
//           document.getElementById("mem_email").value="";
//           document.getElementById("mem_password").value="";
//           document.getElementById("mem_phone").value="";
//           document.getElementById("mem_address").value="";
//           document.getElementById("mem_birthday").value="";
//           document.querySelector('input[name="genderOptions"]:checked').checked = false;
//           // 轉跳會員中心
//           const protocol = window.location.protocol;
//           const hostname = window.location.hostname;
//           const port = window.location.port;
//           window.location.replace(`${protocol}//${hostname}${port ? `:${port}` : ''}/foreground/my-account.html`);
//         }
//         console.log(data);})
//       .catch(error => console.error(error));
//   });

//   $('form').submit(e => {
//       e.preventDefault();

//       // console.log(JSON.stringify(registerData))

//       fetch(e.target.action, {
//           method: 'POST',
//           body: JSON.stringify(registerData),
//           headers: {'Content-Type': 'application/json'}
//       })
//       .then(response => {
//           if (!response.ok) {
//             throw new Error('Network response was not ok');
//           }
//           return response.json();
//         })
//         .then(data => {
//           console.log(data);
//         })
//         .catch(error => {
//           console.error('There was a problem with the fetch operation:', error);
//         });    
//     });

// })

Vue.createApp({
  data() {
    return {
      memName: "",
      memPhone: "",
      memGender: "",
      memBirthday: "",
      memEmail: "",
      memPassword: "",
      memAddress: "",
    };
  },
  methods: {
    async register() {
      const registerData={
        memName: this.memName,
        memPhone: this.memPhone,
        memGender: this.memGender,
        memBirthday: this.memBirthday,
        memEmail: this.memEmail,
        memPassword: this.memPassword,
        memAddress: this.memAddress
      }
      console.log(registerData);
      try {
        if (!this.memGender) {
          throw new Error("請選擇性別");
        }
        const response = await fetch(`/mem/register`, {
          method: "POST",
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(registerData),
        });
        console.log("response:", response); // test
        if (response.status === 400) {
          const data = await response.json();
          if (data.errors && data.errors.length > 0) {
            throw new Error(data.errors[0]);
          } else {
            throw new Error("Validation error");
          }
        } else if (response.status === 401) {
          const data = await response.json();
          if (data.errors && data.errors.length > 0) {
            throw new Error(data.errors[0]);
          } else {
            throw new Error("Unauthorized");
          }
        } else if (response.status === 405) {
          const data = await response.json();
          console.log(data);
          if (data.errors && data.errors.length > 0) {
            throw new Error(data.errors[0]);
          } else {
            throw new Error("Unauthorized");
          }
        } else if (response.ok) {
          const data = await response.json();
          console.log(data);
          if (data.message === "Register successful") {
            // 紀錄當前路徑
            // await this.saveCurrentUrl();
            Swal.fire({
              text: data.message,
              icon: "success",
            }).then(() => {
              const currentUrl = "/foreground/my-account.html";
              const url = new URL(window.location.href);
              const redirectUrl = url.origin + currentUrl;
              window.location.href = redirectUrl;
            });
          } else {
            throw new Error("Unexpected response");
          }
        } else {
          throw new Error("Unexpected response");
        }
      } catch (error) {
        console.error(error);
        Swal.fire({
          icon: "error",
          title: "Oops...",
          text: error.message,
        });
      }
    },
  },
}).mount("#app_register");
