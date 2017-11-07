package dxh.support.pdf.element;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import dxh.support.pdf.VePdfStyle;

import java.util.List;
import java.util.Map;

/**
 * Author:   dongxinhang
 * Date:     2017/11/6 16:09
 * Description: PDF 目录
 */
public class VePdfDirectoryTitle extends VePdfElement{
    /**
     * 标题内容
     * 格式：
     *      title   -- String
     *      subList -- List<Map>
     *          title -- String
     *          subList -- List<Map>
     */
    private List<Map> titleList;

    public List<Map> getTitleList() {
        return titleList;
    }

    public void setTitleList(List<Map> titleList) {
        this.titleList = titleList;
    }

    @Override
    public void addToDocument(Document document) throws DocumentException {
        if(titleList != null && titleList.size() > 0){
            for(Map one : titleList){
                String title = (String) one.get("title");
                Paragraph paragraph = new Paragraph("               ★ "+title, VePdfStyle.FONT_MAP.get("directoryTitle1"));
                paragraph.setAlignment(Element.ALIGN_LEFT);
                document.add(paragraph);
                List<Map> subList = (List<Map>) one.get("subList");
                if(null!=subList && subList.size()>0){
                    for (Map m : subList){
                        String subTitle = (String) m.get("subTitle");
                        Paragraph paragraph1 = new Paragraph("                      ◆ "+subTitle, VePdfStyle.FONT_MAP.get("directoryTitle2"));
                        paragraph1.setAlignment(Element.ALIGN_LEFT);
                        document.add(paragraph1);
                    }
                }
            }
            //设置目录后换页
            document.newPage();
        }
    }

}
