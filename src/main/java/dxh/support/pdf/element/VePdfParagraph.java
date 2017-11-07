package dxh.support.pdf.element;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import org.apache.commons.lang3.StringUtils;

/**
 * pdf段落元素
 *
 * @author huyu 8979@vetech.cn
 * @date 2017-08-21
 */
public class VePdfParagraph extends VePdfElement {
	private static final long serialVersionUID = -1252586050020126428L;

	private String content;

	private Font font = DEFAULT_FONT;

	private float firstLineIndent = DEFAULT_FIRST_LINE_INDENT;

	public VePdfParagraph() {}

	public VePdfParagraph(String content) {
		this.content = content;
	}

	@Override
	public void addToDocument(Document document) throws DocumentException {
		if (StringUtils.isEmpty(this.content)) {
			return ;
		}
		Paragraph paragraph = new Paragraph(this.content, this.font);
		paragraph.setFirstLineIndent(firstLineIndent);
		paragraph.setSpacingBefore(10);
		paragraph.setSpacingAfter(10);
		document.add(paragraph);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public float getFirstLineIndent() {
		return firstLineIndent;
	}

	public void setFirstLineIndent(float firstLineIndent) {
		this.firstLineIndent = firstLineIndent;
	}
}
