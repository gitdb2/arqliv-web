<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<title>Listado de Partidas</title>
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css">

<style>
.departureContainer {
	padding-left: 0px;
}

.departureContainer li {
	display: inline;
}
</style>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-lg-12 ">
				<br> <br> <br> <a class="btn btn-default" href="<c:url value="/departures/menu.html"/>">Menu
					Partidas</a> <br>

				<h1>Listado de Partidas</h1>
				<c:if test="${not empty departures}">
					<div class="table-responsive">
						<table class="table table-condensed table-hover table-striped">
							<tr>
								<th>Id</th>
								<th>Fecha de partida</th>
								<th>Id de barco</th>
								<th>Pais de Destino</th>
								<th>Ids contenedores</th>
								<th>Desc. Contenedores</th>
								<!-- 								<th>Capacidad Barco</th> -->
								<!-- 								<th>Peso Transportado</th> -->
								<th>Acciones</th>
							</tr>
							<c:forEach var="departure" items="${departures}">
								<tr>
									<td>${departure.id}</td>
									<td><fmt:formatDate pattern="yyyyMMdd" value="${departure.departureDate}" /></td>
									<td>${departure.ship.id}</td>
									<td>${departure.shipDestination}</td>
									<td>
										<ul style="padding-left: 0px;">
											<c:forEach var="container" items="${departure.containers}">
												<li>
													<ul class="departureContainer">
														<li>${container.id}<b>,</b></li>
														<li>${container.code}<b>,</b></li>
														<li>${container.brand}<b>,</b></li>
														<li>${container.model}<b>,</b></li>
														<li style="font-weight: bold;">${container.capacity}</li>
													</ul>
												</li>
											</c:forEach>
										</ul>
									</td>
									<td>${departure.containersDescriptions}</td>
									<%-- 									<td>${departure.shipCapacityThatDay}</td> --%>
									<%-- 									<td>${departure.shipTransportedWeightThatDay}</td> --%>
									<td><a href="edit.html?id=${departure.id}">Modificar</a> | <a href="delete.html?id=${departure.id}">Eliminar</a></td>
								</tr>
							</c:forEach>
						</table>
					</div>
				</c:if>

				<c:if test="${empty departures}">
					<p>No hay Partidas en el sistema</p>
				</c:if>
			</div>
		</div>
	</div>
	<script src="//code.jquery.com/jquery-2.1.1.min.js"></script>
	<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
</body>
</html>
