package financeiro.F8;

import financeiro.modelo.RepositorioOrcamentos;
import financeiro.modelo.RepositorioTransacoes;
import financeiro.modelo.TipoTransacao;
import financeiro.modelo.Transacao;
import java.time.YearMonth;

public class VerificarLimite {

    private final RepositorioTransacoes transacoes;
    private final RepositorioOrcamentos orcamentos;

    public VerificarLimite(RepositorioTransacoes transacoes, RepositorioOrcamentos orcamentos) {
        this.transacoes = transacoes;
        this.orcamentos = orcamentos;
    }

    public boolean excedeu(YearMonth mes) {
        return totalDespesas(mes) > teto(mes);
    }

    public double excedente(YearMonth mes) {
        return Math.max(0, totalDespesas(mes) - teto(mes));
    }

    private double teto(YearMonth mes) {
        return orcamentos.buscar(mes)
                .orElseThrow(() -> new IllegalStateException("Não há teto de gastos definido para o mês"));
    }

    private double totalDespesas(YearMonth mes) {
        return transacoes.listarTodas().stream()
                .filter(t -> t.getTipo() == TipoTransacao.DESPESA)
                .filter(t -> YearMonth.from(t.getData()).equals(mes))
                .mapToDouble(Transacao::getValor)
                .sum();
    }
}