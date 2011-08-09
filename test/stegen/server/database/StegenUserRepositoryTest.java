package stegen.server.database;

import org.junit.*;

import stegen.client.dto.*;
import stegen.shared.*;

import com.google.appengine.tools.development.testing.*;

public class StegenUserRepositoryTest {
	private static final EmailAddressDto EMAIL = new EmailAddressDto("gurka@gurka.se");
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalTaskQueueTestConfig(),
			new LocalDatastoreServiceTestConfig());
	private StegenUserRepository repository;

	@Before
	public void setUp() {
		helper.setUp();
		repository = StegenUserRepository.get();
		repository.clearCache();
	}

	@After
	public void tearDown() {
		helper.tearDown();
	}

	@Test
	public void testHasNicknameNope() {
		Assert.assertEquals(false, repository.hasNickname(EMAIL));
	}

	@Test
	public void testCreateDefaultNickname() {
		repository.createDefaultNickname(EMAIL);
		Assert.assertEquals(EMAIL.address, repository.getNickname(EMAIL));
	}

	@Test
	public void testHasNicknameInCache() {
		repository.createDefaultNickname(EMAIL);
		Assert.assertEquals(true, repository.hasNickname(EMAIL));
	}

	@Test
	public void testHasNicknameInDbNotCache() {
		repository.createDefaultNickname(EMAIL);
		repository.clearCache();
		Assert.assertEquals(true, repository.hasNickname(EMAIL));
	}

	@Test
	public void testGetOrCreateNicknameNotExist() {
		String nickname = repository.getOrCreateNickname(EMAIL);
		Assert.assertEquals(EMAIL.address, nickname);
	}

	@Test
	public void testUpdateNicknameInCache() {
		repository.createDefaultNickname(EMAIL);
		repository.updateNickname(EMAIL, "tomat");
		Assert.assertEquals("tomat", repository.getNickname(EMAIL));
	}

	@Test
	public void testUpdateNicknameInDb() {
		repository.createDefaultNickname(EMAIL);
		repository.updateNickname(EMAIL, "tomat");
		repository.clearCache();
		Assert.assertEquals("tomat", repository.getNickname(EMAIL));
	}

	@Test
	public void testGetOrCreateNicknameExist() {
		repository.createDefaultNickname(EMAIL);
		repository.updateNickname(EMAIL, "tomat");
		String nickname = repository.getOrCreateNickname(EMAIL);
		Assert.assertEquals("tomat", nickname);
	}

}
