package com.sft.service.impl;

import com.sft.dao.NewCarDao;
import com.sft.model.CountModel;
import com.sft.model.NewCarModel;
import com.sft.service.NewCarService;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class NewCarServiceImpl implements NewCarService {

    @Resource
    private NewCarDao newCarDao;

    public NewCarModel getDetail(String id) {
        return newCarDao.getDetail(id);
    }

    public List<NewCarModel> queryList(CountModel count, String userId, int page, int pageSize) {
        return newCarDao.queryList(count,userId, page, pageSize);
    }
}
