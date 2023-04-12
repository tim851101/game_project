//結帳
const memNo = 10;
console.log('memNo = ' + memNo);
let n = 1;
setMemDataBymemNo(memNo);

showProduct()



$('#button_submit').click(e => {
    e.preventDefault();
    // type must be the samew
    Swal.fire({
        title: "是否確定送出?",
        text: "送出後即無法改變內容",
        icon: "warning",
        showCancelButton: true
    }).then(function (result) {
        if (result.value) {
            if (checkForm()) {
                //會員編號10的訂單
                insertOrderBymemNo(memNo);
                Swal.fire({
                    title: "感謝您的購買!",
                    icon: "success" //success/info/warning/error/question
                }).then(function (result) {
                    if (result.value) {
                        window.location.href = "/foreground/shop.html"
                    };
                });
            } else {
                checkForm()
                Swal.fire({
                    title: "資料格式未齊全，請重新嘗試!",
                    icon: "error"   //success/info/warning/error/question
                })
            }
        }
    });
});



//前端即時錯誤驗證
$("#recipient").blur(checkRecipient);
$("#recipientAddres").blur(checkRecipientAddres);
$("#recipientPh").blur(checkRecipientPh);


//信用卡輸入框
$("#ordPayStatus").change(() => {
    if ($("#ordPayStatus").val() === '2') {
        $("#input-creidtCard").show();
    } else {
        $("#input-creidtCard").hide();
    }
})
//到店取貨時，地址定在商家地址並隱藏地址input，並且改變運費金額
$("#ordPick").change(() => {
    if (+$('#ordPick').val() === 0) {
        $("#recipientAddres").val("");
        $("#input-addres").hide();
        $("#ordFee").text("0")
        sumActulAmount()
    } else if(+$('#ordPick').val() === 1){
        $.get(`/mem/find-one?id=${memNo}`, function (result) {
            $("#recipientAddres").val(result.memAddress);
        })
        $("#input-addres").show();
        $("#ordFee").text("75")
        sumActulAmount()
    } else if(+$('#ordPick').val() === 2){
        $.get(`/mem/find-one?id=${memNo}`, function (result) {
            $("#recipientAddres").val(result.memAddress);
        })
        $("#input-addres").show();
        $("#ordFee").text("100")
        sumActulAmount()
    }
})

// =================== function start =========================

//新增訂單
function insertOrderBymemNo(memNo) {
    const totalAmount =  sumTotalAmount();
    const ordFormData = {
        "memNo": +memNo,
        "useCoupon": +$("#useCoupon").text(),
        "ordFee": +$("#ordFee").text(),
        "ordPick": +$("#ordPick").val(),
        "totalAmount": +totalAmount,
        "actualAmount": +$("#actualAmount").text(),
        "recipient": $("#recipient").val(),
        "recipientAddres": $("#recipientAddres").val(),
        "recipientPh": $("#recipientPh").val()
    }
    // Send form data as POST request
    fetch('/ord/save', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(ordFormData),
    }).then((response) => {
        return response.text();
    }).then((ordNo) => {
        saveOrdList(+ordNo, 10, 10, 1000);
        saveOrdList(+ordNo, 3, 10, 1000);
        saveOrdList(+ordNo, 2, 10, 1000);
    }).catch((error) => {
        console.error('There was a problem with the fetch operation:', error);
    });
}



// 新增訂單明細
function saveOrdList(ordNo, pdNo, qty, price) {
    const ordListData = {
        "ordNo": +ordNo,
        "pdNo": +pdNo,
        "qty": +qty,
        "price": +price
    };
    fetch('/ordList/save', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(ordListData),
    }).then(response => {
    }).catch(error => {
        console.error('There was a problem with the fetch operation:', error);
    });
}

// 驗證收件人姓名
function checkRecipient() {
    const recipientInput = document.getElementById("recipient");
    const recipientError = document.getElementById("recipientError");

    if (recipientInput.value.trim() === "") {
        recipientError.textContent = "請輸入收件人名稱";
        return false;
    } else {
        recipientError.textContent = "";
        return true;
    }
}

// 驗證收件/超商地址
function checkRecipientAddres() {
    const recipientAddresInput = document.getElementById("recipientAddres");
    const addresError = document.getElementById("addresError");
    if (+$('#ordPick').val() === 0) {
        addresError.textContent = ""
        return true
    }

    if (recipientAddresInput.value.trim() === "") {
        addresError.textContent = "請輸入收件/超商地址";
        return false;
    } else {
        addresError.textContent = "";
        return true;
    }
}

// 驗證收件人電話
function checkRecipientPh() {
    const recipientPhInput = document.getElementById("recipientPh");
    const phError = document.getElementById("PhError");
    const phoneRegex = /^09\d{8}$/;

    if (recipientPhInput.value.trim() === "") {
        phError.textContent = "請輸入收件人電話";
        return false;
    } else if (!phoneRegex.test(recipientPhInput.value.trim())) {
        phError.textContent = "請輸入正確的電話號碼格式 ex:0912345678";
        return false;
    } else {
        phError.textContent = "";
        return true;
    }
}

// 驗證表單
function checkForm() {
    const recipientValid = checkRecipient();
    const recipientAddresValid = checkRecipientAddres();
    const recipientPhValid = checkRecipientPh();

    return (recipientValid && recipientAddresValid && recipientPhValid);
}

// 獲取會員資料並放入輸入框
function setMemDataBymemNo(memNo) {
    fetch(`/mem/find-one?id=${+memNo}`, {
        method: 'GET'
    }).then(request => {
        return request.json()
    }).then(memData => {
        $("#recipient").val(memData.memName)
        $("#recipientAddres").val(memData.memAddress);
        $("#recipientPh").val(memData.memPhone);
    })
}

//計算應付金額
function sumActulAmount(){
    const totalAmount = sumTotalAmount();
    $("#actualAmount").text(totalAmount + (+$("#ordFee").text()) - (+$("#useCoupon").text()));
}
//計算產品總金額
function sumTotalAmount(){
    const productMoneyList = $("[id^='productMoney']")
    let totalAmount = 0;
    for (productMoney of productMoneyList) {
        totalAmount += +productMoney.innerText;
    }
    return +totalAmount;
}


//購物車商品
function showProduct(){
    const producttBody = document.querySelector("#productTable")
    let str = '';
    producttBody.innerHTML = '';
    for(item of shoppingcart){
    const qty = item.qty;
    const pdNo = item.pdNo
    fetch(`/product/find-one?id=${pdNo}`,{
        method:"GET"
        }).then(response => response.json()
        ).then(data=>{
            str +=`  <tr class="cart_item">
                                            <td class="cart-product-name"><span>${data.pdName}<strong class="product-quantity">× ${qty}</strong></span></td>
                                            <td class="cart-product-total text-center">$<span id="productMoney" class="amount">${qty*data.pdPrice}</span></td>
                                        </tr>`;

            producttBody.innerHTML = str;                           
            sumActulAmount()
        });
    }
}
