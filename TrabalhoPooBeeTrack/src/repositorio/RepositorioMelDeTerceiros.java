package repositorio;

import controle.MelDeTerceiros;
import java.util.ArrayList;
import java.util.List;

public class RepositorioMelDeTerceiros {
    private List<MelDeTerceiros> listaMel;
    private int proximoId;

    public RepositorioMelDeTerceiros() {
        this.listaMel = new ArrayList<>();
        this.proximoId = 1;
    }

    public boolean adicionar(MelDeTerceiros mel) {
        if (mel == null) {
            return false;
        }

        MelDeTerceiros copia = new MelDeTerceiros(mel);
        copia.setId(proximoId);

        boolean adicionado = listaMel.add(copia);
        if (adicionado) {
            proximoId++;
        }

        return adicionado;
    }

    public List<MelDeTerceiros> listarTodos() {
        List<MelDeTerceiros> copias = new ArrayList<>();
        for (MelDeTerceiros mel : listaMel) {
            copias.add(new MelDeTerceiros(mel));
        }
        return copias;
    }

    public MelDeTerceiros buscarPorId(int id) {
        for (MelDeTerceiros mel : listaMel) {
            if (mel.getId() == id) {
                return new MelDeTerceiros(mel);
            }
        }
        return null;
    }

    public boolean remover(int id) {
        for (int i = 0; i < listaMel.size(); i++) {
            if (listaMel.get(i).getId() == id) {
                listaMel.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean atualizar(MelDeTerceiros melAtualizado) {
        if (melAtualizado == null) {
            return false;
        }

        for (int i = 0; i < listaMel.size(); i++) {
            if (listaMel.get(i).getId() == melAtualizado.getId()) {
                MelDeTerceiros copia = new MelDeTerceiros(melAtualizado);
                listaMel.set(i, copia);
                return true;
            }
        }
        return false;
    }

    public List<MelDeTerceiros> listarPorProdutor(String produtor) {
        List<MelDeTerceiros> resultado = new ArrayList<>();

        if (produtor == null || produtor.trim().isEmpty()) {
            return resultado;
        }

        for (MelDeTerceiros mel : listaMel) {
            if (mel.getProdutor().equalsIgnoreCase(produtor.trim())) {
                resultado.add(new MelDeTerceiros(mel));
            }
        }

        return resultado;
    }

    public List<MelDeTerceiros> listarPorData(String data) {
        List<MelDeTerceiros> resultado = new ArrayList<>();

        if (data == null || data.trim().isEmpty()) {
            return resultado;
        }

        for (MelDeTerceiros mel : listaMel) {
            if (mel.getData().equals(data.trim())) {
                resultado.add(new MelDeTerceiros(mel));
            }
        }

        return resultado;
    }

    public double calcularPesoTotalProcessado() {
        double total = 0;
        for (MelDeTerceiros mel : listaMel) {
            total += mel.getDiferencaPeso();
        }
        return total;
    }

    public double calcularPesoProcessadoPorProdutor(String produtor) {
        double total = 0;

        if (produtor == null || produtor.trim().isEmpty()) {
            return total;
        }

        for (MelDeTerceiros mel : listaMel) {
            if (mel.getProdutor().equalsIgnoreCase(produtor.trim())) {
                total += mel.getDiferencaPeso();
            }
        }

        return total;
    }

    public int getTotalRegistros() {
        return listaMel.size();
    }

    public boolean existeProdutor(String produtor) {
        if (produtor == null || produtor.trim().isEmpty()) {
            return false;
        }

        for (MelDeTerceiros mel : listaMel) {
            if (mel.getProdutor().equalsIgnoreCase(produtor.trim())) {
                return true;
            }
        }
        return false;
    }

    public void limparTodos() {
        listaMel.clear();
        proximoId = 1;
    }
}
