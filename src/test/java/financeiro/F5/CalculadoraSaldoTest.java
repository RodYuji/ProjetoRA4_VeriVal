package financeiro.F5;

import financeiro.F1.CadastroReceita;
import financeiro.F2.CadastroDespesa;
import financeiro.modelo.RepositorioTransacoes;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class CalculadoraSaldoTest  {

    @Test
    void deveRetornarSaldoZeroSemTransacoes() {
        RepositorioTransacoes repo = new RepositorioTransacoes();
        CalculadoraSaldo calculadora = new CalculadoraSaldo(repo);
        assertEquals(0.0, calculadora.calcularSaldo(), 0.001);
    }

    @Test
    void deveRetornarSaldoZeroComTransaçõesEquivalentes() {
        RepositorioTransacoes repo = new RepositorioTransacoes();

        CadastroReceita receita = new CadastroReceita(repo);
        receita.cadastrar(500.00, LocalDate.of(2026, 11, 5), "Auxílio");

        CadastroDespesa despesa = new CadastroDespesa(repo);
        despesa.cadastrar(500.00, LocalDate.of(2026, 11, 6), "Aluguel");

        CalculadoraSaldo calculadora = new CalculadoraSaldo(repo);
        assertEquals(0.0, calculadora.calcularSaldo(), 0.001);
    }

    @Test
    void deveCalcularSaldoPositivo() {
        RepositorioTransacoes repo = new RepositorioTransacoes();

        CadastroReceita receita = new CadastroReceita(repo);
        receita.cadastrar(3000.00, LocalDate.of(2026, 11, 5), "Salario");

        CadastroDespesa despesa = new CadastroDespesa(repo);
        despesa.cadastrar(150.00, LocalDate.of(2026, 11, 6), "Mercado");

        CalculadoraSaldo calculadora = new CalculadoraSaldo(repo);
        assertEquals(2850.0, calculadora.calcularSaldo(), 0.001);
    }

    @Test
    void deveCalcularSaldoNegativo() {
        RepositorioTransacoes repo = new RepositorioTransacoes();

        CadastroReceita receita = new CadastroReceita(repo);
        receita.cadastrar(150.00, LocalDate.of(2026, 11, 5), "Salario");

        CadastroDespesa despesa = new CadastroDespesa(repo);
        despesa.cadastrar(200.00, LocalDate.of(2026, 11, 6), "Mercado");

        CalculadoraSaldo calculadora = new CalculadoraSaldo(repo);
        assertEquals(-50.0, calculadora.calcularSaldo(), 0.001);
    }

}
