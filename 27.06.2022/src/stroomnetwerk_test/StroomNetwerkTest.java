package stroomnetwerk_test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.*;

import org.junit.jupiter.api.Test;

import stroomnetwerk.*;

class StroomNetwerkTest {

	@Test
	void test() {
		assertThrows(IllegalArgumentException.class, () -> new Leiding(-1));
		Leiding l1 = new Leiding(4);
		assertEquals(4, l1.getDebiet());
		assertNull(l1.getBronknoop());
		assertNull(l1.getDoelknoop());
		
		assertTrue(l1.getGekoppelde().equals(new HashSet<Knoop>()));
		
		
		Knoop bron = new Knoop();
		Knoop doel = new Knoop();
		assertTrue(bron.getGekoppelde().equals(new HashSet<Knoop>()));
		assertTrue(doel.getGekoppelde().equals(new HashSet<Knoop>()));
		
		l1.koppelLeiding(bron, doel);
		
		assertEquals(bron, l1.getBronknoop());
		assertEquals(doel, l1.getDoelknoop());
		assertEquals(4, l1.getDebiet());
		HashSet<Knoop>expected = new HashSet<Knoop>();
		expected.add(bron);
		expected.add(doel);
		HashSet<Leiding>verwacht = new HashSet<Leiding>();
		verwacht.add(l1);
		assertTrue(l1.getGekoppelde().equals(expected));
		assertTrue(bron.getGekoppelde().equals(verwacht));
		assertTrue(doel.getGekoppelde().equals(verwacht));
		assertTrue(l1.getBronknoop().getWeggaande().contains(l1));
		assertTrue(l1.getDoelknoop().getAankomende().contains(l1));
		
		l1.ontkoppel();
		assertNull(l1.getBronknoop());
		assertNull(l1.getDoelknoop());
		assertTrue(l1.getGekoppelde().equals(new HashSet<Knoop>()));
		assertFalse(bron.getWeggaande().contains(l1));
		assertFalse(doel.getAankomende().contains(l1));
		
		
		Leiding l2 = new Leiding(10);
		Leiding l3 = new Leiding(10);
		
		assertTrue(l2.gelijkaardig(l3));
		assertFalse(l2.gelijkaardig(l1));
		assertFalse(l2.gelijkaardig(bron));
		
		assertTrue(bron.gelijkaardig(doel));
		assertFalse(bron.gelijkaardig(l1));


		
	}
	
	@Test
	void extra() {
		Knoop bron = new Knoop();
		Knoop doel1 = new Knoop();
		Knoop doel2 = new Knoop();
		Knoop oorsprong = new Knoop();
		Leiding l1 = new Leiding(4);
		Leiding l2 = new Leiding(4);
		Leiding l3 = new Leiding(9);
		l1.koppelLeiding(bron, doel1);
		l2.koppelLeiding(bron, doel2);
		l3.koppelLeiding(oorsprong, bron);
		
		HashSet<Knoop> stroomafwaartse = new HashSet<Knoop>();
		for (Iterator<Knoop> i = bron.stroomafwaartseKnopenIterator(); i.hasNext(); stroomafwaartse.add(i.next()));
		assertEquals(Set.of(doel1, doel2), stroomafwaartse);
		
		
		HashSet<Knoop> tegenoverliggende = new HashSet<Knoop>();
		bron.tegenoverliggendeKnopen(k -> tegenoverliggende.add(k));
		assertEquals(Set.of(oorsprong, doel1, doel2), tegenoverliggende);
		
		assertEquals(Set.of(bron, doel1, doel2), oorsprong.stroomafwaartseStream().collect(Collectors.toSet()));

	}

}
