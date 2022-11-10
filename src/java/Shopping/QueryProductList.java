package Shopping;

import Entities.Vxz7784products;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * This Servlet queries the DB to populate an Application Bean with the product list
 * This Servlet should always be used before navigating to Catalogue.jsp
 * @author Tamati Rudd 18045626
 */
@WebServlet(name = "QueryProductList", urlPatterns = {"/QueryProductList"})
public class QueryProductList extends HttpServlet {
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
        
        if (entityManager != null) { 
            //Create & run JPQL query to get the product list
            String jpql = "SELECT p FROM Vxz7784products p";
            Query query = entityManager.createQuery(jpql);
            List<Vxz7784products> results = query.getResultList();
            
            //Save the result as a Bean
            ArrayList<Vxz7784products> products = new ArrayList();
            for (Vxz7784products p : results) {
                products.add(p);
            }
            ServletContext application = getServletContext();
            application.setAttribute("prodlist", products);
            
            //Navigate to shopping Catalogue
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Catalogue.jsp");
            dispatcher.forward(request, response);
        }
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
