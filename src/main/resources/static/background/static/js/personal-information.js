window.onload = () => {
    const dummyId = 6;

    // get all dom element we need
    let employeeNo = null;
    const employeeName = $("#emp-update-name");
    const employeePhone = $("#emp-update-phone");
    const employeeAddress = $("#emp-update-addr");
    const employeeEmail = $("#emp-update-email");

    const token = sessionStorage.getItem('token');
    const tokenGotEmail = parseJwt(token).sub;
    fetch(`/emp/ls-by-email?email=${parseJwt(token).sub}`)
        .then(response => response.json())
        .then(emp => {
            employeeNo = emp.employeeNo;
            employeeName.val(emp.employeeName);
            employeeEmail.val(emp.employeeEmail);
            employeePhone.val(emp.employeePhone);
            employeeAddress.val(emp.employeeAddress);
        })
    /**
     * sanity check
     */
    $('#old-pwd').blur(e => {
        fetch(`/emp/pwd-check`, {
            method: 'POST',
            body: JSON.stringify({
                "email": employeeEmail.val(),
                "password": e.target.value + ""
            }),
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(response => response.json()) // promise -> data
            .then(ok => {
                if (ok) {
                    $('#msg-old-pwd').text('');
                    $('#new-pwd').prop('disabled', false);
                    $('#check-pwd').prop('disabled', false);
                } else {
                    $('#msg-old-pwd').text('舊密碼錯誤');
                    e.target.value = '';
                    $('#new-pwd').prop('disabled', true);
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

    // password show and hide toggle
    $('#new-show-pass').click(() => {
        const input = $('#new-pwd');
        const icon = $('#new-show-pass i');

        if (input.attr('type') === 'password') {
            input.attr('type', 'text');
            icon.removeClass('fa-eye-slash').addClass('fa-eye');
        } else {
            input.attr('type', 'password');
            icon.removeClass('fa-eye').addClass('fa-eye-slash');
        }
    });

    $('#check-show-pass').click(() => {
        const input = $('#check-pwd');
        const icon = $('#check-show-pass i');

        if (input.attr('type') === 'password') {
            input.attr('type', 'text');
            icon.removeClass('fa-eye-slash').addClass('fa-eye');
        } else {
            input.attr('type', 'password');
            icon.removeClass('fa-eye').addClass('fa-eye-slash');
        }
    });
    /**
     * update password
     */
    $('#emp-update-btn').click(e => {
        if ($('#msg-check-pwd').text().length != 0){
            return
        }

        fetch('/emp/save-one-pwd', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                'email': tokenGotEmail,
                'password': $('#check-pwd').val()
            })
        })
            .then(response => response.json())
            .then(ok => {
                if (ok) {
                    swal.fire('密碼更新成功');
                } else {
                    swal.fire('密碼更新失敗');
                }
            })
    })

    /**
     * update personal information
     */
    $('#emp-update-submit').click(() => {
        const formData = {
            'employeeNo': employeeNo,
            'employeeName': employeeName.val(),
            'employeePhone': employeePhone.val(),
            'employeeAddress': employeeAddress.val(),
            'employeeEmail': employeeEmail.val()
        }
        fetch('/emp/save-one-part', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        })
            .then(response => response.json())
            .then( ok => {
                if (ok) {
                    swal.fire('個人資料更新成功');
                } else {
                    swal.fire('個人資料更新失敗');
                }
            })
            .catch(() => {
                swal.fire('個人資料更新失敗!');
            });
    })
}
