package processor.pipeline;

public class EX_MA_LatchType {
	
	boolean MA_enable;
	int op2;
	int aluResult;
	int opcode;
	
	public EX_MA_LatchType()
	{
		MA_enable = false;
	}

	public void setOp2(int op2)
	{
		this.op2 = op2;
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

	public int getAluResult()
	{
		return aluResult;
	}

	public int getOpcode()
	{
		return opcode;
	}

	public void setMA_enable(boolean mA_enable) {
		MA_enable = mA_enable;
	}

}
