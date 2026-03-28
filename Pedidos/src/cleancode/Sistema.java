package cleancode;

import java.util.List;
import java.util.Scanner;

//Movi grande parte das responsabilidades do sistema para outras classes,
//para respeitar melhor o princípio de responsabilidade única
public class Sistema {

    private final Scanner scanner = new Scanner(System.in);

    private final Db db = new Db();
    private final List<Pedido> pedidos = db.getTodosPedidos();
    private final PedidoView pedidoView = new PedidoView();
    //a lista de pedidos estava sendo salva em outra lista e o Db estava inutilizado

    public void exibirMenu() {
        int operacao = -1;

        while (operacao != 0) {
            imprimirOpcoesMenu();
            operacao = lerOpcaoSegura();

            switch (operacao) {
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
        TipoCliente tipoCliente = TipoCliente.fromOpcao(lerOpcaoSegura());

        int novoId = pedidos.size() + 1;
        Cliente cliente = new Cliente(novoId, nomeCliente, Cliente.gerarEmail(nomeCliente), tipoCliente);

        Pedido pedido = new Pedido(novoId, cliente, StatusPedido.NOVO);

        adicionarItensAoPedido(pedido);

        double totalFinal = new CalculaValorPedido().calcularTotalFinal(pedido);
        pedido.setTotalPedido(totalFinal);

        db.save(pedido);

        pedidoView.exibirConfirmacaoPedido(pedido);
    }

    public void listar() {
        pedidoView.exibirLista(pedidos);
    }

    public void buscarPorId() {
        System.out.println("Digite o id:");
        int id = lerOpcaoSegura();

        for (Pedido pedido : pedidos) {
            if (pedido.getId() == id) {
                pedidoView.exibirDetalhe(pedido);
                return;
            }
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

                if (continua.equalsIgnoreCase("s") || continua.equalsIgnoreCase("n")) {
                    break;
                } else {
                    System.out.println("Erro: Opção inválida! Digite apenas 's' para Sim ou 'n' para Não.");
                }
            }
        }
    }

    public void imprimirOpcoesMenu() {
        System.out.println("==== SISTEMA ====");
        System.out.println("1 - Novo pedido");
        System.out.println("2 - Listar pedidos");
        System.out.println("3 - Buscar pedido por id");
        System.out.println("4 - Relatorio");
        System.out.println("5 - Cancelar pedido");
        System.out.println("0 - Sair");
        System.out.print("Opcao: ");
    }


    //Lê corretamente o input inteiro
    public int lerOpcaoSegura() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException  e) {
            System.out.println("Insira um número valido");
            return -1;
        }
    }
}
