package controle;

import modelo.Apiario;
import java.util.ArrayList;
import java.util.List;

public class ControleApiario {
    private List<Apiario> listaApiarios = new ArrayList<>();
    private int proximoId = 1;

    public boolean criarApiario(String nome, String raca, String local, int qntCaixas) {
        Apiario novo = Apiario.criarApiario(proximoId, nome, raca, local, qntCaixas);
        if (novo != null) {
            listaApiarios.add(novo);
            proximoId++;
            return true;
        }
        return false;
    }

    public List<Apiario> listar() {
        return new ArrayList<>(listaApiarios);
    }

    public Apiario buscarPorId(int id) {
        for (Apiario a : listaApiarios) {
            if (a.getId() == id) {
                return a;
            }
        }
        return null;
    }

    public boolean remover(int id) {
        Apiario a = buscarPorId(id);
        if (a != null) {
            return listaApiarios.remove(a);
        }
        return false;
    }

    public boolean atualizar(Apiario novoApiario) {
        if (novoApiario == null) return false;

        for (int i = 0; i < listaApiarios.size(); i++) {
            if (listaApiarios.get(i).getId() == novoApiario.getId()) {
                listaApiarios.set(i, novoApiario);
                return true;
            }
        }
        return false;
    }

    public List<Apiario> listarPorLocal(String local) {
        List<Apiario> resultado = new ArrayList<>();
        if (local == null || local.trim().isEmpty()) return resultado;

        for (Apiario a : listaApiarios) {
            if (a.getLocal() != null && a.getLocal().equalsIgnoreCase(local.trim())) {
                resultado.add(a);
            }
        }
        return resultado;
    }
}
