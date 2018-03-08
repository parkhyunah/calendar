package ca.query;

public class ScheduleQuery
{
	private String selectScheduleQuery = 	" with base as ( "
											+" select s.id id, "
											+" to_char(s.reg_dt, 'yyyymmddhh24miss') reg_dt, to_char(s.mod_dt, 'yyyymmddhh24miss') mod_dt, "
											+" to_char(s.start_dt, 'yyyymmddhh24miss') start_dt, to_char(s.end_dt, 'yyyymmddhh24miss') end_dt, "
											+" s.title, s.text, s.h_flag "
											+" from schedule s "
											+" where ? between to_char(start_dt, 'yyyymmdd') and to_char(end_dt, 'yyyymmdd') order by start_dt asc, id asc ) "
											+" select base.*, (select count(*) from base) totalcount from base ";
//	private String selectScheduleQuery = 	"with base as "
//											+"( "
//											+"select s.id id,   "
//											+"to_char(reg_dt, 'yyyymmddhh24miss') reg_dt, to_char(mod_dt, 'yyyymmddhh24miss') mod_dt, "
//											+"to_char(start_dt, 'yyyymmddhh24miss') start_dt, to_char(end_dt, 'yyyymmddhh24miss') end_dt, "
//											+"title, text, h_flag "
//											+"from schedule s "
//											+") "
//											+"select base.*, (select count(*) from base) totalcount from base ";
	private String insertScheduleQuery = 	"insert into schedule  "
											+"values('sd'||lpad(schedule_seq.nextval,8,0), sysdate, sysdate, "
											+"to_date(?, 'yyyymmddhh24miss'), to_date(?, 'yyyymmddhh24miss'), "
											+"?, ?, '0') ";
//	private String insertScheduleQuery = 	"insert into schedule  "
//											+"values('sd'||lpad(schedule_seq.nextval,8,0), sysdate, sysdate, "
//											+"to_date('18991230010000', 'yyyymmddhh24miss'), to_date('18991230010000', 'yyyymmddhh24miss'), "
//											+"'title', 'text', '1') ";
	
	
	private String updateScheduleQuery = 	"update schedule set mod_dt=sysdate,  "
											+"start_dt=to_date(?, 'yyyymmddhh24miss'),  "
											+"end_dt=to_date(?, 'yyyymmddhh24miss'), "
											+"title=?, text=? "
											+"where id=? ";
//	private String updateScheduleQuery = 	"update schedule set mod_dt=sysdate,  "
//											+"start_dt=to_date('18991230010000', 'yyyymmddhh24miss'),  "
//											+"end_dt=to_date('18991230010000', 'yyyymmddhh24miss'), "
//											+"title='2', text='wer', h_flag='2' "
//											+"where id='1' ";
	
	private String deleteScheduleQuery = "delete from schedule where id=?";

	private String countOneDaySchedule =	 "select count(*) count from schedule s "
											+"where ? between to_char(start_dt,'yyyymmdd') and to_char(end_dt,'yyyymmdd') ";
	
	
	public String getCountOneDaySchedule()
	{
		return countOneDaySchedule;
	}

	public String getSelectScheduleQuery()
	{
		return selectScheduleQuery;
	}

	public String getInsertScheduleQuery()
	{
		return insertScheduleQuery;
	}

	public String getUpdateScheduleQuery()
	{
		return updateScheduleQuery;
	}

	public String getDeleteScheduleQuery()
	{
		return deleteScheduleQuery;
	}
	
	
	
	
	
}
