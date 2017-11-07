package dxh;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.util.IOUtils;

import dxh.entity.AirTicketInfo;
import dxh.entity.AnalyzeByCategoryInfo;
import dxh.exceptions.PdfException;
import dxh.support.chart.BarChart;
import dxh.support.chart.ChartSupport;
import dxh.support.chart.PieChart;
import dxh.support.chart.Serie;
import dxh.support.pdf.VePdfSupport;
import dxh.support.pdf.element.*;
import org.jfree.chart.JFreeChart;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Date:     2017/11/6 17:04
 * Description: 新版差旅分析测试
 */
public class Test {

    public static void main(String[] args) throws PdfException {
        List<VePdfSection> sections = null;
        try {
            sections = assembleData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Map> titleList = getTitleList();
        VePdf vePdf = new VePdf.VePdfBuilder("差旅分析报告", null, sections)
                .footer(true)
                .bookmark(true)
                .footTitle("十一重科技发展有限公司")
                .setDirectoryTitleList(titleList)
                .footSubTitle("2017年7月份分析报告")
                .build();
        VePdfSupport.genPdf(vePdf, new File("pdfTest1.pdf"));
    }

    /**
     * 组装生成PDF的数据
     * @return
     */
    public static List<VePdfSection> assembleData() throws IOException {
        //第一页
        List<VePdfSection> sections = new ArrayList<VePdfSection>();
        VePdfSection section1 = new VePdfSection();
        section1.setCode("001");
        section1.setLevel(1);
        section1.setName("差旅月度整体分析");
        VePdfParagraph vePdfParagraph1 = new VePdfParagraph("十一重科技发展有限公司10月份总体消费为16954.3，其中机票消费占比最多为34.76%，与上月相比增长2.73%。芒果项目为本月消费最多项目，主要消费部门为研发部。");
        List<VePdfElement> list1 = new ArrayList<VePdfElement>();
        list1.add(vePdfParagraph1);
        section1.setElements(list1);
        //第二页
        List<VePdfSection> sections1 = new ArrayList<VePdfSection>();
        VePdfSection section2 = new VePdfSection();
        section2.setCode("002");
        section2.setLevel(2);
        section2.setName("整体费用构成");
        //表格图片数据
        JFreeChart jfreechart = new PieChart("", new String[]{"机票","酒店","火车票","旅游","门票"}, new Double[]{1557.36,1238.55,1589.32,1698.45,2136.54}).createChart().getChart();
        byte[] imgBytes = ChartSupport.genPngBytes(jfreechart, 500, 500);
        List<AnalyzeByCategoryInfo> list = getList1();
        VePdfMixingTable table = new VePdfMixingTable("类别/金额占比分析", AnalyzeByCategoryInfo.class,list,imgBytes);
        List<VePdfElement> list2 = new ArrayList<VePdfElement>();
        list2.add(table);
        section2.setElements(list2);
        section2.setParent(section1);
        sections1.add(section2);
        //第三页
        VePdfSection section3 = new VePdfSection();
        section3.setCode("003");
        section3.setLevel(2);
        section3.setName("历史数据回顾");
        //表格柱状图数据
        Vector<Serie> series = new Vector<Serie>();
        series.add(new Serie("按部门", new Double[]{1557.36,1238.55,1589.32,1698.45,2136.54}));
        JFreeChart jfreechart1 = new BarChart("", null, null, new String[]{"机票","酒店","火车票","旅游","门票"}, series).createChart().getChart();
        byte[] imgBytes1 = ChartSupport.genPngBytes(jfreechart1, 500, 500);
        List<AirTicketInfo> airTicketInfoList = listAirTicketInfo();
        VePdfMixingTable table1 = new VePdfMixingTable("机票数据",AirTicketInfo.class,airTicketInfoList,imgBytes,imgBytes1);
        List<VePdfElement> list3 = new ArrayList<VePdfElement>();
        list3.add(table1);
        section3.setElements(list3);
        section3.setParent(section1);
        sections1.add(section3);
        section1.setChildren(sections1);
        sections.add(section1);
        return sections;
    }

    public static List<AnalyzeByCategoryInfo> getList1(){
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
        return data;
    }

    private static List<AirTicketInfo> listAirTicketInfo() {
        InputStream is = Test.class.getClassLoader().getResourceAsStream("airticketinfo.json");
        List<AirTicketInfo> list = null;
        try {
            list = JSON.parseArray(IOUtils.toString(is), AirTicketInfo.class);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public static List<Map> getTitleList(){
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
