package immortal.models;

import immortal.annotations.Column;
import immortal.annotations.Table;

@Table("vehicle")
public class Vehicle {
	@Column("number_plate")
	private final String numberPlate;

	@Column("model")
	private final String model;

	@Column("fare_id")
	private final int fareId;

	private Vehicle(Builder builder) {
		if(builder.numberPlate == null) {
			throw new IllegalArgumentException("Null value given!");
		}
		this.numberPlate = builder.numberPlate;
		this.model = builder.model;
		this.fareId = builder.fareId;
	}

	public String getNumberPlate() {
		return numberPlate;
	}

	public String getModel() {
		return model;
	}

	public int getFareId() {
		return fareId;
	}

	public static final class Builder {
		private String numberPlate;
		private String model;
		private int fareId;

		public Builder() {}

		public Builder withNumberPlate(String numberPlate) {
			this.numberPlate = numberPlate;
			return this;
		}

		public Builder withModel(String model) {
			this.model = model;
			return this;
		}

		public Builder withFareId(int fareId) {
			this.fareId = fareId;
			return this;
		}

		public Vehicle build() {
			return new Vehicle(this);
		}
	}
}
