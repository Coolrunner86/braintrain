package com.github.coolrunner86.braintrain;

import java.util.ArrayList;
import java.util.List;

public class DataState {
	private List<Byte> data;
	
	public DataState(List<Byte> newData) {
		data = newData;
	}
	
	public DataState() {
		data = new ArrayList<Byte>();
	}
	
	public List<Byte> getData() {
		return data;
	}

	public void removeAtPos(int pos) {
		if(pos < data.size())
			data.set(pos, (byte)0);
	}
}
