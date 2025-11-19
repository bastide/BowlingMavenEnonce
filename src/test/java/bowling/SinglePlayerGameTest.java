package bowling;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SinglePlayerGameTest {

	private PartieMonoJoueur partie;

	@BeforeEach
	void setUp() {
		partie = new PartieMonoJoueur();
	}

	/**
	 * Si on envoie toutes les 20 boules dans la rigole, le score final est 0
	 */
	@Test
	void toutDansLaRigole() {
		lancerPlusieurs(20, 0);
		assertEquals(0, partie.score());
		assertTrue(partie.estTerminee());
	}

	@Test
	void uneQuilleAChaqueLancer() {
		lancerPlusieurs(20, 1);
		assertEquals(20, partie.score());
		assertTrue(partie.estTerminee());
	}

	@Test
	void unSpare() {
		faireUnSpare(); // 10 + 3
		partie.enregistreLancer(3); // 3
		assertEquals(16, partie.score());
		assertFalse(partie.estTerminee());
	}

	@Test
	void deuxStrikes() {
		faireUnStrike(); // 10 + 10 + 4
		faireUnStrike(); // 10 + 4 + 3
		partie.enregistreLancer(4); // 4
		partie.enregistreLancer(3); // 3
		assertEquals((10 + 10 + 4) + (10 + 4 + 3) + 4 + 3, partie.score());
	}

	@Test
	void testOneStrike() {
		faireUnStrike(); // 10 + 7
		assertEquals(2, partie.numeroTourCourant(), "On doit être au tour n°2");
		assertEquals(1, partie.numeroProchainLancer(), "On doit être à la boule n°1");
		partie.enregistreLancer(3);
		partie.enregistreLancer(4);
		assertEquals(10 + 7 + 3 + 4, partie.score());
		assertFalse(partie.estTerminee());
	}

	@Test
	void testPerfectGame() {
		// 12 boules à 10 points
		lancerPlusieurs(12, 10);
		assertEquals(300, partie.score());
		assertTrue(partie.estTerminee());
	}

	@Test
	void testTypicalGame() {
		lancerPlusieurs(8, 3); // 6 points aux 4 1° tours -> 24
		assertEquals(5, partie.numeroTourCourant()); // On est au tour n° 5
		faireUnStrike(); // 10 + 10
		faireUnSpare(); // 10 + 0
		lancerPlusieurs(6, 0); // 0 points aux 3 tours suivants
		lancerPlusieurs(3, 10); // 30 points au dernier tour
		assertTrue(partie.estTerminee());
		assertEquals(84, partie.score());
	}

	// Quelques methodes utilitaires pour faciliter l'écriture des tests
	private boolean lancerPlusieurs(int n, int quilles) {
		boolean leTourcontinue = false;
		for (int i = 0; i < n; i++) {
			leTourcontinue = partie.enregistreLancer(quilles);
		}
		return leTourcontinue;
	}

	private void faireUnSpare() {
		partie.enregistreLancer(7);
		partie.enregistreLancer(3);
	}

	private void faireUnStrike() {
		partie.enregistreLancer(10);
	}

}