package immortal.models;

import immortal.annotations.Column;
import immortal.annotations.Table;
import immortal.constants.Type;

@Table("fare")
public class Fare {
	@Column(name="amount", type=Type.NUMERIC)
	private final int amount;
	
	@Column(name="vehicle_type", type=Type.NUMERIC)
	private final int vehicleType;
	
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
