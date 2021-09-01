$(document).ready(function () {
    getAllUsers()
    getAllRoles()
})
let allUsers

let allRoles


async function getAllUsers() {
    let data = await fetch('admins')
    allUsers = await data.json()
    allUserTable(allUsers)
    return allUsers
}

async function getAllRoles() {
    let data = await fetch('admins/roles')
    allRoles = await data.json()
    return allRoles
}

function allUserTable(users) {
    $('.myTbody').empty()

    $.each(users, function (i, user) {
        let role = user.roles
        let stringRole = ''


        for (let name in role) {
            stringRole += `${role[name].name}` + " "
        }

        $('<tr>').append(
            $('<td>').text(user.id),
            $('<td>').text(user.firstName),
            $('<td>').text(user.secondName),
            $('<td>').text(user.age),
            $('<td>').text(user.email),
            $('<td>').text(stringRole),
            $('<td>').append($('<button>').text("Edit").attr({
                "type": "button",
                "id": "buttonEdit",
                "class": "btn btn-info",
                "data-bs-toggle": "modal",
                "data-bs-target": "#editModal",
            }).data("user", user)),
            $('<td>').append($('<button>').text("Delete").attr({
                "type": "button",
                "id": "buttonDelete",
                "class": "btn btn-danger",
                "data-bs-toggle": "modal",
                "data-bs-target": "#deleteModal",
            }).data("user", user))
        ).appendTo('.myTbody')
    })
}

$(document).on('click', '#buttonEdit', function () {
    $('#editModal').modal('show')

    let user = $(this).data("user")
    $('#ID').val(user.id)
    $('#firstName').val(user.firstName)
    $('#secondName').val(user.secondName)
    $('#Age').val(user.age)
    $('#Email').val(user.email)
    $('#password').val(user.password)

    let sel = false


    $.each(allRoles, function (i, role) {

        $.each(user.roles, function (i, userRole) {
            if (userRole.name === role.name) {
                sel = true
            }
        })

        $('#Role').append(
            $('<option>').text(role.name).attr({
                "id" : role.id,
                "value" : role.name,
                "selected" : sel,
            })
        )


        sel = false

    })
})

$(document).on('click', '#submitEdit',async function () {
    let roles = []

    $('#Role option:selected').each(function (index, value) {
        roles[index] = {
            id : value.id,
            role: value.value
        }
    })

    const user = {
        id: $('#ID').val(),
        firstName: $('#firstName').val(),
        secondName: $('#secondName').val(),
        age : $('#Age').val(),
        email : $('#Email').val(),
        password : $('#password').val(),
        roles : roles
    }

    try {
        const response = await fetch('admins/change', {
            method : 'PATCH',
            body : JSON.stringify(user),
            headers: {
                'Content-Type' : 'application/json'
            }

    })

        $("#editClose").click();
        await getAllUsers()
        await getAllRoles()
        const json = await response.json()
        console.log('Succes',JSON.stringify(json))

    }catch (error) {
        console.error('error',error)
    }
})

$(document).on('click', '#buttonDelete', function () {
    $('#deleteModal').modal('show')

    let user = $(this).data("user")
    $('#IDdelete').val(user.id)
    $('#firstNameDelete').val(user.firstName)
    $('#secondNameDelete').val(user.secondName)
    $('#AgeDelete').val(user.age)
    $('#EmailDelete').val(user.email)
    $('#passwordDelete').val(user.password)

    let sel = false


    $.each(allRoles, function (i, role) {

        $.each(user.roles, function (i, userRole) {
            if (userRole.name === role.name) {
                sel = true
            }
        })

        $('#RoleDelete').append(
            $('<option>').text(role.name).attr({
                "id" : role.id,
                "value" : role.name,
                "selected" : sel,
            })
        )


        sel = false

    })
})

$(document).on('click', '#submitDelete',async function () {


    try {
        const response = await fetch('admins/delete/' + $('#IDdelete').val(), {
            method : 'DELETE',


        })

        $("#closeDeleteModal").click();
        await getAllUsers()
        await getAllRoles()
        const json = await response.json()
        console.log('Succes',JSON.stringify(json))

    }catch (error) {
        console.error('error',error)
    }
})
















