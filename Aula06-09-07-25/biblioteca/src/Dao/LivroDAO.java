package Dao;

import Conexao.ConexaoPostgresDB;
import Model.Aluno;
import Model.Livro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static Conexao.ConexaoPostgresDB.fecharConexao;

public class LivroDAO {
    public void setLivro(Livro livro) {
        String sql = "INSERT INTO livro (titulo_livro, autor_livro, genero_livro, isbn_livro ) VALUES (?, ?, ?, ?)";
        Connection conexao = null;
        PreparedStatement stmt = null;

        try {
            conexao = ConexaoPostgresDB.conectar();
            if (conexao != null) {
                stmt = conexao.prepareStatement(sql);
                stmt.setString(1, livro.getTitulo_livro());
                stmt.setString(2, livro.getAutor_livro());
                stmt.setString(3, livro.getGenero_livro());
                stmt.setString(4, livro.getIsbn_livro());
                int linhasAfetadas = stmt.executeUpdate();
                if (linhasAfetadas > 0) {
                    System.out.println(" O Livro " + livro.getTitulo_livro() + " foi adicionado com sucesso!");
                }
            }
        } catch (SQLException error) {
            System.err.println("Erro ao inserir o livro: " + error.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conexao != null) fecharConexao(conexao);
            } catch (SQLException error) {
                System.err.println("Erro ao fechar conexao: " + error.getMessage());
            }
        }
    }

    public List<Livro> getLivros() {
        String sql = "SELECT * FROM livro ORDER BY id_Livro";
        Connection conexao = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Livro> livros = new ArrayList<>(); //lista para armazenar os objetos aluno
        try {
            conexao = ConexaoPostgresDB.conectar();
            if (conexao != null) {
                stmt = conexao.prepareStatement(sql);
                rs = stmt.executeQuery();
                System.out.println("\n--- Livros cadastrados no BD ---");
                Boolean encontrouAluno = false;
                while (rs.next()) {
                    encontrouAluno = true;
                    int id = rs.getInt("id_livro");
                    String titulo= rs.getString("titulo_livro");
                    String autor= rs.getString("autor_livro");
                    String genero= rs.getString("genero_livro");
                    String isbn= rs.getString("isbn_livro");



                    livros.add(new Livro(id, titulo, autor, genero, isbn));

                }
            }
        } catch (SQLException error) {
            System.out.println("Erro ao conectar com o banco de dados: " + error.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conexao != null) fecharConexao(conexao);
            } catch (SQLException error) {
                System.err.println("Erro ao fechar recursos após pesquisa: " + error.getMessage());
            }
        }
        return livros;
    }

    public void atualizarLivro(Livro livro) {
        String sql = "UPDATE livro SET titulo_livro = ?, autor_livro = ?, genero_livro = ?, isbn_livro =? WHERE id_Aluno = ?";
        Connection conexao = null;
        PreparedStatement stmt = null;
        try {
            conexao = ConexaoPostgresDB.conectar();
            if (conexao != null) {
                stmt = conexao.prepareStatement(sql);
                stmt.setString(1, livro.getTitulo_livro());
                stmt.setString(2, livro.getAutor_livro());
                stmt.setString(3, livro.getGenero_livro());
                stmt.setInt(4, livro.getId_livro());
                int linhasAfetadas = stmt.executeUpdate();
                if (linhasAfetadas > 0) {
                    System.out.println("Livro com ID " + livro.getId_livro() + " atualizado com sucesso!");
                } else {
                    System.out.println("Nenhum livro com o ID " + livro.getId_livro() + " foi encontrado");
                }
            }
        } catch (SQLException error) {
            System.err.println("Erro ao inserir o aluno: " + error.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conexao != null) fecharConexao(conexao);
            } catch (SQLException error) {
                System.err.println("Erro ao fechar conexao: " + error.getMessage());
            }
        }
    }

    public void removerLivro(int id_Livro) {
        String sql = "DELETE FROM livro WHERE id_Livro = ?";
        Connection conexao = null;
        PreparedStatement stmt = null;
        try {
            conexao = ConexaoPostgresDB.conectar();
            if (conexao != null) {
                stmt = conexao.prepareStatement(sql);
                stmt.setInt(1,id_Livro);
                int linhasAfetadas = stmt.executeUpdate();
                if (linhasAfetadas > 0) {
                    System.out.println("Livro com ID " + id_Livro + " deletado com sucesso!");
                } else {
                    System.out.println("Nenhum livro encontrado com ID " + id_Livro);
                }
            }
        } catch (SQLException error) {
            System.err.println("Erro ao inserir o aluno: " + error.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conexao != null) fecharConexao(conexao);
            } catch (SQLException error) {
                System.out.println("Erro ao fechar conexao: " + error.getMessage());
            }
        }
    }
}

