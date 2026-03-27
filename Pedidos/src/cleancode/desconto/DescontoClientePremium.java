package cleancode.desconto;

public class DescontoClientePremium implements IRegraDesconto{
    @Override
    public double calcular(double subtotal) {
        if (subtotal > 200){
            return subtotal - (subtotal * 0.10);
        }
        return subtotal - (subtotal * 0.03);
    }
}
