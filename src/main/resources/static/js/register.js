/**
 * Get without param
 */
$('#show').click(() => {
    fetch(`/emp/ls`, {
        method: 'GET',
    }).then((response) => {
        response.json().then(data => {
                // Create a table element
                const table = document.querySelector('#tab1');
                table.setAttribute('border', '1');
                table.innerHTML = "";

                // Create the table header row
                const headerRow = table.insertRow();
                const headerCells = Object.keys(data[0]).map(key => `<th>${key}</th>`).join('');
                headerRow.innerHTML = headerCells;

                // Create the table body rows
                const bodyCells = data.map(obj => Object.values(obj).map(val => `<td>${val}</td>`).join(''));
                const bodyRows = bodyCells.map(cells => `<tr>${cells}</tr>`).join('');
                table.innerHTML += bodyRows;

            }
        )
    });
})

/**
 * Get with param
 */
$('#sent-get').click(e => {
    e.preventDefault();
    const empId = $('#empId').val();
    fetch(`/emp/get-by-id?id=${empId}`, {
        method: 'GET',
    }).then((response) => {
        response.text().then(res => {
            $('#sent-get-p').text(`id ${empId}'s name is ${res}`);
        });
    });
});

/**
 * Post
 */
$('#mem-reg').click(() => {
    fetch(`/mem/reg`, {
        method: 'POST',
        body: JSON.stringify({
            memName: 'John',
            memPhone: '12345678'
        }),
        headers: {'Content-Type': 'application/json'},
    }).then(response => {
        response.text().then(res => {
            alert(`post ${res?'success':'fail'}`)
        })
    })
    ;
});


function getOpenHour() {

}