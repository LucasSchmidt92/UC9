
package Model.DAO;

import Conexao.ConexaoPostgresDB;
import Model.Livro;
import Model.Livro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static Conexao.ConexaoPostgresDB.fecharConexao;

public class LivroDAO {
    public void setLivro (Livro livro){
        String sql = "INSERT INTO livro(\n" +
                "\ttitulo_livro, autor_livro, genero_livro, isbn_livro)\n" +
                "\tVALUES (?, ?, ?, ?);";
        Connection conexao = null;
        PreparedStatement stmt = null;

        try{
            conexao = ConexaoPostgresDB.conectar();
            if(conexao != null){
                stmt = conexao.prepareStatement(sql);

                stmt.setString(1, livro.getTitulo());
                stmt.setString(2, livro.getAutor());
                stmt.setString(3, livro.getGenero());
                stmt.setString(4, livro.getIsbn());
                int linhasAfetadas = stmt.executeUpdate();
                if (linhasAfetadas > 0) {
                    System.out.println("Livro "+ livro.getTitulo() + " foi adicionado com sucesso!");
                }
            }
        } catch (SQLException e) {
            System.err.println("Falha ao cadastrar o Livro: "+e.getMessage());
        }  finally {
            try{
                if (stmt != null) stmt.close();
                if(conexao != null) fecharConexao(conexao);
            } catch (SQLException error) {
                System.err.println("Erro ao fechar conexao: " + error.getMessage());
            }
        }
    }

    public List<Livro> getLivros(){
        String sql = "SELECT * FROM livro";
        Connection conexao = null;
        PreparedStatement stmt = null;
        List<Livro> listaLivros = new ArrayList<>();
        ResultSet rs = null;

        try {
            conexao = ConexaoPostgresDB.conectar();
            if (conexao != null) {
                stmt = conexao.prepareStatement(sql);
                rs = stmt.executeQuery();
                System.out.println("\n--- Livros cadastrados no BD ---");
                while (rs.next()) {
                    int id = rs.getInt("id_livro");
                    String genero = rs.getString("genero_livro");
                    String isbn = rs.getString("isbn_livro");
                    String titulo = rs.getString("titulo_livro");
                    String autor = rs.getString("autor_livro");

                    listaLivros.add(new Livro(id, titulo, autor, genero, isbn));
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

        return listaLivros;
    }

    public Livro getLivroByID(int id){
        String sql = "SELECT * FROM livro WHERE id_livro = ?";
        Connection conexao = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conexao = ConexaoPostgresDB.conectar();
            if (conexao != null) {
                stmt = conexao.prepareStatement(sql);
                stmt.setInt(1, id);
                rs = stmt.executeQuery();
                System.out.println("\n--- Livros cadastrados no BD ---");
                while (rs.next()) {
                    String genero = rs.getString("genero_livro");
                    String isbn = rs.getString("isbn_livro");
                    String titulo = rs.getString("titulo_livro");
                    String autor = rs.getString("autor_livro");

                    return new Livro(id, titulo, autor, genero, isbn);
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
        return null;
    }

    public List<Livro> getLivrosByTitulo(String titulo){
        String sql = "SELECT * FROM livro WHERE titulo_livro = ?";
        Connection conexao = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Livro> livros = new ArrayList<>();
        try {
            conexao = ConexaoPostgresDB.conectar();
            if (conexao != null) {
                stmt = conexao.prepareStatement(sql);
                stmt.setString(1, titulo);
                rs = stmt.executeQuery();
                System.out.println("\n--- Livros cadastrados no BD ---");
                while (rs.next()) {
                    int id = rs.getInt("id_livro");
                    String genero = rs.getString("genero_livro");
                    String isbn = rs.getString("isbn_livro");
                    String autor = rs.getString("autor_livro");

                    livros.add(new Livro(id, titulo, autor, genero, isbn));
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
        return livros;
    }

    public void updateLivros(Livro livro, int id){
        String sql = "UPDATE livro SET titulo_livro = ?, autor_livro = ?, genero_livro = ?, isbn_livro = ? WHERE id_livro = ?";
        Connection conexao = null;
        PreparedStatement stmt = null;
        try {
            conexao = ConexaoPostgresDB.conectar();
            if (conexao != null) {
                stmt = conexao.prepareStatement(sql);
                stmt.setString(1, livro.getTitulo());
                stmt.setString(2, livro.getAutor());
                stmt.setString(3, livro.getGenero());
                stmt.setString(4, livro.getIsbn());
                stmt.setInt(5, id);
                int linhasAfetadas = stmt.executeUpdate();
                if (linhasAfetadas > 0) {
                    System.out.println("Livro com ID "+ livro.getId() +" atualizado com sucesso!");
                } else {
                    System.out.println("Nenhum livro com o ID "+ livro.getId() +" foi encontrado");
                }
            }
        } catch (SQLException error) {
            System.err.println("Erro ao inserir o livro: " + error.getMessage());
        } finally {
            try{
                if(stmt != null) stmt.close();
                if(conexao != null) fecharConexao(conexao);
            }
            catch(SQLException error){
                System.err.println("Erro ao fechar conexao: " + error.getMessage());}
        }
    }

    public void deletarLivro(int id){
        String sql = "DELETE FROM livro WHERE id_livro = ?";
        Connection conexao = null;
        PreparedStatement stmt = null;
        try {
            conexao = ConexaoPostgresDB.conectar();
            if (conexao != null) {
                stmt = conexao.prepareStatement(sql);
                stmt.setInt(1, id);
                int linhasAfetadas = stmt.executeUpdate();
                if (linhasAfetadas > 0) {
                    System.out.println("\n--- Livros com ID "+id+" Deletado com Sucesso! ---");
                } else {
                    System.out.println("Nenhum livro encontrado com ID "+id);
                }
            }
        } catch (SQLException error) {
            System.err.println("Erro ao inserir o livro: " + error.getMessage());
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
