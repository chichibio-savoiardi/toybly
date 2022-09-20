import java.util.HashMap;

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

	public String set(int num, int memCell) {
		if (!((memCell < this.memory.length) && (memCell >= 0))) {
			return String.format("Memory cell num. %d does not exist", memCell);
		}

		this.memory[memCell] = num;

		return "";
	}

	public String load(String register, int memCell) {
		if (!this.state.containsKey(register)) {
			return String.format("Register %s does not exist", register);
		}

		if (!((memCell < this.memory.length) && (memCell >= 0))) {
			return String.format("Memory cell num. %d does not exist", memCell);
		}

		this.state.put(register, this.memory[memCell]);

		return "";
	}

	public String store(String register, int memCell) {
		if (!this.state.containsKey(register)) {
			return String.format("Register %s does not exist", register);
		}

		if (!((memCell < this.memory.length) && (memCell >= 0))) {
			return String.format("Memory cell num. %d does not exist", memCell);
		}

		this.memory[memCell] = this.state.get(register);

		return "";
	}
}
