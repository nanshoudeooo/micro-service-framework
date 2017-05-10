package com.bizdata.service.impl;

import com.bizdata.dao.OrganizationDao;
import com.bizdata.po.Organization;
import com.bizdata.extend.BeanCopyUtil;
import com.bizdata.result.ResultStateUtil;
import com.bizdata.result.ResultStateVO;
import com.bizdata.service.OrganizationService;
import com.bizdata.vo.organization.CreateParamVO;
import com.bizdata.vo.organization.DeleteParamVO;
import com.bizdata.vo.organization.ReadByIDParamVO;
import com.bizdata.vo.organization.UpdateParamVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 组织机构Service实现
 * <p>
 * Created by sdevil507 on 2017/5/9.
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrganizationDao organizationDao;

    @Override
    public ResultStateVO create(CreateParamVO createParamVO) {
        ResultStateVO resultStateVO;
        try {
            Organization organization = new Organization();
            BeanUtils.copyProperties(createParamVO, organization);
            //判断名称是否重复
            if (null != organizationDao.findByName(organization.getName())) {
                //如果不重复执行插入
                organizationDao.save(organization);
                resultStateVO = ResultStateUtil.create(0, "组织机构新增成功!");
            } else {
                resultStateVO = ResultStateUtil.create(1, "组织机构名称不可重复!");
            }
        } catch (Exception e) {
            logger.error("组织机构新增失败!", e);
            resultStateVO = ResultStateUtil.create(2, "组织机构新增失败!");
        }
        return resultStateVO;
    }

    @Override
    public ResultStateVO readByID(ReadByIDParamVO readByIDParamVO) {
        ResultStateVO resultStateVO;
        try {
            Organization organization = organizationDao.findOne(readByIDParamVO.getId());
            resultStateVO = ResultStateUtil.create(0, "根据ID查询组织机构成功!");
        } catch (Exception e) {
            logger.error("根据ID查询组织机构失败!", e);
            resultStateVO = ResultStateUtil.create(1, "根据ID查询组织机构失败!");
        }
        return resultStateVO;
    }

    @Override
    public ResultStateVO readAll() {
        ResultStateVO resultStateVO;
        try {
            List<Organization> organizations = organizationDao.findAll();
            resultStateVO = ResultStateUtil.create(0, "查询组织结构列表成功!");
        } catch (Exception e) {
            logger.error("查询组织机构列表失败!", e);
            resultStateVO = ResultStateUtil.create(1, "查询组织机构列表失败!");
        }
        return resultStateVO;
    }

    @Override
    public ResultStateVO update(UpdateParamVO updateParamVO) {
        ResultStateVO resultStateVO;
        try {
            Organization organization = new Organization();
            BeanCopyUtil.copyProperties(updateParamVO, organization);
            organizationDao.save(organization);
            resultStateVO = ResultStateUtil.create(0, "组织机构更新成功!");
        } catch (Exception e) {
            logger.error("组织机构更新失败!", e);
            resultStateVO = ResultStateUtil.create(1, "组织机构更新失败!");
        }
        return resultStateVO;
    }

    @Override
    public ResultStateVO delete(DeleteParamVO deleteParamVO) {
        ResultStateVO resultStateVO;
        try {
            organizationDao.delete(deleteParamVO.getId());
            resultStateVO = ResultStateUtil.create(0, "组织机构删除成功!");
        } catch (Exception e) {
            logger.error("组织机构删除失败!", e);
            resultStateVO = ResultStateUtil.create(1, "组织机构删除失败!");
        }
        return resultStateVO;
    }
}
