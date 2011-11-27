package stegen.server.service;

import org.junit.*;

import stegen.server.database.*;
import stegen.shared.*;

import com.google.appengine.tools.development.testing.*;

public class NicknameServiceTest {
	private static final EmailAddressDto EMAIL = new EmailAddressDto("gurka@gurka.se");
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalTaskQueueTestConfig(),
			new LocalDatastoreServiceTestConfig());
	private NicknameService service;
	private StegenUserRepository repository;

	@Before
	public void setUp() {
		helper.setUp();
		repository = StegenUserRepository.get();
		service = NicknameService.get();
		service.clearCache();
	}

	@After
	public void tearDown() {
		helper.tearDown();
	}

	@Test
	public void newUserShouldNotHaveNickname() {
		Assert.assertEquals(false, service.hasNickname(EMAIL));
	}

	@Test
	public void userShouldGetDefaultNickname() {
		service.createDefaultNickname(EMAIL);
		Assert.assertEquals(EMAIL.address, service.getExistingNickname(EMAIL));
	}

	@Test
	public void defaultNicknameShouldBeInCache() {
		service.createDefaultNickname(EMAIL);
		Assert.assertEquals(true, service.cache.containsKey(EMAIL.address));
	}

	@Test
	public void defaultNicknameShouldNotBeInDB() {
		service.createDefaultNickname(EMAIL);
		Assert.assertEquals(false, repository.isUserInDatabase(EMAIL.address));
	}

	@Test
	public void defaultNicknameShouldOnlyBeInCache() {
		service.createDefaultNickname(EMAIL);
		service.clearCache();
		Assert.assertEquals(false, service.hasNickname(EMAIL));
	}

	@Test
	public void newUserShouldGetEmailAsNickname() {
		String nickname = service.getNickname(EMAIL);
		Assert.assertEquals(EMAIL.address, nickname);
	}

	@Test
	public void updatedNicknameShouldBeCached() {
		service.updateNickname(EMAIL, "tomat");
		Assert.assertEquals("tomat", service.cache.get(EMAIL.address));
	}

	@Test
	public void updatedNicknameShouldBeStoredInDb() {
		service.updateNickname(EMAIL, "tomat");
		Assert.assertEquals("tomat", repository.getUserInDatabase(EMAIL.address).getNickname());
	}

	@Test
	public void updatedNicknameShouldReturned() {
		service.updateNickname(EMAIL, "tomat");
		Assert.assertEquals("tomat", service.getNickname(EMAIL));
	}

	@Test
	public void twiceUpdatedNicknameShouldBeReturned() {
		service.updateNickname(EMAIL, "tomat");
		service.updateNickname(EMAIL, "gurka");
		Assert.assertEquals("gurka", service.getNickname(EMAIL));
	}

	@Test
	public void longNicknamesShouldBeTruncated() {
		service.createDefaultNickname(EMAIL);
		service.updateNickname(EMAIL, "tomat123456789012345678900901234567890090123456789009012345678900");
		Assert.assertEquals("tomat123456789012345", repository.getUserInDatabase(EMAIL.address).getNickname());
		service.clearCache();
		Assert.assertEquals("tomat123456789012345", service.getNickname(EMAIL));
	}

	@Test
	public void createdPlayerDtoShouldContainNickname() {
		service.updateNickname(EMAIL, "gurka");
		Assert.assertEquals("gurka", service.createPlayerDto(EMAIL.address).nickname);
	}

	@Test(expected = IllegalStateException.class)
	public void illegalCallToGetNicknameShouldThrowException() {
		Assert.assertEquals("gurka", service.getExistingNickname(EMAIL));
	}

	@Test
	public void emptyEmailShouldReturnEmptyNickname() {
		Assert.assertEquals("", service.getNickname(new EmailAddressDto("")));
	}

	@Test
	public void removePlayerShouldClearDatabaseAndCache() {
		service.updateNickname(EMAIL, "gurka");
		service.removePlayer(EMAIL);
		Assert.assertEquals(false, repository.isUserInDatabase(EMAIL.address));
		Assert.assertEquals(EMAIL.address, service.getNickname(EMAIL));
	}
}
