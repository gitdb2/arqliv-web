<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css">
<title>Home</title>
</head>
<body>

	
	<div class="container">
		<div class="row">
			<div class="col-md-6 col-md-offset-3">
				<h1>Hola ${user}</h1><br>
				<a class="btn btn-primary btn-lg btn-block" href="<c:url value="/ships/menu.html"/>">
					Barcos</a>
				<a class="btn btn-primary btn-lg btn-block" href="<c:url value="/containers/menu.html"/>">
					Contenedores</a>
				<a class="btn btn-primary btn-lg btn-block" href="<c:url value="/arrivals/menu.html"/>">
					Arribos</a>
				<a class="btn btn-primary btn-lg btn-block" href="<c:url value="/departures/menu.html"/>">
					Partidas</a>
				<a class="btn btn-primary btn-lg btn-block" href="<c:url value="/reports/menu.html"/>">
					Reportes</a>
				<a class="btn btn-primary btn-lg btn-block" href="<c:url value="/profiling/menu.html"/>">
					Profiling</a>
			</div>
		</div>
	</div>
	<script src="//code.jquery.com/jquery-2.1.1.min.js"></script>
	<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
</body>
</html>
