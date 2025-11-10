package Repositório;

import controle.Pessoa;
import controle.Apiario;
import java.util.ArrayList;
import java.util.List;

public class RepositorioUsuario {
    private List<Pessoa> usuarios = new ArrayList<>();

    public void adicionar(Pessoa usuario) {
        usuarios.add(usuario);
    }

    public void remover(int id) // throws usuario não encontado exceção
    {
        Pessoa usuario = buscar(id);
        usuarios.remove(usuario);
    }

    // public Pessoa buscar(int id) // throws usuario não encontado exceção
    // {
    //     // return 
    //     // usuarios.stream().filter(u -> u.getId() == id).findFirst().orElseThrow(() 
    //     // -> new usuarionãoencontadoexceção("Usuário não encontrado"));
    // }

    public List<Pessoa> listar() {
        return new ArrayList<>(usuarios);
    }

    public void atualizar(Pessoa usuario) // throws usuario não encontado exceção
    {
        Pessoa existente = buscar(usuario.getId());
        int index = usuarios.indexOf(existente);
        usuarios.set(index, usuario);
    }

    private Pessoa buscar(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buscar'");
    }

    public String getTipoUsuario() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTipoUsuario'");
    }
}

// Repita para RepositorioApiario (List<Apiario>), RepositorioVisita (List<Visita>), RepositorioProducao (List<Producao>)