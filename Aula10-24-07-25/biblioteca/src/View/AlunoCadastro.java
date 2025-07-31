package View;

import Controller.AlunoController;
import Model.Aluno;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate; // caso queria usar data
import java.time.format.DateTimeParseException; // caso queria usar datetime

public class AlunoCadastro extends JInternalFrame {
    private AlunoController controller;
    private JTextField txtId, txtNome, textIdade, txtContato;
    private JButton btnSalvar, btnBuscar;
    private Integer alunoIdParaEdicao;

    public AlunoCadastro(AlunoController controller, Integer alunoId) {
        super("Cadastro de Aluno", true, true, true, true); // Título, redimensionável, fechável, maximizável, iconificável
        this.controller = controller;
        this.alunoIdParaEdicao = alunoId;

        setSize(500, 350); // Tamanho da janela interna
        setLayout(new GridBagLayout()); // Layout para organizar os componentes
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 40, 5, 40); // Espaçamento entre os componentes
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
        btnBuscar.addActionListener(e -> buscarAluno()); // Adiciona ação ao botão Buscar
        add(btnBuscar, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.gridwidth = 2; // Ocupa 2 colunas
        txtNome = new JTextField(20);
        add(txtNome, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Idade:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        textIdade = new JTextField(20);
        add(textIdade, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Contato:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        txtContato = new JTextField(20);
        add(txtContato, gbc);
        row++;

        // Botão Salvar
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 3; // Ocupa 3 colunas
        btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> salvarAluno()); // Adiciona ação ao botão Salvar
        add(btnSalvar, gbc);

        // Se um ID foi passado, carrega o aluno para edição
        if (alunoIdParaEdicao != null) {
            carregarAlunoParaEdicao(alunoIdParaEdicao);
            txtId.setText(String.valueOf(alunoIdParaEdicao));
            btnBuscar.setEnabled(false); // Desabilita o botão buscar se já está editando
        }
    }

    private void buscarAluno() {
        String idStr = JOptionPane.showInputDialog(this, "Digite o ID do aluno para buscar:");
        if (idStr != null && !idStr.trim().isEmpty()) {
            try {
                int id = Integer.parseInt(idStr);
                carregarAlunoParaEdicao(id);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID inválido. Por favor, digite um número.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void carregarAlunoParaEdicao(int id) {
        try {
            Aluno aluno = controller.getAlunoById(id);
            if (aluno != null) {
                txtId.setText(String.valueOf(aluno.getId()));
                txtNome.setText(aluno.getNome());
                textIdade.setText(String.valueOf(aluno.getIdade()));
                txtContato.setText(aluno.getContato());
                alunoIdParaEdicao = aluno.getId(); // Define o ID para indicar que é uma edição
            } else {
                JOptionPane.showMessageDialog(this, "Aluno com ID " + id + " não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                limparCampos(); // Limpa os campos se não encontrar
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar aluno: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void salvarAluno() {
        try {
            String nome = txtNome.getText().trim();
            int idade = Integer.parseInt(textIdade.getText().trim());
            String contato = txtContato.getText().trim();

            if (alunoIdParaEdicao == null) { // Se alunoIdParaEdicao é null, é um novo cadastro
                controller.cadastrarAluno(nome, idade, contato);
                JOptionPane.showMessageDialog(this, "Aluno cadastrado com sucesso!");
            } else { // Caso contrário, é uma atualização
                controller.atualizarAluno(alunoIdParaEdicao, nome, idade, contato);
                JOptionPane.showMessageDialog(this, "Aluno atualizado com sucesso!");
            }
            this.dispose(); // Fecha a janela após a operação bem-sucedida
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar aluno: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void limparCampos() {
        txtId.setText("");
        txtNome.setText("");
        textIdade.setText("");
        txtContato.setText("");
        alunoIdParaEdicao = null; // Reseta para modo de novo cadastro
        btnBuscar.setEnabled(true); // Habilita o botão buscar novamente
    }
}