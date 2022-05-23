package Login;

import DBUtil.DBConnection;
import methode.Agent;
import methode.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginModel {
    Connection connection;
    private static boolean isLoginBoolean_A=false;
    private static boolean isLoginBoolean_C=false;

    public LoginModel(){
        try{
            this.connection= DBConnection.connectionBD();
        }catch(SQLException e){
            e.printStackTrace();
        }

        if(this.connection==null){
            System.out.println("Vous avez un problème avec LoginModel");
            System.exit(1);
        }
    }

    public boolean isDBConnected(){
        return this.connection != null;
    }

    public boolean isLogin(String email, String pass,String opt) throws Exception{
        final String opt_C= "Client";
        final String opt_AV= "AgentVente";
        final String opt_AC= "AgentCommercial";
        final String opt_PDG= "Directeur";
       //  AGENT DE VENTE
        PreparedStatement pr_AV=null;
        ResultSet rs_AV=null;

        // AGENT COMMERCIAL
        PreparedStatement pr_AC=null;
        ResultSet rs_AC=null;

        // DIRECTEUR
        PreparedStatement pr_PDG=null;
        ResultSet rs_PDG=null;

        // CLIENT
        PreparedStatement pr_C=null;
        ResultSet rs_C=null;

        String sql_AV="select * from Agent where email = ? and mdp = ? and type_agent = 1";
        String sql_AC="select * from Agent where email = ? and mdp = ? and type_agent = 2";
        String sql_PDG="select * from Agent where email = ? and mdp = ? and type_agent = 3";
        String sql_C="select * from Client where email = ? and mdp = ?";



        try {

            /////// AGENT COMMERCIAL
            pr_AC=this.connection.prepareStatement(sql_AC);

            pr_AC.setString(1, email);
            pr_AC.setString(2, pass);

            rs_AC= pr_AC.executeQuery();
            /////// AGENT DE VENTE
            pr_AV=this.connection.prepareStatement(sql_AV);

            pr_AV.setString(1, email);
            pr_AV.setString(2, pass);

            rs_AV= pr_AV.executeQuery();
            /////// PDG
            pr_PDG=this.connection.prepareStatement(sql_PDG);

            pr_PDG.setString(1, email);
            pr_PDG.setString(2, pass);

            rs_PDG= pr_PDG.executeQuery();
            /////// CLIENT
            pr_C=this.connection.prepareStatement(sql_C);

            pr_C.setString(1, email);
            pr_C.setString(2, pass);

            rs_C= pr_C.executeQuery();

            if((rs_AC.next())&&(opt.equals(opt_AC))){
                isLoginBoolean_A=true;
                return true;
            }else if((rs_AV.next())&&(opt.equals(opt_AV))){
                isLoginBoolean_A=true;
                return true;
            }else if((rs_PDG.next())&&(opt.equals(opt_PDG))){
                isLoginBoolean_A=true;
                return true;
            }else if((rs_C.next())&&(opt.equals(opt_C))){
                isLoginBoolean_C=true;
                return true;
            }else{
                isLoginBoolean_A=false;
                isLoginBoolean_C=false;
                return false;
            }
        }catch (SQLException e){
            System.out.println("Vous avez un probleme avec LoginModel/ isLogin");
            return false;
        }finally {
            pr_AC.close();
            rs_AC.close();
            pr_AV.close();
            rs_AV.close();
            pr_PDG.close();
            rs_PDG.close();
            pr_C.close();
            rs_C.close();
        }
    }

    public Agent getInfoAgent(String email, String mdp) throws SQLException {

        //isLoginBoolean_M est a vrai ssi isLogin est a true donc si le medecin a reussi a se connectй
        if(isLoginBoolean_A)
        {
            System.out.println("Success Connection to AGENT!");
            //initialiser les variables
            String sql_M="select * from agent where email = ? and mdp = ?";

            PreparedStatement pr_M=null;
            ResultSet rs_M=null;

            try {

                pr_M=this.connection.prepareStatement(sql_M);

                pr_M.setString(1, email);
                pr_M.setString(2, mdp);
                rs_M= pr_M.executeQuery();


                //je crée l'objet agent et je vais recupperer tt les info du agent de vente dès qu'il se connect au logiciel pour renvoyer un objet agent avec tt ses infos
                Agent agent=new Agent();


                //affecter a notre objet agent de vente tt les données recolter par le ResultSet

                agent.setId_agent(rs_M.getInt("id_agent"));
                agent.setType_agent(Integer.parseInt(rs_M.getString("type_agent")));

                agent.setNom(rs_M.getString("nom_agent"));
                agent.setPrenom(rs_M.getString("prenom_agent"));
                agent.setEmail(rs_M.getString("email"));
                agent.setTel(rs_M.getString("tel"));


                return agent;

            } catch (SQLException e) {
                System.out.println("Vous avez un probleme dans la classe LoginModel / getInfoAgent");

            }
            finally {
                pr_M.close();
                rs_M.close();
            }


        }

        return null;
    }

    public Client getInfoClient(String email, String mdp) throws SQLException {

        //isLoginBoolean_M est a vrai ssi isLogin est a true donc si le medecin a reussi a se connectй
        if(isLoginBoolean_C)
        {
            System.out.println("Success Connection to CLIENT!");
            //initialiser les variables
            String sql_M="select * from client where email = ? and mdp = ?";

            PreparedStatement pr_M=null;
            ResultSet rs_M=null;

            try {

                pr_M=this.connection.prepareStatement(sql_M);

                pr_M.setString(1, email);
                pr_M.setString(2, mdp);


                rs_M= pr_M.executeQuery();


                //je crйe l'objet Client et je vais recupperer tt les info du client dиs qu'il se connect au logiciel pour renvoyer un objet client avec tt ses info
                Client client=new Client();


                //affecter a notre objet client tt les donnйes recolter par le ResultSet
                client.setId_client(String.valueOf(rs_M.getInt("id_client")));
                 client.setType_client(rs_M.getString("type_client"));
                client.setNom(rs_M.getString("nom_client"));
                client.setPrenom(rs_M.getString("prenom_client"));

                client.getIP().setEmail(rs_M.getString("email"));
                client.getIP().setTel(rs_M.getString("tel"));

              //  client.setDesignation(rs_M.getString("designation"));
                client.getIP().setAdresse(rs_M.getString("adresse"));


                return client;

            } catch (SQLException e) {
                System.out.println("Vous avez un probleme dans la classe LoginModel / getInfoClient");

            }
            finally {
                pr_M.close();
                rs_M.close();
            }


        }

        return null;
    }
}
