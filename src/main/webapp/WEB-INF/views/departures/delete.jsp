<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

<!DOCTYPE html>
<html>
<head>
<title>Eliminar Partida</title>
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css">

<style>
.error {
	color: red;
	font-size: 0.9em;
	font-weight: bold;
}
</style>
</head>

<body>

	
	<div class="container">
		<div class="row">
			<div class="col-md-6 col-md-offset-3">
				<br>
			<br>
			<br>
				<a class="btn btn-primary" href="<c:url value="/departures/list.html"/>">Listado</a>
				<br>
				<h1>Eliminar Partida</h1>
				<form:form method="post" modelAttribute="id">
					<form:errors class="error" />

					<h3>Seguro quiere eliminar el Partida de id ${departureId}?</h3>
					<button type="submit" class="btn btn-primary">Eliminar Partida</button>
				</form:form>
			</div>
		</div>
		
	</div>
	
	<script src="//code.jquery.com/jquery-2.1.1.min.js"></script>
	<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>

</body>
</html>
