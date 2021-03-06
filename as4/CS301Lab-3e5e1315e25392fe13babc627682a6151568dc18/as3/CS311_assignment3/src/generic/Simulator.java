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
					processor.getMainMemory().setWord(cnt - 1, intRead);
				}
				cnt++;
			}
			processor.getRegisterFile().setValue(0, 0);
			int temp = (int)Math.pow(2, 16) - 1;
			processor.getRegisterFile().setValue(1, temp);
			processor.getRegisterFile().setValue(2, temp);
			// System.out.println(processor.getRegisterFile().getValue(0));
			processor.getRegisterFile().setProgramCounter(pc);
			System.out.println(processor.getMainMemory().getContentsAsString(0, 9));
		}
		catch(IOException exc)
		{
			exc.printStackTrace();
		}
		
	}
	
	public static void simulate()
	{
		int cnt = 0;
		while(simulationComplete == false)
		{
			cnt++;
			// System.out.println(cnt);
			processor.getIFUnit().performIF();
			Clock.incrementClock();
			processor.getOFUnit().performOF();
			Clock.incrementClock();
			processor.getEXUnit().performEX();
			Clock.incrementClock();
			processor.getMAUnit().performMA();
			Clock.incrementClock();
			processor.getRWUnit().performRW();
			Clock.incrementClock();
			for(int i = 0; i < 32; i++)
			{
				System.out.print("register " + i +" : ");
				System.out.println(processor.getRegisterFile().getValue(i));
			}
			// System.out.println(processor.getMainMemory().getContentsAsString(0, 29));
		}
		
		// TODO
		// set statistics
		Statistics.setNumberOfCycles(Clock.getCurrentTime());
		Statistics.setNumberOfInstructions(cnt);
	}
	
	public static void setSimulationComplete(boolean value)
	{
		simulationComplete = value;
	}
}
