
window.onload = () => {
    fetch(`/emp/ls-all`)
        .then(response => response.json()) // promise -> data
        .then(employees => {
            findAll(employees);
        })
    /**
     *
     */
    $('#emp-update-submit').click(() => {
        const employeeNo = $("#emp-update-no"); // 型態一定要對到
        const employeeName = $("#emp-update-name");
        const employeePhone = $("#emp-update-phone");
        const employeeAddress = $("#emp-update-addr");
        const employeeEmail = $("#emp-update-email");
        const employeePassword = $("#emp-update-pwd");
        if (!employeeName.val() || !employeePassword.val() || !employeeEmail.val()) {
            return;
        }
        const formData = {
            'employeeNo': +employeeNo.val(), // 型態一定要對到
            'employeeName': employeeName.val(),
            'employeePhone': employeePhone.val(),
            'employeeAddress': employeeAddress.val(),
            'employeeEmail': employeeEmail.val(),
            'employeePassword': employeePassword.val(),
            'roleNo': $('#emp-role option:selected').text() == '工讀生' ? 0 : 1,
            'employeeStatus': $("#emp-onJob").prop('checked')
        }
        employeeNo.val('');
        employeeName.val('');
        employeePhone.val('');
        employeeAddress.val('');
        employeeEmail.val('');
        employeePassword.val('');

        fetch('/emp/save', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                fetch(`/emp/ls-all`)
                    .then(response => response.json()) // promise -> data
                    .then(employees => {
                        findAll(employees);
                    })
                return response.json();
            })
            .then(() => {
            })
            .catch(error => {
                console.error('There was a problem with the fetch operation:', error);
            });
    });
}

function findAll(employees) {
    const empTable = document.getElementById('emp-ls-all');
    let tdString = "";
    for (const emp of employees) {
        tdString += `
                <tr>
                    <td><strong>${1000 + emp.employeeNo}</strong></td>
                    <td>
                        <div class="d-flex align-items-center">
                            <span class="w-space-no">${emp.employeeName}</span>
                            <img src="" class="rounded-lg me-2" width="24" alt="" />
                        </div>
                    </td>
                    <td>${emp.employeeEmail}</td>
                    <td>${emp.employeePhone}</td>
                    <td>
                        <div class="d-flex align-items-center">
                            <i class="fa fa-circle ${emp.employeeStatus ? 'text-success' : 'text-danger'} me-1"></i>
                            ${emp.employeeStatus ? '在職' : '離職'}
                        </div>
                    </td>
                </tr>
           `;
    }
    empTable.innerHTML = tdString;
}