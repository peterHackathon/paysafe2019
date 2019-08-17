<%@ include file="header.jsp"%>

<script src="./resources/js/Chart.bundle.js"></script>
<script src="./resources/js/utils.js"></script>

<div style="margin-top: 150px;"></div>

<div class="container"
	style="width: 1170px; background-color: #f2f2f2; border-radius: 30px; box-shadow: 0px 20px 25px rgba(0, 0, 0, 1);">
	<div style="width: 100%">
		<canvas id="canvas"></canvas>
	</div>
	<script>
		window.chartColors = {
			red : 'rgb(255, 0, 0)',
			cyan : 'rgb(88, 172, 250)',
			magenta : 'rgb(255, 0, 255)',
			black : 'rgb(0, 0, 0)',
			orange : 'rgb(255, 159, 64)',
			yellow : 'rgb(255, 128, 0)',
			green : 'rgb(0, 255, 0)',
			blue : 'rgb(0, 60, 255)',
			purple : 'rgb(247, 129, 243)',
			grey : 'rgb(201, 203, 207)'
		};

		var color = Chart.helpers.color;

		var scatterChartData = {
			datasets : <% out.println(request.getAttribute("data")); %>
		};

		window.onload = function() {
			var ctx = document.getElementById('canvas').getContext('2d');
			window.myScatter = Chart.Scatter(ctx, {
				data : scatterChartData,
				options : {
					title : {
						display : true,
						text : 'Users Clusters'
					},
				}
			});
			console.log(window.chartColors);
		};
	</script>
</div>


<div style="margin-top: 50px; height: 70px;"></div>

</body>
</html>