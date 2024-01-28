package RMPackage;

import java.io.*;

public class RMEmulator {

	//stores the values of the registers
	int[] Registers;
	
	//stores the value of the accumulator
	int Accumulator;

	int CommandCounter = 1;
	
	//counter for the number of commands, which the program has executed
	int CEC = 0;
	
	//check if an END command has been executed
	boolean end = false;

	BufferedReader reader;
	String Path;

	public RMEmulator(int RegisterAmount) {
		//specifies the number of registers
		this.Registers = new int[RegisterAmount];
	}

	//Resets the machine
	public void RESET() {
		for (int i = 0; i < Registers.length; i++) {
			Registers[i] = 0;
		}
		CommandCounter = 1;
		end = false;
		Accumulator = 0;
	}

	//Load the value of Rx into the Accumulator
	public void LOAD(int Rx) {
		Accumulator = Registers[Rx];
		CommandCounter++;
	}

	//Directly loads a value to the Accumulator
	public void DLOAD(int Number) {
		Accumulator = Number;
		CommandCounter++;
	}

	//Stores the value in the Accumulator in Rx
	public void STORE(int Rx) {
		Registers[Rx] = Accumulator;
		CommandCounter++;
	}

	//Adds the value in Rx to the Accumulator
	public void ADD(int Rx) {
		Accumulator += Registers[Rx];
		CommandCounter++;
	}

	//Subtracts the value in Rx to the Accumulator
	public void SUB(int Rx) {
		Accumulator -= Registers[Rx];
		CommandCounter++;
	}

	//Multiplies the value in Rx to the Accumulator
	public void MULT(int Rx) {
		Accumulator *= Registers[Rx];
		CommandCounter++;
	}

	//Divides the value in Rx to the Accumulator
	public void DIV(int Rx) {
		Accumulator /= Registers[Rx];
		CommandCounter++;
	}

	//Jumps to a specified line in the program
	public void JUMP(int Line) {
		CommandCounter = Line;
	}

	//Jumps if the value in the Accumulator is >= 0
	public void JGE(int Line) {
		if (Accumulator >= 0) {
			CommandCounter = Line;
		} else {
			CommandCounter++;
		}
	}

	//Jumps if the value in the Accumulator is > 0
	public void JGT(int Line) {
		if (Accumulator > 0) {
			CommandCounter = Line;
		} else {
			CommandCounter++;
		}
	}

	//Jumps if the value in the Accumulator is <= 0
	public void JLE(int Line) {
		if (Accumulator <= 0) {
			CommandCounter = Line;
		} else {
			CommandCounter++;
		}
	}

	//Jumps if the value in the Accumulator is < 0
	public void JLT(int Line) {
		if (Accumulator < 0) {
			CommandCounter = Line;
		} else {
			CommandCounter++;
		}
	}

	//Jumps if the value in the Accumulator is == 0
	public void JEQ(int Line) {
		if (Accumulator == 0) {
			CommandCounter = Line;
		} else {
			CommandCounter++;
		}
	}

	//Jumps if the value in the Accumulator is != 0
	public void JNE(int Line) {
		if (Accumulator != 0) {
			CommandCounter = Line;
		} else {
			CommandCounter++;
		}
	}

	//Ends the program
	public void END() {
		CommandCounter++;
		end = true;
	}

	//sets the path of the txt file 
	public void READFILE(String Path) throws IOException {
		this.Path = Path;
	}

	//executes one command
	public void EXECUTENEXTCOMMAND() throws IOException {
		this.reader = new BufferedReader(new FileReader(Path));

		for (int i = 0; i < CommandCounter - 1; i++) {
			reader.readLine();
		}
		String line = reader.readLine();
		System.out.println(line);
		//System.out.println(CommandCounter);

		if (line.contains("DLOAD")) {
			String[] linecmd = line.split(" ");
			DLOAD(Integer.parseInt(linecmd[2]));
		} else {
			if (line.contains("LOAD")) {
				String[] linecmd = line.split(" ");
				LOAD(Integer.parseInt(linecmd[2]));
			}
		}
		if (line.contains("STORE")) {
			String[] linecmd = line.split(" ");
			STORE(Integer.parseInt(linecmd[2]));
		}
		if (line.contains("ADD")) {
			String[] linecmd = line.split(" ");
			ADD(Integer.parseInt(linecmd[2]));
		}
		if (line.contains("SUB")) {
			String[] linecmd = line.split(" ");
			SUB(Integer.parseInt(linecmd[2]));
		}
		if (line.contains("MULT")) {
			String[] linecmd = line.split(" ");
			MULT(Integer.parseInt(linecmd[2]));
		}
		if (line.contains("DIV")) {
			String[] linecmd = line.split(" ");
			DIV(Integer.parseInt(linecmd[2]));
		}
		if (line.contains("JUMP")) {
			String[] linecmd = line.split(" ");
			JUMP(Integer.parseInt(linecmd[2]));
		}
		if (line.contains("JGE")) {
			String[] linecmd = line.split(" ");
			JGE(Integer.parseInt(linecmd[2]));
		}
		if (line.contains("JGT")) {
			String[] linecmd = line.split(" ");
			JGT(Integer.parseInt(linecmd[2]));
		}
		if (line.contains("JLE")) {
			String[] linecmd = line.split(" ");
			JLE(Integer.parseInt(linecmd[2]));
		}
		if (line.contains("JLT")) {
			String[] linecmd = line.split(" ");
			JLT(Integer.parseInt(linecmd[2]));
		}
		if (line.contains("JEQ")) {
			String[] linecmd = line.split(" ");
			JEQ(Integer.parseInt(linecmd[2]));
		}
		if (line.contains("JNE")) {
			String[] linecmd = line.split(" ");
			JNE(Integer.parseInt(linecmd[2]));
		}
		if (line.contains("END")) {
			END();
		}
		CEC++;
	}

	//runs the program
	public void RUN() throws IOException {
		RESET();

		while (!end) {
			EXECUTENEXTCOMMAND();
		}
		dump();
		System.out.println("Program has finished running with " + CEC + " commands executed");
	}

	//dumps the memory on screen
	public void dump() {
		for (int i = 0; i < Registers.length; i++) {
			System.out.print("R" + i + ": " + Registers[i] + " ");

			if ((i + 1) % 4 == 0) {
				System.out.print("\n");
			}
		}

		System.out.print("Akk: " + Accumulator);
		System.out.print("\n\n");

	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		RMEmulator e1 = new RMEmulator(16);
		e1.READFILE(""); //Path of the txt file thats going to be executed
		e1.RUN();
	}

}
