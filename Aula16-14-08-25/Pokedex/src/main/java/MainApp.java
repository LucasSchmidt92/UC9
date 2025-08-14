
import Controller.PokemonController;
import Util.HibernateUtil;

public class MainApp {
    private static PokemonController controller = new PokemonController();

    public static void cadastrar() throws Exception {
        controller.cadastrarPokemon("Treecko", "Grass", null, 15, 35);
    }

    public static void main(String[] args) throws Exception {
        cadastrar();
        System.out.println("teste trsyrr");
    }
}
