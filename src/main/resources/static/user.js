$(document).ready(function () {
    getUser()


})


async function getUser() {
    let user = await fetch('/admins/user')
        .then(res => res.json())



    navBar(user)
    userTable(user)

}



function navBar( user) {
    let role = user.roles
    let stringRole = ''
    if (role[0].name === 'ROLE_ADMIN') {
        stringRole = 'admin'
    } else {
        stringRole = 'user'
    }

    $('#userNameNavBar').append(user.firstName)
    $('#userRoleNavBar').append(stringRole)

}

function userTable (user){
    $('#tbodyUserTable').append('<tr id="trUserTable"></tr>')
    $('#trUserTable').append('<td id="tdUserId"></td>')
    $('#trUserTable').append('<td id="tdUserFirstName"></td>')
    $('#trUserTable').append('<td id="tdUserSecondName"></td>')
    $('#trUserTable').append('<td id="tdUserAge"></td>')
    $('#trUserTable').append('<td id="tdUserEmail"></td>')
    $('#trUserTable').append('<td id="tdUserRole"></td>')
    $('#tdUserId').append(user.id)
    $('#tdUserFirstName').append(user.firstName)
    $('#tdUserSecondName').append(user.secondName)
    $('#tdUserAge').append(user.age)
    $('#tdUserEmail').append(user.email)
    $('#tdUserRole').append(user.roles[0].name)
}








