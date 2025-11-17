package UI;

import controle.Apiario;
import controle.ControleApiario;
import java.util.List;
import java.util.Scanner;

public class UIapiario {
    private Scanner scanner;
    private ControleApiario controle;

    public UIapiario(Scanner scanner, ControleApiario controle) {
        if (scanner == null || controle == null) {
            throw new IllegalArgumentException("Parametros nao podem ser nulos");
        }
        this.scanner = scanner;
        this.controle = controle;
    }

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

    private void exibirMenu() {
        System.out.println("\n========================================");
        System.out.println("        GERENCIAMENTO DE APIARIOS");
        System.out.println("========================================");
        System.out.println("1 - Cadastrar Apiario");
        System.out.println("2 - Listar Apiarios");
        System.out.println("3 - Buscar Apiario");
        System.out.println("4 - Atualizar Apiario");
        System.out.println("5 - Remover Apiario");
        System.out.println("6 - Estatisticas");
        System.out.println("0 - Voltar");
        System.out.println("========================================");
        System.out.print("Escolha uma opcao: ");
    }

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

    private void processarOpcao(int opcao) {
        try {
            switch (opcao) {
                case 1: cadastrar(); break;
                case 2: listar(); break;
                case 3: buscar(); break;
                case 4: atualizar(); break;
                case 5: remover(); break;
                case 6: exibirEstatisticas(); break;
                case 0: System.out.println("Voltando..."); break;
                default: System.out.println("Opcao invalida. Escolha entre 0 e 6.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao processar opcao: " + e.getMessage());
        }
    }

    private void cadastrar() {
        System.out.println("\n--- CADASTRAR APIARIO ---");

        try {
            System.out.print("Nome: ");
            String nome = scanner.nextLine();
            
            if (nome == null || nome.trim().isEmpty()) {
                System.out.println("Erro: Nome nao pode ser vazio.");
                return;
            }

            System.out.print("Raca das abelhas: ");
            String raca = scanner.nextLine();
            
            if (raca == null || raca.trim().isEmpty()) {
                System.out.println("Erro: Raca nao pode ser vazia.");
                return;
            }

            System.out.print("Local: ");
            String local = scanner.nextLine();
            
            if (local == null || local.trim().isEmpty()) {
                System.out.println("Erro: Local nao pode ser vazio.");
                return;
            }

            System.out.print("Quantidade de caixas: ");
            String qntCaixasStr = scanner.nextLine();
            int qntCaixas;
            
            try {
                qntCaixas = Integer.parseInt(qntCaixasStr);
                if (qntCaixas <= 0) {
                    System.out.println("Erro: Quantidade deve ser maior que zero.");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: Quantidade de caixas deve ser um numero valido.");
                return;
            }

            boolean sucesso = controle.criarApiario(nome, raca, local, qntCaixas);

            if (sucesso) {
                System.out.println("\nApiario cadastrado com sucesso!");
            } else {
                System.out.println("\nErro: " + controle.getUltimaMensagemErro());
            }
            
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar apiario: " + e.getMessage());
        }
    }

    private void listar() {
        System.out.println("\n--- LISTA DE APIARIOS ---");
        
        try {
            List<Apiario> apiarios = controle.listar();

            if (apiarios == null || apiarios.isEmpty()) {
                System.out.println("Nenhum apiario cadastrado.");
                return;
            }
            
            System.out.printf("%-5s %-20s %-15s %-20s %-10s%n", 
                "ID", "Nome", "Raca", "Local", "Caixas");
            System.out.println("----------------------------------------------------------------------");
            
            for (Apiario a : apiarios) {
                try {
                    System.out.printf("%-5d %-20s %-15s %-20s %-10d%n",
                        a.getId(), 
                        truncar(a.getNome(), 20), 
                        truncar(a.getRaca(), 15), 
                        truncar(a.getLocal(), 20), 
                        a.getQntCaixas());
                } catch (Exception e) {
                    System.out.println("Erro ao exibir apiario ID " + a.getId());
                }
            }
            
            System.out.println("\nTotal: " + apiarios.size() + " apiario(s)");
            
        } catch (Exception e) {
            System.out.println("Erro ao listar apiarios: " + e.getMessage());
        }
    }

    private void buscar() {
        System.out.println("\n--- BUSCAR APIARIO ---");
        System.out.println("1 - Buscar por ID");
        System.out.println("2 - Buscar por Nome");
        System.out.println("3 - Buscar por Local");
        System.out.println("4 - Buscar por Raca");
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
                case 2: buscarPorNome(); break;
                case 3: buscarPorLocal(); break;
                case 4: buscarPorRaca(); break;
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
            System.out.print("ID do apiario: ");
            String idStr = scanner.nextLine();
            
            int id = Integer.parseInt(idStr);
            if (id <= 0) {
                System.out.println("Erro: ID deve ser um numero positivo.");
                return;
            }
            
            Apiario apiario = controle.buscarPorId(id);
            
            if (apiario != null) {
                exibirApiario(apiario);
            } else {
                System.out.println("Erro: Apiario nao encontrado.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro: ID deve ser um numero valido.");
        } catch (Exception e) {
            System.out.println("Erro ao buscar apiario: " + e.getMessage());
        }
    }

    private void buscarPorNome() {
        try {
            System.out.print("Nome do apiario: ");
            String nome = scanner.nextLine();
            
            if (nome == null || nome.trim().isEmpty()) {
                System.out.println("Erro: Nome nao pode ser vazio.");
                return;
            }
            
            List<Apiario> apiarios = controle.buscarTodosPorNome(nome);
            
            if (apiarios == null || apiarios.isEmpty()) {
                System.out.println("Nenhum apiario encontrado com este nome.");
            } else {
                System.out.println("\n" + apiarios.size() + " apiario(s) encontrado(s):");
                for (Apiario a : apiarios) {
                    exibirApiario(a);
                    System.out.println("---");
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar por nome: " + e.getMessage());
        }
    }

    private void buscarPorLocal() {
        try {
            System.out.print("Local: ");
            String local = scanner.nextLine();
            
            if (local == null || local.trim().isEmpty()) {
                System.out.println("Erro: Local nao pode ser vazio.");
                return;
            }
            
            List<Apiario> apiarios = controle.listarPorLocal(local);
            
            if (apiarios == null || apiarios.isEmpty()) {
                System.out.println("Nenhum apiario encontrado neste local.");
            } else {
                System.out.println("\n" + apiarios.size() + " apiario(s) encontrado(s):");
                for (Apiario a : apiarios) {
                    exibirApiario(a);
                    System.out.println("---");
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar por local: " + e.getMessage());
        }
    }

    private void buscarPorRaca() {
        try {
            System.out.print("Raca: ");
            String raca = scanner.nextLine();
            
            if (raca == null || raca.trim().isEmpty()) {
                System.out.println("Erro: Raca nao pode ser vazia.");
                return;
            }
            
            List<Apiario> apiarios = controle.listarPorRaca(raca);
            
            if (apiarios == null || apiarios.isEmpty()) {
                System.out.println("Nenhum apiario encontrado com esta raca.");
            } else {
                System.out.println("\n" + apiarios.size() + " apiario(s) encontrado(s):");
                for (Apiario a : apiarios) {
                    exibirApiario(a);
                    System.out.println("---");
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar por raca: " + e.getMessage());
        }
    }

    private void atualizar() {
        System.out.println("\n--- ATUALIZAR APIARIO ---");
        
        try {
            System.out.print("ID do apiario: ");
            String idStr = scanner.nextLine();
            
            int id;
            try {
                id = Integer.parseInt(idStr);
                if (id <= 0) {
                    System.out.println("Erro: ID deve ser um numero positivo.");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: Digite um ID valido (numero inteiro).");
                return;
            }

            Apiario apiario = controle.buscarPorId(id);

            if (apiario == null) {
                System.out.println("Erro: Apiario nao encontrado.");
                return;
            }

            System.out.println("\nDados atuais:");
            exibirApiario(apiario);
            System.out.println("\n(Pressione Enter para manter o valor atual)");

            System.out.print("Novo nome [" + apiario.getNome() + "]: ");
            String nome = scanner.nextLine();
            if (nome != null && !nome.trim().isEmpty()) {
                apiario.setNome(nome);
            }

            System.out.print("Nova raca [" + apiario.getRaca() + "]: ");
            String raca = scanner.nextLine();
            if (raca != null && !raca.trim().isEmpty()) {
                apiario.setRaca(raca);
            }

            System.out.print("Novo local [" + apiario.getLocal() + "]: ");
            String local = scanner.nextLine();
            if (local != null && !local.trim().isEmpty()) {
                apiario.setLocal(local);
            }

            System.out.print("Nova quantidade de caixas [" + apiario.getQntCaixas() + "]: ");
            String caixasInput = scanner.nextLine();
            if (caixasInput != null && !caixasInput.trim().isEmpty()) {
                try {
                    int qntCaixas = Integer.parseInt(caixasInput);
                    if (qntCaixas <= 0) {
                        System.out.println("Valor invalido. Mantendo valor anterior.");
                    } else {
                        apiario.setQntCaixas(qntCaixas);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Valor invalido. Mantendo valor anterior.");
                }
            }

            boolean sucesso = controle.atualizar(apiario);
            
            if (sucesso) {
                System.out.println("\nApiario atualizado com sucesso!");
            } else {
                System.out.println("\nErro: " + controle.getUltimaMensagemErro());
            }
            
        } catch (Exception e) {
            System.out.println("Erro ao atualizar apiario: " + e.getMessage());
        }
    }

    private void remover() {
        System.out.println("\n--- REMOVER APIARIO ---");
        
        try {
            System.out.print("ID do apiario: ");
            String idStr = scanner.nextLine();
            
            int id;
            try {
                id = Integer.parseInt(idStr);
                if (id <= 0) {
                    System.out.println("Erro: ID deve ser um numero positivo.");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: Digite um ID valido (numero inteiro).");
                return;
            }

            Apiario apiario = controle.buscarPorId(id);
            
            if (apiario == null) {
                System.out.println("Erro: Apiario nao encontrado.");
                return;
            }

            System.out.println("\nDados do apiario:");
            exibirApiario(apiario);
            
            System.out.print("\nConfirma a remocao? (S/N): ");
            String confirmacao = scanner.nextLine();

            if (confirmacao == null || confirmacao.trim().isEmpty()) {
                System.out.println("Remocao cancelada.");
                return;
            }

            if (confirmacao.equalsIgnoreCase("S") || confirmacao.equalsIgnoreCase("SIM")) {
                boolean sucesso = controle.remover(id);
                
                if (sucesso) {
                    System.out.println("\nApiario removido com sucesso!");
                } else {
                    System.out.println("\nErro: " + controle.getUltimaMensagemErro());
                }
            } else {
                System.out.println("Remocao cancelada.");
            }
            
        } catch (Exception e) {
            System.out.println("Erro ao remover apiario: " + e.getMessage());
        }
    }

    private void exibirEstatisticas() {
        System.out.println("\n--- ESTATISTICAS ---");
        
        try {
            int totalApiarios = controle.getTotalApiarios();
            int totalCaixas = controle.getTotalCaixas();
            
            System.out.println("Total de apiarios: " + totalApiarios);
            System.out.println("Total de caixas: " + totalCaixas);
            
            if (totalApiarios > 0) {
                double mediaCaixas = (double) totalCaixas / totalApiarios;
                System.out.printf("Media de caixas por apiario: %.2f%n", mediaCaixas);
            } else {
                System.out.println("Nenhum apiario cadastrado para calcular estatisticas.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao exibir estatisticas: " + e.getMessage());
        }
    }

    private void exibirApiario(Apiario a) {
        try {
            System.out.println("\nID: " + a.getId());
            System.out.println("Nome: " + a.getNome());
            System.out.println("Raca: " + a.getRaca());
            System.out.println("Local: " + a.getLocal());
            System.out.println("Caixas: " + a.getQntCaixas());
        } catch (Exception e) {
            System.out.println("Erro ao exibir detalhes: " + e.getMessage());
        }
    }

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
