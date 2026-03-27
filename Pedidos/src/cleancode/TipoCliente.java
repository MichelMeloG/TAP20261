package cleancode;

import cleancode.desconto.IRegraDesconto;
import cleancode.desconto.DescontoClienteVip;
import cleancode.desconto.DescontoClientePremium;
import cleancode.desconto.DescontoClienteComum;

public enum TipoCliente {

    VIP(new DescontoClienteVip()),
    PREMIUM(new DescontoClientePremium()),
    COMUM(new DescontoClienteComum());

    private final IRegraDesconto regra;

    TipoCliente(IRegraDesconto regra) {
        this.regra = regra;
    }

    public IRegraDesconto getRegra() {
        return regra;
    }
}