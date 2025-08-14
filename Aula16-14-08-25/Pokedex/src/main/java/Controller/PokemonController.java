package Controller;

import Util.HibernateUtil;
import jakarta.transaction.*;
import Model.Pokemon;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PokemonController {
    public void cadastrarPokemon(String nome, String tipoPrimario, String tipoSecundario, int nivel, int hpMaximo) throws Exception {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
    // Validações de negócio teste (nome, tipo, etc.)
            String segundoTipo = null;
            if (nome == null || nome.trim().isEmpty()) {
                throw new Exception("O nome do Pokémon é obrigatório.");
            }

            if(!revisaoDeTexto(nome) && !nome.trim().toLowerCase().equalsIgnoreCase("porygon2")){
                throw new Exception("O nome do pokémon não deve conter números!");
            }

            if (tipoPrimario == null || tipoPrimario.trim().isEmpty()) {
                throw new Exception("O tipo primário do Pokémon é obrigatório.");
            }

            if (!revisaoDeTexto(tipoPrimario)){
                throw new Exception("O Tipo do Pokémon não deve conter números!");
            }

            if (tipoSecundario != null){
                if (tipoPrimario.toLowerCase().equals(tipoSecundario.toLowerCase()) || !revisaoDeTexto(tipoSecundario)) {
                    throw new Exception("O Tipo Secundário não pode ser igual ao Tipo Primário e também não pode conter números.");
                }
            }

            if (nivel < 0 || nivel > 100 || String.valueOf(nivel).trim().isEmpty()) {
                throw new Exception("O nível não é válido.");
            }

            if (hpMaximo < 0 || String.valueOf(hpMaximo).trim().isEmpty()) {
                throw new Exception("O HP máximo não é válido.");
            }

//            if (pokemonDAO.pokemonJaExiste(nome)) {
//                throw new Exception("Já existe um Pokémon com esse nome!");
//            }

            Pokemon pokemon = new Pokemon();
            pokemon.setNome(nome);
            pokemon.setTipoPrimario(tipoPrimario);
            pokemon.setTipoSecundario(tipoSecundario);
            pokemon.setNivel(nivel);
            pokemon.setHpMaximo(hpMaximo);

            session.persist(pokemon); // Salva o objeto no banco
            transaction.commit();

        }
    }

    public boolean revisaoDeTexto(String texto){
        List<Character> letrasTexto = new ArrayList<>();

        for(int i = 0; i<texto.length(); i++){
            letrasTexto.add(texto.charAt(i));
        }

        for (Character letra: letrasTexto){
            if (!Character.isLetter(letra)){
                return false;
            }
        }
        return true;
    }
}