
window.onload = () => {
    fetch(`/emp/ls-all`)
        .then(response => response.json()) // promise -> data
        .then(employees => {
            findAll(employees);
        })
    /**
     * update submit
     */
    const employeeNo = $("#emp-update-no"); // 型態一定要對到
    const employeeName = $("#emp-update-name");
    const employeePhone = $("#emp-update-phone");
    const employeeAddress = $("#emp-update-addr");
    const employeeEmail = $("#emp-update-email");
    const employeePassword = $("#emp-update-pwd");
    const employeeList = document.getElementById("emp-ls-all");

    // update employee
    $('#emp-update-submit').click(() => {
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

        // reset input after submit
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

    // edit and auto insert update info
    employeeList.addEventListener('click', (e) => {
        const empId = parseInt(e.target.id.split('-')[2]);
        fetch(`/emp/ls-one?id=${empId}`)
            .then(response => response.json()) // promise -> data
            .then(emp => {
                employeeNo.val(emp.employeeNo);
                employeeName.val(emp.employeeName);
                employeeEmail.val(emp.employeeEmail);
                employeePassword.val(emp.employeePassword);
                employeePhone.val(emp.employeePhone);
                employeeAddress.val(emp.employeeAddress);
            })
    }, true);
}

// list all
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
                    <td>
                        <div class="d-flex">
                            <a id="emp-edit-${emp.employeeNo}" href="#" class="btn btn-primary shadow btn-xs sharp me-1 emp-edit-btn"><i
                                    class="fas fa-pencil-alt"></i></a>
                        </div>
                    </td>
                </tr>
           `;
    }
    empTable.innerHTML = tdString;
}