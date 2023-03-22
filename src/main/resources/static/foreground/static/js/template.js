fetch('header.html')
    .then(response => response.text())
    .then(data => {
        document.getElementById('header').innerHTML = data;
    })
fetch('mobile.html')
    .then(response => response.text())
    .then(data => {
        document.getElementById('mobile').innerHTML = data;
    })
fetch('support.html')
    .then(response => response.text())
    .then(data => {
        document.getElementById('support').innerHTML = data;
    })
fetch('footer.html')
    .then(response => response.text())
    .then(data => {
        document.getElementById('footer').innerHTML = data;
    })