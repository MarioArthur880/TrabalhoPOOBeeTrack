package gui;

import controle.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaUsuarios extends JDialog {
    private ControleUsuario controle;
    private Pessoa usuarioLogado;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    
    public TelaUsuarios(JFrame parent, ControleUsuario controle, Pessoa usuarioLogado) {
        super(parent, "Gerenciamento de Usuários", true);
        this.controle = controle;
        this.usuarioLogado = usuarioLogado;
        inicializarComponentes();
        carregarDados();
    }
    
    private void inicializarComponentes() {
        setSize(700, 500);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout(10, 10));
        
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel painelHeader = new JPanel(new BorderLayout());
        painelHeader.setBackground(new Color(156, 39, 176));
        painelHeader.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel lblTitulo = new JLabel("Gerenciamento de Usuários");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        painelHeader.add(lblTitulo);
        
        painelPrincipal.add(painelHeader, BorderLayout.NORTH);
        
        String[] colunas = {"ID", "Nome", "Email", "Tipo"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        
        tabela = new JTable(modeloTabela);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tabela);
        painelPrincipal.add(scrollPane, BorderLayout.CENTER);
        
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        
        JButton btnNovo = criarBotao("Novo Usuário", new Color(76, 175, 80));
        btnNovo.addActionListener(e -> novoUsuario());
        painelBotoes.add(btnNovo);
        
        JButton btnExcluir = criarBotao("Excluir", new Color(244, 67, 54));
        btnExcluir.addActionListener(e -> excluirUsuario());
        painelBotoes.add(btnExcluir);
        
        JButton btnFechar = criarBotao("Fechar", new Color(96, 125, 139));
        btnFechar.addActionListener(e -> dispose());
        painelBotoes.add(btnFechar);
        
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);
        add(painelPrincipal);
    }
    
    private JButton criarBotao(String texto, Color cor) {
        JButton btn = new JButton(texto);
        btn.setBackground(cor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 11));
        return btn;
    }
    
    private void novoUsuario() {
        DialogoCadastroUsuario dialogo = new DialogoCadastroUsuario(this, controle);
        dialogo.setVisible(true);
        carregarDados();
    }
    
    private void excluirUsuario() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um usuário!");
            return;
        }
        
        int id = (Integer) modeloTabela.getValueAt(linha, 0);
        String nome = (String) modeloTabela.getValueAt(linha, 1);
        
        if (id == usuarioLogado.getId()) {
            JOptionPane.showMessageDialog(this, "Você não pode excluir seu próprio usuário!");
            return;
        }
        
        int confirmacao = JOptionPane.showConfirmDialog(this,
            "Excluir o usuário \"" + nome + "\"?",
            "Confirmar",
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacao == JOptionPane.YES_OPTION) {
            if (controle.removerPorId(id)) {
                JOptionPane.showMessageDialog(this, "Usuário excluído!");
                carregarDados();
            } else {
                JOptionPane.showMessageDialog(this, "Erro: " + controle.getUltimaMensagemErro());
            }
        }
    }
    
    private void carregarDados() {
        modeloTabela.setRowCount(0);
        for (Pessoa p : controle.listar()) {
            modeloTabela.addRow(new Object[]{p.getId(), p.getNome(), p.getEmail(), p.getTipoUsuario()});
        }
    }
}