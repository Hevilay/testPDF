package dxh;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.util.IOUtils;
import dxh.entity.AirTicketInfo;
import dxh.entity.AnalyzeByCategoryInfo;
import dxh.entity.VipReportSection;
import dxh.entity.VipReportSectionItem;
import dxh.support.chart.*;
import dxh.support.pdf.VePdfElementCatagory;
import dxh.support.pdf.VePdfSupport;
import dxh.support.pdf.element.*;
import org.apache.commons.lang3.StringUtils;
import org.jfree.chart.JFreeChart;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 一段话描述
 *
 * @author huyu 8979@vetech.cn
 * @date 2017-08-21
 */
public class VePdfSupportTest {

	/**
	 * 业务数据
	 */
	private static Map<String, Object> data = new HashMap<String, Object>();

	private static final int S_LEVEL = 0;
	private static final int S_COUNT = 3;

	@Test
	public void gen() throws Exception {
		VePdfSupport.genPdf(buildVePdf("1"), new File("mixingTableTest2.pdf"));
//		VePdfSupport.genPdf(buildVePdf(), new File("test3.pdf"));
	}

	private VePdf buildVePdf() {
		List<Map> titleList = getTitleList();
		List<VePdfSection> sections = buildSections(serviceSections(S_LEVEL, S_COUNT, null), data);
		return new VePdf.VePdfBuilder("差旅分析报告", null, sections)
				.footer(true)
				.bookmark(true)
				.setDirectoryTitleList(titleList)
				.footTitle("十一重科技发展有限公司")
				.footSubTitle("2017年7月份分析报告")
				.build();
	}

	private VePdf buildVePdf(String type) throws IOException {
		List<VePdfSection> sections = new ArrayList<VePdfSection>();
		VePdfSection section = new VePdfSection();
		//表格list数据
		AnalyzeByCategoryInfo one = new AnalyzeByCategoryInfo("机票","1557.36","18.82");
		AnalyzeByCategoryInfo two = new AnalyzeByCategoryInfo("酒店","1238.55","15.00");
		AnalyzeByCategoryInfo three = new AnalyzeByCategoryInfo("火车票","1589.32","19.21");
		AnalyzeByCategoryInfo four = new AnalyzeByCategoryInfo("旅游","1698.45","20.53");
		AnalyzeByCategoryInfo five = new AnalyzeByCategoryInfo("门票","2136.54","25.82");
		List<AnalyzeByCategoryInfo> data = new ArrayList<AnalyzeByCategoryInfo>();
		data.add(one);
		data.add(two);
		data.add(three);
		data.add(four);
		data.add(five);
		//表格图片数据
		JFreeChart jfreechart = new PieChart("", new String[]{"机票","酒店","火车票","旅游","门票"}, new Double[]{1557.36,1238.55,1589.32,1698.45,2136.54}).createChart().getChart();
		byte[] imgBytes = ChartSupport.genPngBytes(jfreechart, 500, 500);
		//表格柱状图数据
		Vector<Serie> series = new Vector<Serie>();
		series.add(new Serie("按部门", new Double[]{1557.36,1238.55,1589.32,1698.45,2136.54}));
		JFreeChart jfreechart1 = new BarChart("", null, null, new String[]{"机票","酒店","火车票","旅游","门票"}, series).createChart().getChart();
		byte[] imgBytes1 = ChartSupport.genPngBytes(jfreechart1, 500, 500);

		VePdfMixingTable table = new VePdfMixingTable("类别/金额占比分析", AnalyzeByCategoryInfo.class,data,imgBytes);
		List<VePdfElement> elements = new ArrayList<VePdfElement>();
		elements.add(table);
		section.setElements(elements);
		section.setName("混合表格测试");
		section.setCode("00");
		section.setLevel(1);
		sections.add(section);
		return new VePdf.VePdfBuilder("差旅分析报告", null, sections)
				.footer(true)
				.bookmark(true)
				.footTitle("十一重科技发展有限公司")
				.footSubTitle("2017年7月份分析报告")
				.build();
	}

	/**
	 * 业务数据向pdf元数据转换
	 * @param sections			业务数据
	 * @param data				业务数据
	 * @return
	 */
	private List<VePdfSection> buildSections(List<VipReportSection> sections, Map<String, Object> data) {
		List<VePdfSection> res = new ArrayList<VePdfSection>(sections.size());
		for (VipReportSection section : sections) {
			VePdfSection tmp = new VePdfSection();
			tmp.setName(section.getName());
			tmp.setCode(section.getCode());
			tmp.setLevel(section.getLevel());
			if (section.getParent() != null) {
				VipReportSection parent = section.getParent();

				VePdfSection tmpParent = new VePdfSection();
				tmpParent.setCode(parent.getCode());
				tmpParent.setName(parent.getName());
				tmpParent.setLevel(parent.getLevel());
				tmp.setParent(tmpParent);
			}

			List<VipReportSectionItem> items = section.getItems();
			if (items != null && items.size() > 0) {
				List<VePdfElement> elements = new ArrayList<VePdfElement>();
				for (VipReportSectionItem item : items) {
					VePdfElement element = null;
					String catagory = item.getCatagory();
					Object d = data.get(item.getDataKey());
					if (d == null) {
						continue ;
					}
					if (VePdfElementCatagory.PARAGRAPHY.toString().equals(catagory)) {
						element = new VePdfParagraph(d.toString());
					} else if (VePdfElementCatagory.TABLE.toString().equals(catagory)) {
						List list = (List) d;
						element = new VePdfTable("数据列表", list.get(0).getClass(), list);
					} else if (VePdfElementCatagory.CHART_BAR.toString().equals(catagory)) {
						element = new VePdfBarChart((byte[]) d);
					} else if (VePdfElementCatagory.CHART_LINE.toString().equals(catagory)) {
						element = new VePdfLineChart((byte[]) d);
					} else if (VePdfElementCatagory.CHART_PIE.toString().equals(catagory)) {
						element = new VePdfPieChart((byte[]) d);
					}
					elements.add(element);
				}
				tmp.setElements(elements);
			}

			List<VipReportSection> children = section.getChildren();
			if (children != null && children.size() > 0) {
				tmp.setChildren(buildSections(children, data));
			}
			res.add(tmp);
		}
		return res;
	}

	/**
	 * 构建虚假业务数据
	 * @param level		层级
	 * @param count		计数
	 * @param parent	父元素
	 * @return
	 */
	private List<VipReportSection> serviceSections(int level, int count, VipReportSection parent) {
		List<VipReportSection> sections = new ArrayList<VipReportSection>();
		for (int i=0; i<count; i++) {
			VipReportSection section = section(level+1, i, parent);
			sections.add(section);
		}
		return sections;
	}

	private VipReportSection section(int level, int count, VipReportSection parent) {
		VipReportSection section = new VipReportSection();
		String code = genCode(parent == null ? null : parent.getCode(), count);
		section.setCode(code);
		section.setLevel(level);
		section.setSortNum(count);
		section.setName("一段话描述"+"-"+code);
		section.setParent(parent);

		List<VipReportSectionItem> items = new ArrayList<VipReportSectionItem>();
		if (random.nextInt(10) % 3 == 0) {
			items.add(paragraphyItem(code));
		}

		if (random.nextBoolean()) {
			if (items.size() == 0) {
				items.add(paragraphyItem(code));
			}
			items.add(lineChartItem(code));
		}

		if (random.nextBoolean()) {
			if (items.size() == 0) {
				items.add(paragraphyItem(code));
			}
			items.add(barChartItem(code));
		}

		if (random.nextBoolean()) {
			if (items.size() == 0) {
				items.add(paragraphyItem(code));
			}
			items.add(pieChartItem(code));
		}

		if (level == S_COUNT) {
			if (items.size() == 0) {
				items.add(paragraphyItem(code));
			}
			items.add(tableItem(code));
		}
		section.setItems(items);

		List<VipReportSection> children = serviceSections(level, count, section);
		section.setChildren(children);

		return section;
	}

	/**
	 * 段落数据
	 * @param dataKey
	 * @return
	 */
	private VipReportSectionItem paragraphyItem(String dataKey) {
		VipReportSectionItem res = new VipReportSectionItem();
		res.setCatagory(VePdfElementCatagory.PARAGRAPHY.toString());
		String code = dataKey+atomicInteger.getAndIncrement();
		res.setDataKey(code);
		data.put(code, "违背标准的损失费用为6130.0元，其中提前0～3天订票张数为276张，张数占比为96.2%；其中违背前后1小时最低价机票张数22张，张数占比为7.7%。");
		return res;
	}

	/**
	 * 表格数据
	 * @param dataKey
	 * @return
	 */
	private VipReportSectionItem tableItem(String dataKey) {
		VipReportSectionItem res = new VipReportSectionItem();
		res.setCatagory(VePdfElementCatagory.TABLE.toString());
		String code = dataKey+atomicInteger.getAndIncrement();
		res.setDataKey(code);
		data.put(code, listAirTicketInfo());
		return res;
	}

	/**
	 * 折线图数据
	 * @param dataKey
	 * @return
	 */
	private VipReportSectionItem lineChartItem(String dataKey) {
		VipReportSectionItem res = new VipReportSectionItem();
		res.setCatagory(VePdfElementCatagory.CHART_LINE.toString());
		String code = dataKey+atomicInteger.getAndIncrement();
		res.setDataKey(code);
		data.put(code, chartData(VePdfElementCatagory.CHART_LINE));
		return res;
	}

	/**
	 * 柱状图数据
	 * @param dataKey
	 * @return
	 */
	private VipReportSectionItem barChartItem(String dataKey) {
		VipReportSectionItem res = new VipReportSectionItem();
		res.setCatagory(VePdfElementCatagory.CHART_BAR.toString());
		String code = dataKey+atomicInteger.getAndIncrement();
		res.setDataKey(code);
		data.put(code, chartData(VePdfElementCatagory.CHART_BAR));
		return res;
	}

	/**
	 * 饼状图数据
	 * @param dataKey
	 * @return
	 */
	private VipReportSectionItem pieChartItem(String dataKey) {
		VipReportSectionItem res = new VipReportSectionItem();
		res.setCatagory(VePdfElementCatagory.CHART_PIE.toString());
		String code = dataKey+atomicInteger.getAndIncrement();
		res.setDataKey(code);
		data.put(code, chartData(VePdfElementCatagory.CHART_PIE));
		return res;
	}

	private byte[] chartData(VePdfElementCatagory catagory) {
		Map<String, Double> map = new HashMap<String, Double>();
		List<AirTicketInfo> list = listAirTicketInfo();
		for (AirTicketInfo info : list) {
			String key = info.getDepartment();
			double value = 0;
			if (map.containsKey(key)) {
				value = map.get(key);
			}
			map.put(key, value + info.getPrice());
		}
		String[] categories;
		List<String> c = new ArrayList<String>();
		Vector<Serie> series = new Vector<Serie>();
		List<Double> v = new ArrayList<Double>();
		for (Map.Entry<String, Double> entry : map.entrySet()) {
			c.add(entry.getKey());
			v.add(entry.getValue());
		}
		categories = new String[c.size()];
		for (int i=0; i<c.size(); i++) {
			String p = c.get(i);
			categories[i] = p;
			//categories[i] = p.substring(0, p.length() > 4 ? 4 : p.length());
		}
		series.add(new Serie("按部门", v.toArray()));

		String title = "按部门分析机票信息";
		try {
			JFreeChart jfreechart = null;
			switch (catagory) {
				case CHART_LINE:
					jfreechart = new LineChart(title, null, null, categories, series).createChart().getChart();
					break;
				case CHART_BAR:
					jfreechart = new BarChart(title, null, null, categories, series).createChart().getChart();
					break;
				case CHART_PIE:
					jfreechart = new PieChart(title, categories, v.toArray()).createChart().getChart();
					break;
				default:
					break;
			}
			return ChartSupport.genPngBytes(jfreechart, 500, 400);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private List<AirTicketInfo> listAirTicketInfo() {
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("airticketinfo.json");
		List<AirTicketInfo> list = null;
		try {
			list = JSON.parseArray(IOUtils.toString(is), AirTicketInfo.class);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Collections.emptyList();
	}


	private static String genCode(String parentCode, int count) {
		if (StringUtils.isEmpty(parentCode)) {
			parentCode = "";
		}
		return parentCode+df.format(count);
	}

	private static Random random = new Random();

	private static DecimalFormat df = new DecimalFormat("00");

	private static AtomicInteger atomicInteger = new AtomicInteger(0);

	public List<Map> getTitleList(){
		List<Map> titleList = new ArrayList<Map>();
		String[] titleArr = new String[]{"差旅月度整体消费分析","差旅节省与损失分析","机票统计分析","酒店统计分析"};
		String[] ydztTitleArr = new String[]{"整体费用构成","历史数据回顾（同比、环比）","按部门统计","按项目统计","按成本中心统计","按人员总消费排名分析"};
		String[] jsssTitleArr = new String[]{"节省分布（按部门，前十）","节省分布（按项目，前十）","节省分布（按成本中心，前十）","节省分布（按人员，前二十）","损失分布分析（按部门）"};
		String[] jpTitleArr = new String[]{"显性节省分析","隐性节省分析","机票损失分析","按航班时刻分析","按机票折扣分析","按提前天数分析","按航空公司分析","前十航线消费分析","退废票、改签分析"};
		String[] jdTitleArr = new String[]{"按提前天数分析","按星级排名分析","按酒店品牌分析（前二十）","按消费城市排名分析（排名，前二十）","协议酒店消费分析"};
		List list = new ArrayList();
		list.add(ydztTitleArr);
		list.add(jsssTitleArr);
		list.add(jpTitleArr);
		list.add(jdTitleArr);
		for(int i = 0 ; i<titleArr.length;i++){
			Map m = new HashMap();
			m.put("title",titleArr[i]);
			String[] arr = (String[]) list.get(i);
			List<Map> subList = new ArrayList<Map>();
			for(String subTitle : arr){
				Map map = new HashMap();
				map.put("subTitle",subTitle);
				subList.add(map);
			}
			m.put("subList",subList);
			titleList.add(m);
		}

		return titleList;
	}

}
