const cartBody = document.querySelector("#cartbody")
cartBody.innerHTML = '';
let n = 1;
for(item of shoppingcart){
    const qty = item.qty;
    const pdNo = item.pdNo
    fetch(`/product/find-one?id=${pdNo}`,{
        method:"GET"
        }).then(response => response.json()
        ).then(data=>{
        cartBody.innerHTML += `<tr>
                    <td class = pdNo style = "display:none">${pdNo}</td>
                    <td class="pro-thumbnail"><a href="product-details.html?pdNo=${pdNo}"><img class="img-fluid" src="static/picture/14.jpg" alt="Product"></a></td>
                    <td class="pro-title"><a href="product-details.html?pdNo=${pdNo}">${data.pdName}</td>
                    <td class="pro-price"><span>$${data.pdPrice}</span></td>
                    <td class="pro-quantity">
                        <div class="quantity">
                            <div class="cart-plus-minus">
                                <input id = "qty${n}" class="cart-plus-minus-box" value="${qty}" type="text">
                                <div class="dec qtybutton">-</div>
                                <div class="inc qtybutton">+</div>
                                <div class="dec qtybutton"><i class="fa fa-minus"></i></div>
                                <div class="inc qtybutton"><i class="fa fa-plus"></i></div>
                            </div>
                        </div>
                    </td>
                    <td id = "totalAmount${n++}" class="pro-subtotal"><span>$${qty*data.pdPrice}</span></td>
                    <td class="pro-remove"><a href="javascript:void(0)"><i class="ion-trash-b"></i></a>
                    </td>
                </tr>`;

        $('.cart-plus-minus').append(
            '<div class="dec qtybutton"><i class="fa fa-minus"></i></div><div class="inc qtybutton"><i class="fa fa-plus"></i></div>'
        );
        $('.qtybutton').on('click', function () {
            var $button = $(this);
            var oldValue = $button.parent().find('input').val();
            if ($button.hasClass('inc')) {
                var newVal = parseFloat(oldValue) + 1;
            } else {
                // Don't allow decrementing below zero
                if (oldValue > 1) {
                    var newVal = parseFloat(oldValue) - 1;
                } else {
                    newVal = 1;
                }
            }
            $button.parent().find('input').val(newVal);

            var unitPrice = +$button.closest("tr").find(".pro-price span").text().replace("$", "");
            $button.closest('tr').find(".pro-subtotal span").text("$" + (unitPrice * newVal))
        });

        const deleteButtons = document.querySelectorAll('.pro-remove');
        
        deleteButtons.forEach(button => {
            button.addEventListener('click', () => {
                const pdNoNode = button.closest('tr').querySelector('.pdNo');
                del(pdNoNode.textContent);
                const productRow = button.parentNode;
                productRow.remove();
            });
        });

                
    });

}












// function showproduct(prdNo){ 
//     fetch(`/product/find-one?id=${prdNo}`,{
//     methone:"GET"
//     }).then(response => response.json()
//     ).then(data=>{
//     console.log(data)
    
//     cartBody.innerHTML += `<tr>
//                 <td class="pro-thumbnail"><a href="#"><img class="img-fluid" src="static/picture/14.jpg" alt="Product"></a></td>
//                 <td class="pro-title"><a href="#">${data.pdName}</td>
//                 <td class="pro-price"><span>$${data.pdPrice}</span></td>
//                 <td class="pro-quantity">
//                     <div class="quantity">
//                         <div class="cart-plus-minus">
//                             <input id = "qty" class="cart-plus-minus-box" value="0" type="text">
//                             <div class="dec qtybutton">-</div>
//                             <div class="inc qtybutton">+</div>
//                             <div class="dec qtybutton"><i class="fa fa-minus"></i></div>
//                             <div class="inc qtybutton"><i class="fa fa-plus"></i></div>
//                         </div>
//                     </div>
//                 </td>
//                 <td id = "totalAmount${data.pdNo}" class="pro-subtotal"><span>$100</span></td>
//                 <td class="pro-remove"><a href="#"><i class="ion-trash-b"></i></a>
//                 </td>
//             </tr>`
//     })
// }