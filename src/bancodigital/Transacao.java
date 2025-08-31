
package bancodigital;

import java.time.LocalDateTime;

public class Transacao {
    private TipoTransacao tipo;
    private double valor;
    private LocalDateTime dataHora;
    private String descricao;
    private int contaOrigem;
    private int contaDestino;
    private double saldoAnterior;
    private double saldoPosterior;

    public Transacao(TipoTransacao tipo, double valor, String descricao, 
                    int contaOrigem, double saldoAnterior, double saldoPosterior) {
        this.tipo = tipo;
        this.valor = valor;
        this.descricao = descricao;
        this.contaOrigem = contaOrigem;
        this.saldoAnterior = saldoAnterior;
        this.saldoPosterior = saldoPosterior;
        this.dataHora = LocalDateTime.now();
    }

    public TipoTransacao getTipo() {
        return tipo;
    }

    public double getValor() {
        return valor;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getContaOrigem() {
        return contaOrigem;
    }

    public int getContaDestino() {
        return contaDestino;
    }

    public void setContaDestino(int contaDestino) {
        this.contaDestino = contaDestino;
    }

    public double getSaldoAnterior() {
        return saldoAnterior;
    }

    public double getSaldoPosterior() {
        return saldoPosterior;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - Valor: R$ %.2f - Saldo: R$ %.2f", 
                           dataHora.toString(), tipo, valor, saldoPosterior);
    }
}