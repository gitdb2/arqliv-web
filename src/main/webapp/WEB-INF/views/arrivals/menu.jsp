<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css">
<title>Arribos</title>
</head>
<body>
	<h1>Hola ${user}</h1>
	
	<div class="container">
		<div class="row">
			<div class="col-md-6 col-md-offset-3">
				<a class="btn btn-primary btn-lg btn-block" href="<c:url value="/arrivals/list.html"/>">
					Listado</a>
				<a class="btn btn-primary btn-lg btn-block" href="<c:url value="/arrivals/create.html"/>">
					Alta</a>
			</div>
		</div>
	</div>

	<script src="//code.jquery.com/jquery-2.1.1.min.js"></script>
	<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
</body>
</html>
