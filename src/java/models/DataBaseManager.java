/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Yuri.Ramos
 */
public class DataBaseManager {
    
    private Connection conn;
    
    public DataBaseManager() {
        this.conn = null;
    }
    
    public void OpenConnectionDataBase() {
        
        // estabelecendo conexão com o banco de dados //
        try {
            String url = "jdbc:mysql://localhost:3306/movies_database";
            String user = "root";
            String password = "";

            Class.forName("com.mysql.jdbc.Driver");
            this.conn = DriverManager.getConnection(url, user, password);
            if (this.conn != null) {
                System.out.println("Connected to the database");
            }

        } catch (SQLException ex) {
            System.out.println("An error occurred. Maybe user/password is invalid");
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
        
        }
        
    }
    
    public ArrayList<Titles> ExecuteQuerySelectTitles(String search, String filter) {
    
        // Construindo Query de Busca //
        String query = null;
        // se campo estiver vazio, trazer todos os registros //
        if(search.compareTo("") == 0){
            query = "select * from TITLES;";
        }
        else{
            switch(filter){
                case "Title":
                    query = "select * from TITLES where TITLE_NAME like '%" + search + "%';";
                    break;
                case "Year":
                    query = "select * from TITLES where YEAR = " + search + ";";
                    break;
                default:
                    query = "select * from TITLES where " + filter + " like '%" + search + "%';";
                    break;
            }  
        }
    
        ArrayList<Titles> titlesList = new ArrayList<Titles>();
        
         // executando querys de seleção //
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            // preenchendo valores com os atributos do objeto Title //
            ResultSet rst;
            rst = stmt.executeQuery();
            
            while(rst.next()){
                Titles titles = new Titles();
                titles.setDirector(rst.getString("DIRECTOR"));
                titles.setGenre(rst.getString("GENRE"));
                
                if(rst.getString("IMAGE_COVER") != ""){
                    titles.setImageCover(rst.getString("IMAGE_COVER"));
                }
                else{
                    titles.setImageCover("https://upload.wikimedia.org/wikipedia/commons/thumb/b/be/Saint_Andrew%27s_cross_%28red%29.svg/600px-Saint_Andrew%27s_cross_%28red%29.svg.png");
                }
                titles.setImdbScore(rst.getDouble("IMDB_SCORE"));
                titles.setReleaseYear(rst.getInt("YEAR"));
                titles.setTitleName(rst.getString("TITLE_NAME"));
                titles.setWriter(rst.getString("WRITER"));
                
                titlesList.add(titles);
            } 
            stmt.close();
        } catch (SQLException ex) {
            
        }
        
        return titlesList;
    }
    
    public void CloseConnection() {
        try {
               this.conn.close();
               System.out.println("Database was closed!");
        } catch (SQLException ex) {
                
        }
    }
    
}
