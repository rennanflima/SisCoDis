/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.uninorte.siscodis.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Rennan Francisco
 */
public class Conexao {

    private String driver = "com.mysql.jdbc.Driver";
    private String url = "jdbc:mysql://localhost:3306/siscodis";
    private String user = "root";
    private String senha = "";
    
    Connection con = null;

    public Connection abreConexao() {
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, senha);
        } catch (ClassNotFoundException e) {
            System.out.println("Classe não encontrada. " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Problema com o BD. " + e.getMessage());
        }
        return con;
    }

    public void fechaConexao() {
        try {
            con.close();
        } catch (Exception e) {
            System.out.println("Problema ao fechar a conexão. " + e.getMessage());
        }
    }
}
