package util;

import model.City;
import model.County;
import model.Province;
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
	
}
