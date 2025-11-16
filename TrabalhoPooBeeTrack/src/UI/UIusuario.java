package UI;

import controle.ControleUsuario;
import controle.Pessoa;
import java.util.List;
import java.util.Scanner;

public class UIusuario {
    private Scanner scanner;
    private ControleUsuario controle;
    private Pessoa usuarioLogado;

    public UIusuario(Scanner scanner, ControleUsuario controle, Pessoa usuarioLogado) {
        this.scanner = scanner;
        this.controle = controle;
        this.usuarioLogado = usuarioLogado;
    }

    public void exibir() {
        int opcao;

        do {
            System.out.println("\n========================================");
            System.out.println("      GERENCIAMENTO DE USUÁRIOS");
            System.out.println("========================================");

            if (usuarioLogado.getTipoUsuario().equalsIgnoreCase("Admin")) {
                System.out.println("1 - Cadastrar Usuário");
                System.out.println("2 - Listar Usuários");
                System.out.println("3 - Atualizar Usuário");
                System.out.println("4 - Remover Usuário");
            } else {
                System.out.println("2 - Listar Usuários");
            }

            System.out.println("0 - Voltar");
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
    }

    private void processarOpcao(int opcao) {
        boolean admin = usuarioLogado.getTipoUsuario().equalsIgnoreCase("Admin");

        switch (opcao) {
            case 1:
                if (admin) cadastrar();
                else System.out.println("Acesso negado. Apenas administradores podem cadastrar usuários.");
                break;
            case 2: 
                listar(); 
                break;
            case 3:
                if (admin) atualizar();
                else System.out.println("Acesso negado. Apenas administradores podem atualizar usuários.");
                break;
            case 4:
                if (admin) remover();
                else System.out.println("Acesso negado. Apenas administradores podem remover usuários.");
                break;
            case 0: 
                break;
            default: 
                System.out.println("Opção inválida.");
        }
    }

    private void cadastrar() {
        System.out.println("\n--- CADASTRAR USUÁRIO ---");

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        System.out.print("Tipo de Usuário (Admin/Apicultor): ");
        String tipoUsuario = scanner.nextLine();

        boolean sucesso = controle.criarUsuario(nome, email, senha, tipoUsuario);

        if (sucesso) {
            Pessoa novo = controle.buscarPorEmail(email);
            if (novo != null && novo.getTipoUsuario().equalsIgnoreCase("Admin") && controle.listar().size() == 1) {
                System.out.println("Este é o primeiro usuário do sistema e foi cadastrado como Administrador.");
            } else {
                System.out.println("Usuário cadastrado com sucesso.");
            }
        } else {
            System.out.println("Dados inválidos ou e-mail já cadastrado.");
        }
    }

    private void listar() {
        System.out.println("\n--- LISTA DE USUÁRIOS ---");
        List<Pessoa> usuarios = controle.listar();

        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário cadastrado.");
        } else {
            if (usuarioLogado.getTipoUsuario().equalsIgnoreCase("Admin")) {
                System.out.printf("%-5s %-20s %-25s %-15s %-15s%n", "ID", "Nome", "Email", "Tipo", "Senha");
                System.out.println("-------------------------------------------------------------------------------");
                for (Pessoa u : usuarios) {
                    System.out.printf("%-5d %-20s %-25s %-15s %-15s%n",
                            u.getId(), u.getNome(), u.getEmail(), u.getTipoUsuario(), u.getSenha());
                }
            } else {
                System.out.printf("%-5s %-20s %-25s %-15s%n", "ID", "Nome", "Email", "Tipo");
                System.out.println("---------------------------------------------------------------");
                for (Pessoa u : usuarios) {
                    System.out.printf("%-5d %-20s %-25s %-15s%n",
                            u.getId(), u.getNome(), u.getEmail(), u.getTipoUsuario());
                }
            }
        }
    }

    private void atualizar() {
        System.out.println("\n--- ATUALIZAR USUÁRIO ---");
        System.out.print("Email do usuário: ");
        String email = scanner.nextLine();

        Pessoa usuario = controle.buscarPorEmail(email);

        if (usuario == null) {
            System.out.println("Usuário não encontrado.");
            return;
        }

        System.out.print("Novo nome (Enter para manter): ");
        String nome = scanner.nextLine();
        if (!nome.trim().isEmpty()) {
            usuario.setNome(nome);
        }

        System.out.print("Nova senha (Enter para manter): ");
        String senha = scanner.nextLine();
        if (!senha.trim().isEmpty()) {
            usuario.setSenha(senha);
        }

        System.out.print("Novo tipo (Enter para manter): ");
        String tipo = scanner.nextLine();
        if (!tipo.trim().isEmpty()) {
            usuario.setTipoUsuario(tipo);
        }

        boolean sucesso = controle.atualizar(usuario);
        if (sucesso) {
            System.out.println("Usuário atualizado com sucesso.");
        } else {
            System.out.println("Erro ao atualizar usuário.");
        }
    }

    private void remover() {
        System.out.println("\n--- REMOVER USUÁRIO ---");
        System.out.print("Email: ");
        String email = scanner.nextLine();

        if (controle.remover(email)) {
            System.out.println("Usuário removido com sucesso.");
        } else {
            System.out.println("Usuário não encontrado.");
        }
    }
}