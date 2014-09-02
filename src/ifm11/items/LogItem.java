package ifm11.items;

public class LogItem {

	String	date;
	
	String	time;
	
	String	method;
	
	int		line;
	
	String	text;

	
	
	public LogItem(Builder builder) {
		// TODO Auto-generated constructor stub
		this.date	= builder.date;
		
		this.time	= builder.time;
		
		this.method	= builder.method;;
		
		this.line	= builder.line;
		
		this.text	= builder.text;

	}



	public String getDate() {
		return date;
	}



	public String getTime() {
		return time;
	}



	public String getMethod() {
		return method;
	}



	public int getLine() {
		return line;
	}



	public String getText() {
		return text;
	}



	public void setDate(String date) {
		this.date = date;
	}



	public void setTime(String time) {
		this.time = time;
	}



	public void setMethod(String method) {
		this.method = method;
	}



	public void setLine(int line) {
		this.line = line;
	}



	public void setText(String text) {
		this.text = text;
	}



	public static class Builder {

		String	date;
		
		String	time;
		
		String	method;
		
		int		line;
		
		String	text;

		public LogItem build() {
			
			return new LogItem(this);
			
		}
		
		public Builder setDate(String date) {
			this.date = date; return this;
		}

		public Builder setTime(String time) {
			this.time = time; return this;
		}

		public Builder setMethod(String method) {
			this.method = method; return this;
		}

		public Builder setLine(int line) {
			this.line = line; return this;
		}

		public Builder setText(String text) {
			this.text = text; return this;
		}

		
	}
	
}
