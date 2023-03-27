let loginData={};
$('form').submit(e => {

    e.preventDefault();
    loginData.memEmail=document.getElementById("loginEmail").value;
    loginData.memPassword=document.getElementById("loginPassword").value;
    // console.log(JSON.stringify(registerData))

    fetch('/mem/login', {
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
        if(data) sessionStorage.setItem('memberEmail', loginData.memEmail);
      })
      .catch(error => {
        console.error('There was a problem with the fetch operation:', error);
      });    
});