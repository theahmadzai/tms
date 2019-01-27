package immortal.models;

import immortal.annotations.Column;
import immortal.annotations.Table;

@Table("fare")
public class Fare extends Model {
	@Column("amount")
	private int amount;

	@Column("vehicle_type")
	private int vehicleType;

	public Fare() { }

	private Fare(Builder builder) {
		this.amount = builder.amount;
		this.vehicleType = builder.vehicleType;
	}

	public int getAmount() {
		return amount;
	}

	public int getVehicleType() {
		return vehicleType;
	}

	public static final class Builder {
		private int amount;
		private int vehicleType;

		public Builder() {}

		public Builder withAmount(int amount) {
			this.amount = amount;
			return this;
		}

		public Builder withVehicleType(int vehicleType) {
			this.vehicleType = vehicleType;
			return this;
		}

		public Fare build() {
			return new Fare(this);
		}
	}
}
