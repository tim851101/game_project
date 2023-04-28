let eventDate = 0; 
let eventStarttime = 0; 
let eventEndtime = 0; 

$(document).ready(e => {
  // document.getElementById("eventName").focus();
  //  舉辦日期參數 
  $.datetimepicker.setLocale('zh'); // kr ko ja en
  $('#eventDate').datetimepicker({
    theme: '',          //theme: 'dark',
    timepicker: false,   //timepicker: false,
    format: 'Y-m-d',
    defaultDate: '-1969-12-22', //disable 10天後才能選擇天數
    minDate: '-1969-12-22' //選擇時預設值在10天後,
  });

  // 報名開始時間 disable當天，選擇隔天
  $.datetimepicker.setLocale('zh');
  $('#signupStartTime').datetimepicker({
    theme: '',
    timepicker: true,
    step: 60,
    format: 'Y-m-d H:i',
    defaultTime: '08:00',
    minDate: '-1969-12-31',
    defaultDate: '-1969-12-31',
    allowTimes: [
      '07:00','08:00','09:00', '10:00'
     ]
  });


  //  報名截止時間  disable隔天，選擇後天
  $.datetimepicker.setLocale('zh'); // kr ko ja en
  $('#signupDeadline').datetimepicker({
    theme: '',          //theme: 'dark',
    timepicker: true,   //timepicker: false,
    step: 60,
    format: 'Y-m-d H:i',
    minDate: '-1969-12-30',
    defaultTime: '08:00',
    defaultDate: '-1969-12-30',
    allowTimes: [
       '20:00','21:00', '22:00', '23:00'
     ]
  }
  );

  $('#eventStarttime').change(()=>{ 
    eventStarttime = $('#eventStarttime').val().substring(0,2)
  })
  
  $('#eventEndtime').change(()=>{ 
    eventEndtime = $('#eventEndtime').val().substring(0,2)
  })
  
  $('#eventDate').change(()=>{ 
    eventDate = $('#eventDate').val()
  })

// 活動開始時間 
$('#eventStarttime').datetimepicker({
  datepicker: false,
  format: 'H:i', 
  minDate: 0,
  allowTimes: [
   '09:00', '10:00', '11:00', '12:00',
   '13:00', '14:00', '15:00', '16:00'
    
  ]
});
 // 活動結束時間
$('#eventEndtime').datetimepicker({
  datepicker: false,
  format: 'H:i',
  minDate: 0, 
  allowTimes: [
    
    '13:00', '14:00', '15:00', '16:00',
    '17:00', '18:00', '19:00', '20:00',
    '21:00', '22:00' , '23:00'
  ]
});

// redis座位人數是否上限
function eventLimit(){
  const eventLimitError = document.getElementById("eventLimitError");
  const enterBtn = $("#enterBtn");
  datas={
    date: eventDate,
    minTime: eventStarttime,
    maxTime: eventEndtime
  } 
  $.ajax({url:"/book/testseat", 
  type:'post',
  data:datas,
 success : function(response){ 
  if(response != 60){
    eventLimitError.textContent = "該時間無法選擇";
    enterBtn.attr('disabled', true);
        return false;
  }else{
    eventLimitError.textContent = "";
    enterBtn.attr('disabled', false);
    return true;
  }
}});
  }

$("#eventEndtime").on('blur', e=>{
  eventLimit();
});


// 新增資料至event table
$('#enterBtn').click(e => {
// $('#event-form').submit(e => {
  const formData = {
    'eventName': $("#eventName").val(),
    'eventDisc': $("#eventDisc").val(),
    'eventDate': $("#eventDate").val(),
    'eventStarttime': $('#eventStarttime').val(),
    'eventEndtime': $('#eventEndtime').val(),
    'eventLimit': $("#eventLimit").val(),
    'signupNum':0,
    'eventFee': $("#eventFee").val(),
    'signupStartTime': $("#signupStartTime").val(),
    'signupDeadline': $("#signupDeadline").val(),
    'eventStatus': ""
  };

  // 驗證表單資料
  fetch('/event/save-event', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(formData)
  })
  .then(response => {
    // 檢查是否有錯誤訊息
    return response.json().then(errors => {
      if (Object.keys(errors).length === 0) {
        $.post("/book/saveseat4time", {
          date: eventDate,
          minTime: eventStarttime,
          maxTime: eventEndtime,
          change:60
        });

        // 如果沒有錯誤訊息，顯示提示視窗
       Swal.fire({
        title: "已送出資料！",
        icon: 'success',
        confirmButtonText: '確定',
        confirmButtonColor: '#21870D',
        preConfirm: setTimeout(() => {
            document.location.reload();
          }, 2000)
      })
      } else {
        // 如果有錯誤訊息，顯示錯誤訊息
        if (errors.eventName) {
          $("#eventNameError").text(`${errors.eventName}`);
        } else {
          $("#eventNameError").text("");
        }
        if (errors.eventDisc) {
          $("#eventDiscError").text(`${errors.eventDisc}`);
        } else {
          $("#eventDiscError").text("");
        }
        if (errors.eventLimit) {
          $("#eventLimitError").text(`${errors.eventLimit}`);
        } else {
          $("#eventLimitError").text("");
        }
        if (errors.eventFee) {
          $("#eventFeeError").text(`${errors.eventFee}`);
        } else {
          $("#eventFeeError").text("");
        }
        eventDate1();
        signupDeadline();
        eventStarttime1();
        eventEndtime1();
        signupStartTime();
      }
    })
  });
  });

// 錯誤顯示
  $("#eventName").on('blur',e =>{
    eventName();
  });
  $("#eventDisc").on('blur',e =>{
    eventDisc();
  });
  $("#eventLimit").on('blur',e =>{
    eventLimit1();
  });
  $("#eventFee").on('blur',e =>{
    eventFee();
  });
  $("#signupDeadline").on('blur',e =>{
    signupDeadline();
  });
  $("#signupStartTime").on('blur',e =>{
    signupStartTime();
  });
  $("#eventDate").on('blur',e =>{
    eventDate1();
  });
  $("#eventStarttime").on('blur',e =>{
    eventStarttime1();
  });
  $("#eventEndtime").on('blur',e =>{
    eventEndtime1();
  });

  function eventName(){
    const eventName = document.getElementById("eventName");
    const eventNameError = document.getElementById("eventNameError");
    if (eventName.value.trim() === "") {
      eventNameError.textContent = "賽事名稱未填寫";
        return false;
    } else {
      eventNameError.textContent = "";
        return true;
    }
  }
  function eventDisc(){
    const eventDisc = document.getElementById("eventDisc");
    const eventDiscError = document.getElementById("eventDiscError");
    if (eventDisc.value.trim() === "") {
      eventDiscError.textContent = "賽事資訊未填寫";
        return false;
    } else {
      eventDiscError.textContent = "";
        return true;
    }
  }
  function eventLimit1(){
    const eventLimit = document.getElementById("eventLimit");
    const eventLimitError = document.getElementById("eventLimitError");
    if (eventLimit.value.trim() === "") {
      eventLimitError.textContent = "最少需要30人、最大是60人";
        return false;
    } else {
      eventLimitError.textContent = "";
        return true;
    }
  }
  function eventFee(){
    const eventFee = document.getElementById("eventFee");
    const eventFeeError = document.getElementById("eventFeeError");
    if (eventFee.value.trim() === "") {
      eventFeeError.textContent = "參加費用未填寫，最少需要200元";
        return false;
    } else {
      eventFeeError.textContent = "";
        return true;
    }
  }

function eventDate1(){
  const eventDate = document.getElementById("eventDate");
  const eventDateError = document.getElementById("eventDateError");
  if (eventDate.value.trim() === "") {
    eventDateError.textContent = "未填寫";
      return false;
  }  else {
    eventDateError.textContent = "";
      return true;
  }
}

function eventStarttime1(){
  const eventStarttime = document.getElementById("eventStarttime");
  const eventStarttimeError = document.getElementById("eventStarttimeError");
  const timeRegex = /^([01][0-9]|2[0-3]):00$/;
  if (eventStarttime.value.trim() === "") {
    eventStarttimeError.textContent = "未填寫";
      return false;
  } else if (!timeRegex.test(eventStarttime.value.trim())) {
    eventStarttimeError.textContent = "只能是\"XX:00\"格式";
      return false;
  } else {
    eventStarttimeError.textContent = "";
      return true;
  }
}

function eventEndtime1(){
  const eventEndtime = document.getElementById("eventEndtime");
  const eventEndtimeError = document.getElementById("eventEndtimeError");
  const timeRegex = /^([01][0-9]|2[0-3]):00$/;
  if (eventEndtime.value.trim() === "") {
    eventEndtimeError.textContent = "未填寫";
      return false;
    } else if (!timeRegex.test(eventEndtime.value.trim())) {
      eventEndtimeError.textContent = "只能是\"XX:00\"格式";
        return false;
  } else {
    eventEndtimeError.textContent = "";
      return true;
  }
}

  function signupStartTime() {
    const signupStartTime = document.getElementById("signupStartTime");
    const signupStartTimeError = document.getElementById("signupStartTimeError");
    if (signupStartTime.value.trim() === "") {
      signupStartTimeError.textContent = "未填寫";
        return false;
    }  
    else {
      signupStartTimeError.textContent = "";
        return true;
    }
  }

  function signupDeadline() {
    const signupDeadline = document.getElementById("signupDeadline");
    const signupDeadlineError = document.getElementById("signupDeadlineError");
    if (signupDeadline.value.trim() === "") {
      signupDeadlineError.textContent = "未填寫";
        return false;
    } 
    else {
      signupDeadlineError.textContent = "";
        return true;
    }
}

  // 動態新增賽事清單(取出event table值) 
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
   
  // DataTable樣式
function findAll(data){
  let table = $("#eventList").DataTable({
    autoWidth: true,
    // stateSave: true,
    "lengthMenu": [[10, 20, 30, -1], [10, 20, 30, "全部"]],
    language: {
      "emptyTable": "無資料...",
      "processing": "處理中...",
      "loadingRecords": "載入中...",
      "lengthMenu": "每頁 _MENU_ 筆資料",
      "zeroRecords": "無搜尋結果",
      "info": "_START_ 至 _END_ / 共 _TOTAL_ 筆",
      "infoEmpty": "尚無資料",
      "infoFiltered": "(從 _MAX_ 筆資料過濾)",
      "infoPostFix": "",
      "search": "搜尋字串:",
      "paginate": {
        "first": "首頁",
        "last": "末頁",
        "next": "＞",
        "previous": "＜"
      },
      "aria": {
        "sortAscending": ": 升冪",
        "sortDescending": ": 降冪",
      },
    },
    data: data,
    columns: [
      {
        className: "details-control",
        orderable: false,
        data: null,
        defaultContent: '<i class="material-icons"">expand_more</i>'
      },
      { data: "eventName" },
      { data: null, 
        render: (row) => {
          return  `<th><textarea cols="10" rows="2" disabled >${row.eventDisc}</textarea></th>`;
        } },
       
      
      {    data: null,
        render: (row) => {
          return `<th style="padding: 0px;">${row.eventDate}</th><br>` + `<th>${row.eventStarttime}-${row.eventEndtime}</th>`;
        } },
       {data: null,
        render: (row) => {
        return `<th>${row.signupStartTime}</th><br>` + `<th>${row.signupDeadline}</th>`;
      } },
      { data: null  ,render: (row) => {
        return `<th>${row.eventLimit}</th`;
      }},
      { data: "eventFee" },
      { data: null,render: (row) => {
        switch (row.eventStatus) {
                                    case 1: {
                                     return  `<th><i class="fa fa-circle text-success me-1"> </i></th>` 
                                    }
                                    case 2: {
                                      return `<th><i class="fa fa-circle text-danger me-1"></i></th>` 
                                    }
                                    default: {
                                      return `<th><i class="fa fa-circle text-warning me-1"> </i></th>` 
                                    }
                                  }
      }
    },
      { data: "eventNo"}
    ],
    order: [[8, "desc"]],
    rowCallback: function (row, data) {
      let $row = $(row);
      $row.attr('style', "text-align:center;");
    }
  });
  table.columns([8]).visible(false);
  $("#eventList tbody").on("click", "td.details-control", function () {
    let tr = $(this).closest("tr");
    let row = table.row(tr);
    
    if (row.child.isShown()) {
      row.child.hide();
      tr.removeClass("shown");
    } else {
      row.child(format(row.data()), "p-0").show();
      tr.addClass("shown");
    }
  });
}

  // 新增冠亞季軍欄位
  function format(d) {
    let trString = "";
    if (d.eventStatus === 1) {
      trString +=
        `<thead >
           <tr>
           <th  style="padding: 10px 18px;"></th>
           <th  style="padding: 10px 18px;"></th>
           <th><strong>冠軍</strong></th>
           <th></th>
           <th><strong>亞軍</strong></th>
           <th></th>
           <th><strong>季軍</strong></th>
           <th></th>
           </tr>
         </thead>
         <tr >
         <th  style="padding: 10px 18px;"></th>
         <th  style="padding: 10px 18px;"></th>
           <th><strong>${d.eventWinner1}</strong></th>
           <th></th>
           <th><strong>${d.eventWinner2}</strong></th>
           <th></th>
           <th><strong>${d.eventWinner3}</strong></th>
         </tr>`;
    } else if(d.eventStatus === 0 || d.eventStatus === 3){
      trString +=
        `<thead >
           <tr>
           <th  style="padding: 10px 18px;"></th>
           <th  style="padding: 10px 18px;"></th>
             <th><strong>冠軍</strong></th>
             <th></th>
             <th><strong>亞軍</strong></th>
             <th></th>
             <th><strong>季軍</strong></th>
             <th></th>
             <th><strong>修改賽事</strong></th>
           </tr>
         </thead>
         <tr >
         <th  style="padding: 10px 18px;"></th>
         <th  style="padding: 10px 18px;"></th>
           <th><strong>無資料</strong></th>
           <th></th>
           <th><strong>無資料</strong></th>
           <th></th>
           <th><strong>無資料</strong></th>
           <th></th>
           <th ><a id="edit${d.eventNo}" href="#" class="btn btn-primary shadow btn-xs sharp me-1 fas fa-pencil-alt" data-bs-toggle="modal" data-bs-target="#exampleModal" data-bs-whatever="@mdo"  onClick="alertTest(this);"></a></th>
         </tr>`;
    }else{
      trString +=
      `<thead >
         <tr>
         <th  style="padding: 10px 18px;"></th>
         <th  style="padding: 10px 18px;"></th>
           <th><strong>冠軍</strong></th>
           <th></th>
           <th><strong>亞軍</strong></th>
           <th></th>
           <th><strong>季軍</strong></th>
           <th></th>
         </tr>
       </thead>
       <tr >
       <th  style="padding: 10px 18px;"></th>
       <th  style="padding: 10px 18px;"></th>
         <th><strong>無資料</strong></th>
         <th></th>
         <th><strong>無資料</strong></th>
         <th></th>
         <th><strong>無資料</strong></th>
         <th></th>
       </tr>`;
    }
    return trString;
  };
});

  // 修改冠亞季軍
function alertTest(data){
  $('#saveWinner').click(e => {
    if ($.trim($("#eventWinner1").val()) === "" || $.trim($("#eventWinner2").val()) === "" || $.trim($("#eventWinner3").val()) === "") {
      Swal.fire(
        {
          title: "內容不能空白！",
          text:  "請再次檢查您輸入的字是否正確",
          icon: 'error',
          confirmButtonText: '確定'
        }
      );
    } else {
      let buttonId = $(data).attr('id');
      let eventNo = buttonId.match(/edit(\d+)/)[1];
      const formData = {
        "eventNo": eventNo,
        "eventWinner1": $("#eventWinner1").val(),
        "eventWinner2": $("#eventWinner2").val(),
        "eventWinner3": $("#eventWinner3").val(),
        "eventStatus": 1
      }
      Swal.fire({
        title: "確定要送出資料嗎？",
        text: "資料送出後將無法修改！",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#21870D',
    cancelButtonColor: '#d33',
        confirmButtonText: '確定',
        cancelButtonText: "取消",
        reverseButtons: true
      }).then(function (result) {
        if (result.value) {
          fetch('/event/updateWinners', {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData)
          })
            .then(response => {
              if (!response.ok) {
                throw new Error('Event not update');
              }
              return response.json()
            })
            .catch(error => {
              console.error('Error:', error.message);
            });
            Swal.fire({
              title: "已送出資料！",
              icon: 'success',
              confirmButtonText: '確定',
              confirmButtonColor: '#21870D',
              preConfirm: setTimeout(() => {
                document.location.reload();
              }, 2000)
            })
        }
      });
    }

  });
}
// 賽事資訊的欄寬隨字數自動增加
function autoResize(textarea) {
textarea.style.height = 'auto';
textarea.style.height = textarea.scrollHeight + 'px';
}


