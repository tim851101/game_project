
const table = document.querySelector("tbody#orders")

fetch('/ord/getAll', {
    method: 'GET'
})
    .then(resp => resp.json())
    .then(list => {
        for (let item of list){
            switch(item.ordStatus){
                case(0):
                    item.ordStatus='<span class="badge badge-warning">未出貨<span class="ms-1 fas fa-stream"></span></span>';
                    break;
                case(1):
                    item.ordStatus='<span class="badge badge-primary">已出貨<span class="ms-1 fa fa-redo"></span></span>';
                    break;
                case(2):
                    item.ordStatus='<span class="badge badge-primary">已到貨<span class="ms-1 fa fa-redo"></span></span>'
                    break;
                case(3):
                    item.ordStatus='<span class="badge badge-warning">退貨申請<span class="ms-1 fas fa-stream"></span></span>';
                    break;
                case(4):
                    item.ordStatus='<span class="badge badge-success">退貨成功<span class="ms-1 fa fa-check"></span></span>';
                    break;
                case(5):
                    item.ordStatus='<span class="badge badge-success">訂單完成<span class="ms-1 fa fa-check"></span></span>';
                    break;
                case(6):
                    item.ordStatus='<span class="badge badge-success">訂單取消<span class="ms-1 fa fa-check"></span></span>';
                    break;
            }
            switch(item.ordPick){
                case(0):
                    item.ordPick='店面取貨';
                    break;
                case(1):
                    item.ordPick='超商取貨';
                    break;
                case(2):
                    item.ordPick='宅配';
                    break;
            }
            switch(item.ordPayStatus){
                case(0):
                    item.ordPayStatus=`<span class="badge badge-secondary">未付款<span class="ms-1 fa fa-ban"></span></span>`;
                    break;
                case(1):
                    item.ordPayStatus=`<span class="badge badge-success">已付款<span class="ms-1 fa fa-check"></span></span>`;
                    break;
            }
            table.innerHTML+=`
            <tr class="btn-reveal-trigger">
                <td class="py-2 text-center">${item.ordNo}</td>
                <td class="py-2 text-center">${item.memNo}</td>  
                <td class="py-2 text-center">${item.ordCreate}</td>
                <td class="py-2 text-center">$${item.actualAmount}</td>
                <td class="py-2 text-center">${item.ordPayStatus}</td>
                <td class="py-2 text-center">${item.ordStatus}</td>
                <td class="py-2 text-center">${item.ordPick}</td>
                <td class="py-2 text-center">${item.recipient}</td>
                <td class="py-2" ><address>${item.recipientAddres}</address></td>
                <td class="py-2 text-center">${item.recipientPh}</td>
                <td>
                    <form action="Product-details.html">
                        <input type="hidden" name="ordNo" value="${item.ordNo}"/>
                        <input type="submit" class="btn btn-primary btn-xxs" style="padding: 0.4rem 0.8rem;" value="查詢">
                    </form>
                </td>
            <td><button type="button" class="btn btn-primary btn-xxs" style="padding: 0.4rem 0.8rem;">修改</button></td>
          </tr>`
        }
    });