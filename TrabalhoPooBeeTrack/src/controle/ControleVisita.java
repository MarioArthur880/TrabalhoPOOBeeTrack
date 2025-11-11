package controle;

import java.util.List;
import repositorio.RepositorioVisita;

public class ControleVisita {
    private RepositorioVisita repositorio;

    public ControleVisita(RepositorioVisita repositorio) {
        this.repositorio = repositorio;
    }

    public boolean criarVisita(String data, Apiario apiario, int colheita, String tipoVisita) {
        if (data == null || data.trim().isEmpty()) return false;
        if (apiario == null) return false;
        if (colheita < 0) return false;
        if (tipoVisita == null || tipoVisita.trim().isEmpty()) return false;

        Visita novaVisita = new Visita(0, data, apiario, colheita, tipoVisita);
        return repositorio.adicionar(novaVisita);
    }

    public List<Visita> listar() {
        return repositorio.listarTodos();
    }

    public Visita buscarPorId(int id) {
        if (id <= 0) return null;
        return repositorio.buscarPorId(id);
    }

    public boolean remover(int id) {
        if (id <= 0) return false;
        return repositorio.remover(id);
    }

    public boolean atualizar(Visita visita) {
        if (visita == null) return false;
        if (visita.getData() == null || visita.getData().trim().isEmpty()) return false;
        if (visita.getApiario() == null) return false;
        if (visita.getColheita() < 0) return false;
        if (visita.getTipoVisita() == null || visita.getTipoVisita().trim().isEmpty()) return false;

        return repositorio.atualizar(visita);
    }

    public List<Visita> listarPorApiario(Apiario apiario) {
        return repositorio.listarPorApiario(apiario);
    }
}
