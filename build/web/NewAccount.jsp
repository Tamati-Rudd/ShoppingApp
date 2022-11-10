<%-- 
    Document   : NewAccount.jsp
    Created on : 21/03/2022, 10:58:09 am
    Author     : Tamati Rudd 18045626
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<!DOCTYPE html>
<jsp:useBean id="newuser" class="Entities.Vxz7784users" scope="request"/>
<jsp:useBean id="feedback" class="Beans.UserFeedback" scope="request"/>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Account Creation</title>
    </head>
    <body>
        <h1>Shopping Application - Create an Account</h1>
        <c:out value="${feedback.message}"/>
        <br />
        <c:choose>
            <c:when test="${requestScope.feedback.success == false}"> <%--prompt new account creation--%>
                <form action="CreateAccount" method="post">
                    <p>Username: *
                        <input type="text" name="username" required></p>
                    <p>Password: *
                        <input type="password" name="password" required></p>
                    <p>* Indicates required field</p>
                    <p>Note: your username must be unique</p>
                    <button type="submit">Create Account</button>
                </form>
            </c:when>
            <c:otherwise> <%--confirm account creation was successful--%>
                <c:out value="Login to credit funds and begin shopping!"/>  
            </c:otherwise>
        </c:choose>
    </body> <%--Allow user to return to login page--%>
    <a href='<%= response.encodeURL(request.getContextPath()) %>'>
        Return to Login Page
    </a>
</html>
