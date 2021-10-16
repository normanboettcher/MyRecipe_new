<%@ page language="java" contentType="text/html; UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="icon" href="<%=request.getContextPath()%>/IMG/logo.png">
<script src="https://kit.fontawesome.com/e7e1f4a24e.js"
	crossorigin="anonymous"></script>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/CSS/RezepteStyle.css">
<title>MyRecipe</title>
</head>
<header> <!-- Navbar with Logo --> <nav
class="navbar sticky-top navbar-expand-lg navbar-light bg-light">
<div class="container-fluid">
	<a class="navbar-brand" href="index.jsp"><img
		src="<%=request.getContextPath()%>/IMG/logo.png" alt="" width="50"
		height="45"> MyRecipe</a>
	<button class="navbar-toggler" type="button" data-bs-toggle="collapse"
		data-bs-target="#navbarNav" aria-controls="navbarNav"
		aria-expanded="false" aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>

	<div class="collapse navbar-collapse" id="navbarNav">
		<ul class="navbar-nav">
			<li><a
				class="nav-link" href="JSP/index.jsp"><i
					class="fas fa-arrow-left"></i> Zurück zur Startseite
			</a></li>
			
		</ul>
	</div>
</div>
</nav></header>
<div class="regform">
	<h1>Preise Ihres Einkaufs</h1>
</div>
<div class="main">
<form>
	<c:forEach items="${result}" var="result">
			<h3 style="text-align:center; color:white;">${result.value.getLaden()}</h3>
			<table class="table">
			<tr>
				<th style="text-align:center;">Produkte</th>
				<th style="text-align:center;">Menge</th>
				<th style="text-align:center;">Preis/Stk</th>
				<th style="text-align:center;">Originalpreis/Stk</th>
				<th style="text-align:center;">Gespart/Stk</th>
				<th style="text-align:center;">Gespart in Prozent/Stk</th>
				<th style="text-align:center;">Preis/Gesamt</th>
				<th style="text-align:center;">Gespart/Gesamt</th>
				<th style="text-align:center;">Gespart in Prozent/Gesamt</th>
				
			</tr>
			<c:forEach items="${result.value.getProduktliste()}" var="produkte">
				<tr>
					<td>${produkte.value.getBezeichnung()}</td>
					<c:forEach items="${result.value.getProdukteMitMenge()}"
							var="menge">
					<c:if test="${produkte.key == menge.key}">
							<td>${menge.value}</td>
							<td>${produkte.value.getPreis()}</td>
						</c:if>
					</c:forEach>
					<c:if test="${produkte.value.getImAngebot() == true }">
						<td>${produkte.value.getOriginPreis()} EUR</td>
						<td>${produkte.value.getErsparnisFuerFood()}</td>
						<td>${produkte.value.getErsparnisInProzentFuerFood()} Prozent</td>
						<td>-</td>
						<td>-</td>
						<td>-</td>
					</c:if>
					<c:if test="${produkte.value.getImAngebot() == false }">
						<td>Originalpreis entspricht Preis</td>
						<td>-</td>
						<td>-</td>
						<td>-</td>
						<td>-</td>
						<td>-</td>
					</c:if>
				</tr>
				</c:forEach>
			<tr>
				<td colspan="6"></td>
				<td style="text-align:center; font-weight: bold; width: 33%;">${result.value.getGesamtPreis()} EUR Gesamt</td>
				<td style="text-align:center; font-weight: bold; width: 33%;">${result.value.getErsparnis()} EUR</td>
				<td style="text-align:center; font-weight: bold; width: 33%;">${result.value.getErsparnisInProzent()} Prozent</td>
			</tr>
		</table>
		
	</c:forEach>
	</form>
				
</div>
</body>

</html>