package de.tud.cs.st.vespucci.marker;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.statushandlers.StatusManager;

import de.tud.cs.st.vespucci.interfaces.IDataView;
import de.tud.cs.st.vespucci.interfaces.IDataViewObserver;
import de.tud.cs.st.vespucci.interfaces.IViolationSummary;

public class ViolationSummaryMarker implements IDataViewObserver<IViolationSummary>{

	private static String PLUGIN_ID = "de.tud.cs.st.vespucci.marker";
	
	private static HashMap<IViolationSummary, IMarker> markers = new HashMap<IViolationSummary, IMarker>();
	
	private String createViolationSummaryDescription(IViolationSummary element) {
		// TODO : create displayed description for marker here
		return "";
	}
	
	public ViolationSummaryMarker(IDataView<IViolationSummary> dataView){
		if (dataView == null){
			return;
		}
		
		dataView.register(this);
		
		for (Iterator<IViolationSummary> i = dataView.iterator(); i.hasNext();){
			added(i.next());
		}
		
	}
	
	@Override
	public void added(IViolationSummary element) {
		IMarker marker = addMarker(element, createViolationSummaryDescription(element));
		if (marker != null){
			if (!markers.containsKey(element)){
				markers.put(element, marker);
			}
		}
	}

	@Override
	public void deleted(IViolationSummary element) {
		
		if (markers.containsKey(element)){
			try {
				markers.get(element).delete();
			} catch (CoreException e) {
				final IStatus is = new Status(IStatus.ERROR, PLUGIN_ID, e.getMessage(), e);
				StatusManager.getManager().handle(is, StatusManager.LOG);
			}
		}
	}

	@Override
	public void updated(IViolationSummary oldValue, IViolationSummary newValue) {
		deleted(oldValue);
		added(newValue);
	}
	
	protected static void deleteAllMarkers(){
		for (Entry<IViolationSummary, IMarker> entry : markers.entrySet()) {
			try {
				entry.getValue().delete();
			} catch (CoreException e) {
				final IStatus is = new Status(IStatus.ERROR, PLUGIN_ID, e.getMessage(), e);
				StatusManager.getManager().handle(is, StatusManager.LOG);
			}
		}
	}
	
	private IMarker addMarker(IViolationSummary element, String description) {
		try {

			IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(Path.fromPortableString(element.getDiagramFile()));
			IMarker marker = file.createMarker("org.eclipse.core.resources.problemmarker");
			marker.setAttribute(IMarker.MESSAGE, description);
			marker.setAttribute(IMarker.SEVERITY, IMarker.PRIORITY_NORMAL);
			marker.setAttribute(IMarker.TRANSIENT, true);
			return marker;
		}
		catch (CoreException e) {
			final IStatus is = new Status(IStatus.ERROR, PLUGIN_ID, e.getMessage(), e);
			StatusManager.getManager().handle(is, StatusManager.LOG);
		}
		return null;
	}

}
