import controle.ControleUsuario;
import controle.ControleApiario;
import controle.ControleVisita;
import controle.ControleMelDeTerceiros;
import controle.Pessoa;
import repositorio.RepositorioUsuario;
import repositorio.RepositorioApiario;
import repositorio.RepositorioVisita;
import repositorio.RepositorioMelDeTerceiros;
import UI.UIapiario;
import UI.UIusuario;
import UI.UIvisita;
import UI.UImeldeterceiros;

import java.util.Scanner;

public class UIprincipal {
    private Scanner scanner = new Scanner(System.in);
    private ControleUsuario controleUsuario;
    private ControleApiario controleApiario;
    private ControleVisita controleVisita;
    private ControleMelDeTerceiros controleMelDeTerceiros;
    private Pessoa usuarioLogado = null;

    public UIprincipal() {
        try {
            RepositorioUsuario repoUsuario = new RepositorioUsuario();
            RepositorioApiario repoApiario = new RepositorioApiario();
            RepositorioVisita repoVisita = new RepositorioVisita();
            RepositorioMelDeTerceiros repoMelDeTerceiros = new RepositorioMelDeTerceiros();

            this.controleUsuario = new ControleUsuario(repoUsuario);
            this.controleApiario = new ControleApiario(repoApiario);
            this.controleVisita = new ControleVisita(repoVisita);
            this.controleMelDeTerceiros = new ControleMelDeTerceiros(repoMelDeTerceiros);
        } catch (Exception e) {
            System.err.println("Erro ao inicializar o sistema: " + e.getMessage());
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        try {
            UIprincipal principal = new UIprincipal();
            principal.iniciarSistema();
            principal.scanner.close();
        } catch (Exception e) {
            System.err.println("Erro critico no sistema: " + e.getMessage());
        }
    }

    public void iniciarSistema() {
        try {
            System.out.println("========================================");
            System.out.println("        SISTEMA DE APIARIOS");
            System.out.println("========================================");

            while (usuarioLogado == null) {
                exibirTelaInicial();
            }

            exibirMenuPrincipal();
        } catch (Exception e) {
            System.err.println("Erro ao iniciar sistema: " + e.getMessage());
        }
    }

    private void exibirTelaInicial() {
        try {
            System.out.println("\n1 - Fazer login");

            if (controleUsuario.listar().isEmpty()) {
                System.out.println("2 - Cadastrar novo usuario");
            }

            System.out.println("0 - Sair");
            System.out.print("Escolha uma opcao: ");
            String input = scanner.nextLine();

            if (input == null || input.trim().isEmpty()) {
                System.out.println("Opcao invalida.");
                return;
            }

            switch (input.trim()) {
                case "1":
                    usuarioLogado = realizarLogin();
                    break;
                case "2":
                    if (controleUsuario.listar().isEmpty()) {
                        cadastrarUsuario();
                    } else {
                        System.out.println("Cadastro bloqueado. Apenas administradores podem cadastrar novos usuarios.");
                    }
                    break;
                case "0":
                    System.out.println("Encerrando o sistema.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opcao invalida.");
            }
        } catch (Exception e) {
            System.out.println("Erro na tela inicial: " + e.getMessage());
        }
    }

    private Pessoa realizarLogin() {
        System.out.println("\n--- LOGIN ---");
        
        try {
            System.out.print("Email: ");
            String email = scanner.nextLine();
            
            if (email == null || email.trim().isEmpty()) {
                System.out.println("Erro: Email nao pode ser vazio.");
                return null;
            }

            System.out.print("Senha: ");
            String senha = scanner.nextLine();
            
            if (senha == null || senha.trim().isEmpty()) {
                System.out.println("Erro: Senha nao pode ser vazia.");
                return null;
            }

            Pessoa usuario = controleUsuario.buscarPorEmail(email);

            if (usuario != null && usuario.validarSenha(senha)) {
                System.out.println("Login realizado com sucesso. Bem-vindo, " + usuario.getNome() + "!");
                return usuario;
            } else {
                System.out.println("Email ou senha invalidos.");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Erro ao realizar login: " + e.getMessage());
            return null;
        }
    }

    private void cadastrarUsuario() {
        System.out.println("\n--- CADASTRO DE USUARIO ---");

        try {
            System.out.print("Nome: ");
            String nome = scanner.nextLine();
            
            if (nome == null || nome.trim().isEmpty()) {
                System.out.println("Erro: Nome nao pode ser vazio.");
                return;
            }

            System.out.print("Email: ");
            String email = scanner.nextLine();
            
            if (email == null || email.trim().isEmpty()) {
                System.out.println("Erro: Email nao pode ser vazio.");
                return;
            }

            System.out.print("Senha: ");
            String senha = scanner.nextLine();
            
            if (senha == null || senha.trim().isEmpty()) {
                System.out.println("Erro: Senha nao pode ser vazia.");
                return;
            }

            String tipo = "Admin"; 

            boolean sucesso = controleUsuario.criarUsuario(nome, email, senha, tipo);

            if (sucesso) {
                System.out.println("Usuario administrador cadastrado com sucesso. Agora voce pode fazer login.");
            } else {
                System.out.println("Erro ao cadastrar: " + controleUsuario.getUltimaMensagemErro());
            }
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar usuario: " + e.getMessage());
        }
    }

    private void exibirMenuPrincipal() {
        int opcao;

        do {
            try {
                System.out.println("\n========================================");
                System.out.println("         MENU PRINCIPAL");
                System.out.println("========================================");
                System.out.println("1 - Gerenciar Usuarios");
                System.out.println("2 - Gerenciar Apiarios");
                System.out.println("3 - Gerenciar Visitas");
                System.out.println("4 - Gerenciar Mel de Terceiros");
                System.out.println("5 - Logout");
                System.out.println("0 - Sair");
                System.out.println("========================================");
                System.out.print("Escolha uma opcao: ");

                String input = scanner.nextLine();
                
                if (input == null || input.trim().isEmpty()) {
                    System.out.println("Opcao invalida. Digite um numero.");
                    opcao = -1;
                    continue;
                }
                
                if (!input.matches("\\d+")) {
                    System.out.println("Digite apenas numeros.");
                    opcao = -1;
                    continue;
                }
                
                opcao = Integer.parseInt(input);
                processarOpcao(opcao);
                
            } catch (NumberFormatException e) {
                System.out.println("Erro: Digite um numero valido.");
                opcao = -1;
            } catch (Exception e) {
                System.out.println("Erro no menu principal: " + e.getMessage());
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
        try {
            switch (opcao) {
                case 1:
                    if (usuarioLogado.getTipoUsuario().equalsIgnoreCase("Admin")) {
                        UIusuario uiUsuario = new UIusuario(scanner, controleUsuario, usuarioLogado);
                        uiUsuario.exibir();
                    } else {
                        System.out.println("Acesso negado. Apenas administradores podem gerenciar usuarios.");
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
                    UImeldeterceiros uiMelDeTerceiros = new UImeldeterceiros(scanner, controleMelDeTerceiros);
                    uiMelDeTerceiros.exibir();
                    break;
                case 5:
                    System.out.println("Logout realizado. Ate logo, " + usuarioLogado.getNome() + "!");
                    usuarioLogado = null;
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opcao invalida. Escolha entre 0 e 5.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao processar opcao: " + e.getMessage());
        }
    }
}