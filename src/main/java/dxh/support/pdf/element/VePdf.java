package dxh.support.pdf.element;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * pdf主体
 *
 * @author huyu 8979@vetech.cn
 * @date 2017-08-22
 */
public class VePdf implements Serializable {
	private static final long serialVersionUID = 1205148183714775315L;

	private final List<Map> directoryTitleList;

	private final String title;

	private final String subTitle;

	private final String footTitle;

	private final String footSubTitle;

	private final List<VePdfSection> sections;

	private final boolean bookmark;

	private final boolean footer;

	private final boolean bgPic;

	private final byte[] bgPicData;

	private VePdf(VePdfBuilder builder) {
		this.title = builder.title;
		this.subTitle = builder.subTitle;
		this.sections = builder.sections;
		this.footTitle = builder.footTitle;
		this.footSubTitle = builder.footSubTitle;
		this.bookmark = builder.bookmark;
		this.footer = builder.footer;
		this.bgPic = builder.bgPic;
		this.bgPicData = builder.bgPicData;
		this.directoryTitleList = builder.directoryTitleList;
	}

	public String getTitle() {
		return title;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public String getFootTitle() {
		return footTitle;
	}

	public String getFootSubTitle() {
		return footSubTitle;
	}

	public List<VePdfSection> getSections() {
		return sections;
	}

	public boolean isBookmark() {
		return bookmark;
	}

	public boolean isFooter() {
		return footer;
	}

	public boolean isBgPic() {
		return bgPic;
	}

	public byte[] getBgPicData() {
		return bgPicData;
	}

	public List<Map> getDirectoryTitleList() {
		return directoryTitleList;
	}

	//=============builder============

	public static class VePdfBuilder {
		private final String title;

		private final String subTitle;

		private String footTitle;

		private String footSubTitle;

		private List<Map> directoryTitleList;

		private final List<VePdfSection> sections;

		private boolean bookmark;

		private boolean footer;

		private boolean bgPic;

		private byte[] bgPicData;

		public VePdfBuilder(String title, String subTitle, List<VePdfSection> sections) {
			this.title = title;
			this.subTitle = subTitle;
			this.sections = sections;
		}

		public VePdfBuilder footTitle(String footTitle) {
			this.footTitle = footTitle;
			return this;
		}

		public VePdfBuilder footSubTitle(String footSubTitle) {
			this.footSubTitle = footSubTitle;
			return this;
		}

		public VePdfBuilder bookmark(boolean bookmark) {
			this.bookmark = bookmark;
			return this;
		}

		public VePdfBuilder footer(boolean footer) {
			this.footer = footer;
			return this;
		}

		public VePdfBuilder bgPicData(byte[] bgPicData) {
			this.bgPicData = bgPicData;
			return this;
		}

		public VePdfBuilder bgPic(boolean bgPic) {
			this.bgPic = bgPic;
			return this;
		}
		public VePdf build() {
			return new VePdf(this);
		}

		public List<Map> getDirectoryTitleList() {
			return directoryTitleList;
		}

		public VePdfBuilder setDirectoryTitleList(List<Map> directoryTitleList) {
			this.directoryTitleList = directoryTitleList;
			return this;
		}
	}
}
