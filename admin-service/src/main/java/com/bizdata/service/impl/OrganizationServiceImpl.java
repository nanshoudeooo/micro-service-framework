package com.bizdata.service.impl;

import com.bizdata.controller.organization.vo.in.InDeleteVO;
import com.bizdata.controller.organization.vo.in.InGetByIdVO;
import com.bizdata.controller.organization.vo.in.InSaveVO;
import com.bizdata.controller.organization.vo.in.InUpdateVO;
import com.bizdata.controller.organization.vo.out.OutOrganizationVO;
import com.bizdata.dao.OrganizationDao;
import com.bizdata.extend.BeanCopyUtil;
import com.bizdata.jpa.vo.JpaListPO2VO;
import com.bizdata.po.Organization;
import com.bizdata.service.OrganizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
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
    public boolean save(InSaveVO inSaveVO) {
        boolean state;
        Organization organization = new Organization();
        try {
            BeanUtils.copyProperties(inSaveVO, organization);
            organizationDao.save(organization);
            state = true;
        } catch (Exception e) {
            logger.error("组织机构新增失败!", e);
            state = false;
        }
        return state;
    }

    @Override
    public OutOrganizationVO getByID(InGetByIdVO inGetByIdVO) {
        OutOrganizationVO outOrganizationVO;
        try {
            outOrganizationVO = new OutOrganizationVO();
            Organization organization = organizationDao.findOne(inGetByIdVO.getId());
            BeanUtils.copyProperties(organization, outOrganizationVO);
            outOrganizationVO.setParentName(organizationDao.findOne(organization.getParent()).getName());
        } catch (Exception e) {
            outOrganizationVO = null;
            logger.error("根据ID查询组织机构失败!", e);
        }
        return outOrganizationVO;
    }

    @Override
    public List<OutOrganizationVO> list() {
        List<OutOrganizationVO> outOrganizationVOs = null;
        try {
            List<Organization> organizations = organizationDao.findAll();
            outOrganizationVOs = JpaListPO2VO.convert(organizations, OutOrganizationVO.class);
        } catch (Exception e) {
            logger.error("查询组织机构列表失败!", e);
        }
        return outOrganizationVOs;
    }

    @Override
    public boolean update(InUpdateVO inUpdateVO) {
        boolean state;
        try {
            Organization organization = new Organization();
            BeanCopyUtil.copyProperties(inUpdateVO, organization);
            organizationDao.save(organization);
            state = true;
        } catch (Exception e) {
            logger.error("更新组织机构失败!", e);
            state = false;
        }
        return state;
    }

    @Override
    public boolean delete(InDeleteVO inDeleteVO) {
        boolean state;
        try {
            organizationDao.delete(inDeleteVO.getId());
            state = true;
        } catch (Exception e) {
            logger.error("组织机构删除失败", e);
            state = true;
        }
        return state;
    }
}
