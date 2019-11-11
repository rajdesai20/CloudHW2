<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<table border=1>
<tr>
<th>Date</th>
<th>Tmax</th>
<th>Tmin</th>
</tr>
<c:forEach var = "data" items="${list}">
	<tr> 
		<td>${data.date}</td>
		<td>${data.tmax}</td>
		<td>${data.tmin}</td>
	</tr>
</c:forEach> 

</table>

</body>
</html>