package cleancode.desconto;

public class DescontoClienteComum implements IRegraDesconto {
    @Override
    public double calcular(double subtotal) {
        if (subtotal > 300) {
            return subtotal - (subtotal * 0.05);
        }
        return subtotal;
    }
}
