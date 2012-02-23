package stegen.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class UILayout extends Composite {

	private static UILayoutUiBinder uiBinder = GWT.create(UILayoutUiBinder.class);

	interface UILayoutUiBinder extends UiBinder<Widget, UILayout> {
	}
	
	@UiField 
	public static FlowPanel mainArea;
	@UiField 
	public static FlowPanel versionArea;
	@UiField 
	public static FlowPanel logoutArea;
	@UiField 
	public static FlowPanel userArea;
	@UiField 
	public static FlowPanel refreshArea;

	public UILayout() {
		initWidget(uiBinder.createAndBindUi(this));
	}	
}
