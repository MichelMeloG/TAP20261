package cleancode;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private int id;
    private Cliente cliente;
    private List<Item> itens;
    private double totalPedido;
    private StatusPedido statusPedido;


    public Pedido(int id, Cliente cliente, StatusPedido status) {
        this.id = id;
        this.cliente = cliente;
        this.statusPedido = status;
        this.itens = new ArrayList<>();
    }

    public void adicionarItem(Item item) {
        this.itens.add(item);
    }

    public double getSubtotalItens() {
        double subtotal = 0;
        for (Item item : itens) {
            subtotal += item.getValorTotal();
        }
        return subtotal;
    }

    public void cancelar(){
        this.statusPedido = StatusPedido.CANCELADO;
    }

    public boolean isCancelado(){
        return this.statusPedido == StatusPedido.CANCELADO;
    }

    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<Item> getItens() {
        return itens;
    }

    public double getTotalPedido() {
        return totalPedido;
    }

    public StatusPedido getStatusPedido() {
        return statusPedido;
    }

    public void setTotalPedido(double totalPedido) {
        this.totalPedido = totalPedido;
    }
}
