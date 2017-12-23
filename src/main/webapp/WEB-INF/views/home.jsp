<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 <%-- <%@include file="taglibs.jsp"%> --%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

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
		    <hr/>
			  <button type="submit" class="btn btn-default">Submit</button>
			  <hr/>
		</form>
	 </div>
</body>
</html>