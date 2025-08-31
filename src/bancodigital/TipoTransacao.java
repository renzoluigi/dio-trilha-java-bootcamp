
package bancodigital;

public enum TipoTransacao {
    DEPOSITO("Depósito"),
    SAQUE("Saque"),
    TRANSFERENCIA_ENVIADA("Transferência Enviada"),
    TRANSFERENCIA_RECEBIDA("Transferência Recebida"),
    PIX_ENVIADO("PIX Enviado"),
    PIX_RECEBIDO("PIX Recebido"),
    PAGAMENTO("Pagamento"),
    RENDIMENTO("Rendimento");

    private final String descricao;

    TipoTransacao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}