package gui;

import controle.*;
import repositorio.*;
import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {
    private Pessoa usuarioLogado;
    private ControleUsuario controleUsuario;
    private ControleApiario controleApiario;
    private ControleVisita controleVisita;
    private ControleMelDeTerceiros controleMelDeTerceiros;
    
    private static RepositorioApiario repositorioApiarioCompartilhado;
    private static RepositorioVisita repositorioVisitaCompartilhado;
    private static RepositorioMelDeTerceiros repositorioMelCompartilhado;
    
    public TelaPrincipal(Pessoa usuarioLogado, ControleUsuario controleUsuario) {
        this.usuarioLogado = usuarioLogado;
        this.controleUsuario = controleUsuario;
        
        if (repositorioApiarioCompartilhado == null) {
            repositorioApiarioCompartilhado = new RepositorioApiario();
        }
        if (repositorioVisitaCompartilhado == null) {
            repositorioVisitaCompartilhado = new RepositorioVisita();
        }
        if (repositorioMelCompartilhado == null) {
            repositorioMelCompartilhado = new RepositorioMelDeTerceiros();
        }
        
        this.controleApiario = new ControleApiario(repositorioApiarioCompartilhado);
        this.controleVisita = new ControleVisita(repositorioVisitaCompartilhado);
        this.controleMelDeTerceiros = new ControleMelDeTerceiros(repositorioMelCompartilhado);
        
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        setTitle("Sistema de Apiários - Menu Principal");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBackground(new Color(245, 245, 245));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel painelHeader = criarHeader();
        painelPrincipal.add(painelHeader, BorderLayout.NORTH);
        
        JPanel painelCards = criarPainelCards();
        painelPrincipal.add(painelCards, BorderLayout.CENTER);
        
        JPanel painelFooter = criarFooter();
        painelPrincipal.add(painelFooter, BorderLayout.SOUTH);
        
        add(painelPrincipal);
    }
    
    private JPanel criarHeader() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(33, 150, 243));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel lblTitulo = new JLabel("Sistema de Gerenciamento de Apiários");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        panel.add(lblTitulo, BorderLayout.WEST);
        
        JLabel lblUsuario = new JLabel("Usuário: " + usuarioLogado.getNome() + " (" + usuarioLogado.getTipoUsuario() + ")");
        lblUsuario.setFont(new Font("Arial", Font.PLAIN, 14));
        lblUsuario.setForeground(Color.WHITE);
        panel.add(lblUsuario, BorderLayout.EAST);
        
        return panel;
    }
    
    private JPanel criarPainelCards() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 245, 245));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        
        if (usuarioLogado.getTipoUsuario().equalsIgnoreCase("Admin")) {
            gbc.gridx = 0;
            gbc.gridy = 0;
            panel.add(criarCard("Gerenciar Usuários", 
                "Cadastrar, editar e remover usuários do sistema", 
                new Color(156, 39, 176),
                e -> abrirTelaUsuarios()), gbc);
            
            gbc.gridx = 1;
            gbc.gridy = 0;
            panel.add(criarCard("Gerenciar Apiários", 
                "Cadastrar e gerenciar apiários", 
                new Color(255, 152, 0),
                e -> abrirTelaApiarios()), gbc);
            
            gbc.gridx = 0;
            gbc.gridy = 1;
            panel.add(criarCard("Gerenciar Visitas", 
                "Registrar visitas aos apiários", 
                new Color(46, 125, 50),
                e -> abrirTelaVisitas()), gbc);
            
            gbc.gridx = 1;
            gbc.gridy = 1;
            panel.add(criarCard("Mel de Terceiros", 
                "Gerenciar mel recebido de produtores", 
                new Color(230, 160, 0),
                e -> abrirTelaMelTerceiros()), gbc);
        } else {
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            panel.add(criarCard("Gerenciar Apiários", 
                "Cadastrar e gerenciar apiários", 
                new Color(255, 152, 0),
                e -> abrirTelaApiarios()), gbc);
            
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 1;
            panel.add(criarCard("Gerenciar Visitas", 
                "Registrar visitas aos apiários", 
                new Color(46, 125, 50),
                e -> abrirTelaVisitas()), gbc);
            
            gbc.gridx = 1;
            gbc.gridy = 1;
            panel.add(criarCard("Mel de Terceiros", 
                "Gerenciar mel recebido de produtores", 
                new Color(230, 160, 0),
                e -> abrirTelaMelTerceiros()), gbc);
        }
        
        return panel;
    }
    
    private JPanel criarCard(String titulo, String descricao, Color cor, java.awt.event.ActionListener action) {
        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        card.setPreferredSize(new Dimension(300, 200));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        JPanel headerCard = new JPanel(new BorderLayout());
        headerCard.setBackground(cor);
        headerCard.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setForeground(Color.WHITE);
        headerCard.add(lblTitulo, BorderLayout.CENTER);
        
        card.add(headerCard, BorderLayout.NORTH);
        
        JLabel lblDescricao = new JLabel("<html>" + descricao + "</html>");
        lblDescricao.setFont(new Font("Arial", Font.PLAIN, 12));
        lblDescricao.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        card.add(lblDescricao, BorderLayout.CENTER);
        
        JButton btnAcessar = new JButton("Acessar");
        btnAcessar.setBackground(cor);
        btnAcessar.setForeground(Color.BLACK);
        btnAcessar.setFocusPainted(false);
        btnAcessar.setFont(new Font("Arial", Font.BOLD, 12));
        btnAcessar.setOpaque(true);
        btnAcessar.setBorderPainted(false);
        btnAcessar.addActionListener(action);
        card.add(btnAcessar, BorderLayout.SOUTH);
        
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(cor, 2),
                    BorderFactory.createEmptyBorder(20, 20, 20, 20)
                ));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200)),
                    BorderFactory.createEmptyBorder(20, 20, 20, 20)
                ));
            }
        });
        
        return card;
    }
    
    private JPanel criarFooter() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        panel.setBackground(new Color(245, 245, 245));
        
        JButton btnLogout = new JButton("Sair");
        btnLogout.setPreferredSize(new Dimension(100, 35));
        btnLogout.setBackground(new Color(244, 67, 54));
        btnLogout.setForeground(Color.BLACK);
        btnLogout.setFocusPainted(false);
        btnLogout.setFont(new Font("Arial", Font.BOLD, 12));
        btnLogout.setOpaque(true);
        btnLogout.setBorderPainted(false);
        btnLogout.addActionListener(e -> realizarLogout());
        panel.add(btnLogout);
        
        return panel;
    }
    
    private void abrirTelaUsuarios() {
        TelaUsuarios tela = new TelaUsuarios(this, controleUsuario, usuarioLogado);
        tela.setVisible(true);
    }
    
    private void abrirTelaApiarios() {
        TelaApiarios tela = new TelaApiarios(this, controleApiario);
        tela.setVisible(true);
    }
    
    private void abrirTelaVisitas() {
        TelaVisitas tela = new TelaVisitas(this, controleVisita, controleApiario);
        tela.setVisible(true);
    }
    
    private void abrirTelaMelTerceiros() {
        TelaMelTerceiros tela = new TelaMelTerceiros(this, controleMelDeTerceiros);
        tela.setVisible(true);
    }
    
    private void realizarLogout() {
        int resposta = JOptionPane.showConfirmDialog(this,
            "Deseja realmente sair do sistema?",
            "Confirmar Saída",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        if (resposta == JOptionPane.YES_OPTION) {
            SwingUtilities.invokeLater(() -> {
                TelaLogin telaLogin = new TelaLogin(controleUsuario);
                telaLogin.setVisible(true);
                dispose();
            });
        }
    }
}