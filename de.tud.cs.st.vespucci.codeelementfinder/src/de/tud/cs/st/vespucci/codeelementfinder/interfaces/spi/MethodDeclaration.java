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
package de.tud.cs.st.vespucci.codeelementfinder.interfaces.spi;

import java.util.Arrays;

import de.tud.cs.st.vespucci.interfaces.IMethodDeclaration;

/**
 * Concrete implementation of IMethodDelcaration
 * 
 * @author
 */
public class MethodDeclaration extends CodeElement implements
		IMethodDeclaration {

	private String methodName;
	private String returnType;
	private String[] paramTypes;

	public MethodDeclaration(String packageIdentifier, String simpleClassName,
			String methodName, String returnType, String[] paramTypes) {
		super(packageIdentifier, simpleClassName);
		this.methodName = methodName;
		this.returnType = returnType;
		this.paramTypes = paramTypes;
	}

	@Override
	public String getMethodName() {
		return this.methodName;
	}

	@Override
	public String getReturnTypeQualifier() {
		return this.returnType;
	}

	@Override
	public String[] getParameterTypeQualifiers() {
		return this.paramTypes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((methodName == null) ? 0 : methodName.hashCode());
		result = prime * result + Arrays.hashCode(paramTypes);
		result = prime * result
				+ ((returnType == null) ? 0 : returnType.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof IMethodDeclaration))
			return false;
		IMethodDeclaration other = (IMethodDeclaration) obj;
		if (methodName == null) {
			if (other.getMethodName() != null)
				return false;
		} else if (!methodName.equals(other.getMethodName()))
			return false;
		if (!Arrays.equals(paramTypes, other.getParameterTypeQualifiers()))
			return false;
		if (returnType == null) {
			if (other.getReturnTypeQualifier() != null)
				return false;
		} else if (!returnType.equals(other.getReturnTypeQualifier()))
			return false;
		return true;
	}
}
