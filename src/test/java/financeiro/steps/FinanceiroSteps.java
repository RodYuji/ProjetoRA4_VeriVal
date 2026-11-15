package financeiro.steps;

import financeiro.F1.CadastroReceita;
import financeiro.F2.CadastroDespesa;
import financeiro.F3.GerenciadorCategorias;
import financeiro.F4.ConsultaTransacoes;
import financeiro.F5.CalculadoraSaldo;
import financeiro.F6.ConsultaMaiorDespesa;
import financeiro.F7.DefinirOrcamento;
import financeiro.F8.VerificarLimite;
import financeiro.modelo.RepositorioOrcamentos;
import financeiro.modelo.RepositorioTransacoes;
import financeiro.modelo.Transacao;
import io.cucumber.java.pt.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;

public class FinanceiroSteps {

    private final RepositorioTransacoes repo = new RepositorioTransacoes();
    private final RepositorioOrcamentos repoOrcamentos = new RepositorioOrcamentos();
    private final GerenciadorCategorias gerenciadorCategorias = new GerenciadorCategorias();
    private final ConsultaTransacoes consulta = new ConsultaTransacoes(repo);

    private Exception erro;
    private Exception erroCategoria;
    private Exception erroConsulta;

    private boolean limiteExcedido;
    private double valorExcedente;
    private double saldo;
    private Transacao maiorDespesa;
    private List<Transacao> listaMes;

    // ==========================================
    // F1 - Cadastrar Receita
    // ==========================================
    @Quando("eu cadastro uma receita de {double} em {string} com descrição {string}")
    public void euCadastroUmaReceita(Double valor, String data, String descricao) {
        try {
            new CadastroReceita(repo).cadastrar(valor, LocalDate.parse(data), descricao);
        } catch (IllegalArgumentException e) {
            erro = e;
        }
    }

    // ==========================================
    // F2 - Cadastrar Despesa
    // ==========================================
    @Quando("eu cadastro uma despesa de {double} em {string} com descrição {string}")
    public void euCadastroUmaDespesa(Double valor, String data, String descricao) {
        try {
            new CadastroDespesa(repo).cadastrar(valor, LocalDate.parse(data), descricao);
        } catch (IllegalArgumentException e) {
            erro = e;
        }
    }

    @Dado("que eu cadastro uma despesa de {double} em {string} com descrição {string}")
    public void queEuCadastroUmaDespesa(Double valor, String data, String descricao) {
        new CadastroDespesa(repo).cadastrar(valor, LocalDate.parse(data), descricao);
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

    // ==========================================
    // F3 - Cadastrar Categoria (Giovanna)
    // ==========================================
    @Dado("que a categoria {string} já foi cadastrada")
    public void queACategoriaJaFoiCadastrada(String nome) {
        gerenciadorCategorias.cadastrar(nome);
    }

    @Quando("eu cadastro a categoria {string}")
    public void euCadastroACategoria(String nome) {
        try {
            gerenciadorCategorias.cadastrar(nome);
        } catch (IllegalArgumentException e) {
            erroCategoria = e;
        }
    }

    @Então("a categoria {string} deve estar cadastrada")
    public void aCategoriaDeveEstarCadastrada(String nome) {
        assertTrue(gerenciadorCategorias.existe(nome));
    }

    @Então("o sistema deve conter {int} categorias cadastradas")
    public void oSistemaDeveConterCategoriasCadastradas(Integer quantidade) {
        assertEquals(quantidade, gerenciadorCategorias.listarTodas().size());
    }

    @Então("o sistema deve rejeitar a categoria com a mensagem {string}")
    public void oSistemaDeveRejeitarACategoriaComAMensagem(String mensagemEsperada) {
        assertNotNull(erroCategoria, "Esperava-se um erro, mas a operação foi aceita");
        assertEquals(mensagemEsperada, erroCategoria.getMessage());
    }

    // ==========================================
    // F4 - Listar Transações do Mês (Giovanna)
    // ==========================================
    @Quando("eu listo as transações do mês {int}")
    public void euListoAsTransacoesDoMes(Integer mes) {
        try {
            listaMes = consulta.listarPorMes(mes);
        } catch (IllegalArgumentException e) {
            erroConsulta = e;
        }
    }

    @Então("devem ser encontradas {int} transações")
    public void devemSerEncontradasTransacoes(Integer qtd) {
        assertEquals(qtd, listaMes.size());
    }

    @Então("o sistema deve rejeitar a consulta com a mensagem {string}")
    public void oSistemaDeveRejeitarAConsultaComAMensagem(String msgEsperada) {
        assertNotNull(erroConsulta, "Esperava-se um erro, mas nenhum foi lançado");
        assertEquals(msgEsperada, erroConsulta.getMessage());
    }

    // ==========================================
    // F5 - Calcular Saldo Atual (Emilly)
    // ==========================================
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

    // ==========================================
    // F6 - Consultar Maior Despesa (Emilly)
    // ==========================================
    @Dado("que nenhuma transação foi cadastrada")
    public void queNenhumaTransacaoFoiCadastrada() {
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

    // ==========================================
    // F7 - Definir Teto de Gastos (Lucas)
    // ==========================================
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

    // ==========================================
    // F8 - Verificar Alerta de Limite Excedido (Lucas)
    // ==========================================
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