package ca.vo;

import java.util.Arrays;


public class MorphemeVo
{
	private String sentence = null;				//분석할 문장
	private String [] resultWord = null;		
	private String [] resultMorpheme = null;
	
	
	//생성자
	public MorphemeVo()
	{
		
	}
	public MorphemeVo(String sentence)
	{
		this.sentence = sentence;
	}

	
	
	//게터, 세터
	public String getSentence()
	{
		return sentence;
	}
	public void setSentence(String sentence)
	{
		this.sentence = sentence;
	}
	public String[] getResultWord()
	{
		return resultWord;
	}
	public void setResultWord(String[] resultWord)
	{
		this.resultWord = resultWord;
	}
	public String[] getResultMorpheme()
	{
		return resultMorpheme;
	}
	public void setResultMorpheme(String[] resultMorpheme)
	{
		this.resultMorpheme = resultMorpheme;
	}

	
}
