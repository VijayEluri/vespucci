/*
 *  License (BSD Style License):
 *   Copyright (c) 2010
 *   Software Engineering
 *   Department of Computer Science
 *   Technische Universit�t Darmstadt
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
 *     Universit�t Darmstadt nor the names of its contributors may be used to 
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
package de.tud.cs.st.vespucci.diagram.handler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.statushandlers.StatusManager;

import de.tud.cs.st.vespucci.diagram.converter.DiagramConverter;
import de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramEditorPlugin;

/**
 * A handler for saving a *.sad file to a *.pl Prolog file.
 * 
 * @author MalteV
 */
public class GeneratePrologFacts extends AbstractHandler {

	/**
	 * TODO beschreibung einf�gen
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IStructuredSelection selection = (IStructuredSelection) HandlerUtil
				.getActiveMenuSelection(event); // current package Explorer selection
		boolean re = false;
		if (selection.getFirstElement() instanceof IFile) { //first and only selected Element
			IFile file = (IFile) selection.getFirstElement();
			//String fname = file.getName();
			//String path = file.getRawLocation().toFile().getParent();
			File f = file.getRawLocation().toFile();
			DiagramConverter dc = new DiagramConverter();
			if (dc.isDiagramFile(f)) {
				try {
					dc.ConvertDiagramToProlog(f); // generating from the *.sad a *.pl
				} catch (FileNotFoundException e) {
					IStatus is = new Status(Status.ERROR, VespucciDiagramEditorPlugin.ID,
							"FileNotFoundException", e);
					StatusManager.getManager().handle(is, StatusManager.SHOW);
					StatusManager.getManager().handle(is, StatusManager.LOG);
				} catch (IOException e) {
					IStatus is = new Status(Status.ERROR, VespucciDiagramEditorPlugin.ID,
							"Failed to save Prolog file", e);
					StatusManager.getManager().handle(is, StatusManager.SHOW);
					StatusManager.getManager().handle(is, StatusManager.LOG);
				} catch (Exception e) {
					IStatus is = new Status(Status.ERROR, VespucciDiagramEditorPlugin.ID,
							"FileNotFoundException", e);
					StatusManager.getManager().handle(is, StatusManager.SHOW);
					StatusManager.getManager().handle(is, StatusManager.LOG);
				}
				re = true;
				// Refresh Pageview
				try {
					file.getProject().refreshLocal(IResource.DEPTH_INFINITE,
							new NullProgressMonitor());
				} catch (CoreException e1) {
					StatusManager.getManager().handle(e1, VespucciDiagramEditorPlugin.ID);
				}
			}

		}
		return re;
	}
	
	
	/*
	 * 	public Object execute(ExecutionEvent event) throws ExecutionException {
		IStructuredSelection selection = (IStructuredSelection) HandlerUtil
				.getActiveMenuSelection(event); // current package Explorer selection
		boolean re = false;
		if (selection.getFirstElement() instanceof IFile) { //first and only selected Element
			IFile file = (IFile) selection.getFirstElement();
			String fname = file.getName();
			String path = file.getRawLocation().toFile().getParent();
			DiagramConverter dc = new DiagramConverter();
			if (dc.isDiagramFile(file.getFullPath().toFile())) {
				try {
					dc.ConvertDiagramToProlog(path, fname); // generating from the *.sad a *.pl
				} catch (FileNotFoundException e) {
					IStatus is = new Status(Status.ERROR, "Vespucci",
							"FileNotFoundException", e);
					StatusManager.getManager().handle(is, StatusManager.SHOW);
				} catch (IOException e) {
					IStatus is = new Status(Status.ERROR, "Vespucci",
							"Failed to save Prolog file", e);
					StatusManager.getManager().handle(is, StatusManager.SHOW);

				}
				re = true;
				// Refresh Pageview
				try {
					file.getProject().refreshLocal(IResource.DEPTH_INFINITE,
							new NullProgressMonitor());
				} catch (CoreException e1) {
					StatusManager.getManager().handle(e1, "Vespucci");
				}
			}

		}

		return re;
	}*/
	
}
