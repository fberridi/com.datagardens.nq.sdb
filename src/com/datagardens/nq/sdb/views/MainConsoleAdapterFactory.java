package com.datagardens.nq.sdb.views;

import java.text.MessageFormat;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.IWorkbenchAdapter;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.datagardens.nq.sdb.MainConsole;
import com.datagardens.nq.sdb.test.model.Contact;
import com.datagardens.nq.sdb.test.model.ContactsEntry;
import com.datagardens.nq.sdb.test.model.ContactsGroup;
import com.datagardens.nq.sdb.test.model.Presence;

public class MainConsoleAdapterFactory implements IAdapterFactory {

	
	/**
	 * View Adapter for all the {@link ContactsGroup} objects
	 */
	private IWorkbenchAdapter groupAdapter = new IWorkbenchAdapter() {
		
		@Override
		public Object getParent(Object o) {
			return ((ContactsGroup) o).getParent();
		}
		
		@Override
		public String getLabel(Object o) 
		{			
			ContactsGroup group = (ContactsGroup) o;	
			int available = 0;
			for(Contact contact : group.getEntries())
			{
				if(contact instanceof ContactsEntry)
				{
					if(((ContactsEntry) contact) .getPresence() != Presence.INVISIBLE)
					{
						available++;
					}
				}
			}
			
			return MessageFormat.format("{0} ({1}/{2})",
					group.getName(), available, group.getEntries().length);
		}
		
		@Override
		public ImageDescriptor getImageDescriptor(Object object) {
			return AbstractUIPlugin.imageDescriptorFromPlugin(MainConsole.PLUGIN_ID,
					IImageKeys.CHAT_GROUP);
		}
		
		@Override
		public Object[] getChildren(Object o) {
			return ((ContactsGroup) o).getEntries();
		}
	};
	
	///////////////////////////////////////////////////////
	private IWorkbenchAdapter entityAdapter = new IWorkbenchAdapter() {
		
		@Override
		public Object getParent(Object o) {
			return ((ContactsEntry) o).getParent();
		}
		
		@Override
		public String getLabel(Object o) {
			ContactsEntry entry = (ContactsEntry)o;
			return MessageFormat.format("{0} ({1}@{2})", 
					entry.getName(), entry.getNickname(), entry.getServer());
		}
		
		@Override
		public ImageDescriptor getImageDescriptor(Object object) {
			return AbstractUIPlugin.imageDescriptorFromPlugin(MainConsole.PLUGIN_ID,
					presenceToKey(((ContactsEntry) object).getPresence()));
		}
		
		@Override
		public Object[] getChildren(Object o) {
			return new Object[]{};
		}
	};
	
	///////////////////////////////////////////////////////
	
	@Override
	public Object getAdapter(Object adaptableObject, 
			@SuppressWarnings("rawtypes") Class adapterType) 
	{
		if (adapterType == IWorkbenchAdapter.class)
		{
			if(adaptableObject instanceof ContactsGroup)
			{
				return groupAdapter;
			}
			else if(adaptableObject instanceof ContactsEntry)
			{
				return entityAdapter;
			}
		}
		return null;
	}

	@Override
	public Class<?>[] getAdapterList() {
		return new Class[]{IWorkbenchAdapter.class};
	}
	
	private String presenceToKey(Presence presence)
	{
		if(presence == Presence.ONLINE)
		{
			return IImageKeys.CHAT_USER_ONLINE;
		}
		else if(presence == Presence.AWAY)
		{
			return IImageKeys.CHAT_USER_AWAY;
		}
		else if(presence == Presence.DO_NOT_DISTURB)
		{
			return IImageKeys.CHAT_USER_DND;
		}
		else if(presence == Presence.INVISIBLE)
		{
			return IImageKeys.CHAT_USER_OFFLINE;
		}
		return IImageKeys.CHAT_USER_OFFLINE;
	}
}
