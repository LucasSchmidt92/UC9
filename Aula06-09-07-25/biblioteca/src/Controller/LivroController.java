package Controller;

import Dao.AlunoDAO;
import Dao.LivroDAO;
import Model.Aluno;

import java.util.List;

public class LivroController {

    private AlunoDAO livroDAO;

    public LivroController() {
        this.livroDAO = new LivroDAO();
    }

    public void cadastrarLivro(String titulo_livro, String autor_livro, String genero_livro, String isbn_livro) throws Exception {
        if (titulo_livro == null || titulo_livro.trim().isEmpty()) {
            throw new Exception("O nome do livro é obrigatório.");
        }
        if (autor_livro == null || autor_livro.trim().isEmpty()) {
            throw new Exception("o autor do livro é obrigatório");
        }
        if (genero_livro == null || genero_livro.trim().isEmpty()) {
            throw new Exception("Genero do livro é obrigatório");
        }
        if (isbn_livro == null || isbn_livro.trim().isEmpty()) {
            throw new Exception("O ISBN do livro é obrigatório");
        }
        Aluno aluno = new Aluno(titulo_livro, autor_livro, genero_livro, isbn_livro);
        livroDAO.setLivro(livro);
    }

    public Aluno buscarLivrooPorId(int id) {
        return livroDAO.buscarPorId(id);
    }

    public void atualizarAluno(int id, String nome, int idade, String contato) throws Exception {
        if (nome == null || nome.trim().isEmpty() || String.valueOf(idade).trim().isEmpty() || contato == null || contato.trim().isEmpty() ) {
            throw new Exception("Todos os campos do aluno são obrigatórios e devem ser válidos.");
        }
        Aluno aluno = new Aluno(id, nome, idade, contato);
        livroDAO.atualizar(aluno);
    }


    public List<Aluno> listarTodosAlunos() {
        return livroDAO.listarTodos();
    }


    public void removerAluno(int id) {
        livroDAO.remover(id);
    }


    public List<Aluno> buscarAlunoPorNome(String nome) {
        return AlunoDAO.buscarPorNome(nome);
    }
}
}
