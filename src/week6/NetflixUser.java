package week6;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class NetflixUser extends UnicastRemoteObject implements NetflixRemote {

	private String name;
	private String location;
	private int minutesWatched;

	public NetflixUser(String name, String location, int minutesWatched) throws RemoteException {
		this.name = name;
		this.location = location;
		this.minutesWatched = minutesWatched;
	}

	@Override
	public String getName() throws RemoteException {
		return name;
	}

	@Override
	public String getLocation() throws RemoteException {
		return location;
	}

	@Override
	public int getMinutesWatched() throws RemoteException {
		return minutesWatched;
	}
	
	public static void main(String[] args) {
		NetflixUser netflixUser = null;
		
		if (args.length != 3) {
			System.out.println("args.length: " + args.length);
			System.out.println("NetflixUser <name> <location> <minutesWatched>");
			System.exit(1);
		}
		
		int minutesWatched;
		try {
			minutesWatched = Integer.parseInt(args[2]);
			netflixUser = new NetflixUser(args[0], args[1], minutesWatched);
			Naming.rebind("//localhost/netflixuser/" + args[0], netflixUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
