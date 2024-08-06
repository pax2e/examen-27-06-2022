package stroomnetwerk;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * @invar | getWeggaande() != null
 * @invar | getAankomende() != null
 * @invar | getWeggaande().stream().allMatch(l -> l.getBronknoop() == this)
 * @invar | getAankomende().stream().allMatch(l -> l.getDoelknoop() == this)
 */
public class Knoop extends Onderdeel {
	
	/**
	 * @representationObject
	 * @peerObjects
	 * @invar | aankomende != null
	 * @invar | aankomende.stream().allMatch(l -> l != null && l.getDoelknoop() == this)
	 */
	HashSet<Leiding> aankomende;

	/**
	 * @representationObject
	 * @peerObjects
	 * @invar | weggaande != null
	 * @invar | weggaande.stream().allMatch(l -> l != null && l.getBronknoop() == this)
	 */
	HashSet<Leiding> weggaande;
	
	/**
	 * @post | getAankomende().isEmpty()
	 * @post | getWeggaande().isEmpty()
	 */
	public Knoop() {
	this.aankomende = new HashSet<Leiding>();
	this.weggaande = new HashSet<Leiding>();
	}
	
	/**
	 * @peerObjects
	 * @creates | result
	 * @post | result != null
	 */
	public Set<Leiding> getAankomende() {
		return Set.copyOf(aankomende);
	}
	
	/**
	 * @peerObjects
	 * @creates | result
	 * @post | result != null
	 */
	public Set<Leiding> getWeggaande() {
		return Set.copyOf(weggaande);
	}
	
	/**
	 * @creates | result
	 * @post | result != null
	 * @post | getWeggaande().stream().allMatch(l -> result.contains(l)) && getAankomende().stream().allMatch(l -> result.contains(l))
	 */
	@Override
	public HashSet<Onderdeel> getGekoppelde() {
		HashSet<Onderdeel> gekoppeld = new HashSet<Onderdeel>();
		for (Leiding leiding : aankomende)
			gekoppeld.add(leiding);
		for (Leiding leiding : weggaande)
			gekoppeld.add(leiding);
		return gekoppeld;
	}
	
	/**
	 * @pre | ander != null
	 * @post | result == (ander instanceof Knoop k && k.getAankomende() == getAankomende() && k.getWeggaande() == getWeggaande())
	 */
	@Override
	public boolean gelijkaardig(Onderdeel ander) {
		return ander instanceof Knoop k && k.aankomende.size() == aankomende.size() && k.weggaande.size() == weggaande.size();
	}
	
	
	public Iterator<Knoop> stroomafwaartseKnopenIterator(){
		return new Iterator<Knoop>() {
			Iterator<Leiding> weggaandeLeidingIterator = weggaande.iterator();
			@Override
			public boolean hasNext() {
				return weggaandeLeidingIterator.hasNext();
			}
			
			@Override
			public Knoop next() {
				return weggaandeLeidingIterator.next().getDoelknoop();
			}
		};
	}
	
	public void tegenoverliggendeKnopen(Consumer<? super Knoop> consumer) {
		for (Leiding leiding : weggaande)
			consumer.accept(leiding.getDoelknoop());
		for (Leiding leiding : aankomende)
			consumer.accept(leiding.getBronknoop());
	}
	
	public Stream<Knoop> stroomafwaartseStream() {
		return weggaande.stream().map(l -> l.getDoelknoop()).flatMap(k -> Stream.concat(Stream.of(k), k.stroomafwaartseStream()));
	}
}
