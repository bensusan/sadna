package TS_ServiceLayer;

import java.util.List;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import TS_BL.BlMain;
import TS_ServiceLayer.HelloMessage.functionNames;
import TS_SharedClasses.*;

@Controller
public class GreetingController {


    @MessageMapping("/TS_ServiceLayer")
    @SendTo("/topic/greetings")
    public Object greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        
        List<Object> args = message.getArgs();
        //addImmediatelyProduct example
        
        functionNames f = (functionNames)args.get(0);
        Object ret = null;
        switch(f){
        	case addImmediatelyProduct:
        		ret = BlMain.addImmediatelyProduct((Guest)args.get(1), (Product)args.get(2), (Integer)args.get(3));
        		
        }
        return ret;
    }

}


