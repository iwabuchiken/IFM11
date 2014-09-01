package ifm11.items;


public class ListItem {

	String	text;
	
	int		iconID;
	
	int		textColor_ID;

	public ListItem(Builder builder) {
		// TODO Auto-generated constructor stub
		
		this.text	= builder.text;
		
		this.iconID	= builder.iconID;
		
		this.textColor_ID	= builder.textColor_ID;
		
	}

	public int getTextColor_ID() {
		return textColor_ID;
	}

	public void setTextColor_ID(int textColor_ID) {
		this.textColor_ID = textColor_ID;
	}

	public String getText() {
		return text;
	}

	public int getIconID() {
		return iconID;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setIconID(int iconID) {
		this.iconID = iconID;
	}
	
	public static class Builder {
		
		String	text;
		
		int		iconID;

		int		textColor_ID;
		
		public ListItem build() {
			
			return new ListItem(this);
			
		}

		public Builder setTextColor_ID(int textColor_ID) {
			this.textColor_ID = textColor_ID; return this;
		}

		public Builder setText(String text) {
			this.text = text; return this;
		}

		public Builder setIconID(int iconID) {
			this.iconID = iconID; return this;
		}

		
		
	}

	
}
