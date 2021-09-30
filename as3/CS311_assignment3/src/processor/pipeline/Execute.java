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
		if(OF_EX_Latch.isEX_enable())
		{
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


			//ALU code
			int A = op1;
			int B = 0, aluResult = 0;

			if((opcode <= 21 && opcode % 2 == 1))
			{
				B = imm;
			}
			else if(opcode <= 21 && opcode % 2 == 0)
			{
				B = op2;
			}
			else;

			//calculate aluResult
			switch(opcode)
			{
				case 0:
				case 1:
					aluResult = A + B;
					break;
				case 2:
				case 3:
					aluResult = A - B;
					break;
				case 4:
				case 5:
					aluResult = A*B;
					break;
				case 6:
				case 7:
					aluResult = A/B;
					break;
				case 8:
				case 9:
					aluResult = A&B;
					break;
				case 10:
				case 11:
					aluResult = A|B;
					break;
				case 12:
				case 13:
					aluResult = A^B;
					break;
				case 14:
				case 15:
					if(A < B)
					{
						aluResult = 1;
					}
					else
					{
						aluResult = 0;
					}
					break;
				case 16:
				case 17:
					aluResult = A<<B;
					break;
				case 18:
				case 19:
					aluResult = A>>B;
					break;
				case 20:
				case 21:
					aluResult = A>>>B;
					break;
			}

			if(opcode == 22)	//if load
			{
				aluResult = op1 + imm;
			}
			if(opcode == 23)	//if store
			{
				aluResult = op2 + imm;
			}

			//set data from this stage to the next latch
			EX_MA_Latch.setOpcode(opcode);
			EX_MA_Latch.setAluResult(aluResult);
			EX_MA_Latch.setOp2(op2);
			OF_EX_Latch.setEX_enable(false);
			EX_MA_Latch.setMA_enable(true);
		}
	}
		

}
