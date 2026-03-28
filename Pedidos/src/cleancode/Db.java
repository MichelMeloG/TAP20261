package cleancode;

import java.util.ArrayList;
import java.util.List;

public class Db {

    private static final List<Pedido> banco = new ArrayList<>();

    public void save(Pedido pedido) {
        try {
            banco.add(pedido);
            System.out.println("salvou no banco");
        } catch (Exception e) {
            System.out.println("erro ao salvar");
        }
    }

    public List<Pedido> getTodosPedidos() {
        return banco;
    }
}
