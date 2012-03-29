/*
 *  License (BSD Style License):
 *   Copyright (c) 2011
 *   Software Technology Group
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
 *   - Neither the name of the Software Technology Group Group or Technische
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
package de.tud.cs.st.vespucci.diagram.model.output.spi;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.util.EList;

import de.tud.cs.st.vespucci.model.IConstraint;
import de.tud.cs.st.vespucci.model.IEnsemble;
import de.tud.cs.st.vespucci.vespucci_model.Dummy;
import de.tud.cs.st.vespucci.vespucci_model.Shape;

public class ConversionUtils {

	public static IEnsemble createEnsemble(Shape shape) {
		if (shape instanceof Dummy)
			return new EmptyEnsemble((Dummy) shape);
		else
			return new Ensemble(shape);
	}

	static Set<IConstraint> getIConnections(
			EList<de.tud.cs.st.vespucci.vespucci_model.Connection> eList) {
		Set<IConstraint> connections = new HashSet<IConstraint>();

		for (de.tud.cs.st.vespucci.vespucci_model.Connection connection : eList) {
			if (!connection.getTarget().eIsProxy())
				connections.add(createIConnectedSubtypeInstance(connection));
		}

		return connections;
	}

	static Constraint createIConnectedSubtypeInstance(
			de.tud.cs.st.vespucci.vespucci_model.Connection connection) {

		if (connection instanceof de.tud.cs.st.vespucci.vespucci_model.Expected) {
			return new Expected(
					(de.tud.cs.st.vespucci.vespucci_model.Expected) connection);
		}
		if (connection instanceof de.tud.cs.st.vespucci.vespucci_model.Incoming) {
			return new Incoming(
					(de.tud.cs.st.vespucci.vespucci_model.Incoming) connection);
		}
		if (connection instanceof de.tud.cs.st.vespucci.vespucci_model.NotAllowed) {
			return new NotAllowed(
					(de.tud.cs.st.vespucci.vespucci_model.NotAllowed) connection);
		}
		if (connection instanceof de.tud.cs.st.vespucci.vespucci_model.Outgoing) {
			return new Outgoing(
					(de.tud.cs.st.vespucci.vespucci_model.Outgoing) connection);
		}
		if (connection instanceof de.tud.cs.st.vespucci.vespucci_model.GlobalIncoming) {
			return new GlobalIncoming(
					(de.tud.cs.st.vespucci.vespucci_model.GlobalIncoming) connection);
		}
		if (connection instanceof de.tud.cs.st.vespucci.vespucci_model.GlobalOutgoing) {
			return new GlobalOutgoing(
					(de.tud.cs.st.vespucci.vespucci_model.GlobalOutgoing) connection);
		}
		if (connection instanceof de.tud.cs.st.vespucci.vespucci_model.InAndOut) {
			return new InAndOut(
					(de.tud.cs.st.vespucci.vespucci_model.InAndOut) connection);
		}
		if (connection instanceof de.tud.cs.st.vespucci.vespucci_model.Violation) {
			return new DocumentedViolation(
					(de.tud.cs.st.vespucci.vespucci_model.Violation) connection);
		}

		return new Constraint(connection);

	}

}
