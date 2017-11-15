package com.sft.service.impl;

import com.sft.dao.MaintainDao;
import com.sft.model.MaintainModel;
import com.sft.model.SubMaintainModel;
import com.sft.service.MaintainService;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class MaintainServiceImpl implements MaintainService {

    @Resource
    private MaintainDao maintainDao;

    public List<MaintainModel> getAll() {
        return maintainDao.getAll();
    }

    public List<MaintainModel> getMaintain(String userId) {
        return maintainDao.getMaintain(userId);
    }

    public List<SubMaintainModel> getSubMaintain(String userId, String id) {
        return maintainDao.getSubMaintain(userId,id);
    }
}
