<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title><spring:message code="garage.title"></spring:message></title>
</head>
<body>
	<div>
		<form method="POST" modelAttribute="operation" action="home"> 
		<hr/>
			<div class="form-group">
			  <label for="vehicle">Plate number:</label>
			  <form:input path="vehicle.plateNumber" class="form-control"/>
			   <c:if test="${not empty msg}">
					<strong style="color: red; font-size: 16px">${msg}</strong>
			</c:if>
			</div>
			<hr/>
			  <div class="radio">
		      <label><input type="radio" checked="checked" name="type" value="1">Enter</label>
		       <div>
		    	<form:select path="vehicleType.name" id="vehicleType.name">
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
		    <hr/>
		    <div>
		    	<form:select path="parkingLevel.levelName" id="parkingLevel.levelName">
		    		<form:options items="${parkingLevels}" itemValue="levelName" itemLabel="levelName" />
		    	</form:select>
		    </div>
			 <hr/>
		     <div>
		    	<table id="parkingLotTable" border="1" class="table table-striped" >
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
			    	<table id="vehicleStatusTable" border="1" class="table table-striped" >
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
			  <button type="submit" class="btn btn-default">Submit</button>
			  <hr/>
		</form>
	 </div>
</body>
</html>