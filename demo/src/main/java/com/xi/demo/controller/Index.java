package com.xi.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xi.demo.bean.Users;
import com.xi.demo.mapper.UsersMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * @RefreshScope 的作用是动态刷新需要配合配置中心一起使用，可以实时修改配置中心中的配置文件
 * 使用/actuator/refresh接口刷新注解所在文件中调用的配置文件的值，如果有文件中调用了配置文件中的
 * 值但是没有使用这个注解也无法完成实时刷新
 *
 *
 * Created by xxb on 2018/11/1.
 */
@RestController
@ResponseBody
@Slf4j
@RequestMapping("/api")
@Api(value = "demo测试接口", description ="demo测试接口相关类")
public class Index {

    @Resource
    UsersMapper usersMapper;

    @GetMapping("/logTest")
    @ApiOperation(value="日志测试", notes="测试admin管理日志是否生效")
    public String logTest(){
        log.trace("日志测试:这是trace");
        log.debug("日志测试:这是debug");
        log.info("日志测试:这是info");
        log.warn("日志测试:这是warn");
        log.error("日志测试:这是error");
        return "ok";
    }

    @RequestMapping(value = "/getUserCount",method = RequestMethod.GET)
    public String getUserCount(){
        int i = usersMapper.getUserCount();
        return "用户表用户数量为："+i;
    }
    @ApiOperation(value = "分页示例代码", notes = "分页示例代码")
    @RequestMapping(value = "getPageHelper", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ispage", value = "是否使用分页", required = true, dataType = "boolean", paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "查询页数", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = false, dataType = "int", paramType = "query"),
    })
    public JSONObject getPageHelper(@RequestParam(name = "ispage") boolean ispage,
                                    @RequestParam(name = "pageNum", required = false) Integer pageNum,
                                    @RequestParam(name = "pageSize", required = false) Integer pageSize)
    {
        if (ispage) {
            /**
             * mybatis分页使用配置方法
             */
            PageHelper.startPage(pageNum, pageSize);
        }
        JSONObject k = new JSONObject();

        try {
            /**
             * 使用PageInfo将正常查询所有数据进行包装
             * 则执行查询所有时mybatis会自动在执行sql后加上limit，页数和条数通过上面的配置方法配置
             * 如果不使用PageInfo进行包装则返回所有数据
             */
            List<Users> users=usersMapper.getAllUsers();
            k.put("code", 1);
            k.put("message", "查询成功");
            k.put("result", users);
            if (ispage) {
                PageInfo<Users> pageInfo = new PageInfo<>(users);
                k.put("result", pageInfo);
            }
        }catch (Exception e){
            k.put("code", 0);
            k.put("message", "查询失败");
            k.put("result", new JSONObject());
            k.put("Exception",e);
        }
        return k;
    }
}
