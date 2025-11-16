package UI;

import controle.ControleMelDeTerceiros;
import controle.MelDeTerceiros;
import java.util.List;
import java.util.Scanner;

public class UImeldeterceiros {
    private Scanner scanner;
    private ControleMelDeTerceiros controle;

    public UImeldeterceiros(Scanner scanner, ControleMelDeTerceiros controle) {
        this.scanner = scanner;
        this.controle = controle;
    }

    public void exibir() {
        int opcao;
        do {
            System.out.println("\n========================================");
            System.out.println("      GERENCIAMENTO DE MEL DE TERCEIROS");
            System.out.println("========================================");
            System.out.println("1 - Registrar Recebimento");
            System.out.println("2 - Listar Recebimentos");
            System.out.println("3 - Atualizar Recebimento");
            System.out.println("4 - Remover Recebimento");
            System.out.println("5 - Relatórios");
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
            case 5: exibirRelatorios(); break;
            case 0: break;
            default: System.out.println("Opção inválida.");
        }
    }

    private void registrar() {
        System.out.println("\n--- REGISTRAR RECEBIMENTO DE MEL ---");
        
        try {
            System.out.print("Nome do produtor: ");
            String produtor = scanner.nextLine();
            
            System.out.print("Peso antes da extração (kg): ");
            double pesoAntes = Double.parseDouble(scanner.nextLine());
            
            System.out.print("Peso depois da extração (kg): ");
            double pesoDepois = Double.parseDouble(scanner.nextLine());
            
            System.out.print("Data (dd/mm/aaaa): ");
            String data = scanner.nextLine();
            
            boolean sucesso = controle.criarMelDeTerceiros(produtor, pesoAntes, pesoDepois, data);
            
            if (sucesso) {
                double melExtraido = pesoAntes - pesoDepois;
                double nossaParte = melExtraido * 0.20; // 20%nossa parte
                System.out.println("\n✓ Recebimento registrado com sucesso!");
                System.out.printf("  Mel extraído: %.2f kg%n", melExtraido);
                System.out.printf("  Nossa parte (20%%): %.2f kg%n", nossaParte);
                System.out.printf("  Parte do produtor (80%%): %.2f kg%n", melExtraido - nossaParte);
            } else {
                System.out.println("\n✗ Dados inválidos. Recebimento não registrado.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro: valores numéricos inválidos.");
        } catch (Exception e) {
            System.out.println("Erro ao registrar recebimento: " + e.getMessage());
        }
    }

    private void listar() {
        System.out.println("\n--- LISTA DE RECEBIMENTOS ---");
        List<MelDeTerceiros> lista = controle.listar();
        
        if (lista.isEmpty()) {
            System.out.println("Nenhum recebimento registrado.");
        } else {
            System.out.printf("%-5s %-20s %-12s %-12s %-12s %-12s%n", 
                "ID", "Produtor", "Peso Antes", "Peso Depois", "Extraído", "Data");
            System.out.println("--------------------------------------------------------------------------------");
            
            for (MelDeTerceiros mel : lista) {
                System.out.printf("%-5d %-20s %-12.2f %-12.2f %-12.2f %-12s%n",
                    mel.getId(),
                    truncar(mel.getProdutor(), 20),
                    mel.getPesoAntes(),
                    mel.getPesoDepois(),
                    mel.getDiferencaPeso(),
                    mel.getData());
            }
        }
    }

    private void atualizar() {
        System.out.println("\n--- ATUALIZAR RECEBIMENTO ---");
        
        try {
            System.out.print("ID do recebimento: ");
            int id = Integer.parseInt(scanner.nextLine());
            
            MelDeTerceiros mel = controle.buscarPorId(id);
            
            if (mel == null) {
                System.out.println("Recebimento não encontrado.");
                return;
            }
            
            System.out.println("\nDados atuais:");
            exibirDetalhes(mel);
            System.out.println("\n(Pressione Enter para manter o valor atual)");
            
            System.out.print("Novo nome do produtor: ");
            String produtor = scanner.nextLine();
            if (!produtor.trim().isEmpty()) {
                mel.setProdutor(produtor);
            }
            
            System.out.print("Novo peso antes (kg): ");
            String pesoAntesStr = scanner.nextLine();
            if (!pesoAntesStr.trim().isEmpty()) {
                mel.setPesoAntes(Double.parseDouble(pesoAntesStr));
            }
            
            System.out.print("Novo peso depois (kg): ");
            String pesoDepoisStr = scanner.nextLine();
            if (!pesoDepoisStr.trim().isEmpty()) {
                mel.setPesoDepois(Double.parseDouble(pesoDepoisStr));
            }
            
            System.out.print("Nova data: ");
            String data = scanner.nextLine();
            if (!data.trim().isEmpty()) {
                mel.setData(data);
            }
            
            boolean sucesso = controle.atualizar(mel);
            System.out.println(sucesso ? "✓ Recebimento atualizado com sucesso!" : "✗ Erro ao atualizar recebimento.");
        } catch (NumberFormatException e) {
            System.out.println("Erro: valores numéricos inválidos.");
        } catch (Exception e) {
            System.out.println("Erro ao atualizar: " + e.getMessage());
        }
    }

    private void remover() {
        System.out.println("\n--- REMOVER RECEBIMENTO ---");
        
        try {
            System.out.print("ID do recebimento: ");
            int id = Integer.parseInt(scanner.nextLine());
            
            MelDeTerceiros mel = controle.buscarPorId(id);
            
            if (mel == null) {
                System.out.println("Recebimento não encontrado.");
                return;
            }
            
            exibirDetalhes(mel);
            
            System.out.print("\nConfirma a remoção? (S/N): ");
            String confirmacao = scanner.nextLine();
            
            if (confirmacao.equalsIgnoreCase("S")) {
                boolean sucesso = controle.remover(id);
                System.out.println(sucesso ? "✓ Recebimento removido com sucesso!" : "✗ Erro ao remover recebimento.");
            } else {
                System.out.println("Remoção cancelada.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro: ID inválido.");
        } catch (Exception e) {
            System.out.println("Erro ao remover: " + e.getMessage());
        }
    }

    private void exibirRelatorios() {
        System.out.println("\n--- RELATÓRIOS DE MEL DE TERCEIROS ---");
        
        List<MelDeTerceiros> lista = controle.listar();
        
        if (lista.isEmpty()) {
            System.out.println("Nenhum dado disponível para relatórios.");
            return;
        }
        
        // Calcular totais
        double totalPesoAntes = 0;
        double totalPesoDepois = 0;
        double totalMelExtraido = 0;
        
        for (MelDeTerceiros mel : lista) {
            totalPesoAntes += mel.getPesoAntes();
            totalPesoDepois += mel.getPesoDepois();
            totalMelExtraido += mel.getDiferencaPeso();
        }
        
        double porcentagemMedia = (totalMelExtraido / totalPesoAntes) * 100;
        double nossaParte = totalMelExtraido * 0.20; // 20% para nós
        double parteProdutor = totalMelExtraido * 0.80; // 80% para o produtor
        
        System.out.println("\n=== ESTATÍSTICAS GERAIS ===");
        System.out.println("Total de recebimentos: " + lista.size());
        System.out.printf("Peso total recebido: %.2f kg%n", totalPesoAntes);
        System.out.printf("Peso total após extração: %.2f kg%n", totalPesoDepois);
        System.out.printf("Mel total extraído: %.2f kg%n", totalMelExtraido);
        System.out.printf("Porcentagem média de extração: %.2f%%%n", porcentagemMedia);
        
        System.out.println("\n=== DIVISÃO DO MEL (20%/80%) ===");
        System.out.printf("Nossa parte (20%%): %.2f kg%n", nossaParte);
        System.out.printf("Parte dos produtores (80%%): %.2f kg%n", parteProdutor);
        
        // Listar por produtor
        System.out.println("\n=== POR PRODUTOR ===");
        List<MelDeTerceiros> todosRegistros = controle.listar();
        List<String> produtoresProcessados = new java.util.ArrayList<>();
        
        for (MelDeTerceiros mel : todosRegistros) {
            String produtor = mel.getProdutor();
            if (!produtoresProcessados.contains(produtor)) {
                produtoresProcessados.add(produtor);
                List<MelDeTerceiros> registrosProdutor = controle.listarPorProdutor(produtor);
                
                double totalProdutor = 0;
                for (MelDeTerceiros m : registrosProdutor) {
                    totalProdutor += m.getDiferencaPeso();
                }
                
                System.out.printf("\n%s:%n", produtor);
                System.out.printf("  Total extraído: %.2f kg%n", totalProdutor);
                System.out.printf("  Parte do produtor: %.2f kg%n", totalProdutor * 0.80);
                System.out.printf("  Nossa parte: %.2f kg%n", totalProdutor * 0.20);
            }
        }
    }

    private void exibirDetalhes(MelDeTerceiros mel) {
        System.out.println("\nProdutor: " + mel.getProdutor());
        System.out.printf("Peso antes: %.2f kg%n", mel.getPesoAntes());
        System.out.printf("Peso depois: %.2f kg%n", mel.getPesoDepois());
        System.out.printf("Mel extraído: %.2f kg%n", mel.getDiferencaPeso());
        System.out.println("Data: " + mel.getData());
    }

    private String truncar(String texto, int tamanho) {
        if (texto == null) return "";
        if (texto.length() <= tamanho) return texto;
        return texto.substring(0, tamanho - 3) + "...";
    }
}