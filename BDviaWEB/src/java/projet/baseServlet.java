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

// JDBC
import java.sql.DriverManager;
import java.sql.*;
import oracle.jdbc.OracleDriver;
import oracle.jdbc.pool.*;
import java.util.*;
import oracle.jdbc.OracleTypes;

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
    
    private String urlBD = "jdbc:oracle:thin:@mercure.clg.qc.ca:1521:orcl";
    private String userName = "Labontel";
    private String password = "ORACLE2";
    
    //methode pour créer les pages webs    
    public void acceuil(PrintWriter out, String[] categorie) {
        out.println("<table width=\"100%\" height=auto cellpadding=\"10px\" style=\"background-color:lightgrey\">\n");
        out.println("<tr> <td colspan=\"4\"> Voici l'acceuil </td> <td rowspan=\"3\" width=200> Catégorie <br> <form>");

        out.print("<input type=\"checkbox\" name=\"categorie\" value=\"Humour\"");
        for (int i = 0; categorie != null && i < categorie.length; i++) {
            if (categorie[i].equals("Humour")) {
                out.print(" Checked");
            }
        }
        out.println("> Humour <br>");

        out.print("<input type=\"checkbox\" name=\"categorie\" value=\"Musique\"");
        for (int i = 0; categorie != null && i < categorie.length; i++) {
            if (categorie[i].equals("Musique")) {
                out.print(" Checked");
            }
        }
        out.println("> Musique <br>");

        out.print("<input type=\"checkbox\" name=\"categorie\" value=\"Enfant\"");
        for (int i = 0; categorie != null && i < categorie.length; i++) {
            if (categorie[i].equals("Enfant")) {
                out.print(" Checked");
            }
        }
        out.println("> Enfant <br>");

        out.print("<input type=\"checkbox\" name=\"categorie\" value=\"Illusion\"");
        for (int i = 0; categorie != null && i < categorie.length; i++) {
            if (categorie[i].equals("Illusion")) {
                out.print(" Checked");
            }
        }
        out.println("> Illusion <br>");

        out.print("<input type=\"checkbox\" name=\"categorie\" value=\"Danse\"");
        for (int i = 0; categorie != null && i < categorie.length; i++) {
            if (categorie[i].equals("Danse")) {
                out.print(" Checked");
            }
        }
        out.println("> Danse <br>");

        out.print("<input type=\"checkbox\" name=\"categorie\" value=\"Jeux_Video\"");
        for (int i = 0; categorie != null && i < categorie.length; i++) {
            if (categorie[i].equals("Jeux_Video")) {
                out.print(" Checked");
            }
        }
        out.println("> Jeux Vidéo <br>");

        out.print("<input type=\"checkbox\" name=\"categorie\" value=\"Sport\"");
        for (int i = 0; categorie != null && i < categorie.length; i++) {
            if (categorie[i].equals("Sport")) {
                out.print(" Checked");
            }
        }
        out.println("> Sport <br>");

        out.println("<input type=\"submit\" name=\"Recherche\" value=\"SubmitCatSearch\"> </td> </tr> </form>");

        out.println(
                "           <tr> <td rowspan=\"2\">Nom du Spectacle <br> Date </td> <td rowspan=\"2\">Nom du Spectacle <br> Date </td> <td rowspan=\"2\">Nom du Spectacle <br> Date </td> <td rowspan=\"2\">Nom du Spectacle <br> Date </td>"
                + "           <tr> </tr>"
                + "           <tr> <td rowspan=\"2\">Nom du Spectacle <br> Date </td> <td rowspan=\"2\">Nom du Spectacle <br> Date </td> <td rowspan=\"2\">Nom du Spectacle <br> Date </td> <td rowspan=\"2\">Nom du Spectacle <br> Date </td>  <td rowspan=\"2\"> Salle <div> <select> <option value=\"Salle1\">Salle 1 </option> </select> <br> <button>Chercher</button> </div> </td>"
                + "           <tr> </tr>"
                + "           <tr> <td rowspan=\"2\">Nom du Spectacle <br> Date </td> <td rowspan=\"2\">Nom du Spectacle <br> Date </td> <td rowspan=\"2\">Nom du Spectacle <br> Date </td> <td rowspan=\"2\">Nom du Spectacle <br> Date </td> <td rowspan=\"2\"> Artiste <div> <input type=\"text\"/> <button>Chercher</button>  </div> </td>"
                + "           <tr> </tr>"
                + "        </table>"
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

            /* AX: Ok, check. Faut faire ça ici en haut sinon les résulats
            seront effacés lorsqu'on regénérera la page. */
            String btnRecherche = request.getParameter("Recherche");
            
            String[] categorie = null; /* On va mettre les categories sélectionnées icitte */
            
            if (btnRecherche != null) {
                /*Ici on check si le bouton existe. Dans un autre page que 
                la page d'accueil, le bouton va être null*/
                if (btnRecherche.equals("SubmitCatSearch")) {
                    /* Si le bouton qui a sumbit la page était 'SubmitCatSearch' */
                    categorie = request.getParameterValues("categorie");
                    if (categorie != null) {
                        /* On popule la table de strings */
                        for (int i = 0; i < categorie.length; i++) {
                            System.out.println(categorie[i]); 
                        }
                    }
                }
            }
            
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
            acceuil(out, categorie);
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
            
            faireTableSpectacles(out, categorie);
            
            out.println("</body>");
            out.println("</html>");   
                       
        }
    }
    
    private Connection seConnecter()
    {
        Connection conn = null;
        
        try {
            OracleDataSource ods = new OracleDataSource();
            ods.setURL(urlBD);
            ods.setUser(userName);
            ods.setPassword(password);
            conn = ods.getConnection();
        } catch (SQLException sqlex) {
            System.out.println(sqlex.getMessage());
        }
        
         return conn;
    }
    
    private void deconnexion(Connection conne)
    {
        // C'est une bitch blonde
        try {
            conne.close();
        } catch (SQLException se) {
            System.out.println("La conne ne s'est pas fermée");
            conne = null;
        }
    }



    private void faireTableSpectacles(PrintWriter out, String[] categories)
    {
        Connection oracleConne = seConnecter(); // Oracle s'tune conne
        
        try
        {
            CallableStatement stm2 =oracleConne.prepareCall("{? = call TPF_BD_JAVA.GetSpectacleParCat(?)}",
            ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stm2.registerOutParameter(1,OracleTypes.CURSOR);
            stm2.registerOutParameter(2,OracleTypes.CURSOR);
             //execution de la procédure
            // Caster le paramètre de retour en ResultSet
                      /*  ResultSet rest = stm2.executeQuery();   */
            stm2.execute();
            ResultSet rest = (ResultSet)stm2.getObject(1);
            out.println("<table>");
            int i = 0;
            while (rest.next())
            {
                if (i == 0)
                {
                    out.println("<tr>");
                }
                
                out.println("<td>");
                String Categorie = rest.getString("CATEGORIE");
                String Prix = rest.getString("PRIXDEBASE");
                String Artiste = rest.getString("ARTISTE");
                String Nom = rest.getString("NOMSPECTACLE");
                String Image = rest.getString("AFFICHE");
                System.out.print(Nom + " par " +Artiste + " de " + Categorie + " pour " + Prix + "$");
                out.println("<img src=\"affiches/" + Image + "\" width=\"180px\" height=\"200px\" " + ">");
                out.println("</td>");
                
                i++;
                
                if (i == 5)
                {
                    out.println("</tr>");
                    i = 0;
                }
            }
            stm2.clearParameters();
            stm2.close();
            rest.close();
            out.println("</table>");
        }
        catch (SQLException sqlex) {
            out.println(sqlex.getMessage());
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
