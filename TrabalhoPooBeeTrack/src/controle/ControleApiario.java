package controle;

import controle.Apiario;
import Repositório.RepositorioApiario;
import java.util.List;

public class ControleApiario {
    private RepositorioApiario repositorio;

    public ControleApiario(RepositorioApiario repositorio) {
        this.repositorio = repositorio;
    }

    public boolean criarApiario(String nome, String raca, String local, int qntCaixas) {
        // Validações
        if (nome == null || nome.trim().isEmpty()) {
            return false;
        }
        if (raca == null || raca.trim().isEmpty()) {
            return false;
        }
        if (local == null || local.trim().isEmpty()) {
            return false;
        }
        if (qntCaixas < 0) {
            return false;
        }

        Apiario novoApiario = new Apiario(0, nome, raca, local, qntCaixas);
        return repositorio.adicionar(novoApiario);
    }

    public List<Apiario> listar() {
        return repositorio.listarTodos();
    }

    public Apiario buscarPorId(int id) {
        if (id <= 0) {
            return null;
        }
        return repositorio.buscarPorId(id);
    }

    public boolean remover(int id) {
        if (id <= 0) {
            return false;
        }
        return repositorio.remover(id);
    }

    public boolean atualizar(Apiario apiario) {
        if (apiario == null) {
            return false;
        }
        
        // Validações
        if (apiario.getNome() == null || apiario.getNome().trim().isEmpty()) {
            return false;
        }
        if (apiario.getRaca() == null || apiario.getRaca().trim().isEmpty()) {
            return false;
        }
        if (apiario.getLocal() == null || apiario.getLocal().trim().isEmpty()) {
            return false;
        }
        if (apiario.getQntCaixas() < 0) {
            return false;
        }

        return repositorio.atualizar(apiario);
    }

    public List<Apiario> listarPorLocal(String local) {
        return repositorio.listarPorLocal(local);
    }
}