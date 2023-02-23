package com.fc.pay.bill.service;

import java.util.Date;
import java.util.List;

import com.fc.pay.bill.entity.BillBizBatch;
import com.fc.pay.bill.entity.BillBizDoubt;
import com.fc.pay.bill.entity.BillBizItem;
import com.fc.pay.bill.entity.BillBizMistake;
import com.fc.pay.bill.entity.BillBizSummary;
import com.fc.pay.bill.vo.BillBizCheckProgressData;
import com.fc.pay.trade.entity.MerchantApp;
import com.fc.pay.trade.entity.MerchantAppConfig;

/**
 * 业务对账数据事务服务接口
 * 
 * @author zhanjq
 *
 */
public interface BillBizCheckTranxService {
	
	/**
	 * 准备对账过程数据
	 * @param billDate
	 * @param merchantAppList
	 * @param macList
	 * @return
	 */
	public BillBizCheckProgressData prepareProgressData(Date billDate, List<MerchantApp> merchantAppList, List<MerchantAppConfig> macList);
	
	/**
	 * 处理批次对账结果
	 * @param summary 对账汇总
	 * @param batch   对账批次
	 * @param mistakeList 差错列表
	 * @param insertDoubtList 待新增存疑列表
	 * @param removeDoubtList 待删除存疑列表
	 * @param bizItemList 待插入内部账单明细列表
	 */
	public void handleBatchCheckResult(BillBizSummary summary, BillBizBatch batch, List<BillBizMistake> mistakeList, List<BillBizDoubt> insertDoubtList, List<BillBizDoubt> removeDoubtList, List<BillBizItem> bizItemList);

	/**
	 * 处理对账周期存疑数据
	 * @param removeDoubtlist	待删除存疑记录
	 * @param insertMistakeList 待新增差错记录
	 */
	public void handleDoubtInPeriod(List<BillBizDoubt> removeDoubtlist, List<BillBizMistake> insertMistakeList);

	/**
	 * 处理差错记录 
	 * @param userName 		当前用户名
	 * @param mistakeId 	差错记录ID
	 * @param handleType 	差错处理方式
	 * @param handleRemark 	差错处理备注
	 */
	public void handleMistake(String userName, String mistakeId, String handleType, String handleRemark) throws Exception ;
	
	/**
	 * 差错处理：调整交易状态为失败
	 * 满足以下情况：
	 * 情况1：渠道漏单[渠道不存在该交易]
	 * 情况2：本地长款,状态不符[本地支付成功但渠道支付不成功(基本不会出现)]
	 * @param mistake
	 */
	public void handleTradeToFail(BillBizMistake mistake);

	/**
	 * 差错处理：调整交易状态为失败
	 * 情况1：本地短款，状态不符[本地支付不成功但渠道支付成功(比较常见)]
	 * @param mistake
	 */
	public void handleTradeToSuccess(BillBizMistake mistake);
	
	/**
	 * 差错处理：调整交易金额
	 * @param mistake
	 * @throws Exception
	 */
	public void modifyTradeAmount(BillBizMistake mistake) throws Exception;

	/**
	 * 修改对账批次
	 * @param billBizBatch
	 */
	public int modifyBillBizBatch(BillBizBatch billBizBatch);

	/**
	 * 新增对账批次
	 * @param billBizBatch
	 * @return
	 */
	public int addBillBizBatch(BillBizBatch billBizBatch);

	/**
	 * 批量添加对账批次
	 * @param batchList
	 */
	public List<BillBizBatch> addBillBizBatchList(List<BillBizBatch> batchList);

	/**
	 * 模拟错误账单对账，仅供测试验证第三方账单渠道错误场景
	 * @param batch
	 * @param mistakeList
	 * @param insertDoubtList
	 * @param removeDoubtList
	 * @param bizItemList
	 */
	public void handleBatchCheckResultForTestVerify(BillBizBatch batch,
			List<BillBizMistake> mistakeList,
			List<BillBizDoubt> insertDoubtList,
			List<BillBizDoubt> removeDoubtList, List<BillBizItem> bizItemList);

}
