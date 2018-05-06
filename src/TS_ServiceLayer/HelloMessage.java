package TS_ServiceLayer;

import java.util.List;

public class HelloMessage {

	public static enum functionNames {addImmediatelyProduct}
	
	//assume first argument is on of the enums.
	private List<Object> args;

    public HelloMessage(List<Object> args) {
    	this.args = args;
    }

    public List<Object> getArgs() {
        return args;
    }

    public void setArgs(List<Object> args) {
        this.args = args;
    }
}
