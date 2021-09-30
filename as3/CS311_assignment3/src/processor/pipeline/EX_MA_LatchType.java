package processor.pipeline;

public class EX_MA_LatchType {
	
	boolean MA_enable;
	int rs1, rs2, rd;
	int op2;
	int op1;
	int aluResult;
	int opcode;
	
	public EX_MA_LatchType()
	{
		MA_enable = false;
	}

	public void setRs1(int rs1)
	{
		this.rs1 = rs1;
	}

	public void setRs2(int rs2)
	{
		this.rs2 = rs2;
	}

	public void setRd(int rd)
	{
		this.rd = rd;
	}

	public void setOp2(int op2)
	{
		this.op2 = op2;
	}

	public void setOp1(int op1)
	{
		this.op1 = op1;
	}

	public void setAluResult(int aluResult)
	{
		this.aluResult = aluResult;
	}

	public void setOpcode(int opcode)
	{
		this.opcode = opcode;
	}

	public boolean isMA_enable() {
		return MA_enable;
	}

	public int getOp2()
	{
		return op2;
	}

	public int getOp1()
	{
		return op1;
	}

	public int getAluResult()
	{
		return aluResult;
	}

	public int getOpcode()
	{
		return opcode;
	}
	
	public int getRs1()
	{
		return this.rs1;
	}

	public int getRs2()
	{
		return this.rs2;
	}

	public int getRd()
	{
		return this.rd;
	}

	public void setMA_enable(boolean mA_enable) {
		MA_enable = mA_enable;
	}

}
