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
window.onload = function() {
 const findByIdBtn = document.getElementById('findById');
     const inputEmployeeId = document.getElementById('productId');
     const showTable = document.getElementById('show');

     findByIdBtn.addEventListener('click', () => {
         const productId = inputEmployeeId.value;

         fetch(`/product/find-one?id=${productId}`) //response
             .then(response => response.json()   //promise 轉 data
             )
             .then(data => {
                 console.log(`/emp/find-one?id='${productId}'`);
                 console.log(data);
                 showTable.setAttribute('border', '1');

                 const table = document.getElementById('show');
                 table.innerHTML="";
                 for (const property in data) {
                     const row = table.insertRow();
                     const keyCell = row.insertCell();
                     const valueCell = row.insertCell();
                     keyCell.innerHTML = property;
                     valueCell.innerHTML = data[property];
                 }
             })
     })
}

//    查詢全部
 window.onload = function() {
   const allshowTable = document.getElementById('allshow');
   const paginationContainer = document.querySelector('.pagination');

   const ITEMS_PER_PAGE = 10;

   // 獲取所有商品數據
   fetch(`/product/get-all`)
     .then(response => response.json())
     .then(data => {
       console.log(data);
       const recordsPerPage = ITEMS_PER_PAGE;

       // 分頁
       const pageCount = Math.ceil(data.length / recordsPerPage);
       const pagination = generatePagination(pageCount, data);

       paginationContainer.appendChild(pagination);

       showPage(1, data);
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
       statusCell.innerHTML = product.pdStatus;
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