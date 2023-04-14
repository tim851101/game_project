
window.onload = () => {
    const dummyId = 2;

    // get all dom element we need
    const employeeNo = $("#emp-update-no");
    const employeeName = $("#emp-update-name");
    const employeePhone = $("#emp-update-phone");
    const employeeAddress = $("#emp-update-addr");
    const employeeEmail = $("#emp-update-email");
    const employeePassword = $("#emp-update-pwd");
    const employeeRole = $('#emp-role');

    // TODO: get below information from login
    fetch(`/emp/ls-one-join-role?id=${dummyId}`)
        .then(response => response.json()) // promise -> data
        .then(emp => {
            employeeNo.val(emp.employeeNo);
            employeeName.val(emp.employeeName);
            employeeEmail.val(emp.employeeEmail);
            employeePassword.val(emp.employeePassword);
            employeePhone.val(emp.employeePhone);
            employeeAddress.val(emp.employeeAddress);
            employeeRole.val(emp.roleName);
        })

    /**
     * sanity check
     */
    $('#old-pwd').blur(e => {
        fetch(`/emp/ls-one-pwd?id=${dummyId}`)
            .then(response => response.text()) // promise -> data
            .then(pwd => {
                if (e.target.value !== pwd) {
                    $('#msg-old-pwd').text('舊密碼錯誤');
                    e.target.value = '';
                    $('#new-pwd').prop('disabled', true);
                } else {
                    $('#msg-old-pwd').text('');
                    $('#new-pwd').prop('disabled', false);
                    $('#check-pwd').prop('disabled', false);
                }
            })
    })
    $('#new-pwd').blur(e => {
        if (e.target.value.length < 1) {
            $('#msg-new-pwd').text('新密碼不能為空');
        } else {
            $('#msg-new-pwd').text('');
        }
    })

    $('#check-pwd').blur(e => {
        if (e.target.value !== $('#new-pwd').val()) {
            $('#msg-check-pwd').text('密碼不同');
        } else {
            $('#msg-check-pwd').text('');
        }
    })

    /**
     * update password
     */
    $('#emp-update-btn').click(e => {
        console.log($('#check-pwd').val())
        fetch('/emp/save-one-pwd', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                'id': dummyId,
                'password': $('#check-pwd').val()
            })
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
            })
    })

    /**
     * update personal information
     */
    $('#emp-update-submit').click( () => {
        const formData = {
            'employeeNo': dummyId, // 型態一定要對到
            'employeeName': employeeName.val(),
            'employeePhone': employeePhone.val(),
            'employeeAddress': employeeAddress.val(),
            'employeeEmail': employeeEmail.val()
        }
        console.log('fromData', formData);

        fetch('/emp/save-one-part', {
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
            })
            .then(() => {})
            .catch(error => {
                console.error('There was a problem with the fetch operation:', error);
            });

    })

}