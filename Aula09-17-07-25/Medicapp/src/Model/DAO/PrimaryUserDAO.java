package Model.DAO;

import ConexaoMedicapp.Conexao;
import Model.PrimaryUser;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static ConexaoMedicapp.Conexao.fecharConexao;

public class PrimaryUserDAO {
    public static void setUsers(PrimaryUser user){
        String sql = "INSERT INTO Primary_User (User_Name, User_Email, User_Address, User_Contact, User_Birth_Date) VALUES (?, ?, ?, ?, ?)";
        Connection conexao = null;
        PreparedStatement stmt = null;

        try {
            conexao = Conexao.conectar();
            if (conexao != null) {
                stmt = conexao.prepareStatement(sql);
                stmt.setString(1, user.getName());
                stmt.setString(2,user.getEmail());
                stmt.setString(3, user.getAddress());
                stmt.setString(4, user.getContact());
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                Date date = (Date) format.parse(user.getBirth_date());
                stmt.setDate(5, date);
                int linhasAfetadas = stmt.executeUpdate();
                if (linhasAfetadas > 0) {
                    System.out.println("Usuario "+ user.getName() + " foi adicionado com sucesso!");
                }
            }
        } catch (SQLException | ParseException error) {
            System.err.println("Erro ao inserir o Usuário: " + error.getMessage());
        } finally {
            try{
                if (stmt != null) stmt.close();
                if(conexao != null) fecharConexao(conexao);
            } catch (SQLException error) {
                System.err.println("Erro ao fechar conexao: " + error.getMessage());
            }
        }
    }

    public static void getUsers(){
        String sql = "SELECT * FROM Primary_User ORDER BY User_ID";
        Connection conexao = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conexao = Conexao.conectar();
            if (conexao != null) {
                stmt = conexao.prepareStatement(sql);
                rs = stmt.executeQuery();
                System.out.println("\n--- Usuarios cadastrados no BD ---");
                Boolean encontrouUsuario = false;
                while (rs.next()) {
                    encontrouUsuario = true;
                    int id = rs.getInt("User_ID");
                    String name = rs.getString("User_Name");
                    String email = rs.getString("User_Email");
                    String address = rs.getString("User_Address");
                    String contact = rs.getString("User_Contact");
                    Date birth_date = rs.getDate("User_Birth_Date");
                    System.out.println("ID: "+id + ", Name: " + name + ", Email: " + email + ", Address: " + address+
                            ", Contact: " + contact + ", Birth Date: " + birth_date);
                }
                if (!encontrouUsuario) {
                    System.out.println("Nenhum Usuário encontrado");
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

    public void updateUser(PrimaryUser user){
        String sql = "UPDATE Primary_User SET User_Name = ?, User_Email = ?, User_Address = ?, User_Contact = ?, User_Birth_Date = ? WHERE User_ID = ?";
        Connection conexao = null;
        PreparedStatement stmt = null;
        try {
            conexao = Conexao.conectar();
            if (conexao != null) {
                stmt = conexao.prepareStatement(sql);
                stmt.setString(1, user.getName());
                stmt.setString(2,user.getEmail());
                stmt.setString(3, user.getAddress());
                stmt.setString(4, user.getContact());
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                Date date = (Date) format.parse(user.getBirth_date());
                stmt.setDate(5, date);
                stmt.setInt(6, user.getId_user());
                int linhasAfetadas = stmt.executeUpdate();
                if (linhasAfetadas > 0) {
                    System.out.println("Aluno com ID "+ user.getId_user() +" atualizado com sucesso!");
                } else {
                    System.out.println("Nenhum Usuário com o ID "+ user.getId_user() +" foi encontrado");
                }
            }
        } catch (SQLException error) {
            System.err.println("Erro ao inserir o Usuário: " + error.getMessage());
        } catch (ParseException e) {
            System.out.println("Erro ao converter data de aniversário" + e.getMessage());
        } finally {
            try{
                if(stmt != null) stmt.close();
                if(conexao != null) fecharConexao(conexao);
            }
            catch(SQLException error){
                System.err.println("Erro ao fechar conexao: " + error.getMessage());}
        }
    }

    public static void deleteUser(int id, PrimaryUser user){
        String sql = "DELETE FROM Primary_User WHERE User_ID = ?";
        Connection conexao = null;
        PreparedStatement stmt = null;
        try {
            conexao = Conexao.conectar();
            if (conexao != null) {
                stmt = conexao.prepareStatement(sql);
                stmt.setInt(1, id);
                int linhasAfetadas = stmt.executeUpdate();
                if (linhasAfetadas > 0) {
                    System.out.println("Usuario com ID "+ id +" deletado com sucesso!");
                } else {
                    System.out.println("Nenhum Usuário encontrado com ID "+id);
                }
            }
        } catch (SQLException error) {
            System.err.println("Erro ao inserir o Usuário: " + error.getMessage());
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
