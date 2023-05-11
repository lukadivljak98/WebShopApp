<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="net.etfbl.ip.dto.Category"%>
<%@page import="net.etfbl.ip.beans.CategoryBean"%>
<%@page import="net.etfbl.ip.beans.AttributeBean"%>
<jsp:useBean id="categoryBean" type="net.etfbl.ip.beans.CategoryBean" scope="session"/>
<jsp:useBean id="attributeBean" type="net.etfbl.ip.beans.AttributeBean" scope="session"/>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html"; charset="UTF-8">
		<title>Insert title here</title>
		<link
      rel="stylesheet" href="styles/style.css" type="text/css"
    /> 
	</head>
	<body>
		<%
    String categoryIdStr = (String) request.getAttribute("categoryId");
    if (categoryIdStr != null) {
        try {
            int categoryId = Integer.parseInt(categoryIdStr);
            
            Category category = categoryBean.getById(categoryId);
            
            %>
            <h1>Update Category:</h1>
            <form method="POST" action="/AdminApp/?action=updateCategory">
                <input type="hidden" name="categoryId" value="<%=categoryId%>"/>
                <h3>Category Name:</h3>
                <input type="text" name="categoryName" value="<%=category.getType()%>"/><br/>
                <h3>Category Attributes:</h3>
                <input type="text" name="categoryAttributes" value="<%=category.getAttributes()%>" 
                size="<%=category.getAttributes().size()*15%>"/><br/><br/>
                <input type="submit" value="Update Category" name="submit"/><br/>
                <h3><%=session.getAttribute("notification").toString()%></h3><br/>
            </form>
            <%
        } catch (NumberFormatException e) {
            out.println("Invalid category ID: " + categoryIdStr);
        }
    } else {
        out.println("Category ID parameter is missing");
    }
%>

	</body>	
</html>