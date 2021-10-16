package processor.pipeline;

import processor.Processor;

public class OperandFetch {
	Processor containingProcessor;
	IF_OF_LatchType IF_OF_Latch;
	OF_EX_LatchType OF_EX_Latch;

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
			int opcode = bitExtracted(instruction, 5, 1, 0);
			int immediate = 0;
			int branchTarget = 0;
			// System.out.println(instruction);
			System.out.print("opcode : ");
			System.out.println(opcode);

			int rs1 = 0, rs2 = 0, rd = 0;
			if(opcode <= 21 && opcode % 2 == 1)
			{
				rs1 = bitExtracted(instruction, 5, 6, 0);
				rd = bitExtracted(instruction, 5, 11, 0);
				immediate = bitExtracted(instruction, 17, 16, 1);
				System.out.print("rs1 : ");
				System.out.println(rs1);
				System.out.print("rd : ");
				System.out.println(rd);
				System.out.print("immediate : ");
				System.out.println(immediate);

			}
			else if(opcode <= 21 && opcode % 2 == 0)
			{
				rs1 = bitExtracted(instruction, 5, 6, 0);
				rs2 = bitExtracted(instruction, 5, 11, 0);
				rd = bitExtracted(instruction, 5, 16, 0);
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
				System.out.print("rd : ");
				System.out.println(rd);
				System.out.print("immediate : ");
				System.out.println(immediate);
			}
			else if(opcode == 29)	//end
			{
				//nothing
			}
			else
			{
				rs1 = bitExtracted(instruction, 5, 6, 0);
				rd = bitExtracted(instruction, 5, 11, 0);
				immediate = bitExtracted(instruction, 17, 16, 1);
				System.out.print("rs1 : ");
				System.out.println(rs1);
				System.out.print("rd : ");
				System.out.println(rd);
				System.out.print("immediate : ");
				System.out.println(immediate);
			}
			int op1 = containingProcessor.getRegisterFile().getValue(rs1);
			int op2 = 0;
			
			if(opcode == 23 || (opcode  >= 25 && opcode <= 28))	//store command
			{
				op2 = containingProcessor.getRegisterFile().getValue(rd);
			}
			else
			{
				op2 = containingProcessor.getRegisterFile().getValue(rs2);
			}
			if(opcode >= 24 && opcode <= 28)
			{
				branchTarget = containingProcessor.getRegisterFile().getProgramCounter() + immediate;
			}

			
			//set data on latch
			OF_EX_Latch.setRs1(rs1);
			OF_EX_Latch.setRs2(rs2);
			OF_EX_Latch.setRd(rd);
			OF_EX_Latch.setOpcode(opcode);
			OF_EX_Latch.setBranchTarget(branchTarget);
			OF_EX_Latch.setOp1(op1);
			OF_EX_Latch.setOp2(op2);
			OF_EX_Latch.setImm(immediate);
			IF_OF_Latch.setOF_enable(false);
			OF_EX_Latch.setEX_enable(true);
		}
	}

}
