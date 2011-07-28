﻿/**
 *  License (BSD Style License):
 *   Copyright (c) 2010
 *   Author Patrick Jahnke
 *   Software Engineering
 *   Department of Computer Science
 *   Technische Universitiät Darmstadt 
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

package de.tud.cs.st.vespucci.diagram.actions;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.m2m.internal.qvt.oml.ast.env.ModelExtentContents;
import org.eclipse.m2m.internal.qvt.oml.emf.util.*;
import org.eclipse.m2m.internal.qvt.oml.library.*;
import org.eclipse.m2m.internal.qvt.oml.runtime.generator.TransformationRunner;
import org.eclipse.m2m.internal.qvt.oml.runtime.generator.TransformationRunner.In;
import org.eclipse.m2m.internal.qvt.oml.runtime.generator.TransformationRunner.Out;
import org.eclipse.m2m.internal.qvt.oml.runtime.project.QvtInterpretedTransformation;
import org.eclipse.m2m.internal.qvt.oml.runtime.project.TransformationUtil;
import org.eclipse.m2m.internal.qvt.oml.trace.*;
import org.eclipse.m2m.qvt.oml.util.IContext;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.actions.RenameResourceAction;

import de.tud.cs.st.vespucci.vespucci_model.diagram.part.Messages;

/**
 * @author DominicS
 */
@SuppressWarnings("restriction")
public class TransformVespucciV0ToV1 implements IObjectActionDelegate {

	private IWorkbenchPart targetPart;
	private ArrayList<URI> fileURIs;

	@Override
	public void run(IAction action) {
		final ResourceSet resourceSet = new ResourceSetImpl();
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < fileURIs.size(); i++)
		{
			sb.append(fileURIs.get(i).toString());
			if (i != fileURIs.size() - 1)
				sb.append(", ");
		}
		
		Job job = new Job("Convert Vespucci diagram " + sb.toString()) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {				
				for (URI fileURI : fileURIs)
				{
					EObject source = getInput(fileURI);
					if (source == null) {
						// No source given => Cancel
						String title = Messages.VespucciTransformationNoFileTitle;
						String message = Messages.VespucciTransformationNoFileMessage;
						MessageDialog.openInformation(getShell(), title,
								NLS.bind(message, fileURI.toString()));
	
						return Status.CANCEL_STATUS;
					}
	
					try {
						// The URIs for model and notation transformation
						URI modelTransfUri = URI
							.createURI("platform:/plugin/de.tud.cs.st.vespucci/transformations/migrate_v0_to_v1.model.qvto"); //$NON-NLS-1$
						URI notationTransfUri = URI
							.createURI("platform:/plugin/de.tud.cs.st.vespucci/transformations/migrate_v0_to_v1.notation.qvto"); //$NON-NLS-1$
						
						// BEGIN MONITOR TASK
						monitor.beginTask("Converting Vespucci diagram...", 7 * fileURIs.size());
	
						// Get Resource from input URI
						Resource inResource = resourceSet
								.getResource(fileURI, true);
	
						// Create the input objects for the transformation
						List<EObject> inObjects = inResource.getContents();
						
						// Determine order of objects (ShapesDiagram / DiagramImpl) in the document
						int posShapesDiagram, posDiagramImpl;
						if (inObjects.get(0).getClass().toString().contains("ShapesDiagramImpl")) {
							posShapesDiagram = 0;
							posDiagramImpl = 1;
						} else {
							posShapesDiagram = 1;
							posDiagramImpl = 0;
						}
						
						// Get model contents
						ModelContent shapesDiagram = new ModelContent(inObjects.subList(posShapesDiagram, posShapesDiagram + 1));
						ModelContent notationDiagram = new ModelContent(inObjects.subList(posDiagramImpl, posDiagramImpl + 1));
	
						// Setup the execution environment details (context)
						IContext context = new Context();
	
						// Create transformation objects
						QvtInterpretedTransformation shapesDiagramTransformation = new QvtInterpretedTransformation(
							TransformationUtil.getQvtModule(modelTransfUri));
						In shapesDiagramInTransformationRunner = new TransformationRunner.In(
							new ModelContent[] { shapesDiagram },
							context
						);
						
						QvtInterpretedTransformation notationDiagramTransformation = new QvtInterpretedTransformation(
							TransformationUtil.getQvtModule(notationTransfUri));
						In notationDiagramInTransformationRunner = new TransformationRunner.In(
							new ModelContent[] { notationDiagram },
							context
						);
						
						// Run the transformations
						Out shapesDiagramOutTransformationRunner = shapesDiagramTransformation
							.run(shapesDiagramInTransformationRunner);
						monitor.worked(2);
						
						Out notationDiagramOutTransformationRunner = notationDiagramTransformation
							.run(notationDiagramInTransformationRunner);					
						monitor.worked(2);
	
						// Retrieve the outputs
						List<ModelExtentContents> shapesDiagramTransfOutputs =
							shapesDiagramOutTransformationRunner.getExtents();
						List<ModelExtentContents> notationDiagramTransfOutputs =
							notationDiagramOutTransformationRunner.getExtents();
	
						// Retrieve the traces
						Trace shapesDiagramTrace = shapesDiagramOutTransformationRunner.getTrace();
						Trace notationDiagramTrace = shapesDiagramOutTransformationRunner.getTrace();
	
						// Check the output validity
						if (shapesDiagramTrace != null && shapesDiagramTransfOutputs.size() == 1 &&
							notationDiagramTrace != null && notationDiagramTransfOutputs.size() == 1) {
							
							// Trace processing
							// Get trace URI
							URI traceURI = fileURI.trimFileExtension()
								.appendFileExtension("trace");
							EList<TraceRecord> shapesDiagramTraceRecords = shapesDiagramTrace
								.getTraceRecords();
							EList<TraceRecord> notationDiagramTraceRecords = notationDiagramTrace
								.getTraceRecords();
							// Create new trace file resource
							Resource traceResource = resourceSet
								.createResource(traceURI);
							// Add the contents of both the traces
							traceResource.getContents()
								.addAll(shapesDiagramTraceRecords);
							traceResource.getContents()
								.addAll(notationDiagramTraceRecords);
	
							// Output process
							ModelExtentContents outputNotation = notationDiagramTransfOutputs.get(0);
							ModelExtentContents outputModel = shapesDiagramTransfOutputs.get(0);
							monitor.worked(1);
							
							// Load original file
							Resource origFile = resourceSet.getResource(fileURI, true);
							origFile.load(null);
							
							// Create new file name for original file
							URI outputOrigFileURI = fileURI.trimFileExtension()
								.appendFileExtension("sad")
								.appendFileExtension("old");
							while (resourceSet.getURIConverter().exists(outputOrigFileURI, null))
								outputOrigFileURI.appendFileExtension("old");
							
							// Save original file with new name
							origFile.setURI(outputOrigFileURI);
							origFile.save(null);
							
							// Delete old file
							resourceSet.getURIConverter().delete(fileURI, null);
	
							List<EObject> outObjectsNotation = outputNotation
								.getAllRootElements();
							List<EObject> outObjectsModel = outputModel
								.getAllRootElements();
	
							// Create and fill the output resource
							// Save new file under old name
							Resource outputResource = resourceSet
								.createResource(fileURI);
							outputResource.getContents().addAll(
								outObjectsModel);
							outputResource.getContents().addAll(
								outObjectsNotation);
	
							monitor.worked(1);
	
							// Save the sad file
							outputResource.save(Collections.emptyMap());
							
							monitor.worked(1);
						}
					} catch (Exception e) {
						handleError(e);
						
						monitor.done();
						return Status.CANCEL_STATUS;
					}
				}
				
				monitor.done();
				return Status.OK_STATUS;
			}
		};
		
		job.setUser(true);
		job.schedule();
	}

	private void handleError(final Exception ex) {
		Display.getDefault().asyncExec(new Runnable() {			
			@Override
			public void run() {
				MessageDialog.openError(getShell(), "Transformation failed",
						MessageFormat.format(
								"{0}: {1}",
								ex.getClass().getSimpleName(),
								ex.getMessage() == null ? "no message" : ex
										.getMessage()));
			}
		});
	}

	private Shell getShell() {
		return targetPart.getSite().getShell();
	}

	private EObject getInput(URI fileURI) {
		ResourceSetImpl rs = new ResourceSetImpl();
		return rs.getEObject(fileURI.appendFragment("/"), true);
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {		
		fileURIs = new ArrayList<URI>();
		action.setEnabled(false);
		if (selection instanceof IStructuredSelection == false
				|| selection.isEmpty()) {
			return;
		}
		
		for (Object fileObject : ((IStructuredSelection)selection).toArray())
		{
			IFile file = (IFile)fileObject;
			fileURIs.add(URI.createPlatformResourceURI(file.getFullPath().toString(), true));
		}
		
		action.setEnabled(true);
	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		this.targetPart = targetPart;
	}
}