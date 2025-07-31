package View;

import Controller.LivroController;
import Model.Livro;

import javax.swing.*;
import java.awt.*;

public class LivroCadastro extends JInternalFrame{
    private LivroController controller;
    private JTextField txtId, txtTitulo, textAutor, textGenero, textIsbn;
    private JButton btnSalvar, btnBuscar;
    private Integer livroIdParaEdicao;

    public LivroCadastro(LivroController controller, Integer livroId) {
        super("Cadastro de Livro", true, true, true, true); // Título, redimensionável, fechável, maximizável, iconificável
        this.controller = controller;
        this.livroIdParaEdicao = livroId;

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
        btnBuscar.addActionListener(e -> buscarLivro()); // Adiciona ação ao botão Buscar
        add(btnBuscar, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Título:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.gridwidth = 2; // Ocupa 2 colunas
        txtTitulo = new JTextField(20);
        add(txtTitulo, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Autor:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        textAutor = new JTextField(20);
        add(textAutor, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Gênero:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        textGenero = new JTextField(20);
        add(textGenero, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("ISBN:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        textIsbn = new JTextField(20);
        add(textIsbn, gbc);
        row++;

        // Botão Salvar
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 3; // Ocupa 3 colunas
        btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> salvarLivro()); // Adiciona ação ao botão Salvar
        add(btnSalvar, gbc);

        // Se um ID foi passado, carrega o livro para edição
        if (livroIdParaEdicao != null) {
            carregarLivroParaEdicao(livroIdParaEdicao);
            txtId.setText(String.valueOf(livroIdParaEdicao));
            btnBuscar.setEnabled(false); // Desabilita o botão buscar se já está editando
        }
    }

    private void buscarLivro() {
        String idStr = JOptionPane.showInputDialog(this, "Digite o ID do livro para buscar:");
        if (idStr != null && !idStr.trim().isEmpty()) {
            try {
                int id = Integer.parseInt(idStr);
                carregarLivroParaEdicao(id);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID inválido. Por favor, digite um número.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void carregarLivroParaEdicao(int id) {
        try {
            Livro livro = controller.getLivroById(id);
            if (livro != null) {
                txtId.setText(String.valueOf(livro.getId()));
                txtTitulo.setText(livro.getTitulo());
                textAutor.setText(livro.getAutor());
                textGenero.setText(livro.getGenero());
                textIsbn.setText(livro.getIsbn());
                livroIdParaEdicao = livro.getId(); // Define o ID para indicar que é uma edição
            } else {
                JOptionPane.showMessageDialog(this, "Livro com ID " + id + " não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                limparCampos(); // Limpa os campos se não encontrar
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar livro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void salvarLivro() {
        try {
            String titulo = txtTitulo.getText().trim();
            String autor = textAutor.getText().trim();
            String genero = textGenero.getText().trim();
            String isbn = textIsbn.getText().trim();

            if (livroIdParaEdicao == null) { // Se livroIdParaEdicao é null, é um novo cadastro
                controller.cadastrarLivro(titulo, autor, genero, isbn);
                JOptionPane.showMessageDialog(this, "Livro cadastrado com sucesso!");
            } else { // Caso contrário, é uma atualização
                controller.atualizarLivro(livroIdParaEdicao, titulo, autor, genero, isbn);
                JOptionPane.showMessageDialog(this, "Livro atualizado com sucesso!");
            }
            this.dispose(); // Fecha a janela após a operação bem-sucedida
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar livro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void limparCampos() {
        txtId.setText("");
        txtTitulo.setText("");
        textAutor.setText("");
        textGenero.setText("");
        textIsbn.setText("");
        livroIdParaEdicao = null; // Reseta para modo de novo cadastro
        btnBuscar.setEnabled(true); // Habilita o botão buscar novamente
    }
}
