package Model.DAO;

import ConexaoMedicapp.Conexao;
import Model.AppointmentBook;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static ConexaoMedicapp.Conexao.fecharConexao;

public class AppointmentDAO {
    public static void setAppointmentNaturalPerson (AppointmentBook.AppointmentNaturalPerson appointment){
        String sql = "INSERT INTO Appointment_Book_Natural_Person (Appointment_End_Date, Appointment_Start_Date, fk_Natural_Person_ID, fk_Medication_ID) VALUES (?, ?, ?, ?)";
        Connection conexao = null;
        PreparedStatement stmt = null;

        try {
            conexao = Conexao.conectar();
            if (conexao != null) {
                stmt = conexao.prepareStatement(sql);
                SimpleDateFormat endFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date endDate = (Date) endFormat.parse(appointment.getEnd_date());
                stmt.setDate(1, endDate);
                SimpleDateFormat startFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date startDate = (Date) startFormat.parse(appointment.getStart_date());
                stmt.setDate(2, startDate);
                stmt.setInt(3, appointment.getFk_naturalPerson());
                stmt.setInt(4, appointment.getFk_medication());
                int linhasAfetadas = stmt.executeUpdate();
                if (linhasAfetadas > 0) {
                    System.out.println("Agenda foi criada com sucesso!");
                }
            }
        } catch (SQLException error) {
            System.err.println("Erro ao criar Agenda: " + error.getMessage());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } finally {
            try{
                if (stmt != null) stmt.close();
                if(conexao != null) fecharConexao(conexao);
            } catch (SQLException error) {
                System.err.println("Erro ao fechar conexao: " + error.getMessage());
            }
        }
    }

    public static void getAppointmentsNaturalPerson(){
        String sql = "SELECT * FROM Appointment_Book_Natural_Person ORDER BY Appointment_ID";
        Connection conexao = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conexao = Conexao.conectar();
            if (conexao != null) {
                stmt = conexao.prepareStatement(sql);
                rs = stmt.executeQuery();
                System.out.println("\n--- Agendas cadastrados no BD ---");
                Boolean encontrouAgenda = false;
                while (rs.next()) {
                    encontrouAgenda = true;
                    int id = rs.getInt("Appointment_ID");
                    String endDate = String.valueOf(rs.getDate("Appointment_End_date"));
                    String startDate = String.valueOf(rs.getDate("Appointment_Start_Date"));
                    String personId = rs.getString("fk_Natural_Person_ID");
                    String medicationId = rs.getString("fk_Medication_ID");

                    System.out.println("ID: "+ id + ", Start Date: " + startDate + ", End Date: " + endDate + ", Person ID: " + personId +
                            ", Medication ID: " + medicationId);
                }
                if (!encontrouAgenda) {
                    System.out.println("Nenhuma Agenda encontrada");
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

    public void updateAppointment(AppointmentBook.AppointmentNaturalPerson appointment, int id){
        String sql = "UPDATE Appointment_Book_Natural_Person SET Appointment_End_Date = ?, Appointment_Start_Date = ?, fk_Natural_Person_ID = ?, fk_Medication_ID = ? WHERE Appointment_ID = ?";
        Connection conexao = null;
        PreparedStatement stmt = null;
        try {
            conexao = Conexao.conectar();
            if (conexao != null) {
                stmt = conexao.prepareStatement(sql);
                SimpleDateFormat endFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date endDate = (Date) endFormat.parse(appointment.getEnd_date());
                stmt.setDate(1, endDate);
                SimpleDateFormat startFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date startDate = (Date) startFormat.parse(appointment.getStart_date());
                stmt.setDate(2, startDate);
                stmt.setInt(3, appointment.getFk_naturalPerson());
                stmt.setInt(4, appointment.getFk_medication());
                stmt.setInt(5, id);
                int linhasAfetadas = stmt.executeUpdate();
                if (linhasAfetadas > 0) {
                    System.out.println("Agenda com ID "+ id +" atualizado com sucesso!");
                } else {
                    System.out.println("Nenhuma Agenda com o ID "+ id +" foi encontrado");
                }
            }
        } catch (SQLException error) {
            System.err.println("Erro ao criar Agenda: " + error.getMessage());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } finally {
            try{
                if(stmt != null) stmt.close();
                if(conexao != null) fecharConexao(conexao);
            }
            catch(SQLException error){
                System.err.println("Erro ao fechar conexao: " + error.getMessage());}
        }
    }

    public static void deleteAppointmentNaturalPerson(int id, AppointmentBook.AppointmentNaturalPerson appointment){
        String sql = "DELETE FROM Appointment_Book_Natural_Person WHERE Appointment_ID = ?";
        Connection conexao = null;
        PreparedStatement stmt = null;
        try {
            conexao = Conexao.conectar();
            if (conexao != null) {
                stmt = conexao.prepareStatement(sql);
                stmt.setInt(1, id);
                int linhasAfetadas = stmt.executeUpdate();
                if (linhasAfetadas > 0) {
                    System.out.println("Agenda com ID "+ id +" deletado com sucesso!");
                } else {
                    System.out.println("Nenhuma Agenda encontrado com ID "+id);
                }
            }
        } catch (SQLException error) {
            System.err.println("Erro ao criar Agenda: " + error.getMessage());
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
