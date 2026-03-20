package cleancode;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Sistema {

    Scanner scanner = new Scanner(System.in);
    List<Pedido> pedidos = new ArrayList<>();
    Db db = new Db();

    public void exibirMenu() {
        int operacao = -1;

        while (operacao != 0) {
            System.out.println("==== SISTEMA ====");
            System.out.println("1 - Novo pedido");
            System.out.println("2 - Listar pedidos");
            System.out.println("3 - Buscar pedido por id");
            System.out.println("4 - Relatorio");
            System.out.println("5 - Cancelar pedido");
            System.out.println("0 - Sair");
            System.out.print("Opcao: ");

            try {
                operacao = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("erro");
                operacao = -1;
            }

            if (operacao == 1) {
                novoPedido();
            } else if (operacao == 2) {
                listar();
            } else if (operacao == 3) {
                buscar();
            } else if (operacao == 4) {
                rel();
            } else if (operacao == 5) {
                cancelar();
            } else if (operacao == 0) {
                System.out.println("fim");
            } else {
                System.out.println("opcao invalida");
            }
        }
    }

    public void novoPedido() {
        System.out.println("Nome cliente:");
        String nomeCliente = scanner.nextLine();

        System.out.println("Tipo cliente (1 comum, 2 premium, 3 vip):");
        int tipoCliente = 0;
        try {
            tipoCliente = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Tipo de cliente inexistente, usuario comum selecionado");
            tipoCliente = 1;
        }

        Cliente c = new Cliente();
        c.id = pedidos.size() + 1;
        c.nome = nomeCliente;
        c.tipoCliente = tipoCliente;
        c.email = nomeCliente.replace(" ", "").toLowerCase() + "@email.com";

        Pedido p = new Pedido();
        p.id = pedidos.size() + 1;
        p.cliente = c;
        p.status = "NOVO";
        p.itens = new ArrayList<>();

        String continua = "s";
        while (continua.equalsIgnoreCase("s")) {
            System.out.println("Nome item:");
            String ni = scanner.nextLine();

            System.out.println("Preco item:");
            double pr = 0;
            try {
                pr = Double.parseDouble(scanner.nextLine());
            } catch (Exception e) {
                pr = 0;
            }

            System.out.println("Qtd:");
            int q = 0;
            try {
                q = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                q = 1;
            }

            Item i = new Item();
            i.nome = ni;
            i.preco = pr;
            i.qtd = q;
            p.itens.add(i);

            System.out.println("Adicionar mais item? s/n");
            continua = scanner.nextLine();
        }

        double total = 0;
        for (int i = 0; i < p.itens.size(); i++) {
            total = total + (p.itens.get(i).preco * p.itens.get(i).qtd);
        }

        // regra de desconto
        if (c.tipoCliente == 1) {
            if (total > 300) {
                total = total - (total * 0.05);
            }
        } else if (c.tipoCliente == 2) {
            if (total > 200) {
                total = total - (total * 0.10);
            } else {
                total = total - (total * 0.03);
            }
        } else if (c.tipoCliente == 3) {
            total = total - (total * 0.15);
        } else {
            total = total;
        }

        // frete
        if (total < 100) {
            total = total + 25;
        } else if (total >= 100 && total < 300) {
            total = total + 15;
        } else {
            total = total + 0;
        }

        p.total = total;

        pedidos.add(p);
        db.save(p);

        System.out.println("Pedido criado com sucesso");
        System.out.println("Id: " + p.id);
        System.out.println("Cliente: " + p.cliente.nome);
        System.out.println("Total: " + p.total);

        if (p.total > 500) {
            System.out.println("Pedido importante!!!");
        }
    }

    public void listar() {
        if (pedidos.size() == 0) {
            System.out.println("sem pedidos");
        } else {
            for (int i = 0; i < pedidos.size(); i++) {
                Pedido p = pedidos.get(i);
                System.out.println("---------------");
                System.out.println("id: " + p.id);
                System.out.println("cliente: " + p.cliente.nome);
                System.out.println("email: " + p.cliente.email);
                System.out.println("tipo: " + p.cliente.tipoCliente);
                System.out.println("status: " + p.status);
                System.out.println("total: " + p.total);
                System.out.println("itens:");
                for (int j = 0; j < p.itens.size(); j++) {
                    Item it = p.itens.get(j);
                    System.out.println(it.nome + " - " + it.qtd + " - " + it.preco);
                }
            }
        }
    }

    public void buscar() {
        System.out.println("Digite o id:");
        int id = Integer.parseInt(scanner.nextLine());
        boolean achou = false;

        for (int i = 0; i < pedidos.size(); i++) {
            Pedido p = pedidos.get(i);
            if (p.id == id) {
                achou = true;
                System.out.println("Pedido encontrado");
                System.out.println("id: " + p.id);
                System.out.println("cliente: " + p.cliente.nome);
                System.out.println("status: " + p.status);
                System.out.println("total: " + p.total);

                double subtotal = 0;
                for (int j = 0; j < p.itens.size(); j++) {
                    subtotal = subtotal + (p.itens.get(j).preco * p.itens.get(j).qtd);
                }
                System.out.println("subtotal calculado novamente: " + subtotal);

                if (p.cliente.tipoCliente == 1) {
                    System.out.println("cliente comum");
                } else if (p.cliente.tipoCliente == 2) {
                    System.out.println("cliente premium");
                } else if (p.cliente.tipoCliente == 3) {
                    System.out.println("cliente vip");
                } else {
                    System.out.println("cliente desconhecido");
                }

                for (int j = 0; j < p.itens.size(); j++) {
                    Item it = p.itens.get(j);
                    System.out.println("item " + (j + 1) + ": " + it.nome + " / " + it.qtd + " / " + it.preco);
                }
            }
        }

        if (achou == false) {
            System.out.println("nao achou");
        }
    }

    public void rel() {
        Relatorio r = new Relatorio();
        r.gerar(pedidos);
    }

    public void cancelar() {
        System.out.println("Digite id do pedido");
        int id = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < pedidos.size(); i++) {
            if (pedidos.get(i).id == id) {
                if (pedidos.get(i).status.equals("CANCELADO")) {
                    System.out.println("ja cancelado");
                } else {
                    pedidos.get(i).status = "CANCELADO";
                    System.out.println("cancelado");
                }
                return;
            }
        }

        System.out.println("pedido nao existe");
    }
}
