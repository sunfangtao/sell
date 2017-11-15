package com.sft.dao;

import com.sft.model.CountModel;
import com.sft.model.SellCarModel;

import java.util.List;

public interface SellCarDao {

    /**
     * 添加卖车信息
     *
     * @return
     */
    public boolean addSell(SellCarModel sellCarModel);

    /**
     * 修改卖车信息
     *
     * @param sellCarModel
     * @return
     */
    public boolean updateSell(SellCarModel sellCarModel);

    /**
     * 获取卖车详情
     *
     * @param id
     * @return
     */
    public SellCarModel getDetail(String id);

    /**
     * 条件查询
     *
     * @return
     */
    public List<SellCarModel> queryList(CountModel countModel, String sortType, SellCarModel sellCarModel, String priceRange, int page, int pageSize);
}
