package cleancode;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Sistema {

    private final Scanner scanner = new Scanner(System.in);
    private final List<Pedido> pedidos = new ArrayList<>();
    private final Db db = new Db();

    public void exibirMenu() {
        int operacao = -1;

        while (operacao != 0) {
            imprimirOpcoesMenu();
            operacao = lerOpcaoSegura();

            switch (operacao){
                case 1:
                    novoPedido();
                    break;
                case 2:
                    buscar();
                    break;
                case 3:
                    listar();
                    break;
                case 4:
                    gerarRelatorio();
                    break;
                case 5:
                    cancelar();
                    break;
                case 0:
                    System.out.println("Encerrando Sistema...");
                    break;
                default:
                    System.out.println("Operação Invalida!");
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

        double totalFinal = calcularValorFinalComDesconteFrete(pedido);
        pedido.setTotalPedido(totalFinal);

        pedidos.add(pedido);
        db.save(pedido);

        System.out.println("Pedido criado com sucesso");
        System.out.println("Id: " + pedido.getId());
        System.out.println("Cliente: " + pedido.getCliente().getNome());
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
                System.out.println("cliente: " + pedido.getCliente().getNome());
                System.out.println("email: " + pedido.getCliente().getEmail());
                System.out.println("tipo: " + pedido.getCliente().getTipoCliente());
                System.out.println("status: " + pedido.getStatusPedido());
                System.out.println("total: " + pedido.getTotalPedido());
                System.out.println("itens:");
                for (Item item : pedido.getItens()) {
                    System.out.println(item.getNome() + " - " + item.getQtd() + " - " + item.getPreco());
                }
            }
        }
    }

    public void buscar() {
        System.out.println("Digite o id:");
        int id = lerOpcaoSegura();

        for (Pedido pedido : pedidos) {
            if (pedido.getId() == id) {
                System.out.println("Pedido encontrado");
                System.out.println("id: " + pedido.getId());
                System.out.println("cliente: " + pedido.getCliente().getNome());
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
                    System.out.println("item " + contador + ": " + item.getNome() + " / " + item.getQtd() + " / " + item.getPreco());
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

            System.out.println("Preço do item:");
            double preco = 0;
            try
            {
                preco = Double.parseDouble(scanner.nextLine());
            }
            catch (Exception ignored)
            {

            }

            System.out.println("Quantidade:");
            int quantidade = 1;

            try
            {
                quantidade = Integer.parseInt(scanner.nextLine());
            }
            catch (Exception ignored)
            {

            }

            pedido.adicionarItem(new Item(nomeItem, quantidade, (int) preco));

            System.out.println("Adicionar mais itens? (s/n)");
            continua = scanner.nextLine();
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
            System.out.println("erro");
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

    double calcularValorFinalComDesconteFrete(Pedido pedido){

        double total = pedido.getTotalPedido();
        Cliente cliente = pedido.getCliente();

        if (cliente.isComum() && total > 300){
            total -= total * 0.05;
        } else if (cliente.isPremium() && total > 200){
            total -= total * 0.10;
        } else if (cliente.isPremium() && total < 200){
            total -= total * 0.03;
        } else if (cliente.isVip()){
            total -= total * 0.15;
        }

        if (total < 100) {
            total += 25;
        } else if (total < 300) {
            total += 15;
        }

        return total;

    }
}

