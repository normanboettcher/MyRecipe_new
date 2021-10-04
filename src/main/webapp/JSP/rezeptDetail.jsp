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
	href="<%=request.getContextPath()%>/CSS/RezepteDetailStyle.css">
<title>MyRecipe</title>
</head>
<header><!-- Sticky Navbar with Logo --> <nav
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
			<li><a class="nav-link" href="rezepte.jsp"><i class="fas fa-utensils"></i>
					Rezepte</a></li>
			<li class="nav-item dropdown"><a
				class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
				role="button" data-bs-toggle="dropdown" aria-expanded="false"> <i
					class="fas fa-store-alt"></i> H�ndler
			</a>
				<ul class="dropdown-menu" aria-labelledby="navbarDropdown">
					<li><form action="../ZeigeSortimentServlet" method="get">
							<button name="supermarkt" value="lidl"
								style="border: none; background: #ffffff; color: #3d3832; margin-left: 15px;">
								</i><a><img
									src="<%=request.getContextPath()%>/IMG/lidl_logo.png" alt=""
									width="30" height="25"> Lidl</a>
							</button>
						</form></li>
					<li><hr class="dropdown-divider"></li>
					<li><form action="../ZeigeSortimentServlet" method="get">
							<button name="supermarkt" value="penny"
								style="border: none; background: #ffffff; color: #3d3832; margin-left: 15px;">
								</i><a><img
									src="<%=request.getContextPath()%>/IMG/penny_logo.png" alt=""
									width="30" height="25"> Penny</a>
							</button>
						</form></li>
					<li><hr class="dropdown-divider"></li>
					<li><form action="../ZeigeSortimentServlet" method="get">
							<button name="supermarkt" value="rewe"
								style="border: none; background: #ffffff; color: #3d3832; margin-left: 15px;">
								</i><a><img
									src="<%=request.getContextPath()%>/IMG/rewe_logo.png" alt=""
									width="30" height="25"> Rewe</a>
							</button>
						</form></li>
					<li><hr class="dropdown-divider"></li>
					<li><form action="../ZeigeSortimentServlet" method="get">
							<button name="supermarkt" value="netto"
								style="border: none; background: #ffffff; color: #3d3832; margin-left: 15px;">
								</i><a><img
									src="<%=request.getContextPath()%>/IMG/netto_logo.png" alt=""
									width="25" height="25"> Netto</a>
							</button>
						</form></li>

				</ul></li>
			</form>
			</li>
			<li><a class="nav-link" href="test.jsp"><i
					class="fas fa-shopping-basket"></i> Einkaufskorb</a></li>
			<c:if test="${kunde == null && admin == null }">				
			<li><a class="nav-link" href="login.jsp"><i
					class="fa fa-user"></i> Login</a></li>
			</c:if>
			<c:if test="${kunde != null || admin != null }">
				<li><a class="nav-link" href="../LogoutServlet"><i
						class="fa fa-unlock-alt"></i> Logout</a></li>
			</c:if>
		</ul>
	</div>
</div>
</nav></header>
<body>
<div class="movieImage">
		<br>
		<h1>${film.filmtitel()}</h1>
		<br> <img class="pic" alt="." src="${film.getTitelbildQuelle()}">
	</div>
	<div class="showDetails">
		<!-- Tabelle mit den Filmdetails-->
		<table class="filmdetails">
			<tr>
				<th>Filmtitel</th>
				<th>Genre</th>
				<th>Altersbeschränkung</th>
				<th>Preis</th>
				<th>Bewertung</th>
				<th>Produzent</th>
			</tr>
			<tr>
				<td>${film.filmtitel()}</td>
				<td>${film.getGenre()}</td>
				<td>${film.getAltersbeschraenkung()}</td>
				<td>${film.getPreis()}&#8364</td>
				<td>${film.getBewertung()}</td>
				<td>${film.getProduzent().ganzerNameProduzent()}</td>
			</tr>
			<tr>
				<td colspan="6">${film.printBeschreibung()}</td>
		</table>
		<br>
		<!-- YT-Link eingebettet-->
		<h4 class="link">Der Trailer zum Film "${film.filmtitel()}"!</h4>
		<p class="link">${film.getYoutubeLink()}</p>
		<br>
		<!-- Admin kann einen Film nicht bewerten, Kunde der nicht eingeloggt ist, wird weitergeleitet.
		Nur ein eingeloggter Kunde kann einen Film bewerten. Die Bewertung wird in die Gesamtbewertung miteinberechnet.-->
		<div class="bewertung">
			<c:if test="${admin == null}">
				<h4>Bewerten Sie jetzt "${film.filmtitel()}"!</h4>
				<br>
				<form action="FilmBewerten" method="post">
					<input type="hidden" name="film" value="${film}">
					<table>
						<tr>
							<th><input type="radio" name="bewertung_abgabe" value="1.0">1
								Stern</th>
							<th><input type="radio" name="bewertung_abgabe" value="1.5">1.5
								Sterne</th>
							<th><input type="radio" name="bewertung_abgabe" value="2.0">2
								Sterne</th>
							<th><input type="radio" name="bewertung_abgabe" value="2.5">2.5
								Sterne</th>
							<th><input type="radio" name="bewertung_abgabe" value="3.0">3
								Sterne</th>
							<th><input type="radio" name="bewertung_abgabe" value="3.5">3.5
								Sterne</th>
							<th><input type="radio" name="bewertung_abgabe" value="4.0">4
								Sterne</th>
							<th><input type="radio" name="bewertung_abgabe" value="4.5">4.5
								Sterne</th>
							<th><input type="radio" name="bewertung_abgabe" value="5.0">5
								Sterne</th>
						</tr>
					</table>

					<button class="button" type="submit">Bewertung abgeben</button>
				</form>
			</c:if>
		</div>
		<!-- Nur ein eingloggter Kunde kann den Film zum Warenkorb hinzufügen.-->
		<c:if test="${kunde != null }">
			<form action="WarenkorbServlet" method="post">
				<input type="hidden" value="${film.getId()}" name="film_id">
				<a class="add-cart cart"><button class="button" type="submit">Zum
						Warenkorb hinzufügen</button></a>
			</form>
		</c:if>
		<a href="filme.jsp"><button class="button" type="submit">Zurück
				zu den Filmen</button></a>
	</div>
</body>
</html>