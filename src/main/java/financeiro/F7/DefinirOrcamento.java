package financeiro.F7;

import financeiro.modelo.RepositorioOrcamentos;
import java.time.YearMonth;

public class DefinirOrcamento {

    private final RepositorioOrcamentos repo;

    public DefinirOrcamento(RepositorioOrcamentos repo) {
        this.repo = repo;
    }

    public void definir(YearMonth mes, double teto) {
        if (teto <= 0) {
            throw new IllegalArgumentException("O teto de gastos deve ser maior que zero");
        }
        repo.salvar(mes, teto);
    }
}