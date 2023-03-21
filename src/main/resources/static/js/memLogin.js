let loginData={};
$('#mem-login').click((e) => {
    e.preventDefault();
    // 取得user輸入的資料
    loginData.memEmail=document.getElementById("loginEmail").value;
    loginData.memPassword=document.getElementById("loginPassword").value;
    console.log(loginData);
    fetch(`/mem/login`, {
        method: 'POST',
        body: JSON.stringify(loginData),
        headers: {'Content-Type': 'application/json'},
    }).then(response => response.json())
    .then(data => console.log(data))
    .catch(error => console.error(error));
});

$('form').submit(e => {
    e.preventDefault();

    // console.log(JSON.stringify(registerData))

    fetch('http://localhost:8082/mem/login.html', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(loginData)
      })
      .then(response => {
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        return response.json();
      })
      .then(data => {
        console.log(data);
      })
      .catch(error => {
        console.error('There was a problem with the fetch operation:', error);
      });    
});