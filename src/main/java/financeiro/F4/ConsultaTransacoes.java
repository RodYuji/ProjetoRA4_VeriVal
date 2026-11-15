package financeiro.F4;

import financeiro.modelo.RepositorioTransacoes;
import financeiro.modelo.Transacao;
import java.util.ArrayList;
import java.util.List;

public class ConsultaTransacoes {

    private final RepositorioTransacoes repo;

    public ConsultaTransacoes(RepositorioTransacoes repo) {
        this.repo = repo;
    }

    public List<Transacao> listarPorMes(int mes) {
        if (mes < 1 || mes > 12) {
            throw new IllegalArgumentException("Mês inválido");
        }

        List<Transacao> lista = new ArrayList<>();

        for (Transacao t : repo.listarTodas()) {
            if (t.getData().getMonthValue() == mes) {
                lista.add(t);
            }
        }

        return lista;
    }
}