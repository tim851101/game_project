const shoppingcart = JSON.parse(localStorage.getItem('shoppingcart')) ?? [];

window.addEventListener('beforeunload', () => {   
    localStorage.setItem('shoppingcart', JSON.stringify(shoppingcart))
});







function set(pdNo, qty) {
    let item = shoppingcart.find((item) => (+item.pdNo === pdNo))
    if (item) {
        item.qty = qty;
    } else {
        item = {
            "pdNo": pdNo,
            "qty": qty
        }
        shoppingcart.push(item);
    }
}

function add(pdNo) {
    let item = shoppingcart.find((item) => (+item.pdNo === pdNo))
    if (item) {
        item.qty++;
    } else {
        item = {
            "pdNo": pdNo,
            "qty": 1
        }
        shoppingcart.push(item);
    }
}

function reduce(pdNo) {
    let item = shoppingcart.find((item) => (+item.pdNo === pdNo))
    if (item) {
        if(+item.qty <= 1){
            del(item.pdNo);
        }else{
            item.qty--;
        }
    } 
}

function del(pdNo) {
    let item = shoppingcart.find((item) => (+item.pdNo === pdNo))
    if (item) {
        const index = shoppingcart.indexOf(item);
        shoppingcart.splice(index,1);
    }
}