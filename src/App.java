public class App {
    public static void main(String[] args) throws Exception {
        ToyAssembler toy = new ToyAssembler(16);
        toy.set(9, 0);
        toy.load("R0", 0);
        toy.store("R0", 1);
        System.out.println(toy.toString());
    }
}
