package Shopping;

import Beans.UserFeedback;
import Entities.Vxz7784orders;
import Entities.Vxz7784products;
import Entities.Vxz7784users;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * This Servlet generates an order summary - which summarizes all order details before final confirmation
 * @author Tamati Rudd 18045626
 */
@WebServlet(name = "CalculateTotal", urlPatterns = {"/CalculateTotal"})
public class CalculateTotal extends HttpServlet {
    @PersistenceContext(unitName="ShoppingAppPU") private EntityManager entityManager;
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        UserFeedback feedback = new UserFeedback();
        
        //Get quantity of each order product from request. Validate that a number  <= 1 was input
        HttpSession session = request.getSession(true); //create if none
        ArrayList<Vxz7784products> cart = (ArrayList<Vxz7784products>) session.getAttribute("cart");
        Vxz7784users account = (Vxz7784users) session.getAttribute("details");
        int[] quantities = new int[cart.size()];
        int index = 0;
        try {
            for (Vxz7784products p : cart) {
                int quantity = Integer.parseInt(request.getParameter(p.getProdID())); //Get quantity from request
                quantities[index] = quantity;
                index++;
            }
        }
        catch (NumberFormatException e) {
            feedback.setMessage("Error: all quantities must be positive whole numbers");
            request.setAttribute("checkoutfb", feedback);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Checkout.jsp");
            dispatcher.forward(request, response);
        }
        
        //Create an Order for each product in the shopping cart
        ArrayList<Vxz7784orders> orders = new ArrayList<>();
        float grandTotal = 0;
        int nextOrderId = determineOrderID(); //Get first orderID for this set of orders
        index = 0;
        for (Vxz7784products p : cart) {
            Vxz7784orders order = new Vxz7784orders();
            order.setOrderID(Integer.toString(nextOrderId));
            nextOrderId++;
            order.setUsername(account.getUsername());
            order.setProdID(p.getProdID());
            if (quantities[index] < 1 || p.getInStock() - quantities[index] < 0) { //Disallow negative stock and zero or negative quantity
                feedback.setMessage("Error: a quantity is less than 1 or would take an item to negative stock");
                request.setAttribute("checkoutfb", feedback);
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Checkout.jsp");
                dispatcher.forward(request, response);
            }
            order.setQuantity(quantities[index]);
            index++;
            order.setTotal(p.getPrice() * order.getQuantity());
            orders.add(order);
            grandTotal = grandTotal + order.getTotal(); //Add to grand total
        }

        //Validate user can afford the orders
        Vxz7784users acc = (Vxz7784users) session.getAttribute("details");
        if (acc.getBalance() >= grandTotal) {
            session.setAttribute("orders", orders);
            session.setAttribute("total", grandTotal);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/OrderSummary.jsp");
            dispatcher.forward(request, response);
        }
        else { //Cannot afford
            feedback.setMessage("You do not have enough balance on your account to complete this order!");
            request.setAttribute("checkoutfb", feedback);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Checkout.jsp");
            dispatcher.forward(request, response);
        }
    }

    //Determine what OrderID to use for a new order by querying to see the most recent value
    public int determineOrderID() {
        String jpql = "SELECT o FROM Vxz7784orders o";
        Query query = entityManager.createQuery(jpql);
        List<Vxz7784orders> results = query.getResultList();
        if (results.isEmpty()) //No orders exist in the table
            return 1;
        
        //Find the highest order ID in the DB, then return that value + 1
        int decision = Integer.MIN_VALUE;
        for (Vxz7784orders o : results) {
            int id = Integer.parseInt(o.getOrderID());
            if (id > decision)
                decision = id;
        }
        return decision+1;
    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
