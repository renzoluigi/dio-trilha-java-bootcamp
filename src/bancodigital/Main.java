package bancodigital;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Banco banco = new Banco("Banco Digital DIO", "001");
        
        Cliente renzo = new Cliente("Renzo Silva", "123.456.789-00");
        renzo.setEmail("renzo@email.com");
        renzo.setTelefone("(11) 99999-9999");
        renzo.setDataNascimento(LocalDate.of(1990, 5, 15));
        
        Endereco enderecoRenzo = new Endereco("Rua das Flores", "123", "Centro", 
                                             "São Paulo", "SP", "01234-567");
        renzo.setEndereco(enderecoRenzo);
        
        Cliente maria = new Cliente("Maria Santos", "987.654.321-00");
        maria.setEmail("maria@email.com");
        maria.setTelefone("(11) 88888-8888");
        
        ContaCorrente ccRenzo = (ContaCorrente) banco.criarContaCorrente(renzo, 1000.0);
        ContaPoupanca poupancaRenzo = (ContaPoupanca) banco.criarContaPoupanca(renzo);
        ContaCorrente ccMaria = (ContaCorrente) banco.criarContaCorrente(maria);
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("DEMONSTRAÇÃO DO SISTEMA BANCÁRIO");
        System.out.println("=".repeat(50));
        
        System.out.println("\n--- DEPÓSITOS ---");
        ccRenzo.depositar(1000.0);
        poupancaRenzo.depositar(500.0);
        ccMaria.depositar(750.0);
        
        System.out.println("\n--- SAQUES ---");
        ccRenzo.sacar(200.0);
        poupancaRenzo.sacar(100.0);
        ccMaria.sacar(50.0);
        
        System.out.println("\n--- TRANSFERÊNCIAS ---");
        ccRenzo.transferir(150.0, ccMaria);
        
        System.out.println("\n--- PIX ---");
        ccRenzo.realizarPix(100.0, "maria@email.com");
        poupancaRenzo.realizarPix(50.0, "alguem@email.com");
        
        System.out.println("\n--- PAGAMENTOS ---");
        ccRenzo.pagarConta(80.0, "Conta de luz");
        ccMaria.pagarConta(120.0, "Conta de internet");
        
        System.out.println("\n--- APLICAÇÃO DE RENDIMENTO ---");
        poupancaRenzo.aplicarRendimento();
        
        System.out.println("\n--- EXTRATOS ---");
        ccRenzo.imprimirExtrato();
        poupancaRenzo.imprimirExtrato();
        ccMaria.imprimirExtrato();
        
        System.out.println("\n--- SIMULAÇÃO DE RENDIMENTO ---");
        double simulacao = poupancaRenzo.simularRendimento(12);
        System.out.println("Saldo simulado após 12 meses: R$ " + String.format("%.2f", simulacao));
        
        System.out.println("\n--- RELATÓRIO GERAL ---");
        banco.imprimirRelatorioGeral();
        
        System.out.println("\n--- PROCESSAMENTO MENSAL ---");
        banco.processarRendimentosPoupanca();
        banco.cobrarTaxasManutencao();
        
        System.out.println("\n--- EXTRATO DETALHADO ---");
        ccRenzo.imprimirExtratoDetalhado();
        
        System.out.println("Demonstração finalizada!");
    }
}
