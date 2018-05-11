package TS_ServiceLayer;

public class Greeting {
	
	
	private String pageName;
	private String functionName;
	private boolean isException;
    private String contentAsJson;
    
    public Greeting(String pageName, String functionName) {
    	this.pageName = pageName;
    	this.functionName = functionName;
    	this.isException = false;
        this.contentAsJson = null;
    }

    public String getPageName(){
    	return this.pageName;
    }
    
    public String getFunctionName(){
    	return this.functionName;
    }
    
    public boolean getIsException(){
    	return this.isException;
    }
    
    public void setException(){
    	this.isException = true;
    }
    
    public String getContentAsJson() {
        return this.contentAsJson;
    }
    
    public void setContentAsJson(String contentAsJson){
    	this.contentAsJson = contentAsJson;
    }
}
