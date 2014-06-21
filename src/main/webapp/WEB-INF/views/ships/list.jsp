<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html data-ng-app="">
<head>
	<title>Listado de Barcos</title>
	<link rel="stylesheet"	href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
	<link rel="stylesheet"	href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css">
</head>
<body>
	<h1>Listado de Barcos</h1>
	<c:if test="${not empty ships}">
	    <table border="1">
	   	    <tr>
	 	    	    <th>Id</th>
	                <th>Capacidad</th>
	                <th>Codigo</th>
	                <th>Cantidad de tripulantes</th>
	                <th>Bandera</th>
	                <th>Año de fabricación</th>
	                <th>Nombre</th>
	 	    </tr>
	        <c:forEach var="ship" items="${ships}">
	            <tr>
	                <td>${ship.id}</td>
	                <td>${ship.capacity}</td>
	                <td>${ship.code}</td>
	                <td>${ship.crewQuantity}</td>
	                <td>${ship.flag}</td>
	                <td>${ship.manufactoringYear}</td>
	                <td>${ship.name}</td>
	            </tr>
	        </c:forEach>
	    </table>
	</c:if>
	
	<c:if test="${empty ships}">
		<p>No hay Barcos en el sistema</p>
	</c:if>
	
	<script	src="//code.jquery.com/jquery-2.1.1.min.js"></script>
	<script	src="//ajax.googleapis.com/ajax/libs/angularjs/1.2.17/angular.min.js"></script>
	<script	src="//ajax.googleapis.com/ajax/libs/angularjs/1.2.17/angular-resource.min.js"></script>
	<script	src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
</body>
</html>
