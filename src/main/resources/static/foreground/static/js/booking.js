// Initialize the Datepicker
InitialDatepicker = () => {
    const MonthFromNow = new Date();
    MonthFromNow.setMonth(MonthFromNow.getMonth() + 2);

    $('#datepicker').datepicker({
        dateFormat: 'yy-mm-dd',
        showOtherMonths: true,
        selectOtherMonths: true,
        beforeShowDay: function (date) {
            // Disable dates more than n months from now
            return [date >= new Date() && date <= MonthFromNow];
        }
    });
};
$(window).ready(() => {
    InitialDatepicker();
    const hourBlock = $('.hour-block');
    for (const block of hourBlock) {
        $(block).addClass('locked');
    }
    // datepicker
    $('#datepicker').change(e => {
        $('#warn-datepicker').text('');
        fetch(`/seat/find/${e.target.value}`, {
        })
            .then(response => response.json())
            .then(seats => {
                for (let i = 0; i < seats.length; i++) {
                    if (seats[i] > 0) {
                        $(`#hour-${i + 1}`).css("opacity", "1");
                        $(`#hour-${i + 1}`).removeClass('locked');
                    } else {
                        $(`#hour-${i + 1}`).css("opacity", "0.5");
                        $(`#hour-${i + 1}`).addClass('locked');
                    }
                }
            })
            .catch(error => console.error(error));
    })
    // select how many people booking
    $('#booking-amount').blur(e => {
        if (e.target.value.length != ''){
            $('#warn-amount').text('');
        } else {
            $('#warn-amount').text('請選擇人數');
            return
        }
        fetch(`/seat/find/${$('#datepicker').val()}`, {
        })
            .then(response => response.json())
            .then(seats => {
                for (let i = 0; i < seats.length; i++) {
                    if (seats[i] > 0 && seats[i] >= e.target.value) {
                        $(`#hour-${i + 1}`).css("opacity", "1");
                        $(`#hour-${i + 1}`).removeClass('locked');
                    } else {
                        $(`#hour-${i + 1}`).css("opacity", "0.5");
                        $(`#hour-${i + 1}`).addClass('locked');
                    }
                }
            })
            .catch(error => console.error(error));
    })

    // toggle select
    $('.hour-block').click( e => {
        if (!$(e.target).hasClass('locked')) {
            $(e.target).toggleClass('book-selected');
        }
    })

    // submit logic and sweat alert
    $('#booking-submit-button').click(e => {
        const date = $('#datepicker').val()
        const amount = $('#booking-amount').val();
        if (amount.length == ''){
            $('#warn-amount').text('請選擇人數');
            return;
        }
        if (!date){
            $('#warn-datepicker').text('請選擇日期');
            return;
        }
        let seatList = [];
        const selected =  $('.book-selected');
        for (const select of selected) {
            const index = $(select).attr('id').split('-')[1]
            seatList.push({
                "date": date,
                "hour": +index,
                "change": +amount
            })
        }
        console.log(seatList)
        fetch(`/seat/update`, {
            method: 'POST',
            body: JSON.stringify(seatList),
            headers: {'Content-Type': 'application/json'},
        }).then(response => {
            console.log(response);
        });

        // clear page
        $('#booking-amount').val('');
        $('#datepicker').val('');
        const hourBlock = $('.hour-block');
        for (const block of hourBlock) {
            $(block).removeClass('book-selected')
            $(block).css("opacity", "1");
        }
        Swal.fire(
            '訂位完成',
        )
    })

})
