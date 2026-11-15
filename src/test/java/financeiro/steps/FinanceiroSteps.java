package financeiro.steps;

import financeiro.F1.CadastroReceita;
import financeiro.F2.CadastroDespesa;
import financeiro.modelo.RepositorioTransacoes;
import io.cucumber.java.pt.*;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;
import financeiro.F5.CalculadoraSaldo;
import financeiro.modelo.Transacao;
import financeiro.F6.ConsultaMaiorDespesa;
import java.util.NoSuchElementException;
import io.cucumber.java.Before;

public class FinanceiroSteps {

    private final RepositorioTransacoes repo = new RepositorioTransacoes();
    private Exception erro;
    private double saldo;
    private Transacao maiorDespesa;

    @Quando("eu cadastro uma receita de {double} em {string} com descrição {string}")
    public void euCadastroUmaReceita(Double valor, String data, String descricao) {
        try {
            new CadastroReceita(repo).cadastrar(valor, LocalDate.parse(data), descricao);
        } catch (IllegalArgumentException e) {
            erro = e;
        }
    }
    @Quando("eu cadastro uma despesa de {double} em {string} com descrição {string}")
    public void euCadastroUmaDespesa(Double valor, String data, String descricao) {
        try {
            new CadastroDespesa(repo).cadastrar(valor, LocalDate.parse(data), descricao);
        } catch (IllegalArgumentException e) {
            erro = e;
        }
    }

    @Então("devem existir {int} transações cadastradas")
    public void devemExistirTransacoes(Integer quantidade) {
        assertEquals(quantidade, repo.listarTodas().size());
    }

    @Então("o sistema deve rejeitar com a mensagem {string}")
    public void oSistemaDeveRejeitar(String mensagem) {
        assertNotNull(erro, "Era esperado um erro, mas nenhum foi lançado");
        assertEquals(mensagem, erro.getMessage());
    }

    // ===== F5 - Calcular Saldo Atual =====
    @Dado("que eu cadastrei uma receita de {double} em {string} com descrição {string}")
    public void queEuCadastreiUmaReceita(Double valor, String data, String descricao) {
        new CadastroReceita(repo).cadastrar(valor, LocalDate.parse(data), descricao);
    }
    @Dado("que eu cadastrei uma despesa de {double} em {string} com descrição {string}")
    public void queEuCadastreiUmaDespesa(Double valor, String data, String descricao) {
        new CadastroDespesa(repo).cadastrar(valor, LocalDate.parse(data), descricao);
    }
    @Quando("eu calculo o saldo atual")
    public void euCalculoOSaldoAtual() {
        saldo = new CalculadoraSaldo(repo).calcularSaldo();
    }
    @Então("o saldo atual deve ser {double}")
    public void oSaldoAtualDeveSer(Double esperado) {
        assertEquals(esperado, saldo, 0.001);
    }

    // ===== F6 - Consultar Maior Despesa =====
    @Dado("que nenhuma transação foi cadastrada")
    public void queNenhumaTransacaoFoiCadastrada() {
        // Cada cenário já inicia com um repo novo e limpo, este passo serve como declaração explícita de pré-condição.
    }
    @Quando("eu consulto a maior despesa")
    public void euConsultoAMaiorDespesa() {
        try {
            maiorDespesa = new ConsultaMaiorDespesa(repo).consultar();
        } catch (NoSuchElementException e) {
            erro = e;
        }
    }

    @Então("a maior despesa deve ser {double}")
    public void aMaiorDespesaDeveSer(Double esperado) {
        assertEquals(esperado, maiorDespesa.getValor(), 0.001);
    }

    @Então("o sistema deve informar que não há despesas cadastradas")
    public void sistemaInformaQueNaoHaDespesas() {
        assertNotNull(erro, "Era esperado um erro, mas nenhum foi lançado");
        assertEquals("Nenhuma despesa cadastrada", erro.getMessage());
    }

}