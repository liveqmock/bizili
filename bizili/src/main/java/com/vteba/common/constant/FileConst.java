package com.vteba.common.constant;

/**
 * 文件相关常量，上传下载等
 * @author yinlei 
 * date 2012-7-1 下午4:18:51
 */
public class FileConst {
	/**
	 * 导入会计科目标题
	 */
	public static final String[] SUB_IMP_TITLE = { "代码", "名称", "类别", "余额方向", "外币核算", "期末调汇", "辅助核算", "状态", "大类", "科目级别", "子科目数" };
	/**
	 * 导入会计科目栏位
	 */
    public static final String[] SUB_IMP_COLUMN = { "subjectCode", "subjectName", "subjectType", "balanceDirection", "foreignCurrencyAccount", "adjustExrate", "aidAccount", "state", "majorCate", "level", "childNumber" };
    /**
     * 导入会计科目栏位长度
     */
    public static final int[] SUB_IMP_COLUMN_LEN = { 30, 100, 100, 45, 45, 45, 10, 10, 40, 5, 5 };
}
