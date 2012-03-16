package stegen.client.gui.mobile;

import stegen.client.gui.*;

import com.google.gwt.core.client.*;
import com.google.gwt.resources.client.*;
import com.google.gwt.uibinder.client.*;
import com.google.gwt.user.client.ui.*;

public class ShellMobile extends ResizeComposite implements Shell  {

	private static UILayoutUiBinder uiBinder = GWT.create(UILayoutUiBinder.class);

	interface UILayoutUiBinder extends UiBinder<Widget, ShellMobile> {
	} 
	/**
	   * A ClientBundle that provides images for this widget.
	   */
	  interface Resources extends ClientBundle {
	    @Source("stegen_logga_liten.gif")
	    ImageResource logo();
	  }
	  
	@UiField 
	public static LayoutPanel mainArea;
	@UiField 
	public static FlowPanel versionArea;
	@UiField 
	public static FlowPanel logoutArea;
	@UiField 
	public static FlowPanel userArea;
	@UiField 
	public static FlowPanel refreshArea;

	public ShellMobile() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	@Override
	public void showInMainArea(IsWidget widget) {
		mainArea.clear();
		mainArea.add(widget);
	}

	@Override
	public void showInLogoutArea(IsWidget widget) {
		logoutArea.clear();
		logoutArea.add(widget);		
	}

	@Override
	public void showInUserArea(IsWidget widget) {
		userArea.clear();
		userArea.add(widget);
	}

	@Override
	public void showInRefreshArea(IsWidget widget) {
		refreshArea.clear();
		refreshArea.add(widget);
	}

	@Override
	public void showInVersionArea(IsWidget widget) {
		versionArea.clear();
		versionArea.add(widget);
	}

}
