package ca.common;

public class CommonUtil
{
	
	public String nvl(Object obj, Object val)
	{
		String str = nvl(obj);
		if("".equals(str)) str = val.toString();
		
		return str;
	}
	public String nvl(Object obj)
	{
		String str = "";
		String str2 = "";
		if(obj!=null)
		{
			str2 = (String)obj;
			if(!"".equals(str2) && str2!=null)
				str = str2;
		}
		return str;
	}
	
	
	// 수치중 null인것 >> 0처리
	public String nvlZero(Object obj)
	{
		String str = "0";
		String str2 = "";
		if(obj!=null)
		{
			str2 = (String)obj;
			if(!"".equals(str2) && str2!=null)
				str = str2;
		}
		return str;
	}
}
