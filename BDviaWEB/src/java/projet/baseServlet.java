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
        out.println("<table class=\"acceuil\" width=\"100%\" height=auto cellpadding=\"15px\" style=\"background-color:rgb(175,175,175)\">\n");
        out.println("<tr> <td colspan=\"2\" style=\"text-align:center; background-color:grey; border-radius:10px; border:1px white solid; font-size:17px;\"> Vous êtes à l'acceuil </td></tr> <tr><td rowspan=4>" );
                faireTableSpectacles(out, categorie) ;
                out.println( "</td><td style=\"vertical-align:top; border:1px white solid; border-radius:10px; height:150px; width:200px;\"> Catégorie <br> <form>");

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

        // On fait la grosse cellule
        
        out.println("<input type=\"submit\" name=\"Recherche\" value=\"SubmitCatSearch\"> <BR>");

        // Combo box pour les salles de spectacle
        try 
        {
            Connection oracleConne = seConnecter();
            ResultSet rest = null;
            CallableStatement stmCombo = oracleConne.prepareCall("{? = call TPF_BD_JAVA.ListeSalles(?)}",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stmCombo.registerOutParameter(1, OracleTypes.CURSOR);
            stmCombo.registerOutParameter(2, OracleTypes.CURSOR);
            //execution de la procédure
            // Caster le paramètre de retour en ResultSet
            stmCombo.execute();

            rest = (ResultSet) stmCombo.getObject(1);
        } catch (SQLException sqlex) {
            System.out.println(sqlex.getMessage());
        }
        
        out.println("<BR><HR><BR>Salle <div> <select> <option value=\"Salle1\">Salle 1 </option> </select> <br><br> <button>Chercher</button> </div> ");
        out.println("<BR><HR><BR>Artiste <div> <input type=\"text\"/> <button>Chercher</button>  </div> ");
        
        out.println("</td> </tr> </form>");
        
        out.println(
                "</tr>" /*<tr colspan=\"8\"> <td rowspan=\"8\">Nom du Spectacle <br> Date*/
                + "<tr><td>  </td></tr>"
                + "<tr><td> </td></tr>"
                + "</table>"
        );
        
        /*Salle <div> <select> <option value=\"Salle1\">Salle 1 </option> </select> <br> <button>Chercher</button> </div> */
        /*Artiste <div> <input type=\"text\"/> <button>Chercher</button>  </div> */
    }
    
    public void achatDeBillets(PrintWriter out)
    {
        String RepValues = TrouverRepValues("Match de hokcey");
        //String SalleValues = TrouverRepValues("Match de hokcey");
        Connection oracleConne = seConnecter(); // Oracle s'tune conne
        try {          
            CallableStatement Callist =
            oracleConne.prepareCall(" { call TPF_BD_JAVA.AfficherSpectacleParNom(?,?)}");
            Callist.registerOutParameter(1,OracleTypes.CURSOR);
            Callist.setString(2,"Match de hokcey");
            Callist.execute();
            ResultSet rstlist = (ResultSet)Callist.getObject(1);
                        
            //codespectacle,nomcat,prixdebase,artiste,nomspectacle,affiche,description           
                rstlist.next();
                
                int codespectacle = rstlist.getInt(1);
                String nomcat = rstlist.getString(2);
                int prixdebase = rstlist.getInt(3);                
                String artiste = rstlist.getString(4);
                String nomspectacle = rstlist.getString(5);
                String affiche = rstlist.getString(6);
                String description = rstlist.getString(7);
                
            Callist.clearParameters();
            Callist.close();
            rstlist.close();
 
        out.println("<table class=\"acceuil\" cellpadding=\"10px\" width=\"100%\" style=\"border:1px white solid; background-color:rgb(175,175,175); height:80%; border-radius:10px;\">"+
			" <tr> <td rowspan=\"3\" colspan=\"13\" style=\"text-align:center; border-radius:10px; border:1px white solid; background-color:grey;\"> Achats de Billets  </tr> "+
                        " <tr> </tr> "+
                        " <tr> </tr> "+
			" <tr> <td rowspan=\"10\" colspan=\"5\" width=\"50%\"> <img src=\"affiches/" + affiche + "\" width=\"250px\" height=\"300px\" " + "> </td> <td> Catégorie <label>"+ nomcat +"</label> </td> "+ 
			" <td rowspan=\"8\" colspan=\"13\" style=\"text-align:center;\" width=\"20%\"> Nb de billet voulu:  <input type=\"textbox\" name=\"billetvoulu\"> <br> Total: <input type=\"textbox\" name=\"total\"> <br> <button> Ajouter au Panier </button> </td></tr>"+
			" <tr> <td> Artiste : <label name=\"AchatArtiste\">" + artiste + "</label> </td> </tr>"+ 
			" <tr> <td> Nom Spectacle : <label name=\"AchatNomSpectacle\">"+nomspectacle+"</label> </td> </tr>"+
			" <tr> <td> Prix de base : <label name=\"AchatPrixBase\">"+prixdebase+"</label> </td> </tr>"+
                        " <tr> <td> Representation : <select name=\"AchatRepresentation\">"+RepValues+"</select> </td> </tr>"+              
                        " <tr> <td> Salle : <select name=\"AchatSalle\"> <option value=\"salle\">salle</option> </select> </td> </tr>"+
			" <tr> <td> Section : <select name=\"AchatSection\"> <option value=\"section\">section</option> </select> </td> </tr>"+
			" <tr> <td> Prix : "+prixdebase+" + Prix de section <label>LABEL</label> </td> </tr>"+
			" <tr> <td> Nombre de billet restant pour la section: <label>LABEL</label>  </td> </tr>"+
			" <tr> <td> Nb de billet restant total <label>LABEL</label> </td> </tr>"+
			" <tr>  </tr>"+
                   "</table>");
            }
        catch(SQLException list)
        {
            System.out.println(list.getMessage());
        }     
    }
    public String TrouverRepValues(String nomSpectacle)
    {
        String repValues = "";
        Connection oracleConne = seConnecter(); // Oracle s'tune conne
        try {          
            CallableStatement Callist =
            oracleConne.prepareCall(" { call TPF_BD_JAVA.AfficherReprParNomSpec(?,?)}");
            Callist.registerOutParameter(1,OracleTypes.CURSOR);
            Callist.setString(2,"Match de hokcey");
            Callist.execute();
            ResultSet rstlist = (ResultSet)Callist.getObject(1);
                        
            //codespectacle,nomcat,prixdebase,artiste,nomspectacle,affiche,description           
                while(rstlist.next())
                {
                    int codeRep = rstlist.getInt(1);
                    
                    repValues = repValues + "<option value=\""+Integer.toString(codeRep)+"\" >" + Integer.toString(codeRep) + "</option>";
                }
                
                
            Callist.clearParameters();
            Callist.close();
            rstlist.close();
            }
        catch(SQLException list)
        {
            System.out.println(list.getMessage());
        }
        return repValues;
    }
    public void panier(PrintWriter out)
    {
        out.println("<table border=\"1px\" cellpadding=\"10px\" width=\"100%\" style=\"background-color:rgb(175,175,175); border-radius:10px; border:1px white solid; height:80%; color:white;\">\n" +
"				<tr style=\"text-align:center\"> <td colspan=\"2\" height=\"70%\" style=\"background-color:grey; border-radius:10px; border:1px white solid;\"> Mon Panier </td> </tr>\n" +
"				<tr style=\"text-align:center\"> <td width=\"75%\" style=\"border:none;\"> <label>LABEL</label> <button>Modifier les achats</button> </td> <td rowspan=\"2\" style=\"border:none;\"> <button>Confirmer l'achat</button> <br> Prix total de l'achat: <label>LABEL</label> </td> </tr>\n" +
"			</table>");
    }
    
    public void inscription(PrintWriter out)
    {
        out.println("<form><table class=\"acceuil\" cellpadding=\"10px\" width=\"100%\" style=\"border:1px white solid; background-color:rgb(175,175,175); height:80%; border-radius:10px;\"> "
                + "<tr> <td style=\"text-align:center; background-color:grey; border:1px white solid; border-radius:10px;\"colspan=\"2\"> Veuillez remplir les champs suivants </td> </tr> "
                + "<tr> <td class=\"inputtext\" name=\"inscription\" value=\"nom\" style=\"height:20%; text-align:right;\"> Nom: </td> <td> <input type=\"textbox\"> </td> </tr>"
                + "<tr> <td class=\"inputtext\" name=\"inscription\" value=\"prenom\" style=\"height:20%; text-align:right;\"> Prénom: </td> <td> <input type=\"textbox\"> </td> </tr>"
                + "<tr> <td class=\"inputtext\" name=\"inscription\" value=\"username\" style=\" text-align:right;\"> Nom d'utilisateur: </td> <td> <input type=\"textbox\"> </td> </tr>"
                + "<tr> <td class=\"inputtext\" name=\"inscription\" value=\"motpasse\" style=\" text-align:right;\"> Mot de passe: </td> <td> <input type=\"textbox:\"> </td> </tr>"
                + "<tr> <td class=\"inputtext\" name=\"inscription\" value=\"telephone\" style=\" text-align:right;\"> Téléphone: </td> <td> <input type=\"textbox:\"> </td> </tr>"
                + "<tr> <td class=\"inputtext\" name=\"inscription\" value=\"adresse\" style=\" text-align:right;\"> Adresse: </td> <td> <input type=\"textbox:\"> </td> </tr> \n"
                + "<tr> <td></td> <td> " /*<button>S'inscrire...</button>*/
                + "<input class=\"inputtext\" type=\"submit\" name=\"inscription\" value=\"S'inscrire...\"></td>"
                + "</table></form>"
                + "</div>"
                + "</div>");
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            String[] categorie = null; /* On va mettre les categories sélectionnées icitte */
            //Phil: on mets les biscuits dans le four... sa va etre pret dans +-30min
            int unMois = 30 * 24 * 60 * 60;
            String cookiecatrecu = "";
            String catselec = "";

            Cookie[] tabcookies = request.getCookies();
            for(Cookie c : tabcookies)
            {
                if(c.getName().equals("categorie"))
                {
                    cookiecatrecu = c.getValue();
                }
            }
            out.println(cookiecatrecu);
            
            if(!cookiecatrecu.equals(""))
            {
                categorie = cookiecatrecu.split(",");
            }
            
            /* AX: Ok, check. Faut faire ça ici en haut sinon les résulats
            seront effacés lorsqu'on regénérera la page. */
            String btnRecherche = request.getParameter("Recherche");

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
            
            //Phil: on mets les biscuits dans le four... sa va etre pret dans +-30min
            if(categorie != null)
            {
                for(String s: categorie )
                {
                        catselec = catselec + s + ",";
                }
            }
            
            Cookie catcookie = new Cookie( "categorie", catselec);
            catcookie.setMaxAge(unMois);
            response.addCookie(catcookie);
            
            
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            
            
            out.println("<style>");
            out.println(" .titre { table-layout: fixed;"+
                        " font-size:20px;  }"
                       + ".acceuil { "
                       + " color:white;"
                       + " border-radius:10px;"
                       + " border:1px white solid;");
            out.println(".Inscription" +
"                        {"+
"                           width:100%;" +
"                           height:80%;" +
"                           text-align:center;" +
"                           font-size:15px;" +
"                           top:20%;" +
"                           left:20px;n" +
"                           background-color:rgb(211,211,198);" +
"			}" +
"			.information" +
"			{" +
"                           top:35%;" +
"                           position:absolute;" +
"                           left:35%;" +
"                           height:500px;" +
"                           width:30%;" +
"                           background-color:rgb(175,174,147);" +
"                           padding:35px;" +
"                           text-align:center;" +
"			}" +
"			.input" +
"			{" +
"                           width:100%;" +
"                           text-align:center;" +
"                           height:100px;" +
"			}" +
"                       .inputtext{ text-align:right; width:50%;}");                    
            out.println("</style>");
            
            
            out.println("<title>RéseauAdmission</title>");            
            out.println("</head>");
            out.println("<body style=\"background-color:grey;\">");
            out.println("<table class=\"titre\" cellpadding=\"10px\" width=\"100%\" style=\"background-color:grey; text-align:center\"> <tr> <td class=\"acceuil\"> <form style=\"height:100%\"> <input type=\"submit\" name=\"acceuil\" value=\"Acceuil\" style=\"width:100%; height:100%; background-color:grey; border:none; color:white; font-size:20px;\"> </form></td>"
                     + " <td class=\"acceuil\" width=\"30%\">Bienvenu sur le site de <br/> <B style=\"font-size:175%; color:white;\">RéseauAdmission</B><BR/>Un site d'achat de billets</td>"
                     + " <td class=\"acceuil\"> <form> <input type=\"submit\" name=\"acceuil\" value=\"Panier\" style=\"width:100%; height:100%; background-color:grey; border:none; color:white; font-size:20px;\"> </form> </td>"
                     + " <td class=\"acceuil\">"+
"                    Utilisateur: <BR/>\n" +
"                    <input type=text name=utilisateur id=utilisateur><BR/>\n" +
"                    Mot de passe: <BR/>\n" +
"                    <input type=password name=motdepasse id=motdepasse><BR/> "+
"                    <button>Login</button>"+"<form> <input type=\"submit\" name=\"acceuil\" value=\"S'inscrire\"></form>"+
"                    </td> </tr> </table> </div> ");

            String btnAcceuil = request.getParameter("acceuil");
            boolean affacc = true;
            
            switch (btnAcceuil) {
                case "Acceuil":
                    acceuil(out, categorie);
                    affacc = false;
                    break;
                case "Panier":
                    panier(out);
                    affacc = false;
                    break;
                case "S'inscrire":
                    inscription(out);
                    affacc = false;
                    break;
            }
                    
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
            int count = 0;   // Count pour compter les categories
            int[] pileDeCat = new int[7]; // Y'a 7 catégories gros max
            for (int i = 0; categories != null  && i < categories.length && categories[i] != null; i++)
            {
                // CatName to #
                if (categories[i].equals("Humour"))
                {
                    pileDeCat[count] = 1;
                }
                else if (categories[i].equals("Musique"))
                {
                    pileDeCat[count] = 2;
                }
                else if (categories[i].equals("Enfant"))
                {
                    pileDeCat[count] = 3;
                }
                else if (categories[i].equals("Illusion"))
                {
                    pileDeCat[count] = 4;
                }
                else if (categories[i].equals("Danse"))
                {
                    pileDeCat[count] = 5;
                }
                else if (categories[i].equals("Jeux_Video"))
                {
                    pileDeCat[count] = 6;
                }
                else if (categories[i].equals("Sport"))
                {
                    pileDeCat[count] = 7;
                }
                
                count++;
            }
            
            ResultSet rest = null;
            
            switch(count)
            {
                case 0:
                    CallableStatement stm0 = oracleConne.prepareCall("{? = call TPF_BD_JAVA.GetSpectacleParCat(?)}",
                            ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    stm0.registerOutParameter(1, OracleTypes.CURSOR);
                    stm0.registerOutParameter(2, OracleTypes.CURSOR);
                    //execution de la procédure
                    // Caster le paramètre de retour en ResultSet
                      /*  ResultSet rest = stm2.executeQuery();   */
                    stm0.execute();
                    rest = (ResultSet) stm0.getObject(1);
                    break;
                    
                case 1:
                    CallableStatement stm1 = oracleConne.prepareCall("{? = call TPF_BD_JAVA.GetSpectacleParCat1(?)}",
                            ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    stm1.registerOutParameter(1, OracleTypes.CURSOR);
                    stm1.registerOutParameter(2, OracleTypes.NUMBER);
                    stm1.setInt(2, pileDeCat[0]);
                    //execution de la procédure
                    // Caster le paramètre de retour en ResultSet
                      /*  ResultSet rest = stm2.executeQuery();   */
                    stm1.execute();
                    rest = (ResultSet) stm1.getObject(1);
                    break;
                case 2:
                    CallableStatement stm2 = oracleConne.prepareCall("{? = call TPF_BD_JAVA.GetSpectacleParCat2(?,?)}",
                            ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    stm2.registerOutParameter(1, OracleTypes.CURSOR);
                    stm2.registerOutParameter(2, OracleTypes.NUMBER);
                    stm2.registerOutParameter(3, OracleTypes.NUMBER);
                    stm2.setInt(2, pileDeCat[0]);
                    stm2.setInt(3, pileDeCat[1]);
                    //execution de la procédure
                    // Caster le paramètre de retour en ResultSet
                      /*  ResultSet rest = stm2.executeQuery();   */
                    stm2.execute();
                    rest = (ResultSet) stm2.getObject(1);
                    break;
                case 3:
                    CallableStatement stm3 = oracleConne.prepareCall("{? = call TPF_BD_JAVA.GetSpectacleParCat3(?,?,?)}",
                            ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    stm3.registerOutParameter(1, OracleTypes.CURSOR);
                    stm3.registerOutParameter(2, OracleTypes.NUMBER);
                    stm3.registerOutParameter(3, OracleTypes.NUMBER);
                    stm3.registerOutParameter(4, OracleTypes.NUMBER);
                    stm3.setInt(2, pileDeCat[0]);
                    stm3.setInt(3, pileDeCat[1]);
                    stm3.setInt(4, pileDeCat[2]);
                    //execution de la procédure
                    // Caster le paramètre de retour en ResultSet
                      /*  ResultSet rest = stm2.executeQuery();   */
                    stm3.execute();
                    rest = (ResultSet) stm3.getObject(1);
                    break;
                case 4:
                    CallableStatement stm4 = oracleConne.prepareCall("{? = call TPF_BD_JAVA.GetSpectacleParCat4(?,?,?,?)}",
                            ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    stm4.registerOutParameter(1, OracleTypes.CURSOR);
                    stm4.registerOutParameter(2, OracleTypes.NUMBER);
                    stm4.registerOutParameter(3, OracleTypes.NUMBER);
                    stm4.registerOutParameter(4, OracleTypes.NUMBER);
                    stm4.registerOutParameter(5, OracleTypes.NUMBER);
                    stm4.setInt(2, pileDeCat[0]);
                    stm4.setInt(3, pileDeCat[1]);
                    stm4.setInt(4, pileDeCat[2]);
                    stm4.setInt(5, pileDeCat[3]);
                    //execution de la procédure
                    // Caster le paramètre de retour en ResultSet
                      /*  ResultSet rest = stm2.executeQuery();   */
                    stm4.execute();
                    rest = (ResultSet) stm4.getObject(1);
                    break;
                case 5:
                    CallableStatement stm5 = oracleConne.prepareCall("{? = call TPF_BD_JAVA.GetSpectacleParCat5(?,?,?,?,?)}",
                            ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    stm5.registerOutParameter(1, OracleTypes.CURSOR);
                    stm5.registerOutParameter(2, OracleTypes.NUMBER);
                    stm5.registerOutParameter(3, OracleTypes.NUMBER);
                    stm5.registerOutParameter(4, OracleTypes.NUMBER);
                    stm5.registerOutParameter(5, OracleTypes.NUMBER);
                    stm5.registerOutParameter(6, OracleTypes.NUMBER);
                    stm5.setInt(2, pileDeCat[0]);
                    stm5.setInt(3, pileDeCat[1]);
                    stm5.setInt(4, pileDeCat[2]);
                    stm5.setInt(5, pileDeCat[3]);
                    stm5.setInt(6, pileDeCat[4]);
                    //execution de la procédure
                    // Caster le paramètre de retour en ResultSet
                      /*  ResultSet rest = stm2.executeQuery();   */
                    stm5.execute();
                    rest = (ResultSet) stm5.getObject(1);
                    break;
                case 6:
                    CallableStatement stm6 = oracleConne.prepareCall("{? = call TPF_BD_JAVA.GetSpectacleParCat6(?,?,?,?,?,?)}",
                            ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    stm6.registerOutParameter(1, OracleTypes.CURSOR);
                    stm6.registerOutParameter(2, OracleTypes.NUMBER);
                    stm6.registerOutParameter(3, OracleTypes.NUMBER);
                    stm6.registerOutParameter(4, OracleTypes.NUMBER);
                    stm6.registerOutParameter(5, OracleTypes.NUMBER);
                    stm6.registerOutParameter(6, OracleTypes.NUMBER);
                    stm6.registerOutParameter(7, OracleTypes.NUMBER);
                    stm6.setInt(2, pileDeCat[0]);
                    stm6.setInt(3, pileDeCat[1]);
                    stm6.setInt(4, pileDeCat[2]);
                    stm6.setInt(5, pileDeCat[3]);
                    stm6.setInt(6, pileDeCat[4]);
                    stm6.setInt(7, pileDeCat[6]);
                    //execution de la procédure
                    // Caster le paramètre de retour en ResultSet
                      /*  ResultSet rest = stm2.executeQuery();   */
                    stm6.execute();
                    rest = (ResultSet) stm6.getObject(1);
                    break;
                case 7:
                    CallableStatement stm7 = oracleConne.prepareCall("{? = call TPF_BD_JAVA.GetSpectacleParCat(?)}",
                            ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    stm7.registerOutParameter(1, OracleTypes.CURSOR);
                    stm7.registerOutParameter(2, OracleTypes.CURSOR);
                    //execution de la procédure
                    // Caster le paramètre de retour en ResultSet
                      /*  ResultSet rest = stm2.executeQuery();   */
                    stm7.execute();
                    rest = (ResultSet) stm7.getObject(1);
                    break;
                default:
                    System.err.println("Nombre invalide de catégories sélectionnées. Vérifiez 'pileDeCat'.");
                    CallableStatement stmD = oracleConne.prepareCall("{? = call TPF_BD_JAVA.GetSpectacleParCat(?)}",
                            ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    stmD.registerOutParameter(1, OracleTypes.CURSOR);
                    stmD.registerOutParameter(2, OracleTypes.CURSOR);
                    //execution de la procédure
                    // Caster le paramètre de retour en ResultSet
                      /*  ResultSet rest = stm2.executeQuery();   */
                    stmD.execute();
                    rest = (ResultSet) stmD.getObject(1);
                    break;
            }

            if (rest == null)
            {
                System.err.println("POINTEUR NULL");
            }
            
           // Mettre du stoque dans la page
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
                out.println("<CENTER><img src=\"affiches/" + Image + "\" width=\"180px\" height=\"200px\" " + ">");
                out.println("<BR/>" + Nom);
                out.println("<BR/>" + Artiste);
                
                switch( Integer.parseInt(Categorie))
                {
                    case 1:
                        out.println("<BR/>Humour");
                        break;
                    case 2:
                        out.println("<BR/>Musique");
                        break;
                    case 3:
                        out.println("<BR/>Enfant");
                        break;
                    case 4:
                        out.println("<BR/>Illusion");
                        break;
                    case 5:
                        out.println("<BR/>Dance");
                        break;
                    case 6:
                        out.println("<BR/>Jeux Vidéo");
                        break;
                    case 7:
                        out.println("<BR/>Sport");
                        break;
                    default:
                        out.println("<BR/>Inconnu");
                        break;
                }
                
                out.println("<BR/>Prix de base: " + Prix + "$");
                out.println("</td>");
                
                i++;
                
                if (i == 5)
                {
                    out.println("</tr>");
                    i = 0;
                }
            }
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
