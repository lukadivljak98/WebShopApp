<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>
<%@ page import="javax.mail.*" %>
<%@ page import="javax.mail.internet.*" %>
<%@ page import="javax.activation.*" %>
<!DOCTYPE html>
<%
    String sender = request.getParameter("sender");
    String recipient = request.getParameter("recipient");
    String subject = request.getParameter("subject");
    String body = request.getParameter("body");

    Properties properties = new Properties();
    properties.setProperty("mail.smtp.host", "localhost");
    properties.setProperty("mail.smtp.port", "1025");
    //properties.setProperty("mail.smtp.auth", "true");

    Session sessionn = Session.getInstance(properties);

    try {
        MimeMessage message = new MimeMessage(sessionn);
        message.setFrom(new InternetAddress(sender));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        message.setSubject(subject);
        message.setText(body);

        Transport.send(message);

        out.println("<p>Email sent successfully.</p>");
    } catch (MessagingException e) {
        out.println("<p>Error sending email: " + e.getMessage() + "</p>");
    }
%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="styles/style.css"></link>
</head>
<body>
 <h1>Send Email</h1>

    <p><b>From:</b> <%= sender %></p>
    <p><b>To:</b> <%= recipient %></p>
    <p><b>Subject:</b> <%= subject %></p>
    <p><b>Body:</b> <%= body %></p>
</body>
</html>