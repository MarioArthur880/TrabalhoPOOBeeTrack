package UI;

import controle.Apiario;
import controle.ControleApiario;
import java.util.List;
import java.util.Scanner;

public class UIapiario {
    private Scanner scanner;
    private ControleApiario controle;

    public UIapiario(Scanner scanner, ControleApiario controle) {
        this.scanner = scanner;
        this.controle = controle;
    }

    public void exibir() {
        int opcao;

        do {
            System.out.println("\n========================================");
            System.out.println("        GERENCIAMENTO DE APIÁRIOS");
            System.out.println("========================================");
            System.out.println("1 - Cadastrar Apiário");
            System.out.println("2 - Listar Apiários");
            System.out.println("3 - Atualizar Apiário");
            System.out.println("4 - Remover Apiário");
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
            case 3: atualizar(); break;
            case 4: remover(); break;
            case 0: break;
            default: System.out.println("Opção inválida.");
        }
    }

    private void cadastrar() {
        System.out.println("\n--- CADASTRAR APIÁRIO ---");

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
            System.out.println("Apiário cadastrado com sucesso.");
        } else {
            System.out.println("Dados inválidos. Apiário não cadastrado.");
        }
    }

    private void listar() {
        System.out.println("\n--- LISTA DE APIÁRIOS ---");
        List<Apiario> apiarios = controle.listar();

        if (apiarios.isEmpty()) {
            System.out.println("Nenhum apiário cadastrado.");
        } else {
            System.out.printf("%-5s %-20s %-15s %-20s %-10s%n", "ID", "Nome", "Raça", "Local", "Caixas");
            System.out.println("--------------------------------------------------------------------------");
            for (Apiario a : apiarios) {
                System.out.printf("%-5d %-20s %-15s %-20s %-10d%n",
                        a.getId(), a.getNome(), a.getRaca(), a.getLocal(), a.getQntCaixas());
            }
        }
    }

    private void atualizar() {
        System.out.println("\n--- ATUALIZAR APIÁRIO ---");
        System.out.print("ID do apiário: ");
        int id = Integer.parseInt(scanner.nextLine());

        Apiario apiario = controle.buscarPorId(id);

        if (apiario == null) {
            System.out.println("Apiário não encontrado.");
            return;
        }

        System.out.print("Novo nome (Enter para manter): ");
        String nome = scanner.nextLine();
        if (!nome.trim().isEmpty()) {
            apiario.setNome(nome);
        }

        System.out.print("Nova raça (Enter para manter): ");
        String raca = scanner.nextLine();
        if (!raca.trim().isEmpty()) {
            apiario.setRaca(raca);
        }

        System.out.print("Novo local (Enter para manter): ");
        String local = scanner.nextLine();
        if (!local.trim().isEmpty()) {
            apiario.setLocal(local);
        }

        System.out.print("Nova quantidade de caixas (Enter para manter): ");
        String caixasInput = scanner.nextLine();
        if (!caixasInput.trim().isEmpty()) {
            int qntCaixas = Integer.parseInt(caixasInput);
            apiario.setQntCaixas(qntCaixas);
        }

        boolean sucesso = controle.atualizar(apiario);
        if (sucesso) {
            System.out.println("Apiário atualizado com sucesso.");
        } else {
            System.out.println("Erro ao atualizar apiário.");
        }
    }

    private void remover() {
        System.out.println("\n--- REMOVER APIÁRIO ---");
        System.out.print("ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        if (controle.remover(id)) {
            System.out.println("Apiário removido com sucesso.");
        } else {
            System.out.println("Apiário não encontrado.");
        }
    }
}