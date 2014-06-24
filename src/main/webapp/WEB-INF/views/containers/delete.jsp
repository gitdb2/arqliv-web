<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css">
<title>Eliminar Contenedor</title>
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
				<br> <br> <br> <a class="btn btn-primary" href="<c:url value="/containers/list.html"/>">Listado</a>
				<h1>Eliminar Contenedor</h1>

				<form:form method="post" modelAttribute="id">
					<h3>Seguro quiere eliminar el Contenedor de id ${contId}?</h3>
					<input type="submit" value="Eliminar Contenedor" />
				</form:form>
			</div>
		</div>
	</div>
	<script src="//code.jquery.com/jquery-2.1.1.min.js"></script>
	<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
</body>
</html>
