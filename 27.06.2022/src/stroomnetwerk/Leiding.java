package stroomnetwerk;

import java.util.HashSet;

/**
 * @invar | getDebiet() > 0
 * @invar |  getBronknoop() == null || getBronknoop().getWeggaande().contains(this)
 * @invar | getDoelknoop() == null || getDoelknoop().getAankomende().contains(this)
 * @invar | (getDoelknoop() == null) == (getBronknoop() == null)
 */
public class Leiding extends Onderdeel {
	
	/**
	 * @invar | debiet > 0
	 */
	int debiet;
	
	/**
	 * @peerObject
	 * @invar | bronknoop == null || bronknoop.getWeggaande().contains(this)
	 * @invar | (bronknoop == null) == (doelknoop == null)
	 */
	Knoop bronknoop;
	
	/**
	 * @peerObject
	 * @invar | doelknoop == null || doelknoop.getAankomende().contains(this)
	 * @invar | (bronknoop == null) == (doelknoop == null)
	 */
	Knoop doelknoop;
	
	/**
	 * @throws IllegalArgumentException | debiet <= 0
	 * @post | getDebiet() == debiet
	 * @post | getBronknoop() == null
	 * @post | getDoelknoop() == null
	 */
	public Leiding(int debiet) {
		if (debiet <= 0)
			throw new IllegalArgumentException("Debiet moet groter zijn dan nul");
		else
			this.debiet = debiet;
		//knopen null -> losse leiding
	}
	
	public int getDebiet() {
		return debiet;
	}
	
	/**
	 * @peerObject
	 */
	public Knoop getBronknoop() {
		return bronknoop;
	}
	
	/**
	 * @peerObject
	 */
	public Knoop getDoelknoop() {
		return doelknoop;
	}
	
	/** bidirectional association
	 * @pre | getBronknoop() == null
	 * @pre | getDoelknoop() == null
	 * @pre | bron != null
	 * @pre | doel != null
	 * @mutates_properties
	 * @post | getBronknoop() == bron
	 * @post | getDoelknoop() == doel
	 * @post | getBronknoop().getWeggaande().contains(this)
	 * @post | getDoelknoop().getAankomende().contains(this)
	 */
	public void koppelLeiding(Knoop bron, Knoop doel) { //koppelen losse leiding
		this.bronknoop = bron;
		this.doelknoop = doel;
		bronknoop.weggaande.add(this);
		doelknoop.aankomende.add(this);
	}
	
	/** bidirectional association
	 * @pre | getBronknoop() != null
	 * @pre | getDoelknoop() != null
	 * @mutates_properties
	 * @post | getBronknoop() == null
	 * @post | getDoelknoop() == null
	 * @post | !old(getBronknoop()).getWeggaande().contains(this)
	 * @post | !old(getDoelknoop()).getAankomende().contains(this)
	 */
	public void ontkoppel( ) {
		bronknoop.weggaande.remove(this);
		doelknoop.aankomende.remove(this);
		this.bronknoop = null;
		this.doelknoop = null;
	}
	
	/**
	 * @creates | result
	 * @post | result != null
	 * @post | getBronknoop() == null || result.contains(getBronknoop())
	 * @post | getDoelknoop() == null || result.contains(getDoelknoop())
	 */
	@Override
	public HashSet<Onderdeel> getGekoppelde() {
		HashSet<Onderdeel> gekoppeld = new HashSet<Onderdeel>();
		if (bronknoop != null)
			gekoppeld.add(bronknoop);
		if (doelknoop != null)
			gekoppeld.add(doelknoop);
		return gekoppeld;
		
	}

	/**
	 * @pre | ander != null
	 * @post | result == (ander instanceof Leiding l && l.getDebiet() == this.getDebiet())
	 */
	@Override
	public boolean gelijkaardig(Onderdeel ander) {
		return ander instanceof Leiding l && l.debiet == debiet;
	}
	

	
}
