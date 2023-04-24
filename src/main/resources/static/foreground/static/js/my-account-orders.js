const memNo = localStorage.getItem('memNo')
render(+memNo);


function render(memNo) {
    const table = document.querySelector('#ordersTable');
    fetch(`/ord/getAllByMemNo?memNo=${+memNo}`, {
        methond: "POST"
    }).then((response) => {
        return response.json();
    }).then((list) => {
        let data = "";
        $('#ordersTable-1').DataTable().clear().destroy();

        let ordStatus = "";
        for (let item of list) {
            switch (item.ordStatus) {
                case (0):
                    ordStatus = '未出貨';
                    break;
                case (1):
                    ordStatus = '已出貨';
                    break;
                case (2):
                    ordStatus = '已到貨'
                    break;
                case (3):
                    ordStatus = '退貨申請';
                    break;
                case (4):
                    ordStatus = '退貨成功';
                    break;
                case (5):
                    ordStatus = '訂單完成';
                    break;
                case (6):
                    ordStatus = '訂單取消';
                    break;
            }
            switch (item.ordPick) {
                case (0):
                    item.ordPick = '店面取貨';
                    break;
                case (1):
                    item.ordPick = '超商取貨';
                    break;
                case (2):
                    item.ordPick = '宅配';
                    break;
            }
            switch (item.ordPayStatus) {
                case (0):
                    item.ordPayStatus = '未付款';
                    break;
                case (1):
                    item.ordPayStatus = '已付款';
                    break;
            }
            data += `
        <tr>
            <td>${item.ordNo}</td>
            <td>${item.ordCreate}</td>
            <td>$${item.actualAmount}</td>
            <td>${item.ordPayStatus}</td>
            <td id = ordStatus${item.ordNo}>${ordStatus}</td>
            <td>${item.ordPick}</td>
            <td><a href="order-detail.html?ordNo=${item.ordNo}" class="btn obrien-button-2 primary-color rounded-0">查詢</a></td>
            <td><button id = btn${item.ordNo} onclick="changeStatusByOrdNo(${item.ordNo},${memNo} ,3)" class="btn obrien-button-2 primary-color rounded-0" ${(+item.ordStatus>=3) ? 'style="visibility:hidden"':''}>退貨</button></td>
        </tr>`
        }
        table.innerHTML = data;

        $('#ordersTable-1').DataTable({
            pageLength: 5,
            lengthMenu: [5,10,15,20],
            language: {
                emptyTable: "無資料",
                info: "顯示 _START_ 至 _END_ 筆資料，共 _TOTAL_ 筆",
                lengthMenu: "顯示 _MENU_ 筆資料",
                paginate: {
                    previous: '<i class="fa fa-chevron-left"></i>',
                    next: '<i class="fa fa-chevron-right"></i>'
                },
            },
        });
    })
}

function changeStatusByOrdNo(ordNo,memNo, ordStatus) {
    const formData = {
        "ordNo": +ordNo,
        "ordStatus": +ordStatus
    }
    fetch('/ord/updateOrdState', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData),
    })
        .then(response => {
            $(`#ordStatus${ordNo}`).text('退貨申請')
            $(`#btn${ordNo}`).css('visibility','hidden')
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
        });
};
