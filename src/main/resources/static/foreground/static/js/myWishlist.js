$(document).ready(function() {

    const tableContext=document.getElementById("tbody-context");
    const memNo=document.getElementById("memNo").value;
    const wishBtn=document.getElementById("myWishlist");
    const getReqUrl=`/wish/delete-one?memNo=${memNo}&pdNo=`
    

    wishBtn.addEventListener('click',()=>

        fetch(`/wish/myList?memNo=${memNo}`,{
            method:'GET'
        }).then(response=>{
            return response.json();
        }).then((list)=>{
            let tdContext='';
            for(let item of list){
                tdContext+=`
                <tr>
                    <td class="pro-thumbnail"><img class="img-fluid" src="static/picture/14.jpg" alt="Product"></a></td>
                    <td class="pro-title">${item.pdName}</td>
                    <td class="pro-price"><span>${item.price}</span></td>
                    <td class="pro-stock"><span><strong>${item.pdStock}</strong></span></td>
                    <td class="pro-cart"><a href="cart.html"><i class="fa fa-cart-arrow-down" style="font-size: 1.5rem;"></i></a></td>
                    <td class="pro-remove"><a onclick="return confirm('是否取消收藏? ')" href="#" class="remove"><p style="display:none;">/wish/delete-one?memNo=${memNo}&pdNo=${item.pdNo}</p><i class="ion-trash-b" style="font-size: 1.5rem;"></i></a></td>
                </tr>
                `;
            }
            tableContext.innerHTML=tdContext;
        })
    )

    
})


    // 獲取所有class為remove的a標籤元素
    const removeButtons = document.querySelectorAll('a.remove');

    // 為每個remove button添加click事件監聽器
    // removeButtons.forEach(function(removeButton) {
        removeButtons.click (e=>{
            if(confirm("取消")){
                console.log("true...");
            // e.preventDefault();
            const nextParagraph = removeButtons.nextElementSibling;
            const nextParagraphValue = nextParagraph.textContent;
            const pattern = /href="(.+)\?memNo=(\d+)&pdNo=(\d+)"/;
            const result = nextParagraphValue.match(pattern);
            const url = result[1]; // /wish/delete-one
            const memNo = result[2]; // 1
            const pdNo = result[3]; // 2
            // if (event.target.tagName === 'A' && event.target.classList.contains('remove')) {
                // const href = e.target.href;
                fetch(url, {
                    method: 'POST',
                    body: JSON.stringify({
                        "memNo":memNo,
                        "pdNo":pdNo
                    }),
                    headers: {'Content-Type': 'application/json'},
                }).then(response => {
                    sessionStorage.setItem("res",response.json());
                    console.log(response.text());
                }).then(data => {
                    console.log(data);
                    if(data==="取消收藏成功"){
                        const protocol = window.location.protocol;
                        const hostname = window.location.hostname;
                        const port = window.location.port;
                        const redirectUrl=sessionStorage.getItem("currentUrl")?sessionStorage.getItem("currentUrl"):`${protocol}//${hostname}${port ? `:${port}` : ''}/foreground/my-account.html#wishlist`;
                        window.location.replace(redirectUrl);
                        fetch(`/wish/myList?memNo=${memNo}`,{
                            method:'GET'
                        }).then(response=>{
                            console.log(response.json());
                        }).then((list)=>{
                            let tdContext='';
                            for(let item of list){
                                tdContext+=`
                                <tr>
                                    <td class="pro-thumbnail"><img class="img-fluid" src="static/picture/14.jpg" alt="Product"></a></td>
                                    <td class="pro-title">${item.pdName}</td>
                                    <td class="pro-price"><span>${item.price}</span></td>
                                    <td class="pro-stock"><span><strong>${item.pdStock}</strong></span></td>
                                    <td class="pro-cart"><a href="cart.html"><i class="fa fa-cart-arrow-down" style="font-size: 1.5rem;"></i></a></td>
                                    <td class="pro-remove"><a onclick="return confirm('是否取消收藏? ')" href="/wish/delete-one?memNo=${memNo}&pdNo=${item.pdNo}" class="remove"><i class="ion-trash-b" style="font-size: 1.5rem;"></i></a></td>
                                </tr>
                                `;
                            }
                            tableContext.innerHTML=tdContext;
                        })
                    }else{
                        console(data);
                    }
                }).catch(error => {
                    console.error('發生錯誤', error);
                });
                }
            }
        // }
        );
    // });

