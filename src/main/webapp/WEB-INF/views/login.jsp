<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<!DOCTYPE html>
<html>
<head>
<title>Login</title>
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css">
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
			<div class="col-md-6 col-md-offset-3 ">
			
				<h1>Login</h1>

				<form method="post"  class="form-horizontal" role="form">
					<div class="form-group">
						<label class="col-lg-4 control-label" for="user">usuario: </label>
						<div class="col-lg-8">
							<input class="form-control" name="user" id="user"></input>
						</div>
					</div>


					<div class="form-group">
						<button class="btn btn-primary  btn-block" type="submit">Login</button>
					</div>
				</form>
			</div>
		</div>
	</div>

	<script src="//code.jquery.com/jquery-2.1.1.min.js"></script>
	<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>

</body>
</html>
