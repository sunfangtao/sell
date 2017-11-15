package com.sft.dao.impl;

import com.sft.dao.SellCarDao;
import com.sft.db.SqlConnectionFactory;
import com.sft.model.CountModel;
import com.sft.model.SellCarModel;
import com.sft.util.Params;
import org.apache.shiro.util.StringUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SellCarDaoImpl implements SellCarDao {

    @Resource
    private SqlConnectionFactory sqlConnectionFactory;

    public boolean addSell(SellCarModel sellCarModel) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        StringBuffer sb = new StringBuffer();
        sb.append("insert into sell_car (id,mobile,mileage,price_tax,sell_price,brand,car_license,displacement,location,gearbox," +
                "discharge_standard,type,user_id,photo,createdate,remark,frontImg,behindImg,sideImg) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

        try {
            con = sqlConnectionFactory.getConnection();
            ps = con.prepareStatement(sb.toString());
            ps.setString(1, sellCarModel.getId());
            ps.setString(2, sellCarModel.getMobile());
            ps.setString(3, sellCarModel.getMileage());
            ps.setDouble(4, sellCarModel.getPriceTax());
            ps.setDouble(5, sellCarModel.getSellPrice());
            ps.setString(6, sellCarModel.getBrand());
            ps.setString(7, sellCarModel.getCarLicense());
            ps.setString(8, sellCarModel.getDisplacement());
            ps.setString(9, sellCarModel.getLocation());
            ps.setString(10, sellCarModel.getGearbox());
            ps.setString(11, sellCarModel.getDischargeStandard());
            ps.setString(12, sellCarModel.getUserType());
            ps.setString(13, sellCarModel.getUserId());
            ps.setString(14, sellCarModel.getPhoto());
            ps.setString(15, sellCarModel.getCreatedate());
            ps.setString(16, sellCarModel.getRemark());
            ps.setString(17, sellCarModel.getFrontImg());
            ps.setString(18, sellCarModel.getBehindImg());
            ps.setString(19, sellCarModel.getSideImg());

            int result = ps.executeUpdate();
            if (result > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlConnectionFactory.closeConnetion(con, ps, rs);
        }
        return false;
    }

    public boolean updateSell(SellCarModel sellCarModel) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        StringBuffer sb = new StringBuffer();
        sb.append("update sell_car set ");
        if (StringUtils.hasText(sellCarModel.getMobile())) {
            sb.append("mobile='").append(sellCarModel.getMobile()).append("'");
        }
        if (StringUtils.hasText(sellCarModel.getMileage())) {
            sb.append("mileage='").append(sellCarModel.getMileage()).append("'");
        }
        if (sellCarModel.getPriceTax() > 0) {
            sb.append("price_tax=").append(sellCarModel.getPriceTax());
        }
        if (sellCarModel.getSellPrice() > 0) {
            sb.append("sell_price=").append(sellCarModel.getSellPrice());
        }
        if (StringUtils.hasText(sellCarModel.getBrand())) {
            sb.append("brand='").append(sellCarModel.getBrand()).append("'");
        }
        if (StringUtils.hasText(sellCarModel.getCarLicense())) {
            sb.append("car_license='").append(sellCarModel.getCarLicense()).append("'");
        }
        if (StringUtils.hasText(sellCarModel.getDisplacement())) {
            sb.append("displacement='").append(sellCarModel.getDisplacement()).append("'");
        }
        if (StringUtils.hasText(sellCarModel.getLocation())) {
            sb.append("location='").append(sellCarModel.getLocation()).append("'");
        }
        if (StringUtils.hasText(sellCarModel.getGearbox())) {
            sb.append("gearbox='").append(sellCarModel.getGearbox()).append("'");
        }
        if (StringUtils.hasText(sellCarModel.getDischargeStandard())) {
            sb.append("discharge_standard='").append(sellCarModel.getDischargeStandard()).append("'");
        }
        if (StringUtils.hasText(sellCarModel.getPhoto())) {
            sb.append("photo='").append(sellCarModel.getPhoto()).append("'");
        }
        if (StringUtils.hasText(sellCarModel.getRemark())) {
            sb.append("remark='").append(sellCarModel.getRemark()).append("'");
        }
        if (StringUtils.hasText(sellCarModel.getFrontImg())) {
            sb.append("frontImg='").append(sellCarModel.getFrontImg()).append("'");
        }
        if (StringUtils.hasText(sellCarModel.getBehindImg())) {
            sb.append("behindImg='").append(sellCarModel.getBehindImg()).append("'");
        }
        if (StringUtils.hasText(sellCarModel.getSideImg())) {
            sb.append("sideImg='").append(sellCarModel.getSideImg()).append("'");
        }
        sb.append(" where id = '").append(sellCarModel.getId()).append("' and user_id = '").append(sellCarModel.getUserId()).append("'");

        try {
            con = sqlConnectionFactory.getConnection();
            ps = con.prepareStatement(sb.toString());

            int result = ps.executeUpdate();
            if (result > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlConnectionFactory.closeConnetion(con, ps, rs);
        }
        return false;
    }

    public SellCarModel getDetail(String id) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        SellCarModel sellCarModel = null;
        StringBuffer sb = new StringBuffer();
        sb.append("select * from sell_car where id = ?");

        try {
            con = sqlConnectionFactory.getConnection();
            ps = con.prepareStatement(sb.toString());
            ps.setString(1, id);

            rs = ps.executeQuery();
            while (rs.next()) {
                sellCarModel = setValue(rs);
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlConnectionFactory.closeConnetion(con, ps, rs);
        }
        return sellCarModel;
    }

    public List<SellCarModel> queryList(CountModel countModel, String sortType, SellCarModel sellCarModel, String priceRange, int page, int pageSize) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<SellCarModel> sellCarModelList = new ArrayList<SellCarModel>();
        StringBuffer sb = new StringBuffer();
        sb.append("select * from sell_car where 1 = 1");
        if (StringUtils.hasText(sellCarModel.getUserId())) {
            sb.append(" and user_id = '").append(sellCarModel.getUserId()).append("'");
        }
        if (StringUtils.hasText(sellCarModel.getUserType())) {
            if ("0".equals(sellCarModel.getUserType())) {
                sb.append(" and type = '0'");
            } else {
                sb.append(" and type = '1'");
            }
        }
        if (StringUtils.hasText(sellCarModel.getBrand())) {
            sb.append(" and brand like %").append(sellCarModel.getBrand()).append("%");
        }
        if (StringUtils.hasText(priceRange) && priceRange.contains("_") && priceRange.length() > 1) {
            String[] prices = priceRange.split("_");
            if ("".equals(prices[0])) {
                // 小于
                sb.append(" and sell_price <= ").append(prices[1]);
            } else if (prices.length == 1) {
                // 大于
                sb.append(" and sell_price >= ").append(prices[0]);
            } else {
                sb.append(" and sell_price between ").append(prices[0]).append(" and ").append(prices[1]);
            }
        }

        if (StringUtils.hasText(sortType)) {
            if (sortType.equals(Params.SortEnum.CREATE_TIME.getValue())) {
                sb.append(" order by create_time desc");
            } else if (sortType.equals(Params.SortEnum.PRICE.getValue())) {
                sb.append(" order by sell_price");
            } else if (sortType.equals(Params.SortEnum.PRICE_ASC.getValue())) {
                sb.append(" order by sell_price asc");
            } else if (sortType.equals(Params.SortEnum.PRICE_DESC.getValue())) {
                sb.append(" order by sell_price desc");
            }
        }

        if (page > 0 && pageSize > 0) {
            sb.append(" limit ").append((page - 1) * pageSize).append(",").append(pageSize);
        }

        try {
            con = sqlConnectionFactory.getConnection();
            ps = con.prepareStatement(sb.toString());

            rs = ps.executeQuery();
            while (rs.next()) {
                sellCarModelList.add(setValue(rs));
            }

            ps = con.prepareStatement(sb.toString().replace("*", "count(1) as count"));
            rs = ps.executeQuery();
            if (rs.next()) {
                countModel.setCount(rs.getInt("count"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlConnectionFactory.closeConnetion(con, ps, rs);
        }
        return sellCarModelList;
    }

    private SellCarModel setValue(ResultSet rs) throws Exception {
        SellCarModel sellCarModel = new SellCarModel();
        sellCarModel.setId(rs.getString("id"));
        sellCarModel.setMobile(rs.getString("mobile"));
        sellCarModel.setMileage(rs.getString("mileage"));
        sellCarModel.setPriceTax(rs.getDouble("price_tax"));
        sellCarModel.setSellPrice(rs.getDouble("sell_price"));
        sellCarModel.setBrand(rs.getString("brand"));
        sellCarModel.setCarLicense(rs.getString("car_license"));
        sellCarModel.setDisplacement(rs.getString("displacement"));
        sellCarModel.setLocation(rs.getString("location"));
        sellCarModel.setGearbox(rs.getString("gearbox"));
        sellCarModel.setDischargeStandard(rs.getString("discharge_standard"));
        sellCarModel.setUserType(rs.getString("type"));
        sellCarModel.setUserId(rs.getString("user_id"));
        sellCarModel.setPhoto(rs.getString("photo"));
        sellCarModel.setCreatedate(rs.getString("createdate"));
        sellCarModel.setRemark(rs.getString("remark"));
        sellCarModel.setFrontImg(rs.getString("frontImg"));
        sellCarModel.setBehindImg(rs.getString("behindImg"));
        sellCarModel.setSideImg(rs.getString("sideImg"));
        sellCarModel.setStatus(rs.getInt("status"));
        return sellCarModel;
    }

}
