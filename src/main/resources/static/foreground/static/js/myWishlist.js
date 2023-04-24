// window.onload = function() {
//     Vue.createApp({
//       data() {
//         return {
//             memNo:'',
//             memWishlist:'',
//         };
//       },
//       methods: {
//         async fetchMemNo() {
//             try {
//               const memNoResponse = await fetch('/mem/get-memNo', {
//                 method: 'POST'
//               });
//               const memNo = await memNoResponse.json();
//               this.memNo = memNo;
//               return memNo;
//             } catch (error) {
//               console.error(error);
//             }
//         },
//         async fetchWishlist() {
//             try {
//               const memNo = await this.fetchMemNo();
//               const memWishlistResponse = await fetch(`/wish/myList?memNo=${memNo}`, {
//                 method: 'GET'
//               });
//               const memWishlist = await memWishlistResponse.json();
//               this.memWishlist = memWishlist;
//               console.log(memWishlist);
//             } catch (error) {
//               console.error(error);
//             }
//           },
//           async renderWishlist() {
//             try {
//               await this.fetchWishlist();
//               const currentUrl = "/foreground/my-account.html#wishlist";
//               const url = new URL(window.location.href);
//               const redirectUrl = url.origin + currentUrl;
//               window.location.href = redirectUrl;
//             } catch (error) {
//               console.error(error);
//             }
//         },
//         async showWishlist(){
//             try{
//                 const memNoResponse = await fetch('/mem/get-memNo', {
//                     method: 'POST',
//                   });
//               const memNo=await memNoResponse.json();
//               const memWishlistResponse=await fetch(`/wish/myList?memNo=${memNo}`,{
//                   method:'GET'
//               });
//               await this.fetchWishlist();
//               console.log(this.memWishlist);
//               await this.renderWishlist();
//           }catch(error){
//               console.error(error);
//           }
//         },
//         async showWishlist(event){
//             event.preventDefault();
//               try{
//                   const memNoResponse = await fetch('/mem/get-memNo', {
//                       method: 'POST',
//                     });
//                 const memNo=await memNoResponse.json();
//                 const memWishlistResponse=await fetch(`/wish/myList?memNo=${memNo}`,{
//                     method:'GET'
//                 });
//                 const memWishlist=await memWishlistResponse.json();
//                 this.memWishlist=memWishlist;
//                 console.log(memWishlist);

//                 // 添加事件監聽器，以監聽在目標元素範圍外的點擊事件
//                 document.addEventListener('click', this.handleOutsideClick);
//             }catch(error){
//                 console.error(error);
//             }
//         },
//         async removeOne(event) {
//             event.preventDefault();
//             // 通过 event 参数获取事件对象
//             const pdNo = event.currentTarget.getAttribute('data-pd-no');
          
//             // 顯示刪除確認訊息
//             const swalResult = await Swal.fire({
//               title: '確定要移除該商品的收藏嗎？',
//               icon: 'warning',
//               showCancelButton: true,
//               confirmButtonColor: '#d33',
//               cancelButtonColor: '#3085d6',
//               confirmButtonText: '刪除',
//               cancelButtonText: '取消'
//             });
          
//             // 如果使用者確認要刪除，才進行刪除操作
//             if (swalResult.isConfirmed) {
//               const memNoResponse = await fetch('/mem/get-memNo', {
//                 method: 'POST'
//               });
//               const memNo = await memNoResponse.json();
//               await fetch('/wish/delete-one', {
//                 method: 'POST',
//                 body: JSON.stringify({
//                   "memNo": memNo,
//                   "pdNo": pdNo
//                 }),
//                 headers: {
//                   'Content-Type': 'application/json'
//                 }
//               });
//               // 更新畫面上的收藏列表
//               await this.renderWishlist();
//             }
//         },
//         handleOutsideClick(event) {
//             // 判斷是否點擊在目標元素內
//             if (event.target.closest('#myWishlist')) {
//               return;
//             }
          
//             // 隱藏目標元素
//             const wishlistTab = document.querySelector('#wishlist');
//             if (wishlistTab.classList.contains('active')) {
//               wishlistTab.classList.remove('active');
//             }
          
//             // 移除事件監聽器
//             document.removeEventListener('click', this.handleOutsideClick);
//         },
//     },
//     mounted() {
//         // this.getMemberData();

//     },
//     }).mount('#wishlist');
// };
  