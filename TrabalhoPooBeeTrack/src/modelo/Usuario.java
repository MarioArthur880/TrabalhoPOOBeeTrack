package modelo;

public class Usuario {
    private String nome;
    private String email;
    private String senha;
    private String tipoUsuario;

    // Construtor principal
    public Usuario(String nome, String email, String senha, String tipoUsuario) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.tipoUsuario = tipoUsuario;
    }

    // Construtor de cópia
    public Usuario(Usuario outro) {
        this.nome = outro.nome;
        this.email = outro.email;
        this.senha = outro.senha;
        this.tipoUsuario = outro.tipoUsuario;
    }

    // Método fábrica com validações básicas
    public static Usuario criarUsuario(String nome, String email, String senha, String tipoUsuario) {
        if (nome == null || nome.trim().isEmpty() ||
            email == null || email.trim().isEmpty() ||
            senha == null || senha.trim().isEmpty() ||
            tipoUsuario == null || tipoUsuario.trim().isEmpty()) {
            return null;
        }

        return new Usuario(nome, email, senha, tipoUsuario);
    }

    // Getters
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

    // Setters
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
