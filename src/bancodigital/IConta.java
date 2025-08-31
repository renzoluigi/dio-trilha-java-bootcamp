package bancodigital;

import java.util.List;

public interface IConta {
    boolean sacar(double valor);
    boolean depositar(double valor);
    boolean transferir(double valor, Conta contaDestino);
    void imprimirExtrato();
    void imprimirExtratoDetalhado();
    boolean pagarConta(double valor, String descricao);
    boolean realizarPix(double valor, String chaveDestino);
    double consultarSaldo();
    List<Transacao> getHistorico();
    boolean validarSaldo(double valor);
}
