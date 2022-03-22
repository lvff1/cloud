package com.daji.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.daji.pojo.Type;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class TypeController extends BaseController{
    @RequestMapping("/types/input")
    public String toTypesInputPage(Model model){
        /*
            为什么要这么做?因为一个恶心的问题. 与知识点无关,单纯debug
            给一个不可能的值,一旦接收到id是这个值,那就就说明应该走新增页面.
            其他值是走修改页面
         */
        model.addAttribute("type", new Type(-9999,""));
        return "admin/types-input";
    }

    /*
        跳转修改分类页面

        @PathVariable的应用 controller端:
       通过@PathVariable来接收types.html里面传来的路径变量.
       注意@GetMapping("/types/{id}/input")里面的{id}字段格式
    */
    @GetMapping("/types/{id}/input")
    public String toTypesEditPage(@PathVariable int id, Model model) {
        model.addAttribute("type", typeService.getTypeById(id));
        return "admin/types-input";
    }

    //去分类管理页面
    @GetMapping("/types")
    public String toTypes(Model model, @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum){
        /*
            PageHelper分页插件: 官方文档: https://pagehelper.github.io/docs/
            String orderBy="字段名 排序规律";
            PageHelper.startPage(pageNum, pageSize, orderBy);
         */
        String orderBy = "id desc";
        PageHelper.startPage(pageNum,8,orderBy);
        List<Type> list = typeService.queryAllTypes();
        //用PageInfo对结果进行包装
        PageInfo<Type> pageInfo = new PageInfo<Type>(list);
        System.out.println(pageInfo);
        model.addAttribute("pageInfo",pageInfo);
        return "admin/types";
    }
    //  Restful风格, 如果是新增就走post,仍然是回到分类管理页面
    @PostMapping("/types")
    public String insertType(Type type, RedirectAttributes attributes) {
        Type type1 = typeService.getTypeByName(type.getName());
        if (type1 != null) {
            /*
            [知识点]
                addAttribute和addFlashAttribute的区别
                attr.addAttribute(“param”, value);
                这种方式就相当于重定向之后，在url后面拼接参数，这样在重定向之后的页面或者控制器再去获取url后面的参数就可以了，但这个方式因为是在url后面添加参数的方式，所以暴露了参数，有风险
                attr.addFlashAttribute(“param”, value);
                这种方式也能达到重新向带参，而且能隐藏参数，其原理就是放到session中，session在跳到页面后马上移除对象。
             */
            attributes.addFlashAttribute("message", "已经有这个分类了,请换一个名字");
            System.out.println("已经有这个分类了,请换一个名字");
            return "redirect:/admin/types/input";
        }else{
            typeService.insertType(type);
            attributes.addFlashAttribute("message", "新增成功");
            return "redirect:/admin/types";
        }
    }
    //    编辑修改分类
    @PostMapping("/types/{id}")
    public String editPost(Type type, RedirectAttributes attributes) {
        Type type1 = typeService.getTypeByName(type.getName());
        if (type1 != null) {
            attributes.addFlashAttribute("message", "已经有这个分类了,请换一个名字");
            System.out.println("已经有这个分类了,请换一个名字");
            //这里做了一个小bug上的处理, 重定向能记住刚刚修改的id.
            int returnid = type1.getId();
            return "redirect:/admin/types/"+ returnid +"/input";
        } else {
            typeService.updateType(type.getId(),type.getName());
            attributes.addFlashAttribute("message", "修改成功");
            return "redirect:/admin/types";
        }
    }

    //    删除分类
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable int id,RedirectAttributes attributes) {
        typeService.deleteType(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/types";
    }

}
