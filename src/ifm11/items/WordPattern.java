package ifm11.items;

public class WordPattern {

	// id in database
	long db_Id;

	String created_at;
	
	String modified_at;

	String word;
	
	String table_name;

	int used;
	
	String used_at;
	
	public WordPattern
	(Builder builder) {
		// TODO Auto-generated constructor stub
		
		this.db_Id	= builder.db_Id;

		this.created_at	= builder.created_at;
		
		this.modified_at	= builder.modified_at;

		this.word	= builder.word;
	
		this.used	= builder.used;
		
		this.used_at	= builder.used_at;
		
//		this.table_name	= builder.table_name;
		
	}



	public int getUsed() {
		return used;
	}



	public String getUsed_at() {
		return used_at;
	}



	public void setUsed(int used) {
		this.used = used;
	}



	public void setUsed_at(String used_at) {
		this.used_at = used_at;
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



	public String getWord() {
		return word;
	}



	public String getTable_name() {
		return table_name;
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



	public void setWord(String word) {
		this.word = word;
	}



	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}



	public static class Builder {

		// id in database
		long db_Id;

		String created_at;
		
		String modified_at;

		String word;
		
		String table_name;

		int used;
		
		String used_at;

		public WordPattern build() {
			
			return new WordPattern(this);
			
		}

		public Builder setUsed(int used) {
			this.used = used; return this;
		}

		public Builder setUsed_at(String used_at) {
			this.used_at = used_at; return this;
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

		public Builder setWord(String word) {
			this.word = word; return this;
		}

		public Builder setTable_name(String table_name) {
			this.table_name = table_name; return this;
		}
		
		
		
	}//public static class Builder

}//public class WordPattern
