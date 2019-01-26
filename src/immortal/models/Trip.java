package immortal.models;

import immortal.annotations.Column;
import immortal.annotations.Table;

@Table("trip")
public class Trip {
	@Column("plaza_id")
	private int plazaId;

	@Column("person_id")
	private int personId;

	@Column("vehicle_id")
	private int vehicleId;

	@Column("date")
	private String date;

	public Trip() { }

	private Trip(Builder builder) {
		if(builder.date == null) {
			throw new IllegalArgumentException("Null value given!");
		}
		this.plazaId = builder.plazaId;
		this.personId = builder.personId;
		this.vehicleId = builder.vehicleId;
		this.date = builder.date;
	}

	public int getPlazaId() {
		return plazaId;
	}

	public int getPersonId() {
		return personId;
	}

	public int getVehicleId() {
		return vehicleId;
	}

	public String getDate() {
		return date;
	}

	public static final class Builder {
		private int plazaId;
		private int personId;
		private int vehicleId;
		private String date;

		public Builder() { }

		public Builder withPlazaId(int plazaId) {
			this.plazaId = plazaId;
			return this;
		}

		public Builder withPersonId(int personId) {
			this.personId = personId;
			return this;
		}

		public Builder withVehicleId(int vehicleId) {
			this.vehicleId = vehicleId;
			return this;
		}

		public Builder withDate(String date) {
			this.date = date;
			return this;
		}

		public Trip build() {
			return new Trip(this);
		}
	}
}
