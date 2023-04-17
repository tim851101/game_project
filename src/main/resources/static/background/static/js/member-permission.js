$(document).ready(() => {
    const memList = document.getElementById("mem-ls-all");
    const token = sessionStorage.getItem('token');
    if (token == null) {
        return;
    }
    // list all member
    fetch(`/mem/ls-dtos`, {
        headers: {
            'Authorization': 'Bearer ' + token,
        }
    })
        .then(response => response.json()) // promise -> data
        .then(members => {
            findAll(members);
        })

    /**
     * Edit member auth
     */
    memList.addEventListener('click', (e) => {
        const memNo = parseInt(e.target.id.split('-')[2]);
        const memAuth = $(`#div-auth-${memNo}`).text();
        const icon = document.getElementById(`i-auth-${memNo}`);
        const auth = document.getElementById(`div-auth-${memNo}`);
        const isAuthorized = auth.textContent === "有權";

        // store auth
        if (e.target.id == `mem-i-${memNo}` || e.target.id == `mem-edit-${memNo}}`) {
            fetch('/mem/save-all-auth', {
                method: 'POST',
                body: JSON.stringify([{
                    'memNo': memNo,
                    'reserveAuth': memAuth == '有權' ? true : false
                }]),
                headers: {
                    'Authorization': 'Bearer ' + token,
                    'Content-Type': 'application/json'
                }
            })
                .then(response => response.json()) // promise -> data
                .then(ok => {
                    if (ok) {
                        swal.fire('權限更新成功');
                    } else {
                        swal.fire('權限更新失敗');
                    }
                })
        }
        // toggle auth
        if (e.target.id == `i-auth-${memNo}` || e.target.id == `div-auth-${memNo}`) {
            // toggle

            icon.classList.toggle("text-success", !isAuthorized);
            icon.classList.toggle("text-danger", isAuthorized);
            auth.textContent = isAuthorized ? "停權" : "有權";
        }
    }, true);
})


// list all
function findAll(members) {
    // datatable destroy -> initial
    let empTable = document.getElementById('mem-ls-all');
    $('#table-example').DataTable().clear().destroy();
    $('#table-example').DataTable().destroy();

    let tdString = "";
    for (const mem of members) {
        tdString += `
            <tr>
                <td>
                    <strong>${1e3 + mem.memNo}</strong>
                </td>
                <td>
                    <div class="d-flex align-items-center">
                        <span class="w-space-no">${mem.memName}</span>
                        <img src="" class="rounded-lg me-2" width="24" alt=""/>
                    </div>
                </td>
                <td>${mem.memEmail}</td>
                <td>
                    <div class="d-flex align-items-center auth-group">
                        <i id="i-auth-${mem.memNo}" class="fa fa-circle ${mem.reserveAuth ? "text-success" : "text-danger"} me-1"></i>
                        <div id="div-auth-${mem.memNo}">${mem.reserveAuth ? "有權" : "停權"}</div>
                    </div>
                </td>
                <td>
                    <div class="d-flex">
                        <a id="mem-edit-${mem.memNo}" class="btn btn-primary shadow btn-xs sharp me-1 mem-edit-btn">
                            <i id="mem-i-${mem.memNo}" class="fas fa-pencil-alt"></i>
                        </a>
                    </div>
                </td>
            </tr>`;
    }
    empTable.innerHTML = tdString;
    $('#mem-ls-all').html(tdString);

    /**
     * add pagination
     * @type {*|jQuery}
     */
    $('#table-example').DataTable({
        paging: true,
        pageLength: 5,
        language: {
            paginate: {
                previous: '<i class="fa fa-chevron-left"></i>',
                next: '<i class="fa fa-chevron-right"></i>'
            }
        }
    });
}