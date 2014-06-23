<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css">
<title>Error</title>
</head>
<body>
	
	<div class="container">
		<div class="row">
			<div class="col-md-6 col-md-offset-3">
				<h1>Ha ocurrido un error...</h1>
				<h2>${error.message}</h2>
				<a class="btn btn-default btn-lg btn-block" href="<c:url value="/${error.entity}/menu.html"/>">
					Volver al menu</a>
				
			</div>
		</div>
	</div>
	<script src="//code.jquery.com/jquery-2.1.1.min.js"></script>
	<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
</body>
</html>