package Conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ConexaoPostgresDB {
        private static final String URL = "jdbc:postgresql://localhost:5432/biblioteca_bd";
        private static final String USUARIO = "postgres"; //o usuario que configura o banco
        private static final String SENHA = "root"; // senha do banco

        public static Connection conectar () {
            Connection conexao = null; //inicializa a conexao como nula

            try {
                conexao = DriverManager.getConnection(URL, USUARIO, SENHA );
                System.out.println("Conexao com o banco de dados estabelecido com sucesso");

            } catch (SQLException e) {
                System.err.println("Erro ao conectar com o bd: " + e.getMessage());
            } return conexao; //retorna a conexao (pode ser null caso de erro)
        }


        public static void fecharConexao(Connection conexao) {
            if (conexao != null) {
                try {
                    conexao.close();
                    System.out.println("Conexao com o bd fechada");
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar a conexão com o bd: " + e.getMessage());
                }
            }
        }

    public static void setAluno(String nome, int idade, String contato) {
        String sql = "INSERT INTO aluno (nome_aluno, idade_aluno, contato_aluno) VALUES (?, ?, ?)";
        Connection conexao = null;
        PreparedStatement stmt = null;
        try {
            conexao = conectar(); //Usa nosso metodo de conexao
            if (conexao != null) {
                stmt = conexao.prepareStatement(sql);
                stmt.setString(1, nome);
                stmt.setInt(2, idade);
                stmt.setString(3, contato);
                int linhasAfetadas = stmt.executeUpdate();
                if (linhasAfetadas > 0) {
                    System.out.println("Aluno" + nome + "Inserido no BD com sucesso!");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir aluno no PostgreSQL : " + e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conexao != null) fecharConexao(conexao);
            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos apos inserção" + e.getMessage());
            }
        }
    }

    public static void getAluno() {
        String sql = "Select * FROM aluno ORDER BY id_aluno";
        Connection conexao = null;
        PreparedStatement stmt = null;
        ResultSet rs = null; //objeto para armazenar os resultados da consulta

        try {
            conexao = conectar();
            if (conexao !=null) {
                stmt = conexao.prepareStatement(sql);
                rs = stmt.executeQuery(); //executa a consulta Select
                System.out.println("\n--- Alunos cadastrados no bd ---");
                boolean encontrourAluno = false;
                while (rs.next()) { //Loop enquanto houver proximas linhas do resultado
                    encontrourAluno = true;
                    int id = rs.getInt("id_aluno");
                    String nome = rs.getString("nome_Aluno");
                    Number idade = rs.getInt("idade_Aluno");
                    String contato = rs.getString("contato_Aluno");
                    System.out.println("ID:" + id + ", Nome: " + nome + ", Idade: " + idade + ", Contato: " + contato);

                }
                if (!encontrourAluno) {
                    System.out.println("Nenhum aluno encontrado");
                }
                System.out.println("---------------------------\n");
            }
        } catch (SQLException e) {System.err.println("Erro ao consultar alunos no DB:" + e.getMessage());
        } finally {
            try{ //sempre fechar os recursos! na ordem inversa de abertura.
                if (rs != null) rs.close();
                if (stmt != null)  stmt.close();
                if (conexao != null) fecharConexao(conexao);
            } catch (SQLException e) {System.err.println("Erro ao fechar recusrsos apos consulta: "
                    +e.getMessage());
            }}
    }

        public static void main(String[] args) {
            Connection testeConexao = ConexaoPostgresDB.conectar();
            if (testeConexao != null) {
                //se a conexao for bem sucedida, podemos fechala
                ConexaoPostgresDB.fecharConexao(testeConexao);
            }

            // testando a inserção
            System.out.println("\n --- Realizando as Inserções ---");
            setAluno("Gabriel", 19, "51984077925");
            setAluno("Lucca", 23, "5193175194");

            //testetando a consulta
            System.out.println("\n --- Realizando a consulta ---");
            getAluno(); //chama o metodo para listar os alunos
        }


}


