public class App {
	public static String helptext = """
			toybly [filename] <memcells>

				Toy assembly assembler

				Arguments:
					filename: file to read as toybly script.
					memcells: memory cells to allocate for the script, default is 16.
			""";

	public static void main(String[] args) throws Exception {
		if (args.length < 1) {
			System.out.println(helptext);
			return;
		}

		switch (args[0]) {
			case "help":
			case "-h":
			case "--help":
				System.out.println(helptext);

			default:
				break;
		}
		
		int mem = 16;
		String fileName = args[0];

		if (args.length == 2) {
			mem = Integer.parseInt(args[1]);
		}

		ToyAssembler toy = new ToyAssembler(mem);
		toy.parseFile(args[0]);
		System.out.println(toy.execute());
		System.out.println(toy.toString());
	}
}
