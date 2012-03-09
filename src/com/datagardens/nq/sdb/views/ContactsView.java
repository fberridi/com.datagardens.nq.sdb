package com.datagardens.nq.sdb.views;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.part.ViewPart;

import com.datagardens.nq.sdb.test.model.Contact;
import com.datagardens.nq.sdb.test.model.ContactsEntry;
import com.datagardens.nq.sdb.test.model.ContactsGroup;
import com.datagardens.nq.sdb.test.model.IContactsListener;
import com.datagardens.nq.sdb.test.model.Session;

public class ContactsView extends ViewPart {

	public static final String ID = "com.datagardens.nq.sdb.views.contacts";
	
	public ContactsView() {
	}

	private Session session;
	private TreeViewer contactsViewer;
	private MainConsoleAdapterFactory adaptableFactory = new MainConsoleAdapterFactory();
	
	
	@Override
	public void createPartControl(Composite parent)
	{
		initializeSession();//temporal
		contactsViewer = new TreeViewer(parent, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		
		Platform.getAdapterManager().registerAdapters(adaptableFactory, Contact.class);
		getSite().setSelectionProvider(contactsViewer);
		
		contactsViewer.setLabelProvider(new WorkbenchLabelProvider());
		contactsViewer.setContentProvider(new BaseWorkbenchContentProvider());
		
		contactsViewer.setInput(session.getRoot());
		
		session.getRoot().addContactsListener(new IContactsListener() {
			
			@Override
			public void contactsChanged(ContactsGroup contacts, ContactsEntry entry) 
			{
				contactsViewer.refresh();
			}
		});
		
		
	}

	@Override
	public void setFocus() 
	{
		contactsViewer.getControl().setFocus();
	}

	
	@Override
	public void dispose() {
		Platform.getAdapterManager().unregisterAdapters(adaptableFactory);
		super.dispose();
	}
	
	private void initializeSession()
	{
		session = new Session();
		ContactsGroup root = session.getRoot();
		
		ContactsGroup friendsGroup = new ContactsGroup(root, "Friends");
		root.addEntry(friendsGroup);
		friendsGroup.addEntry(new ContactsEntry(friendsGroup, "Alize", "aliz", "localhost"));
		friendsGroup.addEntry(new ContactsEntry(friendsGroup, "Sydney", "syd", "localhost"));
		
		ContactsGroup otherGroup = new ContactsGroup(root, "Other");
		root.addEntry(otherGroup);
		otherGroup.addEntry(new ContactsEntry(otherGroup, "Samantha", "sam", "localhost"));
	}
	
}
