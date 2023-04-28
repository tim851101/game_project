const productList = document.getElementById('product-list');

const picSet = new Set(); // 儲存已經使用過的商品圖片編號
let picNo = 1;
let n = 1;

fetch('/product/get-all')
  .then(response => response.json())
  .then(products => {
    console.log(products); // 檢查資料
    if (!Array.isArray(products)) {
      throw new Error('Invalid response from server');
    }

    products.filter(product => product && product.pdStatus) //確保products陣列中每個元素都有pdStatus屬性，避免在filter方法中遇到undefined或null的元素
      .filter(product => product.pdStatus) // 只顯示上架狀態為true的商品
      .forEach((product, index) => {
        console.log(product)   //檢查每個商品

        const productHTML = `
  <div class="col-md-6 col-sm-6 col-lg-4 col-custom product-area">
    <div class="single-product position-relative">
      <div class="product-image">
        <a class="d-block" href="product-details.html?pdNo=${product.pdNo}" >
          <img id = img${n++} src="/pic/getimage?picno=${product.picNo}" alt="product image" class="product-image-1 w-100" >
        </a>
      </div>
      <div class="single-product position-relative">
         <div class="product-description" style="display: none;">${product.pdDescription}</div>
      </div>
      <div class="product-content">
        <div class="product-rating">
          <i class="fa fa-star"></i>
          <i class="fa fa-star"></i>
          <i class="fa fa-star"></i>
          <i class="fa fa-star-o"></i>
          <i class="fa fa-star-o"></i>
        </div>
        <div class="product-title">
          <h4 class="title-2"><a href="product-details.html?pdNo=${product.pdNo}">${product.pdName}</a></h4>
        </div>
        <div class="price-box">
          <span class="regular-price">$${product.pdPrice}</span>
          ${product.discountedPrice ? `<span class="old-price"><del>$${product.discountedPrice}</del></span>` : ''}
        </div>
        <div class="add-action d-flex position-absolute">
          <a href="javascript:void(0)" title="加入購物車" onclick="add(${product.pdNo})"><i class="ion-bag"></i></a>
          <a href="wishlist.html" title="Add To Wishlist"><i class="ion-ios-heart-outline"></i></a>
        </div>
      </div>
    </div>
  </div>`;

        productList.insertAdjacentHTML('beforeend', productHTML);

      })
  }).then(() => {
    for (i = 1; i < n; i++) {
      $(`#img${i}`).attr("src", `/pic/getimage?picno=${i}`);
    }
  })

//商品類別(刪選商品)
const filters = document.querySelectorAll('.mobile-menu a');

filters.forEach(filter => {
  filter.addEventListener('click', (e) => {
    e.preventDefault();
    const range = e.target.dataset.filter.split('-');
    const min = parseInt(range[0], 10);
    const max = parseInt(range[1], 10) || Infinity;

    const products = document.querySelectorAll('.product-area');
    products.forEach(product => {
      const price = parseInt(product.querySelector('.regular-price').textContent.slice(1), 10);

      if (price < min || price > max) {
        product.style.display = 'none';
      } else {
        product.style.display = 'block';
      }
    });
  });
});