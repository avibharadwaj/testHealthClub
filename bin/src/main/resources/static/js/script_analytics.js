
const global = {
  xHoursLabelsDay: [],
  yHoursLabelsDay: [],
  xHoursLabelsWeek: [],
  yHoursLabelsWeek: [],
  xHoursLabelsMonth: [],
  yHoursLabelsMonth: [],

  xEnrollmentLabelsDay: [],
  yEnrollmentLabelsDay_classes: [],
  yEnrollmentLabelsDay_enrollment: [],
  xEnrollmentLabelsWeek: [],
  yEnrollmentLabelsWeek_classes: [],
  yEnrollmentLabelsWeek_enrollment: [],

}

async function showGraph(timePeriod) {
    var chartData = await getChartDataClasses(timePeriod);
    var ctx = document.getElementById('class-enrollment-chart');
    let charStatus = Chart.getChart('class-enrollment-chart');
    if(charStatus){
        charStatus.destroy();
    }
    var chart = new Chart(ctx, {
      type: 'line',
      data: chartData,
      options: {}
    });
  }

  async function updateHours(timePeriod) {
    var chartData = await getChartDataHours(timePeriod);
    //console.log("got the data from getChartDataHours");
    var ctx = document.getElementById('hours-chart');
    let charStatus = Chart.getChart('hours-chart');
    if(charStatus){
        charStatus.destroy();
    }
    var chart = new Chart(ctx, {
      type: 'line',
      data: chartData,
      options: {}
    });
    
  }

function updateVisitors(timePeriod) {
    var chartData = getChartDataVisitors(timePeriod);
    var ctx = document.getElementById('visitors-chart');
    let charStatus = Chart.getChart('visitors-chart');
    if(charStatus){
        charStatus.destroy();
    }
    var chart = new Chart(ctx, {
      type: 'line',
      data: chartData,
      options: {}
    });
    
  }



function getChartDataClasses(timePeriod) {
    var chartData = {};
    if (timePeriod == 'day') {

      const url = 'http://localhost:8080/enrollmentStatus?' + new URLSearchParams({
        locationName: '1',
        filter: 'day',
        });
    
      return fetch(url, {
            method: 'GET',
            mode: 'cors',
            headers: {
                "Content-Type": "application/json"
            }}).then(res => res.json()).then(response=>{
          //    console.log("response "+response);

            
              const counts = Object.values(response).map(obj => Object.keys(obj).length);
		//	console.log("counts "+counts)
              const enrollmentCounts = Object.values(response).map(obj => {
                return Object.values(obj).reduce((sum, curr) => {
                  return sum + curr.enrollmentCount;
                }, 0);
              });
        
              global.xEnrollmentLabelsDay = Object.keys(response);
              global.yEnrollmentLabelsDay_classes = counts;
              global.yEnrollmentLabelsDay_enrollment = enrollmentCounts;




            //  console.log("global.xEnrollmentLabelsDay: "+ global.xEnrollmentLabelsDay);
           //   console.log("global.yEnrollmentLabelsDay_classes"+ global.yEnrollmentLabelsDay_classes);
          //    console.log("global.yEnrollmentLabelsDay_enrollment"+ global.yEnrollmentLabelsDay_enrollment);
              chartData = {
                labels: global.xEnrollmentLabelsDay,
                
                datasets: [{
                    label: 'Classes',
                    data: global.yEnrollmentLabelsDay_classes,
                    backgroundColor: 'rgba(255, 206, 86, 0.2)',
                    borderColor: 'rgba(255, 206, 86, 1)',
                    borderWidth: 1
                  }, {
                    label: 'Enrollment',
                    data: global.yEnrollmentLabelsDay_enrollment,
                    backgroundColor: 'rgba(75, 192, 192, 0.2)',
                    borderColor: 'rgba(75, 192, 192, 1)',
                    borderWidth: 1
                  }]
              };
              //console.log("before return chartData "+ chartData);
              return chartData;
            })
        
    } 
    else if (timePeriod == 'week') {


      const url = 'http://localhost:8080/enrollmentStatus?' + new URLSearchParams({
        locationName: '1',
        filter: 'week',
        });
    
      return fetch(url, {
            method: 'GET',
            mode: 'cors',
            headers: {
                "Content-Type": "application/json"
            }}).then(res => res.json()).then(response=>{
              console.log(response);

            
              const counts = Object.values(response).map(obj => Object.keys(obj).length);

              const enrollmentCounts = Object.values(response).map(obj => {
                return Object.values(obj).reduce((sum, curr) => {
                  return sum + curr.enrollmentCount;
                }, 0);
              });
        
              global.xEnrollmentLabelsWeek = Object.keys(response);
              global.yEnrollmentLabelsWeek_classes = counts;
              global.yEnrollmentLabelsWeek_enrollment = enrollmentCounts;




             // console.log("global.xEnrollmentLabelsDay: "+ global.xEnrollmentLabelsWeek);
             // console.log("global.yEnrollmentLabelsDay_classes"+ global.yEnrollmentLabelsWeek_classes);
              // console.log("global.yEnrollmentLabelsDay_enrollment"+ global.yEnrollmentLabelsWeek_enrollment);
              chartData = {
                  labels: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
                  datasets: [{
                    label: 'Classes',
                    data: [5, 7, 6, 8, 5, 4, 6],
                    backgroundColor: 'rgba(255, 206, 86, 0.2)',
                    borderColor: 'rgba(255, 206, 86, 1)',
                    borderWidth: 1
                  }, {
                    label: 'Enrollment',
                    data: [40, 28, 36, 42, 33, 25, 30],
                    backgroundColor: 'rgba(75, 192, 192, 0.2)',
                    borderColor: 'rgba(75, 192, 192, 1)',
                    borderWidth: 1
                  }]
                };
              //console.log("before return chartData "+ chartData);
              return chartData;
            })









      // chartData = {
      //   labels: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
      //   datasets: [{
      //     label: 'Classes',
      //     data: [5, 7, 6, 8, 5, 4, 6],
      //     backgroundColor: 'rgba(255, 206, 86, 0.2)',
      //     borderColor: 'rgba(255, 206, 86, 1)',
      //     borderWidth: 1
      //   }, {
      //     label: 'Enrollment',
      //     data: [40, 28, 36, 42, 33, 25, 30],
      //     backgroundColor: 'rgba(75, 192, 192, 0.2)',
      //     borderColor: 'rgba(75, 192, 192, 1)',
      //     borderWidth: 1
      //   }]
      // };
      // return chartData;
    
    }
}  

 function getChartDataHours(timePeriod) {
    if (timePeriod == 'day') {      
      const url = 'http://localhost:8080/totalHoursSpent?' + new URLSearchParams({
        locationName: '1',
        filter: 'day',
        });
    
      return fetch(url, {
            method: 'GET',
            mode: 'cors',
            headers: {
                "Content-Type": "application/json"
            }}).then(res => res.json()).then(response=>{
              console.log(response);
        
              global.xHoursLabelsDay = Object.keys(response);
              global.yHoursLabelsDay = Object.values(response);
              console.log("global.xHoursLabelsDay: "+ global.xHoursLabelsDay);
              console.log("global.yHoursLabelsDay"+ global.yHoursLabelsDay);
          chartData = {
              labels: global.xHoursLabelsDay,
              
              datasets: [{
                  label: "hours",
                  data: global.yHoursLabelsDay,
                  backgroundColor: 'rgba(255, 206, 86, 0.2)',
                  borderColor: 'rgba(255, 206, 86, 1)',
                  borderWidth: 1
                }]
            };
            //console.log("before return");
            //console.log(chartData);
            return chartData; 
        })
        .catch(err => {
            //console.error(err);
        }).finally(()=>{
          
        });
    } 
    else if (timePeriod == 'week') {
      const url = 'http://localhost:8080/totalHoursSpent?' + new URLSearchParams({
        locationName: '1',
        filter: 'week',
        });
      return fetch(url, {
        method: 'GET',
        mode: 'cors',
        headers: {
            "Content-Type": "application/json"
        }}).then(res => res.json()).then(response=>{
      //console.log("week selected");
      //console.log(response);
      //console.log("keys "+Object.keys(response));

          const data = response;

          const totalHoursOfWeekArray = Object.values(data).map(obj => obj.totalHoursOfWeek);


      //console.log("X labels "+ totalHoursOfWeekArray);
      //console.log("Y labels" + Object.keys(response));
      global.xHoursLabelsWeek = totalHoursOfWeekArray;
      global.yHoursLabelsWeek = Object.keys(response);
        
      chartData = {
        labels: global.yHoursLabelsWeek,
        datasets: [{
            label: 'members',
            data: global.xHoursLabelsWeek,
            backgroundColor: 'rgba(255, 206, 86, 0.2)',
            borderColor: 'rgba(255, 206, 86, 1)',
            borderWidth: 1
          }]
      };
      return chartData;
    })
    .catch(err => {
        //console.error(err);
    }).finally(()=>{
      
    });
    
    }
    else if (timePeriod == 'month') {


      const url = 'http://localhost:8080/totalHoursSpent?' + new URLSearchParams({
        locationName: '1',
        filter: 'month',
        });
      return fetch(url, {
        method: 'GET',
        mode: 'cors',
        headers: {
            "Content-Type": "application/json"
        }}).then(res => res.json()).then(response=>{
      //console.log("month selected");
      //console.log(response);
      //console.log("keys "+Object.keys(response));

          const data = response;

          const totalHoursOfMonth = Object.values(data).map(obj => obj.totalHoursOfMonth);


      //console.log("X labels "+ totalHoursOfMonth);
      //console.log("Y labels" + Object.keys(response));
      global.xHoursLabelsMonth = totalHoursOfMonth;
      global.yHoursLabelsMonth = Object.keys(response);
        
      chartData = {
        labels: global.yHoursLabelsMonth,
        datasets: [{
            label: 'members',
            data: global.xHoursLabelsMonth,
            backgroundColor: 'rgba(255, 206, 86, 0.2)',
            borderColor: 'rgba(255, 206, 86, 1)',
            borderWidth: 1
          }]
    }
      return chartData;
    })
    .catch(err => {
        //console.error(err);
    }).finally(()=>{
      
    });

     
}
}  

function getChartDataVisitors(timePeriod) {
    var chartData = {};
    if (timePeriod == 'eachday') {
      chartData = {
        labels: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
        
        datasets: [{
            label: 'visitors',
            data: [140, 130, 154, 120, 201, 90, 40],
            backgroundColor: 'rgba(255, 206, 86, 0.2)',
            borderColor: 'rgba(255, 206, 86, 1)',
            borderWidth: 1
          }]
      };
      return chartData;
    } 
    else if (timePeriod == 'weekday') {
      chartData = {
        labels: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri'],
        datasets: [{
            label: 'visitors',
            data: [140, 130, 154, 120, 201],
            backgroundColor: 'rgba(255, 206, 86, 0.2)',
            borderColor: 'rgba(255, 206, 86, 1)',
            borderWidth: 1
          }]
      };
      return chartData;
    
    }
    else if (timePeriod == 'weekend') {
      chartData = {
        labels: [ 'Sat', 'Sun'],
        datasets: [{
            label: 'visitors',
            data: [90, 40],
            backgroundColor: 'rgba(255, 206, 86, 0.2)',
            borderColor: 'rgba(255, 206, 86, 1)',
            borderWidth: 1
          }]
    }
    return chartData;
}
}  


// document.getElementById('class-enrollment-chart').canvas.parentNode.style.height = '500px';
// document.getElementById('class-enrollment-chart').canvas.parentNode.style.width = '400px';
