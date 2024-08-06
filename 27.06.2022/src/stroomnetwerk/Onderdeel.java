package stroomnetwerk;

import java.util.HashSet;

public abstract class Onderdeel {
	
	/**
	 * @creates | result
	 * @post | result != null
	 */
	public abstract HashSet<Onderdeel> getGekoppelde();

	/**
	 * @pre | ander != null
	 */
	public abstract boolean gelijkaardig(Onderdeel ander);

}
