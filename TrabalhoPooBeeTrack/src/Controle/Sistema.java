package controle;

public class Sistema {
    
    public static void main(String[] args) {
        // Criar instância do sistema
        Sistema sistema = new Sistema();
        
        System.out.println("=== SISTEMA DE GESTÃO DE APIÁRIOS ===\n");
        
        // 1. CADASTRAR PRIMEIRO USUÁRIO (será automaticamente Admin)
        System.out.println("1. Cadastrando primeiro usuário (Admin)...");
        boolean cadastrou = sistema.cadastrarUsuario(
            "João Silva", 
            "joao@email.com", 
            "senha123", 
            "Usuario"
        );
        System.out.println("Cadastro: " + (cadastrou ? "Sucesso" : "Falhou"));
        
        // 2. FAZER LOGIN
        System.out.println("\n2. Fazendo login...");
        boolean logou = sistema.login("joao@email.com", "senha123");
        System.out.println("Login: " + (logou ? "Sucesso" : "Falhou"));
        
        if (sistema.isLogado()) {
            Pessoa usuario = sistema.getUsuarioLogado();
            System.out.println("Usuário logado: " + usuario.getNome());
            System.out.println("É admin? " + sistema.isAdmin());
        }
        
        // 3. CADASTRAR MAIS USUÁRIOS
        System.out.println("\n3. Cadastrando apicultor...");
        sistema.cadastrarUsuario(
            "Maria Santos", 
            "maria@email.com", 
            "senha456", 
            "Apicultor"
        );
        
        // 4. CADASTRAR APIÁRIO
        System.out.println("\n4. Cadastrando apiário...");
        boolean apiarioCadastrado = sistema.cadastrarApiario(
            "Apiário Central", 
            "Abelha Italiana", 
            "Fazenda São João", 
            15
        );
        System.out.println("Apiário cadastrado: " + (apiarioCadastrado ? "Sim" : "Não"));
        
        // 5. LISTAR APIÁRIOS
        System.out.println("\n5. Listando apiários:");
        for (Apiario a : sistema.listarApiarios()) {
            System.out.println("- " + a.getNome() + " | Local: " + a.getLocal() + 
                             " | Caixas: " + a.getQntCaixas());
        }
        
        // 6. CADASTRAR VISITA
        System.out.println("\n6. Cadastrando visita...");
        Apiario apiario = sistema.listarApiarios().get(0);
        boolean visitaCadastrada = sistema.cadastrarVisita(
            "10/11/2025", 
            apiario, 
            50, 
            "Colheita"
        );
        System.out.println("Visita cadastrada: " + (visitaCadastrada ? "Sim" : "Não"));
        
        // 7. CADASTRAR MEL DE TERCEIROS
        System.out.println("\n7. Cadastrando mel de terceiros...");
        boolean melCadastrado = sistema.cadastrarMelDeTerceiros(
            "Produtor Local", 
            100.5, 
            95.0, 
            "10/11/2025"
        );
        System.out.println("Mel cadastrado: " + (melCadastrado ? "Sim" : "Não"));
        
        // 8. EXIBIR ESTATÍSTICAS
        System.out.println("\n8. Estatísticas do sistema:");
        System.out.println("- Total de usuários: " + sistema.getTotalUsuarios());
        System.out.println("- Total de apiários: " + sistema.getTotalApiarios());
        System.out.println("- Total de visitas: " + sistema.getTotalVisitas());
        System.out.println("- Produção total (kg): " + sistema.getProducaoTotal());
        System.out.println("- Processamento mel terceiros (kg): " + 
                         sistema.getProcessamentoMelTerceiros());
        
        // 9. LISTAR USUÁRIOS
        System.out.println("\n9. Listando usuários:");
        for (Pessoa p : sistema.listarUsuarios()) {
            System.out.println("- " + p.getNome() + " (" + p.getTipoUsuario() + 
                             ") | Email: " + p.getEmail());
        }
        
        // 10. LOGOUT
        System.out.println("\n10. Fazendo logout...");
        sistema.logout();
        System.out.println("Ainda logado? " + sistema.isLogado());
        
        // 11. TENTAR CADASTRAR SEM LOGIN (deve falhar)
        System.out.println("\n11. Tentando cadastrar apiário sem login...");
        boolean falhou = sistema.cadastrarApiario(
            "Novo Apiário", 
            "Africanizada", 
            "Outro Local", 
            10
        );
        System.out.println("Cadastro sem login: " + (falhou ? "Sucesso (erro!)" : "Falhou (correto!)"));
        
        System.out.println("\n=== FIM DO EXEMPLO ===");
    }
}
