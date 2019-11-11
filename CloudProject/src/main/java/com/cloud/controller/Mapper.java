package com.cloud.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cloud.dao.FileRead;
import com.cloud.model.Weather;

@RestController
public class Mapper {
	
	@Autowired
	FileRead fr;
	
	@GetMapping("/weather")
	public ModelAndView getList() throws IOException{
		ModelAndView mv =new ModelAndView();
		mv.setViewName("displayall.jsp");
		mv.addObject("list", fr.getList());
		return mv;
	}
	
	@GetMapping("/historical")
	public ModelAndView getDate() throws IOException{
		ModelAndView mv = new ModelAndView();
		ArrayList<Weather> list= new ArrayList<Weather>();
		ArrayList<String> ret = new ArrayList<String>();
		list = fr.getList();
		System.out.println("hello historical");
 		for(Weather w : list) {
			ret.add(w.getDate());
		}
 		System.out.println(ret.get(1));
		mv.setViewName("DisplayDate.jsp");
		mv.addObject("listd", ret);
		return mv;
	}
	
	@GetMapping("/historical/{date}")
	public void getSpeDate(@PathVariable String date,HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
		ModelAndView mv = new ModelAndView();
		ArrayList<Weather> list = fr.getList();
		Weather retw = new Weather();
		for(Weather w :list) {
			if(w.getDate().equals(date)) {
				retw = w;
			}
		}
		System.out.println(retw.getTmax());
		request.setAttribute("ret_list", retw);
		RequestDispatcher rd=request.getRequestDispatcher("display1.jsp");
		rd.forward(request, response);
	}
	
	@DeleteMapping("/historical/{date}")
	public ResponseEntity<HttpStatus> deleteWeather(@PathVariable String date) throws IOException{
		List<Weather> list = fr.getList();
		for(Weather w : list) {
			if(w.getDate().equals(date)) {
				list.remove(w);
				System.out.println("Record Deleted");
				ResponseEntity<HttpStatus> re =new ResponseEntity(HttpStatus.ACCEPTED);
				return re;
			}
		}
		ResponseEntity<HttpStatus> re = new ResponseEntity<>(HttpStatus.CONFLICT);
		return re;
	}
	
	@PostMapping("/historical")
	public ResponseEntity<HttpStatus> putWeather(@RequestBody Weather weath) throws IOException{
		List<Weather> list  = fr.getList();
		for(Weather we : list) {
			if(we.getDate().equals(weath.getDate())) {
				we.setTmax(weath.getTmax());
				we.setTmin(weath.getTmin());
				ResponseEntity<HttpStatus> re = new ResponseEntity<>(HttpStatus.ACCEPTED);
				return re;
			}
		}
		ResponseEntity<HttpStatus> re = new ResponseEntity<>(HttpStatus.ACCEPTED);
		list.add(weath);
		return re;
	}
	@GetMapping("/forecast/{date}")
	public ArrayList<Weather> forecast(@PathVariable String date) throws IOException
	{
		ArrayList<Weather> ret = new ArrayList<Weather>();
		List<Weather> list =fr.getList();
		for(Weather w : list) {
			if(w.getDate().equals(date)) {
				int index =list.indexOf(w);
				ret.add(list.get(index-2));
				ret.add(list.get(index-1));
				ret.add(list.get(index));
				for(int i =1 ; i<8;i++) {
					Weather wadd = new Weather();
					String current = Integer.toString((Integer.parseInt(date)+i));
					System.out.println("Current" + current);
					wadd.setDate(current);
					Weather w1 = ret.get(i-1);
					System.out.println(w1);
					Weather w2 = ret.get(i);
					System.out.println(w2);
					Weather w3 =ret.get(i+1);
					System.out.println(w3);
					wadd.setTmin(Float.toString((Float.parseFloat(w1.getTmin())+Float.parseFloat(w2.getTmin())+Float.parseFloat(w3.getTmin()))/3));
					System.out.println(wadd.getTmin());
					wadd.setTmax(Float.toString((Float.parseFloat(w1.getTmax())+Float.parseFloat(w2.getTmax())+Float.parseFloat(w3.getTmax()))/3));
					System.out.println(wadd.getTmax());
					ret.add(wadd);
					
				}
				ret.remove(list.get(index-2));
				ret.remove(list.get(index-1));
				ret.remove(list.get(index));
			}
		}
		return ret;
	}
}	