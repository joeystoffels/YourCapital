<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
<header>
	<h1><img src="http://vamers.com/wp-content/uploads/2013/08/Vamers-Games-LEGO-Marvel-Super-Heroes-Banner.jpg"/></h1>
</header>
<title>YourCapital Home Page</title>
</head>
<body>
	<h2>Zoekscherm...</h2>
	<p>Lijst met kandidaten:</p>
	<c:forEach items="${LijstTotaal}" var="kandidaat">
		<li>"${kandidaat.naam}" is "${kandidaat.leeftijd}" jaar oud en heeft
			de volgende vaardigheden: "${kandidaat.arrSkills}".</li>
	</c:forEach>
	<p></p>
	<form:form id="form" method="post" modelAttribute="Kandidaat">
		
		<form:label path="naam">
		<p>Zoek op naam: </p>
		</form:label>
		<form:input path="naam"/>
		
		<form:label path="leeftijd">
		<p>Zoek op leeftijd:</p>
		</form:label>
		<form:input path="leeftijd"/>

	<form:label path="skill">
		<p>Zoek op skill:</p>
		</form:label>
		<form:input path="skill"/>

		
		<p></p>
		<input type="submit" value="Search">
	</form:form>
	<p></p>
	
	<c:forEach items="${LijstTotaal}" var="kandidaat">
		<c:if test="${naam == kandidaat.naam}">
			<p>${kandidaat.naam}, ${kandidaat.leeftijd}, ${kandidaat.arrSkills}</p>
		</c:if>
		<c:if test="${leeftijd == kandidaat.leeftijd}">
			<p>${kandidaat.naam}, ${kandidaat.leeftijd}, ${kandidaat.arrSkills}</p>
		</c:if>
		
		<c:forEach items="${kandidaat.arrSkills}" var="skill2">
			<c:if test="${skill == skill2}">
				<p>${kandidaat.naam}, ${kandidaat.leeftijd}, ${kandidaat.arrSkills}</p>
			</c:if>
		</c:forEach>
		
	</c:forEach>
	
	
	


	
	<p>last update: 12 april</p>
</body>
</html>