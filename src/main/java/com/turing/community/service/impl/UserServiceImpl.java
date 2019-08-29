package com.turing.community.service.impl;

import com.turing.community.bean.User;
import com.turing.community.bean.UserExample;
import com.turing.community.dao.UserMapper;
import com.turing.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.UUID;

/**
 * @author Meng
 * @date 2019/8/17
 */
@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private UserMapper userMapper;
    @Value("${spring.mail.username}")
    private String from;
    @Autowired
    private JavaMailSender javaMailSender;
    private String SUBJECT="社区激活邮件";

    /**
     * 用户注册发送邮件激活
     * @param user 传过来的注册邮箱和密码
     * @return 邮件发送成功返回true
     */
    public boolean addUser(User user){
        String emailCode= UUID.randomUUID().toString().replaceAll("-","");
        user.setEmailCode(emailCode);
        try {
            if(isUserExisted(user.getEmail())){
                return false;
            }
            userMapper.insertSelective(user);
            sendMail(user);
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 完成激活操作
     * @param user
     * @return 成功激活返回true，已经激活返回false
     */
    @Override
    public boolean registerUser(User user) {
        if(isRegister(user.getUserId())){
            return false;
        }
        else{
            if(equalEmailCode(user)) {
                UserExample userExample = new UserExample();
                UserExample.Criteria criteria = userExample.createCriteria();
                criteria.andUserIdEqualTo(user.getUserId());
                User newUser = new User();
                newUser.setState(1);
                userMapper.updateByExampleSelective(newUser, userExample);
                return true;
            }
            else{
                return false;
            }
        }
    }

    /**
     * 封装邮件发送
     * @param user 发视的用户
     */
    public void sendMail(User user) throws MessagingException {
        String text="<html><body><p>点击下面链接完成账户激活</p><a href='http://localhost:9090/register?emailCode="+user.getEmailCode()+"&userId="+user.getUserId()+"'>激活账户</a></body></html>";
        MimeMessage  mimeMessage=javaMailSender.createMimeMessage();
        MimeMessageHelper helper;
        helper=new MimeMessageHelper(mimeMessage,true);
        helper.setFrom(from);
        helper.setTo(user.getEmail());
        helper.setSubject(SUBJECT);
        helper.setText(text,true);
        javaMailSender.send(mimeMessage);
    }

    /**
     * 根据邮箱判断该用户是否已经存在
     * @param email 用户邮箱
     * @return 存在返回true，不存在返回false
     */
    public boolean isUserExisted(String email){
        UserExample userExample=new UserExample();
        UserExample.Criteria criteria= userExample.createCriteria();
        criteria.andEmailEqualTo(email);
        List<User> list=userMapper.selectByExample(userExample);
        return !list.isEmpty();
    }

    /**
     * 判断用户是否已经激活
     * @param userId 用户id
     * @return 已经激活返回true,否则返回false
     */
    @Cacheable(cacheNames = {"user"})
    public boolean isRegister(Integer userId){
        User user=selectByUserId(userId);
        if(user!=null){
            return user.getState()==1;
        }
        else{
            return false;
        }
    }

    /**
     * 判断激活码是否相等
     * @param user 传过来的用户参数
     * @return 相同为true，否则为false
     */
    public boolean equalEmailCode(User user){
        if(selectByUserId(user.getUserId())!=null) {
            return user.getEmailCode().equals(selectByUserId(user.getUserId()).getEmailCode());
        }
        else{
            return false;
        }
    }

    /**
     * 根据id获取用户
     * @param userId
     * @return
     */
    @Cacheable(cacheNames = {"user"})
    public User selectByUserId(Integer userId){
        UserExample userExample=new UserExample();
        UserExample.Criteria criteria=userExample.createCriteria();
        criteria.andUserIdEqualTo(userId);
        List<User> user=userMapper.selectByExample(userExample);
        return user.isEmpty()?null:user.get(0);
    }

    @Cacheable(cacheNames = {"user"})
    public User selectByEmail(String Email){
        UserExample userExample=new UserExample();
        UserExample.Criteria criteria=userExample.createCriteria();
        criteria.andEmailEqualTo(Email);
        List<User> user=userMapper.selectByExample(userExample);
        return user.isEmpty()?null:user.get(0);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user=selectByEmail(s);
        if(user==null){
            return new User();
        }
        List<Role> roles
    }
}
