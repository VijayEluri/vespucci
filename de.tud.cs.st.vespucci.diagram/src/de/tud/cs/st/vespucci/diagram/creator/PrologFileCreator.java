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
package de.tud.cs.st.vespucci.diagram.creator;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

import de.tud.cs.st.vespucci.vespucci_model.Empty;
import de.tud.cs.st.vespucci.vespucci_model.Shape;
import de.tud.cs.st.vespucci.vespucci_model.ShapesDiagram;

/**
 * PrologFileCreator creates a *.pl file from a *.sad file .
 * 
 * @author Patrick Jahnke
 * @author Alexander Weitzmann
 * @author Thomas Schulz
 * @author Theo Kischka
 */
public class PrologFileCreator {

	/**
	 * Name of the current diagram file.
	 */
	private String diagramFileName;
	
	/**
	 * Read the given diagram and create a prolog file.
	 * 
	 * @param sadFile
	 *            File of the diagram.
	 * @author Malte Viering
	 * @throws Exception
	 */
	public void createPrologFileFromDiagram(final File sadFile) throws Exception {
		this.createPrologFileFromDiagram(sadFile.getParent(), sadFile.getName());
	}

	/**
	 * Read the given diagram and create a prolog file.
	 * 
	 * @param location
	 * @param fileName
	 * @throws Exception
	 */
	public void createPrologFileFromDiagram(final String location, final String fileName) throws Exception {
		diagramFileName = fileName;
		final String fullFileName = location + "/" + fileName;
		final ShapesDiagram diagram = loadDiagramFile(fullFileName);

		// create a new Prolog File
		final File prologFile = new File(fullFileName + ".pl");

		// the file will be overwritten
		final FileOutputStream fos = new FileOutputStream(prologFile);
		final BufferedOutputStream bos = new BufferedOutputStream(fos);

		bos.write(createPrologFacts(diagram, fullFileName));

		bos.close();
		fos.close();
	}

	/**
	 * Loads a diagram file.
	 * 
	 * @param fullPath
	 * @return Returns the loaded diagram.
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @author Dominic Scheurer
	 */
	private static ShapesDiagram loadDiagramFile(final String fullPath) throws FileNotFoundException, IOException {
		final XMIResourceImpl diagramResource = new XMIResourceImpl();
		final FileInputStream diagramStream = new FileInputStream(new File(fullPath));

		diagramResource.load(diagramStream, new HashMap<Object, Object>());

		// Find the ShapesDiagram-EObject
		for (int i = 0; i < diagramResource.getContents().size(); i++) {
			if (diagramResource.getContents().get(i) instanceof ShapesDiagram) {
				final EObject eObject = diagramResource.getContents().get(i);
				return (ShapesDiagram) eObject;
			}
		}

		throw new FileNotFoundException("ShapesDiagram could not be found in Document.");
	}

	/**
	 * Read the diagram and create the prolog facts of ensembles and dependencies.
	 * 
	 * @param diagram
	 *            The diagram where the ensembles and dependencies are defined.
	 * @param fullFileName
	 *            The path to the diagram including its filename.
	 * @return Returns a string with the prolog facts.
	 * @throws Exception
	 */
	private byte[] createPrologFacts(final ShapesDiagram diagram, final String fullFileName) throws Exception {
		final StringBuilder strBuilder = new StringBuilder();

		strBuilder.append(InvariantPrologFacts.createFileHeader(fullFileName));

		// insert ensemble Header
		strBuilder.append(InvariantPrologFacts.createEnsembleHeader());

		final StringBuilder ensembleFacts = EnsemblePrologFacts.getFacts(diagram.getShapes(), diagramFileName);

		if (hasDummy(diagram.getShapes())) {
			ensembleFacts.append("ensemble('" + diagramFileName + "',(empty),empty,[]).\n");
		}

		// insert ensembles
		strBuilder.append(ensembleFacts);

		// insert dependency header
		strBuilder.append(InvariantPrologFacts.createDependencyHeader());

		// insert dependencies
		strBuilder.append(DependencyPrologFacts.getFacts(diagram.getShapes(), diagramFileName));

		return strBuilder.toString().getBytes();
	}

	/**
	 * 
	 * @param shapeList
	 * @return Return true only if given shape list contains a dummy.
	 */
	private static boolean hasDummy(final List<Shape> shapeList) {
		for (final Shape shape : shapeList) {
			if (shape instanceof Empty) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param file
	 * @return Return true only if the given file object is a diagram file.
	 */
	public static boolean isDiagramFile(final File file) {
		final String extension = file.getName().substring((file.getName().length() - 3), file.getName().length());
		return (extension.equals("sad"));
	}

}
