package immortal.models;

import immortal.annotations.Column;
import immortal.annotations.Table;

@Table("vehicle")
public class Vehicle extends Model {
	@Column("number_plate")
	private String numberPlate;

	@Column("fare_id")
	private int fareId;

	public Vehicle() { }

	public Vehicle(String numberPlate, int fareId) {
	    this.numberPlate = numberPlate;
	    this.fareId = fareId;
	}

	private Vehicle(Builder builder) {
		if(builder.numberPlate == null) {
			throw new IllegalArgumentException("Null value given!");
		}
		this.numberPlate = builder.numberPlate;
		this.fareId = builder.fareId;
	}

	public String getNumberPlate() {
		return numberPlate;
	}

	public int getFareId() {
		return fareId;
	}

	public static final class Builder {
		private String numberPlate;
		private int fareId;

		public Builder() {}

		public Builder withNumberPlate(String numberPlate) {
			this.numberPlate = numberPlate;
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
