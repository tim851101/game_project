//新增商品
$('#product-form').submit(e => {
        e.preventDefault();
        // type must be the samew 一定要數字型態 +號是轉數字型態，對應DTO表格，傳時間要有毫秒
        const formData = {
            'pdNo': +$("#pdNo").val(),
            'pdName': $("#pdName").val(),
            'pdPrice': +$("#pdPrice").val(),
            'pdStock': +$("#pdStock").val(),
            'pdDescription': $("#pdDescription").val(),
            'pdStatus': $("#c1").prop('checked'),
            'pdUpdate': moment($("#pdUpdate").val()).format('YYYY-MM-DD HH:mm:ss')
        }
        console.log(formData);

        // Send form data as POST request
        fetch('/product/save-product', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json' //一定要設定!
            },
            body: JSON.stringify(formData) //轉JOSN字串
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                console.log(data);
            })
            .catch(error => {
                console.error('There was a problem with the fetch operation:', error);
            });
    });

//  單筆查詢
$(document).ready(function() {
  const findByIdBtn = document.getElementById('findById');
  const inputProductId = document.getElementById('productId');
  const allShowTable = document.getElementById('allshow').getElementsByTagName('tbody')[0];
  const paginationContainer = document.querySelector('.pagination');

  findByIdBtn.addEventListener('click', () => {
    const productId = inputProductId.value;

    fetch(`/product/find-one?id=${productId}`)
      .then(response => response.json())
      .then(data => {
        console.log(`/product/find-one?id='${productId}'`);
        console.log(data);

        // 藉此避免查詢全部所產生的分頁造成影響
        paginationContainer.innerHTML = '';

        allShowTable.innerHTML = "";

        const row = allShowTable.insertRow();
        const cell1 = row.insertCell(0);
        const cell2 = row.insertCell(1);
        const cell3 = row.insertCell(2);
        const cell4 = row.insertCell(3);
        const cell5 = row.insertCell(4);
        const cell6 = row.insertCell(5);
        const cell7 = row.insertCell(6);

        cell1.innerHTML = data.pdNo;
        cell2.innerHTML = data.pdName;
        cell3.innerHTML = data.pdPrice;
        cell4.innerHTML = data.pdStock;
        cell5.innerHTML = data.pdDescription;
        cell6.innerHTML = data.pdStatus ? '<i class="fa fa-circle text-success me-1"></i>已上架' : '<i class="fa fa-circle text-danger me-1"></i>已下架';
        cell7.innerHTML = moment(data.pdUpdate).format('YYYY-MM-DD HH:mm:ss');

        allShowTable.setAttribute('border', '1');
      })
  });
});

//    查詢全部
window.onload = function() {
   const allshowTable = document.getElementById('allshow');
   const paginationContainer = document.querySelector('.pagination');
   const getAllProductButton = document.getElementById('getAllProduct');

   const ITEMS_PER_PAGE = 10;

   getAllProductButton.addEventListener('click', () => {
        fetch(`/product/get-all`)
          .then(response => response.json())
          .then(data => {
            console.log(data);
            const recordsPerPage = ITEMS_PER_PAGE;

            // 分頁
            const pageCount = Math.ceil(data.length / recordsPerPage);
            paginationContainer.innerHTML = ''; // 解決每按一次全部查詢底下就多一個分頁
            const pagination = generatePagination(pageCount, data);

            paginationContainer.appendChild(pagination);

            showPage(1, data);
          });
      });

   function generatePagination(pageCount, data) {
     const paginationList = document.createElement('ul');
     paginationList.classList.add('pagination');

     for (let i = 1; i <= pageCount; i++) {
       const pageItem = document.createElement('li');
       pageItem.classList.add('page-item');
       const pageLink = document.createElement('a');
       pageLink.classList.add('page-link');
       pageLink.setAttribute('href', '#');
       pageLink.textContent = i;
      pageLink.addEventListener('click', (event) => {
        event.preventDefault();
        const page = i;
        showPage(page, data);
      });
       pageItem.appendChild(pageLink);
       paginationList.appendChild(pageItem);
     }

     return paginationList;
   }

   function showPage(pageNum, data) {
     const tableBody = document.querySelector("#allshow tbody");
     const allshowTable = document.getElementById('allshow');
     if (!allshowTable) {
       return;
     }
     tableBody.innerHTML = "";
     const startIndex = (pageNum - 1) * ITEMS_PER_PAGE;
     const endIndex = startIndex + ITEMS_PER_PAGE;
     const paginatedData = data.slice(startIndex, endIndex);
     for (const product of paginatedData) {
         const row = allshowTable.querySelector('tbody').insertRow();
         const idCell = row.insertCell();
         const nameCell = row.insertCell();
         const priceCell = row.insertCell();
         const stockCell = row.insertCell();
         const descCell = row.insertCell();
         const statusCell = row.insertCell();
         const updateCell = row.insertCell();

         // 在表格行中顯示產品數據
         idCell.innerHTML = product.pdNo;
         nameCell.innerHTML = product.pdName;
         priceCell.innerHTML = product.pdPrice;
         stockCell.innerHTML = product.pdStock;
         descCell.innerHTML = product.pdDescription;

         if (product.pdStatus) {
             statusCell.innerHTML = '<span style="margin-right: 10px;padding-left: 10px;"><i class="fa fa-circle text-success me-1"></i>已上架</span>';
         } else {
             statusCell.innerHTML = '<span style="margin-right: auto;padding-left: 10px;"><i class="fa fa-circle text-danger me-1"></i>已下架</span>';
         }

         updateCell.innerHTML = moment(product.pdUpdate).format('YYYY-MM-DD HH:mm:ss');
     }
   }
}

//檢查每個輸入欄位是否已填寫完
function validateForm() {
  let pdName = document.getElementById("pdName").value;
  let pdPrice = document.getElementById("pdPrice").value;
  let pdStock = document.getElementById("pdStock").value;
  let pdStatus = document.querySelector('input[name="pdStatus"]:checked');
  let pdUpdate = document.getElementById("pdUpdate").value;
  let pdDescription = document.getElementById("pdDescription").value;

  if (
    pdName.trim() === "" ||
    pdPrice.trim() === "" ||
    pdStock.trim() === "" ||
    pdStatus === null ||
    pdUpdate.trim() === "" ||
    pdDescription.trim() === ""
  ) {
    Swal.fire({
      icon: "error",
      title: "請全部欄位填寫完",
    });
    return false;
  } else {
    Swal.fire({
      icon: "success",
      title: "上傳成功",
    });
    return true;
  }
}