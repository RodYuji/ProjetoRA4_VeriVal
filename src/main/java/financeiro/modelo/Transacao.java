package financeiro.modelo;

import java.time.LocalDate;

public class Transacao {

    private final double valor;
    private final LocalDate data;
    private final String descricao;
    private final TipoTransacao tipo;

    public Transacao(double valor, LocalDate data, String descricao, TipoTransacao tipo) {
        this.valor = valor;
        this.data = data;
        this.descricao = descricao;
        this.tipo = tipo;
    }

    public double getValor() { return valor; }
    public LocalDate getData() { return data; }
    public String getDescricao() { return descricao; }
    public TipoTransacao getTipo() { return tipo; }
}