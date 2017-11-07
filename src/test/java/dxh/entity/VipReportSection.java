package dxh.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 一段话描述
 *
 * @author huyu 8979@vetech.cn
 * @date 2017-08-21
 */
public class VipReportSection implements Serializable {
	private static final long serialVersionUID = -792700745282304826L;

	private String id;
	private String parentId;

	private VipReportSection parent;

	private List<VipReportSection> children;

	private String mainId;
	private String name;
	private String code;
	private int level;
	private int sortNum;
	private String description;

	private List<VipReportSectionItem> items;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public VipReportSection getParent() {
		return parent;
	}

	public void setParent(VipReportSection parent) {
		this.parent = parent;
	}

	public List<VipReportSection> getChildren() {
		return children;
	}

	public void setChildren(List<VipReportSection> children) {
		this.children = children;
	}

	public String getMainId() {
		return mainId;
	}

	public void setMainId(String mainId) {
		this.mainId = mainId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getSortNum() {
		return sortNum;
	}

	public void setSortNum(int sortNum) {
		this.sortNum = sortNum;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<VipReportSectionItem> getItems() {
		return items;
	}

	public void setItems(List<VipReportSectionItem> items) {
		this.items = items;
	}
}
