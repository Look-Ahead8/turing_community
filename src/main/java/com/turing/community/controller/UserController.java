package com.turing.community.controller;

import com.turing.community.bean.User;
import com.turing.community.message.Message;
import com.turing.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Meng
 * @date 2019/8/9
 */
@RestController
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public Message getUser(@Valid User user, BindingResult result){
        if(result.hasErrors()){
            List<FieldError> errors=result.getFieldErrors();
            Map<String,Object> map=new HashMap<>();
            for(FieldError error:errors){
                map.put(error.getField(),error.getDefaultMessage());
            }
            return Message.fail().add("errors",map);
        }
        else{
            if(userService.addUser(user)) {
                return Message.success();
            }
            else{
                Map<String,Object> map=new HashMap<>();
                map.put("message","该账户已经发送邮件");
                return Message.fail().add("errors",map);
            }
        }
    }

    @GetMapping("/register")
    public Message registerUser(User user){
        if(userService.registerUser(user)) {
            return Message.success();
        }
        else{
            return Message.fail().add("error","该用户已经激活或激活错误");
        }
    }

    @GetMapping("/user/{userId}")
    public Message getUser(@PathVariable("userId") Integer userId){
        User user=userService.selectByUserId(userId);
        return Message.success().add("user",user);
    }
}
