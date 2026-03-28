package cleancode;

import cleancode.desconto.IRegraDesconto;

public class CalculaValorPedido {

    public double calcularTotalFinal(Pedido pedido) {
        double subtotal = pedido.getSubtotalItens();

        IRegraDesconto regraDesconto = pedido.getRegraDesconto();
        double valorComDesconto = regraDesconto.calcular(subtotal);

        double valorFrete = calcularValorFrete(valorComDesconto);

        return valorComDesconto + valorFrete;
    }

    public double calcularFreteParaExibir(Pedido pedido) {
        double valorComDesconto = pedido.getRegraDesconto().calcular(pedido.getSubtotalItens());
        return calcularValorFrete(valorComDesconto);
    }

    private double calcularValorFrete(double valor) {
        if (valor < 100) return 25;
        if (valor < 300) return 15;
        return 0;
    }
}
