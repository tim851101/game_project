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
      event.preventDefault();
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
            // const errorMessages = data.errors.join(', ');
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
        // 等待10秒
        // await new Promise(resolve => setTimeout(resolve, 10000));
      }
    },
  },
}).mount("#app_register");
