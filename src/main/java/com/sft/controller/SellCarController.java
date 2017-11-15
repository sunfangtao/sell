package com.sft.controller;

import com.sft.model.CountModel;
import com.sft.model.SellCarModel;
import com.sft.service.CodeService;
import com.sft.service.PicService;
import com.sft.service.SellCarService;
import com.sft.util.*;
import org.apache.log4j.Logger;
import org.apache.shiro.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.locks.Lock;

@Controller
@RequestMapping("sell")
public class SellCarController {

    private Logger logger = Logger.getLogger(SellCarController.class);

    @Resource
    private SellCarService sellCarService;
    @Resource
    private PicService picService;
    @Resource
    private CodeService codeService;

    @RequestMapping("add")
    public void addSellCar(String[] picList, SellCarModel sellCarModel, HttpServletRequest req, HttpServletResponse res) {
        try {
            String resultJson = null;
            // 验证手机验证码
            if ((resultJson = codeService.verifyCodeByPhone(sellCarModel.getMobile(), req.getParameter("code"))) != null) {
                res.getWriter().write(SendAppJSONUtil.getFailResultObject(Params.ReasonEnum.NODATA.getValue(), resultJson));
                return;
            }
            sellCarModel.setId(UUID.randomUUID().toString());
            sellCarModel.setUserId(SecurityUtil.getUserId(req));
            if (sellCarService.addSell(sellCarModel)) {
                if (picList != null && picList.length > 0)
                    picService.addPic(sellCarModel.getId(), Arrays.asList(picList));
                resultJson = SendAppJSONUtil.getNormalString("添加成功!");
            } else {
                resultJson = SendAppJSONUtil.getFailResultObject(Params.ReasonEnum.SQLEXCEPTION.getValue(), "添加失败!");
            }
            res.getWriter().write(resultJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("delete")
    public void deleteSell(String id, String userId, HttpServletRequest req, HttpServletResponse res) {
        Lock lock = MtbhLock.getLock(id);
        String resultJson = null;
        try {
            if (lock.tryLock()) {
                SellCarModel sellCarModel = sellCarService.getDetail(id);
                if (sellCarModel != null && sellCarModel.getUserId().equals(userId)) {
                    sellCarModel.setStatus(Params.ReleaseStatus.RELEASE_CANCLE.getValue());
                    if (sellCarService.updateSell(sellCarModel)) {
                        resultJson = SendAppJSONUtil.getNormalString("下架成功！");
                    } else {
                        resultJson = SendAppJSONUtil.getFailResultObject(Params.ReasonEnum.SQLEXCEPTION.getValue(), " 下架失败！");
                    }
                } else {
                    resultJson = SendAppJSONUtil.getFailResultObject(Params.ReasonEnum.PERMISSION.getValue(), "非法删除!");
                }
            } else {
                resultJson = SendAppJSONUtil.getFailResultObject(Params.ReasonEnum.REPEAT.getValue(), "当前正忙，请稍后再试!");
            }
            res.getWriter().write(resultJson);
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }
    }

    @RequestMapping("update")
    public void updateSell(SellCarModel sellCarModel, HttpServletRequest req, HttpServletResponse res) {
        Lock lock = MtbhLock.getLock(sellCarModel.getId());
        String resultJson = null;
        try {
            if (lock.tryLock()) {
                if (sellCarService.updateSell(sellCarModel)) {

                } else {

                }
            } else {
                resultJson = SendAppJSONUtil.getFailResultObject(Params.ReasonEnum.REPEAT.getValue(), "当前正忙，请稍后再试!");
            }
        } catch (Exception e) {

        }
    }

    @RequestMapping("query")
    public void queryList(SellCarModel sellCarModel, String priceRange, String sortType, HttpServletRequest req, HttpServletResponse res) {
        try {
            String resultJson = null;
            int page = PagingUtil.getPage(req);
            int pageSize = PagingUtil.getPageSize(req);
            CountModel countModel = new CountModel();
            List<SellCarModel> list = sellCarService.queryList(countModel, sortType, sellCarModel, priceRange, page, pageSize);
            if (list == null || list.size() == 0) {
                resultJson = SendAppJSONUtil.getNullResultObject();
            } else {
                resultJson = SendAppJSONUtil.getPageJsonString(page, pageSize, countModel.getCount(), list);
            }
            res.getWriter().write(resultJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param sellCarModel
     * @param priceRange
     * @param sortType
     * @param req
     * @param res
     */
    @RequestMapping("queryList")
    public void querySellList(SellCarModel sellCarModel, String priceRange, String sortType, HttpServletRequest req, HttpServletResponse res) {
        queryList(sellCarModel, priceRange, sortType, req, res);
    }

    /**
     * 获取卖车详情
     *
     * @param id
     * @param res
     */
    @RequestMapping("detail")
    public void getDetail(String id, HttpServletResponse res) {
        try {
            String resultJson = null;
            if (StringUtils.hasText(id)) {
                SellCarModel sellCarModel = sellCarService.getDetail(id);
                if (sellCarModel == null) {
                    resultJson = SendAppJSONUtil.getFailResultObject(Params.ReasonEnum.NODATA.getValue(), "ID不存在!");
                } else {
                    resultJson = SendAppJSONUtil.getNormalString(sellCarModel);
                }
            } else {
                resultJson = SendAppJSONUtil.getRequireParamsMissingObject("请上传ID!");
            }
            res.getWriter().write(resultJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
