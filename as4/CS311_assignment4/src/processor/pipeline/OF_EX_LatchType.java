package processor.pipeline;

public class OF_EX_LatchType {
	
	boolean EX_enable;
	int rs1, rs2, rd;
	int imm;
	int opcode;
	boolean nop;
	
	public OF_EX_LatchType()
	{
		EX_enable = false;
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
	public boolean isEX_enable() {
		return EX_enable;
	}

	public void setEX_enable(boolean eX_enable) {
		EX_enable = eX_enable;
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

	public void setImm(int imm)
	{
		this.imm = imm;
	}

	public void setOpcode(int opcode)
	{
		this.opcode = opcode;
	}

	public void setNop(boolean nop)
	{
		this.nop = nop;
	}

	public int getImm()
	{
		return this.imm;
	}

	public int getOpcode()
	{
		return this.opcode;
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
		return nop;
	}
}
