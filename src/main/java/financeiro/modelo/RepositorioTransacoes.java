package financeiro.modelo;

import java.util.ArrayList;
import java.util.List;

public class RepositorioTransacoes {

    private final List<Transacao> transacoes = new ArrayList<>();

    public void adicionar(Transacao transacao) {
        transacoes.add(transacao);
    }

    public List<Transacao> listarTodas() {
        return transacoes;
    }
}