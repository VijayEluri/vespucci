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
package de.tud.cs.st.vespucci.model.adapters;

import de.tud.cs.st.vespucci.model.IConstraint;
import de.tud.cs.st.vespucci.model.IEnsemble;
import de.tud.cs.st.vespucci.vespucci_model.Connection;

/**
 * Implementation of an IConstraintAdapter
 * @author Robert Cibulla
 *
 */
public class ConstraintAdapter implements IConstraint {
	
	/**
	 * The adaptee
	 */
	private Connection adaptee;
	
	/**
	 * The source IEnsemble;
	 */
	private IEnsemble source;
	
	/**
	 * The target IEnsemble
	 */
	private IEnsemble target;
	
	/**
	 * Constructor
	 * @param adaptee
	 */
	public ConstraintAdapter(Connection adaptee){
		super();
		this.adaptee = adaptee;
	}
	
	/**
	 * Initialize this IConstraint
	 */
	protected void init(){
		initSource();
		initTarget();
	}

	public String getDependencyKind() {
		return adaptee.getName();
	}

	public IEnsemble getSource() {
		return source;
	}

	public IEnsemble getTarget() {
		return target;
	}

	/**
	 * @return the adaptee
	 */
	public Connection getAdaptee() {
		return adaptee;
	}
	
	private void initSource() {
		this.source = AdapterRegistry.getEnsembleAdapter(adaptee.getSource());		
	}

	private void initTarget() {
		this.target = AdapterRegistry.getEnsembleAdapter(adaptee.getTarget());
	}

}
