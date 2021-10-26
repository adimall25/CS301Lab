package processor.pipeline;

import javax.lang.model.util.ElementScanner6;

import generic.Simulator;
import processor.Processor;
import generic.Statistics;


public class RegisterWrite {
	Processor containingProcessor;
	MA_RW_LatchType MA_RW_Latch;
	IF_EnableLatchType IF_EnableLatch;
	
	public RegisterWrite(Processor containingProcessor, MA_RW_LatchType mA_RW_Latch, IF_EnableLatchType iF_EnableLatch)
	{
		this.containingProcessor = containingProcessor;
		this.MA_RW_Latch = mA_RW_Latch;
		this.IF_EnableLatch = iF_EnableLatch;
	}
	
	public void performRW()
	{
		if (MA_RW_Latch.getNop()) {
			MA_RW_Latch.setNop(false);
		}
		else if(MA_RW_Latch.isRW_enable())
		{
			//TODO
			
			// if instruction being processed is an end instruction, remember to call Simulator.setSimulationComplete(true);
			Statistics.setnumberOfRegisterWriteInstructions(Statistics.getNumberOfRegisterWriteInstructions() + 1);
			int opcode = MA_RW_Latch.getOpcode();
			int aluResult = MA_RW_Latch.getAluResult();
			int loadResult = MA_RW_Latch.getLoadResult();
			int rs1 = MA_RW_Latch.getRs1();
			int rs2 = MA_RW_Latch.getRs2();
			int rd = MA_RW_Latch.getRd();

			if(opcode == 29)
			{
				Simulator.setSimulationComplete(true);
			}
			else if(opcode == 22)
			{
				containingProcessor.getRegisterFile().setValue(rd, loadResult);
			}			
			else if(opcode <= 21)
			{
				containingProcessor.getRegisterFile().setValue(rd, aluResult);
			}
			else;

			if (opcode < 29) {
				IF_EnableLatch.setIF_enable(true);
			}
		}
	}

}
