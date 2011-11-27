package stegen.server.service;

import stegen.server.database.*;
import stegen.server.memcache.*;
import stegen.shared.*;

public class NicknameService {
	private static final int MAX_NICKNAME_LENGTH = 20;
	private static NicknameService instance = new NicknameService();
	final NicknameCache cache;
	private final StegenUserRepository repository;

	private NicknameService() {
		cache = new NicknameCache();
		repository = StegenUserRepository.get();
	}

	public static NicknameService get() {
		return instance;
	}

	public PlayerDto createPlayerDto(String emailString) {
		EmailAddressDto email = new EmailAddressDto(emailString);
		String nickname = getNickname(email);
		return new PlayerDto(email, nickname);
	}

	public String getNickname(EmailAddressDto email) {
		if (email.address.isEmpty()) {
			return "";
		}
		if (!hasNickname(email)) {
			createDefaultNickname(email);
		}
		String nickname = getExistingNickname(email);
		String trucatedNickname = truncateString(nickname);
		return trucatedNickname;
	}

	boolean hasNickname(EmailAddressDto email) {
		return cache.containsKey(email.address) || repository.isUserInDatabase(email.address);
	}

	void createDefaultNickname(EmailAddressDto email) {
		StegenUser user = StegenUser.createUserWithDefaults(email);
		cache.put(user.getEmailString(), user.getNickname());
	}

	String getExistingNickname(EmailAddressDto email) {
		if (cache.containsKey(email.address)) {
			return cache.get(email.address);
		}
		if (repository.isUserInDatabase(email.address)) {
			StegenUser user = repository.getUserInDatabase(email.address);
			putUserInCache(user.getEmailString(), user.getNickname());
			return user.getNickname();
		}
		throw new IllegalStateException("Nickname was not found for " + email.address);
	}

	private String truncateString(String nickname) {
		return nickname.substring(0, Math.min(nickname.length(), MAX_NICKNAME_LENGTH));
	}

	public void updateNickname(EmailAddressDto email, String nickname) {
		String emailString = email.address;
		String trucatedNickname = truncateString(nickname);

		repository.updateOrAddStegenUserToDatabase(emailString, trucatedNickname);
		putUserInCache(emailString, nickname);
	}

	private void putUserInCache(String emailString, String nickname) {
		cache.put(emailString, nickname);
	}

	public void clearCache() {
		cache.clear();
	}

	public void removePlayer(EmailAddressDto email) {
		repository.removePlayer(email.address);
		cache.remove(email.address);
	}

}
