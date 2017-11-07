package dxh.support.chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;
import java.util.Vector;

/**
 * JFreeChart折线图封装
 *
 * <p>备注：代码来自网络</p>
 *
 * @author huyu 8979@vetech.cn
 * @date 2017-08-17
 */
public class LineChart {

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 分类轴标签
	 */
	private String categoryAxisLabel;

	/**
	 * 值轴标签
	 */
	private String valueAxisLabel;

	/**
	 * 标注类别数组
	 */
	private String[] categories;

	/**
	 * 所有的值集合
	 */
	private Vector<Serie> series;

	public LineChart(String[] categories, Vector<Serie> series) {
		this.categories = categories;
		this.series = series;
	}

	public LineChart(String title, String categoryAxisLabel, String valueAxisLabel, String[] categories, Vector<Serie> series) {
		this.title = title;
		this.categoryAxisLabel = categoryAxisLabel;
		this.valueAxisLabel = valueAxisLabel;
		this.categories = categories;
		this.series = series;
	}

	public DefaultCategoryDataset createDataset() {
		DefaultCategoryDataset dataset = ChartSupport.createDefaultCategoryDataset(series, categories);
		return dataset;
	}

	public ChartPanel createChart() {
		//创建Chart[创建不同图形]
		JFreeChart chart = ChartFactory.createLineChart(this.title, this.categoryAxisLabel, this.valueAxisLabel, createDataset());
		//设置抗锯齿，防止字体显示不清楚
		ChartSupport.setAntiAlias(chart);// 抗锯齿
		//对柱子进行渲染[[采用不同渲染]]
		ChartSupport.setLineRender(chart.getCategoryPlot(), false, true);//
		//对其他部分进行渲染
		ChartSupport.setXAixs(chart.getCategoryPlot());// X坐标轴渲染
		ChartSupport.setYAixs(chart.getCategoryPlot());// Y坐标轴渲染
		//设置标注无边框
		chart.getLegend().setFrame(new BlockBorder(Color.WHITE));
		//使用chartPanel接收
		ChartPanel chartPanel = new ChartPanel(chart);
		return chartPanel;
	}

}
