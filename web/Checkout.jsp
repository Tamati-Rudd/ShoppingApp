<%-- 
    Document   : Checkout
    Created on : 26/03/2022, 7:40:24 pm
    Author     : Tamati Rudd
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<jsp:useBean id="details" class="Entities.Vxz7784users" scope="session"/>
<jsp:useBean id="prodlist" class="java.util.ArrayList" scope="application"/>
<jsp:useBean id="cart" class="java.util.ArrayList" scope="session"/>
<jsp:useBean id="checkoutfb" class="Beans.UserFeedback" scope="request"/>
<html>
    <style> <%--CSS style to give the Catalogue table a border--%>
    table, th, td { 
        border:1px solid black;
    }
    </style>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Checkout</title>
    </head>
    <body>
        <h1>Checkout</h1>
        <h3>Account Details</h3>
        <%--Print the user's name and balance--%>
        <c:out value="User: ${details.username}"/>
        </br >
        <c:out value="Balance: ${details.toString()}"/>
        </br >
        
        <h3>Quantity of Cart Products Available</h3>
        <table>
                <tr>
                    <td>Product ID</td>
                    <td>Product Name</td>
                    <td>Price (NZD) </td> 
                    <td>In Stock</td>
                </tr>      
                <c:forEach items="${cart}" var="Vxz7784products">
                    <tr>
                        <td>${Vxz7784products.prodID}</td>
                        <td><c:out value="${Vxz7784products.prodName}" /></td>
                        <td><fmt:formatNumber value="${Vxz7784products.price}" type="currency" currencyCode="NZD" /></td> 
                        <td><c:out value="${Vxz7784products.inStock}" /></td>
                    </tr>      
                </c:forEach>  
        </table>

        <h3>Enter Order Details</h3>
        <c:out value="${checkoutfb.message}"/>
        <c:choose>
            <c:when test="${requestScope.checkoutfb.success == false}"> <%--prompt entering of quantity for each order item--%>
                <form action="CalculateTotal" method="post">
                    <c:forEach items="${cart}" var="Vxz7784products">
                        <c:out value="Product: ${Vxz7784products.prodName}"/> 
                        <br />
                        <c:out value="Unit Price: "/>
                        <fmt:formatNumber value="${Vxz7784products.price}" type="currency" currencyCode="NZD" />  
                        <p>Quantity: *
                        <input type="text" name="${Vxz7784products.prodID}" required></p>
                        <br />
                    </c:forEach>
                    <p>* Indicates required field</p>
                    <button type="submit">Place Order</button>
                </form>
                
                <br />
                <form action="QueryProductList" method="get"> <%--order cancellation--%>
                    <button type="submit">Cancel Order</button>
                </form>
            </c:when>
        </c:choose>
    </body>
</html>
