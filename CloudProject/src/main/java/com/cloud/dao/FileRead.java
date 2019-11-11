package com.cloud.dao;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import com.cloud.model.Weather;

@Component
public class FileRead {
	ArrayList<Weather> fr=new ArrayList();
	@PostConstruct
	public void getListH() throws IOException
	{
		
		try
		{
		//Reader inputStreamReader = new InputStreamReader(new FileInputStream("./dailyweather.csv"));
		BufferedReader buf=new BufferedReader(new FileReader("src/main/resources/dailyweather.csv"));
		buf.readLine();
	    String s;
		String[] str;
	    Double d;
	    while ((s = buf.readLine()) != null) 
		{
		str=s.split(",");
		Weather wea=new Weather();
		wea.setDate(str[0]);
		wea.setTmax(str[1]);
		wea.setTmin(str[2]);
		fr.add(wea);
		
		}
		
		}
		catch(Exception e) {
			
		}

	}
	public ArrayList<Weather> getList() throws IOException
	{
		return fr;
	}
}