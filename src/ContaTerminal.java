import java.util.Scanner;

public class ContaTerminal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bem vindo ao banco, vamos abrir uma conta!");

        System.out.print("Por favor digite seu nome: ");
        String nome = scanner.nextLine();

        System.out.print("Digite o número da sua agência: ");
        int agencia = scanner.nextInt();

        System.out.print("Digite o número da conta: ");
        int conta = scanner.nextInt();

        System.out.print("Digite seu saldo: ");
        int saldo = scanner.nextInt();

        scanner.close();

        System.out.println("Olá " + nome + ", obrigado por criar uma conta em nosso banco, sua agência é " +
                agencia + ", conta " + conta + " e seu saldo " + saldo + " já está disponível para saque.");
    }
}