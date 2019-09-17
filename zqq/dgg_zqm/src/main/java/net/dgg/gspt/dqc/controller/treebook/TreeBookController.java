package net.dgg.gspt.dqc.controller.treebook;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.dgg.gspt.dqc.entity.business.treebook.TreeBook;
import net.dgg.gspt.dqc.services.TreeBookService;
import net.dgg.tmd.foundation.platform.annotation.AuthOpt;
import net.dgg.tmd.foundation.platform.common.util.LayuiTableResponse;
import net.dgg.tmd.foundation.platform.session.CommonSession;
import net.dgg.tmd.foundation.platform.session.SessionManager;
import net.dgg.tmd.foundation.platform.user.entity.UserEntity;
import net.dgg.tmd.foundation.platform.user.service.UserManager;
import net.fblock.web.common.BaseController;
import net.fblock.web.common.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: TreeBookController
 * @Description: 数据字典管理
 * @Auther: 汤宏
 * @Date: 2018/04/26 17:22
 */
@Controller
@RequestMapping("/tree_book")
public class TreeBookController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TreeBookService treeBookService;

    @Autowired
    private SessionManager sessionManager;

    @Autowired
    private UserManager userManager;

    /**
     * treebook首页
     *
     * @return
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "/tree_book/index";
    }

    /**
     * 根据pid获取子节点
     *
     * @param id 接收到的id作为父级节点的pid条件进行查询，不可为空
     * @return 返回当前节点的下级子集合
     */
    @RequestMapping(value = "tree_book_nodes")
    @ResponseBody
    public RestResponse<List<Map<String, Object>>> getTreeBookNodesByPid(@RequestParam Long id) {
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>(); //返回结果集
        List<TreeBook> result = treeBookService.getTreeBooksByPid(id); //根据id查询下级节点
        Map<String, Object> map = null;
        for (TreeBook treeBook : result) {
            map = new HashMap<String, Object>();
            map.put("id", String.valueOf(treeBook.getId()));
            map.put("pid", String.valueOf(treeBook.getPid()));
            map.put("name", treeBook.getName());
            map.put("open", false);
            //判断该节点下是否还有子节点
            map.put("isParent", treeBookService.isParent(treeBook.getId()));
            resultList.add(map);
        }
        return this.getSuccessResponse(resultList);
    }

    /**
     * 根据查询条件获取子节点
     *
     * @param params {id:字典id,code:字典编码,name:字典名称}
     * @return
     */
    @RequestMapping(value = "table_search_books")
    @ResponseBody
    public LayuiTableResponse tableSearch(@RequestParam Map<String, String> params) {
        List<TreeBook> result = treeBookService.getTreeBooksByConditionWithPage(params);
        return new LayuiTableResponse().data(params.get("count"), result);
    }

    /**
     * 根据ID查询单条字典
     *
     * @param id 数据字典id
     * @return
     */
    @RequestMapping(value = "one_book")
    @ResponseBody
    public RestResponse<TreeBook> getTreeBookById(Long id) {
        return this.getSuccessResponse(treeBookService.getTreeBookById(id));
    }

    /**
     * 新增字典
     *
     * @param treeBook 前端传过来的数据字典对象
     * @return
     */
    @RequestMapping(value = "save_tree_book")
    @ResponseBody
    public RestResponse<Map<String, Object>> addTreeBook(TreeBook treeBook) {
        try {

            CommonSession session = sessionManager.getCurrentSession();

            UserEntity user = this.getUserFromSession();
            TreeBook book = null;
            if (null != treeBook.getId()) {
                book = treeBookService.updateTreeBook(treeBook, user.getUserId(), user.getRealName() + user.getLoginName());
            } else {
                book = treeBookService.addTreeBook(treeBook, user.getUserId(), user.getRealName() + user.getLoginName());
            }
            //返回当前节点信息
            Map<String, Object> mm = new HashMap<String, Object>();
            mm.put("id", book.getId());
            mm.put("pid", book.getPid());
            mm.put("name", book.getName());
            mm.put("open", false);
            mm.put("isParent", false);
            return this.getSuccessResponse(mm);
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse(e.getMessage());
        }

    }

    /**
     * 删除选择的菜单
     *
     * @param bookIds 菜单的id集合，不可为空
     * @return
     */
    @RequestMapping(value = "delete_tree_books")
    @ResponseBody
    @AuthOpt(code = "treebook-delete")
    public RestResponse<String> deleteTreeBooks(String bookIds) {
        if (StringUtils.isEmpty(bookIds)) {
            return this.getFailResponse("请选择需要删除的菜单");
        }
        try {
            String[] bookIdsArray = bookIds.split(",");
            List<Long> bookIdList = new ArrayList<>();
            for (String bookId : bookIdsArray) {
                bookIdList.add(Long.valueOf(bookId));
            }
            treeBookService.deleteTreeBooks(bookIdList);
        } catch (Exception e) {
            logger.error("删除字典数据异常", e);
            return this.getFailResponse("删除字典数据异常");
        }
        return this.getSuccessResponse("删除成功");
    }

    /**
     * 移动字典
     *
     * @param id  字典ID
     * @param pid 目标父级ID
     * @return
     */
    @RequestMapping(value = "tree_book_drop")
    @ResponseBody
    @AuthOpt(code = "tree_book_drop")
    public RestResponse<String> treeBookDrop(Long id, Long pid) {
        if (StringUtils.isEmpty(id)) {
            return this.getFailResponse("请选择需要移动的字典");
        }
        if (StringUtils.isEmpty(pid)) {
            return this.getFailResponse("必须指定父级");
        }
        try {
            treeBookService.updateTreebookDrop(id, pid);
        } catch (Exception e) {
            logger.error("移动字典数据异常", e);
            return this.getFailResponse(e.getMessage());
        }
        return this.getSuccessResponse("移动成功");
    }

    /**
     * 刷新缓存
     *
     * @return
     */
    @RequestMapping(value = "update_tree_book_cache")
    @ResponseBody
    public RestResponse<Object> updateTreeBookCache() {
        treeBookService.updateTreeBookCache();
        return this.buildResponse(RestResponse.SUCCESS_CODE, "刷新成功", null);
    }


    /**
     * 获取user
     *
     * @return
     */
    private UserEntity getUserFromSession() {
        CommonSession session = this.sessionManager.getCurrentSession();
        Long userId = session == null ? null : session.getValue("userId", Long.class);
        UserEntity user = userId == null ? null : this.userManager.findUserById(userId);
        return user;
    }

    /**
     * 查询多个字典名称接口
     *
     * @param codes 编码
     * @return
     **/
    @RequestMapping(value = "/get_tree_book_list_multi.do", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "查询多个字典名称接口", notes = "查询类型说明<br/> 1：根据分类编码查询下级第一级分类（用于Select选择）<br/> 2：根据分类编码查询所有下级分类（用于Tree选择无父级的）<br/> 3：根据分类编码查询所有下级分类包含自己（用于Tree选择有父级的）", httpMethod = "GET")
    public RestResponse<Map> getTreeBookListMulti(
            @ApiParam(name = "codes", value = "编码(多个英文逗号分割)", required = true) @RequestParam String codes,
            @ApiParam(name = "status", value = "状态(1:启用/0:禁用/无:所有)") @RequestParam(required = false) Integer status,
            @ApiParam(name = "type", value = "查询类型", required = true) @RequestParam Integer type,
            @ApiParam(name = "level", value = "查询深度") @RequestParam(required = false) Integer level) {
        try {
            Map reMap = new HashMap();
            String[] codStr = codes.split(",");
            for (int i = 0; i < codStr.length; i++) {
                String code = codStr[i];
                List<TreeBook> resultList = treeBookService.getTreeBookListByCode(code, status, type, level);
                reMap.put(code, resultList);
            }
            return this.getSuccessResponse(reMap);
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse(e.getMessage());
        }
    }

    /**
     * 查询字典接口
     *
     * @param code   编码
     * @param status 状态 1：启用，0：禁用，无：所有
     * @param type   1：根据分类编码查询下级第一级分类（用于Select选择）
     *               2：根据分类编码查询所有下级分类（用于Tree选择无父级的）
     *               3：根据分类编码查询所有下级分类包含自己（用于Tree选择有父级的）
     * @param level  查询深度，如（1，2，3）表示查询到下面第几级
     * @return 字典集合
     */
    @RequestMapping(value = "/get_tree_book_list.do", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "查询字典集合接口", notes = "查询类型说明<br/> 1：根据分类编码查询下级第一级分类（用于Select选择）<br/> 2：根据分类编码查询所有下级分类（用于Tree选择无父级的）<br/> 3：根据分类编码查询所有下级分类包含自己（用于Tree选择有父级的）", httpMethod = "POST")
    public RestResponse<List<TreeBook>> getTreeBookList(
            @ApiParam(name = "code", value = "编码", required = true) @RequestParam String code,
            @ApiParam(name = "status", value = "状态(1:启用/0:禁用/无:所有)") @RequestParam(required = false) Integer status,
            @ApiParam(name = "type", value = "查询类型", required = true) @RequestParam Integer type,
            @ApiParam(name = "level", value = "查询深度") @RequestParam(required = false) Integer level) {
        try {
            List<TreeBook> resultList = treeBookService.getTreeBookListByCode(code, status, type, level);
            return this.getSuccessResponse(resultList);
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse(e.getMessage());
        }
    }

}
