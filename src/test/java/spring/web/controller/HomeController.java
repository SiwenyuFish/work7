package spring.web.controller;

import com.spring.web.annotation.*;
import spring.bean.User;

@Controller
public class HomeController {

    @RequestMapping("/hello")
    public String hello() {
        return "Haha,what can i say, mamba out";
    }

    @GetMapping("/kobe")
    public String hello(@RequestParam("name") String name,@RequestParam("number")Integer number) {
        return "see you again, " + name + number;
    }

    @GetMapping("/users/{id}")
    public String getUser(@PathVariable("id") int id) {
        return "User ID: " + id;
    }

    @GetMapping("/uu/{id}")
    @ResponseBody
    public User uu(@PathVariable("id") int id) {
        return new User("Dr",id);
    }

    @RequestMapping("/auth")
    public String auth(@RequestHeader("Authorization")String header) {
        return "request header is :"+header;
    }

    @GetMapping("/cookie")
    public String cookie(@CookieValue("test")String cookie) {
        return "cookie is :"+cookie;
    }


    @PostMapping("/test/request")
    public String testRequestBody(@RequestBody User uu) {
        return uu.toString();
    }

}
