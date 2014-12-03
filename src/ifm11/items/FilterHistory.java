package ifm11.items;

public class FilterHistory {

	// id in database
	long db_Id;

	String created_at;
	
	String modified_at;

	String keywords;
	
	int operator;
	
	String op_label;

	public FilterHistory(Builder builder) {
		// TODO Auto-generated constructor stub
		
		this.db_Id	= builder.db_Id;

		this.created_at	= builder.created_at;
		
		this.modified_at	= builder.modified_at;

		this.keywords	= builder.keywords;
		
		this.operator	= builder.operator;
		
		this.op_label	= builder.op_label;

	}

	public String getOp_label() {
		return op_label;
	}

	public void setOp_label(String op_label) {
		this.op_label = op_label;
	}

	public long getDb_Id() {
		return db_Id;
	}

	public String getCreated_at() {
		return created_at;
	}

	public String getModified_at() {
		return modified_at;
	}

	public String getKeywords() {
		return keywords;
	}

	public int getOperator() {
		return operator;
	}

	public void setDb_Id(long db_Id) {
		this.db_Id = db_Id;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public void setModified_at(String modified_at) {
		this.modified_at = modified_at;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public void setOperator(int operator) {
		this.operator = operator;
	}
	
	public static class Builder {
		
		// id in database
		long db_Id;

		String created_at;
		
		String modified_at;

		String keywords;
		
		int operator;

		String op_label;
		
		public FilterHistory build() {
			
			return new FilterHistory(this);
		}
		
		public Builder setOp_label(String op_label) {
			this.op_label = op_label; return this;
		}

		public Builder setDb_Id(long db_Id) {
			this.db_Id = db_Id; return this;
		}

		public Builder setCreated_at(String created_at) {
			this.created_at = created_at; return this;
		}

		public Builder setModified_at(String modified_at) {
			this.modified_at = modified_at; return this;
		}

		public Builder setKeywords(String keywords) {
			this.keywords = keywords; return this;
		}

		public Builder setOperator(int operator) {
			this.operator = operator; return this;
		}

	}//public static class Builder
	
}//public class FilterHistory
