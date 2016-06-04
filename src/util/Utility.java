package util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import model.City;
import model.County;
import model.Province;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import db.MyWeatherDB;

public class Utility {

	/**
	 * Analyzed and processed the returned provincial data from server
	 */
	public synchronized static boolean handleProvincesResponse(MyWeatherDB 
			myWeatherDB,String response){
		if(!TextUtils.isEmpty(response)){
			String[] allProvince = response.split(",");
			if(allProvince != null && allProvince.length>0){
				for(String p : allProvince){
					String[] array = p.split("\\|");
					Province province = new Province();
					province.setProvinceCode(array[0]);
					province.setProvinceName(array[1]);
					// analyzed data saved to Province Table
					myWeatherDB.savedProvince(province);
				}
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Analyzed and processed the returned city's data from server
	 */
	public synchronized static boolean handleCitiesResponse(MyWeatherDB 
			myWeatherDB,String response,int provinceId){
		if(!TextUtils.isEmpty(response)){
			String[] allCities = response.split(",");
			if(allCities != null && allCities.length>0){
				for(String p : allCities){
					String[] array = p.split("\\|");
					City city = new City();
					city.setCityCode(array[0]);
					city.setCityName(array[1]);
					// analyzed data saved to Province Table
					myWeatherDB.savedCity(city);
				}
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Analyzed and processed the returned county's data from server
	 */
	public synchronized static boolean handleCountiesResponse(MyWeatherDB 
			myWeatherDB,String response,int cityId){
		if(!TextUtils.isEmpty(response)){
			String[] allCounties = response.split(",");
			if(allCounties != null && allCounties.length>0){
				for(String p : allCounties){
					String[] array = p.split("\\|");
					County county = new County();
					county.setCountyCode(array[0]);
					county.setCountyName(array[1]);
					// analyzed data saved to Province Table
					myWeatherDB.savedCounty(county);
				}
				return true;
			}
		}
		return false;
	}
	
	/**
	 * JSON data that are parsed saved to local
	 */
	public static void handleWeatherResponse(Context context,String response){
		try{
			JSONObject jsonObject = new JSONObject(response);
			JSONObject weatherInfo = jsonObject.getJSONObject("weatherinfo");
			String cityName = weatherInfo.getString("city");
			String weatherCode = weatherInfo.getString("cityid");
			String temp1 = weatherInfo.getString("temp1");
			String temp2 = weatherInfo.getString("temp2");
			String weatherDesp = weatherInfo.getString("weather");
			String publishTime = weatherInfo.getString("ptime");
			saveWeatherInfo(context,cityName,weatherCode,temp1,temp2,
					weatherDesp,publishTime);
		}catch(JSONException e){
			e.printStackTrace();
		}
	}
/*
 * all weatherInformation returned from server saved to SharedPreferences
 */
	public static void saveWeatherInfo(Context context, String cityName,
			String weatherCode, String temp1, String temp2, String weatherDesp,
			String publishTime) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyƒÍM‘¬d»’",Locale.CHINA);
		SharedPreferences.Editor editor = PreferenceManager
				.getDefaultSharedPreferences(context).edit();
		editor.putBoolean("city_selected", true);
		editor.putString("city_name", cityName);
		editor.putString("weather_code", weatherCode);
		editor.putString("temp1", temp1);
		editor.putString("temp2", temp2);
		editor.putString("weather_desp", weatherDesp);
		editor.putString("publish_time", publishTime);
		editor.putString("current_date", sdf.format(new Date()));
		editor.commit();
		
	}
	
}
