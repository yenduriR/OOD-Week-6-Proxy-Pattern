package week6;

import java.rmi.*;

public interface NetflixRemote extends Remote {
	
	public String getName() throws RemoteException; 
	
	public String getLocation() throws RemoteException;
	
	public int getMinutesWatched() throws RemoteException;
}
