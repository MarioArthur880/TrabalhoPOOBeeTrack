package gui;

import controle.ControleUsuario;
import controle.Pessoa;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TelaLogin extends JFrame {
    private ControleUsuario controleUsuario;
    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private JButton btnLogin;
    private JButton btnCadastrar;
    
    public TelaLogin(ControleUsuario controleUsuario) {
        this.controleUsuario = controleUsuario;
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        setTitle("Sistema de Apiários - Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Panel principal com fundo
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BorderLayout(10, 10));
        painelPrincipal.setBackground(new Color(245, 245, 245));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Título
        JLabel lblTitulo = new JLabel("Sistema de Gerenciamento de Apiários", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setForeground(new Color(51, 51, 51));
        painelPrincipal.add(lblTitulo, BorderLayout.NORTH);
        
        // Panel central com formulário
        JPanel painelFormulario = new JPanel(new GridBagLayout());
        painelFormulario.setBackground(Color.WHITE);
        painelFormulario.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Email
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        painelFormulario.add(lblEmail, gbc);
        
        txtEmail = new JTextField();
        txtEmail.setFont(new Font("Arial", Font.PLAIN, 12));
        txtEmail.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.7;
        painelFormulario.add(txtEmail, gbc);
        
        // Senha
        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        painelFormulario.add(lblSenha, gbc);
        
        txtSenha = new JPasswordField();
        txtSenha.setFont(new Font("Arial", Font.PLAIN, 12));
        txtSenha.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.7;
        painelFormulario.add(txtSenha, gbc);
        
        painelPrincipal.add(painelFormulario, BorderLayout.CENTER);
        
        // Panel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        painelBotoes.setBackground(new Color(245, 245, 245));
        
       btnLogin = new JButton("Entrar");
btnLogin.setPreferredSize(new Dimension(120, 35));
btnLogin.setBackground(new Color(46, 125, 50));  // Verde escuro
btnLogin.setForeground(Color.WHITE);           // <-- E AQUI
btnLogin.setFocusPainted(false);
btnLogin.setFont(new Font("Arial", Font.BOLD, 12));
btnLogin.addActionListener(e -> realizarLogin());
painelBotoes.add(btnLogin);

// Botão de cadastro só aparece se não houver usuários
if (controleUsuario.listar().isEmpty()) {
   btnCadastrar = new JButton("Cadastrar Admin");
btnCadastrar.setPreferredSize(new Dimension(120, 35));
btnCadastrar.setBackground(new Color(21, 101, 192));  // Azul escuro
btnCadastrar.setForeground(Color.WHITE);             // <-- E AQUI
btnCadastrar.setFocusPainted(false);
btnCadastrar.setFont(new Font("Arial", Font.BOLD, 12));
btnCadastrar.addActionListener(e -> abrirTelaCadastro());
painelBotoes.add(btnCadastrar);

}

        
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);
        
        add(painelPrincipal);
        
        // Enter no campo senha faz login
        txtSenha.addActionListener(e -> realizarLogin());
    }
    
    private void realizarLogin() {
        String email = txtEmail.getText().trim();
        String senha = new String(txtSenha.getPassword());
        
        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, digite seu email.", 
                "Campo Obrigatório", 
                JOptionPane.WARNING_MESSAGE);
            txtEmail.requestFocus();
            return;
        }
        
        if (senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, digite sua senha.", 
                "Campo Obrigatório", 
                JOptionPane.WARNING_MESSAGE);
            txtSenha.requestFocus();
            return;
        }
        
        Pessoa usuario = controleUsuario.buscarPorEmail(email);
        
        if (usuario != null && usuario.validarSenha(senha)) {
            JOptionPane.showMessageDialog(this, 
                "Bem-vindo, " + usuario.getNome() + "!", 
                "Login Realizado", 
                JOptionPane.INFORMATION_MESSAGE);
            
            // Abre a tela principal
            SwingUtilities.invokeLater(() -> {
                TelaPrincipal telaPrincipal = new TelaPrincipal(usuario, controleUsuario);
                telaPrincipal.setVisible(true);
                dispose();
            });
        } else {
            JOptionPane.showMessageDialog(this, 
                "Email ou senha incorretos.", 
                "Erro de Autenticação", 
                JOptionPane.ERROR_MESSAGE);
            txtSenha.setText("");
            txtSenha.requestFocus();
        }
    }
    
    private void abrirTelaCadastro() {
        DialogoCadastroAdmin dialogo = new DialogoCadastroAdmin(this, controleUsuario);
        dialogo.setVisible(true);
        
        // Atualiza a interface após cadastro
        if (!controleUsuario.listar().isEmpty() && btnCadastrar != null) {
            btnCadastrar.setVisible(false);
        }
    }
}