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
            name: newMemberName,
            username: newMemberEmail,
            phone: newMemberPhone,
            locationName: document.getElementById('locationDropdown').value,
            membershipId: ''
        }),
        headers: {
            "Content-Type": "application/json"
        }
    }).then(res => res.json()).then(response=>{
        console.log("The data returned is:");
        console.log(response);
        if(response.enrollmentStatus==='enrolled'){
            Swal.fire({
                position: 'top-end',
                icon: 'success',
                title: `New member ${newMemberName} enrolled!`,
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

const checkInForm = document.getElementById('checkInForm');
 

checkInForm.addEventListener('submit', function(e) {
    e.preventDefault();
    const memberName = checkInForm.querySelector('#member_id').value;
    // const activitySelected = checkInForm.querySelector('#activity').value;
    // const date = new Date();
    // const time = `${date.getHours()}:${date.getMinutes()}:${date.getSeconds()}`;
    const url = 'http://localhost:8080/checkIn?' + new URLSearchParams({
        username: memberName
        });
    console.log("username: ", memberName);
    fetch(url, {
        method: 'POST',
        mode: 'cors',
        headers: {
            "Content-Type": "application/json"
        }
    }).then(res =>{
        if (res.status===200){
            res.text().then(text => {
            Swal.fire({
                position: 'center',
                icon: 'success',
                title: text,
                showConfirmButton: false,
                timer: 1500
            })})
        }
        else if (res.status===400){
            res.text().then(text => {
            swal.fire({
                position: 'center',
                icon: 'error',
                title: `Member already checked in`,
                showConfirmButton: false,
                timer: 1500
            })})
        }
        else{
            Swal.fire({
                position: 'center',
                icon: 'error',
                title: `Something went wrong!`,
                showConfirmButton: false,
                timer: 1500
            })
        }
    })
    .catch(err => {
        console.log("Error");
        console.log(err);
    });
    // .then(response=>{
        // if(response.checkInStatus==='Checked In'){
        //     Swal.fire({
        //         position: 'top-end',
        //         icon: 'success',
        //         title: `Checked In ${memberName}!`,
        //         showConfirmButton: false,
        //         timer: 1500
        //     })
        // }
    // })
    

});


const checkOutForm = document.getElementById('checkout');


checkOutForm.addEventListener('submit', function(e) {

    e.preventDefault();
    const memberName = checkOutForm.querySelector('#member_id').value;
    const date = new Date();
    const time = console.log();
    const currentDate = `${date.getFullYear()}:${date.getMonth()}:${date.getDate()}`;
    const currentTime = `${date.getHours()}:${date.getMinutes()}:${date.getSeconds()}`;
    console.log(currentDate, currentTime);
    const url = 'http://localhost:8080/checkOut?'+ new URLSearchParams({
        username: memberName
        });
    fetch(url, {
        method: 'POST',
        mode: 'cors',

        headers: {
            "Content-Type": "application/json"
        }
    }).then(res => {
        if (res.status===200){
            res.text().then(text => {
            Swal.fire({
                position: 'center',
                icon: 'success',
                title: 'Member checked out successfully',
                showConfirmButton: false,
                timer: 1500
            })})
        }
        else if (res.status===400){
            res.text().then(text => {
            swal.fire({
                position: 'center',
                icon: 'error',
                title: `Please check in the member first`,
                showConfirmButton: false,
                timer: 1500
            })})
        }
        else{
            Swal.fire({
                position: 'center',
                icon: 'error',
                title: `Something went wrong!`,
                showConfirmButton: false,
                timer: 1500
            })
        }
    })
    .catch(err => {
        console.log("Error");
        console.log(err);
    });
    // .then(response=>{
        // if(response.checkOutStatus==='Checked Out'){
        //     Swal.fire({
        //         position: 'top-end',
        //         icon: 'success',
        //         title: `Checked Out ${memberName}!`,
        //         showConfirmButton: false,
        //         timer: 1500
        //     })
        // }
    // })
});


const freeTrailsForm = document.getElementById('freeTrails');

freeTrailsForm.addEventListener('submit', function(e) {
    
        e.preventDefault();
        const username = freeTrailsForm.querySelector('#userName').value;
        const memberName = freeTrailsForm.querySelector('#name').value;
        const phone = freeTrailsForm.querySelector('#phone').value;
       console.log(username, memberName, phone);
        const url = 'http://localhost:8080/signUpTrial';
        fetch(url, {
            method: 'POST',
            mode: 'cors',
            body: JSON.stringify({
                username: username,
                memberName: memberName,
                phone: phone,
                locationName: document.getElementById('locationDropdown').value,
                membershipDuration: 0
            }),
            headers: {
                "Content-Type": "application/json"
            }
        }).then(res => {
            if (res.status===200){
                res.text().then(text => {
                    console.log(text);
                Swal.fire({
                    position: 'center',
                    icon: 'success',
                    title: "Free Trail Activated!",
                    showConfirmButton: false,
                    timer: 1500
                })})
            }
            else if (res.status===400){
                res.text().then(text => {
                swal.fire({
                    position: 'center',
                    icon: 'error',
                    title: text,
                    showConfirmButton: false,
                    timer: 1500
                })})
            }
            else{
                Swal.fire({
                    position: 'center',
                    icon: 'error',
                    title: `Something went wrong!`,
                    showConfirmButton: false,
                    timer: 1500
                })
            }
        })
        .catch(err => {
            console.log("Error");
            console.log(err);
        });
        // .then(response=>{
            // if(response.freeTrailsStatus==='Free Trail'){
            //     Swal.fire({
            //         position: 'top-end',
            //         icon: 'success',
            //         title: `Free Trail ${memberName}!`,
            //         showConfirmButton: false,
            //         timer: 1500
            //     })
            // }
        // })
    });


   