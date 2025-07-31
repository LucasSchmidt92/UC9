package Model.DAO;

import ConexaoMedicapp.Conexao;
import Model.Medication;
import Model.PrimaryUser;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static ConexaoMedicapp.Conexao.fecharConexao;

public class MedicationDAO {
    public static void setMedication(Medication medication){
        String sql = "INSERT INTO Medication (Medication_Name, Medication_Category, Dosage, Time_Of_Usage, Medication_Pharmaceutical_Form) VALUES (?, ?, ?, ?, ?)";
        Connection conexao = null;
        PreparedStatement stmt = null;

        try {
            conexao = Conexao.conectar();
            if (conexao != null) {
                stmt = conexao.prepareStatement(sql);
                stmt.setString(1, medication.getName());
                stmt.setString(2, medication.getCategory());
                stmt.setString(3, medication.getDosage());
                stmt.setString(4, medication.getTime_of_usage());
                stmt.setString(5, medication.getMedication_pharmaceutical_form());
                int linhasAfetadas = stmt.executeUpdate();
                if (linhasAfetadas > 0) {
                    System.out.println("Medicamento "+ medication.getName() + " foi adicionado com sucesso!");
                }
            }
        } catch (SQLException error) {
            System.err.println("Erro ao inserir o Medicamento: " + error.getMessage());
        } finally {
            try{
                if (stmt != null) stmt.close();
                if(conexao != null) fecharConexao(conexao);
            } catch (SQLException error) {
                System.err.println("Erro ao fechar conexao: " + error.getMessage());
            }
        }
    }

    public static void getMedications(){
        String sql = "SELECT * FROM Medication ORDER BY Medication_ID";
        Connection conexao = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conexao = Conexao.conectar();
            if (conexao != null) {
                stmt = conexao.prepareStatement(sql);
                rs = stmt.executeQuery();
                System.out.println("\n--- Medicamentos cadastrados no BD ---");
                Boolean encontrouMedicamento = false;
                while (rs.next()) {
                    encontrouMedicamento = true;
                    int id = rs.getInt("Medication_ID");
                    String name = rs.getString("Medication_Name");
                    String category = rs.getString("Medication_Category");
                    String dosage = rs.getString("Dosage");
                    String timeOfUsage = rs.getString("Time_Of_Usage");
                    Date medicationPharmaceuticalForm = rs.getDate("Medication_Pharmaceutical_Form");

                    System.out.println("ID: "+ id + ", Name: " + name + ", Category: " + category + ", Dosage: " + dosage+
                            ", Time of Usage: " + timeOfUsage + ", Pharmaceutical Form: " + medicationPharmaceuticalForm);
                }
                if (!encontrouMedicamento) {
                    System.out.println("Nenhum Remédio encontrado");
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

    public void updateMedication(Medication medication, int id){
        String sql = "UPDATE Medication SET Medication_Name = ?, Medication_Category = ?, Dosage = ?, Time_Of_Usage = ?, Medication_Pharmaceutical_Form = ? WHERE Medication_ID = ?";
        Connection conexao = null;
        PreparedStatement stmt = null;
        try {
            conexao = Conexao.conectar();
            if (conexao != null) {
                stmt = conexao.prepareStatement(sql);
                stmt.setString(1, medication.getName());
                stmt.setString(2, medication.getCategory());
                stmt.setString(3, medication.getDosage());
                stmt.setString(4, medication.getTime_of_usage());
                stmt.setString(5, medication.getMedication_pharmaceutical_form());
                stmt.setInt(6, id);
                int linhasAfetadas = stmt.executeUpdate();
                if (linhasAfetadas > 0) {
                    System.out.println("Medicamento com ID "+ id +" atualizado com sucesso!");
                } else {
                    System.out.println("Nenhum Medicamento com o ID "+ id +" foi encontrado");
                }
            }
        } catch (SQLException error) {
            System.err.println("Erro ao inserir o Medicamento: " + error.getMessage());
        } finally {
            try{
                if(stmt != null) stmt.close();
                if(conexao != null) fecharConexao(conexao);
            }
            catch(SQLException error){
                System.err.println("Erro ao fechar conexao: " + error.getMessage());}
        }
    }

    public static void deleteMedication(int id, Medication medication){
        String sql = "DELETE FROM Medication WHERE Medication_ID = ?";
        Connection conexao = null;
        PreparedStatement stmt = null;
        try {
            conexao = Conexao.conectar();
            if (conexao != null) {
                stmt = conexao.prepareStatement(sql);
                stmt.setInt(1, id);
                int linhasAfetadas = stmt.executeUpdate();
                if (linhasAfetadas > 0) {
                    System.out.println("Medicamento com ID "+ id +" deletado com sucesso!");
                } else {
                    System.out.println("Nenhum Medicamento encontrado com ID "+id);
                }
            }
        } catch (SQLException error) {
            System.err.println("Erro ao inserir o Medicamento: " + error.getMessage());
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
