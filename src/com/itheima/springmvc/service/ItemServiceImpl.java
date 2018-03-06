package com.itheima.springmvc.service;

import java.util.Date;
import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itheima.springmvc.dao.ItemsMapper;
import com.itheima.springmvc.pojo.Items;

/**
 * 查询商品信息
 *
 * @author lx
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemsMapper itemsMapper;

    //查询商品列表
    @Override
    public List<Items> selectItemsList() {
        return itemsMapper.selectByExampleWithBLOBs(null);
    }

    @Override
    public Items selectItemsById(Integer id) {
        return itemsMapper.selectByPrimaryKey(id);
    }

    //修改
    @Override
    public void updateItemsById(Items items) {
        if (null != items) {
//            items.setCreatetime(new Date());
            itemsMapper.updateByPrimaryKeyWithBLOBs(items);
        }

    }
}
