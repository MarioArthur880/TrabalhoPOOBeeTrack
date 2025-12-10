package gui;

import controle.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

class DialogoCadastroUsuario extends JDialog {
    private ControleUsuario controle;
    private JTextField txtNome, txtEmail;
    private JPasswordField txtSenha;
    private JComboBox<String> cmbTipo;

    public DialogoCadastroUsuario(JDialog parent, ControleUsuario controle) {
        super(parent, "Novo Usuário", true);
        this.controle = controle;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setSize(400, 300);
        setLocationRelativeTo(getParent());

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Nome:"), gbc);
        txtNome = new JTextField(20);
        gbc.gridx = 1;
        panel.add(txtNome, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Email:"), gbc);
        txtEmail = new JTextField(20);
        gbc.gridx = 1;
        panel.add(txtEmail, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Senha:"), gbc);
        txtSenha = new JPasswordField(20);
        gbc.gridx = 1;
        panel.add(txtSenha, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Tipo:"), gbc);
        cmbTipo = new JComboBox<>(new String[] { "Admin", "Apicultor" });
        gbc.gridx = 1;
        panel.add(cmbTipo, gbc);

        add(panel, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout());

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setPreferredSize(new Dimension(100, 30));
        btnSalvar.setBackground(new Color(46, 125, 50)); // Mesmo verde do login
        btnSalvar.setForeground(Color.WHITE);

        btnSalvar.setFocusPainted(false);
        btnSalvar.addActionListener(e -> salvar());
        painelBotoes.add(btnSalvar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setPreferredSize(new Dimension(100, 30));
        btnCancelar.setBackground(new Color(183, 28, 28)); // Vermelho escuro
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFocusPainted(false);
        btnCancelar.addActionListener(e -> dispose());
        painelBotoes.add(btnCancelar);

        add(painelBotoes, BorderLayout.SOUTH);
    }

    private void salvar() {
        String nome = txtNome.getText().trim();
        String email = txtEmail.getText().trim();
        String senha = new String(txtSenha.getPassword());
        String tipo = (String) cmbTipo.getSelectedItem();

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
            return;
        }

        if (controle.criarUsuario(nome, email, senha, tipo)) {
            JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Erro: " + controle.getUltimaMensagemErro());
        }
    }
}