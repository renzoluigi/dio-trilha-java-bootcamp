package bancodigital;

import java.time.LocalDateTime;

public class ContaCorrente extends Conta {
    private static final double LIMITE_PADRAO = 500.0;
    private static final double TAXA_MANUTENCAO = 12.0;
    private int pixRealizados;
    private static final int LIMITE_PIX_DIARIO = 5;

    public ContaCorrente(Cliente cliente) {
        super(cliente);
        this.limiteCredito = LIMITE_PADRAO;
        this.pixRealizados = 0;
    }

    public ContaCorrente(Cliente cliente, double limiteCredito) {
        super(cliente);
        this.limiteCredito = limiteCredito;
        this.pixRealizados = 0;
    }

    @Override
    public boolean realizarPix(double valor, String chaveDestino) {
        if (pixRealizados >= LIMITE_PIX_DIARIO) {
            System.out.println("Limite diário de PIX atingido. Máximo: " + LIMITE_PIX_DIARIO);
            return false;
        }

        if (super.realizarPix(valor, chaveDestino)) {
            pixRealizados++;
            return true;
        }
        return false;
    }

    public void cobrarTaxaManutencao() {
        if (saldo >= TAXA_MANUTENCAO) {
            double saldoAnterior = this.saldo;
            saldo -= TAXA_MANUTENCAO;
            
            Transacao transacao = new Transacao(TipoTransacao.PAGAMENTO, TAXA_MANUTENCAO,
                                              "Taxa de manutenção mensal", numero, saldoAnterior, saldo);
            historicoTransacoes.add(transacao);
            
            System.out.println("Taxa de manutenção de R$ " + TAXA_MANUTENCAO + " debitada.");
        }
    }

    public void resetarLimitePix() {
        this.pixRealizados = 0;
    }

    @Override
    public void imprimirExtrato() {
        System.out.println("\n=== Extrato Conta Corrente ===");
        imprimirInfosComuns();
        System.out.println("PIX realizados hoje: " + pixRealizados + "/" + LIMITE_PIX_DIARIO);
        
        if (historicoTransacoes.isEmpty()) {
            System.out.println("Nenhuma transação realizada.");
        } else {
            System.out.println("\nÚltimas 5 transações:");
            historicoTransacoes.stream()
                    .skip(Math.max(0, historicoTransacoes.size() - 5))
                    .forEach(System.out::println);
        }
        System.out.println("===============================\n");
    }

    // Getters
    public int getPixRealizados() {
        return pixRealizados;
    }

    public static double getTaxaManutencao() {
        return TAXA_MANUTENCAO;
    }
}
