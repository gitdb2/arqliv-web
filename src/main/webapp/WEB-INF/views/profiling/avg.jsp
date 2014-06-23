<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html data-ng-app="">
<head>
<title>Promedio de tiempo de servicios</title>
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
				<br> <br> <br> <a class="btn btn-default" href="<c:url value="/profiling/menu.html"/>">Menu</a> <br>


				<h1>Promedio de tiempo de servicios</h1>

				<form:form method="get" modelAttribute="profilingWrapper">

					<ul class="inline">
						<li><label for="dateInput">Fecha de consulta (yyyyMMdd): </label> <form:input path="date" id="date"
								value="${date}"></form:input></li>
						<li>
							<button type="submit" class="btn btn-primary">Obtener datos</button>
						</li>
					</ul>
					<br />

					<c:if test="${not empty profilingWrapper.profilingInfoAvg}">
						<table class="table table-condensed table-hover table-striped">
							<tr>
								<th>Servicio</th>
								<th>Tiempo promedio (nanosec)</th>
								<th>Tiempo promedio (milisec)</th>
							</tr>
							<c:forEach var="stat" items="${profilingWrapper.profilingInfoAvg}">
								<tr>
									<td>${stat.key}</td>
									<td><fmt:formatNumber type="number" maxIntegerDigits="15" value="${stat.value}" /></td>
									<td><fmt:formatNumber type="number" maxIntegerDigits="15" value="${stat.value / 1000000.0}" /></td>
								</tr>
							</c:forEach>
						</table>

						<a href="<c:url value="/profiling/getPdfAvg.html"/>?date=${profilingWrapper.date}">Exportar listado a PDF</a>

					</c:if>

					<c:if test="${empty profilingWrapper.profilingInfoAvg}">
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

