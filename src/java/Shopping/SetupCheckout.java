package Shopping;

import Entities.Vxz7784products;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * This Servlet sets up information for the Checkout jsp
 * @author Tamati Rudd 18045626
 */
@WebServlet(name = "SetupCheckout", urlPatterns = {"/SetupCheckout"})
public class SetupCheckout extends HttpServlet {

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
        //Get Data
        ServletContext application = getServletContext();
        ArrayList<Vxz7784products> products = (ArrayList<Vxz7784products>) application.getAttribute("prodlist");
        String[] selectedProducts = request.getParameterValues("inCart");
        
        //Populate a shopping cart ArrayList
        //If a product ID is present in the product list and checked on the catalogue, add it to the shopping cart
        ArrayList<Vxz7784products> shoppingCart = new ArrayList<>();
        for (Vxz7784products p : products) {
            for (String s : selectedProducts) {
                if (p.getProdID().equals(s)) {
                    if (p.getInStock() > 0)
                        shoppingCart.add(p);
                    else { //An item without stock is selected - disallow selection
                        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/QueryProductList");
                        dispatcher.forward(request, response);
                    }
                }
            }
        }
        HttpSession session = request.getSession(true); //create if none
        session.setAttribute("cart", shoppingCart);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Checkout.jsp");
        dispatcher.forward(request, response);
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
