// ####要轉跳到會員登入的頁面都要加此程式碼#####
// sessionStorage.setItem('currentUrl', window.location.pathname);

Vue.createApp({
  data() {
    return {
      email: '',
      password: ''
    }
  },
  methods: {
    async saveCurrentUrl() {
      const currentUrl = window.location.pathname + window.location.search;
      sessionStorage.setItem('currentUrl', currentUrl);
    },
    getCookie(name) {
      const cookies = document.cookie.split(';');
      for (let i = 0; i < cookies.length; i++) {
        const cookie = cookies[i].trim();
        if (cookie.startsWith(`${name}=`)) {
          return cookie.substring(name.length + 1);
        }
      }
      return null;
    },
    async login() {
      // cookie的sessionId
      const jsessionid = this.getCookie('JSESSIONID');
      // 儲存sessionId
      sessionStorage.setItem("session",jsessionid);
      try {
        const loginData = { memEmail: this.email, memPassword: this.password };
        const response = await fetch(`/mem/login`, {
          method: 'POST',
          body: JSON.stringify(loginData),
          headers: { 'Content-Type': 'application/json' },
        });
        console.log('response:', response); // test
        if (response.status === 400) {
          sessionStorage.removeItem("session");
          const data = await response.json();
          if (data.errors && data.errors.length > 0) {
            throw new Error(data.errors[0]);
          } else {
            throw new Error('Validation error');
          }
        } else if (response.status === 401) {
          sessionStorage.removeItem("session");
          const data = await response.json();
          if (data.errors && data.errors.length > 0) {
            throw new Error(data.errors[0]);
          } else {
            throw new Error('Unauthorized');
          }
        } else if (response.ok) {
          const data = await response.json();
          if (data.message === 'Login successful') {

            // 紀錄當前路徑
            // await this.saveCurrentUrl();
            Swal.fire({
              text: data.message,
              icon: 'success',
            }).then(() => {

              const currentUrl = sessionStorage.getItem('currentUrl') || '/foreground/login.html';
              const url = new URL(window.location.href);
              const redirectUrl = url.origin + currentUrl;
              window.location.href = redirectUrl;
            });
          } else {
            sessionStorage.removeItem("session");
            throw new Error('Unexpected response');
          }
        }

      } catch (error) {
        console.error(error);
        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: error.message,
        });
      }
    }
  }
}).mount("#login-form");


