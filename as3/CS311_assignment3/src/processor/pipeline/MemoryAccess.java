package processor.pipeline;

import processor.Processor;

public class MemoryAccess {
	Processor containingProcessor;
	EX_MA_LatchType EX_MA_Latch;
	MA_RW_LatchType MA_RW_Latch;
	
	public MemoryAccess(Processor containingProcessor, EX_MA_LatchType eX_MA_Latch, MA_RW_LatchType mA_RW_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.EX_MA_Latch = eX_MA_Latch;
		this.MA_RW_Latch = mA_RW_Latch;
	}
	
	public void performMA()
	{
		//TODO
		if(EX_MA_Latch.isMA_enable())
		{
			//fetch data from latch
			int rs1 = EX_MA_Latch.getRs1(), rs2 = EX_MA_Latch.getRs2(), rd = EX_MA_Latch.getRd();
			int opcode = EX_MA_Latch.getOpcode();
			int op2 = EX_MA_Latch.getOp2();
			int op1 = EX_MA_Latch.getOp1();
			int aluResult = EX_MA_Latch.getAluResult();

			if(opcode == 22)	//if load
			{
				containingProcessor.getRegisterFile().setValue(rd, containingProcessor.getMainMemory().getWord(aluResult));
			}
			if(opcode == 23)	//if store
			{
				containingProcessor.getMainMemory().setWord(aluResult, containingProcessor.getRegisterFile().getValue(rs1));
			}

			MA_RW_Latch.setOp1(op1);
			MA_RW_Latch.setOp2(op2);
			MA_RW_Latch.setOpcode(opcode);
			MA_RW_Latch.setAluResult(aluResult);
			MA_RW_Latch.setRs1(rs1);
			MA_RW_Latch.setRs2(rs2);
			MA_RW_Latch.setRd(rd);
			EX_MA_Latch.setMA_enable(false);
			MA_RW_Latch.setRW_enable(true);
		}
	}

}
