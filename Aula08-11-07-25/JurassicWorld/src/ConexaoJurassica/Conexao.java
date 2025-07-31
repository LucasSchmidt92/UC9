package ConexaoJurassica;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Conexao {
    private static final String URL = "jdbc:postgresql://localhost:5432/JurassicWorld";
    private static final String usuario = "postgres";
    private static final String senha = "18042006Gab";

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
        //setDinossauro("Giuseppe", "Brontossauro", "Herbívoro", 24, 64, "Algemado");
        //setDinossauro("Jorge", "Joseph Joestar", "Canibal", 30, 999, "Chefe do Obra");

        getDinossauros();
        //atualizarDinossauro(1, "Joseph Joestar", "Dilophossauro", "Canibal", 38, 999, "Chefe de Obra");
        removerDinossauro(1);
        System.out.println("\n--- Conectado com sucesso! ---");
        getDinossauros();

    }

    public static void setDinossauro (String nome, String especie, String dieta, int idade, int idadeEstimada, String status){
        String sql = "INSERT INTO dinossauros (nome_Dinossauro, especie_Dinossauro, dieta_Dinossauro, idade_Dinossauro, idade_Estimada_Dinossauro, status_Cercado) values (?, ?, ?, ?, ?, ?)";
        Connection conexao = Conexao.conectar();
        PreparedStatement stmt = null;

        try{
            conexao = Conexao.conectar();
            if(conexao != null){
                stmt = conexao.prepareStatement(sql);
                stmt.setString(1, nome);
                stmt.setString(2, especie);
                stmt.setString(3, dieta);
                stmt.setInt(4, idade);
                stmt.setInt(5, idadeEstimada);
                stmt.setString(6, status);
                int linhasAfetadas = stmt.executeUpdate();
                if(linhasAfetadas > 0){
                    System.out.println("O Dinossauro "+nome+" foi registrado no banco de dados com sucesso!");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir Dinossauro no nosso banco de dados: "+ e.getMessage());
        } finally{
            try{
                if(stmt != null) stmt.close();
                if(conexao != null) fecharConexao(conexao);
            } catch (SQLException e) {
                System.out.println("Erro ao fechar o banco de dados: "+ e.getMessage());
            }
        }
    }

    public static void getDinossauros(){
        String sql = "SELECT * FROM dinossauros";
        Connection conexao = Conexao.conectar();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conexao = Conexao.conectar();
            if (conexao != null) {
                stmt = conexao.prepareStatement(sql);
                rs = stmt.executeQuery();
                System.out.println("\n--- Dinossauros Cadastrados no Banco de Dados ---");
                Boolean encontrouAluno = false;
                while (rs.next()) {
                    encontrouAluno = true;
                    int id = rs.getInt("id_Dinossauro");
                    String nome = rs.getString("nome_Dinossauro");
                    String especie = rs.getString("especie_Dinossauro");
                    String dieta = rs.getString("dieta_Dinossauro");
                    String status = rs.getString("status_Cercado");
                    int idadeEstimada =   rs.getInt("idade_Estimada_Dinossauro");
                    int idade =   rs.getInt("idade_Dinossauro");

                    System.out.println("ID: "+id + ", Nome: " + nome + ", Idade: " + idade + ", Idade Estimada: " + idadeEstimada + ", Espécie: " + especie + ", Dieta: "+dieta + ", Status: "+ status);
                }
                if (!encontrouAluno) {
                    System.out.println("Nenhum aluno encontrado");
                }
                System.out.println("--------------------------------");
            }
        } catch(SQLException error){
            System.out.println("Erro ao conectar com o banco de dados: " + error.getMessage());
        } finally {
            try{
                if (rs != null) rs.close();
                if(stmt != null) stmt.close();
                if(conexao != null) fecharConexao(conexao);
            } catch(SQLException error){
                System.err.println("Erro ao fechar recursos após pesquisa: " + error.getMessage());
            }
        }
    }

    public static void atualizarDinossauro(int id, String nome, String especie, String dieta, int idade, int idadeEstimada, String status){
        String sql = "UPDATE dinossauros SET nome_Dinossauro = ?, especie_Dinossauro = ?, dieta_Dinossauro = ?, idade_Dinossauro = ?, idade_Estimada_Dinossauro = ?, status_Cercado = ? WHERE id_Dinossauro = ?";
        Connection conexao = Conexao.conectar();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conexao = Conexao.conectar();
            if (conexao != null) {
                stmt = conexao.prepareStatement(sql);
                stmt = conexao.prepareStatement(sql);
                stmt.setString(1, nome);
                stmt.setString(2, especie);
                stmt.setString(3, dieta);
                stmt.setInt(4, idade);
                stmt.setInt(5, idadeEstimada);
                stmt.setString(6, status);
                stmt.setInt(7, id);
                int linhasAfetadas = stmt.executeUpdate();

                if (linhasAfetadas > 0) {
                    System.out.println("Dinossauro com ID " + id + " atualizado com sucesso!");
                } else {
                    System.out.println("Nenhum Dinossauro com o ID " + id + " foi encontrado");
                }
            }
        } catch(SQLException error){
            System.out.println("Erro ao conectar com o banco de dados: " + error.getMessage());
        } finally {
            try{
                if (rs != null) rs.close();
                if(stmt != null) stmt.close();
                if(conexao != null) fecharConexao(conexao);
            } catch(SQLException error){
                System.err.println("Erro ao fechar recursos após pesquisa: " + error.getMessage());
            }
        }
    }

    public static void removerDinossauro(int id){
        String sql = "DELETE FROM dinossauros WHERE id_Dinossauro = ?";
        Connection conexao = null;
        PreparedStatement stmt = null;
        try {
            conexao = Conexao.conectar();
            if (conexao != null) {
                stmt = conexao.prepareStatement(sql);
                stmt.setInt(1, id);
                int linhasAfetadas = stmt.executeUpdate();
                if (linhasAfetadas > 0) {
                    System.out.println("Dinossauro com ID "+ id +" deletado com sucesso!");
                } else {
                    System.out.println("Nenhum Dinossauro encontrado com ID "+id);
                }
            }
        } catch (SQLException error) {
            System.err.println("Erro ao inserir o Dinossauro: " + error.getMessage());
        } finally {
            try{
                if(stmt != null) stmt.close();
                if(conexao != null) fecharConexao(conexao);
            } catch(SQLException error){
                System.out.println("Erro ao fechar conexao: " + error.getMessage());
            }
        }
    }
}
