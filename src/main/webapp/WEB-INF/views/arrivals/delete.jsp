<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<title>Eliminar Arribo</title>
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css">

<style>
.error {
	color: red;
	font-size: 0.9em;
	font-weight: bold;
}
</style>
</head>

<body>

	<h1>Eliminar Arribo</h1>

	<form:form method="post" modelAttribute="id">
		<form:errors path="error" />
	
		<h3>Seguro quiere eliminar el Arribo de id ${arrivalId}?</h3>
		<button type="submit" class="btn btn-primary">Eliminar Arribo</button>
	</form:form>
	 
	
	<script src="//code.jquery.com/jquery-2.1.1.min.js"></script>
	<script
		src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
</body>
</body>
</html>
