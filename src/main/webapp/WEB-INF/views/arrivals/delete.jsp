<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
 
<html> 
<head> 
	<title>Eliminar Contenedor</title> 
	<style>
    	.error { color: red; font-size: 0.9em; font-weight: bold; }
	</style> 
</head> 
 
<body> 

	<h1>Eliminar Contenedor</h1>
	
	<form:form method="post" modelAttribute="id">
		<h3>Seguro quiere eliminar el Contenedor de id ${contId}? </h3>
		<input type="submit" value="Eliminar Contenedor" />
	</form:form>

</body> 
</html>
