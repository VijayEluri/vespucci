package de.tud.cs.st.vespucci.mockprocessor;

import java.util.Set;

import de.tud.cs.st.vespucci.diagram.processing.IModelProcessor;
import de.tud.cs.st.vespucci.diagram.processing.Util;
import de.tud.cs.st.vespucci.information.interfaces.spi.SourceCodeElement;
import de.tud.cs.st.vespucci.information.interfaces.spi.Violation;
import de.tud.cs.st.vespucci.information.interfaces.spi.ViolationReport;
import de.tud.cs.st.vespucci.interfaces.ISourceCodeElement;
import de.tud.cs.st.vespucci.interfaces.IViolation;
import de.tud.cs.st.vespucci.interfaces.IViolationReport;
import de.tud.cs.st.vespucci.model.IArchitectureModel;
import de.tud.cs.st.vespucci.model.IConstraint;
import de.tud.cs.st.vespucci.model.IEnsemble;

public class MockProcessor implements IModelProcessor {

	@Override
	public Object processModel(Object diagramModel) {
		
		IArchitectureModel model = Util.adapt(diagramModel, IArchitectureModel.class);
		Set<IEnsemble> elements = model.getEnsembles();
		
		IEnsemble sourceElement = null;
		IEnsemble targetElement = null;
		
		int i = 0;
		for (IEnsemble iEnsemble : elements) {
			if (i == 0){
				sourceElement = iEnsemble;
				i++;
			}else{
				targetElement = iEnsemble;
				break;
			}
		}
		
		IConstraint myConstraint = null;
	
		for (IConstraint constraint : sourceElement.getTargetConnections()) {
			myConstraint = constraint;
		}		
		
		// Violation in Datei DataModel.java in der Zeile 9 der Methodenaufruf von MainController.doSome();
		ISourceCodeElement sourceCodeElement = new SourceCodeElement("model", "DataModel", 9);
		ISourceCodeElement targetCodeElement = new SourceCodeElement("controller", "MainController", 5);
		
		IViolation firstViolation = new Violation("Not allowed Call", 
													sourceCodeElement, 
													targetCodeElement, 
													sourceElement, 
													targetElement, 
													myConstraint);
			
		IViolationReport violationReport = new ViolationReport();
		
		violationReport.addViolation(firstViolation);
		
		return violationReport;
	}

	@Override
	public Class<?> resultClass() {
		return IViolationReport.class;
	}

}
