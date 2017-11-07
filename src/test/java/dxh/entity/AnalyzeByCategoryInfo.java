package dxh.entity;



import dxh.support.pdf.annotations.VePdfTableColumn;

import java.io.Serializable;

/**
 * Created by dongxinhang on 2017/8/29.
 */
public class AnalyzeByCategoryInfo  implements Serializable {

    @VePdfTableColumn(name = "类别", sortNum = 1)
    private String type;

    @VePdfTableColumn(name = "金额", sortNum = 2)
    private String price;

    @VePdfTableColumn(name = "金额占比(%)", sortNum = 3)
    private String percentage;

    public AnalyzeByCategoryInfo(){

    }

    public AnalyzeByCategoryInfo(String type,String price,String percentage){
        this.type = type;
        this.price = price;
        this.percentage = percentage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }
}
