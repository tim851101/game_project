// let selectedDates = [];
let selectedDates = new Set();
let selectedMap = new Map([
    ['國定假日', new Set()],
    ['店家特休', new Set()]
]);

function fullCalender() {

    /* initialize the external events
        -----------------------------------------------------------------*/

    let containerEl = document.getElementById('external-events');
    new FullCalendar.Draggable(containerEl, {
        itemSelector: '.external-event',
        eventData: function (eventEl) {
            return {
                title: eventEl.innerText.trim(),
                backgroundColor: window.getComputedStyle(eventEl).backgroundColor,
                borderColor: window.getComputedStyle(eventEl).borderColor
            }
        },
    });

    /* initialize the calendar
    -----------------------------------------------------------------*/
    let calendarEl = document.getElementById('calendar');
    let calendar = new FullCalendar.Calendar(calendarEl, {
        /**
         *  header
         */
        headerToolbar: {
            left: 'prev,next today',
            center: 'title',
            right: 'space,dayGridMonth,space'
        },
        /**
         *  droppable from external
         */
        droppable: true,
        drop: args => {
            const droppedValue = args.draggedEl.innerText.trim();
            selectedDates.add(moment(args.date).format('YYYY/MM/DD'));
            selectedMap.get(droppedValue).add(moment(args.date).format('YYYY/MM/DD'));
        },
        // make if draggable and resizable
        editable: true,
        /**
         *  droppable from calender
         */
        eventDrop: args => {
            const eventText = args.event.title;
            if (!moment(args.oldEvent.end)._i) {
                let oldEvent = moment(args.oldEvent.start);
                let newEvent = moment(args.event.start);
                selectedDates.delete(oldEvent.format('YYYY/MM/DD'));
                selectedDates.add(newEvent.format('YYYY/MM/DD'));
                selectedMap.get(eventText).delete(oldEvent.format('YYYY/MM/DD'));
                selectedMap.get(eventText).add(newEvent.format('YYYY/MM/DD'));
            }

            for (let m = moment(args.oldEvent.start); m.isBefore(args.oldEvent.end); m = m.clone().add(1, 'days')) {
                selectedDates.delete(m.format('YYYY/MM/DD'));
                selectedMap.get(eventText).delete(m.format('YYYY/MM/DD'));

            }
            for (let newEvent = moment(args.event.start); newEvent.isBefore(args.event.end); newEvent = newEvent.clone().add(1, 'days')) {
                selectedDates.add(newEvent.format('YYYY/MM/DD'));
                selectedMap.get(eventText).add(newEvent.format('YYYY/MM/DD'));
            }
        },

        eventClick: function (info) {
            // Ask for confirmation before deleting the event
            Swal.fire({
                title: '確認要刪除嗎',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                cancelButtonText: '取消',
                confirmButtonText: '刪除'
            }).then((result) => {
                if (result.isConfirmed) {
                    // Remove the event from the calendar
                    info.event.remove();

                    // Remove the selected dates from the set
                    const start = moment(info.event.start).format('YYYY/MM/DD');
                    const end = moment(info.event.end).subtract(1, 'day').format('YYYY/MM/DD');
                    for (let m = moment(start); m.isSameOrBefore(end); m.add(1, 'days')) {
                        selectedDates.delete(m.format('YYYY/MM/DD'));
                    }
                }
            });
        },

        /**
         *  eventResize
         */
        eventResize: args => {
            const eventText = args.event.title;
            for (let m = moment(args.event.start); m.isBefore(args.event.end); m.add(1, 'days')) {
                selectedDates.add(m.format('YYYY/MM/DD'));
                selectedMap.get(eventText).add(m.format('YYYY/MM/DD'));
            }
        },
        /**
         *  display
         */
        weekNumbers: true,
        nowIndicator: true,

        /**
         *  initial calender
         */
        initialDate: moment().format('YYYY-MM-DD'),
        events: function (fetchInfo, successCallback, failureCallback) {
            // make AJAX call to API to fetch events data
            const loadEvents = new Array();
            fetch('/off-date/find-all', {
                method: 'POST',
                body: JSON.stringify([
                    {'id': '店家特休'},
                    {'id': '國定假日'}
                ]),
                headers: {'Content-Type': 'application/json'},
            })
                .then(response => response.json())
                .then(objs => {
                    for (const data of objs) {
                        for (const date of data.date) {
                            loadEvents.push({
                                title: data.id,
                                start: moment(date).format('YYYY-MM-DD'),
                                className: data.id=='國定假日'?"bg-primary":"bg-warning"
                            })
                            selectedDates.add(moment(date).format('YYYY-MM-DD'));
                            selectedMap.get(data.id).add(moment(date).format('YYYY-MM-DD'));
                        }
                    }
                    successCallback(loadEvents);
                })
                .catch(error => {
                    failureCallback(error);
                });
        }
    });
    calendar.render();
}

jQuery(window).on('load', function () {
    setTimeout(function () {
        fullCalender();
    }, 1000);
});

// for test purpose
document.getElementById('submit-button').addEventListener('click', function () {
    for (const date of selectedDates) {
        const formatDate = date.replaceAll('/', '-');
        fetch(`/seat/close`, {
            method: 'POST',
            body: JSON.stringify({
                'date': formatDate
            }),
            headers: {'Content-Type': 'application/json'},
        }).then(response => {
            Swal.fire(
                '儲存成功',
            )
        }).catch(
            Swal.fire(
                '儲存失敗',
            )
        );
    }

    const offDateList = [];
    for (let [key, value] of selectedMap.entries()) {
        offDateList.push({
            "id": key,
            "date": [...value]
        });
    }
    fetch(`/off-date/save-all`, {
        method: 'POST',
        body: JSON.stringify(offDateList),
        headers: {'Content-Type': 'application/json'},
    }).then(response => {

    }).catch(
    );
});
