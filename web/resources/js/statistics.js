// Load the Visualization API and the corechart package.
google.charts.load('current', {'packages':['corechart']});
// Set a callback to run when the Google Visualization API is loaded.
google.charts.setOnLoadCallback(drawCharts);

// Callback that creates and populates a data table,
// instantiates the pie chart, passes in the data and
// draws it.
function drawCharts(){
    genericDraw( areaData, 'Area', 'Number of Employees', 'Jobs by Area', 'areaDiv' );
    genericDraw( yearData, 'Age', 'Number of Employees', 'Employees by age', 'ageDiv' );
    genericDraw( genderData, 'Gender', 'Number of Employees', 'Employees by Gender', 'genderDiv' );
}

function genericDraw( rawData, colName1, colName2, title, divId ) {
    // Create the data table.
    var data = new google.visualization.DataTable();
    data.addColumn('string', colName1 );
    data.addColumn('number', colName2 );
    rows = []
    var sum = 0;
    for( var area in rawData ){
       rows.push( [ area, rawData[area] ] );
       sum = sum + rawData[area];
    }
    if(sum == 0) return;
    data.addRows(rows);
    console.log(rows);
    // Set chart options
    var options = {'title':title, is3D: true,};

    // Instantiate and draw our chart, passing in some options.
    var newDiv = document.createElement('div');
    newDiv.id = divId;
    document.getElementById('chartsDiv').appendChild(newDiv);
    

    var chart = new google.visualization.PieChart(document.getElementById(divId));
    chart.draw(data, options);
}