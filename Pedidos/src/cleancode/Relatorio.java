package cleancode;

import java.util.List;

public class Relatorio {

    //Criei essas constantes para tirar os números mágicos do relatório
    private static final double META_MUITO_BOM = 1000.0;
    private static final double META_BOA = 500.0;

    public void gerar(List<Pedido> pedidos) {
        //Movi toda a regra de calcular para a classe EstatisticasPedidos
        EstatisticasPedidos estatisticas = new EstatisticasPedidos(pedidos);


        System.out.println("======= RELATORIO =======");
        imprimirResumo(estatisticas);
        avaliarResultado(estatisticas.getValorTotal());
    }

    private void imprimirResumo(EstatisticasPedidos estatisticasP) {
        System.out.println("--------------------");
        System.out.println("Total de pedidos: " + estatisticasP.getTotalPedidos());
        System.out.println("Valor total: " + estatisticasP.getValorTotal());
        System.out.println("Pedidos cancelados: " + estatisticasP.getPedidosCancelados());
        System.out.println("Clientes comuns: " + estatisticasP.getClientesComuns());
        System.out.println("Clientes premium: " + estatisticasP.getClientesPremiums());
        System.out.println("Clientes vip: " + estatisticasP.getClientesVips());

        if (estatisticasP.getTotalPedidos() > 0) {
            System.out.println("media: " + (estatisticasP.getValorTotal() / estatisticasP.getTotalPedidos()));
        } else {
            System.out.println("media: 0");
        }
    }

    private void avaliarResultado(double valorTotal) {
        if (valorTotal > META_MUITO_BOM) {
            System.out.println("resultado muito bom");
        } else if (valorTotal > META_BOA) {
            System.out.println("resultado ok");
        } else {
            System.out.println("resultado fraco");
        }
    }
}
