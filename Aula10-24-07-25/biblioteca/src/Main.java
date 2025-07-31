
import Controller.AlunoController;
import Controller.EmprestimoController;
import Controller.LivroController;
import Model.DAO.EmprestimoDAO;
import Model.DAO.LivroDAO;
import Model.Aluno;
import Model.DAO.AlunoDAO;
import Model.Emprestimo;
import Model.Livro;
import View.*;

import javax.swing.*;

public class Main extends JFrame {
    private JDesktopPane desktopPane;
    private AlunoController alunoController;// Nova instância do AlunoController
    private LivroController livroController;
    private EmprestimoController emprestimoController;

    public Main() {
        super("Sistema de Gerenciamento de Biblioteca");
        this.alunoController = new AlunoController();// Instancia o controller de alunos
        this.livroController = new LivroController();
        this.emprestimoController = new EmprestimoController();

        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        desktopPane = new JDesktopPane();
        setContentPane(desktopPane);

        createMenuBar();
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // --- Menu Alunos (usará alunoController) ---
        JMenu menuAlunos = new JMenu("Alunos");
        JMenuItem itemCadastrarAluno = new JMenuItem("Cadastrar Aluno");
        JMenuItem itemListarAlunos = new JMenuItem("Listar Alunos");

        JMenu menuLivros = new JMenu("Livros");
        JMenuItem itemCadastrarLivro = new JMenuItem("Cadastrar Livro");
        JMenuItem itemListarLivros = new JMenuItem("Listar Livros");

        JMenu menuEmprestimos = new JMenu("Empréstimos");
        JMenuItem itemCadastrarEmprestimo = new JMenuItem("Cadastrar Empréstimo");
        JMenuItem itemListarEmprestimos = new JMenuItem("Listar Empréstimos");

        itemCadastrarAluno.addActionListener(e -> openAlunoForm(null));
        itemListarAlunos.addActionListener(e -> openListaAlunosForm());

        itemCadastrarLivro.addActionListener(e -> openLivroForm(null));
        itemListarLivros.addActionListener(e -> openListaLivrosForm());

        itemCadastrarEmprestimo.addActionListener(e -> openEmprestimoForm(null));
        itemListarEmprestimos.addActionListener(e -> openListaEmprestimosForm());

        menuAlunos.add(itemCadastrarAluno);
        menuAlunos.add(itemListarAlunos);

        menuLivros.add(itemCadastrarLivro);
        menuLivros.add(itemListarLivros);

        menuEmprestimos.add(itemCadastrarEmprestimo);
        menuEmprestimos.add(itemListarEmprestimos);

        menuBar.add(menuAlunos);
        menuBar.add(menuLivros);
        menuBar.add(menuEmprestimos);

        // --- Menu Sair (Existente) ---
        JMenu menuSair = new JMenu("Sair");
        JMenuItem itemSair = new JMenuItem("Sair do Sistema");
        itemSair.addActionListener(e -> System.exit(0));

        menuSair.add(itemSair);
        menuBar.add(menuSair);

        setJMenuBar(menuBar);
    }

    private void openAlunoForm(Integer idAluno) {
        AlunoCadastro alunoForm = new AlunoCadastro(alunoController, idAluno); // Passa o alunoController
        desktopPane.add(alunoForm);
        alunoForm.setVisible(true);
        alunoForm.toFront();
    }

    private void openListaAlunosForm() {
        ListaAlunos listaAlunos = new ListaAlunos(alunoController); // Passa o alunoController
        desktopPane.add(listaAlunos);
        listaAlunos.setVisible(true);
        listaAlunos.toFront();
    }

    private void openLivroForm(Integer idLivro) {
        LivroCadastro livroForm = new LivroCadastro(livroController, idLivro); // Passa o livroController
        desktopPane.add(livroForm);
        livroForm.setVisible(true);
        livroForm.toFront();
    }

    private void openListaLivrosForm() {
        ListaLivros listaLivros = new ListaLivros(livroController); // Passa o alunoController
        desktopPane.add(listaLivros);
        listaLivros.setVisible(true);
        listaLivros.toFront();
    }

    private void openEmprestimoForm(Integer idEmprestimo) {
        EmprestimoCadastro emprestimoForm = new EmprestimoCadastro(emprestimoController, idEmprestimo); // Passa o emprestimoController
        desktopPane.add(emprestimoForm);
        emprestimoForm.setVisible(true);
        emprestimoForm.toFront();
    }

    private void openListaEmprestimosForm() {
        ListaEmprestimos listaEmprestimos = new ListaEmprestimos(emprestimoController); // Passa o alunoController
        desktopPane.add(listaEmprestimos);
        listaEmprestimos.setVisible(true);
        listaEmprestimos.toFront();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Main().setVisible(true);
        });
    }
}