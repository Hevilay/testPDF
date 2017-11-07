package dxh.support.pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;

import java.util.HashMap;
import java.util.Map;

/**
 * pdf基本样式
 *
 * @author huyu 8979@vetech.cn
 * @date 2017-08-21
 */
public abstract class VePdfStyle {

	public static final String FONT_NORMAL = "normal";

	private static BaseFont bf;

	static {
		try {
			bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final Map<String, BaseColor> COLOR_MAP = new HashMap<String, BaseColor>() {{
		put("baseColor", new BaseColor(47, 79, 79));
		put("tableHeadCellBgc", new BaseColor(100, 149, 237));	//蓝色
		put("tableBorderColor", new BaseColor(128, 128, 128));  //灰色
	}};

	public static final Map<String, Font> FONT_MAP = new HashMap<String, Font>() {{
		//目录标题
		put("directoryTitle1", new Font(bf, 14, Font.BOLD, new BaseColor(123, 123, 123)));
		put("directoryTitle2", new Font(bf, 12, Font.BOLD, new BaseColor(142, 170, 219)));

		//书签标题
		put("title1", new Font(bf, 26, Font.BOLD, new BaseColor(53, 84, 84)));
		put("title2", new Font(bf, 20, Font.BOLD, new BaseColor(53, 84, 84)));
		put("title3", new Font(bf, 14, Font.BOLD, new BaseColor(53, 84, 84)));

		//其他字体
		put("section1", new Font(bf, 20, Font.BOLD, new BaseColor(43,125,188)));
		put("section2", new Font(bf, 18, Font.BOLD, new BaseColor(43,125,188)));
		put("section3", new Font(bf, 16, Font.BOLD, new BaseColor(43,125,188)));
		put("section4", new Font(bf, 14, Font.BOLD, new BaseColor(43,125,188)));
		put("section5", new Font(bf, 12, Font.BOLD, BaseColor.BLACK));
		put(FONT_NORMAL, new Font(bf, 10, Font.NORMAL, BaseColor.BLACK));

		put("tableHeadCell", new Font(bf, 12, Font.BOLD, BaseColor.BLACK));
		put("tableBodyCell", new Font(bf, 10, Font.BOLD, BaseColor.BLACK));
	}};


}
