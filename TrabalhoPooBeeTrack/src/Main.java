import gui.TelaLogin;
import controle.ControleUsuario;
import repositorio.RepositorioUsuario;
import javax.swing.*;


public class Main {
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Erro ao configurar Look and Feel: " + e.getMessage());
        }
        
        SwingUtilities.invokeLater(() -> {
            try {
                RepositorioUsuario repositorioUsuario = new RepositorioUsuario();
                ControleUsuario controleUsuario = new ControleUsuario(repositorioUsuario);
                
                TelaLogin telaLogin = new TelaLogin(controleUsuario);
                telaLogin.setVisible(true);
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                    "Erro ao iniciar o sistema: " + e.getMessage(),
                    "Erro Crítico",
                    JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        });
    }
}