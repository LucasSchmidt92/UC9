package Controller;

import Model.Livro;
import Model.DAO.LivroDAO;

import java.util.List;

public class LivroController {
    private LivroDAO livroDAO;

    public LivroController() {
        this.livroDAO = new LivroDAO();
    }

    public void cadastrarLivro(String titulo, String autor, String genero, String isbn) throws Exception{
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new Exception("O Título do Livro é obrigatório.");
        }
        if (autor == null || autor.trim().isEmpty()) {
            throw new Exception("O Nome do autor do livro é obrigatório!");
        }
        if (genero == null || genero.trim().isEmpty()) {
            throw new Exception("Gênero do Livro é obrigatório");
        }
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new Exception("ISBN do livro é obrigatório");
        }

        Livro livro = new Livro(titulo, autor, genero, isbn);
        livroDAO.setLivro(livro);
    }

    public void atualizarLivro(int id, String titulo, String autor, String genero, String isbn) throws Exception {
        if (titulo == null || titulo.trim().isEmpty() || autor == null || autor.trim().isEmpty() || genero == null || genero.trim().isEmpty() || isbn == null || isbn.trim().isEmpty()) {
            throw new Exception("Todos os campos do livro são obrigatórios e devem ser válidos.");
        }
        Livro livro = new Livro(titulo, autor, genero, isbn);
        livroDAO.updateLivros(livro, id);
    }

    public List<Livro> listarLivros(){
        return livroDAO.getLivros();
    }

    public Livro getLivroById(int id){
        return livroDAO.getLivroByID(id);
    }

    public List<Livro> getLivrosByTitulo(String titulo){
        return livroDAO.getLivrosByTitulo(titulo);
    }

    public void removerLivro(int id){
        livroDAO.deletarLivro(id);
    }
}
