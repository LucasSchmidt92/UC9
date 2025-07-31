package View;

import Controller.EmprestimoController;
import Model.Aluno;
import Model.Emprestimo;
import Model.Livro;

import javax.swing.*;
import java.awt.*;

public class EmprestimoCadastro extends JInternalFrame{
    private EmprestimoController controller;
    private JTextField txtId, txtNome, txtContato, txtIsbn, txtDataEmprestimo, txtDevolucao;
    private JButton btnSalvar, btnBuscar;
    private Integer emprestimoIdParaEdicao;

    public EmprestimoCadastro(EmprestimoController controller, Integer emprestimoId) {
        super("Cadastro de Emprestimo", true, true, true, true); // Título, redimensionável, fechável, maximizável, iconificável
        this.controller = controller;
        this.emprestimoIdParaEdicao = emprestimoId;

        setSize(500, 350); // Tamanho da janela interna
        setLayout(new GridBagLayout()); // Layout para organizar os componentes
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 40, 5, 20); // Espaçamento entre os componentes
        gbc.fill = GridBagConstraints.HORIZONTAL; // Preenche o espaço horizontalmente

        int row = 0; // Contador de linhas para o layout

        // Campo ID
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = row;
        txtId = new JTextField(10);
        txtId.setEditable(false); // ID não pode ser editado diretamente, apenas buscado
        add(txtId, gbc);

        // Botão Buscar
        gbc.gridx = 2;
        gbc.gridy = row;
        btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> buscarEmprestimo()); // Adiciona ação ao botão Buscar
        add(btnBuscar, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Nome do Aluno:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.gridwidth = 2; // Ocupa 2 colunas
        txtNome = new JTextField(20);
        add(txtNome, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Contato do Aluno:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        txtContato = new JTextField(20);
        add(txtContato, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("ISBN do Livro:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        txtIsbn = new JTextField(20);
        add(txtIsbn, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Data de Empréstimo:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        txtDataEmprestimo = new JTextField(20);
        add(txtDataEmprestimo, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Data de Devolução:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        txtDevolucao = new JTextField(20);
        add(txtDevolucao, gbc);
        row++;

        // Botão Salvar
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 3; // Ocupa 3 colunas
        btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> salvarEmprestimo()); // Adiciona ação ao botão Salvar
        add(btnSalvar, gbc);

        // Se um ID foi passado, carrega o emprestimo para edição
        if (emprestimoIdParaEdicao != null) {
            carregarEmprestimoParaEdicao(emprestimoIdParaEdicao, txtIsbn.getText(), txtNome.getText(), txtContato.getText());
            txtId.setText(String.valueOf(emprestimoIdParaEdicao));
            btnBuscar.setEnabled(false); // Desabilita o botão buscar se já está editando
        }
    }

    private void buscarEmprestimo() {
        // Dados do Emprestimo
        int idStr = JOptionPane.showConfirmDialog(this, "ID Empréstimo", "Buscar Empréstimo", JOptionPane.OK_CANCEL_OPTION);
        Livro livro = controller.getInfosLivro(idStr);
        Aluno aluno = controller.getInfosAluno(idStr);
        if (!String.valueOf(idStr).trim().isEmpty()) {
            try {
                carregarEmprestimoParaEdicao(idStr, livro.getIsbn(), aluno.getNome(), aluno.getContato());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID inválido. Por favor, digite um número.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void carregarEmprestimoParaEdicao(int id, String isbn, String nomeAluno, String contato) {
        try {
            Emprestimo emprestimo = controller.getEmprestimoById(id);
            if (emprestimo != null) {
                txtId.setText(String.valueOf(emprestimo.getId_emprestimo()));
                txtNome.setText(nomeAluno);
                txtContato.setText(contato);
                txtIsbn.setText(isbn);
                txtDataEmprestimo.setText(emprestimo.getData_emprestimo());
                txtDevolucao.setText(emprestimo.getData_devolucao());
                emprestimoIdParaEdicao = emprestimo.getId_emprestimo(); // Define o ID para indicar que é uma edição
            } else {
                JOptionPane.showMessageDialog(this, "Emprestimo com ID " + id + " não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                limparCampos(); // Limpa os campos se não encontrar
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar emprestimo: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void salvarEmprestimo() {
        try {
            String data_emprestimo = txtDataEmprestimo.getText().trim();
            String devolucao = txtDevolucao.getText().trim();
            int fkAluno = controller.getFkAluno(txtNome.getText().trim(), txtContato.getText().trim());
            System.out.println(fkAluno);
            int fkLivro = controller.getFkLivro(txtIsbn.getText().trim());
            System.out.println(fkLivro);

            if (emprestimoIdParaEdicao == null) { // Se emprestimoIdParaEdicao é null, é um novo cadastro
                controller.cadastrarEmprestimo(fkLivro, fkAluno, data_emprestimo, devolucao);
                JOptionPane.showMessageDialog(this, "Emprestimo cadastrado com sucesso!");
            } else { // Caso contrário, é uma atualização
                controller.atualizarEmprestimo(emprestimoIdParaEdicao, fkLivro, fkAluno, data_emprestimo, devolucao);
                JOptionPane.showMessageDialog(this, "Emprestimo atualizado com sucesso!");
            }
            this.dispose(); // Fecha a janela após a operação bem-sucedida
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar emprestimo: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void limparCampos() {
        txtId.setText("");
        txtNome.setText("");
        txtIsbn.setText("");
        txtDataEmprestimo.setText("");
        txtDevolucao.setText("");
        txtContato.setText("");
        emprestimoIdParaEdicao = null; // Reseta para modo de novo cadastro
        btnBuscar.setEnabled(true); // Habilita o botão buscar novamente
    }
}
