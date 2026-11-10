package financeiro.f1;

import financeiro.modelo.RepositorioTransacoes;
import financeiro.modelo.TipoTransacao;
import financeiro.modelo.Transacao;
import java.time.LocalDate;

public class CadastroReceita {

    private final RepositorioTransacoes repo;

    public CadastroReceita(RepositorioTransacoes repo) {
        this.repo = repo;
    }

    public void cadastrar(double valor, LocalDate data, String descricao) {
        repo.adicionar(new Transacao(valor, data, descricao, TipoTransacao.RECEITA));
    }
}