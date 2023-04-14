$(document).ready(e => {
    fetch('/event/ls-event')
        .then(response => {
            if (!response.ok) {
                console.log("event no return");
            }
            return response.json();
        })
        .then(data => {
            findAll(data);
        });
});
function findAll(data) {
    const select = $('#select');
    let option = "";
    option = `<option id="change">-----請選擇賽事-----</option>`
    for (const e of data) {
        if (e.eventLimit != 0) {
            option += `<option value="${e.eventName}">${e.eventName}</option>`
        }
    }
    select.html(option);
    $("select").on('change', e => {
        const table = $('#Registration');
        let toString = "";
        let val = document.getElementById('select').value
        $("option").remove("#change");
        for (const e of data) {
            if (val === e.eventName) {
                toString +=
                    `
                <div class="col-md-7 col-lg-6 text-center justify-content-center col-custom">
                <div class="feature-content-wrapper">
                    <table class="table">
                        <thead>
                            <tr>
                                <th scope="col">賽事名稱：</th>
                                <td><b>${e.eventName}</b></td>
                                <th scope="col">費用：</th>
                                <td><b>${e.eventFee}</b></td>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <th scope="row">比賽日期：</th>
                                <td><b>${e.eventDate}　${e.eventStarttime} - ${e.eventEndtime}</b></td>
                            </tr>
                            <tr>
                                <th scope="row">報名時間：</th>
                                <td><b>${e.signupStartTime}<br>${e.signupDeadline}</b></td>
                            </tr>
                            <tr>
                                <th scope="row">報名狀況：</th>
                                <td><b>${e.signupNum}／${e.eventLimit}</b></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div>
                <tr>`
                switch (e.signupNum === e.eventLimit) {
                    case true: {
                        toString += `
                        <button  type="button" class="obrien-button btn btn-secondary btn-lg" disabled>已額滿</button>
                        `
                        break;
                    }
                    case false: {
                        toString += `
                        <th><button><a href="index.html" class="obrien-button btn-warning btn-outline-dark">前往報名</a></button></th>
                        `
                        break;
                    }
                }
                toString += `
                        <button id="checkEvent${e.eventNo}" ><a class="obrien-button btn-outline-dark btn-primary">賽事簡介</a></button>
                    </tr>   
                </div>
            </div>`
            }
        }
        toString += `
        <div class="col-md-5 col-lg-6 text-center col-custom">
                        <div class="banner-thumb h-100 d-flex justify-content-center align-items-center">
                            <img src="/foreground/static/picture/105.jpg" alt="Banner Thumb">
                        </div>
                    </div>
        `
        table.html(toString);
        $('button[id^="checkEvent"]').on('click', function alertTest() {
            let buttonId = $(this).attr('id');
            let eventNo = buttonId.match(/checkEvent(\d+)/)[1];
            for (const e of data) {
                if (e.eventNo == eventNo) {
                    Swal.fire({
                        text: e.eventDisc,
                        confirmButtonText: '關閉',
                        confirmButtonColor: '#D0D0D0',
                        showClass: {
                            popup: 'animate__animated animate__fadeInDown'
                        },
                        hideClass: {
                            popup: 'animate__animated animate__fadeOutUp'
                        }
                    })
                }
            }
        });
    });
}








