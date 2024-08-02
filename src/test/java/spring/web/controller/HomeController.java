package spring.web.controller;

import com.spring.web.annotation.*;

@Controller
public class HomeController {

    @RequestMapping("/hello")
    public String hello() {
        return "Haha,what can i say, mamba out";
    }

    @GetMapping("/kobe")
    public String hello(@RequestParam("name") String name) {
        return "see you again, " + name;
    }

    @GetMapping("/users/{id}")
    public String getUser(@PathVariable("id") int id) {
        return "User ID: " + id;
    }

}
