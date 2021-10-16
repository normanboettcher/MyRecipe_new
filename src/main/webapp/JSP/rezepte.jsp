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
<!-- Wenn USer nicht eingeloggt, kann er nicht auf diese Seite zugreifen -->

<header> <!-- Sticky Navbar with Logo --> <nav
	class="navbar sticky-top navbar-expand-lg navbar-light bg-light">
<div class="container-fluid">
	<a class="navbar-brand" href="JSP/index.jsp"><img
		src="<%=request.getContextPath()%>/IMG/logo.png" alt="" width="50"
		height="45"> MyRecipe</a>
	<button class="navbar-toggler" type="button" data-bs-toggle="collapse"
		data-bs-target="#navbarNav" aria-controls="navbarNav"
		aria-expanded="false" aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>
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
<body>
	<!-- Ergebnisse der Useranfrage -->
	<div class="regform">
		<h1>Passende Rezepte:</h1>
	</div>
	<div class="main">
		<form action="EinkaufslistenVergleichServlet" method="get">
			<c:forEach var="i" begin="0" end="${resultingRezepte.size() - 1}">
				<br>

				<table class="table">
					<tr>
						<th>Rank</th>
						<th>Similarity</th>
						<th>Titel</th>
						<th>Küche</th>
						<th>Gerichteart</th>
						<th>Eigenschaft</th>

						<th></th>
					</tr>
					<tr>
						<td>${i + 1}</td>
						<td>${resultingRezepte.get(i).getSimilarity() * 100 }Prozent</td>
						<td>${resultingRezepte.get(i).getTitel()}</td>
						<td>${resultingRezepte.get(i).getKueche()}</td>
						<td>${resultingRezepte.get(i).getGerichteart()}</td>
						<td>${resultingRezepte.get(i).getEigenschaften()}</td>

						<td>
							<div>
							<button class="regButton2"
								value="${resultingRezepte.get(i).getRezepte_id()}"
								name="rezept_checked" type="submit">Berechnen</button>
							</div>	
						</td>
					</tr>
				</table>

			</c:forEach>
		</form>
	</div>
</body>
</html>
