$('#button_submit').click(e => {
    e.preventDefault();
    // type must be the samew
    const ordFormData = {
        // "memNo": +$("#memNo").val(),
        "memNo": 10,
        "useCoupon": +$("#useCoupon").text(),
        "ordFee": +$("#ordFee").text(),
        "ordPick": +$("#ordPick").val(),
        "totalAmount": +$("#totalAmount").text(),
        "actualAmount": +$("#actualAmount").text(),
        "recipient": $("#recipient").val(),
        "recipientAddres": $("#recipientAddres").val(),
        "recipientPh": $("#recipientPh").val()
    }
    // Send form data as POST request
    fetch('/ord/save', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(ordFormData),
    })
        .then((response) =>{ 
            return response.text();
        })
        .then((data) => {
            saveOrdList(+data,10,10,1000);
            saveOrdList(+data,3,10,1000);
            saveOrdList(+data,2,10,1000);
        })
        .catch((error) => {
            console.error('There was a problem with the fetch operation:', error);
        });
});


function saveOrdList(ordNo,pdNo,qty,price){
    const ordListData ={
        "ordNo": +ordNo,
        "pdNo": +pdNo,
        "qty": +qty,
        "price": +price
    };
    console.log(ordListData);
    fetch('/ordList/save',{
        method: 'POST',
        headers: {
            'Content-Type':'application/json'
        },
        body: JSON.stringify(ordListData),
    }).then(response =>{ 
        console.log(response);
    }).catch(error => {
        console.error('There was a problem with the fetch operation:', error);
    });
}