package processor.pipeline;

import processor.Processor;

import javax.lang.model.util.ElementScanner6;

import generic.Statistics;


public class OperandFetch {
	Processor containingProcessor;
	IF_OF_LatchType IF_OF_Latch;
	OF_EX_LatchType OF_EX_Latch;
	EX_MA_LatchType EX_MA_Latch;
	MA_RW_LatchType MA_RW_Latch;
	IF_EnableLatchType IF_EnableLatch;

	static String findTwoscomplement(StringBuffer str)
    {
        int n = str.length();
      
        // Traverse the string to get first '1' from
        // the last of string
        int i;
        for (i = n-1 ; i >= 0 ; i--)
            if (str.charAt(i) == '1')
                break;
      
        // If there exists no '1' concat 1 at the
        // starting of string
        if (i == -1)
            return "1" + str;
      
        // Continue traversal after the position of
        // first '1'
        for (int k = i-1 ; k >= 0; k--)
        {
            //Just flip the values
            if (str.charAt(k) == '1')
                str.replace(k, k+1, "0");
            else
                str.replace(k, k+1, "1");
        }
      
        // return the modified string
        return str.toString();
    }
	public static String toBinary(int x, int len) {
		if (len > 0) {
			if (x < 0) {
				return String.format("%" + len + "s", Integer.toBinaryString(x)).replaceAll(" ", "0")
						.substring(32 - len, 32);
			}
			return String.format("%" + len + "s", Integer.toBinaryString(x)).replaceAll(" ", "0");
		}

		return null;
	}

	boolean isConflict(int latch_opcode, int latch_rd, int rs1, int rs2)
	{
		if(latch_opcode == -1)return false;
		if(rs1 == 31 || rs2 == 31)
		{
			if(latch_opcode == 6 || latch_opcode == 7)
			{
				return true;
			}
		}
		else
		{
			if(latch_opcode <= 22)
			{
				if(latch_rd == rs1 || latch_rd == rs2)return true;
			}
			else return false;
			return false;
		}
		return false;
	}

	public void setBubble()
	{
		IF_EnableLatch.setIF_enable(false);
		OF_EX_Latch.setNop(true);
		OF_EX_Latch.setNull();
	}

	int bitExtracted(int number, int k, int p, int signed)
	{
		String temp=  toBinary(number, 32);
		// System.out.println(temp);
		int n = temp.length();
		String tmp = "";
		for(int i = p - 1, j = 0; j < k && i < n; j++,i++)
		{
			// System.out.print(temp.charAt(i));
			tmp = tmp + Character.toString(temp.charAt(i));
		}
		if(tmp.charAt(0) == '1' && signed == 1)
		{
			StringBuffer sb = new StringBuffer();
			tmp = findTwoscomplement(sb.append(tmp));
			return -1*(int)Long.parseLong(tmp, 2);
		}
		else return (int)Long.parseLong(tmp, 2);
	}

	
	
	public OperandFetch(Processor containingProcessor, IF_OF_LatchType iF_OF_Latch, OF_EX_LatchType oF_EX_Latch, EX_MA_LatchType eX_MA_Latch, MA_RW_LatchType mA_RW_Latch, IF_EnableLatchType iF_EnableLatch) {
		this.containingProcessor = containingProcessor;
		this.IF_OF_Latch = iF_OF_Latch;
		this.OF_EX_Latch = oF_EX_Latch;
		this.EX_MA_Latch = eX_MA_Latch;
		this.MA_RW_Latch = mA_RW_Latch;
		this.IF_EnableLatch = iF_EnableLatch;
	}
	
	public void performOF()
	{
		if(IF_OF_Latch.isOF_enable())
		{
			//TODO
			Statistics.setNumberOfOFInstructions(Statistics.getNumberOfOFInstructions() + 1);
			int instruction = IF_OF_Latch.getInstruction();
			int opcode = bitExtracted(instruction, 5, 1, 0);
			int immediate = 0;
			int branchTarget = 0;
			boolean conflict = false;
			// System.out.println(instruction);
			System.out.print("opcode : ");
			System.out.println(opcode);

			int ex_opcode = OF_EX_Latch.getOpcode(), ex_rs1 = OF_EX_Latch.getRs1();
			int ex_rs2 = OF_EX_Latch.getRs2(), ex_rd = OF_EX_Latch.getRd(), ex_imm = OF_EX_Latch.getImm();

			int ma_opcode = OF_EX_Latch.getOpcode(), ma_rs1 = OF_EX_Latch.getRs1();
			int ma_rs2 = OF_EX_Latch.getRs2(), ma_rd = OF_EX_Latch.getRd(), ma_imm = OF_EX_Latch.getImm();

			int rw_opcode = OF_EX_Latch.getOpcode(), rw_rs1 = OF_EX_Latch.getRs1();
			int rw_rs2 = OF_EX_Latch.getRs2(), rw_rd = OF_EX_Latch.getRd(), rw_imm = OF_EX_Latch.getImm();

			if(opcode >= 24 && opcode <= 28)
			{
				IF_EnableLatch.setIF_enable(false);
			}


			int rs1 = 0, rs2 = 0, rd = 0;
			if(opcode <= 21 && opcode % 2 == 1)
			{
				rs1 = bitExtracted(instruction, 5, 6, 0);
				rd = bitExtracted(instruction, 5, 11, 0);
				immediate = bitExtracted(instruction, 17, 16, 1);
				if(isConflict(ex_opcode, ex_rd, rs1, rs1))conflict = true;
				if(isConflict(ma_opcode, ma_rd, rs1, rs1))conflict = true;
				if(isConflict(rw_opcode, rw_rd, rs1, rs1))conflict = true;

				if(conflict)
				{
					setBubble();
					return;
				}
				System.out.print("rs1 : ");
				System.out.println(rs1);
				System.out.print("rd : ");
				System.out.println(rd);
				System.out.print("immediate : ");
				System.out.println(immediate);

			}
			else if(opcode <= 21 && opcode % 2 == 0)	//r3 type
			{
				rs1 = bitExtracted(instruction, 5, 6, 0);
				rs2 = bitExtracted(instruction, 5, 11, 0);
				rd = bitExtracted(instruction, 5, 16, 0);
				if(isConflict(ex_opcode, ex_rd, rs1, rs2))conflict = true;
				if(isConflict(ma_opcode, ma_rd, rs1, rs2))conflict = true;
				if(isConflict(rw_opcode, rw_rd, rs1, rs2))conflict = true;

				if(conflict)
				{
					setBubble();
					return;
				}
				System.out.print("rs1 : ");
				System.out.println(rs1);
				System.out.print("rs2 : ");
				System.out.println(rs2);
				System.out.print("rd : ");
				System.out.println(rd);

			}
			else if(opcode == 24)	//jmp
			{
				rd = bitExtracted(instruction, 5, 6, 0);
				immediate = bitExtracted(instruction, 22, 11, 1);
				// either one of these will be zero
				System.out.print("rd : ");
				System.out.println(rd);
				System.out.print("immediate : ");
				System.out.println(immediate);
			}
			else if(opcode == 29)	//end
			{
				IF_EnableLatch.setIF_enable(false);
			}
			else if(opcode >= 25 || opcode <= 28)	//beq, bgt, blt, bne
			{
				rs1 = bitExtracted(instruction, 5, 6, 0);
				rd = bitExtracted(instruction, 5, 11, 0);
				immediate = bitExtracted(instruction, 17, 16, 1);
				if(isConflict(ex_opcode, ex_rd, rs1, rd))conflict = true;
				if(isConflict(ma_opcode, ma_rd, rs1, rd))conflict = true;
				if(isConflict(rw_opcode, rw_rd, rs1, rd))conflict = true;
				if(conflict)
				{
					setBubble();
					return;
				}
				System.out.print("rs1 : ");
				System.out.println(rs1);
				System.out.print("rd : ");
				System.out.println(rd);
				System.out.print("immediate : ");
				System.out.println(immediate);
			}
			else 	//simple R2I type, load and store
			{
				rs1 = bitExtracted(instruction, 5, 6, 0);
				rd = bitExtracted(instruction, 5, 11, 0);
				immediate = bitExtracted(instruction, 17, 16, 1);

				if(opcode == 22)
				{
					if(isConflict(ex_opcode, ex_rd, rs1, rs1))conflict = true;
					if(isConflict(ma_opcode, ma_rd, rs1, rs1))conflict = true;
					if(isConflict(rw_opcode, rw_rd, rs1, rs1))conflict = true;
				}
				else if(opcode == 23)
				{
					if(isConflict(ex_opcode, ex_rd, rd, rd))conflict = true;
					if(isConflict(ma_opcode, ma_rd, rd, rd))conflict = true;
					if(isConflict(rw_opcode, rw_rd, rd, rd))conflict = true;
				}
				else
				{
					if(isConflict(ex_opcode, ex_rd, rs1, rs1))conflict = true;
					if(isConflict(ma_opcode, ma_rd, rs1, rs1))conflict = true;
					if(isConflict(rw_opcode, rw_rd, rs1, rs1))conflict = true;
				}
				if(conflict)
				{
					setBubble();
					return;
				}
			}
			IF_EnableLatch.setIF_enable(true);
			
			//set data on latch
			OF_EX_Latch.setRs1(rs1);
			OF_EX_Latch.setRs2(rs2);
			OF_EX_Latch.setRd(rd);
			OF_EX_Latch.setOpcode(opcode);
			OF_EX_Latch.setImm(immediate);
			OF_EX_Latch.setEX_enable(true);
		}
	}

}
