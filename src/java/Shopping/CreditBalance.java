package Shopping;

import Entities.Vxz7784users;
import Beans.UserFeedback;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.HeuristicMixedException;
import jakarta.transaction.HeuristicRollbackException;
import jakarta.transaction.NotSupportedException;
import jakarta.transaction.RollbackException;
import jakarta.transaction.SystemException;
import jakarta.transaction.UserTransaction;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This servlet handles a user crediting funds to their account
 * @author Tamati Rudd 18045626
 */
@WebServlet(name = "CreditBalance", urlPatterns = {"/CreditBalance"})
public class CreditBalance extends HttpServlet {
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
        
        //Validate that the user input can be converted to a float (monetary value)
        UserFeedback balFeedback = new UserFeedback();
        String input = request.getParameter("payment");
        float payment = 0.00f;
        try {
            payment = Float.parseFloat(input);
        }
        catch (NumberFormatException e) {
            balFeedback.setMessage("Error: please enter a monetary value (format: x.xx)");
            request.setAttribute("balfeedback", balFeedback);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/AddBalance.jsp");
            dispatcher.forward(request, response);
        }
        
        //Update the user's balance in the DB and provide user feedback
        //Zero or negative credits are disallowed
        if (entityManager != null && payment > 0) { 
            try {
                HttpSession session = request.getSession(true);
                Vxz7784users account = (Vxz7784users) session.getAttribute("details");
                account.setBalance(account.getBalance() + payment);
                userTransaction.begin();
                entityManager.merge(account); //Use merge as the entity already exists in the DB
                userTransaction.commit();
                balFeedback.setSuccess(true);
                balFeedback.setMessage("Your new balance is "+account.toString());
                request.setAttribute("balfeedback", balFeedback);
                session.setAttribute("details", account);
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/AddBalance.jsp");
                dispatcher.forward(request, response);
                
            //Catch required exceptions 
            } catch (IllegalArgumentException | SystemException | NotSupportedException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
                Logger.getLogger(CreditBalance.class.getName()).log(Level.SEVERE, null, ex);
                balFeedback.setMessage("A system error has occured. Please try again");
                request.setAttribute("balfeedback", balFeedback);
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/AddBalance.jsp");
                dispatcher.forward(request, response);
            }   
        }
        else { //Float value was 0 or negative
            balFeedback.setMessage("You don't want to lose money for nothing... do you?");
            request.setAttribute("balfeedback", balFeedback);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/AddBalance.jsp");
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
