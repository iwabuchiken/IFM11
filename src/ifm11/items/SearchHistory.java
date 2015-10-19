package ifm11.items;

public class SearchHistory {

	// id in database
	long db_Id;

	String created_at;
	
	String modified_at;

	String keywords;
	
	int all_table;
	
	String op_label;

	public SearchHistory(Builder builder) {
		// TODO Auto-generated constructor stub
		
		this.db_Id	= builder.db_Id;

		this.created_at	= builder.created_at;
		
		this.modified_at	= builder.modified_at;

		this.keywords	= builder.keywords;
		
		this.all_table	= builder.all_table;
		
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
		return all_table;
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
		this.all_table = operator;
	}
	
	public static class Builder {
		
		// id in database
		long db_Id;

		String created_at;
		
		String modified_at;

		String keywords;
		
		int all_table;

		String op_label;
		
		public SearchHistory build() {
			
			return new SearchHistory(this);
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

		public Builder setOperator(int all_table) {
			this.all_table = all_table; return this;
		}

	}//public static class Builder
	
}//public class FilterHistory
