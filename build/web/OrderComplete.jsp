<%-- 
    Document   : OrderComplete
    Created on : 27/03/2022, 12:56:14 am
    Author     : Tamati Rudd
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Order Complete</title>
    </head>
    <body>
        <h1>Order Complete!</h1>
        <h3>Your Order was Processed Successfully!</h3>
        
        <%--Return to Catalogue--%>
        <form action="QueryProductList" method="get">
            <button type="submit">Return to Catalogue</button>
        </form>
    </body>
</html>
