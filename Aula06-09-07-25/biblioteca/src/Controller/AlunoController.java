package Controller;

import Dao.AlunoDAO;
import Model.Aluno;

import java.util.List;

public class AlunoController {
    private AlunoDAO alunoDAO;

    public AlunoController() {
        this.alunoDAO = new AlunoDAO();
    }

    public void cadastrarAluno(String nome, int idade, String contato) throws Exception {
        if (nome == null || nome.trim().isEmpty()) {
            throw new Exception("O nome do aluno é obrigatório.");
        }
        if (String.valueOf(idade).trim().isEmpty()){
            throw new Exception("A idade do aluno é obrigatória");
        }
        if (contato == null || contato.trim().isEmpty()) {
            throw new Exception("Contato é obrigatório");
        }
        Aluno aluno = new Aluno(nome, idade, contato);
        alunoDAO.setAluno(aluno);
    }

    public Aluno buscarAlunoPorId(int id) {
        return alunoDAO.buscarPorId(id);
    }

    public void atualizarAluno(int id, String nome, int idade, String contato) throws Exception {
        if (nome == null || nome.trim().isEmpty() || String.valueOf(idade).trim().isEmpty() || contato == null || contato.trim().isEmpty() ) {
            throw new Exception("Todos os campos do aluno são obrigatórios e devem ser válidos.");
        }
        Aluno aluno = new Aluno(id, nome, idade, contato);
        alunoDAO.atualizar(aluno);
    }


    public List<Aluno> listarTodosAlunos() {
        return alunoDAO.listarTodos();
    }


    public void removerAluno(int id) {
        alunoDAO.remover(id);
    }


    public List<Aluno> buscarAlunoPorNome(String nome) {
        return AlunoDAO.buscarPorNome(nome);
    }
}
