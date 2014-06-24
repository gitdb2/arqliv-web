<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html data-ng-app="">
<head>
<title>Listado de Barcos</title>
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css">
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-lg-12 ">
				<br> <br> <br> <a class="btn btn-default" href="<c:url value="/ships/menu.html"/>">Menu</a> <br>
				<br>
				<h1>Listado de Barcos</h1>
				<c:if test="${not empty ships}">
					<div class="table-responsive">
						<table class="table table-condensed table-hover table-striped">
							<tr>
								<th>Id</th>
								<th>Capacidad</th>
								<th>Codigo</th>
								<th>Cantidad de tripulantes</th>
								<th>Bandera</th>
								<th>Año de fabricación</th>
								<th>Nombre</th>
								<th>Acciones</th>
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
									<td style="display: inline-block"><a href="edit.html?id=${ship.id}">Modificar</a> | <a
										href="delete.html?id=${ship.id}">Eliminar</a></td>
								</tr>
							</c:forEach>
						</table>
					</div>
				</c:if>

				<c:if test="${empty ships}">
					<p>No hay Barcos en el sistema</p>
				</c:if>

				<br /> <a href="${pageContext.servletContext.contextPath}/ships/getPdfList.html">Exportar listado a PDF</a>
			</div>
		</div>
	</div>
	<script src="//code.jquery.com/jquery-2.1.1.min.js"></script>
	<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.2.17/angular.min.js"></script>
	<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.2.17/angular-resource.min.js"></script>
	<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
</body>
</html>
