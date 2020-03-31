package com.molcon.escalex.microbiology.util;

import java.io.UnsupportedEncodingException;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;



public class StringUtil {
	public static final String Error="Error:";
    public static String processStr(String s) {

	s = s == null || s.trim().equals("null") || s.trim().equals("") ? "" : changeSQt(s);
	return s;

    }

    public static String processAndSQt(String str)
    {
    	String s = "";
	if(str == null || str.trim().equals(""))
	    s = "";
	else{
	    s = str.replace("'", "''");
	}

	return StringUtil.singleQt(s);
    }

    public static String doubleQt(String s) {
	return "\""+s+"\"";
    }

    public static String singleQt(String s){
	return "'"+s+"'";
    }
    public static String changeSQt(String s){
	return s == null ? "" : s.replaceAll("'","''");
    }

    public  static String updateRegex(String aRegexFragment){
	StringBuffer result = new StringBuffer();

	StringCharacterIterator iterator = new StringCharacterIterator(aRegexFragment);
	char character =  iterator.current();
	while (character != CharacterIterator.DONE ){
	    /*
	     * All literals need to have backslashes doubled.
	     */

	    if (character == '\\') {
	    	result.append("\\\\");
	    }
	    else if (character == '|') {
		result.append("\\|");
	    }
	    else if (character == '.') {
		result.append("\\.");
	    }
	    else if (character == '?') {
		result.append("\\?");
	    }
	    else if (character == '*') {
		result.append("\\*");
	    }
	    else if (character == '+') {
		result.append("\\+");
	    }
	    else if (character == '&') {
		result.append("\\&");
	    }
	    else if (character == ':') {
		result.append("\\:");
	    }
	    else if (character == '{') {
		result.append("\\{");
	    }
	    else if (character == '}') {
		result.append("\\}");
	    }
	    else if (character == '[') {
		result.append("\\[");
	    }
	    else if (character == ']') {
		result.append("\\]");
	    }
	    else if (character == '(') {
		result.append("\\(");
	    }
	    else if (character == ')') {
		result.append("\\)");
	    }
	    else if (character == '^') {
		result.append("\\^");
	    }
	    else if (character == '$') {
		result.append("\\$");
	    }
	   
	   /* else if (character == '+') {
			result.append("%");
		}*/
	    
	    else {
		//the char is not a special one
		//add it to the result as is
		try{

		    result.append(character);
		}catch(Exception e){
		    ;
		}
	    }
	    character = iterator.next();
	}

	return result.toString();
    }
    public  static String updateSQLRegex(String aRegexFragment){
	StringBuffer result = new StringBuffer();

	StringCharacterIterator iterator = new StringCharacterIterator(aRegexFragment);
	char character =  iterator.current();
	while (character != CharacterIterator.DONE ){
	    /*
	     * All literals need to have backslashes doubled.
	     */

	    if (character == '\\') {
	    	result.append("\\\\\\");
	    }
	    else if (character == '.') {
		result.append("\\\\.");
	    }
	    else if (character == '|') {
		result.append("\\\\|");
	    }
	    else if (character == '?') {
		result.append("\\\\?");
	    }
	    else if (character == '*') {
		result.append("\\\\*");
	    }
	    else if (character == '+') {
		result.append("\\\\+");
	    }
	    else if (character == '&') {
		result.append("\\\\&");
	    }
	    else if (character == ':') {
		result.append("\\\\:");
	    }
	    else if (character == '{') {
		result.append("\\\\{");
	    }
	    else if (character == '}') {
		result.append("\\\\}");
	    }
	    else if (character == '[') {
		result.append("\\[");
	    }
	    else if (character == ']') {
		result.append("\\\\]");
	    }
	    else if (character == '(') {
		result.append("\\\\(");
	    }
	    else if (character == ')') {
		result.append("\\\\)");
	    }
	    else if (character == '^') {
		result.append("\\\\^");
	    }
	    else if (character == '$') {
		result.append("\\\\$");
	    }
	   
	    
	    else {
		//the char is not a special one
		//add it to the result as is
		try{

		    result.append(character);
		}catch(Exception e){
		    ;
		}
	    }
	    character = iterator.next();
	}

	return result.toString();
    }
    public static String processStringForXML(String str)
    {

	if(str != null)
	{

	    return str.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;")
	    .replaceAll("'", "&apos;").replaceAll("\"", "&quot;");
	}

	return "";
    }
    public static String getSQLList(Collection collection) {
	StringBuffer strbf = new StringBuffer();

	if(collection != null)
	{
	    for(Object en : collection)
	    {
		String str = en == null ? "" : en.toString();
		strbf.append(processAndSQt(str) + ",");
	    }
	}

	if(strbf.length() > 0)
	    return strbf.substring(0,strbf.length() - 1);
	return "";
    }
    public static String getSQLList(String[] stringArray) {
	StringBuffer strbf = new StringBuffer();

	if(stringArray != null)
	{
	    for(String en : stringArray)
	    {
		String str = en == null ? "" : en.toString();
		strbf.append(processAndSQt(str) + ",");
	    }
	}

	if(strbf.length() > 0)
	    return strbf.substring(0,strbf.length() - 1);
	return "";
    }

    public static String getDelemSeperatedList(Collection collection, String delem) {
	StringBuffer strbf = new StringBuffer();
	String str = null;
	Object obj = null;
	if(collection != null && collection.size() > 0)
	{
	    Iterator it = collection.iterator();
	    if(it.hasNext())
	    {
		obj = it.next();
		str = obj == null ? "" : obj.toString();
		strbf.append(str);
	    }
	    while(it.hasNext())
	    {
		strbf.append(delem);
		obj = it.next();
		str = obj == null ? "" : obj.toString();
		strbf.append(str);
	    }
	}
	return strbf.toString();
    }
    public static String getDelemSeperatedList(String[] stringArray, String delem) {
	StringBuffer strbf = new StringBuffer();
	String str = null;
	if(stringArray != null && stringArray.length > 0)
	{
	    str = stringArray[0] == null ? "" : stringArray[0].toString();
	    strbf.append(str);
	}
	for(int i = 1; i < stringArray.length; i++)
	{
	    strbf.append(delem);
	    str = stringArray[i] == null ? "" : stringArray[i].toString();
	    strbf.append(str);
	}
	return strbf.toString();
    }


    public static String toUTF8(String isoString)
    {
	String utf8String = null;
	if (null != isoString && !isoString.equals(""))
	{
	    try
	    {
		byte[] stringBytesISO = isoString.getBytes("ISO-8859-1");
		utf8String = new String(stringBytesISO, "UTF-8");
//		System.out.println(" UTF -8 " + utf8String);
	    }
	    catch(UnsupportedEncodingException e)
	    {
		// As we can't translate just send back the best guess.
		System.out.println("UnsupportedEncodingException is: " + e.getMessage());
		utf8String = isoString;
	    }
	}
	else
	{
	    utf8String = isoString;
	}
	return utf8String;
    }


    public static String toISO(String isoString)
    {
	String utf8String = null;
	if (null != isoString && !isoString.equals(""))
	{
	    try
	    {
		byte[] stringBytesISO = isoString.getBytes("UTF-8");
		utf8String = new String(stringBytesISO, "ISO-8859-1");
//		System.out.println(" UTF -8 " + utf8String);
	    }
	    catch(UnsupportedEncodingException e)
	    {
		// As we can't translate just send back the best guess.
		System.out.println("UnsupportedEncodingException is: " + e.getMessage());
		utf8String = isoString;
	    }
	}
	else
	{
	    utf8String = isoString;
	}
	return utf8String;
    }
    
    public static List<String> punctuationList = new ArrayList<String>();
    static{
    	
    	punctuationList.add("\\.");
    	punctuationList.add(",");
    	punctuationList.add("\\?");
    	punctuationList.add(";");
    	punctuationList.add(":");
    	punctuationList.add("-");
    	punctuationList.add("!");
    	punctuationList.add("'");
    	punctuationList.add("\"");
    	punctuationList.add("—");
    	punctuationList.add("–");
    	punctuationList.add("\\/");
    	
    }
    


    public static void main(String []args)
    {
	System.out.println("[sdf]".replace("[",""));
	Calendar cal;
	Date dt= new Date();
	dt.getYear();
	System.out.println((dt.getYear() + 1900) + " " + dt.getDate() + "  " + (dt.getMonth() + 1));

//	java.text.DateFormat dateFormat = new java.text.SimpleDateFormat("dd MM yyyy");
	java.text.DateFormat dateFormat = new java.text.SimpleDateFormat("yyyy MM dd");
	java.util.Date date = new java.util.Date();
	System.out.println(dateFormat.format(date));

	System.out.println("2008 08 05".replaceAll("\\D+","-"));

	String create_dt = "2008-05-28 10:44:05";
	create_dt = create_dt.length() > 10 ? create_dt.substring(0,10) : create_dt;
	System.out.println(create_dt);
    }
    public static boolean isError(String message){
    	return message.startsWith(Error);
    }
}
