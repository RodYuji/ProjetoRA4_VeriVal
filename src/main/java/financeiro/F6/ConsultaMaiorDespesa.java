package financeiro.F6;

import financeiro.modelo.RepositorioTransacoes;
import financeiro.modelo.TipoTransacao;
import financeiro.modelo.Transacao;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class ConsultaMaiorDespesa {

    private final RepositorioTransacoes repo;

    public ConsultaMaiorDespesa(RepositorioTransacoes repo) {
        this.repo = repo;
    }

    public Transacao consultar() {
        return repo.listarTodas().stream()
                .filter(t -> t.getTipo() == TipoTransacao.DESPESA)
                .max(Comparator.comparingDouble(Transacao::getValor))
                .orElseThrow(() -> new NoSuchElementException("Nenhuma despesa cadastrada"));
    }
}