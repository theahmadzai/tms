package immortal;

import immortal.database.Database;
import immortal.models.Fare;

public class ServerDriver {

	public static void main(String[] args) {
//	    new Server();
//	    Database.Query.select(Fare.class).where("id", "=", 4).execute();

//        System.out.println(Query.all().delete(Fare.class));

//	    Database.Query(Fare.class).where("id","=",3).select();
//	    Database.Query(Fare.class).all().select();
//	    Database.Query(Fare.class).where("id","=", 5).update(new Fare());
//	    Database.Query(Fare.class).insert(new Fare());
	    System.out.println(Database.Query(Fare.class).where("id","=",2).delete());
//	    Database.Query(Fare.class);
//	    Database.Query(Fare.class).insert(new Fare.Builder().withAmount(15).withVehicleType(33));
//	    Database.Query(Fare.class).where("id", "=", 2).select();

	}

}
