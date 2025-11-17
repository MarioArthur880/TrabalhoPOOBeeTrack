package repositorio;

import controle.Pessoa;
import controle.Administrador;
import controle.Apicultor;
import java.util.ArrayList;
import java.util.List;

public class RepositorioUsuario {
    private List<Pessoa> usuarios = new ArrayList<>();
    private int proximoId = 1;

    public boolean adicionar(Pessoa usuario) {
        if (usuario == null) return false;
        
        Pessoa copia = copiarPessoa(usuario);
        copia.setId(proximoId);
        
        boolean adicionado = usuarios.add(copia);
        if (adicionado) {
            proximoId++;
        }
        return adicionado;
    }

    public boolean remover(int id) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getId() == id) {
                usuarios.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean removerPorEmail(String email) {
        if (email == null || email.trim().isEmpty()) return false;
        
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getEmail().equalsIgnoreCase(email.trim())) {
                usuarios.remove(i);
                return true;
            }
        }
        return false;
    }

    public Pessoa buscarPorId(int id) {
        for (Pessoa p : usuarios) {
            if (p.getId() == id) {
                return copiarPessoa(p);
            }
        }
        return null;
    }

    public Pessoa buscarPorEmail(String email) {
        if (email == null || email.trim().isEmpty()) return null;
        
        for (Pessoa p : usuarios) {
            if (p.getEmail().equalsIgnoreCase(email.trim())) {
                return copiarPessoa(p);
            }
        }
        return null;
    }

    public Pessoa buscarPorNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) return null;
        
        for (Pessoa p : usuarios) {
            if (p.getNome().equalsIgnoreCase(nome.trim())) {
                return copiarPessoa(p);
            }
        }
        return null;
    }

    public List<Pessoa> listarTodos() {
        List<Pessoa> copias = new ArrayList<>();
        for (Pessoa p : usuarios) {
            copias.add(copiarPessoa(p));
        }
        return copias;
    }

    public boolean atualizar(Pessoa usuarioAtualizado) {
        if (usuarioAtualizado == null) return false;
        
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getId() == usuarioAtualizado.getId()) {
                usuarios.set(i, copiarPessoa(usuarioAtualizado));
                return true;
            }
        }
        return false;
    }

    public List<Pessoa> listarPorTipo(String tipoUsuario) {
        List<Pessoa> resultado = new ArrayList<>();
        if (tipoUsuario == null || tipoUsuario.trim().isEmpty()) return resultado;
        
        for (Pessoa p : usuarios) {
            if (p.getTipoUsuario().equalsIgnoreCase(tipoUsuario.trim())) {
                resultado.add(copiarPessoa(p));
            }
        }
        return resultado;
    }

    public int proximoId() {
        return proximoId;
    }

    public int getTotalUsuarios() {
        return usuarios.size();
    }

    public boolean isEmpty() {
        return usuarios.isEmpty();
    }

    private Pessoa copiarPessoa(Pessoa original) {
        if (original == null) return null;
        
        if (original instanceof Administrador) {
            Administrador admin = (Administrador) original;
            return new Administrador(
                admin.getId(), 
                admin.getNome(), 
                admin.getEmail(), 
                admin.getSenha(), 
                admin.getNivelAcesso(), 
                admin.getDepartamento()
            );
        } else if (original instanceof Apicultor) {
            Apicultor api = (Apicultor) original;
            return new Apicultor(
                api.getId(), 
                api.getNome(), 
                api.getEmail(), 
                api.getSenha(), 
                api.getEspecialidade(), 
                api.getAnosExperiencia()
            );
        } else {
            return Pessoa.criarUsuario(
                original.getId(), 
                original.getNome(), 
                original.getEmail(), 
                original.getSenha(), 
                original.getTipoUsuario()
            );
        }
    }
}