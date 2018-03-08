package ca.controller;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ca.common.CommonUtil;
import ca.dao.DateDao;
import ca.dao.MorphemeDao;
import ca.dao.ScheduleDao;
import ca.vo.DateVo;
import ca.vo.MorphemeVo;


@Controller
public class CalendarController
{
	
	@RequestMapping("/sykim.do")
	public ModelAndView sykim(HttpServletRequest request)
	{
		
		ModelAndView mav = new ModelAndView("sykim");
		return mav;
	}
	
	@RequestMapping(value="/month.do", method=RequestMethod.POST)
	public ModelAndView getList(@RequestParam(value = "year", required = false) String year, 
			@RequestParam(value = "month", required = false) String month, 
			@RequestParam(value = "date", required = false) String date, 
			@RequestParam(value = "direction", required = false) String direction, 
			@RequestParam(value = "nlpText", required = true) String nlpText, 
			HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView("calendar");
		CommonUtil util = new CommonUtil();


		
		Connection con = null;
		try
		{
			
			InitialContext initial = new InitialContext();
			DataSource ds = (DataSource)initial.lookup("java:comp/env/jdbc/daegudb");
			
			con = ds.getConnection();
			
			
			//XXX 여기서 자연어 처리 모듈 연결시켜주는 부분이 필요함~
			
			//nlpText가 자연어 처리 대상 문장임.
			MorphemeVo mvo = MorphemeDao.getMorpheme(nlpText);
			DateVo dvo = new DateVo();
			String operation = DateDao.dataExtract(mvo, dvo);
			System.out.println(dvo.toString());

			//FIXME  Morpheme Analysis Parts End.
			
			System.out.println("input - nlpText: "+nlpText+", input - year: "+year+", month: "+month+", date: "+date+", direction: "+direction+"");
			String resultYear = null, resultMonth = null, resultDate = null;
			if(year==null && month==null && date==null)
			{
				Calendar cal = Calendar.getInstance();
				resultYear = ""+cal.get(Calendar.YEAR);
				int tmpMonth = (cal.get(Calendar.MONTH)+1);
				resultMonth = ""+((tmpMonth<10)?"0"+tmpMonth : ""+tmpMonth);
				resultDate = ""+cal.get(Calendar.DATE);
			}
			else
			{
				Calendar cal = Calendar.getInstance();
				if((!"".equals(year)) &&(!"".equals(month)))
					cal.set(Integer.parseInt(year), Integer.parseInt(month)-1, 1);
				
				if(direction.equals("next"))
				{
					cal.add(Calendar.MONTH, +1);
				}
				else if(direction.equals("prev"))
				{
					cal.add(Calendar.MONTH, -1);
				}
				
				resultYear = ""+cal.get(Calendar.YEAR);
				int tmpMonth = (cal.get(Calendar.MONTH)+1);
				resultMonth = ""+((tmpMonth<10)?"0"+(tmpMonth) : ""+(tmpMonth));
				resultDate = ""+cal.get(Calendar.DATE);
			}

			System.out.println("output - year: "+resultYear+", month: "+resultMonth+", date: "+resultDate+", direction: "+direction+"");
			request.setAttribute("year", resultYear);
			request.setAttribute("month", resultMonth);
			request.setAttribute("date", resultDate);
			
			
			
			if(operation.equals("insert"))
			{
				ScheduleDao dateDao = new ScheduleDao(con);
				dateDao.insertSchedule(dvo.getStartSchedule(), dvo.getEndSchedule(), dvo.getTitle(), dvo.getText());
			}
			
			ScheduleDao sdao = new ScheduleDao(con);
			ArrayList<String> calList = sdao.getCalendarList(resultYear, resultMonth);
			ArrayList<Integer> calCountList = new ArrayList<Integer>();
			int calListSize = calList.size();
			for(int i=0 ; i<calListSize ; i++)
			{
				String tmpDate = calList.get(i);
				if("".equals(tmpDate))
				{
					calCountList.add(Integer.valueOf(0));
				}
				else
				{
					int count = sdao.countOneDaySchedule(calList.get((i)));
					calCountList.add(Integer.valueOf(count));
				}
			}
			
			mav.addObject("calList", calList);
			mav.addObject("calCountList", calCountList);

			request.setAttribute("calList", calList);
			request.setAttribute("calCountList", calCountList);

			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try{if(con!=null)con.close();}catch(Exception e){}
		}
		return mav;
	}
	
	
	@RequestMapping(value="/month.do", method=RequestMethod.GET)
	public ModelAndView getList(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView("calendar");
		CommonUtil util = new CommonUtil();
		
		String year=null, month=null, date=null;
		
		System.out.println("input - year: "+year+", month: "+month+", date: "+date);

		Calendar cal = Calendar.getInstance();
		
		
		year = ""+cal.get(Calendar.YEAR);
		int tmpMonth = (cal.get(Calendar.MONTH)+1);
		month = ""+((tmpMonth<10)?"0"+(tmpMonth) : ""+(tmpMonth));
		date = ""+cal.get(Calendar.DATE);
		

		System.out.println("output - year: "+year+", month: "+month+", date: "+date);
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		request.setAttribute("date", date);
		
		Connection con = null;
		try
		{
			
			InitialContext initial = new InitialContext();
			DataSource ds = (DataSource)initial.lookup("java:comp/env/jdbc/daegudb");
			
			con = ds.getConnection();
			
			ScheduleDao sdao = new ScheduleDao(con);
			ArrayList<String> calList = sdao.getCalendarList(year, month);
			ArrayList<Integer> calCountList = new ArrayList<Integer>();
			int calListSize = calList.size();
			for(int i=0 ; i<calListSize ; i++)
			{
				String tmpDate = calList.get(i);
				if("".equals(tmpDate))
				{
					calCountList.add(Integer.valueOf(0));
				}
				else
				{
					int count = sdao.countOneDaySchedule(calList.get((i)));
					calCountList.add(Integer.valueOf(count));
				}
			}
			
			mav.addObject("calList", calList);
			mav.addObject("calCountList", calCountList);

			request.setAttribute("calList", calList);
			request.setAttribute("calCountList", calCountList);
		
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try{if(con!=null)con.close();}catch(Exception e){}
		}
		
		return mav;
	}
	
	
	
	
}