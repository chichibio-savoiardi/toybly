public class App {
	public static void main(String[] args) throws Exception {
		ToyAssembler toy = new ToyAssembler(16);
		toy.parseFile("./program.toybly");
		System.out.println(toy.execute());
		System.out.println(toy.toString());
	}
}
