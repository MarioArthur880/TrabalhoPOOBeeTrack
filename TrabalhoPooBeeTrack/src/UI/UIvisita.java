package UI;

import controle.ControleVisita;
import controle.ControleApiario;
import controle.Visita;
import controle.Apiario;
import java.util.List;
import java.util.Scanner;

public class UIvisita {
    private Scanner scanner;
    private ControleVisita controleVisita;
    private ControleApiario controleApiario;

    public UIvisita(Scanner scanner, ControleVisita controleVisita, ControleApiario controleApiario) {
        if (scanner == null || controleVisita == null || controleApiario == null) {
            throw new IllegalArgumentException("Parametros nao podem ser nulos");
        }
        this.scanner = scanner;
        this.controleVisita = controleVisita;
        this.controleApiario = controleApiario;
    }

    public void exibir() {
        int opcao;
        do {
            try {
                System.out.println("\n========================================");
                System.out.println("         GERENCIAMENTO DE VISITAS");
                System.out.println("========================================");
                System.out.println("1 - Registrar Visita");
                System.out.println("2 - Listar Visitas");
                System.out.println("3 - Atualizar Visita");
                System.out.println("4 - Remover Visita");
                System.out.println("0 - Voltar");
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
                System.out.println("Erro inesperado: " + e.getMessage());
                opcao = -1;
            }
        } while (opcao != 0);
    }

    private void processarOpcao(int opcao) {
        try {
            switch (opcao) {
                case 1: registrar(); break;
                case 2: listar(); break;
                case 3: atualizar(); break;
                case 4: remover(); break;
                case 0: System.out.println("\nVoltando ao menu anterior..."); break;
                default: System.out.println("Opcao invalida. Escolha entre 0 e 4.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao processar opcao: " + e.getMessage());
        }
    }

    private void registrar() {
        System.out.println("\n--- REGISTRAR VISITA ---");
        
        try {
            System.out.print("Data (dd/mm/aaaa): ");
            String data = scanner.nextLine();
            
            if (data == null || data.trim().isEmpty()) {
                System.out.println("Erro: Data e obrigatoria.");
                return;
            }
            
            if (!data.matches("\\d{2}/\\d{2}/\\d{4}")) {
                System.out.println("Erro: Formato de data invalido. Use dd/mm/aaaa");
                return;
            }

            System.out.print("ID do apiario visitado: ");
            String idApiarioStr = scanner.nextLine();
            int idApiario;
            
            try {
                idApiario = Integer.parseInt(idApiarioStr);
                if (idApiario <= 0) {
                    System.out.println("Erro: ID deve ser um numero positivo.");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: Digite um ID valido (numero inteiro).");
                return;
            }
            
            Apiario apiario = controleApiario.buscarPorId(idApiario);
            if (apiario == null) {
                System.out.println("Erro: Apiario nao encontrado com ID: " + idApiario);
                return;
            }

            System.out.print("Quantidade colhida (numero de melgueiras): ");
            String colheitaStr = scanner.nextLine();
            int colheita;
            
            try {
                colheita = Integer.parseInt(colheitaStr);
                if (colheita < 0) {
                    System.out.println("Erro: Quantidade nao pode ser negativa.");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: Digite um valor numerico valido.");
                return;
            }

            System.out.print("Tipo de visita (ex: inspecao, colheita): ");
            String tipoVisita = scanner.nextLine();
            
            if (tipoVisita == null || tipoVisita.trim().isEmpty()) {
                System.out.println("Erro: Tipo de visita e obrigatorio.");
                return;
            }

            boolean sucesso = controleVisita.criarVisita(data, apiario, colheita, tipoVisita);
            
            if (sucesso) {
                System.out.println("\nVisita registrada com sucesso!");
            } else {
                System.out.println("\nErro: Nao foi possivel registrar a visita.");
                System.out.println("  Verifique se todos os dados estao corretos.");
            }
            
        } catch (Exception e) {
            System.out.println("Erro ao registrar visita: " + e.getMessage());
        }
    }

    private void listar() {
        System.out.println("\n--- LISTA DE VISITAS ---");
        
        try {
            List<Visita> visitas = controleVisita.listar();

            if (visitas == null || visitas.isEmpty()) {
                System.out.println("Nenhuma visita registrada.");
                return;
            }
            
            System.out.printf("%-5s %-12s %-20s %-10s %-15s%n", "ID", "Data", "Apiario", "Colheita", "Tipo");
            System.out.println("--------------------------------------------------------------------------");
            
            for (Visita v : visitas) {
                try {
                    String nomeApiario = (v.getApiario() != null) ? v.getApiario().getNome() : "N/A";
                    System.out.printf("%-5d %-12s %-20s %-10d %-15s%n",
                            v.getId(), v.getData(), truncar(nomeApiario, 20), v.getColheita(), truncar(v.getTipoVisita(), 15));
                } catch (Exception e) {
                    System.out.println("Erro ao exibir visita ID " + v.getId());
                }
            }
            
        } catch (Exception e) {
            System.out.println("Erro ao listar visitas: " + e.getMessage());
        }
    }

    private void atualizar() {
        System.out.println("\n--- ATUALIZAR VISITA ---");
        
        try {
            System.out.print("ID da visita: ");
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
            
            Visita visita = controleVisita.buscarPorId(id);

            if (visita == null) {
                System.out.println("Erro: Visita nao encontrada com ID: " + id);
                return;
            }

            System.out.println("\nDados atuais:");
            exibirDetalhes(visita);
            System.out.println("\n(Pressione Enter para manter o valor atual)");

            System.out.print("Nova data (dd/mm/aaaa): ");
            String data = scanner.nextLine();
            if (data != null && !data.trim().isEmpty()) {
                if (data.matches("\\d{2}/\\d{2}/\\d{4}")) {
                    visita.setData(data);
                } else {
                    System.out.println("Formato de data invalido. Mantendo valor anterior.");
                }
            }

            System.out.print("Novo ID de apiario (Enter para manter): ");
            String idApiarioInput = scanner.nextLine();
            if (idApiarioInput != null && !idApiarioInput.trim().isEmpty()) {
                try {
                    int idApiario = Integer.parseInt(idApiarioInput);
                    Apiario novoApiario = controleApiario.buscarPorId(idApiario);
                    if (novoApiario != null) {
                        visita.setApiario(novoApiario);
                    } else {
                        System.out.println("Apiario nao encontrado. Mantido o anterior.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("ID invalido. Mantido o anterior.");
                }
            }

            System.out.print("Nova quantidade colhida (Enter para manter): ");
            String colheitaInput = scanner.nextLine();
            if (colheitaInput != null && !colheitaInput.trim().isEmpty()) {
                try {
                    int colheita = Integer.parseInt(colheitaInput);
                    if (colheita < 0) {
                        System.out.println("Valor negativo ignorado. Mantendo valor anterior.");
                    } else {
                        visita.setColheita(colheita);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Valor invalido. Mantendo valor anterior.");
                }
            }

            System.out.print("Novo tipo de visita (Enter para manter): ");
            String tipo = scanner.nextLine();
            if (tipo != null && !tipo.trim().isEmpty()) {
                visita.setTipoVisita(tipo);
            }

            boolean sucesso = controleVisita.atualizar(visita);
            
            if (sucesso) {
                System.out.println("\nVisita atualizada com sucesso!");
            } else {
                System.out.println("\nErro ao atualizar visita.");
                System.out.println("  Verifique se os dados estao corretos.");
            }
            
        } catch (Exception e) {
            System.out.println("Erro ao atualizar visita: " + e.getMessage());
        }
    }

    private void remover() {
        System.out.println("\n--- REMOVER VISITA ---");
        
        try {
            System.out.print("ID da visita: ");
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
            
            Visita visita = controleVisita.buscarPorId(id);
            
            if (visita == null) {
                System.out.println("Erro: Visita nao encontrada com ID: " + id);
                return;
            }
            
            System.out.println("\nDados da visita:");
            exibirDetalhes(visita);
            
            System.out.print("\nConfirma a remocao? (S/N): ");
            String confirmacao = scanner.nextLine();
            
            if (confirmacao == null || confirmacao.trim().isEmpty()) {
                System.out.println("Remocao cancelada.");
                return;
            }
            
            if (confirmacao.trim().equalsIgnoreCase("S")) {
                boolean sucesso = controleVisita.remover(id);
                if (sucesso) {
                    System.out.println("\nVisita removida com sucesso!");
                } else {
                    System.out.println("\nErro ao remover visita.");
                }
            } else {
                System.out.println("Remocao cancelada.");
            }
            
        } catch (Exception e) {
            System.out.println("Erro ao remover visita: " + e.getMessage());
        }
    }

    private void exibirDetalhes(Visita visita) {
        try {
            System.out.println("\nData: " + visita.getData());
            String nomeApiario = (visita.getApiario() != null) ? visita.getApiario().getNome() : "N/A";
            System.out.println("Apiario: " + nomeApiario);
            System.out.println("Quantidade colhida: " + visita.getColheita() + " melgueiras");
            System.out.println("Tipo de visita: " + visita.getTipoVisita());
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