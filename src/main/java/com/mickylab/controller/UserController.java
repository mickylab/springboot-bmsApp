package com.mickylab.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mickylab.common.Constants;
import com.mickylab.common.Result;
import com.mickylab.controller.dto.UserDTO;
import com.mickylab.entity.User;
import com.mickylab.service.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserServiceImpl userServiceImpl;
    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    // 用户登录
    @PostMapping("/login")
    public Result login(@RequestBody UserDTO userDTO) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return Result.error(Constants.CODE_400, "参数错误");
        }
        UserDTO dto = userServiceImpl.login(userDTO);
        return Result.success(dto);
    }

    // 用户注册
    @PostMapping("/signup")
    public Result signup(@RequestBody UserDTO userDTO) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return Result.error(Constants.CODE_400, "参数错误");
        }
        return Result.success(userServiceImpl.signup(userDTO));
    }

    // 用户名查询
    @GetMapping("/username/{username}")
    public Result findByUsername(@PathVariable String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return Result.success(userServiceImpl.getOne(queryWrapper));
    }

    // 新增和修改
    @PostMapping
    public Result save(@RequestBody User user) {
        // 新增或更新
        return Result.success(userServiceImpl.saveOrUpdate(user));
    }

    // 查询所有
    @GetMapping
    public Result findAll() {
        return Result.success(userServiceImpl.list());
    }

    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id) {
        return Result.success(userServiceImpl.getById(id));
    }

    // 删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(userServiceImpl.removeById(id));
    }

    // 批量删除
    @PostMapping("/del/batch")
    public Result batchDelete(@RequestBody List<Integer> ids) {
        return Result.success(userServiceImpl.removeByIds(ids));
    }

    // 分页查询 - mybatis-plus方式
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize,
                                @RequestParam(defaultValue = "") String username,
                                @RequestParam(defaultValue = "") String email,
                                @RequestParam(defaultValue = "") String address) {
        IPage<User> page = new Page<>(pageNum, pageSize);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (!username.isEmpty()) queryWrapper.like("username", username);
        if (!email.isEmpty()) queryWrapper.like("email", email);
        if (!address.isEmpty()) queryWrapper.like("address", address);
        // 默认是AND, 使用OR: queryWrapper.or().like("email", email);
        // defaultValue = "" 加上这个参数不传不报400
        queryWrapper.orderByDesc("create_time");
        return Result.success(userServiceImpl.page(page, queryWrapper));
    }

    // 导出接口
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        // 从数据库查出所有数据
        List<User> list = userServiceImpl.list();
        // 在内存操作, 直接写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        // 一次性写出list内的对象到excel,使用默认样式, 强制输出标题
        writer.write(list, true);
        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("UserList", StandardCharsets.UTF_8);
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();
    }

    // 导入接口
    @PostMapping("/import")
    public Result importFromExcel(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<User> userList = reader.readAll(User.class);
        userServiceImpl.saveBatch(userList);
        return Result.success(true);
    }
}
