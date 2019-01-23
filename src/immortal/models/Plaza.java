package immortal.models;

import immortal.annotations.Column;
import immortal.annotations.Table;
import immortal.constants.Type;

@Table("plaza")
public class Plaza {
	@Column(name="password", type=Type.TEXT)
	private final String password;
	
	private Plaza(Builder builder) {
		if(builder.password == null) {
			throw new IllegalArgumentException("Null value given!");
		}		
		this.password = builder.password;
	}
	
	public String getPassword() {
		return password;
	}
	
	public static final class Builder {
		private String password;
		
		public Builder() { }
		
		public Builder withPassword(String password) {
			this.password = password;
			return this;
		}
		
		public Plaza build() {
			return new Plaza(this);
		}
	}
}
