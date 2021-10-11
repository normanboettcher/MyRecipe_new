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
	href="<%=request.getContextPath()%>/CSS/SortimentStyle.css">
<title>${supermarkt.getBezeichnung()} Sortiment</title>
</head>
<body>
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

	<div class="collapse navbar-collapse" id="navbarNav">
		<ul class="navbar-nav">
			<li class="nav-item dropdown"><a
				class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
				role="button" data-bs-toggle="dropdown" aria-expanded="false"> <i
					class="fas fa-store-alt"></i> H�ndler
			</a>
			<!-- Navbar: DropDown Verschiedene H�nder und Produkte -->
				<ul class="dropdown-menu" aria-labelledby="navbarDropdown">
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
				<!-- Navbar: DropDown Verschiedene H�nder und Angebote -->
			<li class="nav-item dropdown"><a
				class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
				role="button" data-bs-toggle="dropdown" aria-expanded="false"> <i
					class="fas fa-piggy-bank"></i> Angebote
			</a>
				<ul class="dropdown-menu" aria-labelledby="navbarDropdown">
					<li><form action="../ZeigeAngeboteServlet" method="get">
							<button name="supermarkt" value="penny"
								style="border: none; background: #ffffff; color: #3d3832; margin-left: 15px;">
								</i><a><img
									src="<%=request.getContextPath()%>/IMG/penny_logo.png" alt=""
									width="30" height="25"> Penny</a>
							</button>
						</form></li>
					<li><hr class="dropdown-divider"></li>
					<li><form action="../ZeigeAngeboteServlet" method="get">
							<button name="supermarkt" value="rewe"
								style="border: none; background: #ffffff; color: #3d3832; margin-left: 15px;">
								</i><a><img
									src="<%=request.getContextPath()%>/IMG/rewe_logo.png" alt=""
									width="30" height="25"> Rewe</a>
							</button>
						</form></li>
					<li><hr class="dropdown-divider"></li>
					<li><form action="../ZeigeAngeboteServlet" method="get">
							<button name="supermarkt" value="netto"
								style="border: none; background: #ffffff; color: #3d3832; margin-left: 15px;">
								</i><a><img
									src="<%=request.getContextPath()%>/IMG/netto_logo.png" alt=""
									width="25" height="25"> Netto</a>
							</button>
						</form></li>

				</ul></li>

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
<!-- Sortiment von den H�ndler wird angezeigt -->
	<div class="regform">
		<h1>${supermarkt.getBezeichnung()} Sortiment</h1>
	</div>
	<div class="main">
		<form>
			<table class="table">
				<tr>
					<th></th>
					<th>Produkt</th>
					<th>Preis</th>
					<th>Hersteller</th>
					<th>Vegetarisch</th>
					<th>Vegan</th>
					<th>Lokal</th>
					<th>Bio</th>
				</tr>
				<c:forEach var="i" begin="2"
					end="${supermarkt.getSortiment().size()}">
					<tr>
						<td><img src="${supermarkt.getSortiment().get(i).getImage()}"
							alt="produktbild" width="125" height="125"></td>
						<td>${supermarkt.getSortiment().get(i).getBezeichnung()}</td>
						<td>${supermarkt.getSortiment().get(i).getPreis()}&#8364;</td>
						<td>${supermarkt.getSortiment().get(i).getHersteller()}</td>
						<td>
						<c:choose>
						<c:when test="${supermarkt.getSortiment().get(i).getVeggy()=='1'}">Ja</c:when>
						<c:when test="${supermarkt.getSortiment().get(i).getVeggy()=='0'}">Nein</c:when>
    					</c:choose>
						</td>
						<td>
						<c:choose>
						<c:when test="${supermarkt.getSortiment().get(i).getVegan()=='1'}">Ja</c:when>
						<c:when test="${supermarkt.getSortiment().get(i).getVegan()=='0'}">Nein</c:when>
    					</c:choose>
    					</td>
						<td>
						<c:choose>
						<c:when test="${supermarkt.getSortiment().get(i).getLokal()=='1'}">Ja</c:when>
						<c:when test="${supermarkt.getSortiment().get(i).getLokal()=='0'}">Nein</c:when>
    					</c:choose>
    					</td>
						<td>
						<c:choose>
						<c:when test="${supermarkt.getSortiment().get(i).getBio()=='1'}">Ja</c:when>
						<c:when test="${supermarkt.getSortiment().get(i).getBio()=='0'}">Nein</c:when>
    					</c:choose>
    					</td>
					</tr>
				</c:forEach>
			</table>

		</form>
	</div>
</body>
</html>