package controle;

public class Apicultor extends Pessoa {
    private String especialidade;
    private int anosExperiencia;

    public Apicultor(int id, String nome, String email, String senha, String especialidade, int anosExperiencia) {
        super(id, nome, email, senha);
        this.especialidade = especialidade;
        this.anosExperiencia = anosExperiencia;
        this.setTipoUsuario("Apicultor");
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public int getAnosExperiencia() {
        return anosExperiencia;
    }

    public void setAnosExperiencia(int anosExperiencia) {
        this.anosExperiencia = anosExperiencia;
    }

    @Override
    public String toString() {
        return "Apicultor{" +
                "id=" + getId() +
                ", nome='" + getNome() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", especialidade='" + especialidade + '\'' +
                ", anosExperiencia=" + anosExperiencia +
                '}';
    }
}