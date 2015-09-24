package ifm11.items;

public class HistUpload {

	// id in database
	long _id;

	String created_at;
	
	String modified_at;

	long db_id;
	
	String file_name;
	
	String file_path;
	
	public HistUpload(Builder builder) {
		// TODO Auto-generated constructor stub
		
		this._id	= builder._id;

		this.created_at	= builder.created_at;
		
		this.modified_at	= builder.modified_at;

		this.db_id	= builder.db_id;
		
		this.file_name	= builder.file_name;
		
		this.file_path	= builder.file_path;

	}

	

	public long get_id() {
		return _id;
	}



	public long getDb_id() {
		return db_id;
	}



	public String getFile_name() {
		return file_name;
	}



	public String getFile_path() {
		return file_path;
	}



	public void set_id(long _id) {
		this._id = _id;
	}



	public void setDb_id(long db_id) {
		this.db_id = db_id;
	}



	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}



	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}



	public String getCreated_at() {
		return created_at;
	}

	public String getModified_at() {
		return modified_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public void setModified_at(String modified_at) {
		this.modified_at = modified_at;
	}

	public static class Builder {
		
		// id in database
		long _id;

		String created_at;
		
		String modified_at;

		long db_id;
		
		String file_name;
		
		String file_path;

		public HistUpload build() {
			
			return new HistUpload(this);
		}
		
		
		
		public Builder set_id(long _id) {
			this._id = _id;	return this;
		}



		public Builder setDb_id(long db_id) {
			this.db_id = db_id;	return this;
		}



		public Builder setFile_name(String file_name) {
			this.file_name = file_name;	return this;
		}



		public Builder setFile_path(String file_path) {
			this.file_path = file_path;	return this;
		}



		public Builder setCreated_at(String created_at) {
			this.created_at = created_at; return this;
		}

		public Builder setModified_at(String modified_at) {
			this.modified_at = modified_at; return this;
		}

	}//public static class Builder
	
}//public class FilterHistory
