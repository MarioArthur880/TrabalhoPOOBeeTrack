import gui.TelaLogin;
import controle.ControleUsuario;
import repositorio.RepositorioUsuario;
import javax.swing.*;


public class Main {
    
    public static void main(String[] args) {
        // Configura o Look and Feel para o sistema operacional
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Erro ao configurar Look and Feel: " + e.getMessage());
        }
        
        // Inicializa o sistema na thread de eventos do Swing
        SwingUtilities.invokeLater(() -> {
            try {
                // Inicializa os repositórios e controles
                RepositorioUsuario repositorioUsuario = new RepositorioUsuario();
                ControleUsuario controleUsuario = new ControleUsuario(repositorioUsuario);
                
                // Abre a tela de login
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