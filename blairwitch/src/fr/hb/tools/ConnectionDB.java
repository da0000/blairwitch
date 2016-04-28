package fr.hb.tools;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public final class ConnectionDB {

	private static ConnectionDB instance;
	public EntityManagerFactory emf;

	// Constructor
	public ConnectionDB() {
		this.emf = Persistence.createEntityManagerFactory("blairwitch");
	}

	// Singleton
	public static ConnectionDB getInstance() {
		if (null == instance) {
			instance = new ConnectionDB();
		}
		return instance;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
}
