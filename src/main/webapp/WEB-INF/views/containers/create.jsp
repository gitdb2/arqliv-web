<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
 
<html> 
<head> 
	<title>Alta de Contenedor</title> 
	<style>
    	.error { color: red; font-size: 0.9em; font-weight: bold; }
	</style> 
</head> 
 
<body> 
	
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
</body> 
</html>
