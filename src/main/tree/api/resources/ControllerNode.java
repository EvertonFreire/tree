package main.tree.api.resources;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControllerNode {
    @GetMapping("/")
    public String list(){
        return "Nodes in Java API\n\n " +
                "We uses a SpringBoot Framework and Hibernate to persist the Data.\n" +
                "Thank you for use mine API.";
    }
}
