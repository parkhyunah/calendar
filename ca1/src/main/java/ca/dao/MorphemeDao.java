package ca.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ca.vo.MorphemeVo;



public class MorphemeDao
{
	public static MorphemeVo getMorpheme(String sentence)
	{
		MorphemeVo vo = new MorphemeVo(sentence);
		
		fileCreate(sentence);
		executeKOMA();
		morphemeProcess(vo);
		System.out.println("형태소 분석 완료");
		return vo;
	}
	
	//입력 : 문자열
	//출력 : 0, -1 파일 생성 성공 여부에 따른 값.
	//기능 : 파일 생성, 단순히 형태소 분석을 위해 문자열을 파일로 저장하는 기능의 메소드
	public static int fileCreate(String sentence){
		int resultCode = 0;
		try {
			BufferedWriter bw = new BufferedWriter( new OutputStreamWriter(new FileOutputStream("D:\\new\\Project\\ca\\src\\main\\webapp\\resources\\KOMAINPUT.txt"),"euc-kr" ));
			bw.write(sentence);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
			resultCode = -1;
		}
		return resultCode;
	}
	
	//입력 : -
	//출력 : 0, -1 파일 생성 성공 여부에 따른 값.
	//기능 : KOMAINPUT.txt 파일 형태소 분석 후 KOMAOUTPUT.txt 결과 파일 생성
	private static int executeKOMA(){
		int resultCode = 0;
		try{
			String instructionString = "KoMA -t -s -i KOMAINPUT.txt -o KOMAOUTPUT.txt";
			Process pr = new ProcessBuilder("cmd", "/c", instructionString).start();
			pr.waitFor();
		}catch(Exception e){
			resultCode = -1;
		}
		return resultCode;
	}
	
	//입력 : -
	//출력 : 0, -1 파일 생성 성공 여부에 따른 값.
	//기능 : KoMA의 출력 파일을 필요한 부분만 가져오기 위한 처리과정
	public static int morphemeProcess(MorphemeVo vo){
		int resultCode = 0;
		BufferedReader br = null;
		String line = null;
		ArrayList<String> wordList = new ArrayList<String>();
		ArrayList<String> morphemeList = new ArrayList<String>();
		
		
		//: 안녕(CMCPS) + 하(fph) + 시(fmbr) + 어(fmofd) + 요(fjb) 와 같은 문장만 빼기위한 정규식
		Pattern patternMorphemes  =  Pattern.compile(": .+");
		Matcher matchMorphemes = null;
		
		Pattern patternMorpheme  =  Pattern.compile("\\(.+\\)");
		Matcher matchMorpheme = null;
		
		
		
		String stringMorphemes = null;
		String [] morphemeWords = null;
		String tag = null;
		String morpheme = null;
		
		try
		{
			br = new BufferedReader( new InputStreamReader(new FileInputStream("D:\\new\\Project\\ca\\src\\main\\webapp\\resources\\KOMAOUTPUT.txt"),"euc-kr"));
			line = br.readLine();
			while(line != null)
			{
				matchMorphemes = patternMorphemes.matcher(line);
				if(matchMorphemes.find()){
					stringMorphemes = matchMorphemes.group(0).trim().substring(2);
					morphemeWords = stringMorphemes.split(" \\+ ");
					for(String s : morphemeWords)
					{
						matchMorpheme = patternMorpheme.matcher(s);
						if(matchMorpheme.find())
						{
							morpheme = s.replaceAll("\\(.+\\)", "");
							tag = matchMorpheme.group(0).trim();
							tag = tag.substring(1, tag.length()-1);
							//System.out.println(morpheme +" == "+ tag);
							
							wordList.add(morpheme);
							morphemeList.add(tag);
						}
					}
				}
				line = br.readLine();
			}//end while
			int listSize = 0;
			listSize = wordList.size();
			String[] wordArray = new String[listSize];
			String[] morphemeArray = new String[listSize];
			for(int i=0 ; i<listSize ; i++)
			{
				wordArray[i] = wordList.get(i);
				morphemeArray[i] = morphemeList.get(i);
			}
			vo.setResultWord(wordArray);
			vo.setResultMorpheme(morphemeArray);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try{ if(br!=null) br.close(); } catch (IOException e) { e.printStackTrace(); }
		}
		return resultCode;
	}
	
}
