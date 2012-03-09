package com.datagardens.nq.sdb.views.dialogs;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class AddContactDialog extends Dialog {

	private String name;
	private String nickname;
	private String server;
	
	public AddContactDialog(Shell parentShell) {
		super(parentShell);
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite controlsArea = (Composite) super.createDialogArea(parent);
		controlsArea.setLayout(new GridLayout(2, false));
		new Label(controlsArea, SWT.NONE).setText("Name: ");
		final Text nameText = new Text(controlsArea, SWT.BORDER);
		nameText.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				name = nameText.getText();
			}
		});
		nameText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		new Label(controlsArea, SWT.NONE).setText("Nickname: ");
		final Text nicknameText = new Text(controlsArea, SWT.BORDER);
		nicknameText.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				nickname = nicknameText.getText();
			}
		});
		nicknameText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		new Label(controlsArea, SWT.NONE).setText("Server: ");
		final Text serverText = new Text(controlsArea, SWT.BORDER);
		serverText.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				server = serverText.getText();
			}
		});
		serverText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		
		return controlsArea;
	}
	
	public String getName() {
		return name;
	}
	
	public String getNickname() {
		return nickname;
	}
	
	public String getServer() {
		return server;
	}
}
