const findByIdBtn=document.getElementById('myPersonalData');
// const inputMemberId=sessionStorage.getItem("memNo");

// 轉換日期
function dateFromat(str){
    if(str!=null){
        // 取出日期字串
        const datestr=`${str}`;
        // 將日期字串轉換成 Date 物件
        const dateObj=new Date(datestr);
        // 取出年、月、日
        const year = dateObj.getUTCFullYear();
        const month = (dateObj.getUTCMonth() + 1).toString().padStart(2, "0");
        const day = dateObj.getUTCDate().toString().padStart(2, "0");
        return `${year}-${month}-${day}`;
    }else{
        return null;
    }
    
}
// let password;
// 利用session
window.onload = function() {
    // 註冊成功所需資訊
    const name=sessionStorage.getItem("username");
    document.getElementById("myName").innerText=name;

    const email=sessionStorage.getItem("email");


    document.getElementById("mem_no").value=sessionStorage.getItem("memNo");
    console.log(document.getElementById("mem_no").value);
   

    // 會員帳號維護頁面,讀取會員資料
    findByIdBtn.addEventListener('click',()=>{
        const memberNo=document.getElementById("mem_no").value;
        if(memberNo===""){
            fetch(`/mem/find-email?email=${email}`)
            .then(response=>response.json())
            .then(data=>{
                console.log(`/mem/find-email?email=${email}`);
                console.log(data);

                document.getElementById('mem_no').value=`${data.memNo}`;
                // password=`${data.memPassword}`;
                document.getElementById('mem_name').value=`${data.memName}`;
                document.getElementById('mem_phone').value=`${data.memPhone}`;
                document.querySelector(`input[name="genderOptions"][value="${data.memGender}"]`).checked = true;
                document.getElementById('mem_birthday').value=dateFromat(data.memBirthday);
                document.getElementById('mem_email').value=`${data.memEmail}`;
                document.getElementById('mem_address').value=`${data.memAddress}`;
                document.getElementById('coupon').value=`${data.coupon}`;
                document.querySelector(`input[name="reserve_auth"][value="${data.reserveAuth?1:0}"]`).checked = true;
                document.getElementById('mem_vio').value=`${data.memVIO}`;
                document.getElementById('mem_status').value=dateFromat(data.memStatus);
            })
        }else{
            console.log(typeof(memberNo));
            fetch(`/mem/find-one?id=${memberNo}`)
                .then(response=>response.json())
                .then(data=>{
                    console.log(`/mem/find-one?id=${memberNo}`);
                    console.log(data);
    
                    document.getElementById('mem_no').value=`${data.memNo}`;
                    // password=`${data.memPassword}`;
                    document.getElementById('mem_name').value=`${data.memName}`;
                    document.getElementById('mem_phone').value=`${data.memPhone}`;
                    document.querySelector(`input[name="genderOptions"][value="${data.memGender}"]`).checked = true;
                    document.getElementById('mem_birthday').value=dateFromat(data.memBirthday);
                    document.getElementById('mem_email').value=`${data.memEmail}`;
                    document.getElementById('mem_address').value=`${data.memAddress}`;
                    document.getElementById('coupon').value=`${data.coupon}`;
                    document.querySelector(`input[name="reserve_auth"][value="${data.reserveAuth?1:0}"]`).checked = true;
                    document.getElementById('mem_vio').value=`${data.memVIO}`;
                    document.getElementById('mem_status').value=dateFromat(data.memStatus);
    
            })
        }

    })


    findByIdBtn.addEventListener('click',()=>{
        const memberNo=inputMemberId.value;
        console.log(memberNo);
        fetch(`/mem/find-one?id=${memberNo}`)
            .then(response=>response.json())
            .then(data=>{
                console.log(`/mem/find-one?id=${memberNo}`);
                console.log(data);

                document.getElementById('mem_no').value=`${data.memNo}`;
                // password=`${data.memPassword}`;
                document.getElementById('mem_name').value=`${data.memName}`;
                document.getElementById('mem_phone').value=`${data.memPhone}`;
                document.querySelector(`input[name="genderOptions"][value="${data.memGender}"]`).checked = true;
                document.getElementById('mem_birthday').value=dateFromat(data.memBirthday);
                document.getElementById('mem_email').value=`${data.memEmail}`;
                document.getElementById('mem_address').value=`${data.memAddress}`;
                document.getElementById('coupon').value=`${data.coupon}`;
                document.querySelector(`input[name="reserve_auth"][value="${data.reserveAuth?1:0}"]`).checked = true;
                document.getElementById('mem_vio').value=`${data.memVIO}`;
                document.getElementById('mem_status').value=dateFromat(data.memStatus);

            })
    })


    // 會員修改資料
    let modifyData={};
    $('#mem-save').click((e) => {
        e.preventDefault();
        // 取得user輸入的資料
        modifyData.memNo=document.getElementById('mem_no').value;
        modifyData.memName=document.getElementById('mem_name').value;
        // modifyData.memPassword=password;
        modifyData.memEmail=document.getElementById("mem_email").value;
        modifyData.memPhone=document.getElementById("mem_phone").value;
        modifyData.memAddress=document.getElementById("mem_address").value;
        modifyData.memBirthday=document.getElementById("mem_birthday").value;
        modifyData.memGender=document.querySelector('input[name="genderOptions"]:checked').value;
        console.log(modifyData);
        fetch(`/mem/save`, {
            method: 'POST',
            body: JSON.stringify(modifyData),
            headers: {'Content-Type': 'application/json'},
        }).then(response => response.json())
        .then(data => console.log(data))
        .catch(error => console.error(error));
    });

    $('#mem-form').submit(e=>{

        e.preventDefault();
        console.log(modifyData);

        // Send form data as POST request
        fetch(e.target.action, {
            method: 'POST',
            body: JSON.stringify(modifyData),
            headers: {'Content-Type': 'application/json'}
        })
        .then(response => {
            if (!response.ok) {
            throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log(data);
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
        });   
    })

}
