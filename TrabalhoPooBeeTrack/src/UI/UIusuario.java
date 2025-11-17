package UI;

import controle.ControleUsuario;
import controle.Pessoa;
import java.util.List;
import java.util.Scanner;

/**
 * Classe de interface com o usuario para gerenciamento de usuarios
 */
public class UIusuario {
    private Scanner scanner;
    private ControleUsuario controle;
    private Pessoa usuarioLogado;

    public UIusuario(Scanner scanner, ControleUsuario controle, Pessoa usuarioLogado) {
        if (scanner == null || controle == null || usuarioLogado == null) {
            throw new IllegalArgumentException("Parametros nao podem ser nulos");
        }
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
            try {
                exibirMenu();
                opcao = lerOpcao();
                
                if (opcao != -1) {
                    processarOpcao(opcao);
                }
            } catch (Exception e) {
                System.out.println("Erro no menu: " + e.getMessage());
                opcao = -1;
            }

        } while (opcao != 0);
    }

    /**
     * Exibe o menu de opcoes
     */
    private void exibirMenu() {
        System.out.println("\n========================================");
        System.out.println("      GERENCIAMENTO DE USUARIOS");
        System.out.println("========================================");

        if (usuarioLogado.getTipoUsuario().equalsIgnoreCase("Admin")) {
            System.out.println("1 - Cadastrar Usuario");
            System.out.println("2 - Listar Usuarios");
            System.out.println("3 - Buscar Usuario");
            System.out.println("4 - Atualizar Usuario");
            System.out.println("5 - Remover Usuario");
            System.out.println("6 - Estatisticas");
        } else {
            System.out.println("2 - Listar Usuarios");
        }

        System.out.println("0 - Voltar");
        System.out.println("========================================");
        System.out.print("Escolha uma opcao: ");
    }

    /**
     * Le a opcao do usuario
     */
    private int lerOpcao() {
        try {
            String input = scanner.nextLine();
            
            if (input == null || input.trim().isEmpty()) {
                System.out.println("Opcao invalida. Digite um numero.");
                return -1;
            }
            
            if (!input.matches("\\d+")) {
                System.out.println("Digite apenas numeros.");
                return -1;
            }
            
            return Integer.parseInt(input);
            
        } catch (NumberFormatException e) {
            System.out.println("Erro: Digite um numero valido.");
            return -1;
        } catch (Exception e) {
            System.out.println("Erro ao ler opcao: " + e.getMessage());
            return -1;
        }
    }

    /**
     * Processa a opcao escolhida
     */
    private void processarOpcao(int opcao) {
        try {
            boolean admin = usuarioLogado.getTipoUsuario().equalsIgnoreCase("Admin");

            switch (opcao) {
                case 1:
                    if (admin) cadastrar();
                    else System.out.println("Acesso negado. Apenas administradores podem cadastrar usuarios.");
                    break;
                case 2: 
                    listar(); 
                    break;
                case 3:
                    if (admin) buscar();
                    else System.out.println("Acesso negado.");
                    break;
                case 4:
                    if (admin) atualizar();
                    else System.out.println("Acesso negado. Apenas administradores podem atualizar usuarios.");
                    break;
                case 5:
                    if (admin) remover();
                    else System.out.println("Acesso negado. Apenas administradores podem remover usuarios.");
                    break;
                case 6:
                    if (admin) exibirEstatisticas();
                    else System.out.println("Acesso negado.");
                    break;
                case 0:
                    System.out.println("Voltando...");
                    break;
                default: 
                    System.out.println("Opcao invalida. Escolha entre 0 e 6.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao processar opcao: " + e.getMessage());
        }
    }

    /**
     * Cadastra um novo usuario
     */
    private void cadastrar() {
        System.out.println("\n--- CADASTRAR USUARIO ---");

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
            
            if (senha.length() < 4) {
                System.out.println("Erro: Senha deve ter pelo menos 4 caracteres.");
                return;
            }

            System.out.print("Tipo de Usuario (Admin/Apicultor): ");
            String tipoUsuario = scanner.nextLine();
            
            if (tipoUsuario == null || tipoUsuario.trim().isEmpty()) {
                System.out.println("Erro: Tipo de usuario nao pode ser vazio.");
                return;
            }

            boolean sucesso = controle.criarUsuario(nome, email, senha, tipoUsuario);

            if (sucesso) {
                System.out.println("\nUsuario cadastrado com sucesso!");
            } else {
                System.out.println("\nErro: " + controle.getUltimaMensagemErro());
            }
            
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar usuario: " + e.getMessage());
        }
    }

    /**
     * Lista todos os usuarios
     */
    private void listar() {
        System.out.println("\n--- LISTA DE USUARIOS ---");
        
        try {
            List<Pessoa> usuarios = controle.listar();

            if (usuarios == null || usuarios.isEmpty()) {
                System.out.println("Nenhum usuario cadastrado.");
                return;
            }
            
            System.out.printf("%-5s %-20s %-30s %-15s%n", "ID", "Nome", "Email", "Tipo");
            System.out.println("------------------------------------------------------------------------");
            
            for (Pessoa u : usuarios) {
                try {
                    System.out.printf("%-5d %-20s %-30s %-15s%n",
                            u.getId(), 
                            truncar(u.getNome(), 20), 
                            truncar(u.getEmail(), 30), 
                            u.getTipoUsuario());
                } catch (Exception e) {
                    System.out.println("Erro ao exibir usuario ID " + u.getId());
                }
            }
            
            System.out.println("\nTotal: " + usuarios.size() + " usuario(s)");
            
        } catch (Exception e) {
            System.out.println("Erro ao listar usuarios: " + e.getMessage());
        }
    }

    /**
     * Busca um usuario especifico
     */
    private void buscar() {
        System.out.println("\n--- BUSCAR USUARIO ---");
        System.out.println("1 - Buscar por ID");
        System.out.println("2 - Buscar por Email");
        System.out.println("3 - Buscar por Nome");
        System.out.println("4 - Buscar por Tipo");
        System.out.print("Escolha: ");

        try {
            String input = scanner.nextLine();
            
            if (input == null || input.trim().isEmpty() || !input.matches("\\d+")) {
                System.out.println("Erro: Digite um numero valido.");
                return;
            }
            
            int opcao = Integer.parseInt(input);

            switch (opcao) {
                case 1: buscarPorId(); break;
                case 2: buscarPorEmail(); break;
                case 3: buscarPorNome(); break;
                case 4: buscarPorTipo(); break;
                default: System.out.println("Opcao invalida.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro: Digite um numero valido.");
        } catch (Exception e) {
            System.out.println("Erro ao buscar: " + e.getMessage());
        }
    }

    private void buscarPorId() {
        try {
            System.out.print("ID do usuario: ");
            String idStr = scanner.nextLine();
            
            int id = Integer.parseInt(idStr);
            if (id <= 0) {
                System.out.println("Erro: ID deve ser um numero positivo.");
                return;
            }
            
            Pessoa usuario = controle.buscarPorId(id);
            
            if (usuario != null) {
                exibirUsuario(usuario);
            } else {
                System.out.println("Erro: Usuario nao encontrado.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro: ID deve ser um numero valido.");
        } catch (Exception e) {
            System.out.println("Erro ao buscar usuario: " + e.getMessage());
        }
    }

    private void buscarPorEmail() {
        try {
            System.out.print("Email do usuario: ");
            String email = scanner.nextLine();
            
            if (email == null || email.trim().isEmpty()) {
                System.out.println("Erro: Email nao pode ser vazio.");
                return;
            }
            
            Pessoa usuario = controle.buscarPorEmail(email);
            
            if (usuario != null) {
                exibirUsuario(usuario);
            } else {
                System.out.println("Erro: Usuario nao encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar por email: " + e.getMessage());
        }
    }

    private void buscarPorNome() {
        try {
            System.out.print("Nome do usuario: ");
            String nome = scanner.nextLine();
            
            if (nome == null || nome.trim().isEmpty()) {
                System.out.println("Erro: Nome nao pode ser vazio.");
                return;
            }
            
            Pessoa usuario = controle.buscarPorNome(nome);
            
            if (usuario != null) {
                exibirUsuario(usuario);
            } else {
                System.out.println("Erro: Usuario nao encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar por nome: " + e.getMessage());
        }
    }

    private void buscarPorTipo() {
        try {
            System.out.print("Tipo (Admin/Apicultor): ");
            String tipo = scanner.nextLine();
            
            if (tipo == null || tipo.trim().isEmpty()) {
                System.out.println("Erro: Tipo nao pode ser vazio.");
                return;
            }
            
            List<Pessoa> usuarios = controle.listarPorTipo(tipo);
            
            if (usuarios == null || usuarios.isEmpty()) {
                System.out.println("Nenhum usuario encontrado com este tipo.");
            } else {
                System.out.println("\n" + usuarios.size() + " usuario(s) encontrado(s):");
                for (Pessoa u : usuarios) {
                    exibirUsuario(u);
                    System.out.println("---");
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar por tipo: " + e.getMessage());
        }
    }

    /**
     * Atualiza um usuario existente
     */
    private void atualizar() {
        System.out.println("\n--- ATUALIZAR USUARIO ---");
        
        try {
            System.out.print("Email do usuario: ");
            String email = scanner.nextLine();
            
            if (email == null || email.trim().isEmpty()) {
                System.out.println("Erro: Email nao pode ser vazio.");
                return;
            }

            Pessoa usuario = controle.buscarPorEmail(email);

            if (usuario == null) {
                System.out.println("Erro: Usuario nao encontrado.");
                return;
            }

            System.out.println("\nDados atuais:");
            exibirUsuario(usuario);
            System.out.println("\n(Pressione Enter para manter o valor atual)");

            System.out.print("Novo nome [" + usuario.getNome() + "]: ");
            String nome = scanner.nextLine();
            if (nome != null && !nome.trim().isEmpty()) {
                usuario.setNome(nome);
            }

            System.out.print("Novo email [" + usuario.getEmail() + "]: ");
            String novoEmail = scanner.nextLine();
            if (novoEmail != null && !novoEmail.trim().isEmpty()) {
                usuario.setEmail(novoEmail);
            }

            System.out.print("Nova senha: ");
            String senha = scanner.nextLine();
            if (senha != null && !senha.trim().isEmpty()) {
                if (senha.length() < 4) {
                    System.out.println("Senha muito curta. Mantendo senha anterior.");
                } else {
                    usuario.setSenha(senha);
                }
            }

            System.out.print("Novo tipo [" + usuario.getTipoUsuario() + "]: ");
            String tipo = scanner.nextLine();
            if (tipo != null && !tipo.trim().isEmpty()) {
                usuario.setTipoUsuario(tipo);
            }

            boolean sucesso = controle.atualizar(usuario);
            
            if (sucesso) {
                System.out.println("\nUsuario atualizado com sucesso!");
            } else {
                System.out.println("\nErro: " + controle.getUltimaMensagemErro());
            }
            
        } catch (Exception e) {
            System.out.println("Erro ao atualizar usuario: " + e.getMessage());
        }
    }

    /**
     * Remove um usuario
     */
    private void remover() {
        System.out.println("\n--- REMOVER USUARIO ---");
        
        try {
            System.out.print("Email do usuario: ");
            String email = scanner.nextLine();
            
            if (email == null || email.trim().isEmpty()) {
                System.out.println("Erro: Email nao pode ser vazio.");
                return;
            }

            Pessoa usuario = controle.buscarPorEmail(email);
            
            if (usuario == null) {
                System.out.println("Erro: Usuario nao encontrado.");
                return;
            }

            // Impede que o usuario logado se remova
            if (usuario.getId() == usuarioLogado.getId()) {
                System.out.println("Erro: Voce nao pode remover seu proprio usuario.");
                return;
            }

            System.out.println("\nDados do usuario:");
            exibirUsuario(usuario);
            
            System.out.print("\nConfirma a remocao? (S/N): ");
            String confirmacao = scanner.nextLine();

            if (confirmacao == null || confirmacao.trim().isEmpty()) {
                System.out.println("Remocao cancelada.");
                return;
            }

            if (confirmacao.equalsIgnoreCase("S") || confirmacao.equalsIgnoreCase("SIM")) {
                boolean sucesso = controle.remover(email);
                
                if (sucesso) {
                    System.out.println("\nUsuario removido com sucesso!");
                } else {
                    System.out.println("\nErro: " + controle.getUltimaMensagemErro());
                }
            } else {
                System.out.println("Remocao cancelada.");
            }
            
        } catch (Exception e) {
            System.out.println("Erro ao remover usuario: " + e.getMessage());
        }
    }

    /**
     * Exibe estatisticas dos usuarios
     */
    private void exibirEstatisticas() {
        System.out.println("\n--- ESTATISTICAS ---");
        
        try {
            int totalUsuarios = controle.getTotalUsuarios();
            int totalAdmins = controle.listarPorTipo("Admin").size();
            int totalApicultores = controle.listarPorTipo("Apicultor").size();
            
            System.out.println("Total de usuarios: " + totalUsuarios);
            System.out.println("Administradores: " + totalAdmins);
            System.out.println("Apicultores: " + totalApicultores);
        } catch (Exception e) {
            System.out.println("Erro ao exibir estatisticas: " + e.getMessage());
        }
    }

    /**
     * Exibe os detalhes de um usuario
     */
    private void exibirUsuario(Pessoa u) {
        try {
            System.out.println("\nID: " + u.getId());
            System.out.println("Nome: " + u.getNome());
            System.out.println("Email: " + u.getEmail());
            System.out.println("Tipo: " + u.getTipoUsuario());
            if (usuarioLogado.getTipoUsuario().equalsIgnoreCase("Admin")) {
                System.out.println("Senha: " + u.getSenha());
            }
        } catch (Exception e) {
            System.out.println("Erro ao exibir detalhes: " + e.getMessage());
        }
    }

    /**
     * Trunca uma string para o tamanho especificado
     */
    private String truncar(String texto, int tamanho) {
        try {
            if (texto == null) return "";
            if (texto.length() <= tamanho) return texto;
            return texto.substring(0, tamanho - 3) + "...";
        } catch (Exception e) {
            return "";
        }
    }
}