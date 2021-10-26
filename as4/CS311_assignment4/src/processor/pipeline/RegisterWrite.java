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
		System.out.println("---------------------Register writeback-------------------------");
		if (MA_RW_Latch.getNop()) {
			System.out.println("nop");
			MA_RW_Latch.setNop(false);
			IF_EnableLatch.setIF_enable(true);
		}
		else if(MA_RW_Latch.isRW_enable())
		{
			//TODO
			
			// if instruction being processed is an end instruction, remember to call Simulator.setSimulationComplete(true);
			Statistics.setnumberOfRegisterWriteInstructions(Statistics.getNumberOfRegisterWriteInstructions() + 1);
			int opcode = MA_RW_Latch.getOpcode();
			if(opcode == -1)return;
			int aluResult = MA_RW_Latch.getAluResult();
			int loadResult = MA_RW_Latch.getLoadResult();
			int rs1 = MA_RW_Latch.getRs1();
			int rs2 = MA_RW_Latch.getRs2();
			int rd = MA_RW_Latch.getRd();
			int imm = MA_RW_Latch.getImm();
			// System.out.println("----------------------------------------------");
			System.out.print("RW PC : ");
			System.out.println(containingProcessor.getRegisterFile().getProgramCounter() - 1);
			System.out.print("opcode : ");
			System.out.println(opcode);
			System.out.print("rs1 : ");
			System.out.println(rs1);
			System.out.print("rs2 : ");
			System.out.println(rs2);
			System.out.print("rd : ");
			System.out.println(rd);

			if(opcode == 29)
			{
				MA_RW_Latch.setRW_enable(false);
				Simulator.setSimulationComplete(true);
			}
			else if(opcode == 22)
			{
				containingProcessor.getRegisterFile().setValue(rd, loadResult);
			}			
			else if(opcode <= 21)
			{
				if(opcode == 6)
				{
					int rs1Val = containingProcessor.getRegisterFile().getValue(rs1);
					int rs2Val = containingProcessor.getRegisterFile().getValue(rs2);
					containingProcessor.getRegisterFile().setValue(31, rs1Val % rs2Val);
				}
				if(opcode == 7)
				{
					int rs1Val = containingProcessor.getRegisterFile().getValue(rs1);
					containingProcessor.getRegisterFile().setValue(31, rs1Val % imm);
				}
				containingProcessor.getRegisterFile().setValue(rd, aluResult);
			}
			else;

			if (opcode < 29) {
				IF_EnableLatch.setIF_enable(true);
			}
		}
	}

}
