package DBUtil;

import java.sql.*;
import com.sun.rowset.CachedRowSetImpl;
import javax.sql.RowSet;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;

public class DBConnection {
    private static final String USERNAME="dbuser";
    private static final String PASSWORD="dbpassword";
    private static final String URL="jdbc:sqlite:DB/mydb.db";
    private static Connection conn=null;

    // fonction pour etablir la connection à la BD
    public static Connection connectionBD() throws SQLException{
        try {
           Class.forName("org.sqlite.JDBC");
           return DriverManager.getConnection(URL);
        }catch(ClassNotFoundException | SQLException e){
            System.out.println("Probleme au niveau de la connection à la BD");
            e.printStackTrace();
        }
        return null;
    }
    //cette methode sert a demarrer la connection à la BD
    public static void DBConnect() throws SQLException{
        try{
            conn= DBConnection.connectionBD();
        }catch(SQLException e){
            System.out.println("Probleme au niveau de l'accès à la BD");
            e.printStackTrace();
        }

        if (conn == null){
            System.out.println("Vous avez un probleme avec DbConnection/ dbConnect :Erreur de connection avec la base de donnée");
            System.exit(1);
        }
    }
    //cette methode sert a fermer la connection à la BD
    public static void DBDisconnect() throws SQLException{
        try{
            if(conn != null & !conn.isClosed()){
                conn.close();
            }
        }catch(SQLException e){
            System.out.println("Vous avez un problème pour vous déconnecter de la DB");
            throw e;
        }
    }

    //cette methode nous permet d'executer les commandes Inserer supprimer modifier ou n'importe qu'elle commande Sql qui ne retourne rien
    public static void DBExceuteQuery(String sqlStmt) throws SQLException{
        Statement stmt=null;

        try {
            DBConnect();
            stmt = conn.createStatement();
            stmt.executeUpdate(sqlStmt);
        }catch (SQLException e){
            System.out.println("Vous avez un problème avec DBExecuteQuery");
            throw e;
        }finally {
            if (stmt != null){
                stmt.close();
            }
            DBDisconnect();
        }
    }

    public static CachedRowSet DBExecute(String sqlQuery) throws ClassNotFoundException, SQLException{
        Statement stmt=null;
        ResultSet rs=null;
        CachedRowSet crs=null;

        try{
            DBConnect();
            stmt= conn.createStatement();
            rs= stmt.executeQuery(sqlQuery);
            crs= RowSetProvider.newFactory().createCachedRowSet();
            crs.populate(rs);
        }catch (SQLException e){
            System.out.println("Vous avec un problème avec DBExceute");
            throw e;
        }finally {
            if(rs != null){
                rs.close();
            }
            if(stmt != null){
                stmt.close();
            }
            DBDisconnect();
        }

        return crs;
    }
}


