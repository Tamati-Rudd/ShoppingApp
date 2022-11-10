package Shopping;

import Entities.Vxz7784orders;
import Entities.Vxz7784products;
import Entities.Vxz7784users;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This Servlet updates the database tables in response to a confirmed order
 * @author Tamati Rudd 18045626
 */
@WebServlet(name = "ProcessOrder", urlPatterns = {"/ProcessOrder"})
public class ProcessOrder extends HttpServlet {
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
        
        //Get Bean data
        ServletContext application = getServletContext();    
        HttpSession session = request.getSession(true); //create if none
        Vxz7784users user = (Vxz7784users) session.getAttribute("details");   
        ArrayList<Vxz7784products> products = (ArrayList<Vxz7784products>) application.getAttribute("prodlist");
        ArrayList<Vxz7784orders> orders = (ArrayList<Vxz7784orders>) session.getAttribute("orders");
        Float totalPayment = (Float) session.getAttribute("total");
        
        //Update Database Tables to Reflect Orders
        try {
            
            //Add Orders to Order Table
            userTransaction.begin();
            for (Vxz7784orders o : orders)
                entityManager.persist(o);
            
            //Update Products table to reflect decrease in stock available
            for (Vxz7784products p : products) {
                for (Vxz7784orders o : orders) {
                    if (p.getProdID().equals(o.getProdID())) {
                        p.setInStock(p.getInStock()-o.getQuantity());
                        entityManager.merge(p);
                    }
                }
            }
            
            //Update user balance in Users Table
            user.setBalance(user.getBalance()-totalPayment);
            entityManager.merge(user);
            userTransaction.commit();
            
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/OrderComplete.jsp");
            dispatcher.forward(request, response);
            
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            Logger.getLogger(ProcessOrder.class.getName()).log(Level.SEVERE, null, ex);
            try {
                userTransaction.rollback();
            } catch (IllegalStateException | SecurityException | SystemException ex1) {
                Logger.getLogger(ProcessOrder.class.getName()).log(Level.SEVERE, null, ex1);
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
