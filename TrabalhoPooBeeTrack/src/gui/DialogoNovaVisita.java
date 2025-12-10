package gui;

import controle.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;


class DialogoNovaVisita extends JDialog {
    private ControleVisita controleVisita;
    private ControleApiario controleApiario;
    private JTextField txtData, txtColheita, txtTipo;
    private JComboBox<String> cmbApiario;
    
    public DialogoNovaVisita(JDialog parent, ControleVisita controleVisita, ControleApiario controleApiario) {
        super(parent, "Nova Visita", true);
        this.controleVisita = controleVisita;
        this.controleApiario = controleApiario;
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
        
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Data (dd/mm/aaaa):"), gbc);
        txtData = new JTextField(15);
        gbc.gridx = 1; panel.add(txtData, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Apiário:"), gbc);
        cmbApiario = new JComboBox<>();
        for (Apiario a : controleApiario.listar()) {
            cmbApiario.addItem(a.getId() + " - " + a.getNome());
        }
        gbc.gridx = 1; panel.add(cmbApiario, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Colheita (kg):"), gbc);
        txtColheita = new JTextField(15);
        gbc.gridx = 1; panel.add(txtColheita, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Tipo:"), gbc);
        txtTipo = new JTextField(15);
        gbc.gridx = 1; panel.add(txtTipo, gbc);
        
        add(panel, BorderLayout.CENTER);
        
        JPanel painelBotoes = new JPanel(new FlowLayout());
        
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setPreferredSize(new Dimension(100, 30));
        btnSalvar.setBackground(new Color(76, 175, 80));
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setFocusPainted(false);
        btnSalvar.addActionListener(e -> salvar());
        painelBotoes.add(btnSalvar);
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setPreferredSize(new Dimension(100, 30));
        btnCancelar.setBackground(new Color(158, 158, 158));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFocusPainted(false);
        btnCancelar.addActionListener(e -> dispose());
        painelBotoes.add(btnCancelar);
        
        add(painelBotoes, BorderLayout.SOUTH);
    }
    
    private void salvar() {
        String data = txtData.getText().trim();
        String colheitaStr = txtColheita.getText().trim();
        String tipo = txtTipo.getText().trim();
        
        if (cmbApiario.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um apiário!");
            return;
        }
        
        String itemSelecionado = (String) cmbApiario.getSelectedItem();
        int idApiario = Integer.parseInt(itemSelecionado.split(" - ")[0]);
        Apiario apiario = controleApiario.buscarPorId(idApiario);
        
        int colheita = Integer.parseInt(colheitaStr);
        
        if (controleVisita.criarVisita(data, apiario, colheita, tipo)) {
            JOptionPane.showMessageDialog(this, "Visita registrada com sucesso!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao registrar visita!");
        }
    }
}
