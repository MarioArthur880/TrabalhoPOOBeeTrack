package UI;

import controle.ControleUsuario;
import controle.Pessoa;
import java.util.List;
import java.util.Scanner;

/**
 * Classe de interface com o usuário para gerenciamento de usuários
 */
public class UIusuario {
    private Scanner scanner;
    private ControleUsuario controle;
    private Pessoa usuarioLogado;

    public UIusuario(Scanner scanner, ControleUsuario controle, Pessoa usuarioLogado) {
        this.scanner = scanner;
        this.controle = controle;
        this.usuarioLogado = usuarioLogado;
    }

    /**
     * Exibe o menu principal
     */
    public void exibir() {
        int opcao;

        do {
            exibirMenu();
            opcao = lerOpcao();
            
            if (opcao != -1) {
                processarOpcao(opcao);
            }

        } while (opcao != 0);
    }

    /**
     * Exibe o menu de opções
     */
    private void exibirMenu() {
        System.out.println("\n========================================");
        System.out.println("      GERENCIAMENTO DE USUÁRIOS");
        System.out.println("========================================");

        if (usuarioLogado.getTipoUsuario().equalsIgnoreCase("Admin")) {
            System.out.println("1 - Cadastrar Usuário");
            System.out.println("2 - Listar Usuários");
            System.out.println("3 - Buscar Usuário");
            System.out.println("4 - Atualizar Usuário");
            System.out.println("5 - Remover Usuário");
            System.out.println("6 - Estatísticas");
        } else {
            System.out.println("2 - Listar Usuários");
        }

        System.out.println("0 - Voltar");
        System.out.println("========================================");
        System.out.print("Escolha uma opção: ");
    }

    /**
     * Lê a opção do usuário
     */
    private int lerOpcao() {
        try {
            String input = scanner.nextLine();
            if (input.matches("\\d+")) {
                return Integer.parseInt(input);
            } else {
                System.out.println("Digite um número válido.");
                return -1;
            }
        } catch (Exception e) {
            System.out.println("Erro ao ler opção.");
            return -1;
        }
    }

    /**
     * Processa a opção escolhida
     */
    private void processarOpcao(int opcao) {
        boolean admin = usuarioLogado.getTipoUsuario().equalsIgnoreCase("Admin");

        switch (opcao) {
            case 1:
                if (admin) cadastrar();
                else System.out.println("✗ Acesso negado. Apenas administradores podem cadastrar usuários.");
                break;
            case 2: 
                listar(); 
                break;
            case 3:
                if (admin) buscar();
                else System.out.println("✗ Acesso negado.");
                break;
            case 4:
                if (admin) atualizar();
                else System.out.println("✗ Acesso negado. Apenas administradores podem atualizar usuários.");
                break;
            case 5:
                if (admin) remover();
                else System.out.println("✗ Acesso negado. Apenas administradores podem remover usuários.");
                break;
            case 6:
                if (admin) exibirEstatisticas();
                else System.out.println("✗ Acesso negado.");
                break;
            case 0:
                System.out.println("Voltando...");
                break;
            default: 
                System.out.println("Opção inválida.");
        }
    }

    /**
     * Cadastra um novo usuário
     */
    private void cadastrar() {
        System.out.println("\n--- CADASTRAR USUÁRIO ---");

        try {
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
                System.out.println("✓ Usuário cadastrado com sucesso!");
            } else {
                System.out.println("✗ " + controle.getUltimaMensagemErro());
            }
            
        } catch (Exception e) {
            System.out.println("✗ Erro inesperado ao cadastrar usuário.");
        }
    }

    /**
     * Lista todos os usuários
     */
    private void listar() {
        System.out.println("\n--- LISTA DE USUÁRIOS ---");
        List<Pessoa> usuarios = controle.listar();

        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário cadastrado.");
        } else {
            if (usuarioLogado.getTipoUsuario().equalsIgnoreCase("Admin")) {
                System.out.printf("%-5s %-20s %-30s %-15s%n", "ID", "Nome", "Email", "Tipo");
                System.out.println("------------------------------------------------------------------------");
                for (Pessoa u : usuarios) {
                    System.out.printf("%-5d %-20s %-30s %-15s%n",
                            u.getId(), 
                            truncar(u.getNome(), 20), 
                            truncar(u.getEmail(), 30), 
                            u.getTipoUsuario());
                }
            } else {
                System.out.printf("%-5s %-20s %-30s %-15s%n", "ID", "Nome", "Email", "Tipo");
                System.out.println("------------------------------------------------------------------------");
                for (Pessoa u : usuarios) {
                    System.out.printf("%-5d %-20s %-30s %-15s%n",
                            u.getId(), 
                            truncar(u.getNome(), 20), 
                            truncar(u.getEmail(), 30), 
                            u.getTipoUsuario());
                }
            }
            
            System.out.println("\nTotal: " + usuarios.size() + " usuário(s)");
        }
    }

    /**
     * Busca um usuário específico
     */
    private void buscar() {
        System.out.println("\n--- BUSCAR USUÁRIO ---");
        System.out.println("1 - Buscar por ID");
        System.out.println("2 - Buscar por Email");
        System.out.println("3 - Buscar por Nome");
        System.out.println("4 - Buscar por Tipo");
        System.out.print("Escolha: ");

        try {
            int opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1: buscarPorId(); break;
                case 2: buscarPorEmail(); break;
                case 3: buscarPorNome(); break;
                case 4: buscarPorTipo(); break;
                default: System.out.println("✗ Opção inválida.");
            }
        } catch (NumberFormatException e) {
            System.out.println("✗ Digite um número válido.");
        } catch (Exception e) {
            System.out.println("✗ Erro ao buscar.");
        }
    }

    private void buscarPorId() {
        try {
            System.out.print("ID do usuário: ");
            int id = Integer.parseInt(scanner.nextLine());
            
            Pessoa usuario = controle.buscarPorId(id);
            
            if (usuario != null) {
                exibirUsuario(usuario);
            } else {
                System.out.println("✗ Usuário não encontrado.");
            }
        } catch (NumberFormatException e) {
            System.out.println("✗ ID deve ser um número válido.");
        }
    }

    private void buscarPorEmail() {
        System.out.print("Email do usuário: ");
        String email = scanner.nextLine();
        
        Pessoa usuario = controle.buscarPorEmail(email);
        
        if (usuario != null) {
            exibirUsuario(usuario);
        } else {
            System.out.println("✗ Usuário não encontrado.");
        }
    }

    private void buscarPorNome() {
        System.out.print("Nome do usuário: ");
        String nome = scanner.nextLine();
        
        Pessoa usuario = controle.buscarPorNome(nome);
        
        if (usuario != null) {
            exibirUsuario(usuario);
        } else {
            System.out.println("✗ Usuário não encontrado.");
        }
    }

    private void buscarPorTipo() {
        System.out.print("Tipo (Admin/Apicultor): ");
        String tipo = scanner.nextLine();
        
        List<Pessoa> usuarios = controle.listarPorTipo(tipo);
        
        if (usuarios.isEmpty()) {
            System.out.println("✗ Nenhum usuário encontrado com este tipo.");
        } else {
            System.out.println("\n✓ " + usuarios.size() + " usuário(s) encontrado(s):");
            for (Pessoa u : usuarios) {
                exibirUsuario(u);
                System.out.println("---");
            }
        }
    }

    /**
     * Atualiza um usuário existente
     */
    private void atualizar() {
        System.out.println("\n--- ATUALIZAR USUÁRIO ---");
        
        try {
            System.out.print("Email do usuário: ");
            String email = scanner.nextLine();

            Pessoa usuario = controle.buscarPorEmail(email);

            if (usuario == null) {
                System.out.println("✗ Usuário não encontrado.");
                return;
            }

            System.out.println("\nDados atuais:");
            exibirUsuario(usuario);
            System.out.println("\n(Pressione Enter para manter o valor atual)");

            System.out.print("Novo nome [" + usuario.getNome() + "]: ");
            String nome = scanner.nextLine();
            if (!nome.trim().isEmpty()) {
                usuario.setNome(nome);
            }

            System.out.print("Novo email [" + usuario.getEmail() + "]: ");
            String novoEmail = scanner.nextLine();
            if (!novoEmail.trim().isEmpty()) {
                usuario.setEmail(novoEmail);
            }

            System.out.print("Nova senha: ");
            String senha = scanner.nextLine();
            if (!senha.trim().isEmpty()) {
                usuario.setSenha(senha);
            }

            System.out.print("Novo tipo [" + usuario.getTipoUsuario() + "]: ");
            String tipo = scanner.nextLine();
            if (!tipo.trim().isEmpty()) {
                usuario.setTipoUsuario(tipo);
            }

            boolean sucesso = controle.atualizar(usuario);
            
            if (sucesso) {
                System.out.println("✓ Usuário atualizado com sucesso!");
            } else {
                System.out.println("✗ " + controle.getUltimaMensagemErro());
            }
            
        } catch (Exception e) {
            System.out.println("✗ Erro inesperado ao atualizar usuário.");
        }
    }

    /**
     * Remove um usuário
     */
    private void remover() {
        System.out.println("\n--- REMOVER USUÁRIO ---");
        
        try {
            System.out.print("Email do usuário: ");
            String email = scanner.nextLine();

            Pessoa usuario = controle.buscarPorEmail(email);
            
            if (usuario == null) {
                System.out.println("✗ Usuário não encontrado.");
                return;
            }

            // Impede que o usuário logado se remova
            if (usuario.getId() == usuarioLogado.getId()) {
                System.out.println("✗ Você não pode remover seu próprio usuário.");
                return;
            }

            exibirUsuario(usuario);
            System.out.print("\nConfirma a remoção? (S/N): ");
            String confirmacao = scanner.nextLine();

            if (confirmacao.equalsIgnoreCase("S") || confirmacao.equalsIgnoreCase("SIM")) {
                boolean sucesso = controle.remover(email);
                
                if (sucesso) {
                    System.out.println("✓ Usuário removido com sucesso!");
                } else {
                    System.out.println("✗ " + controle.getUltimaMensagemErro());
                }
            } else {
                System.out.println("Remoção cancelada.");
            }
            
        } catch (Exception e) {
            System.out.println("✗ Erro inesperado ao remover usuário.");
        }
    }

    /**
     * Exibe estatísticas dos usuários
     */
    private void exibirEstatisticas() {
        System.out.println("\n--- ESTATÍSTICAS ---");
        
        int totalUsuarios = controle.getTotalUsuarios();
        int totalAdmins = controle.listarPorTipo("Admin").size();
        int totalApicultores = controle.listarPorTipo("Apicultor").size();
        
        System.out.println("Total de usuários: " + totalUsuarios);
        System.out.println("Administradores: " + totalAdmins);
        System.out.println("Apicultores: " + totalApicultores);
    }

    /**
     * Exibe os detalhes de um usuário
     */
    private void exibirUsuario(Pessoa u) {
        System.out.println("\nID: " + u.getId());
        System.out.println("Nome: " + u.getNome());
        System.out.println("Email: " + u.getEmail());
        System.out.println("Tipo: " + u.getTipoUsuario());
        if (usuarioLogado.getTipoUsuario().equalsIgnoreCase("Admin")) {
            System.out.println("Senha: " + u.getSenha());
        }
    }

    /**
     * Trunca uma string para o tamanho especificado
     */
    private String truncar(String texto, int tamanho) {
        if (texto == null) return "";
        if (texto.length() <= tamanho) return texto;
        return texto.substring(0, tamanho - 3) + "...";
    }
}