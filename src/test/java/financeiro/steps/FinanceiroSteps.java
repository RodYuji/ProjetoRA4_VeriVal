package financeiro.steps;

import financeiro.F1.CadastroReceita;
import financeiro.F2.CadastroDespesa;
import financeiro.modelo.RepositorioTransacoes;
import io.cucumber.java.pt.*;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;
import financeiro.F7.DefinirOrcamento;
import financeiro.modelo.RepositorioOrcamentos;
import java.time.YearMonth;
import financeiro.F8.VerificarLimite;

public class FinanceiroSteps {

    private final RepositorioTransacoes repo = new RepositorioTransacoes();
    private Exception erro;
    private final RepositorioOrcamentos repoOrcamentos = new RepositorioOrcamentos();
    private boolean limiteExcedido;
    private double valorExcedente;

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

    // ===== F7 - Definir teto de gastos =====

    @Dado("que o teto do mês {string} é {double}")
    public void queOTetoDoMesE(String mes, Double teto) {
        new DefinirOrcamento(repoOrcamentos).definir(YearMonth.parse(mes), teto);
    }

    @Quando("eu defino o teto de {double} para o mês {string}")
    public void euDefinoOTeto(Double teto, String mes) {
        try {
            new DefinirOrcamento(repoOrcamentos).definir(YearMonth.parse(mes), teto);
        } catch (IllegalArgumentException e) {
            erro = e;
        }
    }

    @Então("o teto do mês {string} deve ser {double}")
    public void oTetoDoMesDeveSer(String mes, Double esperado) {
        assertEquals(esperado, repoOrcamentos.buscar(YearMonth.parse(mes)).orElseThrow());
    }

    // ===== F8 - Verificar alerta de limite excedido =====

    @Dado("que existe uma despesa de {double} em {string}")
    public void queExisteUmaDespesa(Double valor, String data) {
        new CadastroDespesa(repo).cadastrar(valor, LocalDate.parse(data), "Despesa");
    }

    @Quando("eu verifico o limite do mês {string}")
    public void euVerificoOLimite(String mes) {
        try {
            VerificarLimite verificador = new VerificarLimite(repo, repoOrcamentos);
            YearMonth ym = YearMonth.parse(mes);
            limiteExcedido = verificador.excedeu(ym);
            valorExcedente = verificador.excedente(ym);
        } catch (IllegalStateException e) {
            erro = e;
        }
    }

    @Então("o limite deve estar excedido em {double}")
    public void oLimiteDeveEstarExcedido(Double excedente) {
        assertTrue(limiteExcedido, "Era esperado limite excedido");
        assertEquals(excedente, valorExcedente, 0.001);
    }

    @Então("o limite não deve estar excedido")
    public void oLimiteNaoDeveEstarExcedido() {
        assertFalse(limiteExcedido, "Não era esperado limite excedido");
    }
}