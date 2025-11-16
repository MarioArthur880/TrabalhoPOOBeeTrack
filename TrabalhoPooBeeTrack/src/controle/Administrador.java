package controle;

public class Administrador extends Pessoa {
    private String nivelAcesso;
    private String departamento;

    public Administrador(int id, String nome, String email, String senha, String nivelAcesso, String departamento) {
        super(id, nome, email, senha);
        this.nivelAcesso = nivelAcesso;
        this.departamento = departamento;
        this.setTipoUsuario("Admin");
    }

    public String getNivelAcesso() {
        return nivelAcesso;
    }

    public void setNivelAcesso(String nivelAcesso) {
        this.nivelAcesso = nivelAcesso;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    @Override
    public String toString() {
        return "Administrador{" +
                "id=" + getId() +
                ", nome='" + getNome() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", nivelAcesso='" + nivelAcesso + '\'' +
                ", departamento='" + departamento + '\'' +
                '}';
    }
}