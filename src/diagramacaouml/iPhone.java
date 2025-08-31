package diagramacaouml;

public class iPhone implements ReprodutorMusical, AparelhoTelefonico, NavegadorNaInternet {

    public static void main(String[] args) {
        iPhone meuIphone = new iPhone();

        System.out.println("---- Testando o Reprodutor Musical ----");
        meuIphone.tocar();
        meuIphone.selecionarMusica();
        meuIphone.pausar();
        System.out.println();

        System.out.println("---- Testando o Aparelho Telefônico ----");
        meuIphone.ligar();
        meuIphone.atender();
        meuIphone.iniciarCorreioVoz();
        System.out.println();

        System.out.println("---- Testando o Navegador na Internet ----");
        meuIphone.exibirPagina();
        meuIphone.adicionarNovaAba();
        meuIphone.atualizarPagina();
    }

    // ReprodutorMusical
    @Override
    public void tocar() {
        System.out.println("Música tocando.");
    }

    @Override
    public void pausar() {
        System.out.println("Música pausada.");
    }

    @Override
    public void selecionarMusica() {
        System.out.println("Música selecionada.");
    }

    // AparelhoTelefonico
    @Override
    public void ligar() {
        System.out.println("Ligando para um contato.");
    }

    @Override
    public void atender() {
        System.out.println("Chamada recebida.");
    }

    @Override
    public void iniciarCorreioVoz() {
        System.out.println("Redirecionando para o correio de voz.");
    }

    // NavegadorNaInternet
    @Override
    public void exibirPagina() {
        System.out.println("Página exibida.");
    }

    @Override
    public void adicionarNovaAba() {
        System.out.println("Nova aba adicionada.");
    }

    @Override
    public void atualizarPagina() {
        System.out.println("Página atualizada.");
    }
}