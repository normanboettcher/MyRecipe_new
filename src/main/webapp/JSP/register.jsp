<%@ page language="java" contentType="text/html; UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="icon" href="src/main/webapp/IMG/logo.png">
<script src="https://kit.fontawesome.com/e7e1f4a24e.js"
	crossorigin="anonymous"></script>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/CSS/CreateAccountStyle.css">
<title>MyRecipe</title>
</head>
<body>
	<!-- Formular zum Registrieren-->
	<div class="regform">
		<h1>Registrierungsformular</h1>
	</div>
	<div class="main">
		<form method="post" action= "../RegisterServlet">
			<div id="person">
				<!-- Input Name-->
				<h2 class="abschnitt">Name</h2>
				<input class="vorname" type="text" name="vor_name"
					placeholder="Vorname">
				<input class="nachname"
					type="text" name="nach_name" placeholder="Nachname">
			</div>
			<!-- Input Anschrift-->
			<h2 class="abschnitt">Anschrift</h2>
			<c:if test = "${str_falsch == true }">
				<p>Feld Straße entspricht nicht den Vorgaben</p>
			</c:if>
			<input class="strasse" type="text" name="strasse"
				placeholder="Straße" maxlength="50" required> 
				
			<c:if test="${hn_falsch == true }">
				<p>Das Feld Hausnummer entspricht nicht den Vorgaben</p>
			</c:if>
			<input class="nr" type="text" name="nr" placeholder="Straßen Nr." required>
				
			<c:if test = "${plz_falsch == true }">
				<p>Das Feld Postleitzahl entspricht nicht den Vorgaben</p>
			</c:if>
			<input class="plz" type="text" name="plz" placeholder="Postleitzahl" required> 
			
			<c:if test="${city_falsch == true }">
				<p>Das Feld Stadt entspricht nicht den Vorgaben</p>
			</c:if>
			
			<input class="stadt" type="text" name="stadt" placeholder="Stadt" required>
			<!-- Input EMAIL und Passwort -->
			<h2 class="abschnitt">Account</h2>
			<c:if test="${email_vorhanden == true }">
				<p>Diese Email ist bereits vergeben.</p>
			</c:if>
			
			<c:if test = "${email_falsch == true }">
				<p>Das Feld Email entspricht nicht den Vorgaben</p>
			</c:if>
			<input class="email" type="text" name="email"
				placeholder="E-Mail Adresse" required><br>
				
			<c:if test="${passwort_nicht_identisch == true }]">
				<p>Die Passwoerter stimmen nicht überein</p>
			</c:if>
			
			<c:if test="${pw_falsch }">
				<p>Das Feld Passwort entspricht nicht den Vorgaben</p>
			</c:if>

			<input class="passwort" type="password" name="passwort"
				placeholder="Passwort" required> 
			<input class="passwort2" type="password" name="passwort2"
				placeholder="Passwort bestätigen" required><br>
				
			<button class="regButton" type="submit">Registrieren</button>
		</form>
		<!-- Button zurück zur Startseite-->
		<a href="index.jsp"><button class="regButton" type="submit">Zurück
				zur Startseite</button></a><br>
	</div>
	</body>
</html>