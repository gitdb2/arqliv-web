<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html data-ng-app="">
<head>
	<title>Arribos por mes por Barco</title>
	<link rel="stylesheet"	href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
	<link rel="stylesheet"	href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css">
	<style>.inline li{display:inline;}</style> 
</head>
<body>
	<h1>Arribos por mes por Barco</h1>
	
	<form:form method="get" modelAttribute="reportsWrapper">
	
		<ul class="inline">
			<li>
				<label for="monthInput">Mes:</label>
				<form:select path="month" id="month">
   					<form:options items="${months}"/>
				</form:select>
			</li>
			<li>
				<label for="shipInput">Barco:</label>
				<form:select path="ship" id="ship">
   					<form:options items="${ships}"/>
				</form:select>
			</li>
			<li>
				<button type="submit" class="btn btn-primary">Obtener datos</button>
			</li>
	    </ul>
		<br />
		
		<c:if test="${not empty reportsWrapper.arrivals}">
		    <table class="table">
		   	    <tr>
	 	    	    <th>Id</th>
	                <th>Fecha de arribo</th>
	                <th>Id de barco</th>
	                <th>Pais de Origen</th>
	                <th>Contenedores</th>
	                <th>Descripción de Contenedores</th>
		 	    </tr>
		        <c:forEach var="arrival" items="${reportsWrapper.arrivals}">
		            <tr>
		                <td>${arrival.id}</td>
		                <td>${arrival.arrivalDate}</td>
		                <td>${arrival.ship.id}</td>
		                <td>${arrival.shipOrigin}</td>
		                <td>${arrival.containers}</td>
		                <td>${arrival.containersDescriptions}</td>
		            </tr>
		        </c:forEach>
		    </table>

			<a href="<c:url value="/reports/getPdfArrivalsByMonthByShip.html"/>?month=${reportsWrapper.month}&ship=${reportsWrapper.ship}">Exportar listado a PDF</a>
		    
		</c:if>
		
		<c:if test="${empty reportsWrapper.arrivals}">
			<p>No se encontraron registros</p>
		</c:if>
	
	</form:form>
	
	<script	src="//code.jquery.com/jquery-2.1.1.min.js"></script>
	<script	src="//ajax.googleapis.com/ajax/libs/angularjs/1.2.17/angular.min.js"></script>
	<script	src="//ajax.googleapis.com/ajax/libs/angularjs/1.2.17/angular-resource.min.js"></script>
	<script	src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
</body>
</html>

