package generic;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import processor.Clock;
import processor.Processor;
import processor.memorysystem.MainMemory;

public class Simulator {
		
	static Processor processor;
	static boolean simulationComplete;
	
	public static void setupSimulation(String assemblyProgramFile, Processor p)
	{
		Simulator.processor = p;
		loadProgram(assemblyProgramFile);
		
		simulationComplete = false;
	}
	
	static void loadProgram(String assemblyProgramFile)
	{
		/*
		 * TODO
		 * 1. load the program into memory according to the program layout described
		 *    in the ISA specification
		 * 2. set PC to the address of the first instruction in the main
		 * 3. set the following registers:
		 *     x0 = 0
		 *     x1 = 65535
		 *     x2 = 65535
		 */
		int pc = 0;
		int cnt = 0;
		try
		{
			InputStream inputFile = new FileInputStream(assemblyProgramFile);
			int intRead = 0;
			byte[] b = new byte[4];
			while(inputFile.available() > 0)
			{
				intRead = inputFile.read(b);
				ByteBuffer buff = ByteBuffer.wrap(b);
				intRead = buff.getInt();
				if(cnt == 0)pc = intRead;
				else 
				{
					// System.out.println(cnt);
					processor.getMainMemory().setWord(cnt - 1, intRead);
				}
				cnt++;
			}
			processor.getRegisterFile().setValue(0, 0);
			int temp = (int)Math.pow(2, 16) - 1;
			processor.getRegisterFile().setValue(1, temp);
			processor.getRegisterFile().setValue(2, temp);
			processor.getRegisterFile().setProgramCounter(pc);
		}
		catch(IOException exc)
		{
			exc.printStackTrace();
		}
		
	}
	
	public static void simulate()
	{
		while(simulationComplete == false)
		{
			processor.getRWUnit().performRW();
			Clock.incrementClock();
			processor.getMAUnit().performMA();
			Clock.incrementClock();	
			processor.getEXUnit().performEX();
			Clock.incrementClock();	
			processor.getOFUnit().performOF();
			Clock.incrementClock();
			processor.getIFUnit().performIF();
			Clock.incrementClock();
			Statistics.setNumberOfInstructions(Statistics.getNumberOfInstructions() + 1);
			Statistics.setNumberOfCycles(Statistics.getNumberOfCycles() + 1);
		}
		
		// TODO
		// set statistics
		System.out.println("Number of cycles : " + Statistics.getNumberOfCycles());
		System.out.println("Number of OF stalls : " + (Statistics.getNumberOfInstructions() - Statistics.getNumberOfRegisterWriteInstructions()));
		System.out.println("Number of incorrect branch instructions : " + Statistics.getNumberOfBranchTaken());
	}
	
	public static void setSimulationComplete(boolean value)
	{
		simulationComplete = value;
	}
}
