package ca.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

import ca.common.CommonUtil;
import ca.query.ScheduleQuery;
import ca.vo.ScheduleVo;

//--조회 쿼리
//select s.id id,  
//to_char(reg_dt, 'yyyymmddhh24miss') reg_dt, to_char(mod_dt, 'yyyymmddhh24miss') mod_dt,
//to_char(start_dt, 'yyyymmddhh24miss') start_dt, to_char(end_dt, 'yyyymmddhh24miss') end_dt,
//title, text, h_flag
//from schedule s;
//
//
//--삽입 쿼리
//insert into schedule 
//values('sd'||lpad(schedule_seq.nextval,8,0), sysdate, sysdate, to_date('18991230010000', 'yyyymmddhh24miss'), to_date('18991230010000', 'yyyymmddhh24miss'), '1', '1', '1');
//
//
//--수정 쿼리
//update schedule set mod_dt=sysdate, start_dt=to_date('18991230010000', 'yyyymmddhh24miss'), end_dt=to_date('18991230010000', 'yyyymmddhh24miss'),
//title='2', text='wer', h_flag='2'
//where id='1'


public class ScheduleDao
{

	static Connection con = null;
	static Statement stmt = null;
	static PreparedStatement pstmt = null;
	static ResultSet rs = null;

	public ScheduleDao(Connection con)
	{
		this.con = con;
	}
	
	//productId를 이용하여 프로덕트 리스트 조회
	public ArrayList<ScheduleVo> getScheduleList(String date)
	{
		CommonUtil commonUtil = new CommonUtil();
		ArrayList<ScheduleVo> list = new ArrayList<ScheduleVo>();
		try
		{	
			ScheduleQuery query = new ScheduleQuery();
			pstmt = con.prepareStatement(query.getSelectScheduleQuery());
			pstmt.setString(1, date);
			rs = pstmt.executeQuery();
			
			// 쿼리
			while (rs.next())
			{
				ScheduleVo vo = new ScheduleVo();
				vo.setID(commonUtil.nvl(rs.getString("id")));
				vo.setREG_DT(commonUtil.nvl(rs.getString("reg_dt")));
				vo.setMOD_DT(commonUtil.nvl(rs.getString("mod_dt")));
				vo.setSTART_DT(commonUtil.nvl(rs.getString("start_dt")));
				vo.setEND_DT(commonUtil.nvl(rs.getString("end_dt")));
				vo.setTITLE(commonUtil.nvl(rs.getString("title")));
				vo.setTEXT(commonUtil.nvl(rs.getString("text")));
				vo.setH_FLAG(commonUtil.nvl(rs.getString("h_flag")));
				vo.setTotalCount(commonUtil.nvl(rs.getString("totalCount")));
				list.add(vo);
				System.out.println(vo.toString());
			}

		} 
		catch (Exception e)
		{
			e.printStackTrace();
		} 
		finally
		{
			try { if(rs!=null)rs.close(); } catch (Exception e) { e.printStackTrace();}
			try { if(stmt!=null)stmt.close(); } catch (Exception e) { e.printStackTrace();}
			try { if(pstmt!=null)pstmt.close(); } catch (Exception e) { e.printStackTrace();}
		}
		return list;
	}
	
	public ArrayList<String> getCalendarList(String year, String month)
	{
		int START_DAY_OF_WEEK = 0;
		int END_DAY = 0;
		
		Calendar startDay = Calendar.getInstance();
		Calendar endDay = Calendar.getInstance();
		Calendar today = Calendar.getInstance();
		
		startDay.set(Calendar.YEAR, Integer.parseInt(year));
		startDay.set(Calendar.MONTH, Integer.parseInt(month)-1);
		startDay.set(Calendar.DATE, 1);
		
		endDay.set(Calendar.YEAR, Integer.parseInt(year));
		endDay.set(Calendar.MONTH, Integer.parseInt(month)-1);
		endDay.set(Calendar.DATE, 1);
		
		
		
		
		endDay.add(Calendar.MONTH, +1);
		endDay.add(Calendar.DATE, -1);
		
		
		System.out.println(""+startDay.get(Calendar.YEAR)+(startDay.get(Calendar.MONTH)+1));
		System.out.println(""+endDay.get(Calendar.YEAR)+(endDay.get(Calendar.MONTH)+1)+endDay.get(Calendar.DATE));
		int tmpMonth = (startDay.get(Calendar.MONTH)+1);
		String yyyymm = ""+startDay.get(Calendar.YEAR)+((tmpMonth<10)?"0"+tmpMonth : ""+tmpMonth);
		
		
		START_DAY_OF_WEEK = startDay.get(Calendar.DAY_OF_WEEK);
		END_DAY = endDay.get(Calendar.DATE);
		
		ArrayList<String> calList = new ArrayList<String>();
		
		for(int i=1 ; i<START_DAY_OF_WEEK ; i++)
		{
			calList.add("");
		}
		for(int i=1, n=START_DAY_OF_WEEK ; i<=END_DAY ; i++, n++)
		{
			calList.add(yyyymm+((i<10)? "0"+i : ""+i));
		}
		
//		for(int i=1 ; i<=calList.size() ; i++)
//		{
//			System.out.print(" --"+calList.get(i-1)+"--  ");
//			if(i%7==0)
//				System.out.println();
//		}
		
		return calList;
	}
	
	
	public void insertSchedule(String start_dt, String end_dt, String title, String text)
	{
		System.out.println("start_dt: "+start_dt+", end_dt: "+end_dt+", title: "+title+", text: "+text);

		
		try
		{	
			ScheduleQuery query = new ScheduleQuery();
			pstmt = con.prepareStatement(query.getInsertScheduleQuery());
			pstmt.setString(1, start_dt);
			pstmt.setString(2, end_dt);
			pstmt.setString(3, title);
			pstmt.setString(4, text);
			pstmt.executeQuery();
			System.out.println("일정 '"+title+"' 삽입 완료");

		} 
		catch (Exception e)
		{
			e.printStackTrace();
		} 
		finally
		{
			try { if(rs!=null)rs.close(); } catch (Exception e) { e.printStackTrace();}
			try { if(stmt!=null)stmt.close(); } catch (Exception e) { e.printStackTrace();}
			try { if(pstmt!=null)pstmt.close(); } catch (Exception e) { e.printStackTrace();}
		}
	}
	
	public void deleteSchedule(String delId)
	{
		System.out.println("delId: "+delId);

		
		try
		{	
			ScheduleQuery query = new ScheduleQuery();
			pstmt = con.prepareStatement(query.getDeleteScheduleQuery());
			pstmt.setString(1, delId);

			pstmt.executeQuery();
			System.out.println("일정 id="+delId+" 삭제 완료");

		} 
		catch (Exception e)
		{
			e.printStackTrace();
		} 
		finally
		{
			try { if(rs!=null)rs.close(); } catch (Exception e) { e.printStackTrace();}
			try { if(stmt!=null)stmt.close(); } catch (Exception e) { e.printStackTrace();}
			try { if(pstmt!=null)pstmt.close(); } catch (Exception e) { e.printStackTrace();}
		}
	}

	public int countOneDaySchedule(String date)
	{
		
		System.out.println("date: "+date);

		int resultCount = 0;
		try
		{	
			ScheduleQuery query = new ScheduleQuery();
			pstmt = con.prepareStatement(query.getCountOneDaySchedule());
			pstmt.setString(1, date);

			rs = pstmt.executeQuery();
			rs.next();
			resultCount = rs.getInt("count");
			//System.out.println(date+"의 일정 갯수는"+resultCount+"개");

		} 
		catch (Exception e)
		{
			e.printStackTrace();
		} 
		finally
		{
			try { if(rs!=null)rs.close(); } catch (Exception e) { e.printStackTrace();}
			try { if(stmt!=null)stmt.close(); } catch (Exception e) { e.printStackTrace();}
			try { if(pstmt!=null)pstmt.close(); } catch (Exception e) { e.printStackTrace();}
		}
		return resultCount;
	}

	public void modifySchedule(String delId, String modTitle, String modText, String modStartDay, String modEndDay)
	{

		try
		{	
			ScheduleQuery query = new ScheduleQuery();
			pstmt = con.prepareStatement(query.getUpdateScheduleQuery());
			pstmt.setString(1, modStartDay);
			pstmt.setString(2, modEndDay);
			pstmt.setString(3, modTitle);
			pstmt.setString(4, modText);
			pstmt.setString(5, delId.trim());

			pstmt.executeQuery();
			System.out.println("일정 id="+delId+" 수정 완료");

		} 
		catch (Exception e)
		{
			e.printStackTrace();
		} 
		finally
		{
			try { if(rs!=null)rs.close(); } catch (Exception e) { e.printStackTrace();}
			try { if(stmt!=null)stmt.close(); } catch (Exception e) { e.printStackTrace();}
			try { if(pstmt!=null)pstmt.close(); } catch (Exception e) { e.printStackTrace();}
		}
		
	}
	
	
}
