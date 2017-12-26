<%@include file="master.jsp"%>

<html>

<body>	
	<div class="container">
		<form method="POST" action="addVehicleType"> 
			<hr/>
			<div>
		    	<table id="vehicleTypeTable" border="1" class="table table-striped">
			    	<thead>
						  <tr>
						     <th scope="row"> <spring:message code="vehicleType.id"></spring:message></th>
						     <th scope="row"> <spring:message code="vehicleType.name"></spring:message></th> 
						 </tr>
					</thead>
						 <tbody>
						 <c:forEach var="vehicleType" varStatus="status" items="${vehicleTypes}">
						  <tr>
						    <td>${vehicleType.id}</td>
						    <td>${vehicleType.name}</td> 
						  </tr>
						  </c:forEach>
						 </tbody>
						 
					  
				</table>
				<div class="divider"></div>
				<br>
				<div>
					<label><spring:message code="vehicleType.add"></spring:message></label>
					<form:input path="vehicleType.name" />
				 	<button type="submit" class="btn btn-default"><spring:message code="vehicleType.button.add"></spring:message></button>
				</div>
				<br>
		    </div>
		    <div>
	    		<c:if test="${not empty msgVehicleTypeAdd}">
					<strong style="color: red; font-size: 16px">${msgVehicleTypeAdd}</strong>
				</c:if>
		    	
		    </div>
		</form>
		<form method="POST" action="removeVehicleType">
			<br>
				<div>
					<label><spring:message code="vehicleType.remove"></spring:message></label>
					<form:select path="vehicleType.name" id="vehicleType.name" class="custom-select">
		    			<form:options items="${vehicleTypes}" itemValue="name" itemLabel="name"/>
		    		</form:select>
		    		<button type="submit" class="btn btn-default"><spring:message code="vehicleType.button.remove"></spring:message></button>
		    		<c:if test="${not empty msgVehicleVypeRemove}">
						<strong style="color: red; font-size: 16px">${msgVehicleVypeRemove}</strong>
					</c:if>
				</div>
		</form>
	<hr/>
		    
		 <form method="POST" action="addParkingLevel"> 
			<hr/>
			<div>
		    	<table id="viewParkingLevelTable" border="1" class="table table-striped" >
				  <tr>
				    <th> <spring:message code="parkingLevel.id"></spring:message></th>
				    <th> <spring:message code="parkingLevel.name"></spring:message></th> 
				     <th> <spring:message code="parkingLevel.capacity"></spring:message></th> 
				 </tr>
				 <c:forEach var="parkingLevel" varStatus="status" items="${parkingLevels}">
				  <tr>
				    <td>${parkingLevel.id}</td>
				    <td>${parkingLevel.levelName}</td> 
				     <td>${parkingLevel.capacity}</td> 
				  </tr>
				  </c:forEach>
				</table>
				<div class="divider"></div>
				<br>
				<div>
				<table id="addParkingLevelTable" class="table table-nonfluid">
					<tr>
						<th><label><spring:message code="parkingLevel.add"></spring:message></label></th>
						<th><form:input path="parkingLevel.levelName" class="form-control"/></th>
					</tr>
					<tr>
						<th><label><spring:message code="parkingLevel.capacity"></spring:message></label></th>
						<th><form:input path="parkingLevel.capacity" class="form-control" /></th>
					</tr>
					<tr>
						<th><label><spring:message code="parkingLevel.startNumber"></spring:message></label></th>
						<th><form:input path="parkingLevel.startNumber" class="form-control"/></th>
					</tr>
					<tr>
						<th></th>
						<th><button type="submit" class="btn btn-default" style="float:right;"><spring:message code="parkingLevel.button.add"></spring:message></button></th>
						<c:if test="${not empty msgParkingLevelAdd}">
						<strong style="color: red; font-size: 16px">${msgParkingLevelAdd}</strong>
					</c:if>
					</tr>
				</table>
				</div>
				<br>
		    </div>
		</form>
		<form method="POST" action="removeParkingLevel">
			<br>
				<div>
					<table id="removeParkingLevelTable" class="table table-nonfluid">
					<tr>
						<th><label><spring:message code="parkingLevel.remove"></spring:message></label></th>
						<th>
							<form:select path="parkingLevel.levelName" id="parkingLevel.levelName" class="custom-select">
		    				<form:options items="${parkingLevels}" itemValue="levelName" itemLabel="levelName"/>
		    				</form:select>
		    			</th>
					</tr>
					<tr>
						<th></th>
						<th><button type="submit" class="btn btn-default"><spring:message code="parkingLevel.button.remove"></spring:message></button></th>
					</tr>
					<tr>
						<th></th>
						<th>
							<c:if test="${not empty msgParkingLevelRemove}">
								<strong style="color: red; font-size: 16px">${msgParkingLevelRemove}</strong>
							</c:if>
					    </th>
					</tr>
				</table>
				</div>
		</form>
	<hr/>
	</div>
</body>
</html>