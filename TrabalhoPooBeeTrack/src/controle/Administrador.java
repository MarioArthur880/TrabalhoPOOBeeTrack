package controle;

public class Administrador extends Pessoa {
    private String nivelAcesso;
    private String departamento;

    public Administrador(int id, String nome, String email, String senha, String nivelAcesso, String departamento) {
        super(id, nome, email, senha);
        this.nivelAcesso = nivelAcesso;
        this.departamento = departamento;
    }
}