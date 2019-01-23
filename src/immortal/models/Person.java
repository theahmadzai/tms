package immortal.models;

import immortal.annotations.Column;
import immortal.annotations.Table;
import immortal.constants.Gender;
import immortal.constants.Type;

@Table("person")
public class Person {
	@Column(name="cnic", type=Type.TEXT)
	private final String cnic;
	
	@Column(name="name", type=Type.TEXT)
	private final String name;
	
	@Column(name="age", type=Type.NUMERIC)
	private final int age;
	
	@Column(name="gender", type=Type.ENUM)
	private final Gender gender;
	
	private Person(Builder builder) {
		if(builder.cnic == null) {
			throw new IllegalArgumentException("Null value given!");
		}
		this.cnic = builder.cnic;
		this.name = builder.name;
		this.age = builder.age;
		this.gender = builder.gender;
	}
	
	public String getCnic() {
		return cnic;
	}

	public String getName() {
		return name;
	}
	
	public int getAge() {
		return age;
	}

	public Gender getGender() {
		return gender;
	}

	public static final class Builder {
		private String cnic;
		private String name;
		private int age;
		private Gender gender;
		
		public Builder() { }
		
		public Builder withCnic(String cnic) {
			this.cnic = cnic;
			return this;
		}
		
		public Builder withName(String name) {
			this.name = name;
			return this;
		}
		
		public Builder withAge(int age) {
			this.age = age;
			return this;
		}
		
		public Builder withGender(Gender gender) {
			this.gender = gender;
			return this;
		}
		
		public Person build() {
			return new Person(this);
		}
	}
}
