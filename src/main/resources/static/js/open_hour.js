fetch(`/rev/ls`, {
    method: 'GET',
}).then((response) => {
    console.log(response);
    response.json().then(data => {
            // Create a table element
            const table = document.querySelector('#open-hour');
            table.setAttribute('border', '1');

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

// TODO: set value
