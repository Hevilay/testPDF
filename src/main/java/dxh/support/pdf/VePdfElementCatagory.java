package dxh.support.pdf;

/**
 * pdf元素分类
 *
 * <p>pdf生成过程中支持的pdf元素。如需扩充需要在这里注册，同时还要从VePdfElement继承对应元素，并覆写抽象渲染方法</p>
 *
 * @author huyu 8979@vetech.cn
 * @date 2017-08-21
 */
public enum VePdfElementCatagory {

	/**
	 * 段落
	 */
	PARAGRAPHY,

	/**
	 * 表格
	 */
	TABLE,

	/**
	 * 柱状图
	 */
	CHART_BAR,

	/**
	 * 折线图
	 */
	CHART_LINE,

	/**
	 * 饼状图
	 */
	CHART_PIE, VePdfElementCatagory;
}
