package Dao;

import Conexao.ConexaoPostgresDB;
import Model.Emprestimo;
import Model.Livro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static Conexao.ConexaoPostgresDB.fecharConexao;

public class EmprestimoDAO {
    public void setLivro(Emprestimo emprestimo) {
        String sql = "INSERT INTO emprestimo (fk_id_livro, fk_id_aluno, data_emprestimo, data_devolucao) VALUES (?, ?, ?, ?)";
        Connection conexao = null;
        PreparedStatement stmt = null;

        try {
            conexao = ConexaoPostgresDB.conectar();
            if (conexao != null) {
                stmt = conexao.prepareStatement(sql);
                stmt.setInt(1,emprestimo.getFk_livro());
                stmt.setInt(2,emprestimo.getFk_aluno());
                stmt.setString(3, emprestimo.getData_emprestimo());
                stmt.setString(4, emprestimo.getData_devolucao());
                int linhasAfetadas = stmt.executeUpdate();
                if (linhasAfetadas > 0) {
                    System.out.println("Emprestimo foi adicionado com sucesso!");
                }
            }
        } catch (SQLException error) {
            System.err.println("Erro a criar emprestimo: " + error.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conexao != null) fecharConexao(conexao);
            } catch (SQLException error) {
                System.err.println("Erro ao fechar conexao: " + error.getMessage());
            }
        }
    }

    public List<Emprestimo> getEmprestimo() {
        String sql = "SELECT * FROM emprestimo";
        Connection conexao = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Emprestimo> emprestimo = new ArrayList<>(); //lista para armazenar os objetos emprestimo
        try {
            conexao = ConexaoPostgresDB.conectar();
            if (conexao != null) {
                stmt = conexao.prepareStatement(sql);
                rs = stmt.executeQuery();
                System.out.println("\n--- Emprestimo cadastrado no BD ---");

                while (rs.next()) {

                    int id_livro = rs.getInt("fk_id_livro");
                    int id_aluno = rs.getInt("fk_id_aluno");
                    String data_emprestimo= rs.getString("data_emprestimo");
                    String data_devolucao= rs.getString("data_devoulcao");

                    emprestimo.add(new Emprestimo(id_livro, id_aluno, data_emprestimo, data_devolucao));

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
        return emprestimo;
    }

    public void atualizarEmprestimo(Emprestimo emprestimo, int id_livro, int id_aluno) {
        String sql = "UPDATE emprestimo SET data_emprestimo= ?, data_devolucao = ?, WHERE fk_id_aluno AND fk_id_livro";
        Connection conexao = null;
        PreparedStatement stmt = null;
        try {
            conexao = ConexaoPostgresDB.conectar();
            if (conexao != null) {
                stmt = conexao.prepareStatement(sql);
                stmt.setString(1, emprestimo.getData_emprestimo());
                stmt.setString(2, emprestimo.getData_devolucao());
                stmt.setInt(3, id_livro);
                stmt.setInt(4, id_aluno);
                int linhasAfetadas = stmt.executeUpdate();
                if (linhasAfetadas > 0) {
                    System.out.println("Emprestimo atualizado com sucesso!");
                } else {
                    System.out.println("Nenhum emprestimo com esssas id foi encontrado");
                }
            }
        } catch (SQLException error) {
            System.err.println("Erro ao localizar emprestimo: " + error.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conexao != null) fecharConexao(conexao);
            } catch (SQLException error) {
                System.err.println("Erro ao fechar conexao: " + error.getMessage());
            }
        }
    }

    public void removerEmprestimo(Emprestimo emprestimo, int id_aluno, int id_livro) {
        String sql = "DELETE FROM emprestimo WHERE fk_id_aluno = ? AND fk_id_livro = ?";
        Connection conexao = null;
        PreparedStatement stmt = null;
        try {
            conexao = ConexaoPostgresDB.conectar();
            if (conexao != null) {
                stmt = conexao.prepareStatement(sql);
                stmt.setInt(1,id_aluno);
                stmt.setInt(1,id_livro);
                int linhasAfetadas = stmt.executeUpdate();
                if (linhasAfetadas > 0) {
                    System.out.println("Emprestimo deletado com sucesso!");
                } else {
                    System.out.println("Nenhum emprestimo com os dados selecionados.");
                }
            }
        } catch (SQLException error) {
            System.err.println("Erro ao encontrar emprestimo: " + error.getMessage());
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

