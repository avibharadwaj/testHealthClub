

function showGraph(timePeriod) {
    var chartData = getChartDataClasses(timePeriod);
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

  function updateHours(timePeriod) {
    var chartData = getChartDataHours(timePeriod);
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
      chartData = {
        labels: ['6am', '9am', '12pm', '3pm', '6pm', '9pm', '12am'],
        
        datasets: [{
            label: 'Classes',
            data: [4, 3, 5, 10, 2, 7, 2],
            backgroundColor: 'rgba(255, 206, 86, 0.2)',
            borderColor: 'rgba(255, 206, 86, 1)',
            borderWidth: 1
          }, {
            label: 'Enrollment',
            data: [60, 58, 46, 24, 45, 46, 15],
            backgroundColor: 'rgba(75, 192, 192, 0.2)',
            borderColor: 'rgba(75, 192, 192, 1)',
            borderWidth: 1
          }]
      };
      return chartData;
    } 
    else if (timePeriod == 'week') {
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
      return chartData;
    
    }
}  

function getChartDataHours(timePeriod) {
    var chartData = {};
    if (timePeriod == 'day') {
      chartData = {
        labels: ['6am', '9am', '12pm', '3pm', '6pm', '9pm', '12am'],
        
        datasets: [{
            label: 'members',
            data: [14, 23, 15, 8, 32, 27, 2],
            backgroundColor: 'rgba(255, 206, 86, 0.2)',
            borderColor: 'rgba(255, 206, 86, 1)',
            borderWidth: 1
          }]
      };
      return chartData;
    } 
    else if (timePeriod == 'week') {
      chartData = {
        labels: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
        datasets: [{
            label: 'members',
            data: [100, 98, 102, 90, 120, 80, 60],
            backgroundColor: 'rgba(255, 206, 86, 0.2)',
            borderColor: 'rgba(255, 206, 86, 1)',
            borderWidth: 1
          }]
      };
      return chartData;
    
    }
    else if (timePeriod == 'month') {
      chartData = {
        labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
        datasets: [{
            label: 'members',
            data: [412, 350, 380, 400, 370, 490, 510, 413, 420, 399, 350, 270],
            backgroundColor: 'rgba(255, 206, 86, 0.2)',
            borderColor: 'rgba(255, 206, 86, 1)',
            borderWidth: 1
          }]
    }
    return chartData;
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
