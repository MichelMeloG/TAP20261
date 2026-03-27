package cleancode;

//como os parametros do construtor sao todos os atributos da classe, o intellij mandou usar record
public record Item(String nome, double preco, int qtd) {

    public double getValorTotal() {
        return preco * qtd;
    }


}