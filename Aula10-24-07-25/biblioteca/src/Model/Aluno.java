package Model;

public class Aluno {
    //id nome idade telefone
    private int id;
    private String nome;
    private int idade;
    private String contato;

    public Aluno(int id, String nome, int idade, String contato){
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.contato = contato;
    }

    public Aluno(String nome, int idade, String contato){
        this.nome = nome;
        this.idade = idade;
        this.contato = contato;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getIdade() {
        return idade;
    }

    public String getContato() {
        return contato;
    }
}
