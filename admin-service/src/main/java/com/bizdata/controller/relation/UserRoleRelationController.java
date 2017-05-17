package com.bizdata.controller.relation;

import com.bizdata.result.ResultStateUtil;
import com.bizdata.result.ResultStateVO;
import com.bizdata.service.UserRoleRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户角色关系Controller
 * <p>
 * Created by sdevil507 on 2017/4/16.
 */
@RestController
public class UserRoleRelationController {

    @Autowired
    private UserRoleRelationService userRoleRelationService;

    /**
     * 建立角色用户关系
     *
     * @param roleID  角色ID
     * @param userIDs 用户ID列表
     *                return ResultStateVO
     */
//    @RequestMapping(value = "/userRoleRelation/build", method = RequestMethod.POST)
//    public ResultStateVO build(String roleID, String[] userIDs) {
//        ResultStateVO resultStateVO;
//        if (userRoleRelationService.build(roleID, userIDs)) {
//            resultStateVO = ResultStateUtil.create(0, "角色绑定成功!");
//        } else {
//            resultStateVO = ResultStateUtil.create(1, "角色绑定失败!");
//        }
//        return resultStateVO;
//    }

    /**
     * 新增用户角色关系
     *
     * @param roleID  角色ID
     * @param userIDs 用户ID列表
     * @return ResultStateVO
     */
    @RequestMapping(value = "/userRoleRelation/save", method = RequestMethod.POST)
    public ResultStateVO save(String roleID, String[] userIDs) {
        ResultStateVO resultStateVO;
        if (userRoleRelationService.save(roleID, userIDs)) {
            resultStateVO = ResultStateUtil.create(0, "用户角色关系新增成功!");
        } else {
            resultStateVO = ResultStateUtil.create(1, "用户角色关系新增失败!");
        }
        return resultStateVO;
    }

    /**
     * 删除用户角色关系
     *
     * @param roleID  角色ID
     * @param userIDs 用户ID列表
     * @return ResultStateVO
     */
    @RequestMapping(value = "/userRoleRelation/delete", method = RequestMethod.POST)
    public ResultStateVO delete(String roleID, String[] userIDs) {
        ResultStateVO resultStateVO;
        if (userRoleRelationService.delete(roleID, userIDs)) {
            resultStateVO = ResultStateUtil.create(0, "用户角色关系解除成功!");
        } else {
            resultStateVO = ResultStateUtil.create(1, "用户角色关系解除失败!");
        }
        return resultStateVO;
    }

}
