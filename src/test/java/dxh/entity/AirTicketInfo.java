package dxh.entity;



import dxh.support.pdf.annotations.VePdfTableColumn;

import java.io.Serializable;

/**
 * 一段话描述
 *
 * @author huyu 8979@vetech.cn
 * @date 2017-08-16
 */
public class AirTicketInfo implements Serializable {
	private static final long serialVersionUID = -7836680776790350568L;

	/**
	 * 姓名
	 */
	@VePdfTableColumn(name = "姓名", sortNum = 2)
	private String name;

	/**
	 * 部门
	 */
	@VePdfTableColumn(name = "部门", sortNum = 1)
	private String department;

	/**
	 * 航程
	 */
	@VePdfTableColumn(name = "航程", sortNum = 3)
	private String voyage;

	/**
	 * 票号
	 */
	@VePdfTableColumn(name = "票号", sortNum = 4)
	private String ticketNum;

	/**
	 * 价格
	 */
	@VePdfTableColumn(name = "价格", sortNum = 5)
	private double price;

	/**
	 * 出票时间
	 */
	@VePdfTableColumn(name = "出票时间", sortNum = 6)
	private String ticketTime;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getVoyage() {
		return voyage;
	}

	public void setVoyage(String voyage) {
		this.voyage = voyage;
	}

	public String getTicketNum() {
		return ticketNum;
	}

	public void setTicketNum(String ticketNum) {
		this.ticketNum = ticketNum;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getTicketTime() {
		return ticketTime;
	}

	public void setTicketTime(String ticketTime) {
		this.ticketTime = ticketTime;
	}
}
