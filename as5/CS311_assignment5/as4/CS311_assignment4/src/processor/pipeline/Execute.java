package processor.pipeline;

import generic.Statistics;
import processor.Processor;

public class Execute {
	Processor containingProcessor;
	OF_EX_LatchType OF_EX_Latch;
	EX_MA_LatchType EX_MA_Latch;
	EX_IF_LatchType EX_IF_Latch;
	IF_EnableLatchType IF_EnableLatch;
	IF_OF_LatchType IF_OF_Latch;
	
	public Execute(Processor containingProcessor, OF_EX_LatchType oF_EX_Latch, EX_MA_LatchType eX_MA_Latch, EX_IF_LatchType eX_IF_Latch, IF_EnableLatchType iF_EnableLatch, IF_OF_LatchType iF_OF_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.OF_EX_Latch = oF_EX_Latch;
		this.EX_MA_Latch = eX_MA_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
		this.IF_EnableLatch = iF_EnableLatch;
		this.IF_OF_Latch = iF_OF_Latch;
	}
	
	public void performEX()
	{
		//TODO

		if(OF_EX_Latch.getNop())
		{
			EX_MA_Latch.setNop(true);
			OF_EX_Latch.setNop(false);
			EX_MA_Latch.setNull();
		}
		else if(OF_EX_Latch.isEX_enable())
		{
			int rs1 = OF_EX_Latch.getRs1(), rs2 = OF_EX_Latch.getRs2(), rd = OF_EX_Latch.getRd();
			int imm = OF_EX_Latch.getImm();
			int opcode = OF_EX_Latch.getOpcode();
			int currentPC = containingProcessor.getRegisterFile().getProgramCounter() - 1;

			if(opcode <= 29 && opcode >= 24)	//branching and end
			{
				Statistics.setNumberOfBranchTaken(Statistics.getNumberOfBranchTaken() + 2);
				IF_EnableLatch.setIF_enable(false);
				IF_OF_Latch.setOF_enable(false);
				OF_EX_Latch.setEX_enable(false);
			}

			int alu = 0;

			if(opcode <= 21 && opcode % 2 == 0)
			{
				int rs1Val = containingProcessor.getRegisterFile().getValue(rs1);
				int rs2Val = containingProcessor.getRegisterFile().getValue(rs2);

				switch(opcode)
				{
					case 0:
						alu = rs1Val + rs2Val;
						break;
					case 2:
						alu = rs1Val - rs2Val;
					case 4:
						alu = rs1Val * rs2Val;
						break;
					case 6:
						alu = rs1Val/rs2Val;
						break;
					case 8:
						alu = rs1Val & rs2Val;
						break;
					case 10:
						alu = rs1Val | rs2Val;
						break;
					case 12:
						alu = rs1Val ^ rs2Val;
						break;
					case 14:
						if(rs1Val < rs2Val)alu = 1;
						else alu = 0;
						break;
					case 16:
						alu = rs1Val << rs2Val;
						break;
					case 18:
						alu = rs1Val >>> rs2Val;
						break;
					case 20:
						alu = rs1Val >> rs2Val;
						break;
					default:
						break;
				}
			}
			else if(opcode == 22)
			{
				int rs1Val = containingProcessor.getRegisterFile().getValue(rs1);
				alu = rs1Val + imm;
			}
			else if(opcode == 23)
			{
				int rdVal = containingProcessor.getRegisterFile().getValue(rd);
				alu = rdVal + imm;
			}
			else if(opcode == 24)
			{
				int immVal;
				if(imm == 0)
				{
					immVal = containingProcessor.getRegisterFile().getValue(rd);
				}
				else
				{
					immVal = imm;
				}
				alu = immVal + currentPC;
				EX_IF_Latch.setIS_enable(true);
				EX_IF_Latch.setPC(alu);
			}
			else if(opcode >= 25 && opcode <= 28)
			{
				int rs1Val = containingProcessor.getRegisterFile().getValue(rs1);
				int rdVal = containingProcessor.getRegisterFile().getValue(rd);
				if(opcode == 25)
				{
					if(rs1Val == rdVal)
					{
						alu = imm + currentPC;
						EX_IF_Latch.setIS_enable(true);
						EX_IF_Latch.setPC(alu);
					}
				}
				else if(opcode == 26)
				{
					if(rs1Val != rdVal)
					{
						alu = imm + currentPC;
						EX_IF_Latch.setIS_enable(true);
						EX_IF_Latch.setPC(alu);
					}
				}
				else if(opcode == 27)
				{
					if(rs1Val < rdVal)
					{
						alu = imm + currentPC;
						EX_IF_Latch.setIS_enable(true);
						EX_IF_Latch.setPC(alu);
					}
				}
				else if(opcode == 28)
				{
					if(rs1Val > rdVal)
					{
						alu = imm + currentPC;
						EX_IF_Latch.setIS_enable(true);
						EX_IF_Latch.setPC(alu);
					}
				}
				else;
			}
			else if(opcode <= 21 && opcode % 2 == 1)
			{
				int rs1Val = containingProcessor.getRegisterFile().getValue(rs1);
				switch(opcode)
				{
					case 1:
						alu = rs1Val + imm;
						break;
					case 3:
						alu = rs1Val - imm;
						break;
					case 5:
						alu = rs1Val * imm;
						break;
					case 7:
						alu = rs1Val / imm;
						break;
					case 9:
						alu = rs1Val & imm;
						break;
					case 11:
						alu = rs1Val | imm;
						break;
					case 13:
						alu = rs1Val ^ imm;
						break;
					case 15:
						if(rs1Val < imm)alu = 1;
						else alu = 0;
						break;
					case 17:
						alu = rs1Val << imm;
						break;
					case 19:
						alu = rs1Val >>>imm;
						break;
					case 21:
						alu = rs1Val >> imm;
						break;
					default: break;
				}
			}
			else;
			
			//set data from this stage to the next latch
			EX_MA_Latch.setAluResult(alu);
			EX_MA_Latch.setMA_enable(true);
			EX_MA_Latch.setOpcode(opcode);
			EX_MA_Latch.setRs1(rs1);
			EX_MA_Latch.setRs2(rs2);
			EX_MA_Latch.setRd(rd);
			
		}
		else;
	}
		

}
