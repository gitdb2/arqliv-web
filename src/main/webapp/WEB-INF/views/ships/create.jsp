<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
 
<html> 
<head> 
	<title>Alta de Barco</title> 
	<style>
    	.error { color: red; font-size: 0.9em; font-weight: bold; }
	</style> 
</head> 
 
<body> 
	
	<h1>Alta de Barco</h1>
	
	<form:form method="post" modelAttribute="ship">
	
		<label for="nameInput">Nombre: </label>
	    <form:input path="name" id="nameInput"></form:input>
	    <form:errors path="name" cssclass="error"></form:errors>
	    <br />
	    
   		<label for="flagInput">Bandera: </label>
	    <form:input path="flag" id="flagInput"></form:input>
	    <form:errors path="flag" cssclass="error"></form:errors>
	    <br />
	    
	    <label for="codeInput">Código: </label>
	    <form:input path="code" id="codeInput"></form:input>
	    <form:errors path="code" cssclass="error"></form:errors>
	    <br />
	    
	    <label for="manufactoringYearInput">Año de fabricación: </label>
	    <form:input path="manufactoringYear" id="manufactoringYearInput"></form:input>
	    <form:errors path="manufactoringYear" cssclass="error"></form:errors>
	    <br />
	    
	    <label for="crewQuantityInput">Cantidad de tripulantes: </label>
	    <form:input path="crewQuantity" id="crewQuantityInput"></form:input>
	    <form:errors path="crewQuantity" cssclass="error"></form:errors>
	    <br />
	    
   	    <label for="capacityInput">Capacidad: </label>
	    <form:input path="capacity" id="capacityInput"></form:input>
	    <form:errors path="capacity" cssclass="error"></form:errors>
	    <br />
	    
	    <input type="submit" value="Crear Barco" />
	    
	</form:form> 
</body> 
</html>
