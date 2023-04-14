// var selectedDates = [];
var selectedDates = new Set();

function fullCalender() {

    /* initialize the external events
        -----------------------------------------------------------------*/

    var containerEl = document.getElementById('external-events');
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
    var calendarEl = document.getElementById('calendar');
    var calendar = new FullCalendar.Calendar(calendarEl, {
        /**
         *  header
         */
        headerToolbar: {
            left: 'prev,next today',
            center: 'title',
            right: 'space,dayGridMonth,space'
        },
        /**
         *  selectable
         */
        selectable: true,
        selectMirror: true,
        select: function (arg) {
            Swal.fire({
                input: 'text',
                showCancelButton: true,
                confirmButtonText: 'Add',
                showLoaderOnConfirm: true,
                preConfirm: (title) => {
                    calendar.addEvent({
                        title: title,
                        start: arg.start,
                        end: arg.end,
                        allDay: arg.allDay
                    })
                    calendar.unselect()
                    selectedDates.add(moment(arg.start).format('YYYY/MM/DD'));
                },
                allowOutsideClick: () => !Swal.isLoading()
            })
            // Add selected date to array
        },

        /**
         *  droppable from external
         */
        droppable: true,
        drop: args => {
            console.log("drop", moment(args.date).format('YYYY/MM/DD')); // TODO:
            selectedDates.add(moment(args.start).format('YYYY/MM/DD'));
        },
        // make if dragable and resizable
        editable: true,
        /**
         *  droppable from calender
         */
        eventDrop: args => {
            console.log("Event Drop", moment(args.date).format('YYYY/MM/DD')); // TODO:

            // Remove old date from set
            const oldDate = moment(args.oldEvent.start).format('YYYY/MM/DD');
            selectedDates.delete(oldDate);
            // Add new date to set
            const newDate = moment(args.event.start).format('YYYY/MM/DD');
            selectedDates.add(newDate);
        },

        /**
         *  eventResize
         */
        eventResize: args => {
            const event = args.event;
            for (var m = moment(event.start); m.isBefore(event.end); m.add(1, 'days')) {
                selectedDates.add(m.format('YYYY/MM/DD'));
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
        events: [
            {
                title: 'test',
                start: moment().format('YYYY-MM-DD'),
                className: "bg-warning"
            },
            {
                title: 'test-range',
                start: moment("2023/03/27").format('YYYY-MM-DD'),
                end: moment("2023/03/29").format('YYYY-MM-DD'),
                className: "bg-secondary"
            }
        ]
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
    console.log(selectedDates)
});
