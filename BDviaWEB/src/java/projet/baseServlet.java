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
import javax.servlet.RequestDispatcher;
import javax.servlet.http.Cookie;

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
"             <tr> <td colspan=\"4\"> Voici l'acceuil </td> <td rowspan=\"3\" width=200> Catégorie <br> <form>"+
"                                                                                         <input type=\"checkbox\" name=\"categorie\" value=\"Sport\"> Humour <br>"+
"                                                                                         <input type=\"checkbox\" name=\"categorie\" value=\"Musique\"> Musique <br>" +
"                                                                                         <input type=\"checkbox\" name=\"categorie\" value=\"Enfant\"> Enfant <br>"+
"                                                                                         <input type=\"checkbox\" name=\"categorie\" value=\"Illusion\"> Illusion <br>"+
"                                                                                         <input type=\"checkbox\" name=\"categorie\" value=\"Danse\"> Danse <br>"+
"                                                                                         <input type=\"checkbox\" name=\"categorie\" value=\"Jeux_Video\"> Jeux Vidéo <br>"+
"                                                                                         <input type=\"checkbox\" name=\"categorie\" value=\"Sport\"> Sport <br>"+
"                                                                                         <input type=\"submit\" name=\"Recherche\" value=\"SubmitCatSearch\"> </td> </tr> </form>"+   
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
            
            // C'est ici que sa marche pas
            
            /* AX: Ok, check. Faut faire ça ici en haut sinon les résulats
            seront effacés lorsqu'on regénérera la page. */
            String btnRecherche = request.getParameter("Recherche");
            
            String[] categorie; /* On va mettre les categories sélectionnées icitte */
            
            if (btnRecherche != null) {
                /*Ici on check si le bouton existe. Dans un autre page que 
                la page d'accueil, le bouton va être null*/
                if (btnRecherche.equals("SubmitCatSearch")) {
                    /* Si le bouton qui a sumbit la page était 'SubmitCatSearch' */
                    categorie = request.getParameterValues("categorie");
                    /* On popule la table de strings */
                    for (int i = 0; i < categorie.length; i++) {
                        System.out.println(categorie[i]); 
                    }
                }
            }
            
            /* Il reste 2 bugs. 
            1- Si tu sélectionne pas de checkbox, la page va pas loader 
            2- Quand la page va reloader, les checkbox devraient rester selectionnés 
            */
            
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
            /*
            Cookie test = new Cookie( "test", "Sa marche" );
            response.addCookie(test);
            Cookie[] tabCookies;
            tabCookies = request.getCookies();
            for (Cookie c : tabCookies) {
                if (c.getName().equals("test")) 
                {
                    out.println("Sa marche tu?" + c.getValue());
                }
            }*/
            
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
