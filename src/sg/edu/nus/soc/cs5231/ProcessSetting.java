package sg.edu.nus.soc.cs5231;

public class ProcessSetting {
	private long id = 0;
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public boolean isEnableLogging() {
		return enableLogging;
	}

	public void setEnableLogging(boolean enableLogging) {
		this.enableLogging = enableLogging;
	}

	private String processName = "";
	private boolean enableLogging = false;
	
	// Will be used by the ArrayAdapter in the ListView
	@Override
	public String toString()
	{
		return processName+"("+enableLogging+")";
	}

}
