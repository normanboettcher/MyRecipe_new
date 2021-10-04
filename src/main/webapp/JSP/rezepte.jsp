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
			<li><a class="nav-link" href="#"><i class="fas fa-utensils"></i>
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
<div class="container">
		<c:forEach items="${filmliste}" var="film">
			<div class="img">
				<br>
				<img class="pic" alt="picture" src="${film.getTitelbildQuelle()}">
				<span class="titel">${film.filmtitel()}</span> <span>${film.getPreis()}
					&#8364</span><br>
				<!-- Film in den Warenkorb einfügen-->
				<!-- Sollte Kunde nicht eingeloggt sein, können die Artikel nicht in den Warenkorb eingefügt werden-->
				<c:if test="${kunde != null }">
					<form action="WarenkorbServlet" method="post">
						<input type="hidden" value="${film.getId()}" name="film_id">
						<a class="add-cart cart">
							<button class="button" type="submit">Zum Warenkorb hinzufügen</button>
						</a>
					</form>
				</c:if>
				<form action="FilmDetails" method="post">
					<input type="hidden" name="film_id" value="${film.getId()}">
					<button class="Bbutton" type="submit">Filmdetails</button>
					<br>
				</form>
				<!-- Weiterleitung zu Film bearbeiten und Löschen, falls Nutzer Admin ist.-->
				<c:if test="${admin != null }">
					<form method="post" action="FilmBearbeiten">
						<input type="hidden" name="film_id" value="${film.getId()}">
						<button class="Bbutton" type="submit">Film Bearbeiten</button>
						<br>
					</form>
					<form method="post" action="FilmLoeschen">
						<input type="hidden" name="film_id" value="${film.getId()}">
						<button class="Bbutton" type="submit">Film löschen</button>
					</form>
				</c:if>
			</div>
		</c:forEach>
	</div>
	<br>
</body>
</html>
