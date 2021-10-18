package processor.pipeline;

public class EX_MA_LatchType {
	
	boolean MA_enable;
	int rs1, rs2, rd;
	int aluResult;
	int opcode;
	boolean nop;
	
	public EX_MA_LatchType()
	{
		MA_enable = false;
		nop = false;
		rs1 = -1;
		rs2 = -1;
		rd = -1;
		opcode = -1;
	}

	public void setNull()
	{
		rs1 = -1;
		rs2 = -1;
		rd = -1;
		opcode = -1;
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

	public void setAluResult(int aluResult)
	{
		this.aluResult = aluResult;
	}

	public void setOpcode(int opcode)
	{
		this.opcode = opcode;
	}

	public void setNop(boolean nop)
	{
		this.nop = nop;
	}

	public boolean isMA_enable() {
		return MA_enable;
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

	public boolean getNop()
	{
		return this.nop;
	}
}
