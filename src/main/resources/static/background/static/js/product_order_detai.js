const table1 = document.querySelector('#orderDetail');
const table2 = document.querySelector('#money');
const ordNo = location.href.split('?')[1].split('=')[1];
fetch('/ordList/getBackgroundOrderListByOrdNo?ordNo='+ordNo,{
methond:'GET'
}).then(response=>{
    return response.json();
}).then( (list) => {
    let data1 = '';
    let totalAmount=0;
    let ordFee=0;
    let useCoupon=0;
    let actualAmount=0;
    for (let item of list){ 
        data1 += `
            <tr>
                <td class="center fs-3">1</th>
			    <td class="center"><img src="https://uploads-ssl.webflow.com/575714cc825e8dbc6c83b98a/5ab8f2a51f33703defd7807f_carcassonne_BOX_3D_2014.jpg" alt="" width="75px" height="75px"></td>
                <td class="left fs-3">${item.pdName}</td>
                <td class="right fs-3">$${item.price}</td>
                <td class="center fs-3">${item.qty}</td>
                <td class="right fs-3">$${item.price*item.qty}</td>
            </tr>`;
        totalAmount =item.totalAmount
        ordFee=item.ordFee;
        useCoupon=item.useCoupon;
        actualAmount=item.actualAmount;
    }
    table1.innerHTML = data1;
    table2.innerHTML = `
        <tbody >
            <tr>
                <td class="left fs-4"><strong>商品總價</strong></td>
                <td class="right fs-4">$${totalAmount}</td>
            </tr>
            <tr>
                <td class="left fs-4"><strong>運費</strong></td>
                <td class="right fs-4">$${ordFee}</td>
            </tr>
            <tr>
                <td class="left fs-4"><strong>使用回饋金</strong></td>
                <td class="right fs-4">$-${useCoupon}</td>
            </tr>
        </tbody>
		<tfoot>
			<td class="left fs-4"><strong>應付款</strong></td>
			<td class="right fs-4"><strong>$${actualAmount}</strong></td>
		</tfoot>`
})

