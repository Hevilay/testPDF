package dxh.support.pdf.event;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPTableEvent;

/**
 * 表格隔行换色
 *
 * <p>代码来自网络</p>
 *
 * @author huyu 8979@vetech.cn
 * @date 2017-08-16
 */
public class AlternatingBackgroundEvent implements PdfPTableEvent {

	public void tableLayout(PdfPTable table, float[][] widths, float[] heights, int headerRows, int rowStart, PdfContentByte[] canvases) {
		int columns;
		Rectangle rect;
		//合适的颜色：（235，235，235）
		int footer = widths.length - table.getFooterRows();
		int header = table.getHeaderRows() - table.getFooterRows() + 1;
		for (int row = header; row < footer; row += 2) {
			columns = widths[row].length - 1;
			rect = new Rectangle(widths[row][0], heights[row], widths[row][columns], heights[row + 1]);
			rect.setBackgroundColor(new BaseColor(235,235,235));
			rect.setBorder(Rectangle.NO_BORDER);
			canvases[PdfPTable.BASECANVAS].rectangle(rect);
		}
	}

}