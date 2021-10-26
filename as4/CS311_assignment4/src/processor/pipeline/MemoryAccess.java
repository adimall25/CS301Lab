package processor.pipeline;

import processor.Processor;

public class MemoryAccess {
	Processor containingProcessor;
	EX_MA_LatchType EX_MA_Latch;
	MA_RW_LatchType MA_RW_Latch;
	IF_EnableLatchType IF_EnableLatch;
	
	public MemoryAccess(Processor containingProcessor, EX_MA_LatchType eX_MA_Latch, MA_RW_LatchType mA_RW_Latch, IF_EnableLatchType iF_EnableLatch)
	{
		this.containingProcessor = containingProcessor;
		this.EX_MA_Latch = eX_MA_Latch;
		this.MA_RW_Latch = mA_RW_Latch;
		this.IF_EnableLatch = iF_EnableLatch;
	}
	
	public void performMA()
	{
		System.out.println("---------------------Memory Access-------------------------");
		//TODO
		if (EX_MA_Latch.getNop()) 
		{
			System.out.println("nop");
			MA_RW_Latch.setNop(true);
			EX_MA_Latch.setNop(false);
			MA_RW_Latch.setNull();
			// int opcode = MA_RW_Latch.getOpcode();
			// int rs1 = MA_RW_Latch.getRs1();
			// int rs2 = MA_RW_Latch.getRs2();
			// int rd = MA_RW_Latch.getRd();
			// System.out.println(containingProcessor.getRegisterFile().getProgramCounter() - 1);
			// System.out.print("opcode : ");
			// System.out.println(opcode);
			// System.out.print("rs1 : ");
			// System.out.println(rs1);
			// System.out.print("rs2 : ");
			// System.out.println(rs2);
			// System.out.print("rd : ");
			// System.out.println(rd);
		}
		else if(EX_MA_Latch.isMA_enable())
		{
			//fetch data from latch
			int rs1 = EX_MA_Latch.getRs1(), rs2 = EX_MA_Latch.getRs2(), rd = EX_MA_Latch.getRd();
			int opcode = EX_MA_Latch.getOpcode();
			int imm = EX_MA_Latch.getImm();
			if(opcode == -1)return;
			int aluResult = EX_MA_Latch.getAluResult();
			// System.out.println("----------------------------------------------");
			System.out.print("MA PC : ");
			System.out.println(containingProcessor.getRegisterFile().getProgramCounter() - 1);
			System.out.print("opcode : ");
			System.out.println(opcode);
			System.out.print("rs1 : ");
			System.out.println(rs1);
			System.out.print("rs2 : ");
			System.out.println(rs2);
			System.out.print("rd : ");
			System.out.println(rd);

			if(opcode == 22)	//if load
			{
				MA_RW_Latch.setLoadResult(containingProcessor.getMainMemory().getWord(aluResult));
			}
			if(opcode == 23)	//if store
			{
				containingProcessor.getMainMemory().setWord(aluResult, containingProcessor.getRegisterFile().getValue(rs1));
			}
			if(opcode == 29)
			{
				IF_EnableLatch.setIF_enable(false);
			}
			if(opcode == 29)EX_MA_Latch.setMA_enable(false);
			MA_RW_Latch.setOpcode(opcode);
			MA_RW_Latch.setAluResult(aluResult);
			MA_RW_Latch.setRs1(rs1);
			MA_RW_Latch.setRs2(rs2);
			MA_RW_Latch.setRd(rd);
			MA_RW_Latch.setImm(imm);
			MA_RW_Latch.setRW_enable(true);
		}
	}

}
