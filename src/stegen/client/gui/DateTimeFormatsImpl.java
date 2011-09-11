package stegen.client.gui;

import java.util.*;

import stegen.client.service.*;

import com.google.gwt.i18n.client.*;

public class DateTimeFormatsImpl implements DateTimeFormats {
	private DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm");
	private final DateTimeFormat challengeDateTimeFormat = DateTimeFormat.getFormat("'kl 'HH:mm' den 'dd/MM");

	@Override
	public String getChallengeDateDefaultOneDayFromNow() {
		Date date = new Date(System.currentTimeMillis() + (1000L * 60 * 60 * 24));
		return challengeDateTimeFormat.format(date);
	}

	@Override
	public String formatDate(Date messageDate) {
		return dateTimeFormat.format(messageDate);
	}

}
