package generic;

import java.io.PrintWriter;

public class Statistics {
	
	// TODO add your statistics here
	static long numberOfInstructions;
	static long numberOfCycles;
	

	public static void printStatistics(String statFile)
	{
		try
		{
			PrintWriter writer = new PrintWriter(statFile);
			
			writer.println("Number of instructions executed = " + numberOfInstructions);
			writer.println("Number of cycles taken = " + numberOfCycles);
			
			// TODO add code here to print statistics in the output file
			
			writer.close();
		}
		catch(Exception e)
		{
			Misc.printErrorAndExit(e.getMessage());
		}
	}
	
	// TODO write functions to update statistics
	public static void setNumberOfInstructions(long numberOfInstructions) {
		Statistics.numberOfInstructions = numberOfInstructions;
	}

	public static void setNumberOfCycles(long numberOfCycles) {
		Statistics.numberOfCycles = numberOfCycles;
	}

	public static void setNumberOfOFInstructions(int numberOfOFStageInstructions) {
		Statistics.numberOfOFStageInstructions = numberOfOFStageInstructions;
	}
	
	public static void setnumberOfRegisterWriteInstructions(int numberOfRegisterWriteInstructions) {
		Statistics.numberOfRegisterWriteInstructions = numberOfRegisterWriteInstructions;
	}

	public static void setNumberOfBranchTaken(int numberOfBranchTaken) {
		Statistics.numberOfBranchTaken = numberOfBranchTaken;
	}

	public static int getNumberOfInstructions() {
		return numberOfInstructions;
	}

	public static int getNumberOfCycles() {
		return numberOfCycles;
	}

	public static int getNumberOfOFInstructions() {
		return numberOfOFStageInstructions;
	}

	public static int getNumberOfRegisterWriteInstructions() {
		return numberOfRegisterWriteInstructions;
	}

	public static int getNumberOfBranchTaken() {
		return numberOfBranchTaken;
	}
}
