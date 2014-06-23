<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<title>Listado de Arrivos</title>
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css">

<style>
.arrivalContainer li {
	display: inline;
}
</style>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-md-10 col-md-offset-1 ">
			<br>
			<br>
			<br>
				<a class="btn btn-default" href="<c:url value="/arrivals/menu.html"/>">Volver</a>
				<br>
			
				<h1>Listado de Arrivos</h1>
				<c:if test="${not empty arrivals}">
					<table class="table">
						<tr>
							<th>Id</th>
							<th>Fecha de arribo</th>
							<th>Id de barco</th>
							<th>Pais de Origen</th>
							<th>Ids contenedores</th>
							<th>Desc. Contenedores</th>
							<th>Acciones</th>
						</tr>
						<c:forEach var="arrival" items="${arrivals}">
							<tr>
								<td>${arrival.id}</td>
								<td><fmt:formatDate pattern="dd/MM/yyyy" value="${arrival.arrivalDate}" /></td>
								<td>${arrival.ship.id}</td>
								<td>${arrival.shipOrigin}</td>
								<td>
									<ul>
										<c:forEach var="container" items="${arrival.containers}">
											<li>
												<ul class="arrivalContainer">
													<li>${container.id}</li>
													<li>${container.code}</li>
													<li>${container.brand}</li>
													<li>${container.model}</li>
													<li>${container.capacity}</li>
												</ul>
											</li>
										</c:forEach>
									</ul>
								</td>
								<td>${arrival.containersDescriptions}</td>
								<td style="display: inline-block"><a
									href="edit.html?id=${arrival.id}">Modificar</a> | <a
									href="delete.html?id=${arrival.id}">Eliminar</a></td>
							</tr>
						</c:forEach>
					</table>
				</c:if>

				<c:if test="${empty arrivals}">
					<p>No hay Arrivos en el sistema</p>
				</c:if>
			</div>
		</div>
	</div>
	<script src="//code.jquery.com/jquery-2.1.1.min.js"></script>
	<script
		src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
</body>
</html>
