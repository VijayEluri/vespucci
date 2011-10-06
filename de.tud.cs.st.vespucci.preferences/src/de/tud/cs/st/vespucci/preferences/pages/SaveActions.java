package de.tud.cs.st.vespucci.preferences.pages;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import de.tud.cs.st.vespucci.preferences.Preferences;

public class SaveActions extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	public SaveActions() {
		super(GRID);
		this.
		setPreferenceStore(Preferences.getDefault().getPreferenceStore());
		setDescription("Which Plug-Ins should be executed at save?");
		
	}

	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {
		

		final String EXTENSIONPOINT_ID = "de.tud.cs.st.vespucci.diagram.doSave";

		IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();

		IConfigurationElement[] configurationElement = extensionRegistry
				.getConfigurationElementsFor(EXTENSIONPOINT_ID);
		
		IPreferenceStore store = Preferences.getDefault().getPreferenceStore();
		
		for (IConfigurationElement i : configurationElement) {

			addField(new BooleanFieldEditor(generateId(i),
					i.getAttribute("Label"), getFieldEditorParent()));
			store.setDefault(generateId(i), true);
		}
		
	}

	private String generateId(IConfigurationElement i) {
		return "saveBooleanOption" + i.getAttribute("id");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}

}
