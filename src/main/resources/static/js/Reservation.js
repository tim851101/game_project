/**
 * Post
 */
$('#sent-post').click(e => {
    e.preventDefault();
    console.log(e.target);
    fetch(`/rev/insert-all`, {
        method: 'POST',
        body: JSON.stringify([{
            "week": 1,
            "openTimeStart": "09:00:00",
            "openTimeEnd": "18:00:00"
        }]),
        headers: {'Content-Type': 'application/json'},
    }).then(response => {
        console.log(response);
    });
});


const week = $('#week');
const TimeStart = $('#start');
const TimeEnd = $('#end');

$('form').submit(e => {
    e.preventDefault();

    const jsonData = JSON.stringify(
        [{
            "week": +$('#week').val(),
            "openTimeStart": $('#start').val(),
            "openTimeEnd": $('#end').val()
        }]
    );

    console.log('json', jsonData);
    console.log(JSON.stringify([{
        "week": 1,
        "openTimeStart": "09:00:00",
        "openTimeEnd": "18:00:00"
    }]))

    fetch(e.target.action, {
        method: 'POST',
        body: jsonData,
        headers: {'Content-Type': 'application/json'},
    })
});
