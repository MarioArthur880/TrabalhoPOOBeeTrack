package controle;

public class Pessoa {
    private int id;
    private String nome;
    private String email;
    private String senha;
    private String tipoUsuario;

    public Pessoa(int id, String nome, String email, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.tipoUsuario = "Usuario";
    }

    public static Pessoa criarUsuario(int id, String nome, String email, String senha, String tipoUsuario) {
        if (nome == null || nome.trim().isEmpty()) return null;
        if (email == null || email.trim().isEmpty()) return null;
        if (senha == null || senha.trim().isEmpty()) return null;
        if (tipoUsuario == null || tipoUsuario.trim().isEmpty()) return null;

        switch (tipoUsuario.toLowerCase()) {
            case "admin":
            case "administrador":
                return new Administrador(id, nome, email, senha, "Total", "Geral");
            case "apicultor":
                return new Apicultor(id, nome, email, senha, "Geral", 0);
            default:
                Pessoa pessoa = new Pessoa(id, nome, email, senha);
                pessoa.tipoUsuario = tipoUsuario;
                return pessoa;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public boolean validarSenha(String senhaDigitada) {
        return this.senha != null && this.senha.equals(senhaDigitada);
    }

    @Override
    public String toString() {
        return "Pessoa{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", tipoUsuario='" + tipoUsuario + '\'' +
                '}';
    }
}