Vue.createApp({
    data() {
       return{
        randomSelectOne:null,
        newsDueDate:'',
        newsTitle:'',
        newsDesc:'',
        randomSelectOneEvent:null,
        eventNo:'',
        eventDate:'',
        eventStarttime:'',
        eventEndtime:'',
        eventName:''
       };
    },
    methods: {
        loadOneNews(){
            return fetch('/news/get-one-random')
              .then(response => response.json())
              .then(data => {
                this.randomSelectOne = data;
                this.newsDueDate=this.randomSelectOne.dueDate;
                this.newsTitle=this.randomSelectOne.newsTitle;
                this.newsDesc=this.randomSelectOne.newsDesc;
                return data;
              })
              .catch(error => console.error(error));
        },
        loadOneEventNews(){
            return fetch('/news/get-one-random-event')
              .then(response => response.json())
              .then(data => {
                this.randomSelectOneEvent = data;
                this.eventNo=this.randomSelectOneEvent.eventNo;
                this.eventDate=this.randomSelectOneEvent.eventDate;
                this.eventStarttime=this.randomSelectOneEvent.eventStarttime;
                this.eventEndtime=this.randomSelectOneEvent.eventEndtime;
                this.eventName=this.randomSelectOneEvent.eventName;
                return data;
              })
              .catch(error => console.error(error));
        },
        // doThis() {
        //     fetch('/mem/login', {
        //     method: 'GET'
        //     })
        //     .then(response => {
        //         return response.json();
        //     // 在這裡處理回應
        //     })
        //     .catch(error => {
        //         console.error(error); // log the error to the console
        //     // 在這裡處理錯誤
        //      });
        // },
    },
    provide() {
        return{

        };
    },
    mounted() {
        this.loadOneNews();
        this.loadOneEventNews();
    },
    destroyed(){
        this.loadOneNews();
        this.loadOneEventNews();
    }
}).mount("#app1")

// Vue.createApp({
//     data() {
//        return{
//             response: null, // 用於存儲從伺服器返回的數據
//             error: null, // 用於存儲錯誤
//        };
//     },
//     methods: {
//         doThis() {
//             fetch('/mem/login', {
//             method: 'GET'
//             })
//             .then(response => {
//                 return response.json();
//             // 在這裡處理回應
//             })
//             .catch(error => {
//                 console.error(error); // log the error to the console
//             // 在這裡處理錯誤
//              });
//         },
//     },
//     provide() {
//         return{

//         };
//     },
//     mounted() {

//     },
// }).mount("#app_header")