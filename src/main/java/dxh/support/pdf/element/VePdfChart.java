package dxh.support.pdf.element;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;

import java.io.IOException;

/**
 * pdf图表元素抽象类
 *
 * @author huyu 8979@vetech.cn
 * @date 2017-08-21
 */
public abstract class VePdfChart extends VePdfElement {
	private static final long serialVersionUID = -8932514617052096305L;

	private byte[] data;

	public VePdfChart() {}

	public VePdfChart(byte[] data) {
		this.data = data;
	}

	@Override
	public void addToDocument(Document document) throws DocumentException {
		try {
			Image image = Image.getInstance(data);
			document.add(image);
			//图片添加完后翻新页，防止页面内容错位
			document.newPage();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
