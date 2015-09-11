package ca.uhnresearch.pughlab.tracker.server.httpd;

import static org.hamcrest.Matchers.containsString;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class EmbeddedJettyServerTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testMissingConfigArgument() throws Exception {
		
		thrown.expect(RuntimeException.class);
		thrown.expectMessage(containsString("Failed to find configuration file"));
		
		String[] args = new String[]{ };
		
		new EmbeddedJettyServer(args);
	}
}
