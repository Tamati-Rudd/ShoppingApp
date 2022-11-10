package Login;

import Beans.UserFeedback;
import Entities.Vxz7784users;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.HeuristicMixedException;
import jakarta.transaction.HeuristicRollbackException;
import jakarta.transaction.NotSupportedException;
import jakarta.transaction.RollbackException;
import jakarta.transaction.SystemException;
import jakarta.transaction.UserTransaction;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class handles the creation of a new account
 * @author Tamati Rudd 18045626
 */
@WebServlet(name = "CreateAccount", urlPatterns = {"/CreateAccount"})
public class CreateAccount extends HttpServlet {
    //Obtain an EntityManager & create the userTransaction resource to allow saving to DB
    @PersistenceContext(unitName="ShoppingAppPU") private EntityManager entityManager;
    @Resource private UserTransaction userTransaction;
    
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
        /*Validate User Input. Rules:
            - Username must contain only alphanumeric characters  (must match regular expression)
            - Username must be 3-20 characters in length
            - Password must be 5-25 characters in length
            If one of these rules is not met, send the user back to the form with an appropriate message
        */
        UserFeedback feedback = new UserFeedback();
        boolean valid = true;
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        username = username.trim();
        if (!username.matches("^[a-zA-Z0-9]*$")) { 
            valid = false;
            feedback.setMessage("Error: your username must contain only letters & numbers (no spaces)");
        }
        if (username.length() < 3 || username.length() > 20) {
            valid = false;
            feedback.setMessage("Error: your username must be 3-20 characters long");
        }
        if (password.length() < 5 || password.length() > 25) {
            valid = false;
            feedback.setMessage("Error: your password must be 5-25 characters long");
        }
        if (!valid) {
            request.setAttribute("feedback", feedback);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/NewAccount.jsp");
            dispatcher.forward(request, response);
        }
        
        //Populate an Entity with provided login data
        Vxz7784users account = new Vxz7784users();
        account.setUsername(request.getParameter("username"));
        account.setPassword(request.getParameter("password"));
        account.setBalance(0.00f);

        if (entityManager != null && valid) { 
            try {
                //Attempt a transaction to persist the new account to the DB
                userTransaction.begin();
                entityManager.persist(account);
                userTransaction.commit();
                
                //If the transaction succeeds, notify the user of account creation success
                request.setAttribute("newuser", account);
                feedback.setSuccess(true);
                feedback.setMessage("Your new account "+account.getUsername()+ " has been created!");
                request.setAttribute("feedback", feedback);
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/NewAccount.jsp");
                dispatcher.forward(request, response);
                
            //Catch required exceptions & handle duplicate usernames
            } catch (EntityExistsException | NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
                Logger.getLogger(CreateAccount.class.getName()).log(Level.SEVERE, null, ex);
                feedback.setMessage("Error: That username already exists! Please choose a different username");
                request.setAttribute("feedback", feedback);
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/NewAccount.jsp");
                dispatcher.forward(request, response);
            } 
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
