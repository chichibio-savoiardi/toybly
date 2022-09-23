import java.nio.file.FileSystems;
import java.util.List;

public class App {
	public static void main(String[] args) throws Exception {
		List<String[]> syntax = ToyParser.getSyntax(FileSystems.getDefault().getPath("./program.toybly"));
	}

	private void test() {
		ToyAssembler toy = new ToyAssembler(16);
		toy.setMem(8, 0);
		toy.setMem(2, 1);
		toy.load("F0", 0);
		toy.load("F1", 1);
		toy.add("F0", "F1");
		toy.store("V0", 2);
		System.out.println(toy.toString());
	}
}
