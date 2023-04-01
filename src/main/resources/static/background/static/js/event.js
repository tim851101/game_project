$(document).ready(e => {
  // 舉辦日期參數 
  $.datetimepicker.setLocale('zh'); // kr ko ja en
  $('#eventDate').datetimepicker({
    theme: '',          //theme: 'dark',
    timepicker: false,   //timepicker: false,
    format: 'Y-m-d',
    defaultDate: '-1969-12-17', //disable 15天後才能選擇天數
    minDate: '-1969-12-17' //選擇時預設值在15天後
  });

  // 報名開始時間 disable當天，選擇隔天
  $.datetimepicker.setLocale('zh');
  $('#signupStartTime').datetimepicker({
    theme: '',
    timepicker: true,
    step: 30,
    format: 'Y-m-d H:i',
    defaultTime: '09:00',
    minDate: '-1969-12-31',
    defaultDate: '-1969-12-31',
  });

  //  報名截止時間  disable隔天，選擇後天
  $.datetimepicker.setLocale('zh'); // kr ko ja en
  $('#signupDeadline').datetimepicker({
    theme: '',          //theme: 'dark',
    timepicker: true,   //timepicker: false,
    step: 30,
    format: 'Y-m-d H:i',
    minDate: '-1969-12-30',
    defaultTime: '09:00',
    defaultDate: '-1969-12-30'
  });

  // 活動開始時間 
  $.datetimepicker.setLocale('zh');
  $('#eventStarttime').datetimepicker({
    datepicker: false,
    format: 'H:i',
    defaultTime: '09:00'
  });

  // 活動結束時間
  $.datetimepicker.setLocale('zh');
  $('#eventEndtime').datetimepicker({
    datepicker: false,
    format: 'H:i',
    defaultTime: '09:00'
  });

  // 新增資料至event table
  $('#event-form').submit(e => {
  // $('#enterBtn').click(e => {
    const formData = {
      'eventName': $("#eventName").val(),
      'eventDisc': $("#eventDisc").val(),
      'eventDate': $("#eventDate").val(),
      'eventStarttime': $('#eventStarttime').val(),
      'eventEndtime': $('#eventEndtime').val(),
      'eventLimit': $("#eventLimit").val(),
      'eventFee': $("#eventFee").val(),
      'signupStartTime': $("#signupStartTime").val(),
      'signupDeadline': $("#signupDeadline").val(),
      'eventStatus':0
    }
    fetch('/event/save-event', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(formData)
    })
      .then(response => {
        if (!response.ok) {
          throw new Error('Event not saved');
        }
        return response.json();
      })
      .then(data => {
        if (data) {
          setTimeout(() => {
            document.location.reload();
          }, 1000);
        }
        console.log(data);
      })
      .catch(error => {
        console.error('Error:', error.message);
      });
  });

  // 動態新增賽事清單(取出event table值) 
  fetch('/event/ls-event')
    .then(response => {
      if (!response.ok) {
        console.log("event no save");
      }
      return response.json();
    })
    .then(data => {
      findAll(data);
      $('#myTable').DataTable({
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
            "next": "下頁",
            "previous": "前頁"
          },
          "aria": {
            "sortAscending": ": 升冪",
            "sortDescending": ": 降冪",
          },
        }
      });
    });
  function findAll(data) {
    const eventList = $('#prod-list');
    let tdString = "";
    for (const e of data) {
      if (e.eventStatus == 1) {
        tdString += `
                      <tr> 
                        <th style="text-align:center";><strong></strong>${e.eventName}</th>
                        <th><textarea cols="30" rows="1" disabled >${e.eventDisc}</textarea></th>
                        <th><strong>${e.eventDate}</strong></th>
                        <th><strong>${e.eventStarttime}</strong></th>
                        <th><strong>${e.eventEndtime}</strong></th>
                        <th><strong>${e.eventLimit}</strong></th>
                        <th><strong>${e.eventFee}</strong></th>
                        <th><strong>${e.signupStartTime}</strong></th>
                        <th><strong>${e.signupDeadline}</strong></th>
                        <th><strong>${e.eventWinner1}</strong></th>
                        <th><strong>${e.eventWinner2}</strong></th>
                        <th><strong>${e.eventWinner3}</strong></th>
                        <th><i class="fa fa-circle text-success me-1"></i></th> 
                        <th><a id="edit${e.eventNo}" href="#" class="btn btn-primary shadow btn-xs sharp me-1 fas fa-pencil-alt" data-bs-toggle="modal" data-bs-target="#exampleModal" data-bs-whatever="@mdo""></th>
                     </tr>
        `;
      } else if (e.eventStatus == 0) {
        tdString += `
                        <tr> 
                          <th style="text-align:center";><strong></strong>${e.eventName}</th>
                          <th><textarea cols="30" rows="1" disabled >${e.eventDisc}</textarea></th>
                          <th><strong>${e.eventDate}</strong></th>
                          <th><strong>${e.eventStarttime}</strong></th>
                          <th><strong>${e.eventEndtime}</strong></th>
                          <th><strong>${e.eventLimit}</strong></th>
                          <th><strong>${e.eventFee}</strong></th>
                          <th><strong>${e.signupStartTime}</strong></th>
                          <th><strong>${e.signupDeadline}</strong></th>
                          <th><strong>${e.eventWinner1}</strong></th>
                          <th><strong>${e.eventWinner2}</strong></th>
                          <th><strong>${e.eventWinner3}</strong></th>
                          <th> <i class="fa fa-circle text-danger me-1"></i></th> 
                          <th><a id="edit${e.eventNo}" href="#" class="btn btn-primary shadow btn-xs sharp me-1 fas fa-pencil-alt" data-bs-toggle="modal" data-bs-target="#exampleModal" data-bs-whatever="@mdo""></th>
                    </tr>
          `;
      } 
      // else if (e.eventStatus == null) {
      //   tdString += `
      //                   <tr> 
      //                     <th style="text-align:center";><strong></strong>${e.eventName}</th>
      //                     <th><textarea cols="30" rows="1" disabled >${e.eventDisc}</textarea></th>
      //                     <th><strong>${e.eventDate}</strong></th>
      //                     <th><strong>${e.eventStarttime}</strong></th>
      //                     <th><strong>${e.eventEndtime}</strong></th>
      //                     <th><strong>${e.eventLimit}</strong></th>
      //                     <th><strong>${e.eventFee}</strong></th>
      //                     <th><strong>${e.signupStartTime}</strong></th>
      //                     <th><strong>${e.signupDeadline}</strong></th>
      //                     <th><strong>${e.eventWinner1}</strong></th>
      //                     <th><strong>${e.eventWinner2}</strong></th>
      //                     <th><strong>${e.eventWinner3}</strong></th>
      //                     <th><i class="fa fa-circle text-warning  me-1"></i></th> 
      //                    <th><a id="edit${e.eventNo}" href="#" class="btn btn-primary shadow btn-xs sharp me-1 fas fa-pencil-alt" data-bs-toggle="modal" data-bs-target="#exampleModal" data-bs-whatever="@mdo""></th>
      //               </tr>
      //                     `;
      // }
    }
    eventList.html(tdString);
    $('#prod-list').find('a[id^="edit"]').on('click', function alertTest() {
      $('#saveWinner').click(e => {
        if ($.trim($("#eventWinner1").val()) === "" || $.trim($("#eventWinner2").val()) === "" || $.trim($("#eventWinner3").val()) === "") {
          Swal.fire(
            "內容不能空白", //標題 
            "請再次檢查您輸入的字是否正確", //訊息內容(可省略)
            "error" 
          );
        } else {
          let buttonId = $(this).attr('id');
          let eventNo = buttonId.match(/edit(\d+)/)[1];
          const formData = {
            "eventNo": eventNo,
            "eventWinner1": $("#eventWinner1").val(),
            "eventWinner2": $("#eventWinner2").val(),
            "eventWinner3": $("#eventWinner3").val(),
            "eventStatus": 1
          }
          Swal.fire({
            title: "確定要送出資料嗎",
            text: "資料送出後將無法修改",
            showCancelButton: true
          }).then(function (result) {
            if (result.value) {
              Swal.fire("已送出資料");
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
                  setTimeout(() => {
                    document.location.reload();
                  }, 2000);
                  return response.json()
                })
                .catch(error => {
                  console.error('Error:', error.message);
                });
            }
          });
        }
      });
    });
  };
  let exampleModal = document.getElementById('exampleModal')
  exampleModal.addEventListener('show.bs.modal', function (event) {
    let button = event.relatedTarget
    let recipient = button.getAttribute('data-bs-whatever')
    let modalTitle = exampleModal.querySelector('.modal-title')
    let modalBodyInput = exampleModal.querySelector('.modal-body input')
    modalTitle.textContent = 'New message to ' + recipient
    modalBodyInput.value = recipient
  })
});

// 賽事資訊的欄寬隨字數自動增加
function autoResize(textarea) {
  textarea.style.height = 'auto';
  textarea.style.height = textarea.scrollHeight + 'px';
}
  // if ($.trim($("#eventWinner1").val()) === "" || $.trim($("#eventWinner2").val()) === "" || $.trim($("#eventWinner3").val()) === "") {
  //   Swal.fire(
  //     "內容不能空白", //標題 
  //     "請再次檢查您輸入的字是否正確", //訊息內容(可省略)
  //     "error" //圖示(可省略) success/info/warning/error/question
  //     //圖示範例：https://sweetalert2.github.io/#icons
  //   );



  /* <a href="#" class="btn btn-primary shadow btn-xs sharp me-1"><i class="fas fa-pencil-alt "></i> */

  //     <div class="d-flex align-items-center">
  //     <i class="fa fa-circle text-warning me-1"></i>
  //     進行中
  //   </div>
  //   <div class="d-flex align-items-center">
  //     <i class="fa fa-circle text-danger me-1"></i>
  //     未開始
  //   </div>
  //   <div class="d-flex align-items-center">
  //     <i class="fa fa-circle text-success me-1"></i>
  //     已完賽
  //   </div>
  // </td>-->


  //     <div class="d-flex">
  //       <!-- <a href="#" class="btn btn-danger shadow btn-xs sharp"><i class="fa fa-trash"></i></a> -->
  //     </div>
