package gui;

import controle.ControleUsuario;
import controle.Pessoa;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class DialogoCadastroAdmin extends JDialog {
    private ControleUsuario controleUsuario;
    private JTextField txtNome;
    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private JPasswordField txtConfirmarSenha;
    
    public DialogoCadastroAdmin(JFrame parent, ControleUsuario controleUsuario) {
        super(parent, "Cadastro de Administrador", true);
        this.controleUsuario = controleUsuario;
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        setSize(400, 350);
        setLocationRelativeTo(getParent());
        setResizable(false);
        
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel lblTitulo = new JLabel("Primeiro Acesso - Cadastro de Administrador", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 14));
        painelPrincipal.add(lblTitulo, BorderLayout.NORTH);
        
        JPanel painelForm = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.3;
        painelForm.add(new JLabel("Nome:"), gbc);
        
        txtNome = new JTextField();
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0.7;
        painelForm.add(txtNome, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.3;
        painelForm.add(new JLabel("Email:"), gbc);
        
        txtEmail = new JTextField();
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 0.7;
        painelForm.add(txtEmail, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.3;
        painelForm.add(new JLabel("Senha:"), gbc);
        
        txtSenha = new JPasswordField();
        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 0.7;
        painelForm.add(txtSenha, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0.3;
        painelForm.add(new JLabel("Confirmar Senha:"), gbc);
        
        txtConfirmarSenha = new JPasswordField();
        gbc.gridx = 1; gbc.gridy = 3; gbc.weightx = 0.7;
        painelForm.add(txtConfirmarSenha, gbc);
        
        painelPrincipal.add(painelForm, BorderLayout.CENTER);
        
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        JButton btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.setPreferredSize(new Dimension(100, 30));
        btnCadastrar.setBackground(new Color(46, 125, 50));
        btnCadastrar.setForeground(new Color(51, 51, 51));
        btnCadastrar.setFocusPainted(false);
        btnCadastrar.setFont(new Font("Arial", Font.BOLD, 12));
        btnCadastrar.addActionListener(e -> cadastrar());
        painelBotoes.add(btnCadastrar);
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setPreferredSize(new Dimension(100, 30));
        btnCancelar.setBackground(new Color(158, 158, 158));
        btnCancelar.setForeground(new Color(51, 51, 51));
        btnCancelar.setFocusPainted(false);
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 12));
        btnCancelar.addActionListener(e -> dispose());
        painelBotoes.add(btnCancelar);
        
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);
        
        add(painelPrincipal);
    }
    
    private void cadastrar() {
        String nome = txtNome.getText().trim();
        String email = txtEmail.getText().trim();
        String senha = new String(txtSenha.getPassword());
        String confirmarSenha = new String(txtConfirmarSenha.getPassword());
        
        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Todos os campos são obrigatórios.", 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!senha.equals(confirmarSenha)) {
            JOptionPane.showMessageDialog(this, 
                "As senhas não coincidem.", 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (senha.length() < 4) {
            JOptionPane.showMessageDialog(this, 
                "A senha deve ter pelo menos 4 caracteres.", 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        boolean sucesso = controleUsuario.criarUsuario(nome, email, senha, "Admin");
        
        if (sucesso) {
            JOptionPane.showMessageDialog(this, 
                "Administrador cadastrado com sucesso!\nAgora você pode fazer login.", 
                "Sucesso", 
                JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Erro ao cadastrar: " + controleUsuario.getUltimaMensagemErro(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}