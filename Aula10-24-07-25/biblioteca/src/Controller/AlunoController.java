package Controller;

import Model.Aluno;
import Model.DAO.AlunoDAO;

import java.util.List;

public class AlunoController {
    private AlunoDAO alunoDAO;

    public AlunoController() {
        this.alunoDAO = new AlunoDAO();
    }

    public void cadastrarAluno(String nome, int idade, String contato) throws Exception{
        if (nome == null || nome.trim().isEmpty()) {
            throw new Exception("O nome é obrigatório.");
        }
        if (String.valueOf(idade).trim().isEmpty()) {
            throw new Exception("Idade é obrigatório, caso não tenha saia daqui!");
        }
        if (contato == null || contato.trim().isEmpty()) {
            throw new Exception("Contato é obrigatório");
        }

        Aluno aluno = new Aluno(nome, idade, contato);
        alunoDAO.setAluno(aluno);
    }

    public void atualizarAluno(int id, String nome, int idade, String contato) throws Exception {
        if (nome == null || nome.trim().isEmpty() || String.valueOf(idade).trim().isEmpty() || contato == null || contato.trim().isEmpty() || String.valueOf(id).trim().isEmpty()) {
            throw new Exception("Todos os campos do dinossauro são obrigatórios e devem ser válidos.");
        }

        Aluno aluno = new Aluno(nome, idade, contato);
        alunoDAO.atualizarAluno(aluno, id);
    }

    public List<Aluno> listarAlunos(){
        return alunoDAO.getAlunos();
    }

    public Aluno getAlunoById(int id){
        return alunoDAO.getAlunoByID(id);
    }

    public List<Aluno> getAlunosByName(String nome){
        return alunoDAO.getAlunosByName(nome);
    }

    public void removerAluno(int id){
        alunoDAO.removerAluno(id);
    }
}
