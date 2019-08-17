<%@ page language="java" %>
<html>
<head>
 
 <script src="./resources/js/Chart.bundle.js"></script>
    <script src="./resources/js/utils.js"></script>
 
 
<body style="background-color: #f2f2f2">


 <div style="width: 100%">
        <canvas id="canvas"></canvas>
    </div> 
    <script>
        var color = Chart.helpers.color;
        var barChartData = {
            labels: [ <% out.println( request.getAttribute("dates") ); %> ],
            datasets: [{
                type: 'bar',
                label: 'Expence',
                backgroundColor: color(window.chartColors.red).alpha(0.2).rgbString(),
                borderColor: window.chartColors.red,
                data: [  <% out.println( request.getAttribute("expence") ); %> ]
            }, {
                type: 'line',
                label: 'Income/Expense Average',
                backgroundColor: color(window.chartColors.blue).alpha(0.2).rgbString(),
                borderColor: window.chartColors.blue,
                data: [<% out.println( request.getAttribute("avg") ); %>]
            }, {
                type: 'bar',
                label: 'Income',
                backgroundColor: color(window.chartColors.green).alpha(0.2).rgbString(),
                borderColor: window.chartColors.green,
                data: [ <% out.println( request.getAttribute("income") ); %> ]
            }]
        };

        // Define a plugin to provide data labels
        Chart.plugins.register({
            afterDatasetsDraw: function(chart, easing) {
                // To only draw at the end of animation, check for easing === 1
                var ctx = chart.ctx;

                chart.data.datasets.forEach(function (dataset, i) {
                    var meta = chart.getDatasetMeta(i);
                    if (!meta.hidden) {
                        meta.data.forEach(function(element, index) {
                            // Draw the text in black, with the specified font
                            ctx.fillStyle = 'rgb(0, 0, 0)';

                            var fontSize = 16;
                            var fontStyle = 'normal';
                            var fontFamily = 'Helvetica Neue';
                            ctx.font = Chart.helpers.fontString(fontSize, fontStyle, fontFamily);

                            // Just naively convert to string for now
                            var dataString = dataset.data[index].toString();

                            // Make sure alignment settings are correct
                            ctx.textAlign = 'center';
                            ctx.textBaseline = 'middle';

                            var padding = 5;
                            var position = element.tooltipPosition();
                            ctx.fillText(dataString, position.x, position.y - (fontSize / 2) - padding);
                        });
                    }
                });
            }
        });

        window.onload = function() {
            var ctx = document.getElementById("canvas").getContext("2d");
            window.myBar = new Chart(ctx, {
                type: 'bar',
                data: barChartData,
                options: {
                    responsive: true,
                    title: {
                        display: true,
                        text: 'Average Combo Graph'
                    },
                }
            });
        };
 
    </script>




</body>
</html>