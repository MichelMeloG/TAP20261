package sobrescrita;

public class Gato extends Animal {
    @Override
    void emitirSom() {
        System.out.println("Miau");
    }
}
