package UI;

import controle.ControleMelDeTerceiros;
import controle.MelDeTerceiros;
import java.util.List;
import java.util.Scanner;

public class UImeldeterceiros {
    private Scanner scanner;
    private ControleMelDeTerceiros controle;

    public UImeldeterceiros(Scanner scanner, ControleMelDeTerceiros controle) {
        if (scanner == null || controle == null) {
            throw new IllegalArgumentException("Scanner e Controle não podem ser nulos");
        }
        this.scanner = scanner;
        this.controle = controle;
    }

    public void exibir() {
        int opcao;
        do {
            try {
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
                case 5: exibirRelatorios(); break;
                case 0: System.out.println("\nVoltando ao menu anterior..."); break;
                default: System.out.println("Opcao invalida. Escolha entre 0 e 5.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao processar opcao: " + e.getMessage());
        }
    }

    private void registrar() {
        System.out.println("\n--- REGISTRAR RECEBIMENTO DE MEL ---");
        
        try {
            System.out.print("Nome do produtor: ");
            String produtor = scanner.nextLine();
            
            if (produtor == null || produtor.trim().isEmpty()) {
                System.out.println("Erro: Nome do produtor e obrigatorio.");
                return;
            }
            
            System.out.print("Peso antes da extração (kg): ");
            String pesoAntesStr = scanner.nextLine();
            double pesoAntes;
            
            try {
                pesoAntes = Double.parseDouble(pesoAntesStr);
                if (pesoAntes < 0) {
                    System.out.println("Erro: Peso nao pode ser negativo.");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: Digite um valor numerico valido para o peso antes.");
                return;
            }
            
            System.out.print("Peso depois da extração (kg): ");
            String pesoDepoisStr = scanner.nextLine();
            double pesoDepois;
            
            try {
                pesoDepois = Double.parseDouble(pesoDepoisStr);
                if (pesoDepois < 0) {
                    System.out.println("Erro: Peso nao pode ser negativo.");
                    return;
                }
                if (pesoDepois > pesoAntes) {
                    System.out.println("Erro: Peso depois nao pode ser maior que peso antes.");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: Digite um valor numerico valido para o peso depois.");
                return;
            }
            
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
            
            boolean sucesso = controle.criarMelDeTerceiros(produtor, pesoAntes, pesoDepois, data);
            
            if (sucesso) {
                double melExtraido = pesoAntes - pesoDepois;
                double nossaParte = melExtraido * 0.20;
                System.out.println("\nRecebimento registrado com sucesso!");
                System.out.printf("  Mel extraido: %.2f kg%n", melExtraido);
                System.out.printf("  Nossa parte (20%%): %.2f kg%n", nossaParte);
                System.out.printf("  Parte do produtor (80%%): %.2f kg%n", melExtraido - nossaParte);
            } else {
                System.out.println("\nErro: Nao foi possivel registrar o recebimento.");
                System.out.println("  Verifique se todos os dados estao corretos.");
            }
            
        } catch (Exception e) {
            System.out.println("Erro ao registrar recebimento: " + e.getMessage());
        }
    }

    private void listar() {
        System.out.println("\n--- LISTA DE RECEBIMENTOS ---");
        
        try {
            List<MelDeTerceiros> lista = controle.listar();
            
            if (lista == null || lista.isEmpty()) {
                System.out.println("Nenhum recebimento registrado.");
                return;
            }
            
            System.out.printf("%-5s %-20s %-12s %-12s %-12s %-12s%n", 
                "ID", "Produtor", "Peso Antes", "Peso Depois", "Extraído", "Data");
            System.out.println("--------------------------------------------------------------------------------");
            
            for (MelDeTerceiros mel : lista) {
                try {
                    System.out.printf("%-5d %-20s %-12.2f %-12.2f %-12.2f %-12s%n",
                        mel.getId(),
                        truncar(mel.getProdutor(), 20),
                        mel.getPesoAntes(),
                        mel.getPesoDepois(),
                        mel.getDiferencaPeso(),
                        mel.getData());
                } catch (Exception e) {
                    System.out.println("Erro ao exibir registro ID " + mel.getId());
                }
            }
            
        } catch (Exception e) {
            System.out.println("Erro ao listar recebimentos: " + e.getMessage());
        }
    }

    private void atualizar() {
        System.out.println("\n--- ATUALIZAR RECEBIMENTO ---");
        
        try {
            System.out.print("ID do recebimento: ");
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
            
            MelDeTerceiros mel = controle.buscarPorId(id);
            
            if (mel == null) {
                System.out.println("Recebimento nao encontrado com ID: " + id);
                return;
            }
            
            System.out.println("\nDados atuais:");
            exibirDetalhes(mel);
            System.out.println("\n(Pressione Enter para manter o valor atual)");
            
            System.out.print("Novo nome do produtor: ");
            String produtor = scanner.nextLine();
            if (produtor != null && !produtor.trim().isEmpty()) {
                mel.setProdutor(produtor);
            }
            
            System.out.print("Novo peso antes (kg): ");
            String pesoAntesStr = scanner.nextLine();
            if (pesoAntesStr != null && !pesoAntesStr.trim().isEmpty()) {
                try {
                    double pesoAntes = Double.parseDouble(pesoAntesStr);
                    if (pesoAntes < 0) {
                        System.out.println("Peso negativo ignorado. Mantendo valor anterior.");
                    } else {
                        mel.setPesoAntes(pesoAntes);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Valor invalido ignorado. Mantendo valor anterior.");
                }
            }
            
            System.out.print("Novo peso depois (kg): ");
            String pesoDepoisStr = scanner.nextLine();
            if (pesoDepoisStr != null && !pesoDepoisStr.trim().isEmpty()) {
                try {
                    double pesoDepois = Double.parseDouble(pesoDepoisStr);
                    if (pesoDepois < 0) {
                        System.out.println("Peso negativo ignorado. Mantendo valor anterior.");
                    } else {
                        mel.setPesoDepois(pesoDepois);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Valor invalido ignorado. Mantendo valor anterior.");
                }
            }
            
            System.out.print("Nova data (dd/mm/aaaa): ");
            String data = scanner.nextLine();
            if (data != null && !data.trim().isEmpty()) {
                if (data.matches("\\d{2}/\\d{2}/\\d{4}")) {
                    mel.setData(data);
                } else {
                    System.out.println("Formato de data invalido. Mantendo valor anterior.");
                }
            }
            
            boolean sucesso = controle.atualizar(mel);
            
            if (sucesso) {
                System.out.println("\nRecebimento atualizado com sucesso!");
            } else {
                System.out.println("\nErro ao atualizar recebimento.");
                System.out.println("  Verifique se os dados estao corretos.");
            }
            
        } catch (Exception e) {
            System.out.println("Erro ao atualizar: " + e.getMessage());
        }
    }

    private void remover() {
        System.out.println("\n--- REMOVER RECEBIMENTO ---");
        
        try {
            System.out.print("ID do recebimento: ");
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
            
            MelDeTerceiros mel = controle.buscarPorId(id);
            
            if (mel == null) {
                System.out.println("Recebimento nao encontrado com ID: " + id);
                return;
            }
            
            System.out.println("\nDados do recebimento:");
            exibirDetalhes(mel);
            
            System.out.print("\nConfirma a remocao? (S/N): ");
            String confirmacao = scanner.nextLine();
            
            if (confirmacao == null || confirmacao.trim().isEmpty()) {
                System.out.println("Remocao cancelada.");
                return;
            }
            
            if (confirmacao.trim().equalsIgnoreCase("S")) {
                boolean sucesso = controle.remover(id);
                if (sucesso) {
                    System.out.println("\nRecebimento removido com sucesso!");
                } else {
                    System.out.println("\nErro ao remover recebimento.");
                }
            } else {
                System.out.println("Remocao cancelada.");
            }
            
        } catch (Exception e) {
            System.out.println("Erro ao remover: " + e.getMessage());
        }
    }

    private void exibirRelatorios() {
        System.out.println("\n--- RELATÓRIOS DE MEL DE TERCEIROS ---");
        
        try {
            List<MelDeTerceiros> lista = controle.listar();
            
            if (lista == null || lista.isEmpty()) {
                System.out.println("Nenhum dado disponivel para relatorios.");
                return;
            }
            
            // Calcular totais
            double totalPesoAntes = 0;
            double totalPesoDepois = 0;
            double totalMelExtraido = 0;
            
            for (MelDeTerceiros mel : lista) {
                try {
                    totalPesoAntes += mel.getPesoAntes();
                    totalPesoDepois += mel.getPesoDepois();
                    totalMelExtraido += mel.getDiferencaPeso();
                } catch (Exception e) {
                    System.out.println("Erro ao processar registro ID " + mel.getId());
                }
            }
            
            double porcentagemMedia = (totalPesoAntes > 0) ? (totalMelExtraido / totalPesoAntes) * 100 : 0;
            double nossaParte = totalMelExtraido * 0.20;
            double parteProdutor = totalMelExtraido * 0.80;
            
            System.out.println("\n=== ESTATISTICAS GERAIS ===");
            System.out.println("Total de recebimentos: " + lista.size());
            System.out.printf("Peso total recebido: %.2f kg%n", totalPesoAntes);
            System.out.printf("Peso total apos extracao: %.2f kg%n", totalPesoDepois);
            System.out.printf("Mel total extraido: %.2f kg%n", totalMelExtraido);
            System.out.printf("Porcentagem media de extracao: %.2f%%%n", porcentagemMedia);
            
            System.out.println("\n=== DIVISAO DO MEL (20%/80%) ===");
            System.out.printf("Nossa parte (20%%): %.2f kg%n", nossaParte);
            System.out.printf("Parte dos produtores (80%%): %.2f kg%n", parteProdutor);
            
            // Listar por produtor
            System.out.println("\n=== POR PRODUTOR ===");
            List<String> produtoresProcessados = new java.util.ArrayList<>();
            
            for (MelDeTerceiros mel : lista) {
                try {
                    String produtor = mel.getProdutor();
                    if (produtor != null && !produtoresProcessados.contains(produtor)) {
                        produtoresProcessados.add(produtor);
                        List<MelDeTerceiros> registrosProdutor = controle.listarPorProdutor(produtor);
                        
                        double totalProdutor = 0;
                        for (MelDeTerceiros m : registrosProdutor) {
                            totalProdutor += m.getDiferencaPeso();
                        }
                        
                        System.out.printf("\n%s:%n", produtor);
                        System.out.printf("  Total extraido: %.2f kg%n", totalProdutor);
                        System.out.printf("  Parte do produtor: %.2f kg%n", totalProdutor * 0.80);
                        System.out.printf("  Nossa parte: %.2f kg%n", totalProdutor * 0.20);
                    }
                } catch (Exception e) {
                    System.out.println("Erro ao processar produtor");
                }
            }
            
        } catch (Exception e) {
            System.out.println("Erro ao gerar relatorios: " + e.getMessage());
        }
    }

    private void exibirDetalhes(MelDeTerceiros mel) {
        try {
            System.out.println("\nProdutor: " + mel.getProdutor());
            System.out.printf("Peso antes: %.2f kg%n", mel.getPesoAntes());
            System.out.printf("Peso depois: %.2f kg%n", mel.getPesoDepois());
            System.out.printf("Mel extraido: %.2f kg%n", mel.getDiferencaPeso());
            System.out.println("Data: " + mel.getData());
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