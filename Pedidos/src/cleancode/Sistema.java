package cleancode;

import cleancode.desconto.DescontoClienteComum;
import cleancode.desconto.DescontoClientePremium;
import cleancode.desconto.DescontoClienteVip;
import cleancode.desconto.IRegraDesconto;

import java.util.List;
import java.util.Scanner;

public class Sistema {

    private final Scanner scanner = new Scanner(System.in);

    private final Db db = new Db();
    private final List<Pedido> pedidos = db.getTodosPedidos();
    //a lista de pedidos estava sendo salva em outra lista e o Db estava inutilizado

    public void exibirMenu() {
        int operacao = -1;

        while (operacao != 0) {
            imprimirOpcoesMenu();
            operacao = lerOpcaoSegura();

            switch (operacao){
                case 1 -> novoPedido();
                case 2 -> listar();
                case 3 -> buscarPorId();
                case 4 -> gerarRelatorio();
                case 5 -> cancelar();
                case 0 -> System.out.println("Encerrando Sistema...");
                default -> System.out.println("Operação Invalida!");
            }
        }
    }

    public void novoPedido() {
        System.out.println("Nome cliente:");
        String nomeCliente = scanner.nextLine();

        System.out.println("Tipo cliente (1 comum, 2 premium, 3 vip):");
        TipoCliente tipoCliente = converterParaTipoCliente(lerOpcaoSegura());

        int novoId = pedidos.size() + 1;
        String email = nomeCliente.replace(" ", "").toLowerCase() + "@email.com";
        Cliente cliente = new Cliente(novoId, nomeCliente, email, tipoCliente);

        Pedido pedido = new Pedido(novoId, cliente, StatusPedido.NOVO);

        adicionarItensAoPedido(pedido);

        double totalFinal = calcularTotalFinal(pedido);
        pedido.setTotalPedido(totalFinal);

        db.save(pedido);

        System.out.println("Pedido criado com sucesso");
        System.out.println("Id: " + pedido.getId());
        System.out.println("Cliente: " + pedido.getCliente().nome());
        System.out.println("Total: " + pedido.getTotalPedido());

        if (pedido.getTotalPedido() > 500) {
            System.out.println("Pedido importante!!!");
        }
    }

    public void listar() {

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
                    System.out.println(item.nome() + " - " + item.qtd() + "un" + " - " + "R$" + item.preco() );
                }
            }
        }
    }

    public void buscarPorId() {
        System.out.println("Digite o id:");
        int id = lerOpcaoSegura();

        for (Pedido pedido : pedidos) {
            if (pedido.getId() == id) {
                System.out.println("Pedido encontrado");
                System.out.println("id: " + pedido.getId());
                System.out.println("cliente: " + pedido.getCliente().nome());
                System.out.println("status: " + pedido.getStatusPedido());
                System.out.println("total: " + pedido.getTotalPedido());


                System.out.println("subtotal: " + pedido.getSubtotalItens());

                if (pedido.getCliente().isComum()) {
                    System.out.println("cliente comum");
                } else if (pedido.getCliente().isPremium()) {
                    System.out.println("cliente premium");
                } else if (pedido.getCliente().isVip()) {
                    System.out.println("cliente vip");
                } else {
                    System.out.println("cliente desconhecido");
                }

                int contador = 1;
                for (Item item : pedido.getItens() ) {
                    System.out.println("item " + contador + ": " + item.nome() + " - " + item.qtd() + "un " + " - R$" + item.preco());
                    contador++;
                }
            }

            return;
        }

        System.out.println("Pedido não encontrado");

    }

    public void gerarRelatorio() {
        Relatorio relatorio = new Relatorio();
        relatorio.gerar(pedidos);
    }

    public void cancelar() {
        System.out.println("Digite id do pedido");
        int id = lerOpcaoSegura();

        for (Pedido pedido : pedidos) {
            if (pedido.getId() == id) {
                if (pedido.isCancelado()) {
                    System.out.println("ja cancelado");
                } else {
                    pedido.cancelar();
                    System.out.println("cancelado");
                }
                return;
            }
        }

        System.out.println("pedido nao existe");
    }


    private void adicionarItensAoPedido(Pedido pedido) {
        String continua = "s";
        while (continua.equalsIgnoreCase("s")) {
            System.out.println("Nome do item:");
            String nomeItem = scanner.nextLine();

            double preco;
            while (true) {
                System.out.println("Preço do item:");
                try {
                    preco = Double.parseDouble(scanner.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Valor inválido! Por favor, digite apenas números");
                }
            }

            int quantidade;
            while (true) {
                System.out.println("Quantidade:");
                try {
                    quantidade = Integer.parseInt(scanner.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Valor inválido! Por favor, digite um número inteiro.");
                }
            }

            pedido.adicionarItem(new Item(nomeItem, preco, quantidade));

            while (true) {
                System.out.println("Adicionar mais itens? (s/n)");
                continua = scanner.nextLine();

                if (continua.equalsIgnoreCase("s") || continua.equalsIgnoreCase("n")){
                    break;
                }else {
                    System.out.println("Erro: Opção inválida! Digite apenas 's' para Sim ou 'n' para Não.");
                }
            }
        }
    }

    public void imprimirOpcoesMenu(){
        System.out.println("==== SISTEMA ====");
        System.out.println("1 - Novo pedido");
        System.out.println("2 - Listar pedidos");
        System.out.println("3 - Buscar pedido por id");
        System.out.println("4 - Relatorio");
        System.out.println("5 - Cancelar pedido");
        System.out.println("0 - Sair");
        System.out.print("Opcao: ");
    }

    public int lerOpcaoSegura(){
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Insira um número valido");
            return -1;
        }
    }

    private TipoCliente converterParaTipoCliente(int input) {
        return switch (input) {
            case 1 :
                yield TipoCliente.COMUM;
            case 2 :
                yield TipoCliente.PREMIUM;
            case 3 :
                yield TipoCliente.VIP;
            default :
                System.out.println("Tipo de cliente inválido, cliente comum selecionado");
                yield TipoCliente.COMUM;

        };
    }

    private double calcularTotalFinal(Pedido pedido) {
        double subtotal = pedido.getSubtotalItens();

        IRegraDesconto regraDesconto = pedido.getCliente().tipoCliente().getRegra();
        double valorComDesconto = regraDesconto.calcular(subtotal);

        double valorFrete = calcularValorFrete(valorComDesconto);

        return valorComDesconto + valorFrete;
    }

    

    double calcularValorFrete(double valor) {
        if (valor < 100) return valor + 25;
        if (valor < 300) return valor + 15;
        return valor;
    }
}

