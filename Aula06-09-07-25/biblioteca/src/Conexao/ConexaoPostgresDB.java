package Conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ConexaoPostgresDB {
    private static final String URL = "jdbc:postgresql://localhost:5432/Biblioteca";
    private static final String usuario = "postgres";
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
        Connection testeConexao = ConexaoPostgresDB.conectar();
        if (testeConexao != null) {
            ConexaoPostgresDB.fecharConexao(testeConexao);
        }

        System.out.println("\n--- Conectado com sucesso! ---");
        setAluno("Gabriel", 19, "1827-4765");
        //setAluno("Lucca", 21, "2735-0927");

        System.out.println("\n--- Conectado com sucesso! ---");
        atualizarAluno(1, "Gabriel", 21, "3125-4761");
        getAlunos();
        removerAluno(3);
        getAlunos();
    }

    public static void setAluno(String nome, int idade, String contato){
        String sql = "INSERT INTO aluno (nome_Aluno, idade_Aluno, contato_Aluno ) VALUES (?, ?, ?)";
        Connection conexao = null;
        PreparedStatement stmt = null;

        try {
            conexao = ConexaoPostgresDB.conectar();
            if (conexao != null) {
                stmt = conexao.prepareStatement(sql);
                stmt.setString(1, nome);
                stmt.setInt(2, idade);
                stmt.setString(3, contato);
                int linhasAfetadas = stmt.executeUpdate();
                if (linhasAfetadas > 0) {
                    System.out.println("Aluno "+ nome + " foi adicionado com sucesso!");
                }
            }
        } catch (SQLException error) {
            System.err.println("Erro ao inserir o aluno: " + error.getMessage());
        } finally {
            try{
                if (stmt != null) stmt.close();
                if(conexao != null) fecharConexao(conexao);
            } catch (SQLException error) {
                System.err.println("Erro ao fechar conexao: " + error.getMessage());
            }
        }
    }

    public static void getAlunos(){
        String sql = "SELECT * FROM aluno ORDER BY id_Aluno";
        Connection conexao = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conexao = ConexaoPostgresDB.conectar();
            if (conexao != null) {
                stmt = conexao.prepareStatement(sql);
                rs = stmt.executeQuery();
                System.out.println("\n--- Alunos cadastrados no BD ---");
                Boolean encontrouAluno = false;
                while (rs.next()) {
                    encontrouAluno = true;
                    int id = rs.getInt("id_Aluno");
                    String nome = rs.getString("nome_Aluno");
                    int idade =   rs.getInt("idade_Aluno");
                    String contato = rs.getString("contato_Aluno");
                    System.out.println("ID: "+id + ", Nome: " + nome + ", Idade: " + idade + ", Contato: " + contato);
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

    public static void atualizarAluno(int id, String nome, int idade, String contato){
        String sql = "UPDATE aluno SET nome_Aluno = ?, idade_Aluno = ?, contato_Aluno = ? WHERE id_Aluno = ?";
        Connection conexao = null;
        PreparedStatement stmt = null;
        try {
            conexao = ConexaoPostgresDB.conectar();
            if (conexao != null) {
                stmt = conexao.prepareStatement(sql);
                stmt.setString(1, nome);
                stmt.setInt(2, idade);
                stmt.setString(3, contato);
                stmt.setInt(4, id);
                int linhasAfetadas = stmt.executeUpdate();
                if (linhasAfetadas > 0) {
                    System.out.println("Aluno com ID "+ id +" atualizado com sucesso!");
                } else {
                    System.out.println("Nenhum aluno com o ID "+id+" foi encontrado");
                }
            }
        } catch (SQLException error) {
            System.err.println("Erro ao inserir o aluno: " + error.getMessage());
        } finally {
            try{
            if(stmt != null) stmt.close();
            if(conexao != null) fecharConexao(conexao);
        }
        catch(SQLException error){
            System.err.println("Erro ao fechar conexao: " + error.getMessage());}
        }
    }

    public static void removerAluno(int id){
        String sql = "DELETE FROM aluno WHERE id_Aluno = ?";
        Connection conexao = null;
        PreparedStatement stmt = null;
        try {
            conexao = ConexaoPostgresDB.conectar();
            if (conexao != null) {
                stmt = conexao.prepareStatement(sql);
                stmt.setInt(1, id);
                int linhasAfetadas = stmt.executeUpdate();
                if (linhasAfetadas > 0) {
                    System.out.println("Aluno com ID "+ id +" deletado com sucesso!");
                } else {
                    System.out.println("Nenhum aluno encontrado com ID "+id);
                }
            }
        } catch (SQLException error) {
            System.err.println("Erro ao inserir o aluno: " + error.getMessage());
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
