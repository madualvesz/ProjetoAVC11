package conect;

import java.sql.*;
import javax.swing.*;

public class connectFactor {

    public static void main(String[] args) {
        final String DRIVER = "com.mysql.jdbc.Driver";
        final String URL = "jdbc:mysql://localhost:3306/registroaluno";
        try {
            Class.forName(DRIVER);
            Connection connection = DriverManager.getConnection(URL, "root", "bigo");
            JOptionPane.showMessageDialog(null, "conexao ok");
            connection.close();
        } 
        catch (ClassNotFoundException erro) {
            JOptionPane.showMessageDialog(null, "Drive nao encontrado\n"
                    + erro.toString());
        } 
        catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "conexao out \n"
                    + erro.toString());
        }
    }
}
