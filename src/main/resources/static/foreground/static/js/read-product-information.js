var queryString = window.location.search;
var searchParams = new URLSearchParams(queryString);
let id = searchParams.get("pdNo")
console.log(id);


var queryString = window.location.search;

$('#addToCart').click(() => {
    set(id, $("#qty").val());
});

//        動態抓取商品資料
const urlSearchParams = new URLSearchParams(window.location.search);
const pdNo = urlSearchParams.get('pdNo');

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

//        動態抓取商品資料
const picNos = urlSearchParams.get('pdNo');
document.getElementById('toimage').setAttribute("src", "/pic/getimage?picno=" + picNos);
console.log(picNos);