package com.bizdata.controller.relation;

import com.bizdata.result.ResultStateUtil;
import com.bizdata.result.ResultStateVO;
import com.bizdata.service.RoleResourceRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 角色资源关系Controller
 * <p>
 * Created by sdevil507 on 2017/5/17.
 */
@RestController
public class RoleResourceRelationController {

    private final RoleResourceRelationService roleResourceRelationService;

    @Autowired
    public RoleResourceRelationController(RoleResourceRelationService roleResourceRelationService) {
        this.roleResourceRelationService = roleResourceRelationService;
    }

    /**
     * 角色资源关系绑定
     *
     * @param roleID      角色ID
     * @param resourceIDs 资源ID列表
     * @return ResultStateVO
     */
    @RequestMapping(value = "/roleResourceRelation/build", method = RequestMethod.POST)
    public ResultStateVO build(String roleID, String[] resourceIDs) {
        ResultStateVO resultStateVO;
        if (roleResourceRelationService.build(roleID, resourceIDs)) {
            resultStateVO = ResultStateUtil.create(0, "角色资源关系绑定成功!");
        } else {
            resultStateVO = ResultStateUtil.create(1, "角色资源关系绑定失败!");
        }
        return resultStateVO;
    }
}
