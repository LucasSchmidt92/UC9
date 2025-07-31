package View;

import Controller.LivroController;
import Model.Livro;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ListaLivros extends JInternalFrame {

    private LivroController controller;
    private JTable tabelaLivros;
    private DefaultTableModel tableModel;
    private JButton btnAtualizar, btnRemover, btnBuscar;
    private JTextField txtBuscaTitulo;

    public ListaLivros(LivroController controller) { // Altere o tipo do parâmetro
        super("Lista de Livros", true, true, true, true);
        this.controller = controller; // Atribui o novo controller

        setSize(850, 500);
        setLayout(new BorderLayout());

        String[] colunas = {"ID", "Título", "Autor", "Genero", "ISBN"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaLivros = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabelaLivros);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panelAcoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtBuscaTitulo = new JTextField(20);
        btnBuscar = new JButton("Buscar por Titulo");
        btnAtualizar = new JButton("Atualizar Tabela");
        btnRemover = new JButton("Remover Selecionado");

        panelAcoes.add(new JLabel("Titulo:"));
        panelAcoes.add(txtBuscaTitulo);
        panelAcoes.add(btnBuscar);
        panelAcoes.add(btnAtualizar);
        panelAcoes.add(btnRemover);
        add(panelAcoes, BorderLayout.NORTH);

        btnAtualizar.addActionListener(e -> carregarLivrosNaTabela());
        btnRemover.addActionListener(e -> removerLivroSelecionado());
        btnBuscar.addActionListener(e -> buscarLivrosPorTitulo());

    }

    private void carregarLivrosNaTabela() {
        tableModel.setRowCount(0); // Limpa as linhas existentes na tabela
        java.util.List<Livro> livros = controller.listarLivros(); // Busca todos os livros
        for (Livro livro : livros) {
            // Adiciona cada livro como uma nova linha na tabela
            tableModel.addRow(new Object[]{
                    livro.getId(),
                    livro.getTitulo(),
                    livro.getAutor(),
                    livro.getGenero(),
                    livro.getIsbn()
            });
        }
    }

    private void removerLivroSelecionado() {
        int selectedRow = tabelaLivros.getSelectedRow(); // Obtém a linha selecionada
        if (selectedRow >= 0) { // Verifica se alguma linha foi selecionada
            int idLivro = (int) tableModel.getValueAt(selectedRow, 0); // Obtém o ID da célula da tabela

            // Confirmação antes de remover
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Tem certeza que deseja remover o livro ID: " + idLivro + "?",
                    "Confirmar Remoção", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    controller.removerLivro(idLivro); // Chama o controller para remover
                    JOptionPane.showMessageDialog(this, "Livro removido com sucesso!");
                    carregarLivrosNaTabela(); // Atualiza a tabela após a remoção
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao remover livro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um livro para remover.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void buscarLivrosPorTitulo() {
        String tituloBusca = txtBuscaTitulo.getText().trim(); // Obtém o texto do campo de busca
        tableModel.setRowCount(0); // Limpa a tabela

        List<Livro> livros;
        if (tituloBusca.isEmpty()) {
            // Se o campo de busca estiver vazio, lista todos
            livros = controller.listarLivros();
        } else {
            // Caso contrário, busca por nome
            livros = controller.getLivrosByTitulo(tituloBusca);
        }

        if (livros.isEmpty() && !tituloBusca.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum livro encontrado com o título: '" + tituloBusca + "'", "Busca", JOptionPane.INFORMATION_MESSAGE);
        }

        for (Livro livro : livros) {
            tableModel.addRow(new Object[]{
                    livro.getId(),
                    livro.getTitulo(),
                    livro.getAutor(),
                    livro.getGenero(),
                    livro.getIsbn()
            });
        }
    }
}
