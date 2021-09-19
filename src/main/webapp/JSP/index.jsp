<%@ page language="java" contentType="text/html; UTF-8"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="icon" href="/WebContent/IMG/logo.png">
<script src="https://kit.fontawesome.com/e7e1f4a24e.js"
	crossorigin="anonymous"></script>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We"
	crossorigin="anonymous">
<link rel="stylesheet" href="../CSS/indexStyle.css">
<title>MyRecipe</title>
</head>
<header> <!-- Sticky Navbar with Logo --> <nav
	class="navbar sticky-top navbar-expand-lg navbar-light bg-light">
<div class="container-fluid">
	<a class="navbar-brand" href="index.jsp"><img src="../IMG/logo.png"
		alt="" width="50" height="45"> MyRecipe</a>
	<button class="navbar-toggler" type="button" data-bs-toggle="collapse"
		data-bs-target="#navbarNav" aria-controls="navbarNav"
		aria-expanded="false" aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>
	<div class="collapse navbar-collapse" id="navbarNav">
		<ul class="navbar-nav">
			<li><a class="nav-link" href="#"><i class="fas fa-utensils"></i>
					Rezepte</a></li>

			<li><a class="nav-link" href="#"><i class="fas fa-apple-alt"></i>
					Produkte</a></li>

			<li><a class="nav-link" href="test.jsp"><i
					class="fas fa-shopping-basket"></i> Einkaufskorb</a></li>

			<li><a class="nav-link" href="login.jsp"><i
					class="fa fa-user"></i> Mein Konto</a></li>
		</ul>
	</div>
</div>
</nav> </header>
<body>

	<form class="box" action="#" method="post">
		<div class="boxContainer">
			<table class="elementsContainer">
				<tr>
					<td colspan="3"
						style="font-size: 30px; text-align: center; color: white;"><p>Herzlich
							willkommen auf MyRecipe</p></td>
				</tr>
				<tr>
					<td colspan="2"><input type="text"
						placeholder="Welches Rezept suchen Sie..." class="search"></td>
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
					<td style="width: 33%; position: absolut;"><input
						type="checkbox" id="bio"><label for="bio">
							<p style="color: white; white-space: nowrap;">Italienisch</p>
					</label></td>
					<td style="width: 33%; position: absolut;"><input
						type="checkbox" id="horns"> <label for="horns">
							<p style="color: white; white-space: nowrap;">Asiatisch</p>
					</label></td>
					<td style="width: 33%; position: absolut;"><input
						type="checkbox" id="regional"> <label for="regional">
							<p style="color: white; white-space: nowrap;">Amerikanisch</p>
					</label></td>
				</tr>
				<tr>
					<td
						style="text-align: center; padding-top: 25px; padding-bottom: 10px;"
						colspan="3"><p id="weitereEigenschaften">Eigenschaften
							die Ihr Gericht haben soll:</p></td>
				</tr>
				<tr>
					<td style="width: 33%; position: absolut;"><input
						type="checkbox" id="bio"><label for="bio">
							<p style="color: white; white-space: nowrap;">Vegan</p>
					</label></td>
					<td style="width: 33%; position: absolut;"><input
						type="checkbox" id="horns"> <label for="horns">
							<p style="color: white; white-space: nowrap;">Vegetarisch</p>
					</label></td>
					<td style="width: 33%; position: absolut;"><input
						type="checkbox" id="regional"> <label for="regional"><p
								style="color: white; white-space: nowrap;">Reisgericht</p></label></td>
				</tr>
				<tr>
					<td style="width: 33%; position: absolut;"><input
						type="checkbox" id="bio"><label for="bio"><p
								style="color: white; white-space: nowrap;">Nudelgericht</p></label></td>
					<td style="width: 33%; position: absolut;"><input
						type="checkbox" id="horns"> <label for="horns"><p
								style="color: white; white-space: nowrap;">Suppe</p></label></td>
					<td style="width: 33%; position: absolut;"><input
						type="checkbox" id="regional"> <label for="regional"><p
								style="color: white; white-space: nowrap;">Fast Food</p></label></td>
				</tr>

			</table>
		</div>
	</form>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-U1DAWAznBHeqEIlVSCgzq+c9gqGAJn5c/t99JyeKa9xxaYpSvHU5awsuZVVFIhvj"
		crossorigin="anonymous"></script>
</body>
</html>