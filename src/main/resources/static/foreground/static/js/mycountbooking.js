
     const tbody = document.querySelector('#booking');
      const update= document.querySelector('#update');
      const updatebook= document.querySelector('#updatebook')
     const url ='/book/bookingbymemno?memno=32';//目前寫死
      const MonthFromNow = new Date();
    MonthFromNow.setMonth(MonthFromNow.getMonth() + 1);
     let bookno=null;
     let str="";
     function read(){
        $('#booktable').DataTable().clear().destroy();
        tbody.innerHTML = ""
         fetch(url)
         .then(resp => resp.json())
         .then(list => {
             for (let booking of list) {
             if(booking.bookingFinishDate==null){
                 str="更動"
                deadlineStr="未完成"
                }
             else{
                 str="查詢"
                 deadlineStr=""+booking.bookingFinishDate
                }
                 tbody.innerHTML += `
                <tr>
                    <td>${booking.bookingDate}</td>
                   <td>${booking.bookingStartTime}</td>
                   <td>${booking.bookingEndTime}</td>
                   <td>`+deadlineStr+`</td>
                   <td>${booking.bookingTotalPrice}</td>
                   <td>${booking.bookingPeople}</td>
                    <td><button id="update" onclick="forUpdate(${booking.bookingNo})" class="btn obrien-button-2 primary-color rounded-0">`+str+`</button></td>
                    <td><button id="delete" onclick="forDelete(${booking.bookingNo})" class="btn obrien-button-2 primary-color rounded-0">取消</button></td>
                    </tr>
                    `
                }

            //data table start
            $(document).ready( function () {
            $('#booktable').DataTable({
                pageLength: 3,
                lengthMenu: [3,5,10,20],
                language: {
                    emptyTable: "無資料",
                    info: "顯示 _START_ 至 _END_ 筆資料，共 _TOTAL_ 筆",
                    lengthMenu: "顯示 _MENU_ 筆資料",
                    paginate: {
                        previous: '<i class="fa fa-chevron-left"></i>',
                        next: '<i class="fa fa-chevron-right"></i>'
                    },
                },
            });
         });
         //datatable END
            });

        }
        // let table = new DataTable('#booktable');
        read();
	function forDelete(deleteno){ let confirmdelete = confirm('確認取消'+deleteno+"號訂位?");
         if(confirmdelete){
          $.post('/book/deletebook',{
        deleteno:deleteno
});
}else{
}
sleep(500).then(() => {
   read();
})
	}
	let deadline=null
	function forUpdate(updateno){
	const url ='/book/one?findno='+updateno;
	fetch(url)
	.then(resp => resp.json())
	.then(list => {
            for (let booking of list) {
            bookno=booking.bookingNo
            memno=booking.memNo
            if(booking.bookingFinishDate==null){
             deadlineStr="<button id='toPay' class='btn obrien-button-2 primary-color rounded-0'>去付款</button>"
            deadline=null;
            update.innerHTML =``
                update.innerHTML += `
                <tr>
                    <td>${booking.bookingNo}</td>
                    <td><input type="text" id="srattime"onchange="checkredis()" value="09:00"></td>
                    <td><input type="text" id="endtime" onchange="checkredis()"value="09:00"></td>
                    <td><input type="text"  id="date"   onchange="checkredis()" value=""></td>
                    <td><p id='price'>${booking.bookingTotalPrice}</p></td>
                    <td><select id="people" onchange=pricechange() >
                    <option value="${booking.bookingPeople}">人數</option>
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                    <option value="6">6</option>
                    </select></td>
                     <td>`+deadlineStr+`</td>
                     <td><button id="post" onclick="change()" class="btn obrien-button-2 primary-color rounded-0">送出</button></td>
                </tr>
            `
            }
             else{
             deadlineStr=""+booking.bookingFinishDate
             deadline=booking.bookingFinishDate
             update.innerHTML =``
                update.innerHTML += `
                <tr>
                    <td>${booking.bookingNo}</td>
                    <td>${booking.bookingStartTime}</td>
                    <td>${booking.bookingEndTime}</td>
                    <td>${booking.bookingDate}</td>
                    <td>${booking.bookingTotalPrice}</p></td>
                    <td>${booking.bookingPeople}</td>
                     <td>`+deadlineStr+`</td>
                    <td><button id="post" onclick="change()" class="btn obrien-button-2 primary-color rounded-0">聯絡我們</button></td>
                </tr>
            `
                }
            }
            update.addEventListener('change',()=>{
                let check=0;
                a=document.getElementById('srattime').value.substr(0, 2)
                b=document.getElementById('endtime').value.substr(0, 2)
                datas={
                    date:document.getElementById('date').value,
                    minTime:a,
                    maxTime:b
                }
                console.log(datas);
                $.ajax({
                url : '/book/testseat',
                type : 'POST',
                data : datas,
                success : function(response) {
                check=(response-(document.getElementById('people').value))
                console.log(check);
                if(deadline!=null)
               {
               document.getElementById('post').setAttribute('style',"display:none")
               }
                if(check>0)
                    {
             //       console.log("還有位置");
                    document.getElementById('post').removeAttribute('disabled')
                    document.getElementById('toPay').removeAttribute('disabled')
                    }
                    else
                    {
               //     console.log("沒有");
                    document.getElementById('post').setAttribute('disabled',true)
                    document.getElementById('toPay').setAttribute('disabled',true)
                    }
                 },

            });

        }
            )
              update.addEventListener('change',checkredis() )
        $.datetimepicker.setLocale('zh');
        $('#date').datetimepicker({
           theme: '',              //theme: 'dark',
 	       timepicker:false,       //timepicker:true,
 	       step: 60,                //step: 60 (這是timepicker的預設間隔60分鐘)
 	       format:'Y-m-d',         //format:'Y-m-d H:i:s',
 		   value: '${param.hiredate}',
 		   beforeShowDay: function (date) {
            // Disable dates more than n months from now
            return [date >= new Date() && date <= MonthFromNow];
        }
        // value:   new Date(),
           //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
           //startDate:	            '2017/07/10',  // 起始日
           //minDate:               '-1970-01-01', // 去除今日(不含)之前
           //maxDate:               '+1970-01-01'  // 去除今日(不含)之後
        });
        $('#srattime,#endtime').datetimepicker({
           theme: 'dark',              //theme: 'dark',
 	       datepicker:false,       //timepicker:true,
 	       step: 60,             //step: 60 (這是timepicker的預設間隔60分鐘)
 	       format:'H:i',         //format:'Y-m-d H:i:s',
 		   value: '', // value:   new Date(),
        });
        $('#srattime,#endtime').text("")
	});
	updatebook.setAttribute("style", "visibility:visible");
    }
    let people=null
    function pricechange(){
        people=document.getElementById("people").value
        let price=document.querySelector("#price")
        price.innerHTML = (people*200)
        }

    function change(){
   pricechange();
    let a=document.getElementById("date").value
    let b=document.getElementById("srattime").value+":00"
    let c=document.getElementById("endtime").value+":00"
    let d=deadline
    let e=people*200
    let data={bookingNo:bookno,
              memNo:memno,
              bookingDate:a ,
              bookingStartTime:b,
              bookingEndTime:c ,
              bookingFinishDate:d,
              bookingTotalPrice:e,
              bookingPeople:people ,
              bookingPaymentStatus:0,
              bookingCheckStatus:0,
    }
// redis改動

                redisdata={
                date:document.getElementById('date').value,
                minTime:document.getElementById('srattime').value.substr(0, 2),
                maxTime:document.getElementById('endtime').value.substr(0, 2),
                change:document.getElementById('people').value
                }
    $.ajax({
                url : '/book/saveseat4time',
                type : 'post',
                data : redisdata,
                success : function(response) {
                 },

            });

    $.ajax({
  url : '/book/addbooking',
  type : 'post',
  dataType : 'json',
  contentType : 'application/json; charset=utf-8', // 要送到server的資料型態
  data : JSON.stringify(data),
  success : function(result) {
    console.log(result.success); // result是json物件
  },
});
sleep(500).then(() => {
   read();
   updatebook.setAttribute("style", "visibility:hidden");
})
}
     function sleep (time) {
  return new Promise((resolve) => setTimeout(resolve, time));
}
function checkredis(){
                let check=0;
                a=document.getElementById('srattime').value.substr(0, 2)
                b=document.getElementById('endtime').value.substr(0, 2)
                datas={
                    date:document.getElementById('date').value,
                    minTime:a,
                    maxTime:b
                }
                console.log(datas);
                $.ajax({
                url : 'http://localhost:8082/book/testseat',
                type : 'POST',
                data : datas,
                success : function(response) {
                check=(response-(document.getElementById('people').value))
                console.log(check);
                if(deadline!=null)
               {
               document.getElementById('post').setAttribute('style',"display:none")
               }
                if(check>0)
                    {
             //       console.log("還有位置");
                    document.getElementById('post').removeAttribute('disabled')
                    document.getElementById('toPay').removeAttribute('disabled')
                    document.getElementById('wrong').innerHTML=``
                    }
                    else
                    {
               //     console.log("沒有");
                    document.getElementById('post').setAttribute('disabled',true)
                    document.getElementById('toPay').setAttribute('disabled',true)
                    document.getElementById('wrong').innerHTML=`<div font style="color:red;">此區間段位子以滿，或該時段不開放>`
                    }
                 },

            });

        }
