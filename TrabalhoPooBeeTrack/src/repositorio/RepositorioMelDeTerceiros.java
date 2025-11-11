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

    /**
     * Adiciona um novo registro de mel de terceiros
     * @param mel O objeto MelDeTerceiros a ser adicionado
     * @return true se adicionado com sucesso, false caso contrário
     */
    public boolean adicionar(MelDeTerceiros mel) {
        if (mel == null) {
            return false;
        }
        
        // Cria uma cópia para evitar modificações externas
        MelDeTerceiros copia = new MelDeTerceiros(mel);
        copia.setId(proximoId);
        
        boolean adicionado = listaMel.add(copia);
        if (adicionado) {
            proximoId++;
        }
        
        return adicionado;
    }

    /**
     * Lista todos os registros de mel de terceiros
     * @return Lista com cópias de todos os registros
     */
    public List<MelDeTerceiros> listarTodos() {
        List<MelDeTerceiros> copias = new ArrayList<>();
        for (MelDeTerceiros mel : listaMel) {
            copias.add(new MelDeTerceiros(mel));
        }
        return copias;
    }

    /**
     * Busca um registro de mel por ID
     * @param id O ID do registro a ser buscado
     * @return Uma cópia do registro encontrado ou null se não existir
     */
    public MelDeTerceiros buscarPorId(int id) {
        for (MelDeTerceiros mel : listaMel) {
            if (mel.getId() == id) {
                return new MelDeTerceiros(mel);
            }
        }
        return null;
    }

    /**
     * Remove um registro de mel por ID
     * @param id O ID do registro a ser removido
     * @return true se removido com sucesso, false caso contrário
     */
    public boolean remover(int id) {
        for (int i = 0; i < listaMel.size(); i++) {
            if (listaMel.get(i).getId() == id) {
                listaMel.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Atualiza um registro de mel existente
     * @param melAtualizado O objeto com os dados atualizados
     * @return true se atualizado com sucesso, false caso contrário
     */
    public boolean atualizar(MelDeTerceiros melAtualizado) {
        if (melAtualizado == null) {
            return false;
        }

        for (int i = 0; i < listaMel.size(); i++) {
            if (listaMel.get(i).getId() == melAtualizado.getId()) {
                // Substitui pelo objeto atualizado (mantendo o ID)
                MelDeTerceiros copia = new MelDeTerceiros(melAtualizado);
                listaMel.set(i, copia);
                return true;
            }
        }
        return false;
    }

    /**
     * Lista todos os registros de um produtor específico
     * @param produtor O nome do produtor
     * @return Lista com cópias dos registros do produtor
     */
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

    /**
     * Lista registros por data
     * @param data A data a ser buscada
     * @return Lista com cópias dos registros da data especificada
     */
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

    /**
     * Calcula o peso total processado (diferença entre peso antes e depois)
     * @return O peso total processado
     */
    public double calcularPesoTotalProcessado() {
        double total = 0;
        for (MelDeTerceiros mel : listaMel) {
            total += mel.getDiferencaPeso();
        }
        return total;
    }

    /**
     * Calcula o peso total processado de um produtor específico
     * @param produtor O nome do produtor
     * @return O peso total processado pelo produtor
     */
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

    /**
     * Retorna a quantidade total de registros
     * @return O número de registros no repositório
     */
    public int getTotalRegistros() {
        return listaMel.size();
    }

    /**
     * Verifica se existe algum registro de um produtor específico
     * @param produtor O nome do produtor
     * @return true se existe pelo menos um registro, false caso contrário
     */
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

    /**
     * Limpa todos os registros do repositório (útil para testes)
     */
    public void limparTodos() {
        listaMel.clear();
        proximoId = 1;
    }
}