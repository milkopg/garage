<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="taglibs.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><spring:message code="garage.setup.title"></spring:message></title>
</head>
<body>
		<form method="POST" action="addVehicleType"> 
			<hr/>
			<div>
		    	<table id="vehicleTypeTable" border="1" class="table table-striped" >
				  <tr>
				    <th> <spring:message code="vehicleType.id"></spring:message></th>
				    <th> <spring:message code="vehicleType.name"></spring:message></th> 
				 </tr>
				 <c:forEach var="vehicleType" varStatus="status" items="${vehicleTypes}">
				  <tr>
				    <td>${vehicleType.id}</td>
				    <td>${vehicleType.name}</td> 
				  </tr>
				  </c:forEach>
				</table>
				<hr/>
				<div>
					<label><spring:message code="vehicleType.add"></spring:message></label>
					<form:input path="vehicleType.name" class="form-control"/>
						<%-- <form:button></form:button> --%>
				 	<button type="submit" class="btn btn-default">Submit</button>
				</div>
			
				<br>
		    </div>
		</form>
		    
</body>
</html>