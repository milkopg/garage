<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%-- <%@include file="taglibs.jsp"%> --%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div>
		<form method="POST" modelAttribute="operation" action="${contextPath}/process"> 
			<div class="form-group">
			  <label for="vehicle">Plate number:</label>
			  <input type="text" class="form-control" id="vehicle">
			</div>
			  <div class="radio">
		      <label><input type="radio" checked="checked" name="optradio">Enter</label>
		    </div>
		    <div class="radio">
		      <label><input type="radio" name="optradio">Exit</label>
		    </div>
		    <div>
		    	<form:select path="parkingLevel.name">
		    		<form:options items="${parkingLevels}" itemValue="id" itemLabel="name"/>
		    	</form:select>
		    </div>
			  <button type="submit" class="btn btn-default">Submit</button>
		</form>
	 </div>
</body>
</html>