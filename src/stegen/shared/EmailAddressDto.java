package stegen.shared;

import java.io.*;

public class EmailAddressDto implements Serializable {
	private static final long serialVersionUID = 6858401632387316807L;

	public String address;

	/** For Serialization */
	protected EmailAddressDto() {}

	public EmailAddressDto(String address) {
		this.address = address;
	}

}
