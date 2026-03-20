package cleancode;

import java.util.List;

public class Pedido {
    public int id;
    public Cliente cliente;
    public List<Item> itens;
    public double total;
    public String status;

    public double calcularPrecoPedido() {
        double precoPedido = 0;
        for (Item item : itens) {
            precoPedido = precoPedido + (item.preco * item.qtd);
        }
        return precoPedido;
    }

    public void mostrarPedido() {
        System.out.println("Pedido " + id);
        System.out.println("Cliente " + cliente.nome);
        for (Item item : itens) {
            System.out.println(item.nome);
        }
        System.out.println(total);
    }
}
