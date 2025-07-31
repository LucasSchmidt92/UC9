package Model;

import java.util.Locale;

public class Aluno {
    private int id;
    private String nome;
    private int idade;
    private String contato;

    public Aluno(int id, String nome, int idade, String contato) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.contato = contato;
    }

    public Aluno(String nome, int idade, String contato) {

        this.nome = nome;
        this.idade = idade;
        this.contato = contato;
    }

    public String getContato() {
        return contato;
    }

    public int getIdade() {
        return idade;
    }

    public String getNome() {
        return nome;
    }

    public int getId() {
        return id;

    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }
}
