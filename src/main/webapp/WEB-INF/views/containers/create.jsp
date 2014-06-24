<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

<html>
<head>
<title>Alta de Contenedor</title>
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
				<br> <br> <br> <a class="btn btn-primary" href="<c:url value="/containers/menu.html"/>">Menu</a> <br>
				<h1>Alta de Contenedor</h1>

				<form:form method="post" modelAttribute="container">

					<label for="codeInput">Código: </label>
					<form:input path="code" id="codeInput"></form:input>
					<form:errors path="code" cssclass="error"></form:errors>
					<br />

					<label for="brandInput">Marca: </label>
					<form:input path="brand" id="brandInput"></form:input>
					<form:errors path="brand" cssclass="error"></form:errors>
					<br />

					<label for="modelInput">Modelo: </label>
					<form:input path="model" id="modelInput"></form:input>
					<form:errors path="model" cssclass="error"></form:errors>
					<br />

					<label for="capacityInput">Capacidad: </label>
					<form:input path="capacity" id="capacityInput"></form:input>
					<form:errors path="capacity" cssclass="error"></form:errors>
					<br />

					<input type="submit" value="Crear Contenedor" />

				</form:form>
			</div>
		</div>
	</div>
	<script src="//code.jquery.com/jquery-2.1.1.min.js"></script>
	<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
</body>
</html>
