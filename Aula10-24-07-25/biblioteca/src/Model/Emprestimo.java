package Model;

public class Emprestimo {
    private int id_emprestimo;
    private int fk_livro;
    private int fk_aluno;
    private String data_emprestimo;
    private String data_devolucao;

    public Emprestimo(int id_emprestimo, int fk_livro, int fk_aluno, String data_emprestimo, String data_devolucao) {
        this.id_emprestimo = id_emprestimo;
        this.fk_livro = fk_livro;
        this.fk_aluno = fk_aluno;
        this.data_emprestimo = data_emprestimo;
        this.data_devolucao = data_devolucao;
    }

    public Emprestimo(int id_livro, int id_aluno, String data_emprestimo, String data_devolucao) {
        this.fk_livro = id_livro;
        this.fk_aluno = id_aluno;
        this.data_emprestimo = data_emprestimo;
        this.data_devolucao = data_devolucao;
    }

    public int getId_emprestimo() {
        return id_emprestimo;
    }

    public int getFk_livro() {
        return fk_livro;
    }

    public int getFk_aluno() {
        return fk_aluno;
    }

    public String getData_emprestimo() {
        return data_emprestimo;
    }

    public String getData_devolucao() {
        return data_devolucao;
    }
}
