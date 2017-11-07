package dxh.support.pdf;



import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfAction;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfOutline;
import com.itextpdf.text.pdf.PdfWriter;
import dxh.exceptions.PdfException;
import dxh.support.pdf.element.VePdf;
import dxh.support.pdf.element.VePdfDirectoryTitle;
import dxh.support.pdf.element.VePdfElement;
import dxh.support.pdf.element.VePdfSection;
import dxh.support.pdf.event.PdfReportM1HeaderFooter;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * pdf报表工具类
 *
 * @author huyu 8979@vetech.cn
 * @date 2017-08-21
 */
public class VePdfSupport {

	private static final String DEFAULT_TITLE = "差旅分析报告";
	private static final String DEFAULT_TITLE_EN = "(Travel analysis report)";

	/**
	 * 生成pdf文档
	 * @param vePdf					pdf封装对象
	 * @param destFile				pdf目标文件
	 * @throws PdfException        pdf业务异常
	 */
	public static void genPdf(VePdf vePdf, File destFile) throws PdfException {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(destFile);
			genPdf(vePdf, fos);
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new PdfException(e.getCause());
		} catch (IOException e) {
			e.printStackTrace();
			throw new PdfException(e.getCause());
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
					throw new PdfException(e.getCause());
				}
			}
		}
	}

	/**
	 * 生成pdf文档
	 * @param vePdf					pdf封装对象
	 * @param dest					输出流
	 * @throws PdfException        pdf业务异常
	 */
	public static void genPdf(VePdf vePdf, OutputStream dest) throws PdfException {
		Rectangle a4 = PageSize.A4;
		Document document = new Document(a4, 20, 20, 30, 30);
		PdfWriter writer = null;
		try {
			//从文档获取pdf输出流
			writer = PdfWriter.getInstance(document, dest);

			//页脚设置
			if (vePdf.isFooter()) {
				PdfReportM1HeaderFooter headerFooter = new PdfReportM1HeaderFooter();
				writer.setBoxSize("art", a4);
				writer.setPageEvent(headerFooter);
			}
			writer.setFullCompression();
			writer.setPdfVersion(PdfWriter.VERSION_1_4);
		} catch (DocumentException e) {
			e.printStackTrace();
			throw new PdfException(e.getCause());
		}
		document.open();

		try {
			//渲染首页
			firstPage(document, vePdf.getTitle(), vePdf.getSubTitle(), vePdf.getFootTitle(), vePdf.getFootSubTitle());

			//首页渲染完成后换新页渲染pdf内容页
			document.newPage();

			//添加目录
			setDirectoryTitle(vePdf.getDirectoryTitleList(),document);
			//渲染内容页
			contentPage(document, vePdf.getSections(), vePdf.isBookmark());
		} catch (DocumentException e) {
			e.printStackTrace();
			throw new PdfException(e.getCause());
		}

		//创建标签
		if (vePdf.isBookmark()) {
			PdfContentByte cb = writer.getDirectContent();
			PdfOutline rootOutline = cb.getRootOutline();
			Map<String, PdfOutline> outlineMap = new HashMap<String, PdfOutline>();
			createBookmark(vePdf.getSections(), rootOutline, outlineMap);
		}

		document.close();
	}

	/**
	 * 首页渲染
	 *
	 * @param document			pdf文档主体
	 * @param title				主标题
	 * @param subTitle			副标题
	 * @param footTitle			脚标题
	 * @param footSubTitle		脚副标题
	 * @throws DocumentException
	 */
	private static void firstPage(Document document, String title, String subTitle, String footTitle, String footSubTitle) throws DocumentException {
		//首页占位留白
		Paragraph blank = new Paragraph(" ", VePdfStyle.FONT_MAP.get("title1"));
		for (int i = 0; i < 5; i++) {
			document.add(blank);
		}
		if (StringUtils.isEmpty(title)) {
			title = DEFAULT_TITLE;
		}
		if (StringUtils.isEmpty(subTitle)) {
			subTitle = DEFAULT_TITLE_EN;
		}
		//首页主标题
		Paragraph paragraph = new Paragraph(title, VePdfStyle.FONT_MAP.get("title1"));
		paragraph.setAlignment(Element.ALIGN_CENTER);
		document.add(paragraph);
		//首页副标题
		paragraph = new Paragraph(subTitle, VePdfStyle.FONT_MAP.get("title2"));
		paragraph.setAlignment(Element.ALIGN_CENTER);
		document.add(paragraph);

		//首页占位留白
		for (int i = 0; i < 12; i++) {
			document.add(blank);
		}
		//脚标题
		if (StringUtils.isNotEmpty(footTitle)) {
			paragraph = new Paragraph(footTitle, VePdfStyle.FONT_MAP.get("title3"));
			paragraph.setAlignment(Element.ALIGN_CENTER);
			document.add(paragraph);
			if (StringUtils.isNotEmpty(footSubTitle)) {
				paragraph = new Paragraph(footSubTitle, VePdfStyle.FONT_MAP.get("title3"));
				paragraph.setAlignment(Element.ALIGN_CENTER);
				document.add(paragraph);
			}
		}
	}

	/**
	 * 内容页面渲染
	 * @param document				pdf文档主体
	 * @param sections				pdf数据元素
	 * @param bookmark				是否建立标签
	 * @throws DocumentException
	 */
	private static void contentPage(Document document, List<VePdfSection> sections, boolean bookmark) throws DocumentException {
		if (sections == null || sections.size() < 1) {
			return;
		}
		for (VePdfSection section : sections) {
			String fontKey = "section" + section.getLevel();
			Font font = VePdfStyle.FONT_MAP.get(fontKey);
			if (font == null) {
				font = VePdfStyle.FONT_MAP.get(VePdfStyle.FONT_NORMAL);
			}
			Chunk chunk = new Chunk(section.getName(), font);
			//标签埋点
			if (bookmark) {
				chunk.setLocalDestination(section.getCode());
			}
			Paragraph paragraph = new Paragraph(chunk);
			document.add(paragraph);

			List<VePdfElement> elements = section.getElements();
			if (elements != null && elements.size() > 0) {
				for (VePdfElement element : elements) {
					element.addToDocument(document);
				}
			}

			List<VePdfSection> children = section.getChildren();
			if (children != null && children.size() > 0) {
				contentPage(document, children, bookmark);
			}
		}
	}

	/**
	 * 创建标签
	 * @param sections			pdf元数据集
	 * @param rootOutline		pdf文档父outline
	 * @param outlineMap		缓存
	 */
	private static void createBookmark(List<VePdfSection> sections, PdfOutline rootOutline, Map<String, PdfOutline> outlineMap) {
		for (VePdfSection section : sections) {
			PdfOutline parentPdfOutline = section.getParent() == null ? rootOutline : outlineMap.get(section.getParent().getCode());
			PdfOutline currentOutline = new PdfOutline(parentPdfOutline,	PdfAction.gotoLocalPage(section.getCode(), false), section.getName());

			outlineMap.put(section.getCode(), currentOutline);
			currentOutline.setOpen(false);

			List<VePdfSection> children = section.getChildren();
			if (children != null && children.size() > 0) {
				createBookmark(children, currentOutline, outlineMap);
			}
		}
	}

	/**
	 * 添加目录
	 * @param list	目录内容list
	 * 格式：
	 *      title   -- String
	 *      subList -- List<Map>
	 *          title -- String
	 *          subList -- List<Map>
	 * @param document
	 * @throws DocumentException
     */
	public static void setDirectoryTitle(List<Map> list, Document document) throws DocumentException {
		VePdfDirectoryTitle vePdfDirectoryTitle = new VePdfDirectoryTitle();
		vePdfDirectoryTitle.setTitleList(list);
		vePdfDirectoryTitle.addToDocument(document);
	}
}
