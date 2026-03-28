package cleancode;

public record Item(String nome, double preco, int qtd) {

    public double getValorTotal() {
        return preco * qtd;
    }
}