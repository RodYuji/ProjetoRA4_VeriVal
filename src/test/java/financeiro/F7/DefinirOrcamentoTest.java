package financeiro.F7;

import financeiro.modelo.RepositorioOrcamentos;
import org.junit.jupiter.api.Test;
import java.time.YearMonth;
import static org.junit.jupiter.api.Assertions.*;

class DefinirOrcamentoTest {

    private static final YearMonth NOVEMBRO = YearMonth.of(2026, 11);

    @Test
    void deveDefinirTetoValido() {
        RepositorioOrcamentos repo = new RepositorioOrcamentos();
        DefinirOrcamento definir = new DefinirOrcamento(repo);

        definir.definir(NOVEMBRO, 3000.0);

        assertEquals(3000.0, repo.buscar(NOVEMBRO).orElseThrow());
    }

    @Test
    void deveRejeitarTetoComValorZero() {
        RepositorioOrcamentos repo = new RepositorioOrcamentos();
        DefinirOrcamento definir = new DefinirOrcamento(repo);

        IllegalArgumentException erro = assertThrows(IllegalArgumentException.class,
                () -> definir.definir(NOVEMBRO, 0.0));

        assertEquals("O teto de gastos deve ser maior que zero", erro.getMessage());
        assertTrue(repo.buscar(NOVEMBRO).isEmpty());
    }

    @Test
    void deveSubstituirTetoDeMesJaDefinido() {
        RepositorioOrcamentos repo = new RepositorioOrcamentos();
        DefinirOrcamento definir = new DefinirOrcamento(repo);

        definir.definir(NOVEMBRO, 3000.0);
        definir.definir(NOVEMBRO, 3500.0);

        assertEquals(3500.0, repo.buscar(NOVEMBRO).orElseThrow());
    }

    @Test
    void deveRejeitarTetoNegativo() {
        DefinirOrcamento definir = new DefinirOrcamento(new RepositorioOrcamentos());

        assertThrows(IllegalArgumentException.class, () -> definir.definir(NOVEMBRO, -100.0));
    }
}