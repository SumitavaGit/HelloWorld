package com;

import com.state.TVRemoteContext;
import com.state.TVRemoteStart;
import com.state.TVRemoteStop;

public class TVRemote {

	public static void main(String[] args) {
		TVRemoteContext context = new TVRemoteContext();
		
		context.setState(new TVRemoteStart());
		context.doAction();
		
		context.setState(new TVRemoteStop());
		context.doAction();
	}

}
