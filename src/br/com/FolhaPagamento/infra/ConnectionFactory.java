package br.com.FolhaPagamento.infra;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    public String url = "jdbc:postgresql://localhost:5432/folhapagamento";
    public String usuario = "postgres";
    public String senha = "deumaoito";
    private Connection connection;

    public Connection getConnection() {
        try {
            connection = DriverManager.getConnection(url, usuario, senha);
            return connection;

        } catch (SQLException e) {
            System.err.println("Não foi possivel conectar ao banco de dados!");
            throw new RuntimeException(e.getMessage());
        }
    }
}