package processor.pipeline;

public class EX_IF_LatchType {
	
	boolean IS_enable;
	int PC;

	public EX_IF_LatchType()
	{
		IS_enable = false;
	}

	public void setPC(int pc) {
		PC = pc;
	}

	public void setIS_enable(boolean iS_enable) {
		IS_enable = iS_enable;
	}

	public boolean getIS_enable() {
		return IS_enable;
	}

	public int getPC() {
		return PC;
	}
}
