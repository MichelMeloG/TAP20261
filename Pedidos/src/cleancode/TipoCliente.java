package cleancode;

import cleancode.desconto.IRegraDesconto;
import cleancode.desconto.DescontoClienteVip;
import cleancode.desconto.DescontoClientePremium;
import cleancode.desconto.DescontoClienteComum;

public enum TipoCliente {

    VIP(new DescontoClienteVip(), "cliente vip"),
    PREMIUM(new DescontoClientePremium(), "cliente premium"),
    COMUM(new DescontoClienteComum(), "cliente comum");

    private final IRegraDesconto regra;
    private final String descricao;

    TipoCliente(IRegraDesconto regra, String descricao) {
        this.regra = regra;
        this.descricao = descricao;
    }

    public IRegraDesconto getRegra() {
        return regra;
    }

    public String getDescricao() {
        return descricao;
    }

    //Serve para converter o input de um inteiro para o tipo de cliente
    public static TipoCliente fromOpcao(int opcao) {
        return switch (opcao) {
            case 1 -> COMUM;
            case 2 -> PREMIUM;
            case 3 -> VIP;
            default -> {
                System.out.println("Tipo de cliente inválido, cliente comum selecionado");
                yield COMUM;
            }
        };
    }
}