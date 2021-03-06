package com.example.serviceuser.controller;

import com.example.serviceuser.entity.Usr;
import com.example.serviceuser.entity.UsrExample;
import com.example.serviceuser.mapper.UsrMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
public class UserController {

    @Autowired
    private UsrMapper userMapper;

    @RequestMapping(value = "/user",method = RequestMethod.POST)
    public boolean add(@RequestBody Usr user){
        return userMapper.insert(user) > 0;
    }

    @RequestMapping(value = "/user",method = RequestMethod.PUT)
    public boolean update(@RequestBody Usr user){
        UsrExample me = new UsrExample();
        me.createCriteria().andIdEqualTo(user.getId());
        return userMapper.updateByExample(user,me) > 0;
    }

    @RequestMapping(value = "/user",method = RequestMethod.GET)
    public List<Usr> get(@RequestParam(value = "userId",required = false)Integer userId){
        UsrExample me = new UsrExample();
        if(Objects.nonNull(userId))
            me.createCriteria().andIdEqualTo(userId);
        return userMapper.selectByExample(me);
    }

    @RequestMapping(value = "/user",method = RequestMethod.DELETE)
    public boolean delete(@RequestParam(value = "userId", required = false)Integer userId, @RequestParam(value = "userIdList", required = false)List<Integer> userIdList){
        UsrExample me = new UsrExample();
        if(Objects.nonNull(userId)){
            me.createCriteria().andIdEqualTo(userId);
        }
        if (Objects.nonNull(userIdList)){
            userIdList.stream().forEach(i -> {
                me.createCriteria().andIdEqualTo(i);
            });
        }
        if(Objects.isNull(userId) && Objects.isNull(userIdList)){
            return true;
        }
        return userMapper.deleteByExample(me) > 0;
    }
}
