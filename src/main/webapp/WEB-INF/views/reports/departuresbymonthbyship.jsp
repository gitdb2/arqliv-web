<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html data-ng-app="">
<head>
<title>Partidas por mes por Barco</title>
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css">
<style>
.inline li {
	display: inline;
}
</style>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-lg-12 ">
				<br> <br> <br> <a class="btn btn-default" href="<c:url value="/reports/menu.html"/>">Menu</a> <br>
				<br>
				<h1>Partidas por mes por Barco</h1>

				<form:form method="get" modelAttribute="reportsWrapper">

					<ul class="inline">
						<li><label for="monthInput">Mes:</label> <form:select path="month" id="month">
								<form:options items="${months}" />
							</form:select></li>
						<li><label for="shipInput">Barco:</label> <form:select path="ship" id="ship">
								<form:options items="${ships}" />
							</form:select></li>
						<li>
							<button type="submit" class="btn btn-primary">Obtener datos</button>
						</li>
					</ul>
					<br />

					<c:if test="${not empty reportsWrapper.departures}">
						<table class="table">
							<tr>
								<th>Id</th>
								<th>Fecha de partida</th>
								<th>Id de barco</th>
								<th>Destino del barco</th>
								<th>Contenedores</th>
								<th>Descripción de Contenedores</th>
								<th>Peso Transportado</th>
							</tr>
							<c:forEach var="departure" items="${reportsWrapper.departures}">
								<tr>
									<td>${departure.id}</td>
									<td>${departure.departureDate}</td>
									<td>${departure.ship.id}</td>
									<td>${departure.shipDestination}</td>
									<td>${departure.containers}</td>
									<td>${departure.containersDescriptions}</td>
									<td>${departure.shipTransportedWeightThatDay}</td>
								</tr>
							</c:forEach>
						</table>

						<a
							href="<c:url value="/reports/getPdfDeparturesByMonthByShip.html"/>?month=${reportsWrapper.month}&ship=${reportsWrapper.ship}">Exportar
							listado a PDF</a>

					</c:if>

					<c:if test="${empty reportsWrapper.departures}">
						<p>No se encontraron registros</p>
					</c:if>

				</form:form>
			</div>
		</div>
	</div>
	<script src="//code.jquery.com/jquery-2.1.1.min.js"></script>
	<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.2.17/angular.min.js"></script>
	<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.2.17/angular-resource.min.js"></script>
	<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
</body>
</html>

