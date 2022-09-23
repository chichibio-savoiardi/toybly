import java.util.HashMap;
import java.util.List;

public class ToyAssembler {
	private HashMap<String, Integer> state;
	private int[] memory;

	public ToyAssembler(int memoryCells) {
		state = new HashMap<>();
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

	public String execute(List<String[]> syntax) {
		StringBuilder out = new StringBuilder();
		for (String[] args : syntax) {
			switch (args[0].toLowerCase()) {
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

				default:
					break;
			}
		}

		return out.toString();
	}

	@Override
	public String toString() {
		StringBuilder out = new StringBuilder();
		out.append("State: {");
		for (String reg : this.state.keySet()) {
			out.append(reg + ": " + this.state.get(reg) + ", ");
		}
		out.append("}\nMemory: {");
		for (int mem : this.memory) {
			out.append(mem + ", ");
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

	public String setMem(int num, int memCell) {
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
