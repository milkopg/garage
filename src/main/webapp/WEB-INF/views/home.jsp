
<%@include file="master.jsp"%>
<html>
<tag:header>
	<title><spring:message code="garage.home.title"></spring:message></title>
</tag:header>
<tag:body>
	<div class="container">
		<c:if test="${not empty info}">
			<strong style="color: blue; font-size: 16px">${info}</strong>
		</c:if>
		<form method="POST" modelAttribute="operation" action="home"> 
		<hr/>
			<div class="form-group">
			  <label for="vehicle" class="font-italic"><spring:message code="vehicle.plateNumber"></spring:message></label>
			  <form:input path="vehicle.plateNumber" class="form-control table-nonfluid"/>
			   <c:if test="${not empty msg}">
					<strong style="color: red; font-size: 16px">${msg}</strong>
				</c:if>
			</div>
			<br/>
			<div>
				<label for="vehicle" class="font-italic"><spring:message code="operation"></spring:message></label>
				</div>
			<div class="radio">
		      <label><input type="radio" checked="checked" name="type" value="1" >Enter</label>
		       <div>
		    	<form:select path="vehicleType.name" id="vehicleType.name" class="custom-select">
		    		<form:options items="${vehicleTypes}" itemValue="name" itemLabel="name"/>
		    	</form:select>
		    	</div>
		    </div>
		    <div class="radio">
		      <label><input type="radio" name="type" value="2" >Exit</label>
		    </div>
		    <div class="radio">
		      <label><input type="radio" name="type" value="3">Status</label>
		    </div>
		    <br>
		    <div>
		    	 <label for="name" class="font-italic"><spring:message code="parkingLevel.name"></spring:message></label>
		    </div>
		    <div>
		    	<form:select path="parkingLevel.levelName" id="parkingLevel.levelName" class="custom-select">
		    		<form:options items="${parkingLevels}" itemValue="levelName" itemLabel="levelName" />
		    	</form:select>
		    </div>
		    <br>
		    <div>
		     	<button type="submit" class="btn btn-default" ><spring:message code="button.submit"></spring:message></button>
		     </div>
		    <br>
		     <div>
		     <hr>
		     <label for="name" class="font-italic"><spring:message code="parking.status"></spring:message></label>
		    	<table id="parkingLotTable" border="1" class="table table-striped " >
				  <tr>
				    <th> <spring:message code="parkingLevel.name"></spring:message></th>
				    <th> <spring:message code="parkingLevel.capacity"></spring:message></th> 
				    <th> <spring:message code="parkingLevel.free"></spring:message></th>
				    <th> <spring:message code="parkingLevel.used"></spring:message></th>
				 </tr>
				 <c:forEach var="garage" varStatus="status" items="${garageStatus}">
				  <tr>
				    <td>${garage.levelName}</td>
				    <td>${garage.capacity}</td> 
				    <td>${garage.free}</td> 
				    <td>${garage.used}</td> 
				  </tr>
				  </c:forEach>
				</table>
				<br>
		    </div>
		    <c:if test="${not empty operations}"> 
			 <hr/>
		   		<div>
			    	<table id="vehicleStatusTable" border="1" class="table table-bordered table-nonfluid" >
					  <tr>
					    <th> <spring:message code="vehicle.plateNumber"></spring:message></th>
					    <th> <spring:message code="parkingLevel.name"></spring:message></th> 
					    <th> <spring:message code="parkingLot.name"></spring:message></th>
					    <th> <spring:message code="operation.timeEnter"></spring:message></th>
					    <th> <spring:message code="operation.timeExit"></spring:message></th>
					 </tr>
					 <c:forEach var="operation" varStatus="status" items="${operations}">
					  <tr>
					    <td>${operation.vehicle.plateNumber}</td>
					    <td>${operation.parkingLot.parkingLevel.levelName}</td> 
					    <td>${operation.parkingLot.name}</td> 
					    <td>${operation.timeEnter}</td> 
					     <td>${operation.timeExit}</td> 
					  </tr>
					  </c:forEach>
					</table>
					<br>
			    </div>
		    </c:if>		 
			  <hr/>
		</form>
	 </div>
	<tag:left></tag:left>
	<tag:right></tag:right>
</tag:body>
	<tag:footer></tag:footer>
</html>