package controle;

import java.util.List;
import repositorio.RepositorioVisita;

public class ControleVisita {
    private RepositorioVisita repositorio;

    public ControleVisita(RepositorioVisita repositorio) {
        if (repositorio == null) {
            throw new IllegalArgumentException("Repositorio nao pode ser nulo");
        }
        this.repositorio = repositorio;
    }

    public boolean criarVisita(String data, Apiario apiario, int colheita, String tipoVisita) {
        try {
            // Validacoes de regras de negocio
            if (data == null || data.trim().isEmpty()) {
                return false;
            }
            if (apiario == null) {
                return false;
            }
            if (colheita < 0) {
                return false;
            }
            if (tipoVisita == null || tipoVisita.trim().isEmpty()) {
                return false;
            }
            if (!validarFormatoData(data)) {
                return false;
            }

            Visita novaVisita = new Visita(0, data, apiario, colheita, tipoVisita);
            return repositorio.adicionar(novaVisita);
            
        } catch (Exception e) {
            System.err.println("Erro ao criar visita: " + e.getMessage());
            return false;
        }
    }

    public List<Visita> listar() {
        try {
            return repositorio.listarTodos();
        } catch (Exception e) {
            System.err.println("Erro ao listar visitas: " + e.getMessage());
            return new java.util.ArrayList<>();
        }
    }

    public Visita buscarPorId(int id) {
        try {
            if (id <= 0) {
                return null;
            }
            return repositorio.buscarPorId(id);
        } catch (Exception e) {
            System.err.println("Erro ao buscar visita: " + e.getMessage());
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
            System.err.println("Erro ao remover visita: " + e.getMessage());
            return false;
        }
    }

    public boolean atualizar(Visita visita) {
        try {
            if (visita == null) {
                return false;
            }
            
            // Validacoes de regras de negocio
            if (visita.getData() == null || visita.getData().trim().isEmpty()) {
                return false;
            }
            if (visita.getApiario() == null) {
                return false;
            }
            if (visita.getColheita() < 0) {
                return false;
            }
            if (visita.getTipoVisita() == null || visita.getTipoVisita().trim().isEmpty()) {
                return false;
            }
            if (!validarFormatoData(visita.getData())) {
                return false;
            }

            return repositorio.atualizar(visita);
            
        } catch (Exception e) {
            System.err.println("Erro ao atualizar visita: " + e.getMessage());
            return false;
        }
    }

    public List<Visita> listarPorApiario(Apiario apiario) {
        try {
            if (apiario == null) {
                return new java.util.ArrayList<>();
            }
            return repositorio.listarPorApiario(apiario);
        } catch (Exception e) {
            System.err.println("Erro ao listar visitas por apiario: " + e.getMessage());
            return new java.util.ArrayList<>();
        }
    }

    /**
     * Valida o formato da data (dd/mm/aaaa)
     */
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
            
            // Validacao simples de dias por mes
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