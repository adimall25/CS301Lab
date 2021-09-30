package processor.pipeline;

import generic.Simulator;
import processor.Processor;

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
		if(MA_RW_Latch.isRW_enable())
		{
			//TODO
			
			// if instruction being processed is an end instruction, remember to call Simulator.setSimulationComplete(true);
			int opcode = MA_RW_Latch.getOpcode();
			int op1 = MA_RW_Latch.getOp1();
			int op2 = MA_RW_Latch.getOp2();
			int aluResult = MA_RW_Latch.getAluResult();
			int rs1 = MA_RW_Latch.getRs1();
			int rs2 = MA_RW_Latch.getRs2();
			int rd = MA_RW_Latch.getRd();

			if(opcode == 29)
			{
				MA_RW_Latch.setRW_enable(false);
				Simulator.setSimulationComplete(true);
			}
			else
			{
				if(opcode <= 21)
				{
					containingProcessor.getRegisterFile().setValue(rd, aluResult);
				}
				MA_RW_Latch.setRW_enable(false);
				IF_EnableLatch.setIF_enable(true);
			}
			
			
		}
	}

}
