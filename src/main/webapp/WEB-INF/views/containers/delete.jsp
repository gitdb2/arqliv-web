<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
 
<html> 
<head> 
	<title>Eliminar Barco</title> 
	<style>
    	.error { color: red; font-size: 0.9em; font-weight: bold; }
	</style> 
</head> 
 
<body> 

	<h1>Eliminar Barco</h1>
	
	<form:form method="post" modelAttribute="id">
		<h3>Seguro quiere eliminar el Barco de id ${shipId}? </h3>
		<input type="submit" value="Eliminar Barco" />
	</form:form>

</body> 
</html>
