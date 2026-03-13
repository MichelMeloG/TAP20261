package sobrecarga;

public class TesteSobrecarga {
    public static void main(String[] args) {
        Calculadora calculadora = new Calculadora();
        System.out.println(calculadora.somar(2,3));
        System.out.println(calculadora.somar(2,3, 4));
        System.out.println(calculadora.somar(2.5 ,3.5));
    }
}
