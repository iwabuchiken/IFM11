package ifm11.items;

public class TI {
	/*----------------------------
	 * Class fields
		----------------------------*/
	// id in database
	long db_Id;

	// Id: in MediaStore
	long fileId;
	
	String created_at;
	
	String modified_at;
	
	// Path
	String file_path;
	
	// Name
	String file_name;

	// Date: The value MediaStore has internally
	String date_added;
//	long date_added;
	
	// Modified: The value MediaStore has internally
	String date_modified;
	
	// Memo
	String memo;
	
	// Tags
	String tags;
	
	String last_viewed_at;
	
	String table_name;

	public TI(Builder builder) {
		// TODO Auto-generated constructor stub
		
		// Id
		this.db_Id	= builder.db_Id;
		
		// file id
		this.fileId	= builder.fileId;
		
		// Path
		this.file_path	= builder.file_path;
		
		// Name
		this.file_name	= builder.file_name;

		// Date
		this.date_added	= builder.date_added;
		
		// Modified
		this.date_modified	= builder.date_modified;
		
		// Memo
		this.memo	= builder.memo;
		
		// Tags
		this.tags	= builder.tags;
		
		this.last_viewed_at	= builder.last_viewed_at;
		
		this.table_name	= builder.table_name;
		
		this.created_at	= builder.created_at;
		
		this.modified_at	= builder.modified_at;
		
	}

	
	
	public long getDb_Id() {
		return db_Id;
	}

	public void setDb_Id(long db_Id) {
		this.db_Id = db_Id;
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

	public long getFileId() {
		return fileId;
	}

	public String getFile_path() {
		return file_path;
	}

	public String getDate_added() {
		return date_added;
	}

	public String getDate_modified() {
		return date_modified;
	}

	public String getMemo() {
		return memo;
	}

	public String getTags() {
		return tags;
	}

	public void setFileId(long fileId) {
		this.fileId = fileId;
	}

	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}

	public void setDate_added(String date_added) {
		this.date_added = date_added;
	}

	public void setDate_modified(String date_modified) {
		this.date_modified = date_modified;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getFile_name() {
		return file_name;
	}


	public String getLast_viewed_at() {
		return last_viewed_at;
	}

	
	public void setLast_viewed_at(String last_viewed_at) {
		this.last_viewed_at = last_viewed_at;
	}

	public String getTable_name() {
		return table_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}


	public static class Builder {

		// id in database
		long db_Id;
		
		// Id: in MediaStore
		long fileId;
		
		String created_at;
		
		String modified_at;

		// Path
		String file_path;
		
		// Name
		String file_name;

		// Date
		String date_added;
		
		// Modified
		String date_modified;
		
		// Memo
		String memo;
		
		// Tags
		String tags;
		
		String last_viewed_at;
		
		String table_name;

		public TI build() {
			
			return new TI(this);
			
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



		public Builder setFileId(long fileId) {
			this.fileId = fileId; return this;
		}

		public Builder setFile_path(String file_path) {
			this.file_path = file_path; return this;
		}

		public Builder setFile_name(String file_name) {
			this.file_name = file_name; return this;
		}

		public Builder setDate_added(String date_added) {
			this.date_added = date_added; return this;
		}

		public Builder setDate_modified(String date_modified) {
			this.date_modified = date_modified; return this;
		}

		public Builder setMemo(String memo) {
			this.memo = memo; return this;
		}

		public Builder setTags(String tags) {
			this.tags = tags; return this;
		}

		public Builder setLast_viewed_at(String last_viewed_at) {
			this.last_viewed_at = last_viewed_at; return this;
		}

		public Builder setTable_name(String table_name) {
			this.table_name = table_name; return this;
		}
		
		
		
	}
	
}//public class ThumbnailItem
