// let myKey="f1688d3469e422af82cc95be8c05cdee";
// let city="Taoyuan";
// let url=`https://api.openweathermap.org/data/2.5/weather?q=${city}&appid=${myKey}`;

// async function weather(){
//     try{
//         let result=await fetch(url);
//         let data=await result.json();
//         console.log(data);
//     }catch(e){
//         console.log(e);
//     }
// }
// weather();




window.onload = function() {
    // code to be executed when the page finishes loading
    let emailContext={};
    $('#send-email').click((e) => {
        e.preventDefault();
        // 取得user輸入的資料
        emailContext.name=document.getElementById('con_name').value;
        emailContext.from=document.getElementById('con_email').value;
        emailContext.subject=document.getElementById('con_content').value;
        emailContext.text=document.getElementById("con_message").value;
        console.log(emailContext);
        fetch(`/test/send-email`, {
            method: 'POST',
            body: JSON.stringify(emailContext),
            headers: {'Content-Type': 'application/json'},
        }).then(response => response.text())
        .then(data => console.log(data))
        .catch(error => console.error(error));
    });

    $('#contact-form').submit(e=>{
        e.preventDefault();
        // Send form data as POST request
        fetch(e.target.action, {
            method: 'POST',
            body: JSON.stringify(emailContext),
            headers: {'Content-Type': 'application/json'}
        })
        .then(response => {
            if (!response.ok) {
            throw new Error('Network response was not ok');
            }
            return response.text();
        })
        .then(data => {
            console.log(data);
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
        });
    });
}
