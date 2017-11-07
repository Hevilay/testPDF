package dxh.support.pdf.element;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import dxh.support.pdf.VePdfStyle;

import java.io.Serializable;

/**
 * pdf元素抽象类
 *
 * @author huyu 8979@vetech.cn
 * @date 2017-08-21
 */
public abstract class VePdfElement implements Serializable {
	private static final long serialVersionUID = -7467307144914967658L;

	/**
	 * 默认段落字段
	 */
	protected static final Font DEFAULT_FONT = VePdfStyle.FONT_MAP.get("normal");

	/**
	 * 默认段落首先缩进大小
	 */
	protected static final float DEFAULT_FIRST_LINE_INDENT = 20;

	/**
	 * 向pdf文档渲染pdf元素
	 * @param document				pdf文档
	 * @throws DocumentException
	 */
	public abstract void addToDocument(Document document) throws DocumentException;

}
