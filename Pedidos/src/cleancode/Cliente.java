package cleancode;

public class Cliente {
    public int id;
    public String nome;
    public String email;
    public int tipoCliente; // 1 comum, 2 premium, 3 vip

    public String getTipoDesc() {
        if (tipoCliente == 1) {
            return "comum";
        } else if (tipoCliente == 2) {
            return "premium";
        } else if (tipoCliente == 3) {
            return "vip";
        } else {
            return "outro";
        }
    }
}
