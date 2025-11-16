package UI;

import controle.Apiario;
import controle.ControleApiario;
import java.util.List;
import java.util.Scanner;

/**
 * Classe de interface com o usuário para gerenciamento de apiários
 */
public class UIapiario {
    private Scanner scanner;
    private ControleApiario controle;

    public UIapiario(Scanner scanner, ControleApiario controle) {
        this.scanner = scanner;
        this.controle = controle;
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
        System.out.println("        GERENCIAMENTO DE APIÁRIOS");
        System.out.println("========================================");
        System.out.println("1 - Cadastrar Apiário");
        System.out.println("2 - Listar Apiários");
        System.out.println("3 - Buscar Apiário");
        System.out.println("4 - Atualizar Apiário");
        System.out.println("5 - Remover Apiário");
        System.out.println("6 - Estatísticas");
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
        switch (opcao) {
            case 1: cadastrar(); break;
            case 2: listar(); break;
            case 3: buscar(); break;
            case 4: atualizar(); break;
            case 5: remover(); break;
            case 6: exibirEstatisticas(); break;
            case 0: System.out.println("Voltando..."); break;
            default: System.out.println("Opção inválida.");
        }
    }

    /**
     * Cadastra um novo apiário
     */
    private void cadastrar() {
        System.out.println("\n--- CADASTRAR APIÁRIO ---");

        try {
            System.out.print("Nome: ");
            String nome = scanner.nextLine();

            System.out.print("Raça das abelhas: ");
            String raca = scanner.nextLine();

            System.out.print("Local: ");
            String local = scanner.nextLine();

            System.out.print("Quantidade de caixas: ");
            int qntCaixas = Integer.parseInt(scanner.nextLine());

            boolean sucesso = controle.criarApiario(nome, raca, local, qntCaixas);

            if (sucesso) {
                System.out.println("✓ Apiário cadastrado com sucesso!");
            } else {
                System.out.println("✗ " + controle.getUltimaMensagemErro());
            }
            
        } catch (NumberFormatException e) {
            System.out.println("✗ Quantidade de caixas deve ser um número válido.");
        } catch (Exception e) {
            System.out.println("✗ Erro inesperado ao cadastrar apiário.");
        }
    }

    /**
     * Lista todos os apiários
     */
    private void listar() {
        System.out.println("\n--- LISTA DE APIÁRIOS ---");
        List<Apiario> apiarios = controle.listar();

        if (apiarios.isEmpty()) {
            System.out.println("Nenhum apiário cadastrado.");
        } else {
            System.out.printf("%-5s %-20s %-15s %-20s %-10s%n", 
                "ID", "Nome", "Raça", "Local", "Caixas");
            System.out.println("----------------------------------------------------------------------");
            
            for (Apiario a : apiarios) {
                System.out.printf("%-5d %-20s %-15s %-20s %-10d%n",
                    a.getId(), 
                    truncar(a.getNome(), 20), 
                    truncar(a.getRaca(), 15), 
                    truncar(a.getLocal(), 20), 
                    a.getQntCaixas());
            }
            
            System.out.println("\nTotal: " + apiarios.size() + " apiário(s)");
        }
    }

    /**
     * Busca um apiário específico
     */
    private void buscar() {
        System.out.println("\n--- BUSCAR APIÁRIO ---");
        System.out.println("1 - Buscar por ID");
        System.out.println("2 - Buscar por Nome");
        System.out.println("3 - Buscar por Local");
        System.out.println("4 - Buscar por Raça");
        System.out.print("Escolha: ");

        try {
            int opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1: buscarPorId(); break;
                case 2: buscarPorNome(); break;
                case 3: buscarPorLocal(); break;
                case 4: buscarPorRaca(); break;
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
            System.out.print("ID do apiário: ");
            int id = Integer.parseInt(scanner.nextLine());
            
            Apiario apiario = controle.buscarPorId(id);
            
            if (apiario != null) {
                exibirApiario(apiario);
            } else {
                System.out.println("✗ Apiário não encontrado.");
            }
        } catch (NumberFormatException e) {
            System.out.println("✗ ID deve ser um número válido.");
        }
    }

    private void buscarPorNome() {
        System.out.print("Nome do apiário: ");
        String nome = scanner.nextLine();
        
        List<Apiario> apiarios = controle.buscarTodosPorNome(nome);
        
        if (apiarios.isEmpty()) {
            System.out.println("✗ Nenhum apiário encontrado com este nome.");
        } else {
            System.out.println("\n✓ " + apiarios.size() + " apiário(s) encontrado(s):");
            for (Apiario a : apiarios) {
                exibirApiario(a);
                System.out.println("---");
            }
        }
    }

    private void buscarPorLocal() {
        System.out.print("Local: ");
        String local = scanner.nextLine();
        
        List<Apiario> apiarios = controle.listarPorLocal(local);
        
        if (apiarios.isEmpty()) {
            System.out.println("✗ Nenhum apiário encontrado neste local.");
        } else {
            System.out.println("\n✓ " + apiarios.size() + " apiário(s) encontrado(s):");
            for (Apiario a : apiarios) {
                exibirApiario(a);
                System.out.println("---");
            }
        }
    }

    private void buscarPorRaca() {
        System.out.print("Raça: ");
        String raca = scanner.nextLine();
        
        List<Apiario> apiarios = controle.listarPorRaca(raca);
        
        if (apiarios.isEmpty()) {
            System.out.println("✗ Nenhum apiário encontrado com esta raça.");
        } else {
            System.out.println("\n✓ " + apiarios.size() + " apiário(s) encontrado(s):");
            for (Apiario a : apiarios) {
                exibirApiario(a);
                System.out.println("---");
            }
        }
    }

    /**
     * Atualiza um apiário existente
     */
    private void atualizar() {
        System.out.println("\n--- ATUALIZAR APIÁRIO ---");
        
        try {
            System.out.print("ID do apiário: ");
            int id = Integer.parseInt(scanner.nextLine());

            Apiario apiario = controle.buscarPorId(id);

            if (apiario == null) {
                System.out.println("✗ Apiário não encontrado.");
                return;
            }

            System.out.println("\nDados atuais:");
            exibirApiario(apiario);
            System.out.println("\n(Pressione Enter para manter o valor atual)");

            System.out.print("Novo nome [" + apiario.getNome() + "]: ");
            String nome = scanner.nextLine();
            if (!nome.trim().isEmpty()) {
                apiario.setNome(nome);
            }

            System.out.print("Nova raça [" + apiario.getRaca() + "]: ");
            String raca = scanner.nextLine();
            if (!raca.trim().isEmpty()) {
                apiario.setRaca(raca);
            }

            System.out.print("Novo local [" + apiario.getLocal() + "]: ");
            String local = scanner.nextLine();
            if (!local.trim().isEmpty()) {
                apiario.setLocal(local);
            }

            System.out.print("Nova quantidade de caixas [" + apiario.getQntCaixas() + "]: ");
            String caixasInput = scanner.nextLine();
            if (!caixasInput.trim().isEmpty()) {
                int qntCaixas = Integer.parseInt(caixasInput);
                apiario.setQntCaixas(qntCaixas);
            }

            boolean sucesso = controle.atualizar(apiario);
            
            if (sucesso) {
                System.out.println("✓ Apiário atualizado com sucesso!");
            } else {
                System.out.println("✗ " + controle.getUltimaMensagemErro());
            }
            
        } catch (NumberFormatException e) {
            System.out.println("✗ Valor numérico inválido.");
        } catch (Exception e) {
            System.out.println("✗ Erro inesperado ao atualizar apiário.");
        }
    }

    /**
     * Remove um apiário
     */
    private void remover() {
        System.out.println("\n--- REMOVER APIÁRIO ---");
        
        try {
            System.out.print("ID do apiário: ");
            int id = Integer.parseInt(scanner.nextLine());

            Apiario apiario = controle.buscarPorId(id);
            
            if (apiario == null) {
                System.out.println("✗ Apiário não encontrado.");
                return;
            }

            exibirApiario(apiario);
            System.out.print("\nConfirma a remoção? (S/N): ");
            String confirmacao = scanner.nextLine();

            if (confirmacao.equalsIgnoreCase("S") || confirmacao.equalsIgnoreCase("SIM")) {
                boolean sucesso = controle.remover(id);
                
                if (sucesso) {
                    System.out.println("✓ Apiário removido com sucesso!");
                } else {
                    System.out.println("✗ " + controle.getUltimaMensagemErro());
                }
            } else {
                System.out.println("Remoção cancelada.");
            }
            
        } catch (NumberFormatException e) {
            System.out.println("✗ ID deve ser um número válido.");
        } catch (Exception e) {
            System.out.println("✗ Erro inesperado ao remover apiário.");
        }
    }

    /**
     * Exibe estatísticas dos apiários
     */
    private void exibirEstatisticas() {
        System.out.println("\n--- ESTATÍSTICAS ---");
        
        int totalApiarios = controle.getTotalApiarios();
        int totalCaixas = controle.getTotalCaixas();
        
        System.out.println("Total de apiários: " + totalApiarios);
        System.out.println("Total de caixas: " + totalCaixas);
        
        if (totalApiarios > 0) {
            double mediaCaixas = (double) totalCaixas / totalApiarios;
            System.out.printf("Média de caixas por apiário: %.2f%n", mediaCaixas);
        } else {
            System.out.println("Nenhum apiário cadastrado para calcular estatísticas.");
        }
    }

    /**
     * Exibe os detalhes de um apiário
     */
    private void exibirApiario(Apiario a) {
        System.out.println("\nID: " + a.getId());
        System.out.println("Nome: " + a.getNome());
        System.out.println("Raça: " + a.getRaca());
        System.out.println("Local: " + a.getLocal());
        System.out.println("Caixas: " + a.getQntCaixas());
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