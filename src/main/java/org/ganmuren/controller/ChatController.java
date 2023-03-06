package org.ganmuren.controller;


import com.google.gson.Gson;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.ganmuren.entity.Role;
import org.ganmuren.entity.User;
import org.ganmuren.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ganmuren
 */
@Slf4j
@RestController//@RestController是@Controller和@ResponseBody的结合体
//@responseBody注解的作用是将controller的方法返回的对象通过适当的转换器转换为指定的格式之后
//写入到response对象的body区，通常用来返回JSON数据或者是XML数据
public class ChatController {

    @Autowired
    UserService userService;

    //@GetMapping是一个组合注解，是@RequestMapping(method = RequestMethod.GET)的缩写
    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name") String in) {
        log.error("error!!!!!!!!!!!");
        log.debug("error!!!!!!!!!!!");
        log.info("info!!!!!!!!!!!");
        return "hello " + in + "!!!!!!!";
    }

    @GetMapping("/date")
    public Date date(){
        return new Date();
    }

    @GetMapping("/user/hello")
    public String userHello() {
        return "hello user";
    }

    @GetMapping("/admin/hello")
    public String adminHello() {
        return "hello admin";
    }

    @PostMapping("/admin/sign")
    public String sign(String username, String password) {
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            return "账号或密码为空";
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        List<Role> roles = new ArrayList<>();
        Role role = new Role();
        role.setId(Role.Authority.USER.getId());
        roles.add(role);
        user.setRoles(roles);

        try{
            userService.signUser(user);
        }catch (UsernameNotFoundException e){
            return "账号名已存在";
        }

        return "注册成功";
    }

    //Jackson反序列化内部类，内部类需静态且含无参构造方法
    @Data
    static class Message {
        private String name;
        private String content;
    }

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/send")//接收/app/send路径的消息，js来向该端口发送消息
    @SendTo("/topic/greetings") //转发路径，topic前缀走broker进行广播, 接收端js来订阅该消息
    public Message greeting(Message message){
        log.info("message: {}", new Gson().toJson(message));
        return message;
    }

    //也可写成该样式
    /*@MessageMapping("/send")
    public void greeting(Message message){
        simpMessagingTemplate.convertAndSend ("/topic/greetings", message) ;
    }*/

    @Data
    static class Chat {
        private String to;
        private String from;
        private String content;
    }

    @MessageMapping("/chat")//接收/app/chat路径的消息
    public void chat(Principal principal, Chat chat){//Principal获取当前登录的用户信息
        chat.setFrom(principal.getName());
        simpMessagingTemplate.convertAndSendToUser(chat.getTo(), "/queue/chat", chat); //转发路径为 /user/用户名/queue/chat
        //@SendToUser拼接上 /user/前缀则会进行私人消息发送，此时js订阅/user/queue/chat
    }

}
