<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html"; charset="UTF-8">
		<title>Web Shop Admin App</title>
		<link
      rel="stylesheet" href="styles/style.css" type="text/css"
    /> 
	</head>
	<body>
		<h1>ETF_IP Web Shop Admin App</h1><hr/>
		<h2>Log In</h2>
		<form method="POST" action="?action=login">
			Username:<br/>
			<input type="text" name="username" id="username"/><br/><br/>
			Password:<br/>
			<input type="password" name="password" id="password"/><br/><br/>
			<input type="submit" value="Log In" name="submit"/><br/>
			<h3><%=session.getAttribute("notification")!=null?session.getAttribute("notification").toString():""%></h3>
		</form>
	</body>
</html>