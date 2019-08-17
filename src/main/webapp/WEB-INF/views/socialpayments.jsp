<%@ include file="header.jsp"%>

<link rel="stylesheet" type="text/css"
	href="./resources/css/popupformmain.css">
<link rel="stylesheet" type="text/css"
	href="./resources/css/transactions.css">
<script src="./resources/css/socialpayment.js"></script>
<link rel="stylesheet" type="text/css"
	href="./resources/css/bootstrap-select.min.css">
<script src="./resources/js/bootstrap-select.min.js"></script>

<script src="./resources/datepick/pickmeup.js"></script>
<link rel="stylesheet" type="text/css"
	href="./resources/datepick/pickmeup.css">


<div class="container" style="margin-top: 90px; width: 1030px;">
	<div style="width: 200px; float: left;">
		<a class="btn" data-popup-open="popup-3" href="#"> <span
			class="glyphicon glyphicon-plus"></span> Make Payment to a Friend
		</a>
	</div>

	<div style="margin-left: 130px; width: 200px; float: left;">
		<div></div>
	</div>

	<div style="margin-left: 340px; width: 180px; float: left;">
		<input type="text" id="search-box2" name="search" size="45"
			class="form-control input-lg" placeholder="Filter By Price.."
			onkeypress='return event.charCode >= 48 && event.charCode <= 57 || event.charCode==46'>
		<div id="sugestion-box2" class="sugestion_box"></div>
	</div>

	<div style="margin-left: 780px;">
		<input type="text" id="search-box" name="search" size="45"
			class="form-control input-lg" placeholder="Filter By Note..">
		<div id="sugestion-box" class="sugestion_box"></div>
	</div>


	<hr />
	<div id="catList" class="cont">
		<c:if test="${!empty transactionsView}">
			<!-- 	<div class="row tr" transId="${t.id}" trans-catid="${t.categoryId }" trans-date="${t.date }" trans-price="${t.price}" trans-color="${t.color}"></div>   -->
			<table style="width: 1060; margin-left: -13px;">
				<c:forEach items="${transactionsView}" var="tv">
					<tr class="row tr" transId="${tv.transactionId}"
						trans-date="${tv.date }" trans-price="${tv.price}"
						trans-note="${tv.note }">
						<td style="padding-left: 30px;"><img
							src="./category/image/${tv.image }"
							style="width: 40px; float: left; border-radius: 10px;" />
							<p style="font-size: 16px; margin-top: 10px; font-weight: bold;">
								${tv.categoryName }</p></td>
						<td>
							<p class="par2" style='margin-left: 12px;'>${tv.date }</p>
						</td>
						<td style="text-align: center;">
							<p class="par2" style='font-style: italic; font-size: 19px;'>${tv.note }</p>
						</td>
						<td style="text-align: right;">
							<p class="par2" style='color:${tv.color}'>${tv.price }</p>
						</td>
						<td></td>
						<td style="padding-right: 30px;"></td>
					</tr>
				</c:forEach>
			</table>

		</c:if>
	</div>
</div>
<div style="height: 70px;"></div>

<!--  ==================================================== CREATE NEW PAYMENT POP-UP=================================    -->
<div class="popup" data-popup="popup-3" id="popupform">
	<div class="popup-inner">
		<a class="popup-close" data-popup-close="popup-3" href="#">x</a>
		<div class="row">
			<form id="createSocialPayment" action="" method="post"
				enctype="multipart/form-data">
				<table id="transList">
					<tr>
						<td><select name="selectpicker" class="selectpicker"
							title="Select a Friend" data-width="330px">
								<optgroup label="All Users">
									<c:if test="${!empty userList }">
										<c:forEach items="${userList}" var="u">
											<option value="${u.id }"
												data-content="<div style='width:40px; float :left;'>
								                    <img style='width:50px;border-radius:7px;' src='./user/image/${u.photo }' /></div>
								                    <div style='width:200px; float :left;margin-left:20px;'>
								                    <p class='par'>${u.firstName} ${u.lastName}</p></div>">Club</option>
										</c:forEach>
									</c:if>
								</optgroup>
						</select></td>

						<td><input name="datepicker" id="datepicker" type="text"
							value="2019-08-17" style="height: 52px; width: 200px;"
							class="form-control input-lg" /></td>

						<td><input type="text" name="note"
							class="form-control input-lg" placeholder="Enter Some Note.."
							required style="height: 52px; width: 350px;" /></td>

						<td><input type="text" name="price"
							class="form-control input-lg" placeholder="  Price" required
							onkeypress='return event.charCode >= 48 && event.charCode <= 57 || event.charCode==46'
							style="height: 52px; width: 150px;" /></td>

						<td><input type="submit" value="Send Money"
							class="submit btn" style="padding: 15px; font-size: 18px;" /></td>
					</tr>
				</table>
			</form>
		</div>

	</div>
</div>

</body>
</html>
