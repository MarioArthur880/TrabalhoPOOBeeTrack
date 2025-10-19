package vizualização;

import controle.ControleApiario;
import modelo.Apiario;
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
            System.out.println("3 - Buscar Apiário por ID");
            System.out.println("4 - Atualizar Apiário");
            System.out.println("5 - Remover Apiário");
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
        System.out.println("\n--- CADASTRAR APIÁRIO ---");

        System.out.print("ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Raça das abelhas: ");
        String raca = scanner.nextLine();

        System.out.print("Local: ");
        String local = scanner.nextLine();

        System.out.print("Quantidade de caixas: ");
        int qntCaixas = Integer.parseInt(scanner.nextLine());

        Apiario apiario = Apiario.criarApiario(id, nome, raca, local, qntCaixas);

        if (apiario != null) {
            controle.adicionar(apiario);
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
            for (Apiario a : apiarios) {
                exibirApiario(a);
            }
        }
    }

    private void buscar() {
        System.out.println("\n--- BUSCAR APIÁRIO ---");
        System.out.print("ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        Apiario apiario = controle.buscarPorId(id);

        if (apiario != null) {
            exibirApiario(apiario);
        } else {
            System.out.println("Apiário não encontrado.");
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

        System.out.println("Apiário atualizado com sucesso.");
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

    private void exibirApiario(Apiario a) {
        System.out.println("\n----------------------------------------");
        System.out.println("ID: " + a.getId());
        System.out.println("Nome: " + a.getNome());
        System.out.println("Raça: " + a.getRaca());
        System.out.println("Local: " + a.getLocal());
        System.out.println("Caixas: " + a.getQntCaixas());
        System.out.println("----------------------------------------");
    }
}
