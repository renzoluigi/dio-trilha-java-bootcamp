import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        String ip = scanner.nextLine();
        
        if (validarIP(ip)) {
            System.out.println("ip valido");
        } else {
            System.out.println("ip invalido");
        }
        
        scanner.close();
    }
    
    public static boolean validarIP(String ip) {
        String[] partes = ip.split("\\.");
        
        if (partes.length != 4) {
            return false;
        }
        
        for (String parte : partes) {
            if (!ehNumeroValido(parte)) {
                return false;
            }
        }
        
        return true;
    }
    
    public static boolean ehNumeroValido(String parte) {
        if (parte == null || parte.isEmpty()) {
            return false;
        }
        
        for (char c : parte.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        
        if (parte.length() > 1 && parte.charAt(0) == '0') {
            return false;
        }
        
        try {
            int numero = Integer.parseInt(parte);
            return numero >= 0 && numero <= 255;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}