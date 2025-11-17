package controle;

import repositorio.RepositorioApiario;
import java.util.List;

public class ControleApiario {
    private RepositorioApiario repositorio;
    private String ultimaMensagemErro;

    public ControleApiario(RepositorioApiario repositorio) {
        if (repositorio == null) {
            throw new IllegalArgumentException("Repositorio nao pode ser nulo");
        }
        this.repositorio = repositorio;
        this.ultimaMensagemErro = "";
    }

    public String getUltimaMensagemErro() {
        return ultimaMensagemErro;
    }

    public boolean criarApiario(String nome, String raca, String local, int qntCaixas) {
        try {
            if (nome == null || nome.trim().isEmpty()) {
                ultimaMensagemErro = "Nome nao pode ser vazio.";
                return false;
            }
            
            if (raca == null || raca.trim().isEmpty()) {
                ultimaMensagemErro = "Raca nao pode ser vazia.";
                return false;
            }
            
            if (local == null || local.trim().isEmpty()) {
                ultimaMensagemErro = "Local nao pode ser vazio.";
                return false;
            }
            
            if (qntCaixas <= 0) {
                ultimaMensagemErro = "Quantidade de caixas deve ser maior que zero.";
                return false;
            }

            if (repositorio.existeNome(nome)) {
                ultimaMensagemErro = "Ja existe um apiario com este nome.";
                return false;
            }

            Apiario novoApiario = new Apiario(nome.trim(), raca.trim(), local.trim(), qntCaixas);
            boolean adicionado = repositorio.adicionar(novoApiario);
            
            if (adicionado) {
                ultimaMensagemErro = "";
                return true;
            } else {
                ultimaMensagemErro = "Erro ao adicionar apiario ao repositorio.";
                return false;
            }
            
        } catch (Exception e) {
            System.err.println("Erro ao criar apiario: " + e.getMessage());
            ultimaMensagemErro = "Erro inesperado ao criar apiario.";
            return false;
        }
    }

    public List<Apiario> listar() {
        try {
            return repositorio.listarTodos();
        } catch (Exception e) {
            System.err.println("Erro ao listar apiarios: " + e.getMessage());
            return new java.util.ArrayList<>();
        }
    }

    public Apiario buscarPorId(int id) {
        try {
            if (id <= 0) {
                return null;
            }
            return repositorio.buscarPorId(id);
        } catch (Exception e) {
            System.err.println("Erro ao buscar apiario por ID: " + e.getMessage());
            return null;
        }
    }

    public Apiario buscarPorNome(String nome) {
        try {
            if (nome == null || nome.trim().isEmpty()) {
                return null;
            }
            return repositorio.buscarPorNome(nome);
        } catch (Exception e) {
            System.err.println("Erro ao buscar apiario por nome: " + e.getMessage());
            return null;
        }
    }

    public List<Apiario> buscarTodosPorNome(String nome) {
        try {
            return repositorio.buscarTodosPorNome(nome);
        } catch (Exception e) {
            System.err.println("Erro ao buscar apiarios por nome: " + e.getMessage());
            return new java.util.ArrayList<>();
        }
    }

    public boolean remover(int id) {
        try {
            if (id <= 0) {
                ultimaMensagemErro = "ID invalido.";
                return false;
            }
            
            Apiario apiario = repositorio.buscarPorId(id);
            if (apiario == null) {
                ultimaMensagemErro = "Apiario nao encontrado.";
                return false;
            }
            
            boolean removido = repositorio.remover(id);
            
            if (removido) {
                ultimaMensagemErro = "";
                return true;
            } else {
                ultimaMensagemErro = "Erro ao remover apiario.";
                return false;
            }
            
        } catch (Exception e) {
            System.err.println("Erro ao remover apiario: " + e.getMessage());
            ultimaMensagemErro = "Erro inesperado ao remover apiario.";
            return false;
        }
    }

    public boolean atualizar(Apiario apiario) {
        try {
            if (apiario == null) {
                ultimaMensagemErro = "Apiario nao pode ser nulo.";
                return false;
            }
            
            if (apiario.getNome() == null || apiario.getNome().trim().isEmpty()) {
                ultimaMensagemErro = "Nome nao pode ser vazio.";
                return false;
            }
            
            if (apiario.getRaca() == null || apiario.getRaca().trim().isEmpty()) {
                ultimaMensagemErro = "Raca nao pode ser vazia.";
                return false;
            }
            
            if (apiario.getLocal() == null || apiario.getLocal().trim().isEmpty()) {
                ultimaMensagemErro = "Local nao pode ser vazio.";
                return false;
            }
            
            if (apiario.getQntCaixas() <= 0) {
                ultimaMensagemErro = "Quantidade de caixas deve ser maior que zero.";
                return false;
            }

            Apiario existente = repositorio.buscarPorId(apiario.getId());
            if (existente == null) {
                ultimaMensagemErro = "Apiario nao encontrado.";
                return false;
            }

            if (repositorio.existeNomeEmOutroApiario(apiario.getNome(), apiario.getId())) {
                ultimaMensagemErro = "Ja existe outro apiario com este nome.";
                return false;
            }

            boolean atualizado = repositorio.atualizar(apiario);
            
            if (atualizado) {
                ultimaMensagemErro = "";
                return true;
            } else {
                ultimaMensagemErro = "Erro ao atualizar apiario.";
                return false;
            }
            
        } catch (Exception e) {
            System.err.println("Erro ao atualizar apiario: " + e.getMessage());
            ultimaMensagemErro = "Erro inesperado ao atualizar apiario.";
            return false;
        }
    }

    public List<Apiario> listarPorLocal(String local) {
        try {
            return repositorio.listarPorLocal(local);
        } catch (Exception e) {
            System.err.println("Erro ao listar apiarios por local: " + e.getMessage());
            return new java.util.ArrayList<>();
        }
    }

    public List<Apiario> listarPorRaca(String raca) {
        try {
            return repositorio.listarPorRaca(raca);
        } catch (Exception e) {
            System.err.println("Erro ao listar apiarios por raca: " + e.getMessage());
            return new java.util.ArrayList<>();
        }
    }

    public int getTotalCaixas() {
        try {
            return repositorio.calcularTotalCaixas();
        } catch (Exception e) {
            System.err.println("Erro ao calcular total de caixas: " + e.getMessage());
            return 0;
        }
    }

    public int getTotalCaixasPorLocal(String local) {
        try {
            return repositorio.calcularTotalCaixasPorLocal(local);
        } catch (Exception e) {
            System.err.println("Erro ao calcular caixas por local: " + e.getMessage());
            return 0;
        }
    }

    public int getTotalApiarios() {
        try {
            return repositorio.getTotalApiarios();
        } catch (Exception e) {
            System.err.println("Erro ao obter total de apiarios: " + e.getMessage());
            return 0;
        }
    }

    public boolean existeNome(String nome) {
        try {
            return repositorio.existeNome(nome);
        } catch (Exception e) {
            System.err.println("Erro ao verificar existencia de nome: " + e.getMessage());
            return false;
        }
    }
}
