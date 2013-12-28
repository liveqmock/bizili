package com.vteba.finance.report.service;

import com.vteba.finance.report.model.AssetsLiabilities;
import com.vteba.service.generic.IGenericService;

/**
 * 资产负债表service
 * @author yinlei 
 * date 2012-7-23 上午10:36:47
 */
public interface IAssetsLiabilitiesService extends IGenericService<AssetsLiabilities, String> {
	
	/**
	 * 定时任务，自动产生资产负债表数据
	 * @author yinlei
	 * date 2012-7-27 下午8:50:56
	 */
	public void autoGenerateAssetsLiabilitiesTask();
	
	/**
	 * 创建assetsLiabilities
	 * @author yinlei
	 * date 2012-7-28 下午3:39:39
	 */
	public void createAssetsLiabilities();
	
	/**
	 * 更新资产负债表，只需要更新本期发生额即可
	 * @author yinlei
	 * date 2012-7-31 下午3:55:35
	 */
	public void updateAssetsLiabilities();
}
