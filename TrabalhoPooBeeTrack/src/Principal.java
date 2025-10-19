import controle.ControleUsuario;
import controle.ControleApiario;
import controle.ControleVisita;
import vizualização.UIapiario;
import vizualização.UIusuario;
import vizualização.UIvisita;

import java.util.Scanner;

public class Principal {
    private Scanner scanner = new Scanner(System.in);
    private ControleUsuario controleUsuario = new ControleUsuario();
    private ControleApiario controleApiario = new ControleApiario();
    private ControleVisita controleVisita = new ControleVisita();

    public void exibirMenuPrincipal() {
        int opcao;

        do {
            System.out.println("\n========================================");
            System.out.println("         SISTEMA DE APIÁRIOS");
            System.out.println("========================================");
            System.out.println("1 - Gerenciar Usuários");
            System.out.println("2 - Gerenciar Apiários");
            System.out.println("3 - Gerenciar Visitas");
            System.out.println("0 - Sair");
            System.out.println("========================================");
            System.out.print("Escolha uma opção: ");

            String input = scanner.nextLine();
            if (input.matches("\\d+")) {
                opcao = Integer.parseInt(input);
                processarOpcao(opcao);
            } else {
                System.out.println("Digite um número válido.");
                opcao = -1;
            }

        } while (opcao != 0);

        System.out.println("Sistema encerrado.");
        scanner.close();
    }

    private void processarOpcao(int opcao) {
        switch (opcao) {
            case 1:
                UIusuario uiUsuario = new UIusuario(scanner, controleUsuario);
                uiUsuario.exibir();
                break;
            case 2:
                UIapiario uiApiario = new UIapiario(scanner, controleApiario);
                uiApiario.exibir();
                break;
            case 3:
                UIvisita uiVisita = new UIvisita(scanner, controleVisita, controleApiario);
                uiVisita.exibir();
                break;
            case 0:
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }

    public static void main(String[] args) {
        Principal principal = new Principal();
        principal.exibirMenuPrincipal();
    }
}
