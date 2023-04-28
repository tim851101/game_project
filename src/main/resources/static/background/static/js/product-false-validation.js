//商品名稱錯誤驗證
let nameInput = document.getElementById("pdName");
let nameFeedback = document.getElementById("nameFeedback");
let nameRegex = /^[\u4e00-\u9fa5a-zA-Z0-9]+$/;

let priceInput = document.getElementById("pdPrice");
let priceFeedback = document.getElementById("priceFeedback");
let priceRegex = /^[0-9]*$/;

let stockInput = document.getElementById("pdStock");
let stockFeedback = document.getElementById("stockFeedback");
let stockRegex = /^[0-9]*$/;

function validateInputs() {
  let isNameValid = nameRegex.test(nameInput.value);
  let isPriceValid = priceRegex.test(priceInput.value);
  let isStockValid = stockRegex.test(stockInput.value);

  nameInput.classList.toggle("is-invalid", !isNameValid);
  priceInput.classList.toggle("is-invalid", !isPriceValid);
  stockInput.classList.toggle("is-invalid", !isStockValid);

  let isFormValid = isNameValid && isPriceValid && isStockValid;
  document.querySelector('button[type="submit"]').disabled = !isFormValid;
}

nameInput.addEventListener("input", validateInputs);
priceInput.addEventListener("input", validateInputs);
stockInput.addEventListener("input", validateInputs);

// 顯示庫存輸入格式錯誤的提示
stockInput.addEventListener("input", function () {
  if (!stockRegex.test(stockInput.value)) {
    stockInput.classList.add("is-invalid");
    stockFeedback.style.display = "block";
  } else {
    stockInput.classList.remove("is-invalid");
    stockFeedback.style.display = "none";
  }
  validateInputs();
});

//Modal編輯視窗錯誤驗證商品名稱
let editPdNameInput = document.getElementById('editPdName');
let editPdNameFeedback = document.getElementById('editPdNameFeedback');
let editPdNameRegex = /^[\u4e00-\u9fa5a-zA-Z0-9]+$/;

editPdNameInput.addEventListener('input', function () {
  if (!editPdNameRegex.test(editPdNameInput.value)) {
    editPdNameInput.classList.add('is-invalid');
    editPdNameFeedback.style.display = 'block';
    document.querySelector('button[onclick="updateTime()"]').disabled = true;
  } else {
    editPdNameInput.classList.remove('is-invalid');
    editPdNameFeedback.style.display = 'none';
    document.querySelector('button[onclick="updateTime()"]').disabled = false;
  }
});