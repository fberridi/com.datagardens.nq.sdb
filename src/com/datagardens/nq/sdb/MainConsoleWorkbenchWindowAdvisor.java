package com.datagardens.nq.sdb;

import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tray;
import org.eclipse.swt.widgets.TrayItem;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.datagardens.nq.sdb.views.IImageKeys;

public class MainConsoleWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

	private Image statusImage;
	private Image trayImage;
	private TrayItem trayItem;
	
    public MainConsoleWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
        super(configurer);
    }

    public ActionBarAdvisor createActionBarAdvisor(IActionBarConfigurer configurer) {
        return new MainConsoleActionsAdvisor(configurer);
    }
    
    public void preWindowOpen() {
        IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
        configurer.setInitialSize(new Point(400, 300));
        configurer.setShowMenuBar(true);
        configurer.setShowCoolBar(true);
        configurer.setShowStatusLine(true);
        configurer.setTitle("Serialization Database Manager"); //$NON-NLS-1$
    }
    
    @Override
    public void postWindowOpen() 
    {
    	initStatusLine();
    	final IWorkbenchWindow window = getWindowConfigurer().getWindow();
    	trayItem = initTaskItem(window);
    	if(trayItem != null)
    	{
    		hookPopupMenu(window);
    		hookMinimize(window);
    	}
    	
    }
    
    
    
    private void hookMinimize(final IWorkbenchWindow window) {
    	window.getShell().addShellListener(new ShellAdapter() {
    		@Override
    		public void shellIconified(ShellEvent e) {
    			window.getShell().setVisible(false);
    		}
		});
    	
    	trayItem.addSelectionListener(new SelectionAdapter() {
    		@Override
    		public void widgetDefaultSelected(SelectionEvent e) 
    		{
    			Shell shell = window.getShell();
    			if(!shell.isVisible())
    			{
    				shell.setVisible(true);
    				window.getShell().setMinimized(false);
    			}
    		}
		});
	}

	private void hookPopupMenu(final IWorkbenchWindow window)
	{
		trayItem.addMenuDetectListener(new MenuDetectListener() {
			
			@Override
			public void menuDetected(MenuDetectEvent e) {
				MenuManager trayMenu = new MenuManager();
				Menu menu = trayMenu.createContextMenu(window.getShell());
			}
		});
	}

	private TrayItem initTaskItem(IWorkbenchWindow window) 
    {
		final Tray tray = window.getShell().getDisplay().getSystemTray();
		
		if(tray != null)
		{
			TrayItem trayItem = new TrayItem(tray, SWT.NONE);
			trayImage = AbstractUIPlugin.imageDescriptorFromPlugin(MainConsole.PLUGIN_ID,
					IImageKeys.CHAT_USER_ONLINE).createImage();
			trayItem.setImage(trayImage);
			trayItem.setToolTipText("Norquest SDB");
			return trayItem;
		}
		
		return null;
	}

	private void initStatusLine() {
    	statusImage = AbstractUIPlugin.imageDescriptorFromPlugin(MainConsole.PLUGIN_ID, 
    			IImageKeys.CHAT_USER_ONLINE).createImage();
    	IStatusLineManager statusLineManager = 
    			getWindowConfigurer().getActionBarConfigurer().getStatusLineManager();
    	statusLineManager.setMessage(statusImage, "Online");
	}

	@Override
    public void dispose() {
    	statusImage.dispose();
    }
}
