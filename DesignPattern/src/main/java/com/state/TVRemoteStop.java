package com.state;

public class TVRemoteStop implements State {

	@Override
	public void doAction() {
		System.out.println("TV Stopped..");
	}

}
