/*
 *  License (BSD Style License):
 *   Copyright (c) 2010
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
package de.tud.cs.st.vespucci.diagram.dnd;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.emf.type.core.commands.SetValueCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;

import de.tud.cs.st.vespucci.vespucci_model.Shape;
import de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage;

/**
 * A EditDropPolicy that allow and handle the drop of ISelections on the
 * XXXEditPart that is corresponding to a subtype of the metaclass Shape. The
 * Request of the drop must be a DirectEditRequest.
 * 
 * @author MalteV
 * 
 */
public class EditDropPolicy extends DirectEditPolicy {

	@Override
	public void activate() {
		super.activate();
	}

	@Override
	public void deactivate() {
		super.deactivate();
	}

	@Override
	public void eraseSourceFeedback(Request request) {
		super.eraseSourceFeedback(request);
	}

	@Override
	public void eraseTargetFeedback(Request request) {
		super.eraseTargetFeedback(request);
	}

	@Override
	public Command getCommand(Request request) {
		if (RequestConstants.REQ_DIRECT_EDIT == request.getType())
			return getDirectEditCommand((DirectEditRequest) request);
		return null;
	}

	@Override
	public EditPart getHost() {
		return super.getHost();
	}

	@Override
	public EditPart getTargetEditPart(Request request) {
		EditPart ep = super.getTargetEditPart(request);
		if (ep != null)
			return ep;
		if (REQ_DIRECT_EDIT.equals(request.getType()))
			return getHost();
		return null;
	}

	@Override
	public void setHost(EditPart host) {
		super.setHost(host);
	}

	@Override
	public void showSourceFeedback(Request request) {
		super.showSourceFeedback(request);
	}

	@Override
	public void showTargetFeedback(Request request) {
		super.showTargetFeedback(request);
	}

	@Override
	public String toString() {
		return super.toString();
	}

	@Override
	public boolean understandsRequest(Request req) {
		return super.understandsRequest(req);
	}

	@Override
	protected Command getDirectEditCommand(DirectEditRequest request) {
		// TODO put "http://vespucci.editor" in some constant
		// Make sure that XXXEditPart is an instanceof GraphicalEditPart
		// so that it support resolveSemanticElement()
		// Furthermore make sure that the resolveSemanticElement() is from type
		// Shape
		if (getHost() instanceof GraphicalEditPart
				&& ((GraphicalEditPart) getHost()).resolveSemanticElement() instanceof Shape) {
			// we need infos about the EMF meta modell so we refer to the
			// Singelton that save Vespucci_modelPackage
			EPackage epackage = org.eclipse.emf.ecore.EPackage.Registry.INSTANCE
					.getEPackage("http://vespucci.editor");
			Vespucci_modelPackage vesPackage = (Vespucci_modelPackage) epackage;
			if ((((GraphicalEditPart) getHost()).resolveSemanticElement())
					.eClass().getEAllStructuralFeatures()
					.contains(vesPackage.getShape_Query())) {

				// get the old query
				Object oldQuery = (((GraphicalEditPart) getHost())
						.resolveSemanticElement()).eGet(vesPackage
						.getShape_Query());
				@SuppressWarnings("unchecked")
				SetRequest sr = new SetRequest(
						((GraphicalEditPart) getHost())
								.resolveSemanticElement(),
						vesPackage.getShape_Query(), QueryBuilder
								.createQueryForAMapOfIResource(
										request.getExtendedData(),
										(String) oldQuery));

				org.eclipse.gmf.runtime.common.core.command.ICommand com = new SetValueCommand(
						sr);
				// return the edit Request in a proxy so it can be handled
				return new org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy(
						com);

			}
		}
		return null;
	}

	@Override
	protected void showCurrentEditValue(DirectEditRequest request) {

	}

}
