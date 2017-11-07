package dxh.entity;

import java.io.Serializable;

/**
 * 一段话描述
 *
 * @author huyu 8979@vetech.cn
 * @date 2017-08-21
 */
public class VipReportSectionItem implements Serializable {
	private static final long serialVersionUID = 1096462763572886127L;

	private String id;
	private String sectionCode;
	private String catagory;
	private String dataKey;
	private int sortNum;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSectionCode() {
		return sectionCode;
	}

	public void setSectionCode(String sectionCode) {
		this.sectionCode = sectionCode;
	}

	public String getCatagory() {
		return catagory;
	}

	public void setCatagory(String catagory) {
		this.catagory = catagory;
	}

	public String getDataKey() {
		return dataKey;
	}

	public void setDataKey(String dataKey) {
		this.dataKey = dataKey;
	}

	public int getSortNum() {
		return sortNum;
	}

	public void setSortNum(int sortNum) {
		this.sortNum = sortNum;
	}
}
