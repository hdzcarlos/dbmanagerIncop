package com.cice.dbmanager;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;


/**
 * Clase encargada de generar el acceso y uso de la base de datos.
 */

public class Manager {
    private final String DRIVER;
    private final String HOST;
    private final String PUERTO;
    private final String USER;
    private final String PASS;
    private final String DATABASE;

    private Connection connection;
    private Statement statement;
    public Manager(){
        this.DRIVER = "com.mysql.jdbc.Driver";
        this.HOST = "localhost";
        this.PUERTO = "8889";
        this.USER = "root";
        this.PASS = "root";
        this.DATABASE = "dbmanager";
    }

    public Manager(String DRIVER, String HOST, String PUERTO, String USER, String PASS, String DATABASE) {
        this.DRIVER = DRIVER;
        this.HOST = HOST;
        this.PUERTO = PUERTO;
        this.USER = USER;
        this.PASS = PASS;
        this.DATABASE = DATABASE;
    }
    private String generarUrl(){
        return "jdbc:mysql://"+HOST+":"+PUERTO+"/"+DATABASE;
    }
    /*
    private void crearTablaYBaseDeDatos() {
        conectarBaseDeDatos();
    }
    */
    /**
     * Metodo utilizado para conectar contra una base de datos , ya sea por defecto o inicializando los parametros
     *
     * @return
     */
    private boolean conectarBaseDeDatos(){
        boolean esConectado = false;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(generarUrl(),USER,PASS);
            if(connection != null){
                esConectado = true;
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return esConectado;
    }

    /**
     * Metodo que usaremos para desconectar de la base de datos y liberar recursos.
     * @return estado de la conexion true si esta desconectado
     */
    private boolean desconectarBaseDatos(){
        boolean esDesconectado = false;
        try {
            connection.close();
            esDesconectado = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return esDesconectado;
    }

    /**
     * Hacemos el EjecutarSelect y si no guardamso la información perdemos lo que hemos recuperado cuando desconectamos.
     * Tenemos una clase que es CachedRowSet(); estamos cacheando información.
     * @param sql
     */
    public CachedRowSet ejecutarSelect(String sql){
        CachedRowSet resultado = null;
        ResultSet resultSet = null;
        conectarBaseDeDatos();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            resultado = RowSetProvider.newFactory().createCachedRowSet();
            resultado.populate(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{

            try {
                statement.close();
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        desconectarBaseDatos();
        return resultado;
    }
}
