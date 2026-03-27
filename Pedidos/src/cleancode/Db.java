package cleancode;

import java.util.ArrayList;
import java.util.List;

public class Db {

    public static List<Pedido> banco = new ArrayList<>();

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

    public Pedido buscarPorId(int id) {
        for (Pedido pedido : banco) {
            if (pedido.getId() == id) {
                return pedido;
            }
        }
        return null;
    }
}
