package stegen.client.gui;

import com.google.gwt.user.client.ui.*;

public interface Shell extends IsWidget{
	
	public void showInMainArea(IsWidget widget);
	public void showInLogoutArea(IsWidget widget);
	public void showInUserArea(IsWidget widget);
	public void showInRefreshArea(IsWidget widget);
	public void showInVersionArea(IsWidget widget);
}
