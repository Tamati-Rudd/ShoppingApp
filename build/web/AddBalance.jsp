<%-- 
    Document   : AddBalance
    Created on : 26/03/2022, 1:52:51 pm
    Author     : Tamati Rudd
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<!DOCTYPE html>
<jsp:useBean id="details" class="Entities.Vxz7784users" scope="session"/>
<jsp:useBean id="balfeedback" class="Beans.UserFeedback" scope="request"/>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Balance Page</title>
    </head>
    <body>
        <h1>Add Funds to Your Account</h1>
        <h3>Account Details</h3>
        <c:out value="User: ${details.username}"/>
        </br >
        <c:out value="Balance: ${details.toString()}"/>
        </br >
        
        <h3>Add Balance</h3>
        <c:choose>
            <c:when test="${requestScope.balfeedback.success == false}"> <%--prompt balance credit--%>
                <form action="CreditBalance" method="post">
                    <p>Balance to Add: * $
                        <input type="text" name="payment" required></p>
                    <p>* Indicates required field</p>
                    <button type="submit">Pay Now</button>
                </form>
            </c:when>
            <c:otherwise> <%--confirm balance credit was successful--%>
                <c:out value="Your account has been credited!"/>
                </br >
            </c:otherwise>
        </c:choose>
        <br />
        <c:out value="${balfeedback.message}"/> 
        <br />
        <br />
        <form action="QueryProductList" method="get">
            <button type="submit">Return to Catalogue</button>
        </form>
    </body>
</html>
