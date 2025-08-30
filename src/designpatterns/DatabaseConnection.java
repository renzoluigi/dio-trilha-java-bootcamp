package designpatterns;

public final class DatabaseConnection {
    private static DatabaseConnection instance;
    private final String connectionStatus;

    private DatabaseConnection() {
        System.out.println("Criando a única instância de DatabaseConnection.");
        this.connectionStatus = "Conexão com o DB estabelecida!";
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public String executeQuery(String query) {
        return "Executando a consulta '" + query + "' na " + connectionStatus;
    }

    public static void main(String[] args) {
        System.out.println("--- Acessando a conexão pela primeira vez ---");
        DatabaseConnection db1 = DatabaseConnection.getInstance();
        System.out.println(db1.executeQuery("SELECT * FROM users"));

        System.out.println("\n--- Acessando a conexão pela segunda vez ---");
        DatabaseConnection db2 = DatabaseConnection.getInstance();
        System.out.println(db2.executeQuery("INSERT INTO products VALUES ('lápis')"));

        System.out.println("\nAs duas variáveis se referem à mesma instância? " + (db1 == db2));
    }
}