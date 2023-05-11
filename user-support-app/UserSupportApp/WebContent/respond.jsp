<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.*" %>
<%@ page import="javax.mail.*" %>
<%@ page import="javax.mail.internet.*" %>
<%@ page import="javax.activation.*" %>
<%@ page import="net.etfbl.ip.beans.MessageBean" %>
<%@ page import="net.etfbl.ip.Message" %>
<!DOCTYPE html>
<%
    String id = request.getParameter("id");

    MessageBean messageBean = new MessageBean();
    Message message = messageBean.getMessageById(Long.parseLong(id));
    messageBean.setReadTrue(message);
%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="styles/style.css"></link>
</head>
<body>
<h1>Respond to Message</h1>

    <p><b>From:</b> <%= message.getAppUser().getEmail() %></p>
    <p><b>Message:</b> <%= message.getText() %></p>

    <form method="post" action="send.jsp">
        <input type="hidden" name="sender" value="support@webshop.com">
        <input type="hidden" name="recipient" value="<%= message.getAppUser().getEmail() %>">
        <label>Subject:</label><br>
        <input type="text" name="subject"><br>
        <label>Message:</label><br>
        <textarea name="body" rows="10" cols="50"></textarea><br>
        <input type="submit" value="Send">
    </form>
</body>
</html>