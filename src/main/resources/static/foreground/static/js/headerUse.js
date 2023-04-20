// Vue.createApp({
//     data(){
//         return{
//             memNo:'',
//             isLoggedIn: false,
//         };
//     },
//     methods:{
//         checkLoginOut() {
//             fetch('/mem/get-memNo')
//             .then(response => response.json())
//             .then(data => {
//                 if (data === null) {
//                 // 如果獲取到的資料是null，則表示用戶未登入
//                 this.isLoggedIn = false;
//                 } else {
//                 // 如果獲取到的資料是整數，則表示用戶已經登入
//                 this.isLoggedIn = true;
//                 }
//             });
//         },
//         myAccount(event) {
//             event.preventDefault(); // 取消預設事件
//             alert('hello......')
//             fetch('/mem/get-memNo', {
//                 method: 'POST'
//             })
//             .then(response => response.json())
//             .then(data => {
//                 this.memNo = data;
//                 this.login();
//                 const currentUrl = "/foreground/my-account.html";
//                 const url = new URL(window.location.href);
//                 const redirectUrl = url.origin + currentUrl;
//                 window.location.href = redirectUrl;
//                 return Promise.resolve();
//             })
//             .catch(error => {
//                 console.error('Error:', error);
//                 return Promise.resolve();
//             });
//         }
//     },
//     mounted() {
//         this.memNo = this.$refs.memNoHeader.textContent;
//         this.checkLoginOut();
//     },
// }).mount('#app_header')


// window.onload = function() {
Vue.createApp({
    data(){
        return{
        memNo:'',
        isLoggedIn: false,
        };
    },
    computed: {
        loggedIn() {
        return localStorage.getItem("memNo") !== null;
        }
    },
    methods:{
        async checkLoginOut() {
        this.memNo = localStorage.getItem("memNo");
        if (this.memNo === null) {
            this.isLoggedIn = false;
        } else {
            this.isLoggedIn = true;
        }
        },
        async logout(event){
        event.preventDefault();
        try{
            const logoutResponse = await fetch('/mem/to-logout', {
            method: 'POST'
            });
            console.log(logoutResponse);
            const responseText = await logoutResponse.text();
            if (responseText === null || responseText === "") {
            this.isLoggedIn = false;
            } else {
            localStorage.removeItem("memNo");
            console.log(responseText);
            Swal.fire({
                text: responseText,
                icon: 'success',
            })
            .then(() => {
                this.isLoggedIn = false;
                const url = new URL(window.location.href);
                const redirectUrl = url.origin + "/foreground/index.html";
                window.location.href = redirectUrl;
            });
            }
        } catch(error) {
            console.error(error);
        }
        },
    },
    mounted() {
        setTimeout(() => {
        this.checkLoginOut();
        }, 500);
    },
    watch: {
        memNo: function(newVal, oldVal) {
        this.isLoggedIn = newVal !== null;
        }
    },
}).mount('#app_header');
      

// }