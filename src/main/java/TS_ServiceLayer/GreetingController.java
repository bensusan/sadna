package TS_ServiceLayer;

import org.springframework.messaging.handler.annotation.MessageMapping;
import TS_SharedClasses.*;

import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.google.gson.Gson;

@Controller
public class GreetingController {


    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        Gson gson = new Gson();
        Guest g = gson.fromJson(message.getGuest(), Guest.class);
        return new Greeting(gson.toJson(g) + "\n" + message.getG() + " " + message.getLs()[0] + " " + "User name: " + message.getUserName() + "\nPassword: " + message.getPassword() + "\nThanks!");
    }

}
