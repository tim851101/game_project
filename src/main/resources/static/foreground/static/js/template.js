fetch('/foreground/header.html')
    .then(response => response.text())
    .then(data => {
        document.getElementById('header').innerHTML = data;
    })
// fetch('/foreground/mobile.html')
//     .then(response => response.text())
//     .then(data => {
//         document.getElementById('mobile').innerHTML = data;
//     })
fetch('/foreground/support.html')
    .then(response => response.text())
    .then(data => {
        document.getElementById('support').innerHTML = data;
    })
fetch('/foreground/footer.html')
    .then(response => response.text())
    .then(data => {
        document.getElementById('footer').innerHTML = data;
    })