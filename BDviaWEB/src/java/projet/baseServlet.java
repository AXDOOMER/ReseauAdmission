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
import javax.servlet.http.Cookie;

// JDBC
import java.sql.*;
import java.sql.Date;
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
    
    private final String urlBD = "jdbc:oracle:thin:@mercure.clg.qc.ca:1521:orcl";
    private final String userName = "Labontel";
    private final String password = "ORACLE2";
    
    //methode pour créer les pages webs    
    public void acceuil(PrintWriter out, String[] categorie, String NomSalle, String NomArtiste, String message) {
        out.println("<table class=\"acceuil\" width=\"100%\" height=auto cellpadding=\"15px\" style=\"background-color:rgb(175,175,175)\">\n");
        out.println("<tr> <td colspan=\"2\" style=\"text-align:center; background-color:grey; border-radius:10px; border:1px white solid; font-size:17px;\">");

        if (message == null || message.equals("")) {
            out.println("Vous êtes à l'acceuil");
        } else {
            out.println("Message: " + message);
        }

        out.println("</td></tr> <tr><td rowspan=4>");
        faireTableSpectacles(out, categorie, NomSalle, NomArtiste);
        out.println("</td><td style=\"vertical-align:top; border:1px white solid; border-radius:10px; height:150px; width:200px;\"> Catégorie <br> <form>");

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
        
        out.println("<input type=\"submit\" name=\"Recherche\" value=\"SubmitCatSearch\"> </form><BR>");

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
            out.println("<BR><HR><BR>Salle <div> <form><select name=\"Salles\">");

            while (rest.next()) {
                String SonNom = rest.getString("NOMSALLE"); // on poigne le nom de la salle
                
                out.println("<option name=\"Salles\" value=\""+ SonNom + "\">" + SonNom + "</option>");
            }
            deconnexion(oracleConne);
        }
        catch (SQLException sqlex) {
            System.out.println(sqlex.getMessage());
        }
        
        out.println(" </select> <br><br> <input type=\"submit\" name=\"Salles\" value=\"Chercher par Salle\"> </form></div> ");
        
        out.println("<BR><HR><BR>Artiste <div> <form><input name=\"Artiste\" type=\"text\"/> <input type=\"submit\" name=\"Artiste\" value=\"Chercher par Artiste\"> </form> </div> ");
        
        out.println("</td> </tr> ");
        
        out.println(
                "</tr>" /*<tr colspan=\"8\"> <td rowspan=\"8\">Nom du Spectacle <br> Date*/
                + "<tr><td>  </td></tr>"
                + "<tr><td> </td></tr>"
                + "</table>"
        );
        
        /*Salle <div> <select> <option value=\"Salle1\">Salle 1 </option> </select> <br> <button>Chercher</button> </div> */
        /*Artiste <div> <input type=\"text\"/> <button>Chercher</button>  </div> */
    }
    
    public void achatDeBillets(PrintWriter out, String nomSpectacle)
    {
        String RepValues = TrouverRepValues(nomSpectacle);
        String SalleValues = TrouverSalleValues(nomSpectacle);
        String SectionValues = TrouverSectionValues(nomSpectacle);
        Connection oracleConne = seConnecter(); // Oracle s'tune conne
        try {          
            CallableStatement Callist =
            oracleConne.prepareCall(" { call TPF_BD_JAVA.AfficherSpectacleParNom(?,?)}");
            Callist.registerOutParameter(1,OracleTypes.CURSOR);
            Callist.setString(2,nomSpectacle);
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
            deconnexion(oracleConne);
        out.println("<form> <table class=\"acceuil\" cellpadding=\"10px\" width=\"100%\" style=\"border:1px white solid; background-color:rgb(175,175,175); height:80%; border-radius:10px;\">"+
			" <tr> <td rowspan=\"3\" colspan=\"13\" style=\"text-align:center; border-radius:10px; border:1px white solid; background-color:grey;\"> Achats de Billets  </tr> "+
                        " <tr> </tr> "+
                        " <tr> </tr> "+
			" <tr> <td rowspan=\"10\" colspan=\"5\" width=\"50%\"> <img src=\"affiches/" + affiche + "\" width=\"250px\" height=\"300px\" " + "> </td> <td> Catégorie <label>"+ nomcat +"</label> </td> "+ 
                        " <td rowspan=\"8\" colspan=\"13\" style=\"text-align:center;\" width=\"20%\"> Nb de billet voulu:  <input type=\"textbox\" name=\"AchatParametre\"> <br> Total: <label name=\"AchatTotal\">Le Total </label><br> <input type=\"submit\" name=\"achatbillet\" value=\"Ajouter au Panier\"> </td></tr>"+
			" <tr> <td> Artiste : <label name=\"AchatArtiste\">" + artiste + "</label> </td> </tr>"+ 
			" <tr> <td> Nom Spectacle : <label name=\"AchatNomSpectacle\">"+nomspectacle+"</label> </td> </tr>"+
			" <tr> <td> Prix de base : <label name=\"AchatPrixBase\">"+prixdebase+"</label> </td> </tr>"+
                        "<tr> <td> Imprimer : <select name=\"AchatParametre\"> <option value=\"Oui\">Oui</option><option value=\"Non\">Non</option> </select>  </td> </tr>"+      
                        /*" <tr> <td> Salle : <label>(A modifier)</label><select name=\"AchatSalle\" id=\"AchatSalle\" onchange=\"onChangeSalle()\"> "+ SalleValues +" </select> </td> </tr>"+*/
			" <tr> <td> Section : <select name=\"AchatParametre\"> "+SectionValues+" </select> </td> </tr>"+
			" <tr> <td> Prix : <label>(A modifier)</label>"+prixdebase+" + Prix de section <label>LABEL</label> </td> </tr>"+
			" <tr> <td> Nombre de billet restant pour la section: <label>(A modifier)</label><label>LABEL</label>  </td> </tr>"+
			" <tr> <td> Nb de billet restant total<label>(A modifier)</label> <label>LABEL</label> </td> </tr>"+
			" <tr>  </tr>"+
                        "<p id=\"demo\"></p>"+
                   "</table> </form>");
        out.println("<script>"+
                    "function myFunction() {"+
                    "var nomSpectacle = document.getElementById(\"AchatNomSpectacle\").value;" +
                    "document.getElementById(\"demo\").innerHTML = \"You selected: \" + nomSpectacle;"+
                    "}"+
                    "function onChangeSalle() {"+
                    "var codeSalle = document.getElementById(\"AchatSalle\").value;" +
                    "document.getElementById(\"AchatSection\").innerHTML = \"You selected: \" + nomSpectacle;"+
                    "}"+
                    "</script>");
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
            Callist.setString(2, nomSpectacle);
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
            deconnexion(oracleConne);
            }
        catch(SQLException list)
        {
            System.out.println(list.getMessage());
        }
        return repValues;
    }
    
    public String TrouverSalleValues(String nomSpectacle)
    {
        String salleValues = "";
                Connection oracleConne = seConnecter(); // Oracle s'tune conne
        try {          
            CallableStatement Callist =
            oracleConne.prepareCall(" { call TPF_BD_JAVA.AfficherSalleParReprParNomSpec(?,?)}");
            Callist.registerOutParameter(1,OracleTypes.CURSOR);
            Callist.setString(2, nomSpectacle);
            Callist.execute();
            ResultSet rstlist = (ResultSet)Callist.getObject(1);
                        
            //codespectacle,nomcat,prixdebase,artiste,nomspectacle,affiche,description           
                while(rstlist.next())
                {
                    int codeRep = rstlist.getInt(1);
                    
                    salleValues = salleValues + "<option value=\""+Integer.toString(codeRep)+"\" >" + Integer.toString(codeRep) + "</option>";
                }
                
                
            Callist.clearParameters();
            Callist.close();
            rstlist.close();
            deconnexion(oracleConne);
            }
        catch(SQLException list)
        {
            System.out.println(list.getMessage());
        }
        
        return salleValues;
    }

    public String TrouverSectionValues(String nomSpectacle)
    {
        String sectionValues = "";
        Connection oracleConne = seConnecter(); // Oracle s'tune conne
        try {          
            CallableStatement Callist =
            oracleConne.prepareCall(" { call TPF_BD_JAVA.AfficherSectionParRep(?,?)}");
            Callist.registerOutParameter(1,OracleTypes.CURSOR);
            Callist.setString(2, nomSpectacle);
            Callist.execute();
            ResultSet rstlist = (ResultSet)Callist.getObject(1);
                        
            //codespectacle,nomcat,prixdebase,artiste,nomspectacle,affiche,description           
                while(rstlist.next())
                {
                    int codeSection = rstlist.getInt(1);
                    
                    sectionValues = sectionValues + "<option value=\""+Integer.toString(codeSection)+"\" >" + Integer.toString(codeSection) + "</option>";
                }
                
                
            Callist.clearParameters();
            Callist.close();
            rstlist.close();
            deconnexion(oracleConne);
            }
        catch(SQLException list)
        {
            System.out.println(list.getMessage());
        }
        return sectionValues;
    }
    public int TrouverPrixSection(int codeSection)
    {
        int prix = 0;
         Connection oracleConne = seConnecter(); // Oracle s'tune conne
        try {          
            CallableStatement Callist =
            oracleConne.prepareCall(" { call TPF_BD_JAVA.AfficherSectionParCodeSection(?,?)}");
            Callist.registerOutParameter(1,OracleTypes.CURSOR);
            Callist.setInt(2, codeSection);
            Callist.execute();
            ResultSet rstlist = (ResultSet)Callist.getObject(1);
                                 
            rstlist.next();

            prix = rstlist.getInt("Prix");                            
            Callist.clearParameters();
            Callist.close();
            rstlist.close();
            deconnexion(oracleConne);
            
            }
        catch(SQLException list)
        {
            System.out.println(list.getMessage());
        }
        return prix;
    }
    // Sa remplie le panier de billet non acheter ( avec un date d'achat null )
    // par rapport au numClient (idClient)
    public int RemplirPanier(PrintWriter out,int numClient)
    {
        int prixTotale = 0;
        Connection oracleConne = seConnecter(); // Oracle s'tune conne
        try {          
            CallableStatement Callist =
            oracleConne.prepareCall(" { call TPF_BD_JAVA.AFFICHERPANIERPARCLIENT(?,?)}");
            Callist.registerOutParameter(1,OracleTypes.CURSOR);
            Callist.setInt(2, numClient);
            Callist.execute();
            ResultSet rstlist = (ResultSet)Callist.getObject(1);
                        
            //codespectacle,nomcat,prixdebase,artiste,nomspectacle,affiche,description           
                while(rstlist.next())
                {
                    int numBillet = rstlist.getInt(1);
                    int codeRep = rstlist.getInt(2);
                    Date debut = rstlist.getDate(3);
                    String nomSalle = rstlist.getString(4);
                    String nomSpectacle = rstlist.getString(5);
                    int codeSection = rstlist.getInt(6);
                    int imprimer = rstlist.getInt(7);
                    String imprimerS = "Non";
                    if (imprimer == 1)
                        imprimerS = "Oui";
                    int prixSpectacle = rstlist.getInt(8);
                    int prixSection = TrouverPrixSection(codeSection);
                    int prixBillet = prixSpectacle + prixSection;
                    prixTotale = prixTotale + prixBillet;
                    out.println("<tr style=\"text-align:center\">   <td > <label>"+numBillet+"</label></td>"
                                                                + "<td > <label>"+codeRep+"</label></td>\n"
                                                                + "<td > <label>"+debut+"</label></td>\n"
                                                                + "<td > <label>"+nomSalle+"</label></td>\n"
                                                                + "<td > <label>"+nomSpectacle+"</label></td>\n"
                                                                + "<td > <label>"+codeSection+"</label></td>\n"
                                                                + "<td > <label>"+imprimerS+"</label></td>\n"
                                                                + "<td > <label>"+prixSpectacle+"</label></td>\n"
                                                                + "<td > <label>"+prixSection+"</label></td>\n"
                                                                + "<td > <label>"+prixBillet+"</label></td>\n");
                }
                
                
            Callist.clearParameters();
            Callist.close();
            rstlist.close();
            deconnexion(oracleConne);
            }
        catch(SQLException list)
        {
            System.out.println(list.getMessage());
        }
        return prixTotale;
    }
    // Méthode utile pour la méthode facture
    public void AfficherNumFacture(PrintWriter out,int idClient)
    {
        Connection oracleConne = seConnecter(); // Oracle s'tune conne
        try {          
            CallableStatement Callist =
            oracleConne.prepareCall(" { call TPF_BD_JAVA.AfficherNumFactureParClient(?,?)}");
            Callist.registerOutParameter(1,OracleTypes.CURSOR);
            Callist.setInt(2, idClient);
            Callist.execute();
            ResultSet rstlist = (ResultSet)Callist.getObject(1);
                        
            //codespectacle,nomcat,prixdebase,artiste,nomspectacle,affiche,description           
                while(rstlist.next())
                {
                    int numClient = rstlist.getInt(1);
                    int numFacture = rstlist.getInt(2);
                    out.println("<tr style=\"text-align:center\">   <td > <label>"+numClient+"</label></td>"
                                                                + "<td > <input type=\"submit\" name=\"facture\" value=\""+numFacture+"\"></td>\n");
                }               
            Callist.clearParameters();
            Callist.close();
            rstlist.close();
            deconnexion(oracleConne);
            }
        catch(SQLException list)
        {
            System.out.println(list.getMessage());
        }
    }
    // Méthode utile pour la méthode AfficherLaFacture
    public int RemplirInfoFacture (PrintWriter out,int numClient,int numFacture)
    {
        int prixTotale = 0;
        Connection oracleConne = seConnecter(); // Oracle s'tune conne
        try {          
            CallableStatement Callist =
            oracleConne.prepareCall(" { call TPF_BD_JAVA.AfficherInfoFacture(?,?)}");
            Callist.registerOutParameter(1,OracleTypes.CURSOR);
            Callist.setInt(2, numFacture);
            Callist.execute();
            ResultSet rstlist = (ResultSet)Callist.getObject(1);
                        
            //codespectacle,nomcat,prixdebase,artiste,nomspectacle,affiche,description           
                while(rstlist.next())
                {
                    int numBillet = rstlist.getInt(1);
                    int codeRep = rstlist.getInt(2);
                    Date debut = rstlist.getDate(3);
                    String nomSalle = rstlist.getString(4);
                    String nomSpectacle = rstlist.getString(5);
                    int codeSection = rstlist.getInt(6);
                    int prixSpectacle = rstlist.getInt(7);
                    int prixSection = TrouverPrixSection(codeSection);
                    int prixBillet = prixSpectacle + prixSection;
                    prixTotale = prixTotale + prixBillet;
                    out.println("<tr style=\"text-align:center\">   <td > <label>"+numBillet+"</label></td>"
                                                                + "<td > <label>"+codeRep+"</label></td>\n"
                                                                + "<td > <label>"+debut+"</label></td>\n"
                                                                + "<td > <label>"+nomSalle+"</label></td>\n"
                                                                + "<td > <label>"+nomSpectacle+"</label></td>\n"
                                                                + "<td > <label>"+codeSection+"</label></td>\n"
                                                                + "<td > <label>"+prixSpectacle+"</label></td>\n"
                                                                + "<td > <label>"+prixSection+"</label></td>\n"
                                                                + "<td > <label>"+prixBillet+"</label></td>\n");
                }
                
                
            Callist.clearParameters();
            Callist.close();
            rstlist.close();
            deconnexion(oracleConne);
            }
        catch(SQLException list)
        {
            System.out.println(list.getMessage());
        }
        return prixTotale;
    }
    // Après avoir cliqué sur le numéro de facture, on affiche toutes les billets
    // de cette facture ( Même allure que le panier )
    public void AfficherLaFacture (PrintWriter out,int idClient, int numFacture)
    {
        int numClient = idClient;
        out.println("<form><table border=\"1px\" cellpadding=\"10px\" width=\"100%\" style=\"background-color:rgb(175,175,175); border-radius:10px; border:1px white solid; height:80%; color:white;\">\n" +
				"<tr style=\"text-align:center\"> <td colspan=\"9\" height=\"70%\" style=\"background-color:grey; border-radius:10px; border:1px white solid;\"> La facture numéro "+numFacture+" </td> </tr>\n" +
				"<tr style=\"text-align:center\"> <td > <label>Numéro billet</label></td> <td> <label>Code Rep</label></td> <td > <label>Début</label></td> <td> <label>nom salle</label></td><td > <label>Nom Spectacle</label></td> <td> <label>Section</label></td><td > <label>Prix du spectacle</label></td><td > <label>Prix section</label></td> <td > <label>Prix billet</label></td> </tr>\n" );
        // Sa remplie le panier de billet non acheter ( avec un date d'achat null )
        // par rapport au numClient (idClient)
        int prixTotale = RemplirInfoFacture(out,numClient,numFacture);
        out.println("<td > <label>Prix total: "+prixTotale+" $</label></td> <td>");
	out.println("</table></form>");
    }
    // Dans la fond, la facture est comme le panier 
    public void facture (PrintWriter out,int idClient)
    {
        int numClient = idClient;
        out.println("<form><table border=\"1px\" cellpadding=\"10px\" width=\"100%\" style=\"background-color:rgb(175,175,175); border-radius:10px; border:1px white solid; height:80%; color:white;\">\n" +
				"<tr style=\"text-align:center\"> <td colspan=\"9\" height=\"70%\" style=\"background-color:grey; border-radius:10px; border:1px white solid;\"> Les factures </td> </tr>\n" +
				"<tr style=\"text-align:center\"> <td > <label>Numéro du client</label></td> <td > <label>Numéro de facture</label></td>\n" );
        AfficherNumFacture(out,idClient);                       
	out.println("</table></form>");
    }
    public void panier(PrintWriter out,int idClient)
    {
        int numClient = idClient;
        out.println("<form><table border=\"1px\" cellpadding=\"10px\" width=\"100%\" style=\"background-color:rgb(175,175,175); border-radius:10px; border:1px white solid; height:80%; color:white;\">\n" +
				"<tr style=\"text-align:center\"> <td colspan=\"10\" height=\"70%\" style=\"background-color:grey; border-radius:10px; border:1px white solid;\"> Mon Panier </td> </tr>\n" +
				"<tr style=\"text-align:center\"> <td > <label>Numéro billet</label></td> <td> <label>Code Rep</label></td> <td > <label>Début</label></td> <td> <label>nom salle</label></td><td > <label>Nom Spectacle</label></td> <td> <label>Section</label></td><td> <label>Imprimer</label></td><td > <label>Prix du spectacle</label></td><td > <label>Prix section</label></td> <td > <label>Prix billet</label></td> <td rowspan=\"2\" style=\"border:none;\"> <input type=\"submit\" name=\"ConfirmerAchat\" value=\"Confirmer l'achat\"> </td> </tr>\n" );
                               //<tr style=\"text-align:center\"> <td > <label>LABEL</label></td> </tr>\n"+
        // Sa remplie le panier de billet non acheter ( avec un date d'achat null )
        // par rapport au numClient (idClient)
        int prixTotale = RemplirPanier(out,numClient);
        out.println("<td > <label>Prix total: "+prixTotale+" $</label></td> <td>");
	out.println("</table></form>");
    }
    
    public void inscription(PrintWriter out)
    {
        out.println("<table class=\"acceuil\" cellpadding=\"10px\" width=\"100%\" style=\"border:1px white solid; background-color:rgb(175,175,175); height:80%; border-radius:10px;\"> "
                + "<form> <tr> <td style=\"text-align:center; background-color:grey; border:1px white solid; border-radius:10px;\"colspan=\"2\"> Veuillez remplir les champs suivants </td> </tr> "
                + "<tr> <td class=\"inputtext\" style=\"height:20%; text-align:right;\"> Nom: </td> <td> <input type=\"textbox\" name=\"nom\" id=\"nom\"> </td> </tr>"
                + "<tr> <td class=\"inputtext\" style=\"height:20%; text-align:right;\"> Prénom: </td> <td> <input type=\"textbox\" name=\"prenom\" id=\"prenom\"> </td> </tr>"
                + "<tr> <td class=\"inputtext\" style=\"text-align:right;\"> Nom d'utilisateur: </td> <td> <input type=\"textbox\" name=\"username\" id=\"username\"> </td> </tr>"
                + "<tr> <td class=\"inputtext\" style=\"text-align:right;\"> Mot de passe: </td> <td> <input type=\"textbox:\" name=\"motpasse\" id=\"motpasse\"> </td> </tr>"
                + "<tr> <td class=\"inputtext\" style=\"text-align:right;\"> Téléphone: </td> <td> <input type=\"textbox:\" name=\"telephone\" id=\"telephone\"> </td> </tr>"
                + "<tr> <td class=\"inputtext\" style=\" text-align:right;\"> Adresse: </td> <td> <input type=\"textbox:\" name=\"adresse\" id=\"adresse\"> </td> </tr> \n"
                + "<tr> <td></td> <td> " /*<button>S'inscrire...</button>*/
                + "<input class=\"inputtext\" type=\"submit\" name=\"acceuil\" value=\"Creer compte\"> </td> </form> </table>");
    }
    // Sa permet de trouver le numéro de représentation
    // a partir du codeSection
    public void AjouterBilletAvecLaSection(int codeSection,int idClient,int imprimer)
    {
        Connection oracleConne = seConnecter(); // Oracle s'tune conne
        try {          
            CallableStatement Callist =
            oracleConne.prepareCall(" { call TPF_BD_JAVA.AfficherRepParSection(?,?)}");
            Callist.registerOutParameter(1,OracleTypes.CURSOR);
            Callist.setInt(2, codeSection);
            Callist.execute();
            ResultSet rstlist = (ResultSet)Callist.getObject(1);
                                  
                rstlist.next();
                // Ici on na le code de représentation
                int codeRep = rstlist.getInt(1);
            // Ici c'est pour ajouter un billet par le code représentation
                InsererBillet(codeRep,oracleConne,codeSection,idClient,imprimer);
                        
            Callist.clearParameters();
            Callist.close();
            rstlist.close();
            deconnexion(oracleConne);
            
            }
        catch(SQLException list)
        {
            System.out.println(list.getMessage());
        }
    }
    public void InsererBillet(int codeRep,Connection oracleConne,int codeSection,int idClient,int imprimer)
    {
        //imprimer: 0 non
        //          1 oui
        int numClient = idClient;
        try {          
            CallableStatement Callist =
            oracleConne.prepareCall(" { call TPF_BD_JAVA.AJOUTERBILLET (?,?,?,?,?)}");
            Callist.registerOutParameter(1,OracleTypes.CURSOR);
            Callist.setDate(2, null); // La date est null
            Callist.setInt(3, imprimer); // Imprimer est a 0
            Callist.setInt(4, codeRep); // C'est la le codeReprésentation           
            Callist.setInt(5, codeSection); // Ici, c'est le codeSection
            Callist.executeUpdate();
            ResultSet rstlist = (ResultSet)Callist.getObject(1);
            
            // On recupère la valeur du billet courrant ajouter
            rstlist.next();
            int numBillet = rstlist.getInt("NumBillet");
            
            LinkBilletAuClient(numBillet,oracleConne,numClient); 
            Callist.clearParameters();          
            Callist.close();                               
            }
        catch(SQLException list)
        {
            System.out.println(list.getMessage());
        }
    }
    public void LinkBilletAuClient(int numBillet,Connection oracleConne, int numClient)
    {
        try {          
            CallableStatement Callist =
            oracleConne.prepareCall(" { call TPF_BD_JAVA.AJOUTERPANIER (?,?,?)}");
            Callist.setInt(1, numBillet); // Imprimer est a 0
            Callist.setInt(2, numClient); // C'est la le codeReprésentation           
            Callist.setInt(3,0); // Ici, c'est le codeSection
            Callist.executeUpdate();
                    
            Callist.clearParameters();          
            Callist.close();                               
            }
        catch(SQLException list)
        {
            System.out.println(list.getMessage());
        }
    }
    // Fonction utile pour ConfirmerAchatDesBillets
    public void IncrementerNumFacture()
    {
        Connection oracleConne = seConnecter(); // Oracle s'tune conne
        try {          
            CallableStatement Callist =
            oracleConne.prepareCall(" { call TPF_BD_JAVA.INCREMENTENUMFACTURE(?)}");
            Callist.setString(1, "Peu importe");
            Callist.executeUpdate(); 
            
            Callist.clearParameters();
            Callist.close();
            deconnexion(oracleConne);
            }
        catch(SQLException list)
        {
            System.out.println(list.getMessage());
        }
    }
    // Fonction utile pour ConfirmerAchatDesBillets
    public void AcheterBiller(int numBillet,int uniqueId)
    {
        int numfacture = uniqueId;
        Connection oracleConne = seConnecter(); // Oracle s'tune conne
        try {          
            CallableStatement Callist =
            oracleConne.prepareCall(" { call TPF_BD_JAVA.CONFIRMERACHATBILLET(?,?)}");
            Callist.setInt(1, numBillet);
            Callist.setInt(2, numfacture);
            Callist.executeUpdate(); 
            
            Callist.clearParameters();
            Callist.close();
            deconnexion(oracleConne);
            }
        catch(SQLException list)
        {
            System.out.println(list.getMessage());
        }
    }
    public void ConfirmerAchatDesBillets(int idClient)
    {
        int numClient = idClient;
        Connection oracleConne = seConnecter(); // Oracle s'tune conne
        try {          
            CallableStatement Callist =
            oracleConne.prepareCall(" { call TPF_BD_JAVA.AFFICHERPANIERPARCLIENT(?,?)}");
            Callist.registerOutParameter(1,OracleTypes.CURSOR);
            Callist.setInt(2, numClient);
            Callist.execute();
            ResultSet rstlist = (ResultSet)Callist.getObject(1);
                // On execute une procédure qui sert a incrémenter un numéro
                // unique pour le numéro de facture
                int uniqueId = (int) (System.currentTimeMillis() & 0xfffffff);
                // Finalement, j'utilise le temps pour me générer un numéro unique
                IncrementerNumFacture();
                while(rstlist.next())
                {
                    // On récupère le numéro des billets
                    int numBillet = rstlist.getInt(1);
                    // On modifie la date du billet courrant dans la boucle
                    // C'est comme sa qu'on sais si le billet est acheter ou non
                    AcheterBiller(numBillet,uniqueId);
                }   
            Callist.clearParameters();
            Callist.close();
            rstlist.close();
            }
        catch(SQLException list)
        {
            System.out.println(list.getMessage());
        }
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            //Phil: on mets les biscuits dans le four... sa va etre pret dans +-30min            
            String[] categorie = null; /* On va mettre les categories sélectionnées icitte */
            int unMois = 30 * 24 * 60 * 60;
            String cookiecatrecu = "";
            String catselec = "";
            String artisteCookie = "";
            String salleCookie = "";
            int idClientCookie = -1;
            
            Cookie[] tabcookies = request.getCookies();
            for(Cookie c : tabcookies)
            {
                if( c.getName().equals("categorie"))
                {
                    cookiecatrecu = c.getValue();
                }
                if( c.getName().equals("artiste"));
                {
                    artisteCookie = c.getValue();
                }
                if( c.getName().equals("salle"))
                {
                    salleCookie = c.getValue();
                }
                if( c.getName().equals("id"))
                {
                    idClientCookie = Integer.parseInt(c.getValue());
                }
                
            }
            
            // Ajouter des billets de spectacle au panier
            String achatBillet = null;
            String [] tabAchatBillet = null;
            int imprimer = 0;// L'initialiser a non
            achatBillet = request.getParameter("achatbillet");
            if(achatBillet != null)
            {
                if(achatBillet.equals("Ajouter au Panier"))
                {
                    tabAchatBillet = request.getParameterValues("AchatParametre");
                    // Voir si imprimer est a Oui ou Non
                    if(tabAchatBillet[1].equals("Oui"))
                    {
                        imprimer = 1;
                    }
                    for (int i = 0; i < Integer.parseInt(tabAchatBillet[0]); i++)
                    {
                        AjouterBilletAvecLaSection(Integer.parseInt(tabAchatBillet[2]),idClientCookie,imprimer);
                    }
                }
            }
            
            // Dans le panier, confirmer l'achat
            String confirmerAchat = null;
            String [] tabConfirmerAchat = null;
            confirmerAchat = request.getParameter("ConfirmerAchat");
            if(confirmerAchat != null)
            {
                if(confirmerAchat.equals("Confirmer l'achat"))
                {
                   ConfirmerAchatDesBillets(idClientCookie);
                }
            }
                      
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
            out.println("<form><table class=\"titre\" cellpadding=\"10px\" width=\"100%\" style=\"background-color:grey; text-align:center\"> <tr> <td class=\"acceuil\"> <form style=\"height:100%\"> <input type=\"submit\" name=\"acceuil\" value=\"Acceuil\" style=\"width:100%; height:100%; background-color:grey; border:none; color:white; font-size:20px;\"> </form></td>"
                     + " <td class=\"acceuil\" width=\"30%\">Bienvenu sur le site de <br/> <B style=\"font-size:175%; color:white;\">RéseauAdmission</B><BR/>Un site d'achat de billets</td>"
                     + " <td class=\"acceuil\"> <form> <input type=\"submit\" name=\"acceuil\" value=\"Panier\" style=\"width:100%; height:100%; background-color:grey; border:none; color:white; font-size:20px;\"> </form> </td>"
                     + " <td class=\"acceuil\">"+
"                    Utilisateur: <BR/>\n" +
"                    <input type=\"text\" name=\"utilisateur\" id=\"utilisateur\"><BR/>\n" +
"                    Mot de passe: <BR/>\n" +
"                    <input type=\"password\" name=\"motdepasse\" id=\"motdepasse\"><BR/> "+
"                    <input type=\"submit\" name=\"acceuil\" value=\"Login\"></form>"+"<form> <input type=\"submit\" name=\"acceuil\" value=\"S'inscrire\"></form>"+
"                    <form> <input type=\"submit\" name=\"acceuil\" value=\"Facture\"></form>"+                    
"                    </td> </tr> </table> </div> ");

            String parametreQuiDitOuOnEst = request.getParameter("acceuil");
            String btnSpectacle = request.getParameter("spectacle");
            boolean affacc = true;

            // aller chercher la salle qu'on cherche
            String nomSalle = request.getParameter("Salles");

            // aller checher l'artiste qu'on veut avowère
            String nomArtiste = request.getParameter("Artiste");
            
            if(nomSalle == null && salleCookie != null)
            {
                nomSalle = salleCookie;
            }
            if(nomArtiste == null && artisteCookie != null)
            {
                nomArtiste = artisteCookie;
            }       

            Cookie artcookie = new Cookie("artiste", nomArtiste);
            artcookie.setMaxAge(unMois);
            response.addCookie(artcookie);
            
            // Dans les numéro de factures, afficher la facture
            String facture = null;
            String [] tabFacture = null;
            facture = request.getParameter("facture");
            if(facture != null)
            {
                AfficherLaFacture(out,idClientCookie,Integer.parseInt(facture));
                affacc = false;
            }
            else
                {
            
            if (parametreQuiDitOuOnEst != null) {
                switch (parametreQuiDitOuOnEst) {
                    case "Acceuil":
                        acceuil(out, categorie, nomSalle, nomArtiste, null);
                        affacc = false;
                        break;
                    case "Panier":
                        if (idClientCookie != -1)
                        {
                            panier(out,idClientCookie);
                            affacc = false;
                        }
                        else
                        {
                            acceuil(out, categorie, nomSalle, nomArtiste, "Erreur. Vous ne vous êtes pas login.");
                        }

                        break;
                    case "Facture":
                        if (idClientCookie != -1)
                        {
                            facture(out,idClientCookie);
                            affacc = false;
                        }
                        else
                        {
                            acceuil(out, categorie, nomSalle, nomArtiste, "Erreur. Vous ne vous êtes pas login.");
                        }
                        break;
                    case "S'inscrire":
                        inscription(out);
                        affacc = false;
                        break;
                    case "Login":
                        boolean seLogin = false;
                        String Username = "";
                        
                        try {
                            Connection oracleConn = seConnecter(); 
                            CallableStatement stmCheckUser = oracleConn.prepareCall("{? = call TPF_BD_JAVA.Login(?,?)}",
                                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                            stmCheckUser.registerOutParameter(1, OracleTypes.CURSOR);
                            stmCheckUser.setString(2, request.getParameter("utilisateur"));  //username
                            stmCheckUser.setString(3, request.getParameter("motdepasse"));  //password
                            //execution de la procédure
                            // Caster le paramètre de retour en ResultSet
                            /*  ResultSet rest = stm2.executeQuery();   */
                            stmCheckUser.execute();
                            ResultSet rest = (ResultSet) stmCheckUser.getObject(1);
                            
                            if (rest.next())
                            {
                                Username = rest.getString("pseudo");
                                idClientCookie = rest.getInt("numclient");
                                Cookie cookieId = new Cookie( "id", Integer.toString(idClientCookie));
                                response.addCookie(cookieId);
                           
                                // Si y'a de quoi dedans
                                seLogin = true; // il est là
                            }

                            deconnexion(oracleConn);
                            
                        } catch (SQLException list) {
                            System.out.println(list.getMessage());
                        }
                        
                        if (seLogin)
                        {
                            // C'est bin la place pour mettre des cookies icitte
                            acceuil(out, categorie, nomSalle, nomArtiste, "Bienvenue " + Username);
                        }
                        else
                        {
                            acceuil(out, categorie, nomSalle, nomArtiste, "Erreur. Vous ne vous êtes pas login.");
                        }
                        
                        affacc = false;
                        break;
                    case "Creer compte":
                        String nouveauMessage = null;

                        String iNom = request.getParameter("nom");
                        String iPrenom = request.getParameter("prenom");
                        String iUsername = request.getParameter("username");
                        String iMotpasse = request.getParameter("motpasse");
                        String iTelephone = request.getParameter("telephone");
                        String iAdresse = request.getParameter("adresse");

                        // savoir s'il est déjà là.
                        boolean userNameDejaLa = false;
                        
                        try {
                            Connection oracleConn = seConnecter(); 
                            CallableStatement stmCheckUser = oracleConn.prepareCall("{? = call TPF_BD_JAVA.CheckForUsername(?)}",
                            ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                            stmCheckUser.registerOutParameter(1, OracleTypes.CURSOR);
                            stmCheckUser.setString(2, iUsername);
                            //execution de la procédure
                            // Caster le paramètre de retour en ResultSet
                            /*  ResultSet rest = stm2.executeQuery();   */
                            stmCheckUser.execute();
                            ResultSet rest = (ResultSet) stmCheckUser.getObject(1);
                            
                            if (rest.next())
                            {
                                // Si y'a de quoi dedans
                                userNameDejaLa = true;
                            }
                            
                            deconnexion(oracleConn);
                            
                        } catch (SQLException list) {
                            System.out.println(list.getMessage());
                        }

                        // Checker pour les erreurs
                        if (iNom == null || iNom.length() == 0) {
                            nouveauMessage = "Nom invalide.";
                        } else if (iPrenom == null || iPrenom.length() == 0) {
                            nouveauMessage = "Prenom invalide.";
                        } else if (iUsername == null || iUsername.length() == 0 || userNameDejaLa) {
                            nouveauMessage = "Nom d'utilisateur invalide.";
                            if (userNameDejaLa)
                            {
                                nouveauMessage = nouveauMessage +  " Il y en a déjà un qui porte ce nom.";
                            }
                        } else if (iMotpasse == null || iMotpasse.length() == 0) {
                            nouveauMessage = "Mot de passe invalide.";
                        } else if (iTelephone == null || iTelephone.length() == 0 || iTelephone.length() > 10) {
                            nouveauMessage = "Numero de telephone invalide.";
                        } else if (iAdresse == null || iAdresse.length() == 0) {
                            nouveauMessage = "Adresse invalide.";
                        } else {
                            // Inscire l'usager dans la BD
                            try {
                                Connection oracleConne = seConnecter();
                                CallableStatement Callist
                                        = oracleConne.prepareCall(" { call TPF_BD_JAVA.InscrireClient(?,?,?,?,?,?)}");
                                Callist.setString(1, iNom);
                                Callist.setString(2, iPrenom);
                                Callist.setString(3, iUsername);
                                Callist.setString(4, iMotpasse);
                                Callist.setString(5, iTelephone);
                                Callist.setString(6, iAdresse);
                                
                                Callist.execute();

                                Callist.clearParameters();
                                Callist.close();

                                nouveauMessage = "Vous vous êtes inscrits";
                                //----
                                affacc = false;
                                deconnexion(oracleConne);
                                
                            } catch (SQLException sqlex) {
                                System.out.println(sqlex.getMessage());
                            }
                        }
                    acceuil(out, categorie, nomSalle, nomArtiste, nouveauMessage);  // Ça c'est quand on vient de s'inscrire
                    break;
                        
                }

            } else if (btnSpectacle != null) {
                if(idClientCookie != -1)
                {
                    achatDeBillets(out, btnSpectacle);
                }
                else
                {
                    acceuil(out, categorie, nomSalle, nomArtiste, "Veuillez vous connectez pour acheter un billet");
                }

            } else {
                acceuil(out, categorie, nomSalle, nomArtiste, null);
            }
        }
            //out.println(tabAchatBillet[1]);
            out.println("</body>");
            out.println("</html>");

        }
    }

    private Connection seConnecter() {
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
    
    private void deconnexion(Connection deconne)
    {
        // C'est une bitch blonde
        try {
            deconne.close();
        } catch (SQLException se) {
            System.out.println("La conne ne s'est pas fermée");
            deconne = null;
        }
    }



    private void faireTableSpectacles(PrintWriter out, String[] categories, String NomSalle, String NomArtiste)
    {
        Connection oracleConne = seConnecter(); 
        
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
            
            if (NomSalle != null && NomSalle.length() > 0)
            {
                CallableStatement stmNS = oracleConne.prepareCall("{? = call TPF_BD_JAVA.GetSpectacleParNomSalle(?)}",
                        ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                stmNS.registerOutParameter(1, OracleTypes.CURSOR);
                stmNS.registerOutParameter(2, OracleTypes.VARCHAR);
                stmNS.setString(2, NomSalle);
                    //execution de la procédure
                // Caster le paramètre de retour en ResultSet
                      /*  ResultSet rest = stm2.executeQuery();   */
                stmNS.execute();
                rest = (ResultSet) stmNS.getObject(1);
            } else if (NomArtiste != null && NomArtiste.length() > 0) {
                CallableStatement stmNA = oracleConne.prepareCall("{? = call TPF_BD_JAVA.TrouverSpectacleParArtiste(?)}",
                        ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                stmNA.registerOutParameter(1, OracleTypes.CURSOR);
                stmNA.registerOutParameter(2, OracleTypes.VARCHAR);
                stmNA.setString(2, NomArtiste);
                    //execution de la procédure
                // Caster le paramètre de retour en ResultSet
                      /*  ResultSet rest = stm2.executeQuery();   */
                stmNA.execute();
                        rest = (ResultSet) stmNA.getObject(1);
            } else {
                switch (count) {
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
                out.println("<br> <form> <input type=\"submit\" name=\"spectacle\" value=\"" + Nom + "\"> </form>");
                out.println(Artiste);
                
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
