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
    let n = 0;
    for (let item of list){ 
        n++
        data1 += `
            <tr>
                <td class="pro-thumbnail"><img class="img-fluid" src="static/picture/14.jpg" alt="Product"></td>
                <td class="pro-title">${item.pdName}</td>
                <td class="pro-price"><span>$${item.price}</span></td>
                <td class="pro-quantity"><span>${item.qty}</span></td>
                <td class="pro-subtotal"><span>$${item.price*item.qty}</span></td>
            </tr>`;
        totalAmount =item.totalAmount
        ordFee=item.ordFee;
        useCoupon=item.useCoupon;
        actualAmount=item.actualAmount;
    }
    table1.innerHTML = data1;
    table2.innerHTML = `
                    <tbody>
                        <tr>
                            <td>商品總價</td>
                            <td class="text-end">$${actualAmount}</td>
                        </tr>
                        <tr>
                            <td>使用回饋金</td>
                            <td class="text-end">- $${useCoupon}</td>
                        </tr>
                        <tr>
                            <td>運費</td>
                            <td class="text-end">$${ordFee}</td>
                        </tr>
                    </tbody>
                    <tfoot class="total">
                        <tr>    
                            <td>應付金額</td>
                            <td class="text-end total-amount">$${actualAmount}</td>
                        </tr>
                    </tfoot>`
})

