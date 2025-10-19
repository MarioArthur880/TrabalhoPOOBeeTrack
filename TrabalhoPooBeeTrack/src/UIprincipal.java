import controle.ControleUsuario;
import controle.ControleApiario;
import controle.ControleVisita;
import modelo.Usuario;
import vizualização.UIapiario;
import vizualização.UIusuario;
import vizualização.UIvisita;

import java.util.Scanner;

public class UIprincipal {
    private Scanner scanner = new Scanner(System.in);
    private ControleUsuario controleUsuario = new ControleUsuario();
    private ControleApiario controleApiario = new ControleApiario();
    private ControleVisita controleVisita = new ControleVisita();
    private Usuario usuarioLogado = null;

    public static void main(String[] args) {
        UIprincipal principal = new UIprincipal();
        principal.iniciarSistema();
    }

    public void iniciarSistema() {
        System.out.println("========================================");
        System.out.println("        SISTEMA DE APIÁRIOS");
        System.out.println("========================================");

        while (usuarioLogado == null) {
            exibirTelaInicial();
        }

        exibirMenuPrincipal();
        scanner.close();
    }

    private void exibirTelaInicial() {
        System.out.println("\n1 - Fazer login");
        System.out.println("2 - Cadastrar novo usuário");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opção: ");
        String input = scanner.nextLine();

        switch (input) {
            case "1":
                usuarioLogado = realizarLogin();
                break;
            case "2":
                cadastrarUsuario();
                break;
            case "0":
                System.out.println("Encerrando o sistema.");
                System.exit(0);
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }

    private Usuario realizarLogin() {
        System.out.println("\n--- LOGIN ---");
        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        Usuario usuario = controleUsuario.buscarPorEmail(email);

        if (usuario != null && usuario.getSenha().equals(senha)) {
            System.out.println("Login realizado com sucesso. Bem-vindo, " + usuario.getNome() + "!");
            return usuario;
        } else {
            System.out.println("Email ou senha inválidos.");
            return null;
        }
    }

    private void cadastrarUsuario() {
        System.out.println("\n--- CADASTRO DE USUÁRIO ---");

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        System.out.print("Tipo de usuário (Admin/Apicultor): ");
        String tipo = scanner.nextLine();

        boolean sucesso = controleUsuario.criarUsuario(nome, email, senha, tipo);

        if (sucesso) {
            System.out.println("Usuário cadastrado com sucesso. Agora você pode fazer login.");
        } else {
            System.out.println("Cadastro falhou. Verifique os dados ou se o e-mail já está em uso.");
        }
    }

    private void exibirMenuPrincipal() {
        int opcao;

        do {
            System.out.println("\n========================================");
            System.out.println("         MENU PRINCIPAL");
            System.out.println("========================================");
            System.out.println("1 - Gerenciar Usuários");
            System.out.println("2 - Gerenciar Apiários");
            System.out.println("3 - Gerenciar Visitas");
            System.out.println("4 - Logout");
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

        } while (opcao != 0 && usuarioLogado != null);

        if (usuarioLogado == null) {
            iniciarSistema();
        } else {
            System.out.println("Sistema encerrado.");
        }
    }

    private void processarOpcao(int opcao) {
        switch (opcao) {
            case 1:
                if (usuarioLogado.getTipoUsuario().equalsIgnoreCase("Admin")) {
                    UIusuario uiUsuario = new UIusuario(scanner, controleUsuario, usuarioLogado);
                    uiUsuario.exibir();
                } else {
                    System.out.println("Acesso negado. Apenas administradores podem gerenciar usuários.");
                }
                break;
            case 2:
                UIapiario uiApiario = new UIapiario(scanner, controleApiario);
                uiApiario.exibir();
                break;
            case 3:
                UIvisita uiVisita = new UIvisita(scanner, controleVisita, controleApiario);
                uiVisita.exibir();
                break;
            case 4:
                System.out.println("Logout realizado. Até logo, " + usuarioLogado.getNome() + "!");
                usuarioLogado = null;
                break;
            case 0:
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }
}
