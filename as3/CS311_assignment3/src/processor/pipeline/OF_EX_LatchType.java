package processor.pipeline;

public class OF_EX_LatchType {
	
	boolean EX_enable;
	int branchTarget;
	int op1;
	int op2;
	int imm;
	
	public OF_EX_LatchType()
	{
		EX_enable = false;
	}

	public boolean isEX_enable() {
		return EX_enable;
	}

	public void setEX_enable(boolean eX_enable) {
		EX_enable = eX_enable;
	}

	public void setBranchTarget(int target)
	{
		branchTarget = target;
	}

	public void setOp1(int op1)
	{
		this.op1 = op1;
	}

	public void setOp2(int op2)
	{
		this.op2 = op2;
	}

	public void setImm(int imm)
	{
		this.imm = imm;
	}

	public int getBranchTarget()
	{
		return branchTarget;
	}

	public int getOp1()
	{
		return this.op1;
	}

	public int getOp2()
	{
		return this.op2;
	}

	public int getImm()
	{
		return this.imm;
	}

}
