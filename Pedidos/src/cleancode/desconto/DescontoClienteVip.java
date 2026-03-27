package cleancode.desconto;

public class DescontoClienteVip implements IRegraDesconto{
    @Override
    public double calcular(double subtotal) {
        return subtotal - (subtotal * 0.15);
    }
}
