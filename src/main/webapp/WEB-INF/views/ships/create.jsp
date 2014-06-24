<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

<html>
<head>
<title>Alta de Barco</title>
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
			<div class="col-lg-12 ">
				<br> <br> <br> <a class="btn btn-default" href="<c:url value="/ships/menu.html"/>">Menu</a> <br>
				<br>
				<h1>Alta de Barco</h1>

				<form:form method="post" modelAttribute="ship">

					<label for="nameInput">Nombre: </label>
					<form:input path="name" id="nameInput"></form:input>
					<form:errors path="name" cssclass="error"></form:errors>
					<br />

					<label for="flagInput">Bandera: </label>
					<form:input path="flag" id="flagInput"></form:input>
					<form:errors path="flag" cssclass="error"></form:errors>
					<br />

					<label for="codeInput">Código: </label>
					<form:input path="code" id="codeInput"></form:input>
					<form:errors path="code" cssclass="error"></form:errors>
					<br />

					<label for="manufactoringYearInput">Año de fabricación: </label>
					<form:input path="manufactoringYear" id="manufactoringYearInput"></form:input>
					<form:errors path="manufactoringYear" cssclass="error"></form:errors>
					<br />

					<label for="crewQuantityInput">Cantidad de tripulantes: </label>
					<form:input path="crewQuantity" id="crewQuantityInput"></form:input>
					<form:errors path="crewQuantity" cssclass="error"></form:errors>
					<br />

					<label for="capacityInput">Capacidad: </label>
					<form:input path="capacity" id="capacityInput"></form:input>
					<form:errors path="capacity" cssclass="error"></form:errors>
					<br />

					<input type="submit" value="Crear Barco" />

				</form:form>

			</div>
		</div>
	</div>

	<script src="//code.jquery.com/jquery-2.1.1.min.js"></script>
	<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
</body>
</html>
