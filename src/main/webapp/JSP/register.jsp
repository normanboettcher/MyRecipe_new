<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
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
			<input class="strasse" type="text" name="strasse"
				placeholder="Straße" maxlength="50" required> 
			<input
				class="nr" type="text" name="nr" placeholder="Straßen Nr." required>
			<input class="plz" type="text" name="plz" placeholder="Postleitzahl" required> 
			<input class="stadt" type="text" name="stadt" placeholder="Stadt" required>
			<!-- Input EMAIL und Passwort -->
			<h2 class="abschnitt">Account</h2>
			<input class="email" type="text" name="email"
				placeholder="E-Mail Adresse" required><br>
			<input class="passwort" type="password" name="passwort"
				placeholder="Passwort" required> 
				<input
				class="passwort2" type="password" name="passwort2"
				placeholder="Passwort bestätigen" required><br>
			<button class="regButton" type="submit">Registrieren</button>
		</form>
		<!-- Button zurück zur Startseite-->
		<a href="index.jsp"><button class="regButton" type="submit">Zurück
				zur Startseite</button></a><br>
	</div>
	</body>
</html>