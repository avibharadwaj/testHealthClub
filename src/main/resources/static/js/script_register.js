let newmembersSection = document.querySelector('.registerform');
newmembersSection.querySelector('form').addEventListener('submit', async (e) => {
    e.preventDefault();
    newMemberName = newmembersSection.querySelector('#name').value;
    newMemberEmail = newmembersSection.querySelector('#username').value;
    newMemberPassword = newmembersSection.querySelector("#password").value;
    newMemberPhone = newmembersSection.querySelector('#phone').value;
    console.log(newMemberName, newMemberEmail, newMemberPassword, newMemberPhone);

    const url = 'http://localhost:8080/register';

    fetch(url, {
        method: 'POST',
        mode: 'cors',
        body: JSON.stringify({
            name: newMemberName,
            username: newMemberEmail,
            password: newMemberPassword,
            phone: newMemberPhone
        }),

        headers: {
            'Content-Type' : 'application/json'
        },
    }).then(res => res.json()).then(response=>{
        console.log(response);
            location.replace("/login");
    }).catch(err => {
        console.error(err);
    }).finally(()=>{
        newmembersSection.querySelector('#name').value='';
        newmembersSection.querySelector('#username').value='';
        newmembersSection.querySelector('#password').value='';
        newmembersSection.querySelector('#phone').value='';
    });

})
