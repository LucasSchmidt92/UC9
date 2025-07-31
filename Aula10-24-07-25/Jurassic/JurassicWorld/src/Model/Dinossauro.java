package Model;

import java.time.LocalDate;

public class Dinossauro {
    private int id;
    private String nome;
    private String especie;
    private String dieta; // carnivoro
    private String idade_estimada_anos; // Em milhoes
    private String idade_dinossauro; // idade
    private String status_cercado;


    // Construtor completo
    public Dinossauro(int id, String nome, String especie, String dieta, String idade_estimada_anos, String idade_dinossauro, String status_cercado) {
        this.id = id;
        this.nome = nome;
        this.especie = especie;
        this.dieta = dieta;
        this.idade_estimada_anos = idade_estimada_anos;
        this.idade_dinossauro = idade_dinossauro;
        this.status_cercado = status_cercado;
    }

    // Construtor para novas inserções (sem ID)
    public Dinossauro(String nome, String especie, String dieta, String idade_estimada_anos, String idade_dinossauro, String status_cercado) {
        this.nome = nome;
        this.especie = especie;
        this.dieta = dieta;
        this.idade_estimada_anos = idade_estimada_anos;
        this.idade_dinossauro = idade_dinossauro;
        this.status_cercado = status_cercado;
    }

    // Getters
    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getEspecie() { return especie; }
    public String getDieta() { return dieta; }
    public String getIdade_estimada_anos() { return idade_estimada_anos; }
    public String getIdade_dinossauro() { return idade_dinossauro; }
    public String getStatus_cercado(){return status_cercado; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setEspecie(String especie) { this.especie = especie; }
    public void setDieta(String dieta) { this.dieta = dieta; }
    public void setIdade_estimada_anos(String idade_estimada_anos) { this.idade_estimada_anos = idade_estimada_anos; }
    public void setIdade_dinossauro(String idade_dinossauro) { this.idade_dinossauro = idade_dinossauro; }

    @Override
    public String toString() {
        return "Dinossauro [ID=" + id + ", Nome=" + nome + ", Espécie=" + especie +
                ", dieta=" + dieta + ", idade_estimada=" + idade_estimada_anos + ", idade=" + idade_dinossauro + ", status=" + status_cercado + "]";
    }
}