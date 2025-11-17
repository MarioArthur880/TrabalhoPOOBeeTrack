package controle;

import java.util.List;
import repositorio.RepositorioMelDeTerceiros;

public class ControleMelDeTerceiros {
    private RepositorioMelDeTerceiros repositorio;

    public ControleMelDeTerceiros(RepositorioMelDeTerceiros repositorio) {
        if (repositorio == null) {
            throw new IllegalArgumentException("Repositório não pode ser nulo");
        }
        this.repositorio = repositorio;
    }

    public boolean criarMelDeTerceiros(String produtor, double pesoAntes, double pesoDepois, String data) {
        try {
            if (produtor == null || produtor.trim().isEmpty()) {
                return false;
            }
            if (pesoAntes < 0) {
                return false;
            }
            if (pesoDepois < 0) {
                return false;
            }
            if (pesoDepois > pesoAntes) {
                return false;
            }
            if (data == null || data.trim().isEmpty()) {
                return false;
            }
            if (!validarFormatoData(data)) {
                return false;
            }

            MelDeTerceiros novoMel = new MelDeTerceiros(0, produtor, pesoAntes, pesoDepois, data);
            return repositorio.adicionar(novoMel);
            
        } catch (Exception e) {
            System.err.println("Erro ao criar registro: " + e.getMessage());
            return false;
        }
    }

    public List<MelDeTerceiros> listar() {
        try {
            return repositorio.listarTodos();
        } catch (Exception e) {
            System.err.println("Erro ao listar registros: " + e.getMessage());
            return new java.util.ArrayList<>();
        }
    }

    public MelDeTerceiros buscarPorId(int id) {
        try {
            if (id <= 0) {
                return null;
            }
            return repositorio.buscarPorId(id);
        } catch (Exception e) {
            System.err.println("Erro ao buscar registro: " + e.getMessage());
            return null;
        }
    }

    public boolean remover(int id) {
        try {
            if (id <= 0) {
                return false;
            }
            return repositorio.remover(id);
        } catch (Exception e) {
            System.err.println("Erro ao remover registro: " + e.getMessage());
            return false;
        }
    }

    public boolean atualizar(MelDeTerceiros mel) {
        try {
            if (mel == null) {
                return false;
            }
            
            if (mel.getProdutor() == null || mel.getProdutor().trim().isEmpty()) {
                return false;
            }
            if (mel.getPesoAntes() < 0) {
                return false;
            }
            if (mel.getPesoDepois() < 0) {
                return false;
            }
            if (mel.getPesoDepois() > mel.getPesoAntes()) {
                return false;
            }
            if (mel.getData() == null || mel.getData().trim().isEmpty()) {
                return false;
            }
            if (!validarFormatoData(mel.getData())) {
                return false;
            }

            return repositorio.atualizar(mel);
            
        } catch (Exception e) {
            System.err.println("Erro ao atualizar registro: " + e.getMessage());
            return false;
        }
    }

    public List<MelDeTerceiros> listarPorProdutor(String produtor) {
        try {
            return repositorio.listarPorProdutor(produtor);
        } catch (Exception e) {
            System.err.println("Erro ao listar por produtor: " + e.getMessage());
            return new java.util.ArrayList<>();
        }
    }


    private boolean validarFormatoData(String data) {
        try {
            if (data == null || data.trim().isEmpty()) {
                return false;
            }
            
            String dataLimpa = data.trim();
            if (!dataLimpa.matches("\\d{2}/\\d{2}/\\d{4}")) {
                return false;
            }
            
            String[] partes = dataLimpa.split("/");
            int dia = Integer.parseInt(partes[0]);
            int mes = Integer.parseInt(partes[1]);
            int ano = Integer.parseInt(partes[2]);
            
            if (mes < 1 || mes > 12) {
                return false;
            }
            if (dia < 1 || dia > 31) {
                return false;
            }
            if (ano < 1900 || ano > 2100) {
                return false;
            }
            
            if ((mes == 4 || mes == 6 || mes == 9 || mes == 11) && dia > 30) {
                return false;
            }
            if (mes == 2 && dia > 29) {
                return false;
            }
            
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}