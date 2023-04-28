$('.cart-plus-minus').append(
    '<div class="dec qtybutton"><i class="fa fa-minus"></i></div><div class="inc qtybutton"><i class="fa fa-plus"></i></div>'
);
$('.qtybutton').on('click', function () {
    let $button = $(this);
    let oldValue = $button.parent().find('input').val();
    let pdStock = $("#pdStock").text().split(":")[1];
    if ($button.hasClass('inc')) {
        if (oldValue < +pdStock) {
            var newVal = parseFloat(oldValue) + 1;
        } else {
            var newVal = parseFloat(oldValue);
        }
    } else {
        // Don't allow decrementing below zero
        if (oldValue > 1) {
            var newVal = parseFloat(oldValue) - 1;
        } else if (+pdStock === 0) {
            newVal = 0;
        } else {
            newVal = 1;
        }
    }
    $button.parent().find('input').val(newVal);
})

$("#qty").blur(() => {
    if (+$("#qty").val() > +$("#pdStock").text().split(":")[1]) {
        $("#qty").val($("#pdStock").text().split(":")[1])
    }
})

$('#addToCart').click(() => {
    if (+$("#pdStock").text().split(":")[1] === 0) {
        Swal.fire({
            title: "缺貨中!",
            text: "正在補貨，請過幾天再來，謝謝!",
            icon: "warning" //success/info/warning/error/question
        });
    } else if (+$("#qty").val() === 0) {
        Swal.fire({
            title: "商品數量為0，請選擇數量!",
            icon: "error" //success/info/warning/error/question
        });
    } else {
        Swal.fire({
            title: "加入成功",
            icon: "success" //success/info/warning/error/question
        });
        set(pdNo, $("#qty").val());
    }
});

$('#checkout').click(() => {
    if (+$("#pdStock").text().split(":")[1] === 0) {
        Swal.fire({
            title: "缺貨中!",
            text: "正在補貨，請過幾天再來，謝謝!",
            icon: "warning" //success/info/warning/error/question
        });
    } else if (+$("#qty").val() === 0) {
        Swal.fire({
            title: "商品數量為0，請選擇數量!",
            icon: "error" //success/info/warning/error/question
        });
    } else {
        set(pdNo, $("#qty").val());
        window.location.assign('cart.html')
    }
})

//        動態抓取商品資料
const urlSearchParams = new URLSearchParams(window.location.search);
const pdNo = urlSearchParams.get('pdNo');
let queryString = window.location.search;

if (!pdNo) {
    alert('沒有商品編號');
    window.location.href = 'shop.html';
}

fetch(`/product/find-one?id=${pdNo}`)
    .then(response => response.json())
    .then(product => {
        console.log(product); // 檢查資料
        if (!product) {
            throw new Error('Invalid response from server');
        }
        document.getElementById('product-title').textContent = product.pdName;
        document.getElementById('product-no').textContent = product.pdNo;
        document.getElementById('product-name').textContent = product.pdName;
        document.getElementById('product-price').textContent = '$' + product.pdPrice;
        document.getElementById('pdStock').textContent = '庫存量:' + product.pdStock;
        document.getElementById('pdDescription').textContent = product.pdDescription;
        document.getElementById('pdDescription1').textContent = product.pdDescription;
    });

//        動態抓取商品圖片
const picNos = urlSearchParams.get('pdNo');
document.getElementById('toimage').setAttribute("src", "/pic/getimage?picno=" + picNos);
console.log(picNos);