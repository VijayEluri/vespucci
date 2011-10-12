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
package de.tud.cs.st.vespucci.diagram.processing;

/**
 * An interface for declaring a save method on a diagramObject. 
 * See ExtensionPoint de.tud.cs.st.vespucci.diagram.saveActions
 * 
 * @author Patrick Gottschämmer
 * @author Olav Lenz
 */
public interface IVespucciModelSaveAction {
	
	/** 
	 * 
	 * Use IAdaptable / Platform.getAdapterManager() for converting Object diagramElement,
	 * see <b>Eclipse Corner Article: Adapters</b> for further notice<br>
	 * Example: <pre>{@code @Override
	 * public void doSave(Object diagramObject) {
	 * 
	 * ShapesDiagram shapesDiagram = null;
	 * 
	 * if (ShapesDiagram.class.isInstance(diagramObject)){
	 * 		return (ShapesDiagram) diagramObject;
	 * 	}
	 * if (diagramObject instanceof IAdaptable){
	 * 		shapesDiagram = (ShapesDiagram) ((IAdaptable) diagramObject).getAdapter(ShapesDiagram.class);
	 * 	}
	 * if (shapesDiagram == null){
	 * 		IAdapterManager manager = Platform.getAdapterManager();
	 * 		shapesDiagram = (ShapesDiagram) manager.getAdapter(diagramObject, ShapesDiagram.class);
	 * 	}
	 * return shapesDiagram;
	 * } </pre>
	 * @param diagramElement e.g. IFile, full diagram (ShapesDiagram)
	 * @see <a href="http://www.eclipse.org/articles/article.php?file=Article-Adapters/index.html">Eclipse Corner Article: Adapters</a>
	 * 
	 */
	public void doSave(Object diagramElement);
	
}
