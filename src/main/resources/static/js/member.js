
Vue.createApp({
      data() {
        return {
            memNo:'',
            defaultMemNo:'',
            defaultMemName: '',
            memberData:'',
            // memWishlist:'',

        };
      },
      methods: {
        async getMemberData() {
            try {
                const memNoResponse = await fetch('/mem/get-memNo', {
                method: 'POST',
                });
                const memNo = await memNoResponse.json();
                console.log(memNo);
                this.defaultMemNo = memNo;
                const memberResponse = await fetch(`/mem/find-one?id=${memNo}`);
                const memberData = await memberResponse.json();
                this.memberData=memberData;
                console.log(memberData);
                this.defaultMemName = memberData.memName;
            } catch (error) {
                console.error(error);
            }
        },
        async updateMember(event) {
            event.preventDefault();
            try {
                // if (!this.memGender) {
                //     throw new Error("請選擇性別");
                // }
                console.log(this.memberData);
                const response = await fetch(`/mem/save`, {
                    method: 'POST',
                    headers: {
                    'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(this.memberData)
                });
                console.log("response:", response); // test
                await this.handleResponse(response);
                return;
            } catch (error) {
                console.error(error);
                Swal.fire({
                    icon: "error",
                    title: "Oops...",
                    text: error.message,
                });
            }
        },
        async handleResponse(response) {
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
                if (data.message === "Update successful") {
                    // 紀錄當前路徑
                    // await this.saveCurrentUrl();
                    Swal.fire({
                        text: data.message,
                        icon: "success",
                    })
                } else {
                    throw new Error("Unexpected response");
                }
            } else {
                throw new Error("Unexpected response");
            }
        },        
    },
    mounted() {
        this.getMemberData();
    },
    destroyed(){
        this.getMemberData();
    },
}).mount('#account-info');


Vue.createApp({
        data() {
          return {
              memNo:'',
              defaultMemNo:'',
              defaultMemName: '',
              memberData:'',
              // memWishlist:'',
  
          };
        },
        methods: {
            async getMemberData() {
                try {
                    const memNoResponse = await fetch('/mem/get-memNo', {
                    method: 'POST',
                    });
                    const memNo = await memNoResponse.json();
                    console.log(memNo);
                    this.defaultMemNo = memNo;
                    const memberResponse = await fetch(`/mem/find-one?id=${memNo}`);
                    const memberData = await memberResponse.json();
                    this.memberData=memberData;
                    console.log(memberData);
                    this.defaultMemName = memberData.memName;
                } catch (error) {
                    console.error(error);
                }
            },
        
        },
        mounted() {
            this.getMemberData();
        },
        destroyed(){
            this.getMemberData();
        },
}).mount('#dashboard');

Vue.createApp({
            data() {
            return {
                memNo:'',
                memWishlist:'',
            };
            },
            methods: {
            async fetchMemNo() {
                try {
                    const memNoResponse = await fetch('/mem/get-memNo', {
                    method: 'POST'
                    });
                    const memNo = await memNoResponse.json();
                    this.memNo = memNo;
                    return memNo;
                } catch (error) {
                    console.error(error);
                }
            },
            async fetchWishlist() {
                try {
                    const memNo = await this.fetchMemNo();
                    const memWishlistResponse = await fetch(`/wish/myList?memNo=${memNo}`, {
                    method: 'GET'
                    });
                    const memWishlist = await memWishlistResponse.json();
                    this.memWishlist = memWishlist;
                    console.log(memWishlist);
                } catch (error) {
                    console.error(error);
                }
                },
                async renderWishlist() {
                try {
                    await this.fetchWishlist();
                    const currentUrl = "/foreground/my-account.html#wishlist";
                    const url = new URL(window.location.href);
                    const redirectUrl = url.origin + currentUrl;
                    window.location.href = redirectUrl;
                } catch (error) {
                    console.error(error);
                }
            },
            async showWishlist(){
                try{
                    const memNoResponse = await fetch('/mem/get-memNo', {
                        method: 'POST',
                        });
                    const memNo=await memNoResponse.json();
                    const memWishlistResponse=await fetch(`/wish/myList?memNo=${memNo}`,{
                        method:'GET'
                    });
                    await this.fetchWishlist();
                    console.log(this.memWishlist);
                    await this.renderWishlist();
                }catch(error){
                    console.error(error);
                }
            },
            async showWishlist(event){
                event.preventDefault();
                    try{
                        const memNoResponse = await fetch('/mem/get-memNo', {
                            method: 'POST',
                        });
                    const memNo=await memNoResponse.json();
                    const memWishlistResponse=await fetch(`/wish/myList?memNo=${memNo}`,{
                        method:'GET'
                    });
                    const memWishlist=await memWishlistResponse.json();
                    this.memWishlist=memWishlist;
                    console.log(memWishlist);
                }catch(error){
                    console.error(error);
                }
            },
            async removeOne(event) {
                if (event) {
                    event.preventDefault();
                }
                const pdNo = event.currentTarget.getAttribute('data-pd-no');
                // 通过 event 参数获取事件对象
                
                // 顯示刪除確認訊息
                const swalResult = await Swal.fire({
                    title: '確定要移除該商品的收藏嗎？',
                    icon: 'warning',
                    showCancelButton: true,
                    confirmButtonColor: '#d33',
                    cancelButtonColor: '#3085d6',
                    confirmButtonText: '刪除',
                    cancelButtonText: '取消'
                });
                
                // 如果使用者確認要刪除，才進行刪除操作
                if (swalResult.isConfirmed) {
                    const memNoResponse = await fetch('/mem/get-memNo', {
                        method: 'POST'
                    });
                    const memNo = await memNoResponse.json();
                    await fetch('/wish/delete-one', {
                    method: 'POST',
                    body: JSON.stringify({
                        "memNo": memNo,
                        "pdNo": pdNo
                    }),
                    headers: {
                        'Content-Type': 'application/json'
                    }
                    });
                    // 更新畫面上的收藏列表
                    await this.renderWishlist();
                }
            },
            async addOne(event) {
                if (event) {
                    event.preventDefault();
                }
                const pdNo = event.currentTarget.getAttribute('data-pd-no');
                // 通过 event 参数获取事件对象
                
                // 顯示刪除確認訊息
                const swalResult = await Swal.fire({
                    title: '確定要將該商品加入購物車嗎？',
                    icon: 'warning',
                    showCancelButton: true,
                    confirmButtonColor: '#3085d6',
                    cancelButtonColor: '#d33',
                    confirmButtonText: '確定',
                    cancelButtonText: '取消'
                });
                
                // 如果使用者確認要刪除，才進行刪除操作
                if (swalResult.isConfirmed) {
                    const qty=1;
                    this.set(pdNo, qty);
                }
            },
            set(pdNo, qty) {
                const shoppingcart = JSON.parse(localStorage.getItem('shoppingcart')) ?? [];
                let item = shoppingcart.find((item) => (+item.pdNo === +pdNo))
                if(pdNo===null){
                    return;
                }
                if (item) {
                    
                    if (+qty === 0){
                        del(+pdNo);
                    } else{
                        item.qty = qty;
                    }
                } else {
                    item = {
                        "pdNo": pdNo,
                        "qty": qty
                    }
                    shoppingcart.push(item);
                }
                console.log(shoppingcart)
                localStorage.setItem('shoppingcart', JSON.stringify(shoppingcart))
            },
        },
        mounted() {
            this.fetchWishlist();
        },
    }).mount('#wishlist');

    
Vue.createApp({
    data() {
        return {
            memNo:'',
            oldPwd:'',
            newPwd:'',
            CheckNewPwd:''
        };
    },
    methods: {
        async fetchMemNo() {
            try {
                const memNoResponse = await fetch('/mem/get-memNo', {
                method: 'POST'
                });
                const memNo = await memNoResponse.json();
                this.memNo=memNo;
                // sessionStorage.setItem("memNo",memNo);
            } catch (error) {
                console.error(error);
            }
        },
        async changePwd(e) {
            e.preventDefault();
            if(this.newPwd!==this.CheckNewPwd){
                // throw new Error("新密碼與確認密碼不一致");
                Swal.fire({
                    text: '新密碼與確認密碼不一致',
                    icon: 'error',
                });
                return;
            }
            // const memNo = sessionStorage.getItem("memNo");
            // this.memNo=memNo;
            try {
                const pwdChangeData={memNo:this.memNo, oldPwd:this.oldPwd,newPwd:this.newPwd};
                console.log(pwdChangeData);
                const changePwdResponse = await fetch(`/mem/changePassword`, {
                method: 'POST',
                    headers: {
                    'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(pwdChangeData)
                });
                console.log("changePwdResponse:", changePwdResponse); // test
                await this.handleResponse(changePwdResponse);
                return;
            } catch (error) {
                console.error(error);
            }
        },
        async handleResponse(changePwdResponse) {
            console.log("response:", changePwdResponse); // test
            if (changePwdResponse.status === 400) {
                const data = await changePwdResponse.json();
                if (data.errors && data.errors.length > 0) {
                    Swal.fire({
                        text: data.errors[0],
                        icon: "error",
                    });
                } else {
                    throw new Error("Validation error");
                }
            } else if (changePwdResponse.status === 401) {
                const data = await changePwdResponse.json();
                if (data.errors && data.errors.length > 0) {
                    throw new Error(data.errors[0]);
                } else {
                    throw new Error("Unauthorized");
                }
            } else if (changePwdResponse.status === 405) {
                const data = await changePwdResponse.json();
                console.log(data);
                if (data.errors && data.errors.length > 0) {
                    throw new Error(data.errors[0]);
                } else {
                    throw new Error("Unauthorized");
                }
            } else if (changePwdResponse.ok) {
                const data = await changePwdResponse.json();
                console.log(data);
                if (data.message === "Change successful") {
                    // 紀錄當前路徑
                    // await this.saveCurrentUrl();
                    Swal.fire({
                        text: data.message,
                        icon: "success",
                    })
                    this.oldPwd='';
                    this.newPwd='';
                    this.CheckNewPwd='';
                } else {
                    throw new Error("Unexpected response");
                }
            } else {
                throw new Error("Unexpected response");
            }
        }            
    },
    mounted() {
        this.fetchMemNo();
    },
    destroyed(){
        this.fetchMemNo();
    },
}).mount('#password-edit');


  

