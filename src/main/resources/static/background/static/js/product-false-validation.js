//商品名稱錯誤驗證
let nameInput = document.getElementById("pdName");
let regex = /^[a-zA-Z\u4e00-\u9fa5]+$/;
nameInput.addEventListener("input", function () {
  if (!regex.test(nameInput.value)) {
    nameInput.classList.add("is-invalid");
  } else {
    nameInput.classList.remove("is-invalid");
  }
});

//商品價錢錯誤驗證
function validatePrice() {
  let priceInput = document.getElementById("pdPrice");
  let regex = /^[0-9]*$/;
  if (!regex.test(priceInput.value)) {
    priceInput.classList.add("is-invalid");
  } else {
    priceInput.classList.remove("is-invalid");
  }
}

//商品庫存錯誤驗證
let stockInput = document.getElementById("pdStock");
stockInput.addEventListener("input", function () {
  let regex = /^[0-9]*$/;
  if (!regex.test(stockInput.value)) {
    stockInput.classList.add("is-invalid");
  } else {
    stockInput.classList.remove("is-invalid");
  }
});