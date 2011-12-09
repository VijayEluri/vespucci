package de.tud.cs.st.vespucci.interfaces;

import java.util.List;

public interface IMethodElement {

	String getMethodName();
	String getReturnType();
	List<String> getListParamTypes();
	
}
