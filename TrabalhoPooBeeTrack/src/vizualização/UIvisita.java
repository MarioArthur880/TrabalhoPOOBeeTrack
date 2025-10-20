package vizualização;

import controle.ControleVisita;
import controle.ControleApiario;
import modelo.Visita;
import modelo.Apiario;
import java.util.List;
import java.util.Scanner;

public class UIvisita {
    private Scanner scanner;
    private ControleVisita controleVisita;
    private ControleApiario controleApiario;

    public UIvisita(Scanner scanner, ControleVisita controleVisita, ControleApiario controleApiario) {
        this.scanner = scanner;
        this.controleVisita = controleVisita;
        this.controleApiario = controleApiario;
    }

    public void exibir() {
        int opcao;

        do {
            System.out.println("\n========================================");
            System.out.println("         GERENCIAMENTO DE VISITAS");
            System.out.println("========================================");
            System.out.println("1 - Registrar Visita");
            System.out.println("2 - Listar Visitas");
            System.out.println("3 - Atualizar Visita");
            System.out.println("4 - Remover Visita");
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
            case 1: registrar(); break;
            case 2: listar(); break;
            case 3: atualizar(); break;
            case 4: remover(); break;
            case 0: break;
            default: System.out.println("Opção inválida.");
        }
    }

    private void registrar() {
        System.out.println("\n--- REGISTRAR VISITA ---");

        System.out.print("Data (ex: 19/10/2025): ");
        String data = scanner.nextLine();

        System.out.print("ID do apiário visitado: ");
        int idApiario = Integer.parseInt(scanner.nextLine());
        Apiario apiario = controleApiario.buscarPorId(idApiario);

        if (apiario == null) {
            System.out.println("Apiário não encontrado.");
            return;
        }

        System.out.print("Quantidade colhida (número de melgueiras): ");
        int colheita = Integer.parseInt(scanner.nextLine());

        System.out.print("Tipo de visita (ex: inspeção, colheita): ");
        String tipoVisita = scanner.nextLine();

        boolean sucesso = controleVisita.criarVisita(data, apiario, colheita, tipoVisita);

        if (sucesso) {
            System.out.println("Visita registrada com sucesso.");
        } else {
            System.out.println("Dados inválidos. Visita não registrada.");
        }
    }

    private void listar() {
        System.out.println("\n--- LISTA DE VISITAS ---");
        List<Visita> visitas = controleVisita.listar();

        if (visitas.isEmpty()) {
            System.out.println("Nenhuma visita registrada.");
        } else {
            System.out.printf("%-5s %-12s %-20s %-10s %-15s%n", "ID", "Data", "Apiário", "Colheita", "Tipo");
            System.out.println("--------------------------------------------------------------------------");
            for (Visita v : visitas) {
                String nomeApiario = v.getApiario() != null ? v.getApiario().getNome() : "N/A";
                System.out.printf("%-5d %-12s %-20s %-10d %-15s%n",
                        v.getId(), v.getData(), nomeApiario, v.getColheita(), v.getTipoVisita());
            }
        }
    }

    private void atualizar() {
        System.out.println("\n--- ATUALIZAR VISITA ---");
        System.out.print("ID da visita: ");
        int id = Integer.parseInt(scanner.nextLine());

        Visita visita = controleVisita.buscarPorId(id);

        if (visita == null) {
            System.out.println("Visita não encontrada.");
            return;
        }

        System.out.print("Nova data (Enter para manter): ");
        String data = scanner.nextLine();
        if (!data.trim().isEmpty()) {
            visita.setData(data);
        }

        System.out.print("Novo ID de apiário (Enter para manter): ");
        String idApiarioInput = scanner.nextLine();
        if (!idApiarioInput.trim().isEmpty()) {
            int idApiario = Integer.parseInt(idApiarioInput);
            Apiario novoApiario = controleApiario.buscarPorId(idApiario);
            if (novoApiario != null) {
                visita.setApiario(novoApiario);
            } else {
                System.out.println("Apiário não encontrado. Mantido o anterior.");
            }
        }

        System.out.print("Nova quantidade colhida (Enter para manter): ");
        String colheitaInput = scanner.nextLine();
        if (!colheitaInput.trim().isEmpty()) {
            int colheita = Integer.parseInt(colheitaInput);
            visita.setColheita(colheita);
        }

        System.out.print("Novo tipo de visita (Enter para manter): ");
        String tipo = scanner.nextLine();
        if (!tipo.trim().isEmpty()) {
            visita.setTipoVisita(tipo);
        }

        System.out.println("Visita atualizada com sucesso.");
    }

    private void remover() {
        System.out.println("\n--- REMOVER VISITA ---");
        System.out.print("ID da visita: ");
        int id = Integer.parseInt(scanner.nextLine());

        if (controleVisita.remover(id)) {
            System.out.println("Visita removida com sucesso.");
        } else {
            System.out.println("Visita não encontrada.");
        }
    }
}
