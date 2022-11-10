<%-- 
    Document   : OrderSummary
    Created on : 26/03/2022, 11:41:53 pm
    Author     : Tamati Rudd
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<jsp:useBean id="details" class="Entities.Vxz7784users" scope="session"/>
<jsp:useBean id="prodlist" class="java.util.ArrayList" scope="application"/>
<jsp:useBean id="orders" class="java.util.ArrayList" scope="session"/>
<jsp:useBean id="total" class="java.lang.Float" scope="session"/>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Order Summary</title>
    </head>
    <body>
        <h1>Summary of Order</h1>
        <h3>Account Details</h3>
        <%--Print the user's name and balance--%>
        <c:out value="User: ${details.username}"/>
        </br >
        <c:out value="Balance: ${details.toString()}"/>
        </br >
        
        <h3>Order Details</h3> <%--Print final order details--%>
        <c:forEach items="${orders}" var="Vxz7784orders">
            <c:out value="Order ID: ${Vxz7784orders.orderID}"/> 
            <br />
            <c:out value="Product: ${Vxz7784orders.prodID}"/> 
            <br />
            <c:out value="Quantity: ${Vxz7784orders.quantity}"/> 
            <br />
            <c:out value="Order Price: "/>
            <fmt:formatNumber value="${Vxz7784orders.total}" type="currency" currencyCode="NZD" />  
            <br />
            <br />
        </c:forEach>
        
        <br />
        <c:out value="Total Payment Due: "/>
        <fmt:formatNumber value="${total}" type="currency" currencyCode="NZD" />  
        
        <%--Final order confirmation or cancellation--%>
        <form action="ProcessOrder" method="post">
            <button type="submit">Confirm Order</button>
        </form>
        <br />
        <form action="QueryProductList" method="get">
            <button type="submit">Cancel Order</button>
        </form>
    </body>
</html>
