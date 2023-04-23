// 商品搜尋
const searchBtn = document.querySelector('.search-box button');
const searchInput = document.querySelector('.search-box input');
const noResultMsg = document.createElement('p');
noResultMsg.textContent = '查無此產品';
noResultMsg.style.display = 'none';
document.querySelector('.search-box').appendChild(noResultMsg);

searchBtn.addEventListener('click', () => {
  const searchTerm = searchInput.value.trim().toLowerCase();
  let hasResult = false;

  Array.from(document.querySelectorAll('.product-area')).forEach(product => {
    const productName = product.querySelector('.product-title a').textContent.trim().toLowerCase();

    if (productName.includes(searchTerm)) {
      product.style.display = 'block';
      hasResult = true;
    } else {
      product.style.display = 'none';
    }
  });

  if (!hasResult) {
    noResultMsg.style.display = 'block';
    noResultMsg.style.color = 'red'; // 改變字體顏色
  } else {
    noResultMsg.style.display = 'none';
  }
});