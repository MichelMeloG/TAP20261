package cleancode;

public record Cliente(int Id, String nome, String email, TipoCliente tipoCliente) {

    //Movi a parte de gerar email para uma classe cliente
    public static String gerarEmail(String nome) {
        return nome.replace(" ", "").toLowerCase() + "@email.com";
    }
}
