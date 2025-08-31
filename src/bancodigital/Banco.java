package bancodigital;

import java.util.ArrayList;
import java.util.List;

public class Banco {
    private String nome;
    private String codigo;
    private List<Conta> contas;
    private List<Cliente> clientes;

    public Banco(String nome, String codigo) {
        this.nome = nome;
        this.codigo = codigo;
        this.contas = new ArrayList<>();
        this.clientes = new ArrayList<>();
    }

    public void adicionarCliente(Cliente cliente) {
        if (!clientes.contains(cliente)) {
            clientes.add(cliente);
            System.out.println("Cliente " + cliente.getNome() + " adicionado ao banco.");
        }
    }

    public Conta criarContaCorrente(Cliente cliente) {
        adicionarCliente(cliente);
        ContaCorrente conta = new ContaCorrente(cliente);
        contas.add(conta);
        System.out.println("Conta Corrente criada para " + cliente.getNome() + 
                          " - Agência: " + conta.getAgencia() + 
                          " - Conta: " + conta.getNumero());
        return conta;
    }

    public Conta criarContaCorrente(Cliente cliente, double limiteCredito) {
        adicionarCliente(cliente);
        ContaCorrente conta = new ContaCorrente(cliente, limiteCredito);
        contas.add(conta);
        System.out.println("Conta Corrente criada para " + cliente.getNome() + 
                          " - Agência: " + conta.getAgencia() + 
                          " - Conta: " + conta.getNumero() +
                          " - Limite: R$ " + limiteCredito);
        return conta;
    }

    public Conta criarContaPoupanca(Cliente cliente) {
        adicionarCliente(cliente);
        ContaPoupanca conta = new ContaPoupanca(cliente);
        contas.add(conta);
        System.out.println("Conta Poupança criada para " + cliente.getNome() + 
                          " - Agência: " + conta.getAgencia() + 
                          " - Conta: " + conta.getNumero());
        return conta;
    }

    public Conta buscarConta(int numeroConta) {
        return contas.stream()
                    .filter(conta -> conta.getNumero() == numeroConta)
                    .findFirst()
                    .orElse(null);
    }

    public Cliente buscarCliente(String cpf) {
        return clientes.stream()
                      .filter(cliente -> cliente.getCpf().equals(cpf))
                      .findFirst()
                      .orElse(null);
    }

    public List<Conta> listarContasCliente(String cpf) {
        return contas.stream()
                    .filter(conta -> conta.getCliente().getCpf().equals(cpf))
                    .toList();
    }

    public void processarRendimentosPoupanca() {
        contas.stream()
              .filter(conta -> conta instanceof ContaPoupanca)
              .map(conta -> (ContaPoupanca) conta)
              .forEach(ContaPoupanca::aplicarRendimento);
    }

    public void cobrarTaxasManutencao() {
        contas.stream()
              .filter(conta -> conta instanceof ContaCorrente)
              .map(conta -> (ContaCorrente) conta)
              .forEach(ContaCorrente::cobrarTaxaManutencao);
    }

    public void imprimirRelatorioGeral() {
        System.out.println("\n=== RELATÓRIO GERAL DO BANCO ===");
        System.out.println("Banco: " + nome + " (" + codigo + ")");
        System.out.println("Total de Clientes: " + clientes.size());
        System.out.println("Total de Contas: " + contas.size());
        
        long contasCorrente = contas.stream()
                                   .filter(conta -> conta instanceof ContaCorrente)
                                   .count();
        long contasPoupanca = contas.stream()
                                   .filter(conta -> conta instanceof ContaPoupanca)
                                   .count();
        
        System.out.println("Contas Correntes: " + contasCorrente);
        System.out.println("Contas Poupança: " + contasPoupanca);
        
        double saldoTotal = contas.stream()
                                 .mapToDouble(Conta::getSaldo)
                                 .sum();
        
        System.out.println("Saldo Total: R$ " + String.format("%.2f", saldoTotal));
        System.out.println("=================================\n");
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public List<Conta> getContas() {
        return new ArrayList<>(contas);
    }

    public List<Cliente> getClientes() {
        return new ArrayList<>(clientes);
    }
}
