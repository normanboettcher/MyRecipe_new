<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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
<link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/LogoutStyle.css">
<title>Logout</title>
</head>
<body>
<div class="main">
<h1 class="byeText">Auf Wiedersehen, hoffentlich sehen wir uns bald wieder.</h1>
<!-- Button zurück zum Login-->
		<a href="JSP/login.jsp"><button class="regButton" type="submit">Zurück
				zum Login</button></a>

</div>
</body>
</html>