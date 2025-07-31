package Model.DAO;

import Conexao.ConexaoPostgresDB;
import Model.Emprestimo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static Conexao.ConexaoPostgresDB.fecharConexao;

public class EmprestimoDAO {
    public void setEmprestimo (Emprestimo emprestimo){
        String sql = "INSERT INTO emprestimo(\n" +
                "\tfk_id_livro, fk_id_aluno, data_emprestimo, data_devolucao)\n" +
                "\tVALUES (?, ?, ?, ?);";
        Connection conexao = null;
        PreparedStatement stmt = null;
        try{
            conexao = ConexaoPostgresDB.conectar();
            if(conexao != null){
                stmt = conexao.prepareStatement(sql);

                stmt.setInt(1, emprestimo.getFk_livro());
                stmt.setInt(2, emprestimo.getFk_aluno());
                stmt.setString(3, emprestimo.getData_emprestimo());
                stmt.setString(4, emprestimo.getData_devolucao());
                int linhasAfetadas = stmt.executeUpdate();
                if (linhasAfetadas > 0) {
                    System.out.println("Empréstimo foi adicionado com sucesso!");
                }
            }
        } catch (SQLException e) {
            System.err.println("Falha ao cadastrar o Empréstimo: "+e.getMessage());
        }  finally {
            try{
                if (stmt != null) stmt.close();
                if(conexao != null) fecharConexao(conexao);
            } catch (SQLException error) {
                System.err.println("Erro ao fechar conexao: " + error.getMessage());
            }
        }
    }

    public int getIdAluno(String nome, String contato){
        String sql = "SELECT * FROM aluno WHERE nome_aluno = ? AND contato_aluno = ?";
        Connection conexao = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Integer> id_aluno = new ArrayList<Integer>();
        try {
            conexao = ConexaoPostgresDB.conectar();
            if (conexao != null) {
                stmt = conexao.prepareStatement(sql);
                stmt.setString(1,nome);
                stmt.setString(2,contato);
                rs = stmt.executeQuery();
                System.out.println("\n--- Empréstimos cadastrados no BD ---");
                while (rs.next()) {
                    id_aluno.add(rs.getInt("id_aluno"));
                    System.out.println(id_aluno.getFirst());
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
        System.out.println(id_aluno.getFirst());
        return id_aluno.getFirst();
    }

    public int getIdLivro(String isbn){
        String sql = "SELECT * FROM livro WHERE isbn_livro = ?";
        Connection conexao = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Integer> id_livro = new ArrayList<Integer>();
        try {
            conexao = ConexaoPostgresDB.conectar();
            if (conexao != null) {
                stmt = conexao.prepareStatement(sql);
                stmt.setString(1, isbn);
                rs = stmt.executeQuery();
                System.out.println("\n--- Empréstimos cadastrados no BD ---");
                while (rs.next()) {
                    id_livro.add(rs.getInt("id_livro"));
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

        return id_livro.getFirst();
    }

    public List<Emprestimo> getEmprestimos(){
        String sql = "SELECT * FROM emprestimo";
        Connection conexao = null;
        PreparedStatement stmt = null;
        List<Emprestimo> listaEmprestimos = new ArrayList<>();
        ResultSet rs = null;

        try {
            conexao = ConexaoPostgresDB.conectar();
            if (conexao != null) {
                stmt = conexao.prepareStatement(sql);
                rs = stmt.executeQuery();
                System.out.println("\n--- Empréstimos cadastrados no BD ---");
                while (rs.next()) {
                    int id = rs.getInt("id_emprestimo");
                    int id_livro = rs.getInt("fk_id_livro");
                    int id_aluno = rs.getInt("fk_id_aluno");
                    String data_emprestimo = rs.getString("data_emprestimo");
                    String devolucao = rs.getString("data_devolucao");

                    listaEmprestimos.add(new Emprestimo(id, id_livro, id_aluno, data_emprestimo, devolucao));
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

        return listaEmprestimos;
    }


    public List<Emprestimo> getEmprestimosByAluno(int fkAluno){
        String sql = "SELECT * FROM emprestimo WHERE fk_id_aluno = ?";
        Connection conexao = null;
        PreparedStatement stmt = null;
        List<Emprestimo> listaEmprestimos = new ArrayList<>();
        ResultSet rs = null;
        try {
            conexao = ConexaoPostgresDB.conectar();
            if (conexao != null) {
                stmt = conexao.prepareStatement(sql);
                rs = stmt.executeQuery();
                System.out.println("\n--- Empréstimos cadastrados no BD ---");
                while (rs.next()) {
                    int id_livro = rs.getInt("fk_id_livro");
                    int id = rs.getInt("id_emprestimo");
                    String data_emprestimo = rs.getString("data_emprestimo");
                    String devolucao = rs.getString("data_devolucao");

                    listaEmprestimos.add(new Emprestimo(id, id_livro, fkAluno, data_emprestimo, devolucao));
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

        return listaEmprestimos;
    }


    public List<Emprestimo> getEmprestimosByLivro(int fkLivro){
        String sql = "SELECT * FROM emprestimo WHERE fk_id_livro = ?";
        Connection conexao = null;
        PreparedStatement stmt = null;
        List<Emprestimo> listaEmprestimos = new ArrayList<>();
        ResultSet rs = null;

        try {
            conexao = ConexaoPostgresDB.conectar();
            if (conexao != null) {
                stmt = conexao.prepareStatement(sql);
                rs = stmt.executeQuery();
                System.out.println("\n--- Empréstimos cadastrados no BD ---");
                while (rs.next()) {
                    int id = rs.getInt("id_emprestimo");
                    int id_aluno = rs.getInt("fk_id_aluno");
                    String data_emprestimo = rs.getString("data_emprestimo");
                    String devolucao = rs.getString("data_devolucao");

                    listaEmprestimos.add(new Emprestimo(id, fkLivro, id_aluno, data_emprestimo, devolucao));
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

        return listaEmprestimos;
    }

    public Emprestimo getEmprestimoByID(int id){
        String sql = "SELECT * FROM emprestimo WHERE id_emprestimo = ?";
        Connection conexao = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conexao = ConexaoPostgresDB.conectar();
            if (conexao != null) {
                stmt = conexao.prepareStatement(sql);
                rs = stmt.executeQuery();
                System.out.println("\n--- Empréstimos cadastrados no BD ---");
                while (rs.next()) {
                    int id_livro = rs.getInt("fk_id_livro");
                    int id_aluno = rs.getInt("fk_id_aluno");
                    String data_emprestimo = rs.getString("data_emprestimo");
                    String devolucao = rs.getString("data_devolucao");

                    return new Emprestimo(id, id_livro, id_aluno, data_emprestimo, devolucao);
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

    public void updateEmprestimo(Emprestimo emprestimo, int id){
        String sql = "UPDATE emprestimo SET data_emprestimo = ?, data_devolucao = ?, fk_id_livro = ?, fk_id_aluno = ? WHERE id_emprestimo = ?";
        Connection conexao = null;
        PreparedStatement stmt = null;
        try {
            conexao = ConexaoPostgresDB.conectar();
            if (conexao != null) {
                stmt = conexao.prepareStatement(sql);
                stmt.setInt(3, emprestimo.getFk_livro());
                stmt.setInt(4, emprestimo.getFk_aluno());
                stmt.setString(1, emprestimo.getData_emprestimo());
                stmt.setString(2, emprestimo.getData_devolucao());
                stmt.setInt(5, id);
                int linhasAfetadas = stmt.executeUpdate();
                if (linhasAfetadas > 0) {
                    System.out.println("Emprestimo atualizado com sucesso!");
                } else {
                    System.out.println("Nenhum emprestimo com essas informações foi encontrado");
                }
            }
        } catch (SQLException error) {
            System.err.println("Erro ao inserir o empréstimo: " + error.getMessage());
        } finally {
            try{
                if(stmt != null) stmt.close();
                if(conexao != null) fecharConexao(conexao);
            }
            catch(SQLException error){
                System.err.println("Erro ao fechar conexao: " + error.getMessage());}
        }
    }

    public void deletarEmprestimo(int id){
        String sql = "DELETE FROM emprestimo WHERE id_emprestimo = ?";
        Connection conexao = null;
        PreparedStatement stmt = null;
        try {
            conexao = ConexaoPostgresDB.conectar();
            if (conexao != null) {
                stmt = conexao.prepareStatement(sql);
                stmt.setInt(1, id);
                int linhasAfetadas = stmt.executeUpdate();
                if (linhasAfetadas > 0) {
                    System.out.println("\n--- Emprestimo deletado com Sucesso! ---");
                } else {
                    System.out.println("Nenhum emprestimo encontrado com essas infosmações!");
                }
            }
        } catch (SQLException error) {
            System.err.println("Erro ao inserir o emprestimo: " + error.getMessage());
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