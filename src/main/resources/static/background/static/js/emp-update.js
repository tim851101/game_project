window.onload = () => {
    // list all employee
    fetch(`/emp/ls-all`)
        .then(response => response.json()) // promise -> data
        .then(employees => {
            findAll(employees);
        })

    // get all dom element we need
    const employeeNo = $("#emp-update-no");
    const employeeName = $("#emp-update-name");
    const employeePhone = $("#emp-update-phone");
    const employeeAddress = $("#emp-update-addr");
    const employeeEmail = $("#emp-update-email");
    const employeePassword = $("#emp-update-pwd");
    const employeeList = document.getElementById("emp-ls-all");
    const msgEmpNo = $('#msg-emp-no');
    const msgEmpName = $('#msg-emp-name');
    const msgEmpEmail = $('#msg-emp-email');
    const msgEmpPWD = $('#msg-emp-pwd');

    /**
     * insert and update sanity checker
     */
    employeeNo.blur(e => {
        if (e.target.value.length == 0) {
            msgEmpNo.text('更新員工編號必填');
            return;
        }
        msgEmpNo.text('');
    })
    employeeName.blur(e => {
        // if (employeeName.val().length == 0) {
        if (e.target.value.length == 0) {
            msgEmpName.text('姓名為必填');
            return;
        }
        msgEmpName.text('');
    });
    employeeEmail.blur(e => {
        // if (employeeName.val().length == 0) {
        if (e.target.value.length == 0) {
            msgEmpEmail.text('Email 為必填');
            return;
        }
        msgEmpEmail.text('');
    });
    employeePassword.blur(e => {
        // if (employeeName.val().length == 0) {
        if (e.target.value.length == 0) {
            msgEmpPWD.text('密碼為必填');
            return;
        }
        msgEmpPWD.text('');
    });

    /**
     * update to database when submit
     */
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


    /**
     * Edit employee info / auto insert update info to upper part table
     */
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

    /**
     * Choice Add or Update
     */
    $('#emp-item-group').click(e => {
        if (e.target.id === 'btn-emp-add') {
            $('#emp-no-text').text('');
            $("#emp-update-no").prop("disabled", true);
            $('#emp-update-no').attr('placeholder', 'ID will be generate automatically');
            msgEmpEmail.text(''); msgEmpName.text(''); msgEmpPWD.text(''); msgEmpNo.text('');
        } else if (e.target.id === 'btn-emp-update') {
            $('#emp-no-text').text('*');
            $("#emp-update-no").prop("disabled", false);
            msgEmpEmail.text(''); msgEmpName.text(''); msgEmpPWD.text(''); msgEmpNo.text('');
        }
    })
}

// list all
function findAll(employees) {
    // let empTable = document.getElementById('emp-ls-all');
    $('#table-example').DataTable().clear().destroy();
    $('#table-example').DataTable().destroy();

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
                            <a id="emp-edit-${emp.employeeNo}" href="#" class="btn btn-primary shadow btn-xs sharp me-1 emp-edit-btn">
                            <i id="emp-i-${emp.employeeNo}" class="fas fa-pencil-alt"></i></a>
                        </div>
                    </td>
                </tr>`;
    }
    // empTable.innerHTML = tdString;
    $('#emp-ls-all').html(tdString);

    /**
     * add pagination
     * @type {*|jQuery}
     */
    $('#table-example').DataTable({
        paging: true,
        pageLength: 3,
        language: {
            paginate: {
                previous: '<i class="fa fa-chevron-left"></i>',
                next: '<i class="fa fa-chevron-right"></i>'
            }
        }
        // ...
    });
}
