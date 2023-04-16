$(document).ready( () => {
    const employeeUpdate = $('a[href="/background/employee-update.html"]').first();
    const token = sessionStorage.getItem('token');
    const role = parseJwt(token).role[0];

    if (role == "USER"){
        employeeUpdate.hide();
    } else if (role == "ADMIN") {
        employeeUpdate.show();
    }
    if (role == "ADMIN"){
        employeeUpdate.show();
    }
    const logoutLink = document.querySelector('a[href="/background/employee-login-out.html"]');
    if (logoutLink) {
        logoutLink.style.display = "none";
    }
})

function parseJwt (token) {
    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    const jsonPayload = decodeURIComponent(window.atob(base64).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));

    return JSON.parse(jsonPayload);
}
