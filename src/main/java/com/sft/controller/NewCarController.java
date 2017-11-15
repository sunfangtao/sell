package com.sft.controller;

import com.sft.model.CountModel;
import com.sft.model.NewCarModel;
import com.sft.service.NewCarService;
import com.sft.util.PagingUtil;
import com.sft.util.Params;
import com.sft.util.SendAppJSONUtil;
import org.apache.log4j.Logger;
import org.apache.shiro.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("newCar")
public class NewCarController {

    private Logger logger = Logger.getLogger(NewCarController.class);

    @Resource
    private NewCarService newCarService;

    @RequestMapping("query")
    public void queryList(HttpServletRequest req, HttpServletResponse res) {
        try {
            String resultJson = null;
            int page = PagingUtil.getPage(req);
            int pageSize = PagingUtil.getPageSize(req);
            String userId = req.getParameter("userId");
            if (StringUtils.hasText(userId)) {
                CountModel count = new CountModel();
                List<NewCarModel> newCarList = newCarService.queryList(count, userId, page, pageSize);
                if (newCarList == null || newCarList.size() == 0) {
                    resultJson = SendAppJSONUtil.getNullResultObject();
                } else {
                    resultJson = SendAppJSONUtil.getPageJsonString(page, pageSize, count.getCount(), newCarList);
                }
            } else {
                resultJson = SendAppJSONUtil.getRequireParamsMissingObject("请上传用户Id!");
            }
            res.getWriter().write(resultJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取新车详情
     *
     * @param id
     * @param res
     */
    @RequestMapping("detail")
    public void getDetail(String id, HttpServletResponse res) {
        try {
            String resultJson = null;
            if (StringUtils.hasText(id)) {
                NewCarModel newCarModel = newCarService.getDetail(id);
                if (newCarModel == null) {
                    resultJson = SendAppJSONUtil.getFailResultObject(Params.ReasonEnum.NODATA.getValue(), "ID不存在!");
                } else {
                    resultJson = SendAppJSONUtil.getNormalString(newCarModel);
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
