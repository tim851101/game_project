$(document).ready(e => {
  //  舉辦日期參數 
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
  // $('#enterBtn').click(e => {
  $('#event-form').submit(e => {
    e.preventDefault();
  Swal.fire({
    title: "確定要新增該賽事嗎？",
    text: "資料送出後將無法修改！",
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#21870D',
cancelButtonColor: '#d33',
    confirmButtonText: '確定',
    cancelButtonText: "取消"
  })
  .then(function (result) {
    if (result.value) {
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
      'eventStatus': 0
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

    }} );
  });


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
           <tr ">
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
    } else if(d.eventStatus === 0){
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
           <th><a id="edit${d.eventNo}" href="#" class="btn btn-primary shadow btn-xs sharp me-1 fas fa-pencil-alt" data-bs-toggle="modal" data-bs-target="#exampleModal" data-bs-whatever="@mdo"  onClick="alertTest(this);"></a></th>
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
        cancelButtonText: "取消"
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
            });
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
