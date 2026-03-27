package cleancode;

import java.util.List;

public class Relatorio {

    private static final double META_MUITO_BOM = 1000.0;
    private static final double META_BOA = 500.0;

    public void gerar(List<Pedido> pedidos) {
        System.out.println("======= RELATORIO =======");

        int totalPedidos = 0;
        double valorTotal = 0;
        int pedidosCancelados = 0;
        int clientesComuns = 0;
        int clientesPremiums = 0;
        int clientesVips = 0;

        for (Pedido pedido : pedidos) {
            totalPedidos++;

            valorTotal = valorTotal + pedido.getTotalPedido();

            if (pedido.isCancelado()) {
                pedidosCancelados++;


                if (pedido.getCliente().isComum()) {
                    clientesComuns++;
                } else if (pedido.getCliente().isPremium()) {
                    clientesPremiums++;
                } else if (pedido.getCliente().isVip()) {
                    clientesVips++;
                }

                imprimirDetalhesDoPedido(pedido);

            }

            imprimirResumo(totalPedidos, valorTotal, pedidosCancelados, clientesComuns, clientesPremiums, clientesVips);
            avaliarResultado(valorTotal);


        }


    }

    private void avaliarResultado(double valorTotal){
        if (valorTotal > META_MUITO_BOM) {
            System.out.println("resultado muito bom");
        } else if (valorTotal > META_BOA) {
            System.out.println("resultado ok");
        } else {
            System.out.println("resultado fraco");
        }
    }

    private void imprimirDetalhesDoPedido(Pedido pedido){
        System.out.println("Pedido " + pedido.getId() + " - " + pedido.getCliente().nome() + " - " + pedido.getTotalPedido() + " - " + pedido.getStatusPedido());

        for (Item item : pedido.getItens()) {

            System.out.println("   item: " + item.nome() + " totalPedidos:" + item.qtd() + " preco:" + item.preco());
        }
    }

    private void imprimirResumo(int totalPedidos, double valorTotal, int pedidosCancelados, int clientesComuns, int clientesPremiums, int clientesVips) {
        System.out.println("--------------------");
        System.out.println("totalPedidos pedidos: " + totalPedidos);
        System.out.println("valor total: " + valorTotal);
        System.out.println("pedidosCancelados: " + pedidosCancelados);
        System.out.println("clientes clientesComuns: " + clientesComuns);
        System.out.println("clientes premium: " + clientesPremiums);
        System.out.println("clientes vip: " + clientesVips);


        if (totalPedidos > 0) {
            System.out.println("media: " + (valorTotal / totalPedidos));
        } else {
            System.out.println("media: 0");
        }
    }
}