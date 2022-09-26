import java.util.HashMap;
import java.util.List;
import java.nio.file.*;
import java.util.ArrayList;
import java.nio.charset.*;
import java.io.IOException;

public class ToyAssembler {
	private List<String[]> syntax;
	private HashMap<String, Integer> state;
	private HashMap<String, Integer> labels;
	private int[] memory;

	public ToyAssembler(int memoryCells) {
		state = new HashMap<>();
		labels = new HashMap<>();
		// 32 memory cells
		memory = new int[memoryCells];
		/*
		 * One hard wired zero
		 * Five general porpuse registers
		 * Three function arguments registers
		 * One function return register
		 */
		String[] stateNames = new String[] {
				"Z0", "R0", "R1", "R2", "R3", "R4", "F0", "F1", "F2", "V0"
		};

		for (String name : stateNames) {
			state.put(name, 0);
		}

		for (int i = 0; i < memory.length; i++) {
			memory[i] = 0;
		}
	}

	public void parseFile(String filePath) {
		Path file = Paths.get(filePath);
        Charset charset = Charset.forName("UTF-8");

        List<String[]> syntax = new ArrayList<>();
            
        try {
            List<String> lines = Files.readAllLines(file, charset);
            for (String line : lines) {
                String[] tmp = line.split(" ", 5);
                syntax.add(tmp);
                //System.out.println(line);
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }

        this.syntax = syntax;
    }

	public String execute() {
		StringBuilder out = new StringBuilder();

		// First pass to find all the labels
		for (int i = 0; i < this.syntax.size(); i++) {
			String[] args = this.syntax.get(i);
			System.out.println(args.toString());
			if (args[0].toLowerCase() == "label") {
				this.labels.put(args[1].toLowerCase(), i);
			}
		}

		// Execution
		for (int i = 0; i < this.syntax.size(); i++) {
			String[] args = this.syntax.get(i);
			int lineNum = i + 1;

			switch (args[0].toLowerCase()) {
				case ";": // Comment
					out.append("Skipping comment at line " + lineNum + "\n");
					break;

				case "end":
					return "";

				case "set":
					out.append(setMem(Integer.parseInt(args[1]), Integer.parseInt(args[2])));
					break;

				case "load":
					out.append(load(args[1], Integer.parseInt(args[2])));
					break;

				case "store":
					out.append(store(args[1], Integer.parseInt(args[2])));
					break;

				case "add":
					out.append(add(args[1], args[2]));
					break;

				case "mul":
					out.append(mul(args[1], args[2]));
					break;

				case "sub":
					out.append(sub(args[1], args[2]));
					break;

				case "div":
					out.append(div(args[1], args[2]));
					break;

				case "label":
					break;

				case "jump":
					i = this.labels.getOrDefault(args[1].toLowerCase(), i);
					break;

				case "jzero":
					if (getReg(args[2]) != 0) {
						i = this.labels.getOrDefault(args[1].toLowerCase(), i);
					}
					break;

				default:
					out.append(String.format("Illegal operator '%s' at line %d\n", args[0], lineNum));
					break;
			}
		}

		return out.toString();
	}

	@Override
	public String toString() {
		StringBuilder out = new StringBuilder();
		out.append("Registers: {");
		for (String reg : this.state.keySet()) {
			out.append(reg + ": " + this.state.get(reg) + ", ");
		}
		out.append("}\nMemory: {");
		for (int mem : this.memory) {
			out.append(mem + ", ");
		}
		out.append("}\nLabels: {");
		for (String label : this.labels.keySet()) {
			out.append(String.format("%s: %d, ", label, labels.get(label)));
		}
		out.append("}");
		return out.toString();
	}

	private int getReg(String reg) {
		if (reg.equals("Z0")) {
			return 0;
		}

		return this.state.getOrDefault(reg, 0);
	}

	public String setReg(String reg, int val) {
		if (!this.state.containsKey(reg)) {
			return String.format("Register %s does not exist", reg);
		}

		if (reg == "Z0") {
			return "Cannot assign to Z0";
		}

		this.state.put(reg, val);

		return "";
	}

	public String setMem(int memCell, int num) {
		if (!((memCell < this.memory.length) && (memCell >= 0))) {
			return String.format("Memory cell num. %d does not exist", memCell);
		}

		this.memory[memCell] = num;

		return "";
	}

	public int getMem(int memCell) {
		if (!((memCell < this.memory.length) && (memCell >= 0))) {
			return 0;
		}

		return this.memory[memCell];
	}

	public String load(String register, int memCell) {
		if (!this.state.containsKey(register)) {
			return String.format("Register %s does not exist", register);
		}

		if (!((memCell < this.memory.length) && (memCell >= 0))) {
			return String.format("Memory cell num. %d does not exist", memCell);
		}

		setReg(register, this.memory[memCell]);

		return "";
	}

	public String store(String register, int memCell) {
		if (!this.state.containsKey(register)) {
			return String.format("Register %s does not exist", register);
		}

		if (!((memCell < this.memory.length) && (memCell >= 0))) {
			return String.format("Memory cell num. %d does not exist", memCell);
		}

		setMem(getReg(register), memCell);

		return "";
	}

	public String add(String register1, String register2) {
		if (!this.state.containsKey(register1)) {
			return String.format("Register %s does not exist", register1);
		}

		if (!this.state.containsKey(register2)) {
			return String.format("Register %s does not exist", register2);
		}

		int res = this.state.get(register1) + this.state.get(register2);

		setReg("V0", res);

		return "";
	}

	public String mul(String register1, String register2) {
		if (!this.state.containsKey(register1)) {
			return String.format("Register %s does not exist", register1);
		}

		if (!this.state.containsKey(register2)) {
			return String.format("Register %s does not exist", register2);
		}

		int res = this.state.get(register1) * this.state.get(register2);

		setReg("V0", res);

		return "";
	}

	public String sub(String register1, String register2) {
		if (!this.state.containsKey(register1)) {
			return String.format("Register %s does not exist", register1);
		}

		if (!this.state.containsKey(register2)) {
			return String.format("Register %s does not exist", register2);
		}

		int res = this.state.get(register1) - this.state.get(register2);

		setReg("V0", res);

		return "";
	}

	public String div(String register1, String register2) {
		if (!this.state.containsKey(register1)) {
			return String.format("Register %s does not exist", register1);
		}

		if (!this.state.containsKey(register2)) {
			return String.format("Register %s does not exist", register2);
		}

		int res = this.state.get(register1) / this.state.get(register2);

		setReg("V0", res);

		return "";
	}
}
