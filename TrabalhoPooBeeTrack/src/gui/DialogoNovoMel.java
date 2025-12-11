package gui;

import controle.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;


class DialogoNovoMel extends JDialog {
    private ControleMelDeTerceiros controle;
    private JTextField txtProdutor, txtPesoAntes, txtPesoDepois, txtData;
    
    public DialogoNovoMel(JDialog parent, ControleMelDeTerceiros controle) {
        super(parent, "Novo Registro de Mel", true);
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
        
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Produtor:"), gbc);
        txtProdutor = new JTextField(20);
        gbc.gridx = 1; panel.add(txtProdutor, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Peso Antes (kg):"), gbc);
        txtPesoAntes = new JTextField(20);
        gbc.gridx = 1; panel.add(txtPesoAntes, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Peso Depois (kg):"), gbc);
        txtPesoDepois = new JTextField(20);
        gbc.gridx = 1; panel.add(txtPesoDepois, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Data (dd/mm/aaaa):"), gbc);
        txtData = new JTextField(20);
        gbc.gridx = 1; panel.add(txtData, gbc);
        
        add(panel, BorderLayout.CENTER);
        
        JPanel painelBotoes = new JPanel(new FlowLayout());
        
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setPreferredSize(new Dimension(100, 30));
        btnSalvar.setBackground(new Color(255, 193, 7));
        btnSalvar.setForeground(new Color(51, 51, 51));
        btnSalvar.setFocusPainted(false);
        btnSalvar.addActionListener(e -> salvar());
        painelBotoes.add(btnSalvar);
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setPreferredSize(new Dimension(100, 30));
        btnCancelar.setBackground(new Color(158, 158, 158));
        btnCancelar.setForeground(new Color(51, 51, 51));
        btnCancelar.setFocusPainted(false);
        btnCancelar.addActionListener(e -> dispose());
        painelBotoes.add(btnCancelar);
        
        add(painelBotoes, BorderLayout.SOUTH);
    }
    
    private void salvar() {
        String produtor = txtProdutor.getText().trim();
        double pesoAntes = Double.parseDouble(txtPesoAntes.getText().trim());
        double pesoDepois = Double.parseDouble(txtPesoDepois.getText().trim());
        String data = txtData.getText().trim();
        
        if (controle.criarMelDeTerceiros(produtor, pesoAntes, pesoDepois, data)) {
            JOptionPane.showMessageDialog(this, "Mel registrado com sucesso!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao registrar mel!");
        }
    }
}