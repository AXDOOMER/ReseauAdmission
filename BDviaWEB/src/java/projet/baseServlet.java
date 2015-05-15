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
       out.println(
"        <table width=\"100%\" height=auto cellpadding=\"10px\" style=\"background-color:lightgrey\">\n" +
"             <tr> <td colspan=\"4\"> Voici l'acceuil </td> <td rowspan=\"3\" width=200> Catégorie <br> <input type=\"checkbox\" name\"catégorie\" value=\"humour\"> humour <br>"+
"                                                                                         <input type=\"checkbox\" name\"catégorie\" value=\"musique\"> musique <br>" +
"                                                                                         <input type=\"checkbox\" name\"catégorie\" value=\"enfant\"> enfant <br>"+
"                                                                                         <input type=\"checkbox\" name\"catégorie\" value=\"illusion\"> illusion <br>"+
"                                                                                         <input type=\"checkbox\" name\"catégorie\" value=\"danse\"> danse <br>"+
"                                                                                         <input type=\"checkbox\" name\"catégorie\" value=\"cirque\"> cirque <br>"+
"                                                                                         <input type=\"checkbox\" name\"catégorie\" value=\"conference\"> conference <br>"+
"                                                                                         <input type=\"checkbox\" name\"catégorie\" value=\"sport\"> sport <br>"+
"                                                                                         <button>Chercher</button> </td> </tr>"+   
"           <tr> <td rowspan=\"2\">Nom du Spectacle <br> Date </td> <td rowspan=\"2\">Nom du Spectacle <br> Date </td> <td rowspan=\"2\">Nom du Spectacle <br> Date </td> <td rowspan=\"2\">Nom du Spectacle <br> Date </td>" +
"           <tr> </tr>"+        
"           <tr> <td rowspan=\"2\">Nom du Spectacle <br> Date </td> <td rowspan=\"2\">Nom du Spectacle <br> Date </td> <td rowspan=\"2\">Nom du Spectacle <br> Date </td> <td rowspan=\"2\">Nom du Spectacle <br> Date </td>  <td rowspan=\"2\"> Salle <div> <select> <option value=\"Salle1\">Salle 1 </option> </select> <br> <button>Chercher</button> </div> </td>" +
"           <tr> </tr>"+  
"           <tr> <td rowspan=\"2\">Nom du Spectacle <br> Date </td> <td rowspan=\"2\">Nom du Spectacle <br> Date </td> <td rowspan=\"2\">Nom du Spectacle <br> Date </td> <td rowspan=\"2\">Nom du Spectacle <br> Date </td> <td rowspan=\"2\"> Artiste <div> <input type=\"text\"/> <button>Chercher</button>  </div> </td>"+        
"           <tr> </tr>"+  
"        </table>"
       );
    }
    
    public void achatDeBillets(PrintWriter out)
    {
       out.println(
"      <table width=\"100%\" cellpadding=\"10px\">\n" +
"      <tr> <td> Achat de Billets </td> </tr>" +
"      <div class=\"affichediv\"> ");
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
            out.println(" .titre { table-layout: fixed;"+
                                 " font-size:20px;  }\n" +
"                         .acceuil { width:20%; ");
            out.println("</style>");
            
            
            out.println("<title>Servlet ReseauAdmission</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<table class=\"titre\" border=\"1px\" cellpadding=\"10px\" width=\"100%\" style=\"background-color:grey; text-align:center\"> <tr> <td class=\"acceuil\">Acceuil</td>"
                     + " <td class=\"acceuil\">Achats de billets</td>"
                     + " <td class=\"acceuil\">Panier</td>"
                     + " <td class=\"acceuil\">Inscription</td>"
                     + " <td class=\"acceuil\">"+
"                    Utilisateur: <BR/>\n" +
"                    <input type=text name=utilisateur id=utilisateur><BR/>\n" +
"                    Mot de passe: <BR/>\n" +
"                    <input type=password name=motdepasse id=motdepasse><BR/> "+
"                    <button>Login</button>"+
"                    </td> </tr> </table> </div> ");
            acceuil(out);
            achatDeBillets(out);
            /*out.println("<h1>Bravo! Tu est connecté au servlet. </h1>");
            out.println("<h2>C'est juste une page de test bin simple</h2>");
            out.println("<hr/>");
            out.println("Maintenant, loginnez-vous à la base de données: <BR/><BR/>");

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
