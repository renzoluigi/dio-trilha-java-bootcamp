package bancodigital;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ContaPoupanca extends Conta {
    private static final double RENDIMENTO_MENSAL = 0.005; // 0.5% ao mês
    private LocalDate ultimoRendimento;
    private double totalRendimentos;
    private static final double LIMITE_SAQUE_DIARIO = 1000.0;
    private double saquesRealizadosHoje;
    private LocalDate dataSaques;

    public ContaPoupanca(Cliente cliente) {
        super(cliente);
        this.ultimoRendimento = LocalDate.now();
        this.totalRendimentos = 0.0;
        this.saquesRealizadosHoje = 0.0;
        this.dataSaques = LocalDate.now();
        this.limiteCredito = 0.0; // Poupança não tem limite de crédito
    }

    @Override
    public boolean sacar(double valor) {
        if (!validarLimiteSaque(valor)) {
            System.out.println("Limite diário de saque excedido. Limite: R$ " + LIMITE_SAQUE_DIARIO);
            return false;
        }

        if (super.sacar(valor)) {
            atualizarSaquesRealizados(valor);
            return true;
        }
        return false;
    }

    @Override
    public boolean realizarPix(double valor, String chaveDestino) {
        System.out.println("PIX não disponível para conta poupança. Use transferência.");
        return false;
    }

    private boolean validarLimiteSaque(double valor) {
        if (!dataSaques.equals(LocalDate.now())) {
            saquesRealizadosHoje = 0.0;
            dataSaques = LocalDate.now();
        }
        return (saquesRealizadosHoje + valor) <= LIMITE_SAQUE_DIARIO;
    }

    private void atualizarSaquesRealizados(double valor) {
        if (!dataSaques.equals(LocalDate.now())) {
            saquesRealizadosHoje = 0.0;
            dataSaques = LocalDate.now();
        }
        saquesRealizadosHoje += valor;
    }

    public void aplicarRendimento() {
        LocalDate hoje = LocalDate.now();
        if (ultimoRendimento.getMonth() != hoje.getMonth() || 
            ultimoRendimento.getYear() != hoje.getYear()) {
            
            double rendimento = saldo * RENDIMENTO_MENSAL;
            if (rendimento > 0) {
                double saldoAnterior = this.saldo;
                saldo += rendimento;
                totalRendimentos += rendimento;
                ultimoRendimento = hoje;
                
                Transacao transacao = new Transacao(TipoTransacao.RENDIMENTO, rendimento,
                                                  "Rendimento mensal da poupança", numero, saldoAnterior, saldo);
                historicoTransacoes.add(transacao);
                
                System.out.println("Rendimento de R$ " + String.format("%.2f", rendimento) + " aplicado!");
            }
        }
    }

    public double simularRendimento(int meses) {
        double saldoSimulado = saldo;
        for (int i = 0; i < meses; i++) {
            saldoSimulado += saldoSimulado * RENDIMENTO_MENSAL;
        }
        return saldoSimulado;
    }

    @Override
    public void imprimirExtrato() {
        System.out.println("\n=== Extrato Conta Poupança ===");
        imprimirInfosComuns();
        System.out.println("Total de Rendimentos: R$ " + String.format("%.2f", totalRendimentos));
        System.out.println("Último Rendimento: " + ultimoRendimento);
        System.out.println("Saques realizados hoje: R$ " + String.format("%.2f", saquesRealizadosHoje));
        System.out.println("Limite restante hoje: R$ " + String.format("%.2f", LIMITE_SAQUE_DIARIO - saquesRealizadosHoje));
        
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

    public LocalDate getUltimoRendimento() {
        return ultimoRendimento;
    }

    public double getTotalRendimentos() {
        return totalRendimentos;
    }

    public static double getRendimentoMensal() {
        return RENDIMENTO_MENSAL;
    }

    public double getSaquesRealizadosHoje() {
        return saquesRealizadosHoje;
    }
}
