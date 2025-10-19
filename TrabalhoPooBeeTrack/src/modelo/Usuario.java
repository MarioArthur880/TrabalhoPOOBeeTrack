package modelo;

public class Usuario {
    private int id;
    private String nome;
    private String email;
    private String senha;
    private String tipoUsuario;

    public Usuario(int id, String nome, String email, String senha, String tipoUsuario) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.tipoUsuario = tipoUsuario;
    }

    public Usuario(Usuario outro) {
        this.id = outro.id;
        this.nome = outro.nome;
        this.email = outro.email;
        this.senha = outro.senha;
        this.tipoUsuario = outro.tipoUsuario;
    }

    public static Usuario criarUsuario(int id, String nome, String email, String senha, String tipoUsuario) {
        if (nome == null || nome.trim().isEmpty() ||
            email == null || email.trim().isEmpty() ||
            senha == null || senha.trim().isEmpty() ||
            tipoUsuario == null || tipoUsuario.trim().isEmpty()) {
            return null;
        }

        return new Usuario(id, nome, email, senha, tipoUsuario);
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
