package financeiro.F5;

import financeiro.modelo.RepositorioTransacoes;
import financeiro.modelo.TipoTransacao;
import financeiro.modelo.Transacao;

public class CalculadoraSaldo {

    private final RepositorioTransacoes repo;

    public CalculadoraSaldo(RepositorioTransacoes repo) {
        this.repo = repo;
    }

    public double calcularSaldo() {
        double receitas = repo.listarTodas().stream()
                .filter(t -> t.getTipo() == TipoTransacao.RECEITA)
                .mapToDouble(Transacao::getValor)
                .sum();
        double despesas = repo.listarTodas().stream()
                .filter(t -> t.getTipo() == TipoTransacao.DESPESA)
                .mapToDouble(Transacao::getValor)
                .sum();
        return receitas - despesas;
    }
}