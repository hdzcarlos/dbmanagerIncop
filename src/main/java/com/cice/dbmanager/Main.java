package com.cice.dbmanager;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        Manager manager = new Manager();

        ResultSet resultado = manager.ejecutarSelect("SELECT * FROM prueba");
        try{
        while (resultado.next()) {
            String id = resultado.getString("id");
            String nm = resultado.getString("nombre");
            System.out.println(id + " -  " + nm);
        }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
