package de.tud.cs.st.vespucci.bytecode.database.provider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.statushandlers.StatusManager;

import sae.bytecode.Database;
import sae.bytecode.MaterializedDatabase;
import de.tud.cs.st.vespucci.bytecode.database.Activator;
import de.tud.cs.st.vespucci.bytecode.listener.ClassFileObserver;
import de.tud.cs.st.vespucci.change.observation.IClassFileObserver;
import de.tud.cs.st.vespucci.change.observation.VespucciChangeProvider;
import de.tud.cs.st.vespucci.utilities.Util;
import de.tud.cs.st.vespucci.utilities.Util.Selection;

public class ProjectDatabaseProvider {

	private Map<IProject, Database> databases = new HashMap<IProject, Database>();

	private Map<IProject, Boolean> initializations = new HashMap<IProject, Boolean>();

	private Map<IProject, IClassFileObserver> observers = new HashMap<IProject, IClassFileObserver>();

	public static ProjectDatabaseProvider getInstance() {
		return Activator.getDefault().getDatabaseProvider();
	}

	private IWorkspace workspace;

	public ProjectDatabaseProvider(IWorkspace workspace) {
		this.workspace = workspace;
	}

	/**
	 * Get the bytecode database for a given project. If no database is present
	 * a new one will be created and returned. A listener for changes to
	 * classfiles is installed that automatically updates the database.
	 * 
	 * @param project
	 * @return
	 */
	public Database getDatabase(IProject project) {
		if (databases.containsKey(project))
			return databases.get(project);

		Database database = createDatabase();

		databases.put(project, database);
		addClassFileListener(project, database);

		return database;
	}

	/**
	 * Initialize the byte code database for a given project. If the database is
	 * not yet created this is a no-op.
	 * 
	 * @param project
	 */
	public void initializeDatabase(IProject project) {
		if (initializations.containsKey(project))
			return;

		if (!databases.containsKey(project))
			return;

		Database database = databases.get(project);

		fillDatabase(database, project);

		initializations.put(project, true);
	}

	/**
	 * Destroy the bytecode database for a given project.
	 * 
	 * @param project
	 */
	public void disposeDatabase(IProject project) {
		databases.remove(project);
		initializations.remove(project);
		removeClassFileListener(project);
	}

	protected Database createDatabase() {
		return new MaterializedDatabase();
	}

	/**
	 * Install the listener that updates the database for a given project.
	 * 
	 * @param project
	 * @param database
	 */
	private void addClassFileListener(IProject project, Database database) {
		if (observers.containsKey(project))
			return;

		ClassFileObserver observer = new ClassFileObserver(database, workspace);
		VespucciChangeProvider.getInstance().registerClassFileObserver(project,
				observer);
		observers.put(project, observer);
	}

	/**
	 * Unregisters the listener of the bytecode database for a given project.
	 * 
	 * @param project
	 */
	private void removeClassFileListener(IProject project) {
		if (!observers.containsKey(project))
			return;
		IClassFileObserver observer = observers.get(project);
		VespucciChangeProvider.getInstance().unregisterClassFileObserver(
				project, observer);
	}

	/**
	 * Fill the database with all class files of the current project
	 * 
	 * @param database
	 * @param project
	 */
	private void fillDatabase(Database database, IProject project) {

		List<IFile> classFiles = Util.getFilesOfProject(project,
				Selection.CLASS);

		for (IFile file : classFiles) {
			try {
				database.addClassFile(file.getContents());
			} catch (CoreException e) {
				IStatus is = new Status(IStatus.ERROR, Activator.PLUGIN_ID,
						"unable to read resource: "
								+ file.getLocation().toString(), e);
				StatusManager.getManager().handle(is, StatusManager.LOG);

			}
		}

	}
}
