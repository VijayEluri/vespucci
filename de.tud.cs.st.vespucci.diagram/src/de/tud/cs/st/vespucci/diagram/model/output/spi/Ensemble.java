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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.eclipse.emf.common.util.EList;

import de.tud.cs.st.vespucci.diagram.interfaces.IConstraint;
import de.tud.cs.st.vespucci.diagram.interfaces.IEnsemble;

/**
 * 
 * @author Patrick Gottschämmer
 * @author Olav Lenz
 */
public class Ensemble implements IEnsemble {

	private de.tud.cs.st.vespucci.vespucci_model.Shape shape;
	
	Map<Class<? extends de.tud.cs.st.vespucci.vespucci_model.Connection>, Class<? extends Constraint>> mapping;
	
	private LinkedList<IEnsemble> innerEnsemble;
	private LinkedList<IConstraint> sourceConnection;
	private LinkedList<IConstraint> targetConnection;
	
	public Ensemble(de.tud.cs.st.vespucci.vespucci_model.Shape ensemble) {
		this.shape = ensemble;
	}
	
	@Override
	public LinkedList<IEnsemble> getInnerEnsembles() {
		if (innerEnsemble == null){
			innerEnsemble = getInnerEnsemble();
		}
		return innerEnsemble;	
	}

	private LinkedList<IEnsemble> getInnerEnsemble() {
		if (shape instanceof de.tud.cs.st.vespucci.vespucci_model.Ensemble){
			de.tud.cs.st.vespucci.vespucci_model.Ensemble ensemble = (de.tud.cs.st.vespucci.vespucci_model.Ensemble) shape;
						
			LinkedList<IEnsemble> connections = new LinkedList<IEnsemble>();
			for (de.tud.cs.st.vespucci.vespucci_model.Shape shape : ensemble.getShapes()) {
				connections.add(new Ensemble(shape));
			}		
			return connections;
		}
		return new LinkedList<IEnsemble>();
	}

	@Override
	public String getDescription() {
		return shape.getDescription();
	}

	@Override
	public String getName() {
		return shape.getName();
	}

	@Override
	public String getQuery() {
		return shape.getQuery();
	}

	@Override
	public LinkedList<IConstraint> getSourceConnections() {
		if (sourceConnection == null){
			sourceConnection = getIConnections(shape.getSourceConnections());
		}
		return sourceConnection;	
	}

	@Override
	public LinkedList<IConstraint> getTargetConnections() {
		if (targetConnection == null){
			targetConnection = getIConnections(shape.getTargetConnections());
		}
		return targetConnection;	
	}

	private LinkedList<IConstraint> getIConnections(EList<de.tud.cs.st.vespucci.vespucci_model.Connection> eList) {
		LinkedList<IConstraint> connections = new LinkedList<IConstraint>();
		for (de.tud.cs.st.vespucci.vespucci_model.Connection connection : eList) {
						connections.add(createIConnectedSubtypeInstance(connection));
		}		
		return connections;
	}

	private Constraint createIConnectedSubtypeInstance(de.tud.cs.st.vespucci.vespucci_model.Connection connection) {
		
		fillMapWithMappingClasses();
		// Map connection
		  try {
		    Class<? extends Constraint> subtype = mapping.get( connection.getClass() );
		    
		    // Use for debugging only
		    //System.out.println("from createIConnectedSubtypeInstance, connection.getClass():" + connection.getClass());
		    //System.out.println("subtype.getName(): " + subtype.getName());
		    return subtype.getConstructor( connection.getClass().getInterfaces() ).newInstance( connection );
		    
		  } catch( Exception e) {
			  
			// Use for debugging only
			//e.printStackTrace();
		    return new Constraint(connection);
		  }
	}

	private void fillMapWithMappingClasses() {
		// Initialize Map
		this.mapping = new HashMap<Class<? extends de.tud.cs.st.vespucci.vespucci_model.Connection>, Class<? extends Constraint>>();
		
		/*
		 * Old:
		 * mapping.put(de.tud.cs.st.vespucci.vespucci_model.Expected.class, Expected.class);
		 * .	.	.
		 * .	.	.
		 * .	.	.
		 * mapping.put(de.tud.cs.st.vespucci.vespucci_model.Violation.class, DocumentedViolation.class);
		 * 
		 * New, cause: connection.getClass() (in createIConnectedSubtypeInstance) delivers (e.g.) 
		 * 			de.tud.cs.st.vespucci.vespucci_model.impl.ExpectedImpl.class
		 * and NOT: 
		 * 			de.tud.cs.st.vespucci.vespucci_model.Expected.class
		 * , see Old
		 */
		
		// Put Mapping values (de.tud.cs.st.vespucci.vespucci_model.impl.ExpectedImpl.class --> Expected)
		mapping.put(de.tud.cs.st.vespucci.vespucci_model.impl.ExpectedImpl.class, Expected.class);
		mapping.put(de.tud.cs.st.vespucci.vespucci_model.impl.IncomingImpl.class, Incoming.class);
		mapping.put(de.tud.cs.st.vespucci.vespucci_model.impl.NotAllowedImpl.class, NotAllowed.class);
		mapping.put(de.tud.cs.st.vespucci.vespucci_model.impl.OutgoingImpl.class, Outgoing.class);
		mapping.put(de.tud.cs.st.vespucci.vespucci_model.impl.GlobalIncomingImpl.class, GlobalIncoming.class);
		mapping.put(de.tud.cs.st.vespucci.vespucci_model.impl.GlobalOutgoingImpl.class, GlobalOutgoing.class);
		mapping.put(de.tud.cs.st.vespucci.vespucci_model.impl.InAndOutImpl.class, InAndOut.class);
		mapping.put(de.tud.cs.st.vespucci.vespucci_model.impl.ViolationImpl.class, DocumentedViolation.class);
	}
	
}