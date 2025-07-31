package View;

import Controller.AlunoController;
import Model.Aluno;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ListaAlunos extends JInternalFrame {

    private AlunoController controller;
    private JTable tabelaAlunos;
    private DefaultTableModel tableModel;
    private JButton btnAtualizar, btnRemover, btnBuscar;
    private JTextField txtBuscaNome;

    public ListaAlunos(AlunoController controller) { // Altere o tipo do parâmetro
        super("Lista de Alunos", true, true, true, true);
        this.controller = controller; // Atribui o novo controller

        setSize(850, 500);
        setLayout(new BorderLayout());

        String[] colunas = {"ID", "Nome", "Idade", "Contato"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaAlunos = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabelaAlunos);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panelAcoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtBuscaNome = new JTextField(20);
        btnBuscar = new JButton("Buscar por Nome");
        btnAtualizar = new JButton("Atualizar Tabela");
        btnRemover = new JButton("Remover Selecionado");

        panelAcoes.add(new JLabel("Nome:"));
        panelAcoes.add(txtBuscaNome);
        panelAcoes.add(btnBuscar);
        panelAcoes.add(btnAtualizar);
        panelAcoes.add(btnRemover);
        add(panelAcoes, BorderLayout.NORTH);

        btnAtualizar.addActionListener(e -> carregarAlunosNaTabela());
        btnRemover.addActionListener(e -> removerAlunoSelecionado());
        btnBuscar.addActionListener(e -> buscarAlunosPorNome());

    }

    private void carregarAlunosNaTabela() {
        tableModel.setRowCount(0); // Limpa as linhas existentes na tabela
        java.util.List<Aluno> alunos = controller.listarAlunos(); // Busca todos os alunos
        for (Aluno aluno : alunos) {
            // Adiciona cada aluno como uma nova linha na tabela
            tableModel.addRow(new Object[]{
                    aluno.getId(),
                    aluno.getNome(),
                    aluno.getIdade(),
                    aluno.getContato()
            });
        }
    }

    private void removerAlunoSelecionado() {
        int selectedRow = tabelaAlunos.getSelectedRow(); // Obtém a linha selecionada
        if (selectedRow >= 0) { // Verifica se alguma linha foi selecionada
            int idAluno = (int) tableModel.getValueAt(selectedRow, 0); // Obtém o ID da célula da tabela

            // Confirmação antes de remover
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Tem certeza que deseja remover o aluno ID: " + idAluno + "?",
                    "Confirmar Remoção", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    controller.removerAluno(idAluno); // Chama o controller para remover
                    JOptionPane.showMessageDialog(this, "Aluno removido com sucesso!");
                    carregarAlunosNaTabela(); // Atualiza a tabela após a remoção
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao remover aluno: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um aluno para remover.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void buscarAlunosPorNome() {
        String nomeBusca = txtBuscaNome.getText().trim(); // Obtém o texto do campo de busca
        tableModel.setRowCount(0); // Limpa a tabela

        List<Aluno> alunos;
        if (nomeBusca.isEmpty()) {
            // Se o campo de busca estiver vazio, lista todos
            alunos = controller.listarAlunos();
        } else {
            // Caso contrário, busca por nome
            alunos = controller.getAlunosByName(nomeBusca);
        }

        if (alunos.isEmpty() && !nomeBusca.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum aluno encontrado com o nome: '" + nomeBusca + "'", "Busca", JOptionPane.INFORMATION_MESSAGE);
        }

        for (Aluno aluno : alunos) {
            tableModel.addRow(new Object[]{
                    aluno.getId(),
                    aluno.getNome(),
                    aluno.getIdade(),
                    aluno.getContato()
            });
        }
    }
}
