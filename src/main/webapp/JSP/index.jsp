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
	href="<%=request.getContextPath()%>/CSS/indexStyle.css">
<title>MyRecipe</title>
</head>
<!--<c:if test="${admin == null && kunde == null}">
		<meta http-equiv="refresh" content="0; URL=login.jsp">
	</c:if> -->

<header> <!-- Sticky Navbar with Logo --> <nav
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
			<li class="nav-item dropdown"><a
				class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
				role="button" data-bs-toggle="dropdown" aria-expanded="false"> <i
					class="fas fa-store-alt"></i> Händler
			</a>
			<!-- Navbar: DropDown Verschiedene Händer und Produkte -->
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
				<!-- Navbar: DropDown Verschiedene Händer und Angebote -->
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
	<!-- Suche/ Rezeptauswahl -->
	<form class="box" action="../RezepteServlet" method="get" onsubmit="return ((validateForm() & validateForm2() & validateForm3()) == 1)">
		<div class="boxContainer">
			<table class="elementsContainer">
				<tr>
					<td colspan="3"
						style="font-size: 30px; text-align: center; color: white;"><p>Herzlich
							willkommen auf MyRecipe</p></td>
				</tr>
				<tr>
					<td colspan="2"><input type="text"
						placeholder="Welches Rezept suchen Sie..." class="search"
						value=${ inputTitel }></td>
					<td style="width: 1px; position: absolut;" colspan="1"><button
							class="btn btn-outline-success" type="submit" id="suchbtn">
							<i class="fa fa-search"></i>
						</button></td>
				</tr>
				<tr>
					<td
						style="text-align: center; padding-top: 25px; padding-bottom: 10px;"
						colspan="3"><p id="weitereEigenschaften">
							Keine Idee welches Rezept Sie kochen möchten?<br /> Welche Küche
							möchten Sie probieren:
						</p></td>
				</tr>
				<tr>
					<td style="width: 33%; position: absolut;"><input type="radio"
						name="kueche" id="Italienisch" value="Italienisch"><label> </label><a
						style="color: white; white-space: nowrap;"> Italienisch</a></td>
					<td style="width: 33%; position: absolut;"><input type="radio"
						name="kueche" id="Asiatisch" value="Asiatisch"> <label> </label><a
						style="color: white; white-space: nowrap;"> Asiatisch</a></td>
					<td style="width: 33%; position: absolut;"><input type="radio"
						name="kueche" id="Amerikanisch" value="Amerikanisch"> <label> </label><a
						style="color: white; white-space: nowrap;"> Amerikanisch</a></td>
				</tr>
				<tr>
					<td
						style="text-align: center; padding-top: 25px; padding-bottom: 10px;"
						colspan="3"><p id="weitereEigenschaften">Ihre gewünschte
							Speiseart:</p></td>
				</tr>
				<tr>
					<td style="width: 33%; position: absolut;"><input type="radio"
						name="gerichteart" id="Nudelgericht" value="Nudelgericht"><label></label><a
						style="color: white; white-space: nowrap;"> Nudelgericht</a></td>
					<td style="width: 33%; position: absolut;"><input type="radio"
						name="gerichteart" id="Reisgericht" value="Reisgericht"><label></label><a
						style="color: white; white-space: nowrap;"> Reisgericht</a></td>
					<td style="width: 33%; position: absolut;"><input type="radio"
						name="gerichteart" id="Kartoffelgericht" value="Kartoffelgericht"><label></label><a
						style="color: white; white-space: nowrap;"> Kartoffelgericht</a></td>
				<tr>
				<tr>
					<td style="width: 33%; position: absolut;"><input type="radio"
						name="gerichteart" id="Fastfood" value="Fastfood"><label></label><a
						style="color: white; white-space: nowrap;"> Fast-Food</a></td>
					<td style="width: 33%; position: absolut;"><input type="radio"
						name="gerichteart" id="Suppe" value="Suppe"><label></label><a
						style="color: white; white-space: nowrap;"> Suppe</a></td>
					<td style="width: 33%; position: absolut;"><input type="radio"
						name="gerichteart" id="Salat" value="Salat"><label></label><a
						style="color: white; white-space: nowrap;"> Salat</a></td>
				<tr>
					<td
						style="text-align: center; padding-top: 25px; padding-bottom: 10px;"
						colspan="3"><p id="weitereEigenschaften">Eigenschaften
							die Ihr Gericht haben soll:</p></td>
				</tr>
				<tr>
					<td style="width: 33%; position: absolut;"><input type="radio"
						name="eigenschaften" id="Vegan" value="Vegan"><label> </label><a
						style="color: white; white-space: nowrap;"> Vegan</a></td>
					<td style="width: 33%; position: absolut;"><input type="radio"
						name="eigenschaften" id="Vegetarisch" value="Vegetarisch"> <label>
					</label><a style="color: white; white-space: nowrap;"> Vegetarisch</a></td>
					<td style="width: 33%; position: absolut;"><input type="radio"
						name="eigenschaften" id="Fleisch" value="Fleisch"> <label> </label><a
						style="color: white; white-space: nowrap;">Fleischgericht</a></td>
				</tr>
			</table>
		</div>


		<!-- EasterEgg -->
	</form>
	<div class="popup" id="popup-1">
		<div class="overlay"></div>
		<div class="content">
			<div class="close-btn" onclick="togglePopup()">&times;</div>
		</div>
	</div>
	<button onclick="togglePopup()"
		style="border: none; background: none; align: left; margin-top: 600px;">:)</button>
	<script type="text/javascript">
	function validateForm() {
	    var radios = document.getElementsByName("kueche");
	    var formValid = false;
	    var i = 0;
	    while (!formValid && i < radios.length) {
	        if (radios[i].checked) formValid = true;
	        i++;        
	    }
	    if (!formValid) alert("Bitte wählen Sie eine Küche aus!");
	    return formValid;
		}
	function validateForm2() {
	    var radios = document.getElementsByName("gerichteart");
	    var formValid = false;
	    var i = 0;
	    while (!formValid && i < radios.length) {
	        if (radios[i].checked) formValid = true;
	        i++;        
	    }
	    if (!formValid) alert("Bitte wählen Sie eine Gerichteart aus!");
	    return formValid;
		}
	function validateForm3() {
	    var radios = document.getElementsByName("eigenschaften");
	    var formValid = false;
	    var i = 0;
	    while (!formValid && i < radios.length) {
	        if (radios[i].checked) formValid = true;
	        i++;        
	    }
	    if (!formValid) alert("Bitte wählen Sie eine Eigenschaft aus!");
	    return formValid;
		}
		function togglePopup() {
			document.getElementById("popup-1").classList.toggle("active");
		}
	</script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-U1DAWAznBHeqEIlVSCgzq+c9gqGAJn5c/t99JyeKa9xxaYpSvHU5awsuZVVFIhvj"
		crossorigin="anonymous"></script>

</body>
</html>