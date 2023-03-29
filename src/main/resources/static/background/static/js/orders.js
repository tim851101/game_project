
const table = document.querySelector("tbody#orders")
function render() {
    // table.innerHTML = "";
    fetch('/ord/getAllOrdJoinMemName', {
        method: 'GET'
    })
        .then(resp => resp.json())
        .then(list => {
            let data = "";
            for (let item of list) {
                switch (item.ordStatus) {
                    case (0):
                        item.ordStatus = '<span class="badge badge-warning">未出貨<span class="ms-1 fas fa-stream"></span></span>';
                        break;
                    case (1):
                        item.ordStatus = '<span class="badge badge-primary">已出貨<span class="ms-1 fa fa-redo"></span></span>';
                        break;
                    case (2):
                        item.ordStatus = '<span class="badge badge-primary">已到貨<span class="ms-1 fa fa-redo"></span></span>'
                        break;
                    case (3):
                        item.ordStatus = '<span class="badge badge-warning">退貨申請<span class="ms-1 fas fa-stream"></span></span>';
                        break;
                    case (4):
                        item.ordStatus = '<span class="badge badge-success">退貨成功<span class="ms-1 fa fa-check"></span></span>';
                        break;
                    case (5):
                        item.ordStatus = '<span class="badge badge-success">訂單完成<span class="ms-1 fa fa-check"></span></span>';
                        break;
                    case (6):
                        item.ordStatus = '<span class="badge badge-success">訂單取消<span class="ms-1 fa fa-check"></span></span>';
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
                        item.ordPayStatus = `<span class="badge badge-secondary">未付款<span class="ms-1 fa fa-ban"></span></span>`;
                        break;
                    case (1):
                        item.ordPayStatus = `<span class="badge badge-success">已付款<span class="ms-1 fa fa-check"></span></span>`;
                        break;
                }

                data += `
                <tr class="btn-reveal-trigger">
                    <td id= "ordNo${item.ordNo}" class="py-2 text-center">${item.ordNo}</td>
                    <td class="py-2 text-center">${item.memName}</td>  
                    <td class="py-2 text-center">${item.ordCreate}</td>
                    <td class="py-2 text-center">$${item.actualAmount}</td>
                    <td class="py-2 text-center">${item.ordPayStatus}</td>
                    <td class="py-2 text-center">${item.ordStatus}</td>
                    <td class="py-2 text-center">${item.ordPick}</td>
                    <td class="py-2 text-center">${item.recipient}</td>
                    <td class="py-2" ><address>${item.recipientAddres}</address></td>
                    <td class="py-2 text-center">${item.recipientPh}</td>
                    <td>
                        <form action="product_order_detail.html">
                            <input type="hidden" name="ordNo" value="${item.ordNo}"/>
                            <input type="submit" class="btn btn-primary btn-xxs" style="padding: 0.4rem 0.8rem;" value="查詢">
                        </form>
                    </td>
                    <td>
                        <div class="dropdown text-center"><button class="btn btn-primary tp-btn-light sharp" type="button" id="order-dropdown-0" data-bs-toggle="dropdown" data-boundary="viewport" aria-haspopup="true" aria-expanded="false"><span><svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="18px" height="18px" viewbox="0 0 24 24" version="1.1"><g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd"><rect x="0" y="0" width="24" height="24"></rect><circle fill="#000000" cx="5" cy="12" r="2"></circle><circle fill="#000000" cx="12" cy="12" r="2"></circle><circle fill="#000000" cx="19" cy="12" r="2"></circle></g></svg></span></button>
                            <div class="dropdown-menu dropdown-menu-end border py-0" aria-labelledby="order-dropdown-0">
                                <div class="py-2">
                                    <button  class="dropdown-item" onclick="but(${item.ordNo},0)">未出貨</button>
                                    <button  class="dropdown-item" onclick="but(${item.ordNo},1)">已出貨</button>
                                    <button  class="dropdown-item" onclick="but(${item.ordNo},2)">已到貨</button>
                                    <button  class="dropdown-item" onclick="but(${item.ordNo},3)">退貨申請</button>
                                    <button  class="dropdown-item" onclick="but(${item.ordNo},4)">退貨成功</button>
                                    <button  class="dropdown-item" onclick="but(${item.ordNo},5)">訂單完成</button>
                                    <button  class="dropdown-item" onclick="but(${item.ordNo},6)">訂單取消</button>
                                    <div class="dropdown-divider"></div>
                                    <button  class="dropdown-item" onclick="">已付款</button>
                                    <button  class="dropdown-item" onclick="">未付款</button>
                                </div>
                            </div>
                        </div>
                    </td>
                </tr>`
                table.innerHTML = data;
            }
        });

    // let table1 = $('#table1').DataTable();
    // table1.destroy();
    // $('#table1').DataTable({
    //     paging: true,
    //     pageLength: 3
    // });

}


function but(ordNo, statu) {
    console.log(`/ord/updateOrdState?ordNo=${ordNo}&ordStatus=${statu}`);
    fetch(`/ord/updateOrdState?ordNo=${ordNo}&ordStatus=${statu}`, {
        method: 'GET'
    })
    setTimeout(render, 100)
};


render();




