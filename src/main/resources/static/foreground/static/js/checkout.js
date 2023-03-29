
$('#button_submit').click(e => {
    e.preventDefault();
    // type must be the samew
    const formData = {
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
    console.log(formData);
    // Send form data as POST request
    fetch('/ord/save', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData),
    })
        .then(response => response.json()
        )
        .then(data => {
            const ordNo = data;
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
        });
        console.log(ordNo);
});