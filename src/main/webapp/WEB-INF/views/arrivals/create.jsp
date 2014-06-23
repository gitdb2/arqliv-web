<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<!DOCTYPE html>
<html>
<head>
<title>Alta de Arribo</title>
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/css/datepicker.min.css">
<style>
.error {
	color: red;
	font-size: 0.9em;
	font-weight: bold;
}

.inline li {
	display: inline;
}
</style>
</head>

<body>
	<div class="container">
		<div class="row">
			<div class="col-md-10 col-md-offset-1 ">
				<h1>Alta de Arribo</h1>

				<form:form method="post" modelAttribute="arrivalModel">
					<form:errors class="error" />
					<form:hidden path="arrivalId" />

					<div>
						<label for="arrivalDate">Fecha de Arribo: </label>
						<form:input class="span2" size="8" path="arrivalDate" id="arrivalDate"></form:input>
						<form:errors path="arrivalDate" class="error"></form:errors>
					</div>

					<br />

					<label for="shipId">Id de barco: </label>
					<form:input path="shipId" id="shipId"></form:input>
					<form:errors path="shipId" class="error"></form:errors>
					<br />

					<label for="shipOrigin">Pais de Origen: </label>
					<form:input path="shipOrigin" id="shipOrigin"></form:input>
					<form:errors path="shipOrigin" class="error"></form:errors>
					<br />

					<label for="containersDescriptions">Desc. Contenedores: </label>
					<form:input path="containersDescriptions" id="containersDescriptions"></form:input>
					<form:errors path="containersDescriptions" class="error"></form:errors>
					<br />

					<label for="containers">Ids contenedores (separado por comas): </label>
					<form:input path="containers" id="containers"></form:input>
					<form:errors path="containers" class="error"></form:errors>
					<br />

					<button class="btn btn-primary" type="submit">Crear Arribo</button>
				</form:form>
			</div>
		</div>
	</div>

	<script src="//code.jquery.com/jquery-2.1.1.min.js"></script>
	<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
	<script src="//cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/js/bootstrap-datepicker.min.js"></script>

	<script>
	$(function(){
		$('#arrivalDate').datepicker({
			format: 'yyyymmdd'
		});	
	});
	</script>

</body>
</html>
