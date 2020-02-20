package com.state;

public class TVRemoteStart implements State {

	@Override
	public void doAction() {
		System.out.println("TV Started..");
	}

}
