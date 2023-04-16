window.onload = () => {
    const dummyId = 6;

    // get all dom element we need
    const employeeNo = $("#emp-update-no");
    const employeeName = $("#emp-update-name");
    const employeePhone = $("#emp-update-phone");
    const employeeAddress = $("#emp-update-addr");
    const employeeEmail = $("#emp-update-email");
    const employeePassword = $("#emp-update-pwd");
    const employeeRole = $('#emp-role');

    const token = sessionStorage.getItem('token');
    const email = parseJwt(token).sub;
    console.log(email);
    fetch(`/emp/ls-by-email?email=${parseJwt(token).sub}`)
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
        console.log('target:' + e.target.value)
        console.log({
            "email": employeeEmail.val(),
            "password": e.target.value + ""
        })
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
    $('#emp-update-submit').click(() => {
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
            .then(() => {
            })
            .catch(error => {
                console.error('There was a problem with the fetch operation:', error);
            });
    })
}

function parseJwt (token) {
    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    const jsonPayload = decodeURIComponent(window.atob(base64).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));

    return JSON.parse(jsonPayload);
}
