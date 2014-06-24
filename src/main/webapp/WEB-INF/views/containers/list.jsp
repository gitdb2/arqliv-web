<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

<!DOCTYPE html>
<html data-ng-app="">
<head>
<title>Listado de Contenedores</title>
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css">
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-md-6 col-md-offset-3">
				<br> <br> <br> <a class="btn btn-primary" href="<c:url value="/containers/menu.html"/>">Menu</a> <br>
				<h1>Listado de Contenedores</h1>
				<c:if test="${not empty containers}">
					<div class="table-responsive">
						<table class="table table-condensed table-hover table-striped">
							<tr>
								<th>Id</th>
								<th>Codigo</th>
								<th>Marca</th>
								<th>Modelo</th>
								<th>Capacidad</th>
								<th>Acciones</th>
							</tr>
							<c:forEach var="container" items="${containers}">
								<tr>
									<td>${container.id}</td>
									<td>${container.code}</td>
									<td>${container.brand}</td>
									<td>${container.model}</td>
									<td>${container.capacity}</td>
									<td style="display: inline-block"><a href="edit.html?id=${container.id}">Modificar</a> | <a
										href="delete.html?id=${container.id}">Eliminar</a></td>
								</tr>
							</c:forEach>
						</table>
					</div>
				</c:if>

				<c:if test="${empty containers}">
					<p>No hay Contenedores en el sistema</p>
				</c:if>

				<br /> <a href="${pageContext.servletContext.contextPath}/containers/getPdfList.html">Exportar listado a PDF</a>
			</div>
		</div>
	</div>
	<script src="//code.jquery.com/jquery-2.1.1.min.js"></script>
	<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.2.17/angular.min.js"></script>
	<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.2.17/angular-resource.min.js"></script>
	<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
</body>
</html>

