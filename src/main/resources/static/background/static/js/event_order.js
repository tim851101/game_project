$(document).ready(e => {
  Promise.all([
    fetch('/event/ls-event').then(response => response.json()),
    fetch('/eventord/ls').then(response => response.json())
  ]).then(dataArray => {
    const eventData = dataArray[0];
    const eventordData = dataArray[1];
    PromiseData(eventData, eventordData);
    PromiseData2(eventData, eventordData);
  }).catch(error => {
    console.log('Error: ', error);
  });
  function PromiseData(eventData, eventordData) {
    let table = $("#ordList").DataTable({
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
      data: eventData,
      columns: [
        {
          className: "details-control",
          orderable: false,
          data: null,
          defaultContent: '<i class="material-icons">expand_more</i>'
        },
        { data: "eventNo", visible: false },
        { data: "eventName" },
        { data: "eventDate" },
        { data: "eventStarttime" },
        { data: "eventEndtime" },
        { data: "signupNum" },
        { data: "eventFee" },
        {
          data: "eventNo",
          render: (data, type, row, meta) => {
            if (row.eventStatus === 0) {
              return (
                `<td><button value=` + data + ` id="cancel" type="button" class="btn btn-danger btn-xxs" style="padding: 0.4rem 0.8rem;">取消賽事</button></td> `
              );
            } else {
              return (
                `<td><button value=` + data + ` id="cancel" type="button" class="btn  btn-success btn-xxs" style="padding: 0.4rem 0.8rem;" disabled>已完成</button></td> `
              );

            }
          }
        },

        { data: "eventStatus", orderable: false }
      ],
      order: [[1, "desc"]],
      rowCallback: function (row, data) {
        let $row = $(row);
        $row.attr('class', "eventNo." + data.eventNo);
        $row.attr('style', "text-align:center;");
      }
    });

    const rowsToRemove = $('#ordList tbody tr').filter(function () {
      return $('td:last', this).text() === '2';
    });


    // 從DataTable中刪除這些行
    $('#ordList').DataTable();
    table.rows(rowsToRemove).remove().draw();

    // 隱藏DataTable第10欄位
    table.columns([9]).visible(false);

    $("#ordList tbody").on("click", "td.details-control", function () {
      let tr = $(this).closest("tr");
      let row = table.row(tr);

      if (row.child.isShown()) {
        row.child.hide();
        tr.removeClass("shown");
      } else {
        row.child(format(row.data(), eventordData), "p-0").show();
        tr.addClass("shown");
      }
    });

  }

  function PromiseData2(eventData, eventordData) {
    $("#ordList tbody").on("click", 'button.btn', function () {
      const eventNo = $(this).val();
      Swal.fire({
        title: '確定要取消賽事？',
        text: "此動作將無法復原！",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#21870D',
        cancelButtonColor: '#d33',
        confirmButtonText: '確定',
        cancelButtonText: "取消",
      }).then((result) => {
        if (result.isConfirmed) {

          // 會員訂單狀態取消
          for (const e of eventordData) {
            if (eventNo == e.eventno) {
              $.post("/eventord/updatepay", {
                eventno: eventNo,
                memno: e.memNo,
                check: 1
              });
            }
          };
          const formData = {
            "eventNo": eventNo,
            "eventLimit": 0,
            "signupNum": 0,
            "eventStatus": 2
          }

          // 賽事狀態取消
          fetch('/event/setStatus', {
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
            title: '賽事已取消!',
            icon: 'success',
            confirmButtonText: '確定',
            confirmButtonColor: '#21870D',
            preConfirm: setTimeout(() => {
              document.location.reload();
            }, 2000)
          });
        }
      });
    });
  }

  function format(d, eventordData) {
    let trString = "";
    trString +=
      ` <thead>
         <tr>
              <th id="${d.eventNo}"class="align-middle" style="font-size: 10px;">會員姓名</th>
              <th class="align-middle" style="font-size: 10px;">會員信箱</th>
              <th class="align-middle" style="font-size: 10px;">會員電話</th>
              <th class="align-middle" style="font-size: 10px;">報名日期</th>
              <th class="align-middle" style="font-size: 10px;">訂單狀態</th>
              <th class="align-middle" style="font-size: 10px;">報到狀態</th>
              <th class="no-sort"></th>
          </tr>
          </thead>`
    for (const e of eventordData) {
      if (d.eventNo === e.eventno) {
        if (e.enevtStatus !== 2) {
          trString += `
                  <tr id="eventordList" style="text-align:center;"> 
                        <th><strong>${e.memName}</strong></th>
                        <th ><strong >${e.memEmail}</strong></th>
                         <th><strong>${e.memPhone}</strong></th>
                         <th><strong>2022/04/04</strong></th>`
          switch (e.enevtStatus) {
            case 1: {
              if (d.eventStatus === 1) {
                trString +=
                  `<td ><button class="btn btn-primary badge  btn-xxs bg-primary" id="${d.eventNo}enevtStatus${e.memNo}" disabled ><span class="fa fa-check me-1" ></span>完成報名</button></td>`

                break;
              }
              trString +=
                `<td ><span class="badge btn btn-xxs bg-primary" onclick="enevtStatus(this)" id="${d.eventNo}enevtStatus${e.memNo}" ><span class="fa fa-check me-1"></span>完成報名</span></td>`
              break;
            }
            case 0: {
              if (d.eventStatus === 1) {
                trString +=
                  `<td ><button class="btn btn-secondary badge  btn-xxs bg-secondary" id="${d.eventNo}enevtStatus${e.memNo}" disabled ><span class="fa fa-ban me-1" ></span>未付款</button></td>`

                break;
              }
              trString +=
                `<td><span class="badge btn btn-xxs bg-secondary" onclick="enevtStatus(this)" id="${d.eventNo}enevtStatus${e.memNo}" ><span class="fa fa-ban me-1"></span>未付款</span></td>`
              break;
            }
            // default: {
            //   trString +=
            //     `<td><span class="badge btn btn-xxs bg-secondary" onclick="enevtStatus(this)" id="${d.eventNo}enevtStatus${e.memNo}" ><span class="fa fa-times"></span>取消訂單</span></td>`
            //   break;
            // }
          }
          if (e.memChecked === 1) {
            if (d.eventStatus === 1) {
              trString +=
                `<td ><button class="btn btn-primary badge  btn-xxs bg-primary" id="${d.eventNo}memChecked${e.memNo}" disabled ><span class="fa fa-check me-1" ></span>已報到</button></td>`
            } else {
              trString +=
                `<td "><span class="badge btn btn-xxs bg-primary" onclick="memChecked(this)" id="${d.eventNo}memChecked${e.memNo}" ><span class="fa fa-check me-1"></span>已報到</span></td>`
            }
          } else {
            if (d.eventStatus === 1) {
              trString +=
                `<td ><button class="btn btn-secondary badge  btn-xxs bg-secondary" id="${d.eventNo}memChecked${e.memNo}" disabled ><span class="fa fa-ban me-1" ></span>未報到</button></td>`
            } else {
              trString +=
                `<td "><span class="badge btn btn-xxs bg-secondary" onclick="memChecked(this)" id="${d.eventNo}memChecked${e.memNo}" ><span class="fa fa-ban me-1"></span>未報到</span></td>`
            }
          }
          trString += `</tr>`

        };
      };
    }
    return trString;
  };
});


// 付款按鈕
function enevtStatus(badge) {
  const buttonId = badge.getAttribute("id");
  let memNo = buttonId.match(/enevtStatus(\d+)/)[1];
  let eventNo = buttonId.match(/(\d+)enevtStatus/)[1];
  if (badge.classList.contains("bg-primary")) {
    badge.classList.remove("bg-primary");
    badge.classList.add("bg-secondary");
    badge.innerHTML = "<span class='fa fa-times'></span>取消訂單";
    $.post("/eventord/updatepay", {
      eventno: eventNo,
      memno: memNo,
      check: 1
    });
    const cancelData={
      'eventNo':eventNo
    }
    fetch('/event/delSignupNum', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(cancelData)
    })
      .then(response => {
        if (!response.ok) {
          throw new Error('signupNum is cancel');
        }
        return response.json();
      })

  } else {
    badge.classList.remove("bg-secondary");
    badge.classList.add("bg-primary");
    badge.innerHTML = "<span class='fa fa-check me-1'></span>完成報名";
    $.post("/eventord/updatepay", {
      eventno: eventNo,
      memno: memNo,
      check: 0
    });
  }
  // 取得按鈕的 ID
  // const buttonId = badge.getAttribute("id");
  // 顯示抓出的 ID
  // alert(buttonId);
}

// 報到按鈕
function memChecked(badge) {
  const buttonId = badge.getAttribute("id");
  let memNo = buttonId.match(/memChecked(\d+)/)[1];
  let eventNo = buttonId.match(/(\d+)memChecked/)[1];
  if (badge.classList.contains("bg-primary")) {
    badge.classList.remove("bg-primary");
    badge.classList.add("bg-secondary");
    badge.innerHTML = "<span class='fa fa-ban me-1'></span>未報到";
    $.post("/eventord/updatevent", {
      eventno: eventNo,
      memno: memNo,
      check: 1
    });
  } else {
    badge.classList.remove("bg-secondary");
    badge.classList.add("bg-primary");
    badge.innerHTML = "<span class='fa fa-check me-1'></span>已報到";
    $.post("/eventord/updatevent", {
      eventno: eventNo,
      memno: memNo,
      check: 0
    });
  }
  // 取得按鈕的 ID
  // const buttonId = badge.getAttribute("id");
  // 顯示抓出的 ID
  // alert(buttonId);
}


{/* <tr style="text-align:center;">
<td>${e.eventno}</td>
<td>風聲</td>
<td class="py-2">${e.memName}</td>
<td class="py-2">${e.memEmail}</td>
<td class="py-2">${e.memPhone}</td>
<td class="py-2">2023/04/02</td>
<td><span class="badge btn btn-xxs bg-secondary"
  onclick="checkIn2(this)" id="edit${e.memNo}1" value=""><span class="fa fa-ban me-1"></span>
  未付款
</span></td>
<td class="py-2 text-end"><span class="badge btn btn-xxs bg-secondary"
  onclick="checkIn(this)" id="edit${e.memNo}2" value=""> <span class="fa fa-ban me-1"></span>
  未報到
</span></td>
<td class="py-2">0</td>
<td class="py-2 text-end">
<div class="dropdown text-sans-serif"><button
      class="btn btn-primary tp-btn-light sharp" type="button"
      id="order-dropdown-0" data-bs-toggle="dropdown"
      data-boundary="viewport" aria-haspopup="true"
      aria-expanded="false"><span><svg
              xmlns="http://www.w3.org/2000/svg"
              xmlns:xlink="http://www.w3.org/1999/xlink"
              width="18px" height="18px" viewbox="0 0 24 24"
              version="1.1">
              <g stroke="none" stroke-width="1" fill="none"
                  fill-rule="evenodd">
                  <rect x="0" y="0" width="24" height="24">
                  </rect>
                  <circle fill="#000000" cx="5" cy="12" r="2">
                  </circle>
                  <circle fill="#000000" cx="12" cy="12" r="2">
                  </circle>
                  <circle fill="#000000" cx="19" cy="12" r="2">
                  </circle>
              </g>
          </svg></span></button>
<div class="dropdown-menu dropdown-menu-end border py-0"
      aria-labelledby="order-dropdown-0">
<div class="py-2"><a class="dropdown-item"
              href="javascript:void(0);">未出貨</a><a
              class="dropdown-item"
              href="javascript:void(0);">已出貨</a><a
              class="dropdown-item"
              href="javascript:void(0);">已完成</a>
<div class="dropdown-divider"></div><a
              class="dropdown-item text-danger"
              href="javascript:void(0);">取消</a>
</div>
</div>
</div>
</td>
</tr> */}