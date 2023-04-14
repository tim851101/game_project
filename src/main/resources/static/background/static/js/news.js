Vue.createApp({
  data() {
    return {
      // text:"Hello",
      textTitle:'',
      textDesc:'',
      myTime:'',
      allNews:null,
      finalRes:null,
      news:null,
    };
  },
  methods: {
    async addNews(event) {
      // alert(`Hello ${this.textDesc}${this.textTitle}${this.myTime}${this.myTimeDiff}`);
      if (event) {
        try {
          const response = await fetch(`/news/add-one`, {
            method: 'POST',
            body: JSON.stringify({
              "newsTitle": this.textTitle,
              "newsDesc": this.textDesc,
              "dueDate": this.myTime,
            }),
            headers: { 'Content-Type': 'application/json' },
          });
          const data = await response.json();
          sessionStorage.setItem("data", JSON.stringify(data));
          console.log(data);
          Swal.fire("新增成功");
          this.textTitle = '';
          this.textDesc = '';
          this.myTime = '';
          this.allNews = await this.getNewsList();
        } catch (error) {
          console.error(error);
        }
      }
    },
    async getData() {
      const res = await fetch(`/news/list-all`);
      const finalRes = await res.json();
      finalRes.posts;
      this.allNews = finalRes;
    },
    getNewsList() {
      //重新获取最新的新闻数据
      return fetch('/news/list-all')
              .then(response => response.json())
              .then(data => {
                this.allNews = data;
                return data;
              })
              .catch(error => console.error(error));
    },
    async handleClick(newsTitle) {
      try {
        const response = await fetch(`/news/get-one/${newsTitle}`);
        const news = await response.json();
        this.textTitle = news.newsTitle;
        sessionStorage.setItem('oldkey',this.textTitle);
        this.textDesc = news.newsDesc;
        this.myTime = new Date(news.dueDate).toISOString().split('T')[0];
      } catch (error) {
        console.error(error);
      }
    },
    async deleteClick(newsTitle) {
      Swal.fire({
        title: '確定刪除?',
        text: "確定刪除後資料將不存在",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: '確定'
      }).then(async (result) => {
        if (result.isConfirmed) {
          try {
            const response = await fetch(`/news/delete-one/${newsTitle}`, {
              method: 'DELETE',
              headers: { 'Content-Type': 'application/json' }
            });
            const news = await response.json();
            // console.log(news);
            // this.textTitle = news.newsTitle;
            // this.textDesc = news.newsDesc;
            // this.myTime = new Date(news.dueDate).toISOString().split('T')[0];
              if(news==="刪除成功"){
                Swal.fire(
                  'Deleted!',
                  'Your file has been deleted.',
                  'success'
                  )
                this.allNews=await this.getNewsList();
              }else{
                Swal.fire({
                  icon: 'error',
                  title: 'Oops...',
                  text: 'Something went wrong!',
                  footer: '<a href="">Why do I have this issue?</a>'
                })
                this.allNews=await this.getNewsList();
              }
          } catch (error) {
            console.error(error);
          }    
        }
      })
    },
    saveChanges(event) {
      if(event){
          const oldTitle=sessionStorage.getItem('oldkey');
          fetch(`/news/find-one-and-update`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
              "oldNewsTitle":oldTitle,
              "newsTitle": this.textTitle,
              "newsDesc": this.textDesc,
              "dueDate": this.myTime
            })
          }).then(response => {
                console.log(response.text());
          }).then(data => {
                Swal.fire("修改成功");
                this.textTitle = '';
                this.textDesc = '';
                this.myTime = '';
                this.allNews=this.getNewsList();
          }).catch(error => console.error(error));
      }
    }
  },
  provide(){
    return{
      allNews:this.allNews,
      news:this.news,
    };
  },
  mounted() {
    this.getData()
  },
}).mount('#app')
