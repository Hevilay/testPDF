package dxh.support.pdf.element;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import dxh.support.pdf.VePdfStyle;
import dxh.support.pdf.annotations.VePdfTableColumn;
import dxh.support.pdf.event.AlternatingBackgroundEvent;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;
import java.util.List;

/**
 * pdf表格元素
 *
 * @author huyu 8979@vetech.cn
 * @date 2017-08-21
 */
public class VePdfTable extends VePdfElement {
	private static final long serialVersionUID = 8660604209568839983L;

	private static Map<Class, List<VePdfTableColumnField>> cachedTableColumnNames = new HashMap<Class, List<VePdfTableColumnField>>();

	private String title;

	private Class recordClass;

	private List data;

	private byte[] imgBytes;

	public VePdfTable() {}

	public VePdfTable(String title, Class recordClass, List data) {
		this.title = title;
		this.recordClass = recordClass;
		this.data = data;
	}

	/**
	 * @param title
	 * @param recordClass
	 * @param data
	 * @param imgBytes	需要添加的图片字节数组
     */
	public VePdfTable(String title, Class recordClass, List data,byte[] imgBytes) {
		this.title = title;
		this.recordClass = recordClass;
		this.data = data;
		this.imgBytes = imgBytes;
	}

	@Override
	public void addToDocument(Document document) throws DocumentException {
		//设置表格标题
		if (StringUtils.isNotEmpty(this.title)) {
			Paragraph title = new Paragraph(this.title, VePdfStyle.FONT_MAP.get("section5"));
			title.setAlignment(Element.ALIGN_CENTER);
			document.add(title);
		}
		//设置表格title（第一列宽度），并获取表格title，排序存放于cachedTableColumnNames中
		PdfPTable table = new PdfPTable(parseHeadColumnSize());
//		table.setSplitLate(true);
//		table.setLockedWidth(true);
//		table.setTotalWidth(48);
//		table.setHorizontalAlignment(Element.ALIGN_LEFT);
		genTableHead(table);
		fillTableBody(table);
		table.setSpacingBefore(10);

		table.setTableEvent(new AlternatingBackgroundEvent());

		document.add(table);
	}

	/**
	 * 创建pdf表头
	 *
	 * @param table
	 */
	private void genTableHead(PdfPTable table) {
		for (VePdfTableColumnField columnField : parseHeadNames(this.recordClass)) {
			table.addCell(getTableHeadCell(columnField.getColumnName()));
		}
		if(null != imgBytes && imgBytes.length>0 && null != data && data.size() > 0){
			int crossColumns = data.size();
			try {
				table.addCell(getTableHeadCell(imgBytes,crossColumns+1));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (BadElementException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 填充pdf表体数据
	 *
	 * @param table
	 */
	private void fillTableBody(PdfPTable table) {
		for (Object o : data) {
			//反射填充table body cell
			try {
				for (VePdfTableColumnField columnField : parseHeadNames(this.recordClass)) {
					Field field = null;
					try {
						field = this.recordClass.getDeclaredField(columnField.getFieldName());
					} catch (NoSuchFieldException e) {
						e.printStackTrace();
					}
					field.setAccessible(true);
					Object fieldValue = FieldUtils.readField(field, o);
					table.addCell(getTableBodyCell(fieldValue.toString(),240/data.size()));
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 第一列宽度
	 * @return
     */
	private float[] parseHeadColumnSize() {
		List<VePdfTableColumnField> columnFields = cachedTableColumnNames.get(this.recordClass);
		if (columnFields == null || columnFields.size() < 1) {
			columnFields = parseHeadNames(this.recordClass);
		}
		float[] res = null;
		if (columnFields != null || columnFields.size() > 0) {

			if(null != imgBytes && imgBytes.length>0 && null != data && data.size() > 0){
				res = new float[columnFields.size()+1];
				for (int i = 0; i < columnFields.size()+1; i++) {
					if(i == columnFields.size()){
						res[i] = 30;
					}else {
						res[i] = columnFields.get(i).getSize();
					}
				}
			}else {
				res = new float[columnFields.size()];
				for (int i = 0; i < columnFields.size(); i++) {
					res[i] = columnFields.get(i).getSize();
				}
			}
			return res;
		}
		return null;
	}

	/**
	 * 解析表格title列（第一列）
	 * @param clazz
	 * @return
     */
	public static List<VePdfTableColumnField> parseHeadNames(Class clazz) {
		if (clazz == null) {
			return Collections.emptyList();
		}
		if (cachedTableColumnNames.containsKey(clazz)) {
			return cachedTableColumnNames.get(clazz);
		}
		List<VePdfTableColumnField> columnFields = new ArrayList<VePdfTableColumnField>();
		//解析recordClass获取表头列名称集合
		//获取类的所有属性
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			//获取该属性的注解
			VePdfTableColumn tableColumnAnno = field.getAnnotation(VePdfTableColumn.class);
			if (tableColumnAnno != null) {
				VePdfTableColumnField columnField = new VePdfTableColumnField();
				columnField.setColumnName(tableColumnAnno.name());
				columnField.setFieldName(field.getName());
				columnField.setSortNum(tableColumnAnno.sortNum());
				columnField.setSize(tableColumnAnno.size());
				columnFields.add(columnField);
			}
		}
		Collections.sort(columnFields);
		cachedTableColumnNames.put(clazz, columnFields);
		return columnFields;
	}

	/**
	 * 给一个中文字符串，返回一个已填充字符串的PdfPCell
	 * @param name
	 * @return
     */
	private static PdfPCell getTableHeadCell(String name) {
		PdfPCell cell = new PdfPCell(new Phrase(name, VePdfStyle.FONT_MAP.get("tableHeadCell")));
		//居中对齐
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		//背景色
		cell.setBackgroundColor(VePdfStyle.COLOR_MAP.get("tableHeadCellBgc"));
		return cell;
	}

	/**
	 * 根据图片数据及跨列数获取一个填充了图片的PdfPCell
	 * @param imgBytes
	 * @param crossColumns
	 * @return
	 * @throws IOException
	 * @throws BadElementException
     */
	private static PdfPCell getTableHeadCell(byte[] imgBytes,int crossColumns) throws IOException, BadElementException {
		PdfPCell cell = new PdfPCell(Image.getInstance(imgBytes),true);
		//设置跨列
		cell.setRowspan(crossColumns);
		cell.setPadding(2.0F);
		cell.setBackgroundColor(new BaseColor(251, 251, 251));
		return cell;
	}

	private static PdfPCell getTableBodyCell(String value,float height) {
		PdfPCell cell = new PdfPCell(new Phrase(value, VePdfStyle.FONT_MAP.get("tableBodyCell")));
		if(height > 0 ){
			cell.setFixedHeight(height);
		}
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		return cell;
	}

	/**
	 * pdf列字段封装对象
	 */
	public static class VePdfTableColumnField implements Serializable, Comparable {
		private static final long serialVersionUID = 7158114424630861801L;

		private String columnName;

		private String fieldName;

		private int sortNum;

		private float size;

		public String getColumnName() {
			return columnName;
		}

		public void setColumnName(String columnName) {
			this.columnName = columnName;
		}

		public String getFieldName() {
			return fieldName;
		}

		public void setFieldName(String fieldName) {
			this.fieldName = fieldName;
		}

		public int getSortNum() {
			return sortNum;
		}

		public void setSortNum(int sortNum) {
			this.sortNum = sortNum;
		}

		public float getSize() {
			return size;
		}

		public void setSize(float size) {
			this.size = size;
		}

		@Override
		public int compareTo(Object o) {
			VePdfTableColumnField columnField = (VePdfTableColumnField) o;
			return Integer.valueOf(this.sortNum).compareTo(columnField.getSortNum());
		}
	}
}
