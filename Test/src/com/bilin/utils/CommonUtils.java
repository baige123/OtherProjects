package com.bilin.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;



public class CommonUtils {

	private static Logger log = Logger.getLogger(CommonUtils.class);
	


	/**
	 * 获取指定长度(按字节长度获取)的字符串
	 * @param src 源字符串
	 * @param length 长度
	 * @return
	 */
	public static String getSubStr(String src, int length) {
		if (StringUtils.isEmpty(src)) {
			return null;
		}
		if (src.getBytes().length > length) {
			byte[] b = src.getBytes();
			byte[] s = new byte[length];
			for (int i = 0; i < length; i++) {
				s[i] = b[i];
			}
			return new String(s);
		} else {
			return src;
		}
	}

	/**
	 * 获取异常信息内容
	 * @param e 异常对象
	 * @param length 指定长度
	 * @return 返回异常信息内容
	 */
	public static String getExceptionString(Exception e, int length) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(os);
		e.printStackTrace(ps);
		String msg = os.toString();
		if (msg.length() > length) {
			msg = getSubStr(msg, length);
		}
		return msg;
	}

	/**
	 * 获取异常信息内容
	 * @param e 异常对象
	 * @return 返回异常信息内容
	 */
	public static String getExceptionString(Exception e) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(os);
		e.printStackTrace(ps);
		String msg = os.toString();
		return msg;
	}



	public static String formatDate(Date date, String formatStr) {
		SimpleDateFormat dateformat = new SimpleDateFormat(formatStr);
		String dateStr = dateformat.format(date);
		return dateStr;
	}

	/**
	 * 用于处理前台通过GET提交中文乱码的问题，做一个编码转换
	 * @param originalString  源字符串
	 * @return 编码转换后的字符串
	 */
	public static String changeEncode(String originalString) {
		try {
			return new String(originalString.getBytes("ISO-8859-1"), "UTF-8");
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 复制List对象
	 * @param list
	 * @return
	 */
	public static List copyList(List list) {
		try {
			List listBak = new ArrayList();
			for (Object obj : list) {
				listBak.add(BeanUtils.cloneBean(obj));
			}
			return listBak;
		} catch (Exception e) {
			log.error("对象拷贝异常:" + e.getMessage());
			throw new RuntimeException("数据拷贝异常:" + e.getMessage());
		}
	}

	/**
	 * 拷贝2个对象的一般属性，不包括集合类属性,目标对象的集合类被初始化为空集合
	 * add by lsw
	 * @param newObj
	 * @param srcObj
	 * @throws IllegalAccessException, InvocationTargetException, NoSuchMethodException 
	 * 
	 */
	public static void copyProperties(Object newObj, Object srcObj) throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {

		if (newObj == null || srcObj == null) {
			return;
		}
		Field[] fields = srcObj.getClass().getDeclaredFields();
		String name = null;
		for (int i = 0; i < fields.length; i++) {
			name = fields[i].getName();
			//过滤掉集合属性

			if (!(fields[i].getType().isAssignableFrom(Set.class) || fields[i].getType().isAssignableFrom(
					Collection.class))) {

				PropertyUtils.setSimpleProperty(newObj, name, PropertyUtils.getSimpleProperty(srcObj, name));

			} else {
				PropertyUtils.setSimpleProperty(newObj, name, new HashSet());
			}

		}

	}

	/**
	 * 深度拷贝2个对象,包扩关联对象
	 * add by lsw
	 * 
	 * @param srcObj
	 * @throws IOException, ClassNotFoundException
	 * 
	 */
	public static Object copyObject(Object srcObj) throws IOException, ClassNotFoundException {

		//利用对象序列化技术
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(byteOut);
		out.writeObject(srcObj);

		ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
		ObjectInputStream in = new ObjectInputStream(byteIn);
		return in.readObject();

	}

	/**
	 * 深度拷贝对象,包扩关联对象,返回拷贝数组
	 * add by lsw
	 * 
	 * @param srcObj
	 * @param count 拷贝个数
	 * @throws IOException, ClassNotFoundException
	 * 
	 */
	public static Object[] copyObject(Object srcObj, int count) throws IOException, ClassNotFoundException {

		//利用对象序列化技术
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(byteOut);
		out.writeObject(srcObj);
		ByteArrayInputStream byteIn = null;
		ObjectInputStream in = null;
		Object[] retObj = new Object[count];
		for (int i = 0; i < count; i++) {
			byteIn = new ByteArrayInputStream(byteOut.toByteArray());
			in = new ObjectInputStream(byteIn);
			retObj[i] = in.readObject();
		}

		return retObj;

	}

	/**
	 * 判断字符串中是否只包含数字
	 * @param 字符串
	 * @return true-只包含数字,false-其他任何情况
	 */
	public static boolean isContainsNumberOnly(String str) {
		boolean result = true;
		if (!StringUtils.isEmpty(str)) {
			for (int i = 0; i < str.length(); i++) {
				if (!Character.isDigit(str.charAt(i))) {
					result = false;
				}
			}
		} else {
			result = false;
		}
		return result;
	}

	/**
	 * 判断当前日期是否是月末
	 * @param date
	 * @return
	 */
	public static boolean isMonthEnd(Date date) {
		int day;
		if (date == null) {
			return false;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date.getTime());
		day = cal.get(Calendar.DATE);
		if (day == 28 || day == 29 || day == 30 || day == 31) {
			return true;
		}
		return false;
	}
	/**
	 * 判断List是否为null或者空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isListEmpty(List list) {

		return list == null || list.isEmpty();
	}
	
	
	/**
	 * 生成资源要的uuId
	 * @return
	 */
	public static String getUUIDStr (){
		String uuid = UUID.randomUUID().toString();
		String uuId = uuid.substring(0,8) + uuid.substring(9,13) + uuid.substring(14,18) + uuid.substring(19,23) + uuid.substring(24);
		return uuId; 
	}
	

	
	/**
	 * 去除字符串中的换行，回车。
	 * 
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str) {
		if (null == str || "".equals(str.trim())) {
			return str;
		} else {
			return str.replaceAll("\r", "").replaceAll("\n", "");
		}
	}

	
	public static void main(String[] args){
		System.out.println(CommonUtils.getExceptionString(new Exception()));
	}
	
	
}
