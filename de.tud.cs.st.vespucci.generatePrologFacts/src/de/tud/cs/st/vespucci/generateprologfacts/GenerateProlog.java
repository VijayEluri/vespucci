/*
 *  License (BSD Style License):
 *   Copyright (c) 2011
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
package de.tud.cs.st.vespucci.generateprologfacts;

import java.io.FileNotFoundException;
import java.io.IOException;


import de.tud.cs.st.vespucci.diagram.processing.IVespucciModelProcessor;
import de.tud.cs.st.vespucci.diagram.processing.IVespucciModelSaveAction;
import de.tud.cs.st.vespucci.exceptions.VespucciIOException;
import de.tud.cs.st.vespucci.generateprologfacts.creator.PrologFileCreatorWithAdapter;
import de.tud.cs.st.vespucci.vespucci_model.ShapesDiagram;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IAdapterManager;
import org.eclipse.core.runtime.Platform;

/**
 * A processor/handler for saving a *.sad file to a *.pl Prolog file
 * 
 * @author Patrick Gottschämmer
 * @author Olav Lenz
 */
public class GenerateProlog implements IVespucciModelProcessor, IVespucciModelSaveAction {

	public GenerateProlog() {
	}

	@Override
	public void process(Object diagramObject) {
		
		final PrologFileCreatorWithAdapter prologFileCreator = new PrologFileCreatorWithAdapter();
		
		IFile diagramFile = getDiagramIFile(diagramObject);
		ShapesDiagram shapesdiagram = getShapeDiagram(diagramObject);
				
		try {
			prologFileCreator.createPrologFileFromDiagram(diagramFile, shapesdiagram);
		} catch (final FileNotFoundException e) {
			throw new VespucciIOException(String.format("File [%s] not found.",diagramFile), e);
		} catch (final IOException e) {
			throw new VespucciIOException(String.format("Failed to save Prolog file from [%s].",diagramFile), e);
		} catch (final Exception e) {
			throw new VespucciIOException(String.format("File [%s] not found.",diagramFile), e);
		}

	}
	
	private IFile getDiagramIFile(Object diagramObject) {
		IFile diagramFile = null;
		
		if (IFile.class.isInstance(diagramObject)){
			return (IFile) diagramObject;
		}
		
		if (diagramObject instanceof IAdaptable){
			diagramFile = (IFile) ((IAdaptable) diagramObject).getAdapter(IFile.class);
		}
		
		if (diagramFile == null){
			IAdapterManager manager = Platform.getAdapterManager();
			diagramFile =  (IFile) manager.getAdapter(diagramObject, IFile.class);
		}
		return diagramFile;
	}

	private ShapesDiagram getShapeDiagram(Object diagramObject) {
		ShapesDiagram shapesDiagram = null;
		
		if (ShapesDiagram.class.isInstance(diagramObject)){
			return (ShapesDiagram) diagramObject;
		}
		
		if (diagramObject instanceof IAdaptable){
			shapesDiagram = (ShapesDiagram) ((IAdaptable) diagramObject).getAdapter(ShapesDiagram.class);
		}
		
		if (shapesDiagram == null){
			IAdapterManager manager = Platform.getAdapterManager();
			shapesDiagram =  (ShapesDiagram) manager.getAdapter(diagramObject, ShapesDiagram.class);
		}
		return shapesDiagram;
	}
	
	@Override
	public void doSave(Object diagramElement) {
		process(diagramElement);
	}

}
