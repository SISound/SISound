package model;

import java.util.ArrayList;

public class Country {

	private long countryID;
	private String countryName;
	private ArrayList<City> cities;
	
	public Country(){
		this.cities=new ArrayList<>();
	}
}
