package cleancode;

//como os parametros do construtor sao todos os atributos da classe, o intellij mandou usar record

public record Cliente(int Id, String nome, String email, TipoCliente tipoCliente) {

    //criei os 3 tipos de cliente para tirar os números magicos 1, 2, 3 para simbolizar os tipos de cliente

    public boolean isComum() {
        return this.tipoCliente == TipoCliente.COMUM;
    }

    public boolean isPremium() {
        return this.tipoCliente == TipoCliente.PREMIUM;
    }

    public boolean isVip() {
        return this.tipoCliente == TipoCliente.VIP;
    }
}
