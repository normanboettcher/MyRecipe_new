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

<c:forEach items = "${result}" var = "result">
	<p>${result.value.getLaden()}</p>

		<p> . Platz <p>
		<p>
		<table>
			<tr>
				<th>Produkt</th>
				<th>Menge</th>
				<th>Preis</th>
				<th>Gesamtpreis</th>
			</tr>
			<c:forEach items = "${result.value.getProduktliste()}" var = "produkte">
				<tr>
					<td>${produkte.value.getBezeichnung()}</td>
					<c:forEach items = "${result.value.getProdukteMitMenge()}" var = "menge">
					<c:if test = "${produkte.key == menge.key}">
							<td>${menge.value}</td>
							<td>${produkte.value.getPreis()}</td>
						</c:if>
					</c:forEach>
				</tr>
			</c:forEach>
			<tr>
				<td>-</td>
				<td>-</td>
				<td>-</td>
				<td>${result.value.getGesamtPreis()} EUR</td>
			</tr>
		</table>
		</c:forEach>

</body>
</html>