package ConexaoMedicapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String URL = "jdbc:mysql://localhost:3306/medicapp_bd";
    private static final String usuario = "root";
    private static final String senha = "root";

    public static Connection conectar() {
        Connection conexao = null;
        try {
            conexao = DriverManager.getConnection(URL, usuario, senha);
            System.out.println("Conectado com sucesso!");
        } catch (SQLException error) {
            System.err.println("Erro ao conectar com o banco de dados: " + error.getMessage());

        } return conexao;
    }

    public static void fecharConexao(Connection conexao) {
        if (conexao != null) {
            try {
                conexao.close();
                System.out.println("Conexao fechada com sucesso!");
            } catch (SQLException error) {
                System.err.println("Erro ao fechar conexao: " + error.getMessage());
            }
        }
    }

    public static void main(String[] args){
        Connection testeConexao = Conexao.conectar();
        if (testeConexao != null) {
            Conexao.fecharConexao(testeConexao);
        }
    }
}
