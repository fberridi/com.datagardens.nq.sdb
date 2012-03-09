package com.datagardens.nq.sdb;

import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

import com.datagardens.nq.sdb.actions.AddContactAction;

public class MainConsoleActionsAdvisor extends ActionBarAdvisor {

	
	private IWorkbenchAction exitAction;
	private IWorkbenchAction aboutAction;
	private IWorkbenchAction addContactAction;
	
    public MainConsoleActionsAdvisor(IActionBarConfigurer configurer) {
        super(configurer);
    }

    protected void makeActions(IWorkbenchWindow window) 
    {
    	exitAction = ActionFactory.QUIT.create(window);
    	aboutAction = ActionFactory.ABOUT.create(window);
    	addContactAction = new AddContactAction(window);
    	
    	registerAllActions();
    }

    private void registerAllActions() 
    {
    	register(exitAction);
    	register(aboutAction);
    	register(addContactAction);
	}

	protected void fillMenuBar(IMenuManager menuBar) 
	{
		//file
		MenuManager fileMenu = new MenuManager("&File", "file");
		fileMenu.add(addContactAction);
		fileMenu.add(exitAction);
		
		//help
		MenuManager helpMenu = new MenuManager("&Help", "help");
		helpMenu.add(aboutAction);	
		menuBar.add(fileMenu);
		menuBar.add(helpMenu);
    }
    
	@Override
	protected void fillCoolBar(ICoolBarManager coolBar) {
		IToolBarManager toolBar = new ToolBarManager(coolBar.getStyle());
		coolBar.add(toolBar);
		toolBar.add(addContactAction);
	}
}
