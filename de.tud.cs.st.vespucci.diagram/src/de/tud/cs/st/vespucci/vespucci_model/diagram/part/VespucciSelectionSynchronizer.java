/*
 *  License (BSD Style License):
 *   Copyright (c) 2011
 *   Software Engineering
 *   Department of Computer Science
 *   Technische Universität Darmstadt
 *   All rights reserved.
 * 
 *   Redistribution and use in source and binary forms, with or without
 *   modification, are permitted provided that the following conditions are met:
 * 
 *   - Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   - Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   - Neither the name of the Software Engineering Group or Technische 
 *     Universität Darmstadt nor the names of its contributors may be used to 
 *     endorse or promote products derived from this software without specific 
 *     prior written permission.
 * 
 *   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *   AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 *   IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 *   ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 *   LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *   CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 *   SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *   INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 *   CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 *   ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *   POSSIBILITY OF SUCH DAMAGE.
 */
package de.tud.cs.st.vespucci.vespucci_model.diagram.part;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.parts.SelectionSynchronizer;
import org.eclipse.gef.ui.parts.TreeViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;

/**
 * SelectionSynchronizer for view components
 * 
 * @author Artem Vovk
 * 
 */
public class VespucciSelectionSynchronizer extends SelectionSynchronizer {

	private final List<EditPartViewer> viewers = new ArrayList<EditPartViewer>();
	private boolean isDispatching = false;
	private int disabled = 0;
	private EditPartViewer pendingSelection;

	/**
	 * Adds a viewer to the set of synchronized viewers
	 * 
	 * @param viewer
	 *            the viewer
	 */
	@Override
	public void addViewer(final EditPartViewer viewer) {
		viewer.addSelectionChangedListener(this);
		viewers.add(viewer);
	}

	/**
	 * @param viewer
	 *            the viewer to search in
	 * @param part
	 *            a part from different viewer
	 * @return Returns EditPart associated with given EditPart in given viewer.
	 */
	protected List<EditPart> getAssociatedParts(final EditPartViewer viewer, final EditPart part) {
		final List<EditPart> parts = new ArrayList<EditPart>();

		final EditPart temp = (EditPart) viewer.getEditPartRegistry().get(part.getModel());
		if (temp != null) {
			int selectionCounter = 0;
			if (viewer instanceof TreeViewer) {
				final List<?> editParts = viewer.getSelectedEditParts();
				for (final Object o : editParts) {
					final EditPart editPart = (EditPart) o;
					if (editPart.getModel() == temp.getModel()) {
						selectionCounter++;
						parts.add(editPart);
					}
				}
				
				// TODO: Why does adding temp depend on selectionCounter?
				if (selectionCounter == 1) {
					return parts;
				} else if (selectionCounter > 1) {
					parts.add(temp);
					return parts;
				}
			}
			EditPart newPart = null;

			newPart = temp;
			parts.add(newPart);
		}
		return parts;
	}

	/**
	 * Removes the viewer from the set of synchronized viewers
	 * 
	 * @param viewer
	 *            the viewer to remove
	 */
	@Override
	public void removeViewer(final EditPartViewer viewer) {
		viewer.removeSelectionChangedListener(this);
		viewers.remove(viewer);
		if (pendingSelection == viewer) {
			pendingSelection = null;
		}
	}

	/**
	 * Receives notification from one viewer, and maps selection to all other
	 * members.
	 * 
	 * @param event
	 *            the selection event
	 */
	@Override
	public void selectionChanged(final SelectionChangedEvent event) {
		if (isDispatching) {
			return;
		}
		final EditPartViewer source = (EditPartViewer) event.getSelectionProvider();
		if (disabled > 0) {
			pendingSelection = source;
		} else {
			final ISelection selection = event.getSelection();
			syncSelection(source, selection);
		}
	}

	private void syncSelection(final EditPartViewer source, final ISelection selection) {
		isDispatching = true;
		for (int i = 0; i < viewers.size(); i++) {
			if (viewers.get(i) != source) {
				final EditPartViewer viewer = viewers.get(i);
				setViewerSelection(viewer, selection);
			}
		}
		isDispatching = false;
	}

	/**
	 * Enables or disabled synchronization between viewers.
	 * 
	 * @since 3.1
	 * @param value
	 *            <code>true</code> if synchronization should occur
	 */
	@Override
	public void setEnabled(final boolean value) {
		if (!value) {
			disabled++;
		} else if (--disabled == 0 && pendingSelection != null) {
			syncSelection(pendingSelection, pendingSelection.getSelection());
			pendingSelection = null;
		}
	}

	private void setViewerSelection(final EditPartViewer viewer, final ISelection selection) {
		final ArrayList<EditPart> result = new ArrayList<EditPart>();
		@SuppressWarnings("unchecked")
		final Iterator<EditPart> iter = ((IStructuredSelection) selection).iterator();
		while (iter.hasNext()) {
			final List<EditPart> parts = getAssociatedParts(viewer, iter.next());
			if (parts != null) {
				result.addAll(parts);
			}
		}
		viewer.setSelection(new StructuredSelection(result));
		if (result.size() > 0) {
			viewer.reveal(result.get(result.size() - 1));
		}
	}
}
