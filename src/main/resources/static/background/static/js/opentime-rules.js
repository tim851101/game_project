$(document).ready(() => {
    fetch('/rev/ls')
        .then(response => response.json())
        .then(openTimes => {

            for (const openTime of openTimes) {
                const inputs = $(`#week-${openTime.openTimeNo} input`);
                inputs.eq(0).val(openTime.openTimeStart.substring(0, 5));
                inputs.eq(1).val(openTime.openTimeEnd.substring(0, 5));
            }
        })
    $('#time-submit-button').click(() => {
        const data = [];
        for (let i = 1; i < 8; i++) {
            const inputs = $(`#week-${i} input`);
            data.push({
                "openTimeNo": i,
                "week": i,
                "openTimeStart": inputs.eq(0).val() + ':00',
                "openTimeEnd": inputs.eq(1).val() + ':00'
            })
        }
        console.log(data);
        fetch('/rev/save-all-open-hour',
            {
                method: 'POST',
                body: JSON.stringify(data),
                headers: {'Content-Type': 'application/json'},
            })
            .then(response => response.text())
            .then(ok => {
                if (ok){
                    swal.fire('儲存成功');
                }
            })
    })
});