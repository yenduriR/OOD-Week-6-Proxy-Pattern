package week6;

import java.rmi.*;

public class NetflixMonitor {
	
	NetflixRemote netflixUser;

	public NetflixMonitor(NetflixRemote netflixUser) {
		this.netflixUser = netflixUser;
	}
	
	public void report() {
		
		try {
			System.out.println("Netflix User: " + netflixUser.getName());
			System.out.println("Location: " + netflixUser.getLocation());
			System.out.println("Minutes Watched: " + netflixUser.getMinutesWatched());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
	}
	
	

}
