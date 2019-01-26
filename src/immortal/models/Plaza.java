package immortal.models;

import java.util.Objects;

import immortal.annotations.Column;
import immortal.annotations.Table;

@Table("plaza")
public class Plaza {
	@Column("password")
	private String password;

	public Plaza() { }

	private Plaza(Builder builder) {
		this.password = Objects.requireNonNull(builder.password);
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
