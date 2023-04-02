const table =  document.querySelector('#ordersTable');
fetch('/ord/getAllByMemNo?memNo=5',{
    methond: "POST"
}).then((response)=>{
    return response.json();
}).then((list)=>{
    let data = "";
    for (let item of list) {
        switch (item.ordStatus) {
            case (0):
                item.ordStatus = '未出貨';
                break;
            case (1):
                item.ordStatus = '已出貨';
                break;
            case (2):
                item.ordStatus = '已到貨'
                break;
            case (3):
                item.ordStatus = '退貨申請';
                break;
            case (4):
                item.ordStatus = '退貨成功';
                break;
            case (5):
                item.ordStatus = '訂單完成';
                break;
            case (6):
                item.ordStatus = '訂單取消';
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
            <td>#${item.ordNo}</td>
            <td>${item.ordCreate}</td>
            <td>$${item.actualAmount}</td>
            <td>${item.ordPayStatus}</td>
            <td>${item.ordStatus}</td>
            <td>${item.ordPick}</td>
            <td><a href="order-detail.html?ordNo=${item.ordNo}" class="btn obrien-button-2 primary-color rounded-0">查詢</a></td>
            <td><button class="btn obrien-button-2 primary-color rounded-0">退貨</button></td>
        </tr>`
    }
    table.innerHTML = data;
})