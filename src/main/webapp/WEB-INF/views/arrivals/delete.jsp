<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

<!DOCTYPE html>
<html>
<head>
<title>Eliminar Arribo</title>
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
				<h1>Eliminar Arribo</h1>
				<form:form method="post" modelAttribute="id">
					<form:errors class="error" />

					<h3>Seguro quiere eliminar el Arribo de id ${arrivalId}?</h3>
					<button type="submit" class="btn btn-primary">Eliminar Arribo</button>
				</form:form>
			</div>
		</div>
		<br>
		<br>
		<div class="row">
			<div class="col-md-6 col-md-offset-3">
				<a class="btn btn-primary" href="<c:url value="/arrivals/list.html"/>">Volver</a>
			</div>
		</div>
	</div>
	
	<script src="//code.jquery.com/jquery-2.1.1.min.js"></script>
	<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>

</body>
</html>
