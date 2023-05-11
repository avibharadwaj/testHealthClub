


const classes = document.getElementById('classesUl');
const services = document.getElementById('servicesUl');





// For everyone
function getClasses(){

    const location = document.getElementById('locationDropdown');

    const url = 'http://localhost:8080/schedule?' + new URLSearchParams({
        locationName: location.value
    });
    
        fetch(url, {
            method: 'GET',
            mode: 'cors',
            headers: {
                "Content-Type": "application/json"
            }
        }).then(res => res.json()).then(response=>{
           console.log(response);
           response.forEach(element => {
            classes.innerHTML += `
            <li><span class="class-name">${element.classType}</span> <span class="class-instructor">Instructor : ${element.instructor} </span> <span class="class-time">Tuesday 6:00 PM - 7:00 PM</span> <span class="class-availble-seats">Availble Seats : ${element.freeSeats} </span> <a href="#" class="btn">Sign up</a></li>
            `;
           });
        }).catch(err => {
            console.error(err);
        }).finally(()=>{
           
        });
    // classes.innerHTML = `
    //           <li><span class="class-name">Zumba</span> <span class="class-time">Tuesday 6:00 PM - 7:00 PM</span> <span class="class-instructor">Instructor : durgaInstructorNameHere </span> <span class="class-availble-seats">Availble Seats : durgaInstructorNameHere </span> <a href="#" class="btn">Sign up</a></li>
    //           `;
}



function getServices(){
    services.innerHTML = `
    <li><i class="fas fa-dumbbell"></i> Weight training</li>
              <li><i class="fas fa-bicycle"></i> Cycling</li>
              <li><i class="fas fa-shoe-prints"></i> Treadmill</li>
              <li><i class="fas fa-stairs"></i> Stair machines</li>
    `;
}





console.log(location.pathname);
// const loginForm = document.getElementById('login-form');

// // Login 
// loginForm.addEventListener('submit', function(e) {
//     e.preventDefault();
//     var email = document.getElementById('email').value;
//     var password = document.getElementById('password').value;
//     var data = {
//         email: email,
//         password: password
//     };
//     var xhr = new XMLHttpRequest();
//     xhr.open('POST', '/login', true);
//     xhr.setRequestHeader('Content-Type', 'application/json');
//     xhr.send(JSON.stringify(data));
//     xhr.onreadystatechange = function() {
//         if (xhr.readyState == 4 && xhr.status == 200) {
//             var response = JSON.parse(xhr.responseText);
//             if (response.success) {
//                 window.location.href = '/dashboard';
//             } else {
//                 alert(response.message);
//             }
//         }
//     };
// });


function init(){
    getClasses();
    getServices();
    document.getElementById('locationDropdown').addEventListener('change', getClasses);



}
document.addEventListener('DOMContentLoaded', init);

