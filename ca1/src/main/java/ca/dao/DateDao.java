package ca.dao;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ca.vo.DateVo;
import ca.vo.MorphemeVo;



public class DateDao
{
	public static String dataExtract(MorphemeVo mvo, DateVo dvo)
	{
		String sentence = mvo.getSentence();
		
		String title = titleExtract(sentence);
		dvo.setTitle(title);
		
		String operation = operationExtract(mvo);
		System.out.println("operation : "+operation);
		
		System.out.println(timeExtract(dvo, sentence));
		
		return operation;
	}
	
	public static String timeExtract(DateVo dvo, String sentence)
	{

		Calendar cal = Calendar.getInstance();
		String hour = "09";
		String minute = "00";
		String second = "00";
		System.out.println("today: "+toCharCal(toString(cal)));
		
		Pattern patternAbsoluteTime = Pattern.compile("([2-9]{1}[0-9]{3}\\s{0,1}년){0,1}\\s{0,1}([0-9]{1,2}\\s{0,1}월){0,1}\\s{0,1}([0-9]{1,2}일)");
		Matcher matchAbsoluteTime = patternAbsoluteTime.matcher(sentence);
		
		if(matchAbsoluteTime.find())
		{
			Pattern pYear = Pattern.compile("[2-9]{1}[0-9]{3}\\s{0,1}년");
			Pattern pMonth = Pattern.compile("[0-9]{1,2}\\s{0,1}월");
			Pattern pDate = Pattern.compile("[0-9]{1,2}일");
			
			Matcher mYear = pYear.matcher(matchAbsoluteTime.group(0).trim());
			Matcher mMonth = pMonth.matcher(matchAbsoluteTime.group(0).trim());
			Matcher mDate = pDate.matcher(matchAbsoluteTime.group(0).trim());
			
			if(mYear.find())
			{
				String tmpYear = mYear.group(0).trim().replace("년", "");
				try{cal.set(Calendar.YEAR, Integer.parseInt(tmpYear));}catch(Exception e){e.printStackTrace();}
			}
			if(mMonth.find())
			{
				String tmpMonth = mMonth.group(0).trim().replace("월", "");
				try{cal.set(Calendar.MONTH, Integer.parseInt(tmpMonth)-1);}catch(Exception e){e.printStackTrace();}
			}
			if(mDate.find())
			{
				String tmpDate = mDate.group(0).trim().replace("일", "");
				try{cal.set(Calendar.DATE, Integer.parseInt(tmpDate));}catch(Exception e){e.printStackTrace();}
			}
		}
		
		Pattern patternDetail = Pattern.compile("([1-2]{0,1}[0-9]{1}시){0,1}\\s{0,1}([1-5]{0,1}[0-9]{1}분)");
		Matcher matchDetail = patternDetail.matcher(sentence);
		
		if(matchDetail.find())
		{
			Pattern pHour = Pattern.compile("([1-2]{0,1}[0-9]{1}시)");
			Pattern pMinute = Pattern.compile("([1-5]{0,1}[0-9]{1}분)");
			
			Matcher mHour = pHour.matcher(matchDetail.group(0).trim());
			Matcher mMinute = pMinute.matcher(matchDetail.group(0).trim());
			
			if(mHour.find())
			{
				String tmpHour = mHour.group(0).trim().replace("시", "");
				System.out.println("tmpHour: "+tmpHour);
				if(Integer.parseInt(tmpHour)<10)
				{
					tmpHour = "0"+tmpHour;
				}
				System.out.println("tmpHour: "+tmpHour);
				try{hour = tmpHour;}catch(Exception e){e.printStackTrace();}
			}
			if(mMinute.find())
			{
				String tmpMinute = mMinute.group(0).trim().replace("분", "");
				if(Integer.parseInt(tmpMinute)<10)
				{
					tmpMinute = "0"+tmpMinute;
				}
				System.out.println("tmpMinute: "+tmpMinute);
				try{minute = tmpMinute;}catch(Exception e){e.printStackTrace();}
			}

		}
		
		if(sentence.contains("내일"))
		{
			cal.add(Calendar.DATE, 1);
		}
		if(sentence.contains("모레"))
		{
			cal.add(Calendar.DATE, 2);
		}

		
		String resultTime = toCharCal(toString(cal))+hour+minute+second;
		dvo.setStartSchedule(resultTime);
		dvo.setEndSchedule(resultTime);
		
		return null;
	}
	
	
	
	public static String toCharCal(String date)
	{
		String[] splitedDate = date.split("\\.");
		if(Integer.parseInt(splitedDate[1])<10)
		{
			splitedDate[1] = "0"+splitedDate[1];
		}
		if(Integer.parseInt(splitedDate[2])<10)
		{
			splitedDate[2] = "0"+splitedDate[2];
		}
		
		return splitedDate[0]+splitedDate[1]+splitedDate[2];
	}
	
	public static String today()
	{
		Calendar cal = Calendar.getInstance();
		return ""+cal.get(Calendar.YEAR)+"."+(cal.get(Calendar.MONTH)+1)+"."+cal.get(Calendar.DATE);
	}
	
	public static String toString(Calendar date)
	{
		return ""+date.get(Calendar.YEAR)+"."+(date.get(Calendar.MONTH)+1)+"."+date.get(Calendar.DATE);
	}
	//연산 추출
	public static String operationExtract(MorphemeVo mvo)
	{
		String operation = null;
		String [] words = mvo.getResultWord();
		String [] morphemes = mvo.getResultMorpheme();
		
		int size = words.length;
		
		for(int i=0 ; i<size ; i++)
		{
			if(		(words[i].equals("추가") && morphemes[i].equals("CMCPA")) 
				|| 	(words[i].equals("입력") && morphemes[i].equals("CMCPA"))
				||	(words[i].equals("생성") && morphemes[i].equals("CMCPA")) )
			{
				operation = "insert";
			}
			else if(	(words[i].equals("수정") && morphemes[i].equals("CMCPA")) 
					|| 	(words[i].equals("정정") && morphemes[i].equals("CMCPA"))
					||	(words[i].equals("고치") && morphemes[i].equals("YBDO")) )
			{
				operation = "modify";
			}
			else if(	(words[i].equals("지우") && morphemes[i].equals("YBDO")) 
					|| 	(words[i].equals("삭제") && morphemes[i].equals("CMCPA"))
					||	(words[i].equals("취소") && morphemes[i].equals("CMCPA")) )
			{
				operation = "delete";
			}
			else if(	(words[i].equals("조회") && morphemes[i].equals("CMCPA")) 
					|| 	(words[i].equals("알리") && morphemes[i].equals("YBDO"))
					||	(words[i].equals("있") && morphemes[i].equals("YBDO")) )
			{
				operation = "inquire";
			}
		}
		return operation;
	}
	
	//타이틀 추출
	public static String titleExtract(String sentence)
	{
		String title = null;
		Pattern patternTitle =  Pattern.compile("('.+')|(\".+\")");
		Matcher matchTitle = null;
		matchTitle = patternTitle.matcher(sentence);
		
		if(matchTitle.find())
		{
			title = matchTitle.group(0).trim();
			title = title.substring(1, title.length()-1);
		}
		
		return title;
	}
	
	
}
