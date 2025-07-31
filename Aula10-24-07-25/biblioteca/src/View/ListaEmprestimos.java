package View;

import Controller.EmprestimoController;
import Model.Emprestimo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ListaEmprestimos extends JInternalFrame {

    private EmprestimoController controller;
    private JTable tabelaEmprestimos;
    private DefaultTableModel tableModel;
    private JButton btnAtualizar, btnRemover, btnBuscarPorAluno, btnBuscarPorLivro;
    private JTextField txtBuscaIdAluno, txtBuscaIdLivro;

    public ListaEmprestimos(EmprestimoController controller) { // Altere o tipo do parâmetro
        super("Lista de Emprestimos", true, true, true, true);
        this.controller = controller; // Atribui o novo controller

        setSize(850, 500);
        setLayout(new BorderLayout());

        String[] colunas = {"ID", "ID do Livro", "ID do Aluno", "Data do Empréstimo", "Data de Devolução"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaEmprestimos = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabelaEmprestimos);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panelAcoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtBuscaIdAluno = new JTextField(5);
        txtBuscaIdLivro = new JTextField(5);
        btnBuscarPorAluno = new JButton("Buscar por Id Aluno");
        btnBuscarPorLivro = new JButton("Buscar por Id Livro");
        btnAtualizar = new JButton("Atualizar Tabela");
        btnRemover = new JButton("Remover Selecionado");

        panelAcoes.add(new JLabel("Id Aluno:"));
        panelAcoes.add(txtBuscaIdAluno);
        panelAcoes.add(btnBuscarPorAluno);
        panelAcoes.add(new JLabel("Id Livro:"));
        panelAcoes.add(txtBuscaIdLivro);
        panelAcoes.add(btnBuscarPorLivro);
        panelAcoes.add(btnAtualizar);
        panelAcoes.add(btnRemover);
        add(panelAcoes, BorderLayout.NORTH);

        btnAtualizar.addActionListener(e -> carregarEmprestimosNaTabela());
        btnRemover.addActionListener(e -> removerEmprestimoSelecionado());
        btnBuscarPorAluno.addActionListener(e -> buscarEmprestimosPorIDAluno());
        btnBuscarPorLivro.addActionListener(e -> buscarEmprestimosPorIDLivro());

    }

    private void carregarEmprestimosNaTabela() {
        tableModel.setRowCount(0); // Limpa as linhas existentes na tabela
        java.util.List<Emprestimo> emprestimos = controller.listarEmprestimos(); // Busca todos os emprestimos
        for (Emprestimo emprestimo : emprestimos) {
            // Adiciona cada emprestimo como uma nova linha na tabela
            tableModel.addRow(new Object[]{
                    emprestimo.getId_emprestimo(),
                    emprestimo.getFk_livro(),
                    emprestimo.getFk_aluno(),
                    emprestimo.getData_emprestimo(),
                    emprestimo.getData_devolucao()
            });
        }
    }

    private void removerEmprestimoSelecionado() {
        int selectedRow = tabelaEmprestimos.getSelectedRow(); // Obtém a linha selecionada
        if (selectedRow >= 0) { // Verifica se alguma linha foi selecionada
            int idEmprestimo = (int) tableModel.getValueAt(selectedRow, 0); // Obtém o ID da célula da tabela

            // Confirmação antes de remover
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Tem certeza que deseja remover o emprestimo ID: " + idEmprestimo + "?",
                    "Confirmar Remoção", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    controller.removerEmprestimo(idEmprestimo); // Chama o controller para remover
                    JOptionPane.showMessageDialog(this, "Emprestimo removido com sucesso!");
                    carregarEmprestimosNaTabela(); // Atualiza a tabela após a remoção
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao remover emprestimo: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um emprestimo para remover.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void buscarEmprestimosPorIDAluno() {
        String idBusca = txtBuscaIdAluno.getText().trim(); // Obtém o texto do campo de busca
        tableModel.setRowCount(0); // Limpa a tabela

        List<Emprestimo> emprestimos;
        if (idBusca.isEmpty()) {
            // Se o campo de busca estiver vazio, lista todos
            emprestimos = controller.listarEmprestimos();
        } else {
            // Caso contrário, busca por nome
            emprestimos = controller.getEmprestimosByAluno(Integer.parseInt(idBusca));
        }

        if (emprestimos.isEmpty() && !idBusca.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum empréstimo encontrado com o ID de Aluno: '" + idBusca + "'", "Busca", JOptionPane.INFORMATION_MESSAGE);
        }

        for (Emprestimo emprestimo : emprestimos) {
            tableModel.addRow(new Object[]{
                    emprestimo.getId_emprestimo(),
                    emprestimo.getFk_livro(),
                    emprestimo.getFk_aluno(),
                    emprestimo.getData_emprestimo(),
                    emprestimo.getData_devolucao()
            });
        }
    }

    private void buscarEmprestimosPorIDLivro() {
        String idBusca = txtBuscaIdLivro.getText().trim(); // Obtém o texto do campo de busca
        tableModel.setRowCount(0); // Limpa a tabela

        List<Emprestimo> emprestimos;
        if (idBusca.isEmpty()) {
            // Se o campo de busca estiver vazio, lista todos
            emprestimos = controller.listarEmprestimos();
        } else {
            // Caso contrário, busca por nome
            emprestimos = controller.getEmprestimosByLivro(Integer.parseInt(idBusca));
        }

        if (emprestimos.isEmpty() && !idBusca.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum emprestimo encontrado com o ID de Livro: '" + idBusca + "'", "Busca", JOptionPane.INFORMATION_MESSAGE);
        }

        for (Emprestimo emprestimo : emprestimos) {
            tableModel.addRow(new Object[]{
                    emprestimo.getId_emprestimo(),
                    emprestimo.getFk_livro(),
                    emprestimo.getFk_aluno(),
                    emprestimo.getData_emprestimo(),
                    emprestimo.getData_devolucao()
            });
        }
    }
}
