package sobrecarga;
/**
 * Executa operações aritméticas de soma.
 */
public class Calculadora {
    /**
     * Soma dois números inteiros.
     * @param a primeiro operando
     * @param b segundo operando
     * @return resultado da soma
     */
    public int somar(final int a, final int b) {
        return a + b;
    }

    /**
     * Soma três números inteiros.
     * @param a primeiro operando
     * @param b segundo operando
     * @param c terceiro operando
     * @return resultado da soma
     */
    public int somar(final int a, final int b, final int c) {
        return a + b + c;
    }

    /**
     * Soma dois números de ponto flutuante.
     * @param a primeiro operando
     * @param b segundo operando
     * @return resultado da soma
     */
    public double somar(final double a, final double b) {
        return a + b;
    }
}
