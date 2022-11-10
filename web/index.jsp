<%-- 
    Document   : index.jsp
    Created on : 21/03/2022, 5:09:55 pm
    Author     : Tamati Rudd
    This page serves as the login page, which is the first page visited
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<!DOCTYPE html>
<jsp:useBean id="UserLoginAttempts" class="Beans.LoginAttempts" scope="session"/>
<jsp:setProperty name ="UserLoginAttempts" property="attempts"/>
<html>
    <head>
        <title>Shopping Application for Assignment 1</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <H1>Shopping Application - Login</H1>
        <c:out value = "Login Attempts: ${UserLoginAttempts.attempts}"/>
        <%-- Only allow the user to attempt to login if they haven't reached the allowed limit 
             Otherwise, lock the session out --%>
        <c:choose>
            <c:when test="${UserLoginAttempts.attempts < UserLoginAttempts.allowed}">
                <form action="LoginProcessor">
                    <p>Username: *
                        <input type="text" name="username" required></p>
                    <p>Password: *
                        <input type="password" name="password" required></p>
                    <p>* Indicates required field</p>
                    <button type="submit">Login</button>
                    <br />
                </form>
            </c:when>
            <c:otherwise>
                <p>This session has been locked out as it has reached the maximum allowed login attempts</p>
            </c:otherwise>
        </c:choose>
        
        <%-- Allow the creation of a new account--%>
        <br />
        <p>Don't have an account?</p>
            <a href='<%= response.encodeURL("NewAccount.jsp") %>'>
            Create account
        </a>
    </body>
</html>
