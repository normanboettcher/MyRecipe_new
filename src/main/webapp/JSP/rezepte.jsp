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
<header></header>
<body>
		<c:forEach var="i" begin="0" end="${resultingRezepte.size() - 1}">
		<p> ${i + 1}. Platz <p>
		<table>
			<tr>
				<th>Titel</th>
				<th>Gerichteart</th>
				<th>Eigenschaft</th>
				<th>Similarity</th>
				<th></th>
			</tr>
			<tr>
				<td>${resultingRezepte.get(i).getTitel()}</td>
				<td>${resultingRezepte.get(i).getKueche()}</td>
				<td>${resultingRezepte.get(i).getEigenschaften()}</td>
				<td>${resultingRezepte.get(i).getSimilarity() * 100 } Prozent</td>
				<td>
					<form action = "EinkaufslistenVergleichServlet" method = "get">
						<input type = "radio" id = "radio_auswahl" name = "rezept_checked" value = "${resultingRezepte.get(i).getRezepte_id()}">
						<label for = "radio_auswahl">Einkaufsliste anfertigen</label>
						<input type = "submit">Anfragen
					</form>
				</td>
			</tr>
			</table>
			<br>
			<br>
		</c:forEach>
</body>
</html>
