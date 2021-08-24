$(document).ready(function () {
    getAllUsers()
    getAllRoles()



})
let allUsers

let allRoles



async function getAllUsers() {
    let data = await fetch('http://localhost:8080/admins')
    allUsers = await data.json()

    return allUsers
}

async function getAllRoles() {
    let data = await fetch('http://localhost:8080/admins/roles')
    allRoles = await data.json()
    console.log(allRoles)

    $.each(allRoles, function (i, role) {


        $('#forNewUserRole').append(
            $('<option>').text(role.name).attr({
                "id": role.id,
                "value": role.name,

            })
        )


        sel = false
    })
    return allRoles
}

let sel = false

console.log(allRoles)








$(document).on('click','#createButton', async function () {
    let roles = []

    $('#forNewUserRole option:selected').each(function (index, value) {
        roles[index] = {
            id : value.id,
            role : value.value
        }
    })

    const user = {
        firstName : $('#newfirstName').val(),
        secondName: $('#newsecondName').val(),
        age: $('#newAge').val(),
        email : $('#newEmail').val(),
        password: $('#newpassword').val(),
        roles : roles
    }

    try {
        const response = await fetch('http://localhost:8080/admins/save',{
            method : 'POST',
            body : JSON.stringify(user),
            headers : {
                'Content-Type' : 'application/json'
            }
        })
        $('#allUserPage').click()
        await getAllUsers()
        await getAllRoles()

        const json = await response.json()
        console.log('succec',JSON.stringify(json))

    }catch (error) {
        console.error('error',error)
    }
})