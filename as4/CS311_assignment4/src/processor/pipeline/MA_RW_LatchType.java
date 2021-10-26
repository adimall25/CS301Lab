package processor.pipeline;

public class MA_RW_LatchType {
	
	boolean RW_enable;
	int rs1, rs2, rd;
	int aluResult;
	int opcode;
	boolean nop;
	int imm;

	int loadResult;
	
	public MA_RW_LatchType()
	{
		RW_enable = false;
		nop = false;
		rs1 = -1;
		rs2 = -1;
		rd = -1;
		opcode = -1;
	}
	public void setImm(int imm)
	{
		this.imm = imm;
	}
	public void setNull()
	{
		rs1 = -1;
		rs2 = -1;
		rd = -1;
		opcode = -1;
	}
	public int getImm()
	{
		return this.imm;
	}
	

	public boolean isRW_enable() {
		return RW_enable;
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

	public void setLoadResult(int loadResult)
	{
		this.loadResult = loadResult;
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

	public boolean getNop()
	{
		return this.nop;
	}

	public void setRW_enable(boolean rW_enable) {
		RW_enable = rW_enable;
	}

	public int getLoadResult()
	{
		return this.loadResult;
	}

}
