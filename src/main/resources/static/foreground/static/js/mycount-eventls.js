    let eventls=document.getElementById('eventls');
    const eventlsSE=localStorage.getItem('memNo');
        memno=3;
        console.log(eventlsSE);
        fetch('/eventord/byeventformem/'+eventlsSE,{
            method:'post'
        }).then(response => response.json())
        .then(list =>{eventls.innerHTML + ``
            for(let event of list)
            {
                let time=event.eventStarttime;
                console.log(time);
                eventls.innerHTML += `
                <tr>
                   <td>${event.eventNo}</td>
                   <td>${event.eventName}</td>
                   <td>${event.eventStarttime}</td>
                   <td>${event.eventFee}</td>
                <tr>
                    `

            }
        })
