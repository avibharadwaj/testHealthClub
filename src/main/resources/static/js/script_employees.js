let newmembersSection = document.querySelector('.newmembers');
newmembersSection.querySelector('form').addEventListener('submit',async (e)=>{
    e.preventDefault();
    newMemberName = newmembersSection.querySelector('#name').value;
    newMemberEmail = newmembersSection.querySelector('#email').value;
    newMemberPhone = newmembersSection.querySelector('#phone').value;


    
    console.log(newMemberName, newMemberEmail, newMemberPhone);

    const url = 'http://localhost:8080/enroll';

    fetch(url, {
        method: 'POST',
        mode: 'cors',
        body: JSON.stringify({
            username: newMemberName,
            email: newMemberEmail,
            phone: newMemberPhone
        }),
        headers: {
            "Content-Type": "application/json"
        }
    }).then(res => res.json()).then(response=>{
        if(response.enrollmentStatus==='Enrolled'){
            Swal.fire({
                position: 'top-end',
                icon: 'success',
                title: `New member ${newMemberName} added!`,
                showConfirmButton: false,
                timer: 1500
            })
        }
    }).catch(err => {
        console.error(err);
    }).finally(()=>{
        newmembersSection.querySelector('#name').value='';
        newmembersSection.querySelector('#email').value='';
        newmembersSection.querySelector('#phone').value='';
    });

    

})


