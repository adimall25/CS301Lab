package processor.pipeline;

import processor.Processor;

public class Execute {
	Processor containingProcessor;
	OF_EX_LatchType OF_EX_Latch;
	EX_MA_LatchType EX_MA_Latch;
	EX_IF_LatchType EX_IF_Latch;
	
	public Execute(Processor containingProcessor, OF_EX_LatchType oF_EX_Latch, EX_MA_LatchType eX_MA_Latch, EX_IF_LatchType eX_IF_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.OF_EX_Latch = oF_EX_Latch;
		this.EX_MA_Latch = eX_MA_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
	}
	
	public void performEX()
	{
		//TODO

		//get data from OF_EX_LATCH
		int op1 = OF_EX_Latch.getOp1();
		int op2 = OF_EX_Latch.getOp2();
		int imm = OF_EX_Latch.getImm();
		int branchTarget = OF_EX_Latch.getBranchTarget();
		int opcode = OF_EX_Latch.getOpcode();

		boolean isUBranch = false;
		boolean isBeq = false, isBgt = false, isBne = false, isBlt = false;
		if(opcode == 24)	//if jmp	
		{
			isUBranch = true;
		}

		if(opcode == 25)	//if beq
		{
			if(op1 == op2)isBeq = true;
		}
		if(opcode == 26)	//if bne
		{
			if(op1 != op2)isBne = true;
		}
		if(opcode == 27)	//if blt
		{
			if(op1 < op2)isBlt = true;
		}
		if(opcode == 28)	//if bgt
		{
			if(op1 > op2)isBgt = true;
		}

		//is branch taken or not
		boolean isBranchTaken = isBeq || isBne || isBlt || isBgt || isUBranch;

		//if branch is taken then change PC
		if(isBranchTaken)
		{
			containingProcessor.getRegisterFile().setProgramCounter(branchTarget);
		}
	}

}
