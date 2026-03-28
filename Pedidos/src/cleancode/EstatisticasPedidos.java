package cleancode;

import java.util.List;

//Criei essa classe para tirar a parte de calcular da classe de relatórios

public class EstatisticasPedidos {

    private final int totalPedidos;
    private final double valorTotal;
    private final int pedidosCancelados;
    private final int clientesComuns;
    private final int clientesPremiums;
    private final int clientesVips;

    public EstatisticasPedidos(List<Pedido> pedidos) {
        int totalPedidos = 0;
        double valorTotal = 0;
        int pedidosCancelados = 0;
        int clientesComuns = 0;
        int clientesPremiums = 0;
        int clientesVips = 0;

        for (Pedido pedido : pedidos) {
            totalPedidos++;
            valorTotal += pedido.getTotalPedido();

            TipoCliente tipo = pedido.getTipoCliente();
            if (tipo == TipoCliente.COMUM) clientesComuns++;
            else if (tipo == TipoCliente.PREMIUM) clientesPremiums++;
            else if (tipo == TipoCliente.VIP) clientesVips++;

            if (pedido.isCancelado()) {
                pedidosCancelados++;
            }
        }

        this.totalPedidos = totalPedidos;
        this.valorTotal = valorTotal;
        this.pedidosCancelados = pedidosCancelados;
        this.clientesComuns = clientesComuns;
        this.clientesPremiums = clientesPremiums;
        this.clientesVips = clientesVips;
    }

    public int getTotalPedidos() { return this.totalPedidos; }
    public double getValorTotal() { return valorTotal; }
    public int getPedidosCancelados() { return pedidosCancelados; }
    public int getClientesComuns() { return clientesComuns; }
    public int getClientesPremiums() { return clientesPremiums; }
    public int getClientesVips() { return clientesVips; }
}
