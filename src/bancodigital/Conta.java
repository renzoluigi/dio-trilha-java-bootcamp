package bancodigital;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class Conta implements IConta {
    protected static int AGENCIA_PADRAO = 1;
    private static int SEQUENCIAL = 1;

    protected int agencia;
    protected int numero;
    protected double saldo;
    protected Cliente cliente;
    protected LocalDateTime dataAbertura;
    protected boolean ativa;
    protected List<Transacao> historicoTransacoes;
    protected double limiteCredito;

    public Conta(Cliente cliente) {
        this.agencia = AGENCIA_PADRAO;
        this.numero = SEQUENCIAL++;
        this.cliente = cliente;
        this.saldo = 0.0;
        this.dataAbertura = LocalDateTime.now();
        this.ativa = true;
        this.historicoTransacoes = new ArrayList<>();
        this.limiteCredito = 0.0;
    }

    @Override
    public boolean sacar(double valor) {
        if (!validarOperacao(valor)) {
            return false;
        }

        if (!validarSaldo(valor)) {
            System.out.println("Saldo insuficiente para saque de R$ " + valor);
            return false;
        }

        double saldoAnterior = this.saldo;
        saldo -= valor;
        
        Transacao transacao = new Transacao(TipoTransacao.SAQUE, valor, 
                                          "Saque em conta", numero, saldoAnterior, saldo);
        historicoTransacoes.add(transacao);
        
        System.out.println("Saque de R$ " + valor + " realizado com sucesso!");
        return true;
    }

    @Override
    public boolean depositar(double valor) {
        if (!validarOperacao(valor)) {
            return false;
        }

        double saldoAnterior = this.saldo;
        saldo += valor;
        
        Transacao transacao = new Transacao(TipoTransacao.DEPOSITO, valor, 
                                          "Depósito em conta", numero, saldoAnterior, saldo);
        historicoTransacoes.add(transacao);
        
        System.out.println("Depósito de R$ " + valor + " realizado com sucesso!");
        return true;
    }

    @Override
    public boolean transferir(double valor, Conta contaDestino) {
        if (!validarOperacao(valor) || contaDestino == null) {
            return false;
        }

        if (!validarSaldo(valor)) {
            System.out.println("Saldo insuficiente para transferência de R$ " + valor);
            return false;
        }

        double saldoAnteriorOrigem = this.saldo;
        this.saldo -= valor;
        
        double saldoAnteriorDestino = contaDestino.saldo;
        contaDestino.saldo += valor;

        Transacao transacaoEnviada = new Transacao(TipoTransacao.TRANSFERENCIA_ENVIADA, valor,
                                                  "Transferência para conta " + contaDestino.numero,
                                                  this.numero, saldoAnteriorOrigem, this.saldo);
        transacaoEnviada.setContaDestino(contaDestino.numero);
        this.historicoTransacoes.add(transacaoEnviada);

        Transacao transacaoRecebida = new Transacao(TipoTransacao.TRANSFERENCIA_RECEBIDA, valor,
                                                   "Transferência recebida da conta " + this.numero,
                                                   contaDestino.numero, saldoAnteriorDestino, contaDestino.saldo);
        transacaoRecebida.setContaDestino(this.numero);
        contaDestino.historicoTransacoes.add(transacaoRecebida);

        System.out.println("Transferência de R$ " + valor + " realizada com sucesso!");
        return true;
    }

    @Override
    public boolean pagarConta(double valor, String descricao) {
        if (!validarOperacao(valor)) {
            return false;
        }

        if (!validarSaldo(valor)) {
            System.out.println("Saldo insuficiente para pagamento de R$ " + valor);
            return false;
        }

        double saldoAnterior = this.saldo;
        saldo -= valor;
        
        Transacao transacao = new Transacao(TipoTransacao.PAGAMENTO, valor, 
                                          descricao, numero, saldoAnterior, saldo);
        historicoTransacoes.add(transacao);
        
        System.out.println("Pagamento de R$ " + valor + " realizado com sucesso!");
        return true;
    }

    @Override
    public boolean realizarPix(double valor, String chaveDestino) {
        if (!validarOperacao(valor)) {
            return false;
        }

        if (!validarSaldo(valor)) {
            System.out.println("Saldo insuficiente para PIX de R$ " + valor);
            return false;
        }

        double saldoAnterior = this.saldo;
        saldo -= valor;
        
        Transacao transacao = new Transacao(TipoTransacao.PIX_ENVIADO, valor, 
                                          "PIX para " + chaveDestino, numero, saldoAnterior, saldo);
        historicoTransacoes.add(transacao);
        
        System.out.println("PIX de R$ " + valor + " enviado com sucesso para " + chaveDestino);
        return true;
    }

    @Override
    public boolean validarSaldo(double valor) {
        return (saldo + limiteCredito) >= valor;
    }

    @Override
    public double consultarSaldo() {
        return saldo;
    }

    @Override
    public List<Transacao> getHistorico() {
        return new ArrayList<>(historicoTransacoes);
    }

    private boolean validarOperacao(double valor) {
        if (!ativa) {
            System.out.println("Conta inativa. Operação não permitida.");
            return false;
        }
        
        if (valor <= 0) {
            System.out.println("Valor deve ser maior que zero.");
            return false;
        }
        
        return true;
    }

    @Override
    public void imprimirExtrato() {
        System.out.println("\n=== EXTRATO BANCÁRIO ===");
        imprimirInfosComuns();
        
        if (historicoTransacoes.isEmpty()) {
            System.out.println("Nenhuma transação realizada.");
        } else {
            System.out.println("\nÚltimas 5 transações:");
            historicoTransacoes.stream()
                    .skip(Math.max(0, historicoTransacoes.size() - 5))
                    .forEach(System.out::println);
        }
        System.out.println("========================\n");
    }

    @Override
    public void imprimirExtratoDetalhado() {
        System.out.println("\n=== EXTRATO DETALHADO ===");
        imprimirInfosComuns();
        
        if (historicoTransacoes.isEmpty()) {
            System.out.println("Nenhuma transação realizada.");
        } else {
            System.out.println("\nTodas as transações:");
            historicoTransacoes.forEach(System.out::println);
        }
        System.out.println("==========================\n");
    }

    public int getAgencia() {
        return agencia;
    }

    public int getNumero() {
        return numero;
    }

    public double getSaldo() {
        return saldo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public LocalDateTime getDataAbertura() {
        return dataAbertura;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }

    public double getLimiteCredito() {
        return limiteCredito;
    }

    public void setLimiteCredito(double limiteCredito) {
        this.limiteCredito = limiteCredito;
    }

    protected void imprimirInfosComuns() {
        System.out.println("Titular: " + this.cliente.getNome());
        System.out.println("CPF: " + this.cliente.getCpf());
        System.out.println("Agência: " + this.agencia);
        System.out.println("Conta: " + this.numero);
        System.out.println("Saldo: R$ " + String.format("%.2f", this.saldo));
        System.out.println("Limite de Crédito: R$ " + String.format("%.2f", this.limiteCredito));
        System.out.println("Saldo + Limite: R$ " + String.format("%.2f", this.saldo + this.limiteCredito));
        System.out.println("Data de Abertura: " + this.dataAbertura.toLocalDate());
        System.out.println("Status: " + (ativa ? "Ativa" : "Inativa"));
    }
}
