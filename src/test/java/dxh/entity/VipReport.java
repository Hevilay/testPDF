package dxh.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 一段话描述
 *
 * @author huyu 8979@vetech.cn
 * @date 2017-08-21
 */
public class VipReport implements Serializable {
	private static final long serialVersionUID = 8114237849790058559L;

	private String id;
	private String zgs;
	private String hyid;
	private String hymc;
	private String corpName;
	private String monthy;
	private String quarterly;
	private String annual;

	private List<VipReportSection> sections;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getZgs() {
		return zgs;
	}

	public void setZgs(String zgs) {
		this.zgs = zgs;
	}

	public String getHyid() {
		return hyid;
	}

	public void setHyid(String hyid) {
		this.hyid = hyid;
	}

	public String getHymc() {
		return hymc;
	}

	public void setHymc(String hymc) {
		this.hymc = hymc;
	}

	public String getCorpName() {
		return corpName;
	}

	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}

	public String getMonthy() {
		return monthy;
	}

	public void setMonthy(String monthy) {
		this.monthy = monthy;
	}

	public String getQuarterly() {
		return quarterly;
	}

	public void setQuarterly(String quarterly) {
		this.quarterly = quarterly;
	}

	public String getAnnual() {
		return annual;
	}

	public void setAnnual(String annual) {
		this.annual = annual;
	}

	public List<VipReportSection> getSections() {
		return sections;
	}

	public void setSections(List<VipReportSection> sections) {
		this.sections = sections;
	}
}
