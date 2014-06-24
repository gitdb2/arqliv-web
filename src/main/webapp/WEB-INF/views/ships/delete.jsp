<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

<html>
<head>
<title>Eliminar Barco</title>
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
				<h1>Eliminar Barco</h1>

				<form:form method="post" modelAttribute="id">
					<h3>Seguro quiere eliminar el Barco de id ${shipId}?</h3>
					<input type="submit" value="Eliminar Barco" />
				</form:form>
			</div>
		</div>
	</div>
	<script src="//code.jquery.com/jquery-2.1.1.min.js"></script>
	<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
</body>
</html>
