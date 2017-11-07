package dxh.support.pdf.element;

import java.util.List;

/**
 * pdf文件章节
 *
 * @author huyu 8979@vetech.cn
 * @date 2017-08-21
 */
public class VePdfSection {

	private String name;

	private String code;

	private int level;

	private VePdfSection parent;

	private List<VePdfSection> children;

	private List<VePdfElement> elements;

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

	public VePdfSection getParent() {
		return parent;
	}

	public void setParent(VePdfSection parent) {
		this.parent = parent;
	}

	public List<VePdfSection> getChildren() {
		return children;
	}

	public void setChildren(List<VePdfSection> children) {
		this.children = children;
	}

	public List<VePdfElement> getElements() {
		return elements;
	}

	public void setElements(List<VePdfElement> elements) {
		this.elements = elements;
	}
}
