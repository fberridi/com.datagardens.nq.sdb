package com.datagardens.nq.sdb;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import com.datagardens.nq.sdb.views.ContactsView;

public class Perspective implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) 
	{
		layout.setEditorAreaVisible(false);
		layout.addStandaloneView(ContactsView.ID, false, 
				IPageLayout.LEFT, 1.0F, layout.getEditorArea());
	}
}