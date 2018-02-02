package com.zc.util;
import java.util.Date;
import java.util.Random;

public class RandomUtils {
	private static Random rand=new Random();
	private static char[] randSeed={'a','b','c','d','e','f','g','h','i','j','k','l','m','n'
		,'o','p','q','r','s','t','u','v','w','x','y','z'};
	/**
	 * @param start 起始日期
	 * @param end 截止日期
	 * @return 随机生成的年份
	 */
	public static int randYear(int start,int end){
		return rand.nextInt(end-start)+start;
	}
	/**
	 * @return 使用默认的年份范围返回随机生成的年份
	 */
	public static int randYear(){
		int start=1980,end=2010;
		return randYear(start, end);
	}
	
	
	/**
	 * @return 返回随机月份
	 */
	public static int randMonth(){
		return rand.nextInt(12)+1;
	}
	
	/**
	 * @return 返回随机天,不精准
	 */
	public static int randDay(){
		return rand.nextInt(30)+1;
	}
	
	/**
	 * @return 返回随机生成的String日期
	 */
	public static String randDateStr(){
		return new StringBuilder().append(String.valueOf(randYear())).append("-")
		.append(String.valueOf(randMonth())).append("-")
		.append(String.valueOf(randDay())).toString();
	}
	
	public static Date randDate(){
		return new Date();
	}
	
	/**
	 * @return	随机生成的性别
	 */
	public static String randSex(){
		String[] sexs={"男","女"};
		return sexs[rand.nextInt(2)];
	}
	
	/**
	 * @return	随机生成的年龄
	 */
	public static int randAge(){
		return rand.nextInt(50)+20;
	}
	
	/**
	 * @return	随机生成的工资
	 */
	public static int randSalary(){
		return rand.nextInt(8000)+1001;
	}

	/**
	 * @return	随机生成的姓
	 */
	public static String randNamex(){
		String[] x={"张","李","王","赵","吴","薛","何","孙","周","吴"};
		return x[rand.nextInt(x.length)]; 
	}
	
	/**
	 * @return	随机生成的名
	 */
	public static String randNamem(){
		char c = '\u4e00';
		int add=rand.nextInt(10000);
		char[] cs={(char)(c+add)};
		return new String(cs);
	}
	
	/**
	 * @return	返回随机生成的姓名
	 */
	public static String randName(){
		int len=rand.nextInt(2);
		if(len==0)
			return new StringBuilder().append(randNamex()).append(randNamem()).toString();
		else
			return new StringBuilder().append(randNamex()).append(randNamem()).append(randNamem()).toString();	
	}
	
	/**
	 * @return 返回随机生成的邮箱
	 */
	public static String randEmail(){
		int len=randNum(4, 8);
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<len;i++){
			sb.append(randSeed[rand.nextInt(randSeed.length)]);
		}
		sb.append("@");
		int len2=rand.nextInt(4);
		String[] eAddr={"qq","163","126","gmail"};
		sb.append(eAddr[rand.nextInt(4)]);
		sb.append(".com");
		return sb.toString();
	}
	
	/**返回随机生成的密码
	 * @return
	 */
	public static String randPsw(){
		int len=randNum(6, 8);
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<len;i++){
			sb.append(randSeed[rand.nextInt(randSeed.length)]);
		}
		return sb.toString();
	}
	
	/**返回随机生成的电话
	 * @return
	 */
	public static String randPhone(){
		String[] randBegin={"180","132","155"};
		StringBuilder sb=new StringBuilder(); 
		sb.append(randBegin[rand.nextInt(3)]);
		for(int i=0;i<8;i++){
			sb.append(rand.nextInt(10));
		}
		return sb.toString();
	}
	
	
	/**
	 * @param max
	 * @return 返回一个指定范围的随机数,含头不含尾
	 */
	public static int randNum(int min,int max){
		return rand.nextInt(max-min)+min;
	}
}
