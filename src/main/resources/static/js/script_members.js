
const memberClasses = document.getElementById('memberClassesUl');
const services = document.getElementById('servicesUl');
const activitiesForm = document.getElementById('activitiesForm');
const showActivities = document.getElementById('showActivities');
const logHoursBtn = document.getElementById('logHoursBtn');
const classes = document.getElementById('classesUl');
const currentActivity = document.getElementById('activity');


global = {
    name: '',
    userName : '',
    phone : '',
    location : document.getElementById('locationDropdown').value,
}



function getmemberDetails(){
    console.log("inside getmemberDetails");
  const url = 'http://localhost:8080/getUser?' + new URLSearchParams({
    username: 'sk@gmail.com'
    });
    
    fetch(url, {
            method: 'GET',
            mode: 'cors',
            headers: {
                "Content-Type": "application/json"
            }
        }).then(res => res.json()).then(response=>{
            console.log(response);
            global.name = response.name;
            global.userName = response.username;
            global.phone = response.phone;
            enrollmentStatus = response.enrollmentStatus;
           
        }).catch(err => {
            console.error(err);
        }).finally(()=>{
            console.log("getMemberClasses fucntion called");
            getMemberClasses();

        });
}


// For members
function getMemberClasses(){

    console.log("inside getMemberClasses with userName : " + global.userName);

  const url = 'http://localhost:8080/member/mySchedule?' + new URLSearchParams({
    username: global.userName
});
     fetch(url, {
        method: 'GET',
        mode: 'cors',
        headers: {
            "Content-Type": "application/json"
        }
    }).then(res => res.json()).then(response=>{
      //  console.log(response);
       response.forEach(element => {
        memberClasses.innerHTML += `  
        <li><span class="class-name">${element.classType}</span> <span class="class-time">Monday 6:00 PM - 7:00 PM</span>  <span class="class-instructor">Instructor : ${element.instructor} </span> </li>
        `;
       });
    }).catch(err => {
        console.error(err);
    }).finally(()=>{
      
    });

   
}

function resetContent(){
  console.log("inside resetContent");
  memberClasses.innerHTML = '';
  services.innerHTML = '';
//   activities.innerHTML = '';
//   logHours.innerHTML = '';
  classes.innerHTML = '';
}

// For everyone
function getClasses(){

 global.location = document.getElementById('locationDropdown').value;

  resetContent();

//   const location = document.getElementById('locationDropdown');

  const url = 'http://localhost:8080/schedule?' + new URLSearchParams({
      locationName: global.location
  });
      fetch(url, {
          method: 'GET',
          mode: 'cors',
          headers: {
              "Content-Type": "application/json",
                    "Access-Control-Allow-Origin": "*",
                    "Access-Control-Allow-Headers": "*",
                    "Access-Control-Allow-Methods": "*",
                    "Access-Control-Allow-Credentials": "true",
                    "Access-Control-Max-Age": "180",
                    "Access-Control-Expose-Headers": "*",
                    "Access-Control-Request-Headers": "*",
                    "Access-Control-Request-Method": "*",
                    "Origin": "*"
          }
      }).then(res => res.json()).then(response=>{
            //  console.log(response);
         response.forEach((element,index) => {
          classes.innerHTML += `
          <li id=${index}><span class="class-name">${element.classType}</span> <span class="class-instructor">Instructor : ${element.instructor} </span> <span class="class-time">Tuesday 6:00 PM - 7:00 PM</span> <span class="class-availble-seats">Availble Seats : ${element.freeSeats} </span> <button class="btn" id="signupBtn${index}">Sign up</button></li>
          `;
         //   const signupBtn = document.getElementById(`signupBtn${index}`); // or use a class name to select the button
             console.log("DEBUG HITTING SIGNUP BUTTON");
            //   console.log(signupBtn.outerHTML);
            //   console.log("adding event listener to signup button : " + signupBtn);
             classes.querySelectorAll('li').forEach((li) => {
            li.addEventListener('click', (event) =>{
                    
                    console.log("inside event listener");
                    console.log(event.target.id);
                    if(event.target.classList.contains('btn')){
                        const index = event.target.id.replace('signupBtn', '');
                        console.log("index : " + index);
                        console.log("response[index].classID : " + response[index].classID);
                        const url = 'http://localhost:8080/member/classRegister?' + new URLSearchParams({
                            username: global.userName,
                            classId: response[index].classID
                        });

                        fetch(url, {
                                        method: 'POST',
                                        mode: 'cors',
                                        headers: {
                                            "Content-Type": "application/text",
                                            "Access-Control-Allow-Origin": "*",
                                            "Access-Control-Allow-Headers": "*",
                                            "Access-Control-Allow-Methods": "*",
                                            "Access-Control-Allow-Credentials": "true",
                                            "Access-Control-Max-Age": "180",
                                            "Access-Control-Expose-Headers": "*",
                                            "Access-Control-Request-Headers": "*",
                                            "Access-Control-Request-Method": "*",
                                            "Origin": "*"
                                        }
                                    }).then((res) => {
                                        console.log(res.status);
                                        if (res.status == 400){
                                            res.text().then(text => {
                                                Swal.fire({
                                                    position: 'center',
                                                    icon: "error",
                                                    title: text,
                                                    showConfirmButton: false,
                                                    timer: 1500
                                                })
                                                
                                            });
                                           
                                        }
                                        else if (res.status == 200){
                                            Swal.fire({
                                                position: 'center',
                                                icon: "success",
                                                title: "Successfully registered for the class",
                                                showConfirmButton: false,
                                                timer: 1500
                                            })
                                        }
                                        else{
                                            Swal.fire({
                                                position: 'center',
                                                icon: "error",
                                                title: "Something went wrong",
                                                showConfirmButton: false,
                                                timer: 1500
                                            })
                                            
                                        }
                                    })
                                    .catch(err => {
                                        console.error(err);
                                    }).finally(()=>{
                                    
                                    });
                    }});});

            }
            );
}).catch(err => {
            console.error(err);
        }).finally(()=>{
            console.log("getClasses fucntion called");
            getServices();
        }
        );
}





function getServices(){
    services.innerHTML = `
    <li><i class="fas fa-dumbbell"></i> Weight training</li>
              <li><i class="fas fa-bicycle"></i> Cycling</li>
              <li><i class="fas fa-shoe-prints"></i> Treadmill</li>
              <li><i class="fas fa-stairs"></i> Stair machines</li>
    `;
}

function activitiesOnSubmit(e){
    showActivities.innerHTML = '';
    e.preventDefault();
    const selectedValue = activitiesForm.querySelector('select').value;
    console.log(selectedValue);
    

    const url = 'http://localhost:8080/member/activity?' + new URLSearchParams({
        username: global.userName,
        duration: selectedValue
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
            const ele = document.createElement('p');
            ele.innerHTML = `Class Type: ${element.classType} Date: ${element.date} From: ${element.fromTime} To: ${element.toTime} instructor: ${element.instructor}`;
            showActivities.appendChild(ele);
              });
           // Need to implement this after adding mock data to db
        }).catch(err => {
            console.error(err);
        }).finally(()=>{
           
        });
    
}


function logHours(e){
    e.preventDefault();
    console.log("logHours called");
    const url = 'http://localhost:8080/member/logHours';

    var hours = document.getElementById('hours').value;

    fetch(url, {
        method: 'POST',
        mode: 'cors',
        body: JSON.stringify({
            username: global.userName,
            minutes: hours*60,
            myScheduleId: currentActivity.value
        }),
        headers: {
            "Content-Type": "application/json"
        }
    }).then(res =>{
        
        if (res.status == 400){
                            res.text().then(text => {
                                Swal.fire({
                                    position: 'center',
                                    icon: "error",
                                    title: text,
                                    showConfirmButton: false,
                                    timer: 1500
                                })
                                
                            });
                           
        }
        else if (res.status == 200){
            res.text().then(text => {
                            Swal.fire({
                                position: 'center',
                                icon: "success",
                                title: text,
                                showConfirmButton: false,
                                timer: 1500
                            })
                        })}
        else{
            Swal.fire({
                position: 'center',
                icon: "error",
                title: "Something went wrong",
                showConfirmButton: false,
                timer: 1500
                })
                            
        }

    //     console.log(res);
    //     res.json()
    // // } ).then(response=>{
    // //    console.log(response);
    }).catch(err => {
        console.error(err);
    }).finally(()=>{
       
    });
    
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
    getmemberDetails();
    

 
    getClasses();
    getServices();
    
    document.getElementById('locationDropdown').addEventListener('change', getClasses);
    activitiesForm.addEventListener('submit', activitiesOnSubmit);
    logHoursBtn.addEventListener('click', logHours);
    console.log(logHoursBtn.value);

}
document.addEventListener('DOMContentLoaded', init);