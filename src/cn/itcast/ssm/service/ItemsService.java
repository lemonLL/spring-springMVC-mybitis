package cn.itcast.ssm.service;

import java.util.List;

import cn.itcast.ssm.po.ItemsCustom;
import cn.itcast.ssm.po.ItemsQueryVo;

public interface ItemsService {
	public List<ItemsCustom> findItemList(ItemsQueryVo itemsQueryVo) throws Exception;

	public ItemsCustom findItemsById(Integer id) throws Exception;
	
	public void updateItems(Integer id, ItemsCustom itemsCustom) throws Exception;
}
