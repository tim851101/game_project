fetch('/background/chatbox.html')
    .then(response => response.text())
    .then(data => {
        document.getElementById('chatbox').innerHTML = data;
    })
fetch('/background/header.html')
    .then(response => response.text())
    .then(data => {
        document.getElementById('header').innerHTML = data;
    })
// fetch('/background/sidebar.html')
//     .then(response => response.text())
//     .then(data => {
//         document.getElementById('sidebar').innerHTML = '';
//             document.getElementById('sidebar').innerHTML = data;
//     })
fetch('/background/footer.html')
    .then(response => response.text())
    .then(data => {
        document.getElementById('footer').innerHTML = data;
    })