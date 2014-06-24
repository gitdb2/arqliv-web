<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

<html>
<head>
<title>Modificación de Barco</title>
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
				<h1>Modificación de Barco</h1>

				<form:form method="post" modelAttribute="shipEditWrapper">

					<label for="arrivalDateInput">Fecha de arribo (yyyyMMdd): </label>
					<form:input path="arrivalDate" id="arrivalDateInput" value="${arrivalDate}"></form:input>
					<form:errors path="arrivalDate" cssclass="error"></form:errors>
					<br />

					<form:input path="ship.id" id="idInput" value="${ship.id}" type="hidden"></form:input>
					<form:input path="ship.version" id="versionInput" value="${ship.version}" type="hidden"></form:input>

					<label for="nameInput">Nombre: </label>
					<form:input path="ship.name" id="nameInput" value="${ship.name}"></form:input>
					<form:errors path="ship.name" cssclass="error"></form:errors>
					<br />

					<label for="flagInput">Bandera: </label>
					<form:input path="ship.flag" id="flagInput" value="${ship.flag}"></form:input>
					<form:errors path="ship.flag" cssclass="error"></form:errors>
					<br />

					<label for="codeInput">Código: </label>
					<form:input path="ship.code" id="codeInput" value="${ship.code}"></form:input>
					<form:errors path="ship.code" cssclass="error"></form:errors>
					<br />

					<label for="manufactoringYearInput">Año de fabricación: </label>
					<form:input path="ship.manufactoringYear" id="manufactoringYearInput" value="${ship.manufactoringYear}"></form:input>
					<form:errors path="ship.manufactoringYear" cssclass="error"></form:errors>
					<br />

					<label for="crewQuantityInput">Cantidad de tripulantes: </label>
					<form:input path="ship.crewQuantity" id="crewQuantityInput" value="${ship.crewQuantity}"></form:input>
					<form:errors path="ship.crewQuantity" cssclass="error"></form:errors>
					<br />

					<label for="capacityInput">Capacidad: </label>
					<form:input path="ship.capacity" id="capacityInput" value="${ship.capacity}"></form:input>
					<form:errors path="ship.capacity" cssclass="error"></form:errors>
					<br />

					<input type="submit" value="Editar Barco" />

				</form:form>
			</div>
		</div>
	</div>
	<script src="//code.jquery.com/jquery-2.1.1.min.js"></script>
	<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
</body>
</html>
