package ca.vo;

public class DateVo
{
	private String title = null;
	private String text = null;
	private String startSchedule = null;
	private String endSchedule = null;

	public DateVo()
	{
		this.title = "";
		this.text = "";
	}
	
	
	@Override
	public String toString()
	{
		return "DateVo [title=" + title + ", text=" + text + ", startSchedule="
				+ startSchedule + ", endSchedule=" + endSchedule + "]";
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public String getStartSchedule()
	{
		return startSchedule;
	}

	public void setStartSchedule(String startSchedule)
	{
		this.startSchedule = startSchedule;
	}

	public String getEndSchedule()
	{
		return endSchedule;
	}

	public void setEndSchedule(String endSchedule)
	{
		this.endSchedule = endSchedule;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}
}
