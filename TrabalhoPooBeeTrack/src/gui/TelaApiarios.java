// ============================================
// ARQUIVO: src/GUI/TelaApiarios.java
// ============================================
package gui;

import controle.Apiario;
import controle.ControleApiario;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaApiarios extends JDialog {
    private ControleApiario controle;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JTextField txtNome, txtRaca, txtLocal, txtCaixas;
    private JButton btnNovo, btnSalvar, btnEditar, btnExcluir, btnCancelar;
    private Integer idSelecionado = null;
    
    public TelaApiarios(JFrame parent, ControleApiario controle) {
        super(parent, "Gerenciamento de Apiários", true);
        this.controle = controle;
        inicializarComponentes();
        carregarDados();
    }
    
    private void inicializarComponentes() {
        setSize(900, 600);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout(10, 10));
        
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel painelHeader = new JPanel(new BorderLayout());
        painelHeader.setBackground(new Color(255, 152, 0));
        painelHeader.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel lblTitulo = new JLabel("Gerenciamento de Apiários");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        painelHeader.add(lblTitulo);
        
        painelPrincipal.add(painelHeader, BorderLayout.NORTH);
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(350);
        splitPane.setLeftComponent(criarPainelFormulario());
        splitPane.setRightComponent(criarPainelTabela());
        
        painelPrincipal.add(splitPane, BorderLayout.CENTER);
        
        add(painelPrincipal);
    }
    
    private JPanel criarPainelFormulario() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Cadastro / Edição"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        JPanel painelCampos = new JPanel(new GridBagLayout());
        painelCampos.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        painelCampos.add(new JLabel("Nome:"), gbc);
        
        txtNome = new JTextField();
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
        painelCampos.add(txtNome, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        painelCampos.add(new JLabel("Raça:"), gbc);
        
        txtRaca = new JTextField();
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0;
        painelCampos.add(txtRaca, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        painelCampos.add(new JLabel("Local:"), gbc);
        
        txtLocal = new JTextField();
        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 1.0;
        painelCampos.add(txtLocal, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0;
        painelCampos.add(new JLabel("Qnt. Caixas:"), gbc);
        
        txtCaixas = new JTextField();
        gbc.gridx = 1; gbc.gridy = 3; gbc.weightx = 1.0;
        painelCampos.add(txtCaixas, gbc);
        
        panel.add(painelCampos, BorderLayout.CENTER);
        
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        painelBotoes.setBackground(Color.WHITE);
        
        btnNovo = criarBotao("Novo", new Color(76, 175, 80));
        btnNovo.addActionListener(e -> novo());
        painelBotoes.add(btnNovo);
        
        btnSalvar = criarBotao("Salvar", new Color(33, 150, 243));
        btnSalvar.addActionListener(e -> salvar());
        painelBotoes.add(btnSalvar);
        
        btnCancelar = criarBotao("Cancelar", new Color(158, 158, 158));
        btnCancelar.addActionListener(e -> cancelar());
        painelBotoes.add(btnCancelar);
        
        panel.add(painelBotoes, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel criarPainelTabela() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Lista de Apiários"),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        
        String[] colunas = {"ID", "Nome", "Raça", "Local", "Caixas"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tabela = new JTable(modeloTabela);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.getTableHeader().setReorderingAllowed(false);
        
        tabela.getColumnModel().getColumn(0).setPreferredWidth(50);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(150);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(120);
        tabela.getColumnModel().getColumn(3).setPreferredWidth(120);
        tabela.getColumnModel().getColumn(4).setPreferredWidth(80);
        
        JScrollPane scrollPane = new JScrollPane(tabela);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        JPanel painelAcoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        
        btnEditar = criarBotao("Editar", new Color(255, 152, 0));
        btnEditar.addActionListener(e -> editar());
        painelAcoes.add(btnEditar);
        
        btnExcluir = criarBotao("Excluir", new Color(244, 67, 54));
        btnExcluir.addActionListener(e -> excluir());
        painelAcoes.add(btnExcluir);
        
        JButton btnAtualizar = criarBotao("Atualizar", new Color(96, 125, 139));
        btnAtualizar.addActionListener(e -> carregarDados());
        painelAcoes.add(btnAtualizar);
        
        panel.add(painelAcoes, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JButton criarBotao(String texto, Color cor) {
        JButton btn = new JButton(texto);
        btn.setPreferredSize(new Dimension(90, 30));
        btn.setBackground(cor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 11));
        return btn;
    }
    
    private void novo() {
        limparCampos();
        idSelecionado = null;
        txtNome.requestFocus();
    }
    
    private void salvar() {
        String nome = txtNome.getText().trim();
        String raca = txtRaca.getText().trim();
        String local = txtLocal.getText().trim();
        String caixasStr = txtCaixas.getText().trim();
        
        if (nome.isEmpty() || raca.isEmpty() || local.isEmpty() || caixasStr.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Todos os campos são obrigatórios!",
                "Validação",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int caixas;
        try {
            caixas = Integer.parseInt(caixasStr);
            if (caixas <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "Quantidade de caixas deve ser um número positivo!",
                "Validação",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        boolean sucesso;
        
        if (idSelecionado == null) {
            sucesso = controle.criarApiario(nome, raca, local, caixas);
        } else {
            Apiario apiario = controle.buscarPorId(idSelecionado);
            if (apiario != null) {
                apiario.setNome(nome);
                apiario.setRaca(raca);
                apiario.setLocal(local);
                apiario.setQntCaixas(caixas);
                sucesso = controle.atualizar(apiario);
            } else {
                sucesso = false;
            }
        }
        
        if (sucesso) {
            JOptionPane.showMessageDialog(this,
                "Apiário salvo com sucesso!",
                "Sucesso",
                JOptionPane.INFORMATION_MESSAGE);
            limparCampos();
            carregarDados();
        } else {
            JOptionPane.showMessageDialog(this,
                "Erro: " + controle.getUltimaMensagemErro(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void editar() {
        int linhaSelecionada = tabela.getSelectedRow();
        
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this,
                "Selecione um apiário para editar!",
                "Aviso",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        idSelecionado = (Integer) modeloTabela.getValueAt(linhaSelecionada, 0);
        Apiario apiario = controle.buscarPorId(idSelecionado);
        
        if (apiario != null) {
            txtNome.setText(apiario.getNome());
            txtRaca.setText(apiario.getRaca());
            txtLocal.setText(apiario.getLocal());
            txtCaixas.setText(String.valueOf(apiario.getQntCaixas()));
            txtNome.requestFocus();
        }
    }
    
    private void excluir() {
        int linhaSelecionada = tabela.getSelectedRow();
        
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this,
                "Selecione um apiário para excluir!",
                "Aviso",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int id = (Integer) modeloTabela.getValueAt(linhaSelecionada, 0);
        String nome = (String) modeloTabela.getValueAt(linhaSelecionada, 1);
        
        int confirmacao = JOptionPane.showConfirmDialog(this,
            "Deseja realmente excluir o apiário \"" + nome + "\"?",
            "Confirmar Exclusão",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        if (confirmacao == JOptionPane.YES_OPTION) {
            boolean sucesso = controle.remover(id);
            
            if (sucesso) {
                JOptionPane.showMessageDialog(this,
                    "Apiário excluído com sucesso!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);
                limparCampos();
                carregarDados();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Erro: " + controle.getUltimaMensagemErro(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void cancelar() {
        limparCampos();
    }
    
    private void limparCampos() {
        txtNome.setText("");
        txtRaca.setText("");
        txtLocal.setText("");
        txtCaixas.setText("");
        idSelecionado = null;
        tabela.clearSelection();
    }
    
    private void carregarDados() {
        modeloTabela.setRowCount(0);
        List<Apiario> apiarios = controle.listar();
        
        for (Apiario a : apiarios) {
            Object[] linha = {
                a.getId(),
                a.getNome(),
                a.getRaca(),
                a.getLocal(),
                a.getQntCaixas()
            };
            modeloTabela.addRow(linha);
        }
    }
}