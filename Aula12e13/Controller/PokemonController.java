package Controller;

import Model.DAO.PokemonDAO;
import Model.Pokemon;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PokemonController {
    private PokemonDAO pokemonDAO;

    public PokemonController() {
        this.pokemonDAO = new PokemonDAO();
    }

    public void cadastrarPokemon(String nome, String tipoPrimario, String tipoSecundario, int nivel, int hpMaximo) throws Exception {
        // --- EXERCÍCIO: Adicionar validações aqui! ---
        String segundoTipo = null;
        if (nome == null || nome.trim().isEmpty()) {
            throw new Exception("O nome do Pokémon é obrigatório.");
        }

        if (!revisaoDeTexto(nome) && !nome.trim().toLowerCase().equalsIgnoreCase("porygon2")) {
            throw new Exception("O nome do pokémon não deve conter números!");
        }

        if (tipoPrimario == null || tipoPrimario.trim().isEmpty()) {
            throw new Exception("O tipo primário do Pokémon é obrigatório.");
        }

        if (!revisaoDeTexto(tipoPrimario)) {
            throw new Exception("O Tipo do Pokémon não deve conter números!");
        }

        if (tipoSecundario != null) {
            if (tipoPrimario.toLowerCase().equals(tipoSecundario.toLowerCase()) || !revisaoDeTexto(tipoSecundario)) {
                throw new Exception("O Tipo Secundário não pode ser igual ao Tipo Primário e também não pode conter números.");
            }
            segundoTipo = tipoSecundario.toLowerCase();
        }

        if (nivel < 0 || nivel > 100 || String.valueOf(nivel).trim().isEmpty()) {
            throw new Exception("O nível não é válido.");
        }

        if (hpMaximo < 0 || String.valueOf(hpMaximo).trim().isEmpty()) {
            throw new Exception("O HP máximo não é válido.");
        }

        if (pokemonDAO.pokemonJaExiste(nome)) {
            throw new Exception("Já existe um Pokémon com esse nome!");
        }
        // Exemplo de chamada do Model (já validado):
        try {
            Pokemon pokemon = new Pokemon(nome.toLowerCase(), tipoPrimario.toLowerCase(), segundoTipo, nivel, hpMaximo);
            pokemonDAO.inserir(pokemon);
        } catch (SQLException e) {
            throw new Exception("Erro ao cadastrar Pokémon no banco de dados: " + e.getMessage());
        }
    }

    public void atualizarPokemon(int id, String nome, String tipoPrimario, String tipoSecundario, int nivel, int hpMaximo) throws Exception {
        // --- EXERCÍCIO: Adicionar validações aqui! ---
        // Exemplo de chamada do Model (já validado):
        Pokemon existente = pokemonDAO.buscarPorId(id);
        String segundoTipo = null;
        if (existente == null) {
            throw new Exception("Pokémon com ID " + id + " não encontrado para atualização.");
        }

        if (nome == null || nome.trim().isEmpty()) {
            throw new Exception("O nome do Pokémon é obrigatório.");
        }

        if (!revisaoDeTexto(nome) && !nome.trim().toLowerCase().equalsIgnoreCase("porygon2")) {
            throw new Exception("O nome do pokémon não deve conter números!");
        }

        if (tipoPrimario == null || tipoPrimario.trim().isEmpty()) {
            throw new Exception("O tipo primário do Pokémon é obrigatório.");
        }

        if (!revisaoDeTexto(tipoPrimario)) {
            throw new Exception("O Tipo do Pokémon não deve conter números!");
        }

        if (tipoSecundario != null) {
            if (tipoPrimario.toLowerCase().equals(tipoSecundario.toLowerCase()) || !revisaoDeTexto(tipoSecundario)) {
                throw new Exception("O Tipo Secundário não pode ser igual ao Tipo Primário e também não pode conter números.");
            }
            segundoTipo = tipoSecundario.toLowerCase();
        }

        if (nivel < 0 || nivel > 100 || String.valueOf(nivel).trim().isEmpty()) {
            throw new Exception("O nível não é válido.");
        }

        if (hpMaximo < 0 || String.valueOf(hpMaximo).trim().isEmpty()) {
            throw new Exception("O HP máximo não é válido.");
        }

        List<Pokemon> verificacao = pokemonDAO.buscarPorNome(nome);

        for (Pokemon poke : verificacao) {
            if (poke.getId() != id) {
                throw new Exception("O Pokémon atualizado não deve conter o mesmo nome de outro pokémon já cadastrado!");
            }
        }

        // Atualização
        Pokemon pokemonAtualizado = new Pokemon(id, nome.toLowerCase(), tipoPrimario.toLowerCase(), segundoTipo, nivel, hpMaximo);
        try {
            pokemonDAO.atualizar(pokemonAtualizado);
        } catch (SQLException e) {
            throw new Exception("Erro ao atualizar Pokémon no banco de dados: " + e.getMessage());
        }
    }

    public boolean revisaoDeTexto(String texto) {
        List<Character> letrasTexto = new ArrayList<>();

        for (int i = 0; i < texto.length(); i++) {
            letrasTexto.add(texto.charAt(i));
        }

        for (Character letra : letrasTexto) {
            if (!Character.isLetter(letra)) {
                return false;
            }
        }
        return true;
    }

    public List<Pokemon> listarTodosPokemons() {
        return pokemonDAO.listarTodos();
    }

    public Pokemon buscarPokemonPorId(int id) {
        return pokemonDAO.buscarPorId(id);
    }

    public void removerPokemon(int id) throws Exception {
        // --- EXERCÍCIO: Adicionar validações aqui! ---
        Pokemon existente = pokemonDAO.buscarPorId(id);
        if (existente == null) {
            throw new Exception("Pokémon com ID " + id + " não encontrado para remoção.");
        }

        try {
            pokemonDAO.remover(id);
        } catch (SQLException e) {
            throw new Exception("Erro ao remover Pokémon: " + e.getMessage());
        }
    }

    public List<Pokemon> buscarPokemonPorNome(String nome) {
        // --- EXERCÍCIO: Adicionar validações aqui! ---
        return pokemonDAO.buscarPorNome(nome);
    }
}