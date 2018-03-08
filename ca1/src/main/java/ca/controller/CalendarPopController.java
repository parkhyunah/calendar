package ca.controller;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ca.dao.ScheduleDao;
import ca.vo.ScheduleVo;

@Controller
public class CalendarPopController
{
	@RequestMapping("/calendarPop.do")
	public ModelAndView getList(HttpServletRequest request)
	{
		String date = request.getParameter("date");
		System.out.println(date);
		ModelAndView mav = new ModelAndView("calendarPop");	//여기를 템플릿으로 바꿔야 함~
		Connection con = null;
		try
		{
			InitialContext initial = new InitialContext();
			DataSource ds = (DataSource)initial.lookup("java:comp/env/jdbc/daegudb");
//			
			con = ds.getConnection();
			
			ScheduleDao dateDao = new ScheduleDao(con);
			ArrayList<ScheduleVo> dateList = dateDao.getScheduleList(date);
			mav.addObject("dateList", dateList);
			System.out.println("size: "+dateList.size());

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
	
	@RequestMapping(value="/calendarPop/regist.do")
	public ModelAndView registSchedule(@RequestParam(value = "insertStartDay", required = false) String start_dt, 
			@RequestParam(value = "insertEndDay", required = false ) String end_dt, 
			@RequestParam(value = "insertTitle", required = false) String title, 
			@RequestParam(value = "insertText", required = false) String text, 
			@RequestParam(value = "date", required = false) String date,
			HttpServletRequest request)
	{
		System.out.println(date);
		ModelAndView mav = new ModelAndView("calendarPop");	//여기를 템플릿으로 바꿔야 함~
		Connection con = null;
		try
		{
			InitialContext initial = new InitialContext();
			DataSource ds = (DataSource)initial.lookup("java:comp/env/jdbc/daegudb");
//			
			con = ds.getConnection();
			

			ScheduleDao dateDao = new ScheduleDao(con);
			dateDao.insertSchedule(start_dt, end_dt, title, text);
			
			ArrayList<ScheduleVo> dateList = dateDao.getScheduleList(date);
			mav.addObject("dateList", dateList);
			request.setAttribute("date", date);
			System.out.println("size: "+dateList.size());

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
	
	
	
	@RequestMapping(value="/calendarPop/delete.do")
	public ModelAndView deleteSchedule(@RequestParam(value = "delId", required = false) String delId, 
			@RequestParam(value = "date", required = false) String date,
			HttpServletRequest request)
	{

		
		System.out.println(delId);
		ModelAndView mav = new ModelAndView("calendarPop");	
		Connection con = null;
		try
		{
			InitialContext initial = new InitialContext();
			DataSource ds = (DataSource)initial.lookup("java:comp/env/jdbc/daegudb");

			con = ds.getConnection();
			

			ScheduleDao dateDao = new ScheduleDao(con);
			dateDao.deleteSchedule(delId);
			
			ArrayList<ScheduleVo> dateList = dateDao.getScheduleList(date);
			mav.addObject("dateList", dateList);
			request.setAttribute("date", date);
			System.out.println("size: "+dateList.size());

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
	
	@RequestMapping(value="/calendarPop/modify.do")
	public ModelAndView modifySchedule(@RequestParam(value = "date", required = false) String date,
			@RequestParam(value = "delId", required = false) String delId,
			@RequestParam(value = "modTitle", required = false) String modTitle,
			@RequestParam(value = "modText", required = false) String modText,
			@RequestParam(value = "modStartDay", required = false) String modStartDay,
			@RequestParam(value = "modEndDay", required = false) String modEndDay,
			HttpServletRequest request)
	{

		
		System.out.println("delId: "+delId+", modTitle: "+modTitle+", modText: "+modText+", modStartDay: "+modStartDay+", modEndDay: "+modEndDay);
		ModelAndView mav = new ModelAndView("calendarPop");	
		Connection con = null;
		try
		{
			InitialContext initial = new InitialContext();
			DataSource ds = (DataSource)initial.lookup("java:comp/env/jdbc/daegudb");

			con = ds.getConnection();
			

			ScheduleDao dateDao = new ScheduleDao(con);
			
			//dateDao.deleteSchedule(delId);
			dateDao.modifySchedule(delId, modTitle, modText, modStartDay, modEndDay);
			
			ArrayList<ScheduleVo> dateList = dateDao.getScheduleList(date);
			mav.addObject("dateList", dateList);
			request.setAttribute("date", date);
			System.out.println("size: "+dateList.size());

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
