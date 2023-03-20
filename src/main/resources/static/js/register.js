/**
 * Get user input data
 */

/**
 * Get with param
 */
// $('#sent-get').click(e => {
//     e.preventDefault();
//     const empId = $('#empId').val();
//     fetch(`/emp/get-by-id?id=${empId}`, {
//         method: 'GET',
//     }).then((response) => {
//         response.text().then(res => {
//             $('#sent-get-p').text(`id ${empId}'s name is ${res}`);
//         });
//     });
// });

/**
 * Post
 */
var registerData={};
$('#mem-reg').click(() => {
    // 取得user輸入的資料
    registerData.memName=document.getElementById('mem_name').value;
    registerData.memEmail=document.getElementById("mem_email").value;
    registerData.memPassword=document.getElementById("mem_password").value;
    registerData.memPhone=document.getElementById("mem_phone").value;
    registerData.memAddress=document.getElementById("mem_address").value;
    registerData.memBirthday=document.getElementById("mem_birthday").value;
    registerData.memGender=document.querySelector('input[name="genderOptions"]:checked').value;
    console.log(registerData);
    fetch(`/mem/reg`, {
        method: 'POST',
        body: JSON.stringify(registerData),
        headers: {'Content-Type': 'application/json'},
    }).then(response => response.json())
    .then(data => console.log(data))
    .catch(error => console.error(error));
    // then(response => {
    //     response.text().then(res => {
    //         alert(`post ${res?'success':'fail'}`)
    //     })
    // });

});

$('form').submit(e => {
    e.preventDefault();

    const jsonData = JSON.stringify(registerData);

    console.log('json', jsonData);
    console.log(JSON.stringify(registerData))

    fetch(e.target.action, {
        method: 'POST',
        body: jsonData,
        headers: {'Content-Type': 'application/json'},
    })
});
// function getOpenHour() {

// }