package immortal;

import java.util.List;

import immortal.database.Database;
import immortal.models.Fare;

public class ServerDriver {

	public static void main(String[] args) {
//	    new Server();
//	    Database.Query.select(Fare.class).where("id", "=", 4).execute();

//        System.out.println(Query.all().delete(Fare.class));

//	    Database.Query(Fare.class).where("id","=",3).select();
//	    Database.Query(Fare.class).all().select();
//	    System.out.println(Database.Query(Fare.class).where("id","=", 2).update(new Fare.Builder().withAmount(115).withVehicleType(33).build()));
//	    Database.Query(Fare.class).insert(new Fare());
//	    System.out.println(Database.Query(Plaza.class).delete());
//	    Database.Query(Fare.class);
//	    System.out.println(Database.Query(Fare.class).insert(new Fare.Builder().withAmount(15).withVehicleType(33).build()));
	    List<Fare> fares = Database.Query(Fare.class).where("amount", "=", "15").limit(21).select();

	    fares.forEach((Fare f) -> {
	        System.out.println(f.getAmount() + " - " + f.getVehicleType());
	    });

	}

}
