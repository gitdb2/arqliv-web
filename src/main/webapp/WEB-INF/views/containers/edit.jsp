<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
 
<html> 
<head> 
	<title>Modificación de Contenedor</title> 
	<style>
    	.error { color: red; font-size: 0.9em; font-weight: bold; }
	</style> 
</head> 
 
<body> 
	
	<h1>Modificación de Contenedor</h1>
	
	<form:form method="post" modelAttribute="container">
	
	    <form:input path="id" id="idInput" value="${id}" type="hidden"></form:input>
		<form:input path="version" id="versionInput" value="${container.version}" type="hidden"></form:input>
	
	    <label for="codeInput">Código: </label>
	    <form:input path="code" id="codeInput" value="${code}"></form:input>
	    <form:errors path="code" cssclass="error"></form:errors>
	    <br />
	    
   	    <label for="brandInput">Marca: </label>
	    <form:input path="brand" id="brandInput" value="${brand}"></form:input>
	    <form:errors path="brand" cssclass="error"></form:errors>
	    <br />
	    
	    <label for="modelInput">Modelo: </label>
	    <form:input path="model" id="modelInput" value="${model}"></form:input>
	    <form:errors path="model" cssclass="error"></form:errors>
	    <br />
	    
	    <label for="capacityInput">Capacidad: </label>
	    <form:input path="capacity" id="capacityInput" value="${capacity}"></form:input>
	    <form:errors path="capacity" cssclass="error"></form:errors>
	    <br />
	    
	    <input type="submit" value="Editar Contenedor" />
	    
	</form:form> 
</body> 
</html>
