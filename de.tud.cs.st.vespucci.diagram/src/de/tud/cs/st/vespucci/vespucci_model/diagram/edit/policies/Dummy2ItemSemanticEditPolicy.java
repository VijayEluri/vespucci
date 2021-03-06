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
package de.tud.cs.st.vespucci.vespucci_model.diagram.edit.policies;

import java.util.Iterator;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.core.commands.DeleteCommand;
import org.eclipse.gmf.runtime.emf.commands.core.command.CompositeTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;

/**
 * @generated
 */
public class Dummy2ItemSemanticEditPolicy extends
		de.tud.cs.st.vespucci.vespucci_model.diagram.edit.policies.VespucciBaseItemSemanticEditPolicy {

	/**
	 * @generated
	 */
	public Dummy2ItemSemanticEditPolicy() {
		super(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Dummy_3003);
	}

	/**
	 * @generated
	 */
	protected Command getDestroyElementCommand(DestroyElementRequest req) {
		View view = (View) getHost().getModel();
		CompositeTransactionalCommand cmd = new CompositeTransactionalCommand(getEditingDomain(), null);
		cmd.setTransactionNestingEnabled(false);
		for (Iterator<?> it = view.getTargetEdges().iterator(); it.hasNext();) {
			Edge incomingLink = (Edge) it.next();
			if (de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry.getVisualID(incomingLink) == de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.IncomingEditPart.VISUAL_ID) {
				DestroyElementRequest r = new DestroyElementRequest(incomingLink.getElement(), false);
				cmd.add(new DestroyElementCommand(r));
				cmd.add(new DeleteCommand(getEditingDomain(), incomingLink));
				continue;
			}
			if (de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry.getVisualID(incomingLink) == de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.OutgoingEditPart.VISUAL_ID) {
				DestroyElementRequest r = new DestroyElementRequest(incomingLink.getElement(), false);
				cmd.add(new DestroyElementCommand(r));
				cmd.add(new DeleteCommand(getEditingDomain(), incomingLink));
				continue;
			}
			if (de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry.getVisualID(incomingLink) == de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.InAndOutEditPart.VISUAL_ID) {
				DestroyElementRequest r = new DestroyElementRequest(incomingLink.getElement(), false);
				cmd.add(new DestroyElementCommand(r));
				cmd.add(new DeleteCommand(getEditingDomain(), incomingLink));
				continue;
			}
			if (de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry.getVisualID(incomingLink) == de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.NotAllowedEditPart.VISUAL_ID) {
				DestroyElementRequest r = new DestroyElementRequest(incomingLink.getElement(), false);
				cmd.add(new DestroyElementCommand(r));
				cmd.add(new DeleteCommand(getEditingDomain(), incomingLink));
				continue;
			}
			if (de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry.getVisualID(incomingLink) == de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ExpectedEditPart.VISUAL_ID) {
				DestroyElementRequest r = new DestroyElementRequest(incomingLink.getElement(), false);
				cmd.add(new DestroyElementCommand(r));
				cmd.add(new DeleteCommand(getEditingDomain(), incomingLink));
				continue;
			}
			if (de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry.getVisualID(incomingLink) == de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.GlobalIncomingEditPart.VISUAL_ID) {
				DestroyElementRequest r = new DestroyElementRequest(incomingLink.getElement(), false);
				cmd.add(new DestroyElementCommand(r));
				cmd.add(new DeleteCommand(getEditingDomain(), incomingLink));
				continue;
			}
			if (de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry.getVisualID(incomingLink) == de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.GlobalOutgoingEditPart.VISUAL_ID) {
				DestroyElementRequest r = new DestroyElementRequest(incomingLink.getElement(), false);
				cmd.add(new DestroyElementCommand(r));
				cmd.add(new DeleteCommand(getEditingDomain(), incomingLink));
				continue;
			}
			if (de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry.getVisualID(incomingLink) == de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ViolationEditPart.VISUAL_ID) {
				DestroyElementRequest r = new DestroyElementRequest(incomingLink.getElement(), false);
				cmd.add(new DestroyElementCommand(r));
				cmd.add(new DeleteCommand(getEditingDomain(), incomingLink));
				continue;
			}
		}
		for (Iterator<?> it = view.getSourceEdges().iterator(); it.hasNext();) {
			Edge outgoingLink = (Edge) it.next();
			if (de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry.getVisualID(outgoingLink) == de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.IncomingEditPart.VISUAL_ID) {
				DestroyElementRequest r = new DestroyElementRequest(outgoingLink.getElement(), false);
				cmd.add(new DestroyElementCommand(r));
				cmd.add(new DeleteCommand(getEditingDomain(), outgoingLink));
				continue;
			}
			if (de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry.getVisualID(outgoingLink) == de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.OutgoingEditPart.VISUAL_ID) {
				DestroyElementRequest r = new DestroyElementRequest(outgoingLink.getElement(), false);
				cmd.add(new DestroyElementCommand(r));
				cmd.add(new DeleteCommand(getEditingDomain(), outgoingLink));
				continue;
			}
			if (de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry.getVisualID(outgoingLink) == de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.InAndOutEditPart.VISUAL_ID) {
				DestroyElementRequest r = new DestroyElementRequest(outgoingLink.getElement(), false);
				cmd.add(new DestroyElementCommand(r));
				cmd.add(new DeleteCommand(getEditingDomain(), outgoingLink));
				continue;
			}
			if (de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry.getVisualID(outgoingLink) == de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.NotAllowedEditPart.VISUAL_ID) {
				DestroyElementRequest r = new DestroyElementRequest(outgoingLink.getElement(), false);
				cmd.add(new DestroyElementCommand(r));
				cmd.add(new DeleteCommand(getEditingDomain(), outgoingLink));
				continue;
			}
			if (de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry.getVisualID(outgoingLink) == de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ExpectedEditPart.VISUAL_ID) {
				DestroyElementRequest r = new DestroyElementRequest(outgoingLink.getElement(), false);
				cmd.add(new DestroyElementCommand(r));
				cmd.add(new DeleteCommand(getEditingDomain(), outgoingLink));
				continue;
			}
			if (de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry.getVisualID(outgoingLink) == de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.GlobalIncomingEditPart.VISUAL_ID) {
				DestroyElementRequest r = new DestroyElementRequest(outgoingLink.getElement(), false);
				cmd.add(new DestroyElementCommand(r));
				cmd.add(new DeleteCommand(getEditingDomain(), outgoingLink));
				continue;
			}
			if (de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry.getVisualID(outgoingLink) == de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.GlobalOutgoingEditPart.VISUAL_ID) {
				DestroyElementRequest r = new DestroyElementRequest(outgoingLink.getElement(), false);
				cmd.add(new DestroyElementCommand(r));
				cmd.add(new DeleteCommand(getEditingDomain(), outgoingLink));
				continue;
			}
			if (de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry.getVisualID(outgoingLink) == de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ViolationEditPart.VISUAL_ID) {
				DestroyElementRequest r = new DestroyElementRequest(outgoingLink.getElement(), false);
				cmd.add(new DestroyElementCommand(r));
				cmd.add(new DeleteCommand(getEditingDomain(), outgoingLink));
				continue;
			}
		}
		EAnnotation annotation = view.getEAnnotation("Shortcut"); //$NON-NLS-1$
		if (annotation == null) {
			// there are indirectly referenced children, need extra commands: false
			addDestroyShortcutsCommand(cmd, view);
			// delete host element
			cmd.add(new DestroyElementCommand(req));
		} else {
			cmd.add(new DeleteCommand(getEditingDomain(), view));
		}
		return getGEFWrapper(cmd.reduce());
	}

	/**
	 * @generated
	 */
	protected Command getCreateRelationshipCommand(CreateRelationshipRequest req) {
		Command command = req.getTarget() == null ? getStartCreateRelationshipCommand(req)
				: getCompleteCreateRelationshipCommand(req);
		return command != null ? command : super.getCreateRelationshipCommand(req);
	}

	/**
	 * @generated
	 */
	protected Command getStartCreateRelationshipCommand(CreateRelationshipRequest req) {
		if (de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Incoming_4005 == req.getElementType()) {
			return getGEFWrapper(new de.tud.cs.st.vespucci.vespucci_model.diagram.edit.commands.IncomingCreateCommand(req,
					req.getSource(), req.getTarget()));
		}
		if (de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Outgoing_4003 == req.getElementType()) {
			return getGEFWrapper(new de.tud.cs.st.vespucci.vespucci_model.diagram.edit.commands.OutgoingCreateCommand(req,
					req.getSource(), req.getTarget()));
		}
		if (de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.InAndOut_4001 == req.getElementType()) {
			return getGEFWrapper(new de.tud.cs.st.vespucci.vespucci_model.diagram.edit.commands.InAndOutCreateCommand(req,
					req.getSource(), req.getTarget()));
		}
		if (de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.NotAllowed_4004 == req.getElementType()) {
			return getGEFWrapper(new de.tud.cs.st.vespucci.vespucci_model.diagram.edit.commands.NotAllowedCreateCommand(req,
					req.getSource(), req.getTarget()));
		}
		if (de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Expected_4002 == req.getElementType()) {
			return getGEFWrapper(new de.tud.cs.st.vespucci.vespucci_model.diagram.edit.commands.ExpectedCreateCommand(req,
					req.getSource(), req.getTarget()));
		}
		if (de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.GlobalIncoming_4006 == req
				.getElementType()) {
			return getGEFWrapper(new de.tud.cs.st.vespucci.vespucci_model.diagram.edit.commands.GlobalIncomingCreateCommand(req,
					req.getSource(), req.getTarget()));
		}
		if (de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.GlobalOutgoing_4007 == req
				.getElementType()) {
			return getGEFWrapper(new de.tud.cs.st.vespucci.vespucci_model.diagram.edit.commands.GlobalOutgoingCreateCommand(req,
					req.getSource(), req.getTarget()));
		}
		if (de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Violation_4009 == req.getElementType()) {
			return getGEFWrapper(new de.tud.cs.st.vespucci.vespucci_model.diagram.edit.commands.ViolationCreateCommand(req,
					req.getSource(), req.getTarget()));
		}
		return null;
	}

	/**
	 * @generated
	 */
	protected Command getCompleteCreateRelationshipCommand(CreateRelationshipRequest req) {
		if (de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Incoming_4005 == req.getElementType()) {
			return getGEFWrapper(new de.tud.cs.st.vespucci.vespucci_model.diagram.edit.commands.IncomingCreateCommand(req,
					req.getSource(), req.getTarget()));
		}
		if (de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Outgoing_4003 == req.getElementType()) {
			return getGEFWrapper(new de.tud.cs.st.vespucci.vespucci_model.diagram.edit.commands.OutgoingCreateCommand(req,
					req.getSource(), req.getTarget()));
		}
		if (de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.InAndOut_4001 == req.getElementType()) {
			return getGEFWrapper(new de.tud.cs.st.vespucci.vespucci_model.diagram.edit.commands.InAndOutCreateCommand(req,
					req.getSource(), req.getTarget()));
		}
		if (de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.NotAllowed_4004 == req.getElementType()) {
			return getGEFWrapper(new de.tud.cs.st.vespucci.vespucci_model.diagram.edit.commands.NotAllowedCreateCommand(req,
					req.getSource(), req.getTarget()));
		}
		if (de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Expected_4002 == req.getElementType()) {
			return getGEFWrapper(new de.tud.cs.st.vespucci.vespucci_model.diagram.edit.commands.ExpectedCreateCommand(req,
					req.getSource(), req.getTarget()));
		}
		if (de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.GlobalIncoming_4006 == req
				.getElementType()) {
			return getGEFWrapper(new de.tud.cs.st.vespucci.vespucci_model.diagram.edit.commands.GlobalIncomingCreateCommand(req,
					req.getSource(), req.getTarget()));
		}
		if (de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.GlobalOutgoing_4007 == req
				.getElementType()) {
			return getGEFWrapper(new de.tud.cs.st.vespucci.vespucci_model.diagram.edit.commands.GlobalOutgoingCreateCommand(req,
					req.getSource(), req.getTarget()));
		}
		if (de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Violation_4009 == req.getElementType()) {
			return getGEFWrapper(new de.tud.cs.st.vespucci.vespucci_model.diagram.edit.commands.ViolationCreateCommand(req,
					req.getSource(), req.getTarget()));
		}
		return null;
	}

	/**
	 * Returns command to reorient EClass based link. New link target or source
	 * should be the domain model element associated with this node.
	 * 
	 * @generated
	 */
	protected Command getReorientRelationshipCommand(ReorientRelationshipRequest req) {
		switch (getVisualID(req)) {
			case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.IncomingEditPart.VISUAL_ID:
				return getGEFWrapper(new de.tud.cs.st.vespucci.vespucci_model.diagram.edit.commands.IncomingReorientCommand(req));
			case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.OutgoingEditPart.VISUAL_ID:
				return getGEFWrapper(new de.tud.cs.st.vespucci.vespucci_model.diagram.edit.commands.OutgoingReorientCommand(req));
			case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.InAndOutEditPart.VISUAL_ID:
				return getGEFWrapper(new de.tud.cs.st.vespucci.vespucci_model.diagram.edit.commands.InAndOutReorientCommand(req));
			case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.NotAllowedEditPart.VISUAL_ID:
				return getGEFWrapper(new de.tud.cs.st.vespucci.vespucci_model.diagram.edit.commands.NotAllowedReorientCommand(req));
			case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ExpectedEditPart.VISUAL_ID:
				return getGEFWrapper(new de.tud.cs.st.vespucci.vespucci_model.diagram.edit.commands.ExpectedReorientCommand(req));
			case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.GlobalIncomingEditPart.VISUAL_ID:
				return getGEFWrapper(new de.tud.cs.st.vespucci.vespucci_model.diagram.edit.commands.GlobalIncomingReorientCommand(
						req));
			case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.GlobalOutgoingEditPart.VISUAL_ID:
				return getGEFWrapper(new de.tud.cs.st.vespucci.vespucci_model.diagram.edit.commands.GlobalOutgoingReorientCommand(
						req));
			case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ViolationEditPart.VISUAL_ID:
				return getGEFWrapper(new de.tud.cs.st.vespucci.vespucci_model.diagram.edit.commands.ViolationReorientCommand(req));
		}
		return super.getReorientRelationshipCommand(req);
	}

}
