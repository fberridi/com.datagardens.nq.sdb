package com.datagardens.nq.sdb.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.datagardens.nq.sdb.MainConsole;
import com.datagardens.nq.sdb.test.model.ContactsEntry;
import com.datagardens.nq.sdb.test.model.ContactsGroup;
import com.datagardens.nq.sdb.views.IImageKeys;
import com.datagardens.nq.sdb.views.dialogs.AddContactDialog;

public class AddContactAction extends Action 
implements ISelectionListener,IWorkbenchAction 
{
	public static final String ID = "com.datagardens.sdb.actions.addContact";
	
	private IWorkbenchWindow window;
	private IStructuredSelection selection;
	
	public AddContactAction(IWorkbenchWindow window) 
	{
		this.window = window;
		setId(ID);
		setText("&Add Contact...");
		setToolTipText("Add contacts to your contacts list.");
		setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(MainConsole.PLUGIN_ID,
				IImageKeys.USER_ADD));
		window.getSelectionService().addSelectionListener(this);
	}
	
	@Override
	public void run() 
	{
		if(selection != null && 
				selection.getFirstElement() instanceof ContactsGroup)
		{
			ContactsGroup group = (ContactsGroup) selection.getFirstElement();
			AddContactDialog dialog = new AddContactDialog(window.getShell());
			if(dialog.open() == Window.OK)
			{
				group.addEntry(new ContactsEntry(group, dialog.getName(),
						dialog.getNickname(), dialog.getServer()));
			}
		}
	}
	
	@Override
	public void dispose() 
	{
		window.getSelectionService().removeSelectionListener(this);
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection incomingSelection) 
	{
		if(incomingSelection instanceof IStructuredSelection)
		{
			selection = (IStructuredSelection) incomingSelection;
			setEnabled(selection.size() == 1  &&
					selection.getFirstElement() instanceof ContactsGroup);
		}
		else 
		{
			setEnabled(false);
		}
	}
}
