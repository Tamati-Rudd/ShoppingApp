package Login;

import Beans.LoginAttempts;
import Entities.Vxz7784users;
import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpSession;

/**
 * This Servlet processes a login request 
 * @author Tamati Rudd 18045626
 */
@WebServlet(name = "LoginProcessor", urlPatterns = {"/LoginProcessor"})
public class LoginProcessor extends HttpServlet {
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
        
        //Populate an Entity with provided login data
        Vxz7784users account = new Vxz7784users();
        account.setUsername(request.getParameter("username"));
        account.setPassword(request.getParameter("password"));
        
        //Increment login attempts session Bean
        HttpSession session = request.getSession(true); //create if none
        LoginAttempts attempts = (LoginAttempts) session.getAttribute("UserLoginAttempts");
        attempts.setAttempts(attempts.getAttempts()+1);
        session.setAttribute("UserLoginAttempts", attempts);  
        
        //Use JPQL to determine whether the requested user account exists in the DB
        List<Vxz7784users> accountList = null;
        if (entityManager != null) { //Create & run JPQL query
            String jpql = "SELECT a FROM Vxz7784users a WHERE a.username = :un AND a.password = :pw";
            Query query = entityManager.createQuery(jpql);
            query.setParameter("un", account.getUsername());
            query.setParameter("pw", account.getPassword());
            accountList = query.getResultList();
        }
        
        //Verify whether the Account was found in the DB or not
        boolean found = false;
        for (Vxz7784users a : accountList) { 
            if (a.getUsername().equals(account.getUsername()) && a.getPassword().equals(account.getPassword())) {
                found = true;
                account = a; //Get the balance of the found account for the shopping page
            }
        }
        
        //Navigate to the ShoppingPage if found, or back to the index if not found
        if (found) {
            session.setAttribute("details", account);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/QueryProductList");
            dispatcher.forward(request, response);
        }
        else {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
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
