package processor.pipeline;

import processor.Processor;

public class OperandFetch {
	Processor containingProcessor;
	IF_OF_LatchType IF_OF_Latch;
	OF_EX_LatchType OF_EX_Latch;

	int bitExtracted(int number, int k, int p)
	{
		return (((1 << k) - 1) & (number >> (p - 1)));
	}
	
	public OperandFetch(Processor containingProcessor, IF_OF_LatchType iF_OF_Latch, OF_EX_LatchType oF_EX_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.IF_OF_Latch = iF_OF_Latch;
		this.OF_EX_Latch = oF_EX_Latch;
	}
	
	public void performOF()
	{
		if(IF_OF_Latch.isOF_enable())
		{
			//TODO
			int instruction = IF_OF_Latch.getInstruction();
			int opcode = bitExtracted(instruction, 1, 5);
			int immediate = bitExtracted(instruction, 16, 18);
			int branchOffset = bitExtracted(instruction, 6, 27);
			branchOffset = branchOffset * 4;

			int rs1 = 0, rs2 = 0, rd = 0;
			if(opcode <= 21 && opcode % 2 == 1)
			{
				rs1 = bitExtracted(instruction, 6, 5);
				rd = bitExtracted(instruction, 11, 5);
				immediate = bitExtracted(instruction, 16, 17);
			}
			else if(opcode <= 21 && opcode % 2 == 0)
			{
				rs1 = bitExtracted(instruction, 6, 5);
				rs2 = bitExtracted(instruction, 11, 5);
				rd = bitExtracted(instruction, 16, 5);
			}
			else if(opcode == 24)	//jmp
			{
				rd = bitExtracted(instruction, 6, 5);
				immediate = bitExtracted(instruction, 11, 22);
			}
			else if(opcode == 29)	//end
			{
				//nothing
			}
			else
			{
				rs1 = bitExtracted(instruction, 6, 5);
				rd = bitExtracted(instruction, 11, 5);
				immediate = bitExtracted(instruction, 16, 17);
			}
			int op1 = containingProcessor.getRegisterFile().getValue(rs1);
			int op2 = 0;
			
			if(opcode == 23)	//store command
			{
				op2 = containingProcessor.getRegisterFile().getValue(rd);
			}
			else
			{
				op2 = containingProcessor.getRegisterFile().getValue(rs2);
			}
			
			OF_EX_Latch.setBranchTarget(branchOffset);
			OF_EX_Latch.setOp1(op1);
			OF_EX_Latch.setOp1(op1);
			OF_EX_Latch.setImm(immediate);
			IF_OF_Latch.setOF_enable(false);
			OF_EX_Latch.setEX_enable(true);
		}
	}

}
