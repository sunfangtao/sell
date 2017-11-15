package com.sft.service.impl;

import com.sft.dao.SellCarDao;
import com.sft.model.CountModel;
import com.sft.model.SellCarModel;
import com.sft.service.SellCarService;
import com.sft.util.DateUtil;
import com.sft.util.Params;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class SellCarServiceImpl implements SellCarService {

    @Resource
    private SellCarDao sellCarDao;

    public boolean addSell(SellCarModel sellCarModel) {
        sellCarModel.setCreatedate(DateUtil.getCurDate());
        sellCarModel.setUserType(Params.UserType.APP.getValue() + "");
        return sellCarDao.addSell(sellCarModel);
    }

    public boolean updateSell(SellCarModel sellCarModel) {
        return sellCarDao.addSell(sellCarModel);
    }

    public SellCarModel getDetail(String id) {
        return sellCarDao.getDetail(id);
    }

    public List<SellCarModel> queryList(CountModel countModel, String sortType, SellCarModel sellCarModel, String priceRange, int page, int pageSize) {
        return sellCarDao.queryList(countModel,sortType, sellCarModel, priceRange, page, pageSize);
    }
}
