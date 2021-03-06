package sk.stuba.fiit.perconik.ui.preferences;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

abstract class AbstractWorkbenchPreferencePage extends PreferencePage implements IWorkbenchPreferencePage
{
	AbstractWorkbenchPreferencePage()
	{
	}
	
	@Override
	public void init(final IWorkbench workbench)
	{
	}
	
	final void displayQuestion(final String title, final String message)
	{
		MessageDialog.openQuestion(this.getShell(), title, message);
	}

	final void displayNotice(final String title, final String message)
	{
		MessageDialog.openInformation(this.getShell(), title, message);
	}

	final void displayWarning(final String title, final String message)
	{
		MessageDialog.openWarning(this.getShell(), title, message);
	}

	final void displayError(final String title, final String message)
	{
		MessageDialog.openError(this.getShell(), title, message);
	}
}
