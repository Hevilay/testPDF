package dxh.support.chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.RectangleEdge;

import java.awt.*;

/**
 * JFreeChart饼状图封装
 *
 * <p>备注：代码来自网络</p>
 *
 * @author huyu 8979@vetech.cn
 * @date 2017-08-17
 */
public class PieChart {

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 分类标注
	 */
	private String[] categories;

	/**
	 * 值集合
	 */
	private Object[] datas;


	public PieChart(String[] categories, Object[] datas) {
		this.categories = categories;
		this.datas = datas;
	}

	public PieChart(String title, String[] categories, Object[] datas) {
		this.title = title;
		this.categories = categories;
		this.datas = datas;
	}

	public DefaultPieDataset createDataset() {
		DefaultPieDataset dataset = ChartSupport.createDefaultPieDataset(this.categories, this.datas);
		return dataset;
	}

	public ChartPanel createChart() {
		//创建Chart[创建不同图形]
		JFreeChart chart = ChartFactory.createPieChart(this.title, createDataset());
		//设置抗锯齿，防止字体显示不清楚
		ChartSupport.setAntiAlias(chart);// 抗锯齿
		//对柱子进行渲染[创建不同图形]
		ChartSupport.setPieRender(chart.getPlot());
		/**
		 * 可以注释测试
		 */
		// plot.setSimpleLabels(true);//简单标签,不绘制线条
		// plot.setLabelGenerator(null);//不显示数字
		// 设置标注无边框
		chart.getLegend().setFrame(new BlockBorder(Color.WHITE));
		//标注位于右侧
		chart.getLegend().setPosition(RectangleEdge.BOTTOM);
		//使用chartPanel接收
		ChartPanel chartPanel = new ChartPanel(chart);
		return chartPanel;
	}

}
