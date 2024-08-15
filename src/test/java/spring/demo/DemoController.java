package spring.demo;

import com.spring.core.factory.annotation.Autowired;
import com.spring.web.annotation.*;
import spring.bean.User;

import java.util.List;

@Controller
public class DemoController {

    @Autowired
    private static DemoService demoService;


    @PostMapping("/demoPost")
    public String addUser(@RequestBody User user) {
        try {
            demoService.addUser(user);
            return "post";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/demoDelete")
    public String deleteUser(@RequestParam("id") int id) {
        demoService.deleteUser(id);
        return "delete";
    }

    @GetMapping("/demoGet")
    @ResponseBody
    public List<User> getUsers() {
        return demoService.getUsers();
    }

    @PutMapping("/demoPut")
    public String updateUser(@RequestParam("name") String name, @RequestParam("id") int id) {
        demoService.updateUser(name,id);
        return "put";
    }



    @RequestMapping("/demoHello")
    public String hello() {
        return "hi,demo";
    }




}
