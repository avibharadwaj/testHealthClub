
const memberClasses = document.getElementById('memberClassesUl');
const services = document.getElementById('servicesUl');
const activities = document.getElementById('activities');
const logHours = document.getElementById('logHours');
const classes = document.getElementById('classesUl');

var name;
var email;
var phone;
var enrollmentStatus;

function getmemberDetails(){
  const url = 'http://localhost:8080/getUser?' + new URLSearchParams({
    username: 'Sujit'
    });
    
        fetch(url, {
            method: 'GET',
            mode: 'cors',
            headers: {
                "Content-Type": "application/json"
            }
        }).then(res => res.json()).then(response=>{
            name = response.name,
            email = response.email,
            phone = response.phone,
            enrollmentStatus = response.enrollmentStatus
        }).catch(err => {
            console.error(err);
        }).finally(()=>{
           
        });
}


// For members
function getMemberClasses(){


  const url = 'http://localhost:8080/mySchedule?' + new URLSearchParams({
    username: name
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
  activities.innerHTML = '';
  logHours.innerHTML = '';
  classes.innerHTML = '';
}

// For everyone
function getClasses(){

  resetContent();

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
        //  console.log(response);
         response.forEach(element => {
          classes.innerHTML += `
          <li><span class="class-name">${element.classType}</span> <span class="class-instructor">Instructor : ${element.instructor} </span> <span class="class-time">Tuesday 6:00 PM - 7:00 PM</span> <span class="class-availble-seats">Availble Seats : ${element.freeSeats} </span> <a href="#" class="btn" id="signupBtn">Sign up</a></li>
          `;

          const signupBtn = document.getElementById('signupBtn'); // or use a class name to select the button

          signupBtn.addEventListener('click', function(event) {
            event.preventDefault(); // prevent the default behavior of the link
            
              ////////////////////

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
                    
                  }).catch(err => {
                      console.error(err);
                  }).finally(()=>{
                    
                  });
      
            /////////////////////
          });

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

function getActivities(){
    activities.innerHTML = `
        <h2>Activities</h2>
        <form>
          <label for="activity-type">Activity Type:</label>
          <select id="activity-type">
            <option value="past-week">Past Week</option>
            <option value="past-month">Past Month</option>
            <option value="past-90-days">Past 90 Days</option>
          </select>
          <button type="submit">View</button>
        </form>
    `;
}


function getLogHours(){
    logHours.innerHTML = `
    <h2>Log Hours</h2>
    <form>
      <label for="activity">Activity:</label>
      <select id="activity">
        <option value="treadmill">Treadmill</option>
        <option value="cycling">Cycling</option>
        <option value="stair-machines">Stair Machines</option>
        <option value="weight-training">Weight Training</option>
      </select>
      <label for="hours">Hours:</label>
      <input type="number" id="hours" name="hours" min="1" max="10">
      <button type="submit">Log Hours</button>
    </form>
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
    getActivities();
    getLogHours();
    getMemberClasses();
    getClasses();
    getServices();
    getmemberDetails();
    document.getElementById('locationDropdown').addEventListener('change', getClasses);


}
document.addEventListener('DOMContentLoaded', init);