package gui;

import controle.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

class TelaVisitas extends JDialog {
    private ControleVisita controleVisita;
    private ControleApiario controleApiario;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    
    public TelaVisitas(JFrame parent, ControleVisita controleVisita, ControleApiario controleApiario) {
        super(parent, "Gerenciamento de Visitas", true);
        this.controleVisita = controleVisita;
        this.controleApiario = controleApiario;
        inicializarComponentes();
        carregarDados();
    }
    
    private void inicializarComponentes() {
        setSize(800, 500);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout(10, 10));
        
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel painelHeader = new JPanel(new BorderLayout());
        painelHeader.setBackground(new Color(46, 125, 50));
        painelHeader.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel lblTitulo = new JLabel("Gerenciamento de Visitas");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(51, 51, 51));
        painelHeader.add(lblTitulo);
        painelPrincipal.add(painelHeader, BorderLayout.NORTH);
        
        String[] colunas = {"ID", "Data", "Apiário", "Colheita", "Tipo"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        
        tabela = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabela);
        painelPrincipal.add(scrollPane, BorderLayout.CENTER);
        
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        
        JButton btnNova = new JButton("Nova Visita");
        btnNova.setBackground(new Color(46, 125, 50));
        btnNova.setForeground(new Color(51, 51, 51));
        btnNova.setFocusPainted(false);
        btnNova.setFont(new Font("Arial", Font.BOLD, 12));
        btnNova.setOpaque(true);
        btnNova.setBorderPainted(false);
        btnNova.addActionListener(e -> novaVisita());
        painelBotoes.add(btnNova);
        
        JButton btnFechar = new JButton("Fechar");
        btnFechar.setBackground(new Color(96, 125, 139));
        btnFechar.setForeground(new Color(51, 51, 51));
        btnFechar.setFocusPainted(false);
        btnFechar.setFont(new Font("Arial", Font.BOLD, 12));
        btnFechar.setOpaque(true);
        btnFechar.setBorderPainted(false);
        btnFechar.addActionListener(e -> dispose());
        painelBotoes.add(btnFechar);
        
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);
        add(painelPrincipal);
    }
    
    private void novaVisita() {
        DialogoNovaVisita dialogo = new DialogoNovaVisita(this, controleVisita, controleApiario);
        dialogo.setVisible(true);
        carregarDados();
    }
    
    private void carregarDados() {
        modeloTabela.setRowCount(0);
        for (Visita v : controleVisita.listar()) {
            String nomeApiario = v.getApiario() != null ? v.getApiario().getNome() : "N/A";
            modeloTabela.addRow(new Object[]{
                v.getId(), v.getData(), nomeApiario, v.getColheita(), v.getTipoVisita()
            });
        }
    }
}