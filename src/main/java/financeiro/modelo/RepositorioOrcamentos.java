package financeiro.modelo;

import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RepositorioOrcamentos {

    private final Map<YearMonth, Double> tetos = new HashMap<>();

    public void salvar(YearMonth mes, double teto) {
        tetos.put(mes, teto);
    }

    public Optional<Double> buscar(YearMonth mes) {
        return Optional.ofNullable(tetos.get(mes));
    }
}