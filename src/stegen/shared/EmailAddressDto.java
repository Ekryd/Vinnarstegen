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

	/** @see java.lang.Object#hashCode() */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		return result;
	}

	/** @see java.lang.Object#equals(java.lang.Object) */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		EmailAddressDto other = (EmailAddressDto) obj;
		if (address == null) {
			if (other.address != null) {
				return false;
			}
		} else if (!address.equals(other.address)) {
			return false;
		}
		return true;
	}

}
