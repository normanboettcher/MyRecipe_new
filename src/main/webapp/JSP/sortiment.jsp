<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sortiment von ${supermarkt.getBezeichnung()}</title>
</head>
<body>
<p>Sortiment von ${supermarkt.getBezeichnung()}</p>
<table>
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
	<c:forEach var = "i" begin = "2" end = "${supermarkt.getSortiment().size()}">
		<tr>
			<td><img src = "${supermarkt.getSortiment().get(i).getImage()}" alt = "produktbild"
			width = "125" height = "125"></td>
			<td>${supermarkt.getSortiment().get(i).getBezeichnung()}</td>
			<td>${supermarkt.getSortiment().get(i).getPrice()} €</td>
			<td>${supermarkt.getSortiment().get(i).getManufacturer()}</td>
			<td>${supermarkt.getSortiment().get(i).getVeggy()}</td>
			<td>${supermarkt.getSortiment().get(i).getVegan()}</td>
			<td>${supermarkt.getSortiment().get(i).getLocal()}</td>
			<td>${supermarkt.getSortiment().get(i).getBio()}</td>
		</tr>	
	</c:forEach>
</table>

</body>
</html>