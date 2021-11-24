package academia.entidades;

import academia.utilitarios.DataHora;
import academia.utilitarios.Decimal;
import academia.utilitarios.Textos;

import java.util.Calendar;
import java.util.Date;

public class RecebimentosAReceber {
    private Alunos aluno;

    private Turmas turma;

    public RecebimentosAReceber() {
    }

    public Alunos getAluno() {
        return aluno;
    }

    public String getNomeAluno() {
        return aluno.getNome();
    }

    public String getTelefoneAluno() {
        return Textos.formatarTelefone(aluno.getTelefone());
    }

    public String getWhatsAppAluno() {
        return Textos.formatarTelefone(aluno.getWhatsapp());
    }

    public String getDataVencimento() {
        return DataHora.formatarData(aluno.getDataPrePagamento(new Date()), "dd/MM/yyyy");
    }

    public void setAluno(Alunos aluno) {
        this.aluno = aluno;
    }

    public Turmas getTurma() {
        return turma;
    }

    public String getVMensalidade() {
        return Decimal.formatar(turma.getvMensalidade(), "#,##0.00");
    }

    public void setTurma(Turmas turma) {
        this.turma = turma;
    }
}
