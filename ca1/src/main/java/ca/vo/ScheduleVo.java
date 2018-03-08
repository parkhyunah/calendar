package ca.vo;

public class ScheduleVo
{
	private String ID = null;
	private String REG_DT = null;
	private String MOD_DT = null;
	private String START_DT = null;
	private String END_DT = null;
	private String TITLE = null;
	private String TEXT = null;
	private String H_FLAG = null;
	private String totalCount = null;
	
	
	
	@Override
	public String toString()
	{
		return "DateVo [ID=" + ID + ", REG_DT=" + REG_DT + ", MOD_DT=" + MOD_DT + ", START_DT=" + START_DT + ", END_DT=" + END_DT + ", TITLE=" + TITLE + ", TEXT=" + TEXT + ", H_FLAG=" + H_FLAG + ", totalCount=" + totalCount + "]";
	}
	
	
	public String getID()
	{
		return ID;
	}
	public void setID(String iD)
	{
		ID = iD;
	}
	public String getREG_DT()
	{
		return REG_DT;
	}
	public void setREG_DT(String rEG_DT)
	{
		REG_DT = rEG_DT;
	}
	public String getMOD_DT()
	{
		return MOD_DT;
	}
	public void setMOD_DT(String mOD_DT)
	{
		MOD_DT = mOD_DT;
	}
	public String getSTART_DT()
	{
		return START_DT;
	}
	public void setSTART_DT(String sTART_DT)
	{
		START_DT = sTART_DT;
	}
	public String getEND_DT()
	{
		return END_DT;
	}
	public void setEND_DT(String eND_DT)
	{
		END_DT = eND_DT;
	}
	public String getTITLE()
	{
		return TITLE;
	}
	public void setTITLE(String tITLE)
	{
		TITLE = tITLE;
	}
	public String getTEXT()
	{
		return TEXT;
	}
	public void setTEXT(String tEXT)
	{
		TEXT = tEXT;
	}
	public String getH_FLAG()
	{
		return H_FLAG;
	}
	public void setH_FLAG(String h_FLAG)
	{
		H_FLAG = h_FLAG;
	}
	public String getTotalCount()
	{
		return totalCount;
	}
	public void setTotalCount(String totalCount)
	{
		this.totalCount = totalCount;
	}
	
	
}
