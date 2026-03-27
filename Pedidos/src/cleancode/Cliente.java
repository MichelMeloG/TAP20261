package cleancode;

public class Cliente {
    private final int id;
    private final String nome;
    private final String email;
    private final TipoCliente tipoCliente;

    public Cliente(int id, String nome, String email, TipoCliente tipoCliente) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.tipoCliente = tipoCliente;
    }

    public boolean isComum(){
        return this.tipoCliente == TipoCliente.COMUM;
    }

    public boolean isPremium(){
        return this.tipoCliente == TipoCliente.PREMIUM;
    }

    public boolean isVip(){
        return this.tipoCliente == TipoCliente.VIP;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public TipoCliente getTipoCliente() {
        return tipoCliente;
    }
}
