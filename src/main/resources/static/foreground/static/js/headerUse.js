Vue.createApp({
    data(){
        return{
            memNo:'',
            isLoggedIn: false,
        };
    },
    methods:{
        login() {
            // 登入成功後，將 isLoggedIn 設為 true
            this.isLoggedIn = true
            // 其他登入相關的邏輯
        },
        logout() {
            // 登出時，將 isLoggedIn 設為 false
            this.isLoggedIn = false
            // 其他登出相關的邏輯
        },
        myAccount(event) {
            event.preventDefault(); // 取消預設事件
            alert('hello......')
            fetch('/mem/get-memNo', {
                method: 'POST'
            })
            .then(response => response.json())
            .then(data => {
                this.memNo = data;
                this.login();
                const currentUrl = "/foreground/my-account.html";
                const url = new URL(window.location.href);
                const redirectUrl = url.origin + currentUrl;
                window.location.href = redirectUrl;
                return Promise.resolve();
            })
            .catch(error => {
                console.error('Error:', error);
                return Promise.resolve();
            });
        }
    },
    mounted() {
        this.memNo = this.$refs.memNoHeader.textContent;
    },
}).mount('#app_header')