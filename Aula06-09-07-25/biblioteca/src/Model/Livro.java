package Model;

public class Livro {

    private int id_livro;
    private String titulo_livro;
    private String autor_livro;
    private String genero_livro;
    private String isbn_livro;

    public Livro(int id_livro, String titulo_livro, String autor_livro, String genero_livro, String isbn_livro) {
        this.id_livro = id_livro;
        this.titulo_livro = titulo_livro;
        this.autor_livro = autor_livro;
        this.genero_livro = genero_livro;
        this.isbn_livro = isbn_livro;
    }

    public String getIsbn_livro() {
        return isbn_livro;
    }

    public String getGenero_livro() {
        return genero_livro;
    }

    public String getAutor_livro() {
        return autor_livro;
    }

    public String getTitulo_livro() {
        return titulo_livro;
    }

    public int getId_livro() {
        return id_livro;
    }

    public void setId_livro(int id_livro) {
        this.id_livro = id_livro;
    }

    public void setTitulo_livro(String titulo_livro) {
        this.titulo_livro = titulo_livro;
    }

    public void setAutor_livro(String autor_livro) {
        this.autor_livro = autor_livro;
    }

    public void setGenero_livro(String genero_livro) {
        this.genero_livro = genero_livro;
    }

    public void setIsbn_livro(String isbn_livro) {
        this.isbn_livro = isbn_livro;
    }
}


