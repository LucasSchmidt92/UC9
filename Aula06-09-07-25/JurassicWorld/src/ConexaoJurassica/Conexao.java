package ConexaoJurassica;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Conexao {
    private static final String URL = "jdbc:postgresql://localhost:5432/JurassicWorld";
    private static final String usuario = "postgres";
    private static final String senha = "root";

    public static Connection conectar(){
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

        System.out.println("\n--- Conectado com sucesso! ---");

    }

    public static void setDinossauro (String nome, String especie, String dieta, int idade, String status){
        String sql = "INSERT INTO dinossauros (nome_Dinossauro, especie_Dinossauro, dieta_Dinossauro, idade_Estimada, status_Cercado) values (?, ?, ?, ?, ?)";
        Connection conexao = Conexao.conectar();
        PreparedStatement stmt = null;

    }
}
