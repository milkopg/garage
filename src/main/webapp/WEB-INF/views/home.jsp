<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 <%-- <%@include file="taglibs.jsp"%> --%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="<c:url value='/static/css/bootstrap.css' />" rel="stylesheet"></link>
<link href="<c:url value='/static/css/styles.css' />" rel="stylesheet"></link>
<title>Insert title here</title>
</head>
<body>
	<div>
		<form method="POST" modelAttribute="operation" action="process"> 
		<hr/>
			<div class="form-group">
			  <label for="vehicle">Plate number:</label>
			  <input type="text" class="form-control" name="operation.vehicle.name">
			  <form:input path="vehicle.plateNumber" class="form-control"/>
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
			  <button type="submit" class="btn btn-default">Submit</button>
			  <hr/>
		</form>
	 </div>
</body>
</html>