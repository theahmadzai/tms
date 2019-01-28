package immortal.models;

import immortal.annotations.Column;
import immortal.annotations.Table;

import java.util.Objects;

@Table("plaza")
public class Plaza extends Model {
	@Column("password")
	private String password;

	@Column("name")
    private String name;

	public Plaza() { }

	private Plaza(Builder builder) {
		this.password = Objects.requireNonNull(builder.password);
		this.name = Objects.requireNonNull(builder.name);
	}

	public String getPassword() {
		return password;
	}

	public String getName() {
	    return name;
    }

	public static final class Builder {
		private String password;
		private String name;

		public Builder() { }

		public Builder withPassword(String password) {
			this.password = password;
			return this;
		}

		public Builder withName(String name) {
		    this.name = name;
		    return this;
        }

		public Plaza build() {
			return new Plaza(this);
		}
	}
}
