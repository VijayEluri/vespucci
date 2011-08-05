package de.tud.cs.st.vespucci.diagram.handler;

import java.util.Map;
import java.util.TreeMap;

import org.eclipse.core.commands.IParameterValues;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;

import de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes;

/**
 * Provides possible parameters for the SetType Command. See extension
 * org.eclipse.ui.commands.commandParameter in {@linkplain de.tud.cs.st.vespucci.diagram/plugin.xml}
 * for more context.
 * 
 * @author Alexander Weitzmann
 * @version 0.1
 * 
 */
public class SetConstraintTypeParameter implements IParameterValues {

	@Override
	public Map<String, IElementType> getParameterValues() {
		// TreeMap used for ordered keys
		final Map<String, IElementType> values = new TreeMap<String, IElementType>();
		values.put("Incoming", VespucciElementTypes
				.getElementType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.IncomingEditPart.VISUAL_ID));
		values.put("Outgoing", VespucciElementTypes
				.getElementType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.OutgoingEditPart.VISUAL_ID));
		values.put("In and Out", VespucciElementTypes
				.getElementType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.InAndOutEditPart.VISUAL_ID));
		values.put("Not allowed", VespucciElementTypes
				.getElementType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.NotAllowedEditPart.VISUAL_ID));
		values.put("Expected", VespucciElementTypes
				.getElementType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ExpectedEditPart.VISUAL_ID));
		return values;
	}

}
