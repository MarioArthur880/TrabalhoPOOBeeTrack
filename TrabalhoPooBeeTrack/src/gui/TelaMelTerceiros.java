package gui;

import controle.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
    
class TelaMelTerceiros extends JDialog {
    private ControleMelDeTerceiros controle;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    
    public TelaMelTerceiros(JFrame parent, ControleMelDeTerceiros controle) {
        super(parent, "Gerenciamento de Mel de Terceiros", true);
        this.controle = controle;
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
        painelHeader.setBackground(new Color(255, 193, 7));
        painelHeader.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel lblTitulo = new JLabel("Gerenciamento de Mel de Terceiros");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(51, 51, 51)); // Texto escuro para fundo amarelo
        painelHeader.add(lblTitulo);
        painelPrincipal.add(painelHeader, BorderLayout.NORTH);
        
        String[] colunas = {"ID", "Produtor", "Peso Antes", "Peso Depois", "Extraído", "Data"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        
        tabela = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabela);
        painelPrincipal.add(scrollPane, BorderLayout.CENTER);
        
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        
        JButton btnNovo = new JButton("Novo Registro");
        btnNovo.setBackground(new Color(255, 193, 7));
        btnNovo.setForeground(new Color(51, 51, 51)); // Texto escuro
        btnNovo.setFocusPainted(false);
        btnNovo.setFont(new Font("Arial", Font.BOLD, 12));
        btnNovo.addActionListener(e -> novoRegistro());
        painelBotoes.add(btnNovo);
        
        JButton btnFechar = new JButton("Fechar");
        btnFechar.setBackground(new Color(96, 125, 139));
        btnFechar.setForeground(Color.WHITE);
        btnFechar.setFocusPainted(false);
        btnFechar.addActionListener(e -> dispose());
        painelBotoes.add(btnFechar);
        
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);
        add(painelPrincipal);
    }
    
    private void novoRegistro() {
        DialogoNovoMel dialogo = new DialogoNovoMel(this, controle);
        dialogo.setVisible(true);
        carregarDados();
    }
    
    private void carregarDados() {
        modeloTabela.setRowCount(0);
        for (MelDeTerceiros m : controle.listar()) {
            modeloTabela.addRow(new Object[]{
                m.getId(), m.getProdutor(), m.getPesoAntes(), 
                m.getPesoDepois(), m.getDiferencaPeso(), m.getData()
            });
        }
    }
}
