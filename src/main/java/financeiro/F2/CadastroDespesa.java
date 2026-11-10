package financeiro.F2;

import financeiro.modelo.RepositorioTransacoes;
import financeiro.modelo.TipoTransacao;
import financeiro.modelo.Transacao;
import java.time.LocalDate;

public class CadastroDespesa {

    private final RepositorioTransacoes repo;

    public CadastroDespesa(RepositorioTransacoes repo) {
        this.repo = repo;
    }

    public void cadastrar(double valor, LocalDate data, String descricao) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor deve ser maior que zero");
        }
        repo.adicionar(new Transacao(valor, data, descricao, TipoTransacao.DESPESA));
    }
}