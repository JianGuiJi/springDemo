package com.itheima.springmvc.controller;

import com.itheima.springmvc.exception.MessageException;
import com.itheima.springmvc.pojo.Items;
import com.itheima.springmvc.pojo.QueryVo;
import com.itheima.springmvc.service.ItemService;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.io.FilenameUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 商品管理
 *
 * @author lx
 */
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    /**
     * 1、ModelAndView 无敌的 带数据的返回视图路径 不建议使用
     * 2、String 返回视图路径 model带数据 官方推荐此种方法  解耦 数据 视图 分离 MVC 建议使用（刷新页面）
     * 3、void  ajax 请求 合适jsoin格式数据 异步请求使用
     *
     * @return
     */
    //入门程序 第一   包类 + 类包 + 方法名
    @RequestMapping(value = "/item/itemlist.action")
    public ModelAndView itemList() {
        //从Mysql中查询
        List<Items> list = itemService.selectItemsList();
//		List<Items> list = new ArrayList<Items>();
//		list.add(new Items(1, "1华为 荣耀8", 2399f, new Date(), "质量好！1"));
//		list.add(new Items(2, "2华为 荣耀8", 2399f, new Date(), "质量好！2"));
//		list.add(new Items(3, "3华为 荣耀8", 2399f, new Date(), "质量好！3"));
//		list.add(new Items(4, "4华为 荣耀8", 2399f, new Date(), "质量好！4"));
//		list.add(new Items(5, "5华为 荣耀8", 2399f, new Date(), "质量好！5"));
//		list.add(new Items(6, "6华为 荣耀8", 2399f, new Date(), "质量好！6"));

        ModelAndView mav = new ModelAndView();
        //数据
        mav.addObject("itemList", list);
        mav.setViewName("itemList");
        return mav;
    }

//    @RequestMapping(value = "/item/itemlist.action")
//    public void itemList(Model model,HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
//        //从Mysql中查询
//        List<Items> list = itemService.selectItemsList();
//        model.addAttribute("itemList", list);
//        request.getRequestDispatcher("itemList").forward(request,response);//内部转发 ajax请求的时候
////        return "itemList";
//    }

    //推荐使用 返回视图路径 model 带数据 视图
    @RequestMapping(value = "/item/itemlistview.action")
    public String ItemListView(Model model) throws MessageException {
//        Integer i = 1 / 0;//设置一个异常，用于测试异常类调用（未知异常）

        //MYSQL 中查询
        List<Items> list = itemService.selectItemsList();
        if (null == list || list.size() < 10) {
//            throw new RuntimeException("商品信息不能为空或者少于10条");
            throw new MessageException("商品信息不能为空");
        }
        model.addAttribute("itemList", list);

        return "itemList";
    }

    //去修改页面 入参 id
    @RequestMapping(value = "/itemEdit.action")
//	public ModelAndView toEdit(@RequestParam(value = "id",required = false,defaultValue = "1") Integer id,
    public ModelAndView toEdit(Integer id,
                               HttpServletRequest request, HttpServletResponse response
            , HttpSession session, Model model) {

        //Servlet时代开发
//		String id = request.getParameter("id");

        //查询一个商品
//		Items items = itemService.selectItemsById(Integer.parseInt(id));
        Items items = itemService.selectItemsById(id);

        ModelAndView mav = new ModelAndView();
        //数据
        mav.addObject("item", items);
        mav.setViewName("editItem");
        return mav;

    }
    //提交修改页面 入参  为 Items对象
//	@RequestMapping(value = "/updateitem.action")
//	public ModelAndView updateitem(Items items){
//		//修改
//		System.out.println("======= Items items =======");
//		System.out.println(items.toString());
//
//		itemService.updateItemsById(items);
//		ModelAndView modelAndView=new ModelAndView();
//		modelAndView.setViewName("success");
//		return modelAndView;
//	}

    //提交修改页面 用包装的pojo处理
//    @RequestMapping(value = "/updateitem.action")//由映射器
//    public ModelAndView updateitem(QueryVo vo) {// 由适配器处理
//        //修改
//        System.out.println("======= vo.getItems() =======");
//        System.out.println(vo.getItems());
//        itemService.updateItemsById(vo.getItems());
//
//        ModelAndView mav = new ModelAndView();
//        mav.setViewName("success");
//        return mav;
//
//    }
    // 数据修改后， 重定向回列表页面
    @RequestMapping(value = "/updateitem.action")//由映射器
    public String updateitem(QueryVo vo, MultipartFile pictureFile) throws Exception {// 由适配器处理
        //修改
        System.out.println("======= vo.getItems() =======");
        System.out.println(vo.getItems());

        //保存图片 到 D:\Desktop\image
        String file_root = "D:\\Desktop\\image\\";
        String file_name = UUID.randomUUID().toString().replaceAll("-", "");

        //FilenameUtils 需要jar包  WEB-INF/lib/commons-fileupload-1.2.2.jar  WEB-INF/lib/commons-io-2.4.jar
        String ext = FilenameUtils.getExtension(pictureFile.getOriginalFilename());
        pictureFile.transferTo(new File(file_root + file_name + "." + ext));

        //设置图片路径， 相对路径即可
        vo.getItems().setPic(file_name + "." + ext);

        //修改
        itemService.updateItemsById(vo.getItems());

        ModelAndView mav = new ModelAndView();
        mav.setViewName("success");

        return "redirect:/item/itemlist.action?id=" + vo.getItems().getId();//重定向
//        return "forward:/item/itemlist.action";//内部转发（浏览器的url还是/updateitem.action ，而内容/item/itemlist.action ，刷新页面的又会发起/updateitem.action的请求）
    }

    //
//	@RequestMapping(value = "/deletes.action")
//	public ModelAndView deletes(Integer[] ids){
//		System.out.println(ids);
//
//		ModelAndView modelAndView=new ModelAndView();
//		modelAndView.setViewName("success");
//		return modelAndView;
//	}

    @RequestMapping(value = "/deletes.action")
    public ModelAndView deletes(QueryVo vo) {
        System.out.println(vo.getIds());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("success");
        return modelAndView;
    }

    @RequestMapping(value = "/updates.action")
    public ModelAndView updates(QueryVo vo) {
        System.out.println(vo.getItemsList());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("success");
        return modelAndView;
    }

    //json
    @RequestMapping(value = "/json.action")
    public @ResponseBody Items json(@RequestBody Items items) {

        System.out.println("======= post data=====");
        System.out.println(items);
        return items;
    }

    //去登录页面
    @RequestMapping(value = "login.action",method = RequestMethod.GET)
    public String login(){

        return "login";
    }

    @RequestMapping(value = "login.action",method = RequestMethod.POST)
    public String login(String username,HttpSession httpSession){
        //设置session
        httpSession.setAttribute("USER_SESSION",username);
        return "redirect:/item/itemlist.action";//重定向
    }

    @RequestMapping(value = "logout.action",method = RequestMethod.GET)
    public String logout(HttpSession httpSession){
        httpSession.removeAttribute("USER_SESSION");
        return "redirect:/item/itemlist.action";//重定向
    }


}
