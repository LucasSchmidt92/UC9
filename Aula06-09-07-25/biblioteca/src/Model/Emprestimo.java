package Model;

public class Emprestimo {
    int fk_aluno;
    int fk_livro;
    private String data_emprestimo;
    private String data_devolucao;

    public Emprestimo(int id_aluno, int id_livro, String data_emprestimo, String data_devolucao) {
        this.fk_aluno = id_aluno;
        this.fk_livro = id_livro;
        this.data_emprestimo = data_emprestimo;
        this.data_devolucao = data_devolucao;
    }

    public int getFk_aluno() {
        return fk_aluno;
    }

    public int getFk_livro() {
        return fk_livro;
    }

    public String getData_emprestimo() {
        return data_emprestimo;
    }

    public String getData_devolucao() {
        return data_devolucao;
    }

    public void setFk_aluno(int fk_aluno) {
        this.fk_aluno = fk_aluno;
    }

    public void setFk_livro(int fk_livro) {
        this.fk_livro = fk_livro;
    }

    public void setData_emprestimo(String data_emprestimo) {
        this.data_emprestimo = data_emprestimo;
    }

    public void setData_devolucao(String data_devolucao) {
        this.data_devolucao = data_devolucao;
    }
}
