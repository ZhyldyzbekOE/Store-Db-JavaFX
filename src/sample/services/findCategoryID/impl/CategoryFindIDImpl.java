package sample.services.findCategoryID.impl;

import sample.services.database.DBservice;
import sample.services.database.DatabaseConnection;
import sample.services.findCategoryID.CategoryFindID;

import java.sql.ResultSet;
import java.sql.Statement;

public class CategoryFindIDImpl implements CategoryFindID {

    @Override
    public int findID(String nameCategory) {
        int id = 0;
        Statement statement = null;
        ResultSet rs = null;
        DBservice dBservice = new DatabaseConnection();
        try {
            dBservice.databaseConnection();
            statement = DatabaseConnection.connection.createStatement();
            String query = "SELECT id FROM categories WHERE categories.name = '"+nameCategory+"'";
            rs = statement.executeQuery(query);
//            DatabaseConnection.connection.close();
            id = rs.getInt("id");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
//                System.out.println("Во туту");
//                dBservice.databaseClose();
                rs.close();
                statement.close();
            }catch (Exception e){
                System.out.println("Category do not!");
            }
        }
        return id;
    }
}
