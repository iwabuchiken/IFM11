package ifm11.items;

public class SearchHistory {

	// id in database
	long db_Id;

	String created_at;
	
	String modified_at;

	String keywords;
	
	int all_table;
	
	int by_file_name;
	
	int type;
	
	public SearchHistory(Builder builder) {
		// TODO Auto-generated constructor stub
		
		this.db_Id	= builder.db_Id;

		this.created_at	= builder.created_at;
		
		this.modified_at	= builder.modified_at;

		this.keywords	= builder.keywords;
		
		this.all_table	= builder.all_table;

		this.by_file_name	= builder.by_file_name;
		
		this.type	= builder.type;

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



	public int getAll_table() {
		return all_table;
	}



	public int getBy_file_name() {
		return by_file_name;
	}



	public int getType() {
		return type;
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



	public void setAll_table(int all_table) {
		this.all_table = all_table;
	}



	public void setBy_file_name(int by_file_name) {
		this.by_file_name = by_file_name;
	}



	public void setType(int type) {
		this.type = type;
	}



	public static class Builder {
		
		// id in database
		long db_Id;

		String created_at;
		
		String modified_at;

		String keywords;
		
		int all_table;

		int by_file_name;
		
		int type;
		
		public SearchHistory build() {
			
			return new SearchHistory(this);
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

		public Builder setAll_table(int all_table) {
			this.all_table = all_table; return this;
		}

		public Builder setBy_file_name(int by_file_name) {
			this.by_file_name = by_file_name; return this;
		}

		public Builder setType(int type) {
			this.type = type; return this;
		}

		
	}//public static class Builder
	
}//public class FilterHistory
