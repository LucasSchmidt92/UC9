package Model;

import jakarta.persistence.*;


@Entity
@Table(name = "aluno")
public class Aluno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // para tipo BigSerial

    private int id_aluno;

    @Column(name="nome_aluno", nullable = false)
    private String nome_aluno;

    @Column(name= "idade_aluno", nullable = false)
    private int idade_aluno;

    @Column(name="contato_aluno", nullable = false)
    private String contato_aluno;

    public int getId_aluno() {
        return id_aluno;
    }

    public String getNome_aluno() {
        return nome_aluno;
    }

    public int getIdade_aluno() {
        return idade_aluno;
    }

    public String getContato_aluno() {
        return contato_aluno;
    }

    public void setId_aluno(int id_aluno) {
        this.id_aluno = id_aluno;
    }

    public void setNome_aluno(String nome_aluno) {
        this.nome_aluno = nome_aluno;
    }

    public void setIdade_aluno(int idade_aluno) {
        this.idade_aluno = idade_aluno;
    }

    public void setContato_aluno(String contato_aluno) {
        this.contato_aluno = contato_aluno;
    }
}

