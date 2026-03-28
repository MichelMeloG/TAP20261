package cleancode;

import java.util.List;

public class PedidoView {

    public void exibirConfirmacaoPedido(Pedido pedido) {
        System.out.println("Pedido criado com sucesso");
        System.out.println("Id: " + pedido.getId());
        System.out.println("Cliente: " + pedido.getCliente().nome());
        System.out.println("Total: " + pedido.getTotalPedido());

        if (pedido.getTotalPedido() > 500) {
            System.out.println("Pedido importante!!!");
        }
    }

    public void exibirLista(List<Pedido> pedidos) {
        if (pedidos.isEmpty()) {
            System.out.println("sem pedidos");
        } else {
            for (Pedido pedido : pedidos) {
                System.out.println("---------------");
                System.out.println("id: " + pedido.getId());
                System.out.println("cliente: " + pedido.getCliente().nome());
                System.out.println("email: " + pedido.getCliente().email());
                System.out.println("tipo: " + pedido.getCliente().tipoCliente());
                System.out.println("status: " + pedido.getStatusPedido());
                System.out.println("total: " + pedido.getTotalPedido());
                System.out.println("itens:");
                for (Item item : pedido.getItens()) {
                    System.out.println(item.nome() + " - " + item.qtd() + "un" + " - " + "R$" + item.preco());
                }
            }
        }
    }

    public void exibirDetalhe(Pedido pedido) {
        System.out.println("Pedido encontrado");
        System.out.println("id: " + pedido.getId());
        System.out.println("cliente: " + pedido.getCliente().nome());
        System.out.println("status: " + pedido.getStatusPedido());
        System.out.println("total: " + pedido.getTotalPedido());
        System.out.println("subtotal: " + pedido.getSubtotalItens());
        System.out.println(pedido.getCliente().tipoCliente().getDescricao());

        int contador = 1;
        for (Item item : pedido.getItens()) {
            System.out.println("item " + contador + ": " + item.nome() + " - " + item.qtd() + "un " + " - R$" + item.preco());
            contador++;
        }
    }
}
