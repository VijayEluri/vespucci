package de.tud.cs.st.vespucci.database.bytecode;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import de.tud.cs.st.vespucci.database.bytecode.provider.BytecodeDatabaseProvider;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "de.tud.cs.st.vespucci.bytecode.database"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;

	private BytecodeDatabaseProvider databaseProvider;

	/**
	 * The constructor
	 */
	public Activator() {
	}


	/**
	 * @return the databasePrvider
	 */
	public BytecodeDatabaseProvider getDatabaseProvider() {
		return databaseProvider;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		databaseProvider = new BytecodeDatabaseProvider();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
	 * )
	 */
	public void stop(BundleContext context) throws Exception {
		databaseProvider = null;
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

}
