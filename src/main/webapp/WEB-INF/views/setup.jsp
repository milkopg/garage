<%@include file="master.jsp"%>

<html>

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
				<div class="divider"></div>
				<br>
				<div>
					<label><spring:message code="vehicleType.add"></spring:message></label>
					<form:input path="vehicleType.name" class="form-control"/>
				 	<button type="submit" class="btn btn-default"><spring:message code="vehicleType.button.add"></spring:message></button>
				</div>
				<br>
		    </div>
		</form>
		<form method="POST" action="removeVehicleType">
			<br>
				<div>
					<label><spring:message code="vehicleType.remove"></spring:message></label>
					<form:select path="vehicleType.name" id="vehicleType.name">
		    			<form:options items="${vehicleTypes}" itemValue="name" itemLabel="name"/>
		    		</form:select>
		    		<button type="submit" class="btn btn-default"><spring:message code="vehicleType.button.remove"></spring:message></button>
		    		<c:if test="${not empty msg}">
						<strong style="color: red; font-size: 16px">${msg}</strong>
					</c:if>
				</div>
		</form>
				<hr/>
		    
</body>
</html>