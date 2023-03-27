<!-- Initialize the Datepicker -->
$(function () {
    const MonthFromNow = new Date();
    MonthFromNow.setMonth(MonthFromNow.getMonth() + 1);

    $('#datepicker').datepicker({
        dateFormat: 'yy-mm-dd',
        showOtherMonths: true,
        selectOtherMonths: true,
        beforeShowDay: function (date) {
            // Disable dates more than n months from now
            return [date >= new Date() && date <= MonthFromNow];
        }
    });
});
