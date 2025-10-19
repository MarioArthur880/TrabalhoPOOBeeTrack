package vizualização;

import controle.ControleUsuario;
import modelo.Usuario;
import java.util.List;
import java.util.Scanner;

public class UIusuario {
    private Scanner scanner;
    private ControleUsuario controle;
    
    public UIusuario(Scanner scanner, ControleUsuario controle) {
        this.scanner = scanner;
        this.controle = controle;
    }
    
    public void exibir() {
        int opcao;
        
        do {
            System.out.println("\n========================================");
            System.out.println("      GERENCIAMENTO DE USUÁRIOS");
            System.out.println("========================================");
            System.out.println("1 - Cadastrar Usuário");
            System.out.println("2 - Listar Usuários");
            System.out.println("3 - Buscar Usuário por Email");
            System.out.println("4 - Atualizar Usuário");
            System.out.println("5 - Remover Usuário");
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
        switch (opcao) {
            case 1: cadastrar(); break;
            case 2: listar(); break;
            case 3: buscar(); break;
            case 4: atualizar(); break;
            case 5: remover(); break;
            case 0: break;
            default: System.out.println("Opção inválida.");
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
        
        Usuario usuario = Usuario.criarUsuario(nome, email, senha, tipoUsuario);
        
        if (usuario != null) {
            controle.adicionar(usuario);
            System.out.println("Usuário cadastrado com sucesso.");
        } else {
            System.out.println("Dados inválidos. Usuário não cadastrado.");
        }
    }
    
    private void listar() {
        System.out.println("\n--- LISTA DE USUÁRIOS ---");
        List<Usuario> usuarios = controle.listar();
        
        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário cadastrado.");
        } else {
            for (Usuario u : usuarios) {
                exibirUsuario(u);
            }
        }
    }
    
    private void buscar() {
        System.out.println("\n--- BUSCAR USUÁRIO ---");
        System.out.print("Email: ");
        String email = scanner.nextLine();
        
        Usuario usuario = controle.buscarPorEmail(email);
        
        if (usuario != null) {
            exibirUsuario(usuario);
        } else {
            System.out.println("Usuário não encontrado.");
        }
    }
    
    private void atualizar() {
        System.out.println("\n--- ATUALIZAR USUÁRIO ---");
        System.out.print("Email do usuário: ");
        String email = scanner.nextLine();
        
        Usuario usuario = controle.buscarPorEmail(email);
        
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
        
        System.out.println("Usuário atualizado com sucesso.");
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
    
    private void exibirUsuario(Usuario u) {
        System.out.println("\n----------------------------------------");
        System.out.println("Nome: " + u.getNome());
        System.out.println("Email: " + u.getEmail());
        System.out.println("Tipo: " + u.getTipoUsuario());
        System.out.println("----------------------------------------");
    }
}