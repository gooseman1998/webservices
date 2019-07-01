package nl.hu.v1wac.firstapp.persistence;


import java.sql.Connection;
import java.sql.Statement;
import javax.naming.*;
import javax.sql.*;

public class PostgresBaseDao {
    public Connection getConnection(){
        try
        {
            Context ctx = new InitialContext();
            if(ctx == null )
                throw new Exception("Boom - No Context");
            // /jdbc/postgres is the name of the resource above
            DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/PostgresDS");
            if (ds != null)
            {
                Connection conn = ds.getConnection();
                System.out.println("DataSource found");
                if(conn != null)
                {
                    System.out.println("Connection made");
                    System.out.println(conn);
                    return conn;
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public Statement getStatement(){
        try {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            return stmt;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
