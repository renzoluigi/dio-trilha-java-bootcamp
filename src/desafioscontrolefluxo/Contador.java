package desafioscontrolefluxo;

import java.util.Scanner;

public class Contador {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Insira o primeiro parâmetro: ");
        int primeiroParametro = scanner.nextInt();
        System.out.print("Insira o segundo parâmetro: ");
        int segundoParametro = scanner.nextInt();

        scanner.close();

        if (primeiroParametro > segundoParametro) {
            throw new ParametrosInvalidosException("O segundo parâmetro deve ser maior que o primeiro!");
        }

        for (int i = primeiroParametro; i <= segundoParametro; i++) {
            System.out.println("Imprimindo o número " + i);
        }
    }
}
