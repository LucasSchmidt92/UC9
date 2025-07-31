package Model.DAO;

import Conexao.ConexaoPostgresDB;
import Model.Dinossauro;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DinossauroDAO { // Sem interface

    public void inserir(Dinossauro dinossauro) {
        String sql = "INSERT INTO dinossauro (nome, especie, dieta, idade_estimada_anos, idade_dinossauro, status_cercado) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexaoPostgresDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, dinossauro.getNome());
            stmt.setString(2, dinossauro.getEspecie());
            stmt.setString(3, dinossauro.getDieta());
            stmt.setString(4, dinossauro.getIdade_estimada_anos());
            stmt.setString(5, dinossauro.getIdade_dinossauro());
            stmt.setString(6, dinossauro.getStatus_cercado());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    dinossauro.setId(rs.getInt(1));
                }
            }
            System.out.println("Dinossauro inserido: " + dinossauro.getNome());
        } catch (SQLException e) {
            System.err.println("Erro ao inserir dinossauro: " + e.getMessage());
            // Exemplo de tratamento para nome/espécie duplicados se houver UNIQUE constraint
            if (e.getSQLState() != null && e.getSQLState().startsWith("23")) {
                System.err.println("Erro: Dinossauro com nome e espécie semelhantes já cadastrado.");
            }
        }
    }

    public List<Dinossauro> listarTodos() {
        List<Dinossauro> dinossauros = new ArrayList<>();
        String sql = "SELECT id_dinossauro, nome, especie, dieta, idade_estimada_anos, idade_dinossauro, status_cercado FROM dinossauro ORDER BY nome";
        try (Connection conn = ConexaoPostgresDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Dinossauro dinossauro = new Dinossauro(
                        rs.getInt("id_dinossauro"),
                        rs.getString("nome"),
                        rs.getString("especie"),
                        rs.getString("dieta"),
                        rs.getString("idade_estimada_anos"),
                        rs.getString("idade_dinossauro"),
                        rs.getString("status_cercado")
                );
                dinossauros.add(dinossauro);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar dinossauros: " + e.getMessage());
        }
        return dinossauros;
    }

    public Dinossauro buscarPorId(int idDinossauro) {
        String sql = "SELECT id_dinossauro, nome, especie, dieta, idade_estimada_anos, idade_dinossauro, status_cercado FROM dinossauro WHERE id_dinossauro = ?";
        try (Connection conn = ConexaoPostgresDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idDinossauro);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Dinossauro(
                            rs.getInt("id_dinossauro"),
                            rs.getString("nome"),
                            rs.getString("especie"),
                            rs.getString("dieta"),
                            rs.getString("idade_estimada_anos"),
                            rs.getString("idade_dinossauro"),
                            rs.getString("status_cercado")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar dinossauro por ID: " + e.getMessage());
        }
        return null;
    }

    public void atualizar(Dinossauro dinossauro) {
        String sql = "UPDATE dinossauro SET nome = ?, especie = ?, dieta = ?, idade_estimada_anos = ?, idade_dinossauro = ?, status_cercado = ? WHERE id_dinossauro = ?";
        try (Connection conn = ConexaoPostgresDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, dinossauro.getNome());
            stmt.setString(2, dinossauro.getEspecie());
            stmt.setString(3, dinossauro.getDieta());
            stmt.setString(4, dinossauro.getIdade_estimada_anos());
            stmt.setString(5, dinossauro.getIdade_dinossauro());
            stmt.setString(6, dinossauro.getStatus_cercado());
            stmt.setInt(7, dinossauro.getId());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Dinossauro atualizado: " + dinossauro.getNome());
            } else {
                System.out.println("Nenhum dinossauro encontrado para atualização com ID: " + dinossauro.getId());
            }
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar dinossauro: " + e.getMessage());
        }
    }

    public void remover(int idDinossauro) {
        String sql = "DELETE FROM dinossauros WHERE id_dinossauro = ?";
        try (Connection conn = ConexaoPostgresDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idDinossauro);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Dinossauro removido com ID: " + idDinossauro);
            } else {
                System.out.println("Nenhum dinossauro encontrado para remoção com ID: " + idDinossauro);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao remover dinossauro: " + e.getMessage());
        }
    }

    public List<Dinossauro> buscarPorNome(String nomeBusca) {
        List<Dinossauro> dinossauros = new ArrayList<>();
        String sql = "SELECT id_dinossauro, nome, especie, dieta, idade_estimada_anos, idade_dinossauro, status_cercado FROM dinossauro WHERE nome ILIKE ? ORDER BY nome";
        try (Connection conn = ConexaoPostgresDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + nomeBusca + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Dinossauro dinossauro = new Dinossauro(
                            rs.getInt("id_dinossauro"),
                            rs.getString("nome"),
                            rs.getString("especie"),
                            rs.getString("dieta"),
                            rs.getString("idade_estimada_anos"),
                            rs.getString("idade_dinossauro"),
                            rs.getString("status_cercado")
                    );
                    dinossauros.add(dinossauro);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar dinossauros por nome: " + e.getMessage());
        }
        return dinossauros;
    }
}