/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet;
// POUR EXECUTER LE PROJET, FAIRE BOUTON DROIT SUR 'baseServlet.java' ET PUIS 'Run File'. 

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author alexandre-xavier
 */
@WebServlet(name = "baseServlet", urlPatterns = {"/baseServlet"})
public class baseServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    //methode pour créer les pages webs    
    public void acceuil(PrintWriter out)
    {
        out.append(
"      <table width=\"100%\" cellpadding=\"10px\">\n" +
"      <td> Catégorie </td>\n" +
"      <tr> <td>Nom du Spectacle</td> <td>Nom du Spectacle</td> <td>Nom du Spectacle</td> <td>Nom du Spectacle</td> <td> <div> Chercher  </div> </td>\n" +
"      <tr> <td>date</td> <td>Date</td> <td>Date</td> <td>Date</td>" +
"      <tr> <td>Nom du Spectacle</td> <td>Nom du Spectacle</td> <td>Nom du Spectacle</td> <td>Nom du Spectacle</td> <td> <div> Chercher  </div> </td>\n" +
"      <tr> <td>date</td> <td>Date</td> <td>Date</td> <td>Date</td>" +
"      <tr> <td>Nom du Spectacle</td> <td>Nom du Spectacle</td> <td>Nom du Spectacle</td> <td>Nom du Spectacle</td> <td> <div> Chercher </div> </td>\n" +
"      <tr> <td>date</td> <td>Date</td> <td>Date</td> <td>Date</td>" +
"      </table>"
       );
    }
    
    public void achatDeBillets(PrintWriter out)
    {
        
    }
    
    public void panier(PrintWriter out)
    {
        
    }
    
    public void inscription(PrintWriter out)
    {
        
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            
            
            out.println("<style>");
            out.println("</style>");
            
            
            out.println("<title>Servlet ReseauAdmission</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<table width=\"100%\"> <tr> <td >Acceuil</td> <td>Achats de billets</td> <td>Panier</td> <td>Inscription</td> <td>Login</td> </tr> </table> ");
            acceuil(out);
            /*out.println("<h1>Bravo! Tu est connecté au servlet. </h1>");
            out.println("<h2>C'est juste une page de test bin simple</h2>");
            out.println("<hr/>");
            out.println("Maintenant, loginnez-vous à la base de données: <BR/><BR/>");
            out.println("Utilsateur: <BR/>");
            out.println("<input type=text name=utilisateur id=utilisateur><BR/>");
            out.println("Mot de passe: <BR/>");
            out.println("<input type=password name=motdepasse id=motdepasse><BR/>");
            out.println("<BR/><BUTTON>Essayer de se connecter...</BUTTON>");*/
            out.println("</body>");
            out.println("</html>");
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
