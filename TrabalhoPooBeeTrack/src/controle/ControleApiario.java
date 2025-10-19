package controle;

import modelo.*;
import java.util.ArrayList;
import java.util.List;

public class ControleApiario {
    private List<Apiario> listaApiarios = new ArrayList<>();

    public void adicionar(Apiario a) {
        listaApiarios.add(a);
    }

    public List<Apiario> listar() {
        return listaApiarios;
    }

    public Apiario buscarPorId(int id) {
        for (Apiario a : listaApiarios) {
            if (a.getId() == id) return a; //criar getId() no   apiario1!!
        }
        return null;
    }

    public boolean remover(int id) {
        Apiario a = buscarPorId(id);
        if (a != null) return listaApiarios.remove(a);
        return false;
    }
}