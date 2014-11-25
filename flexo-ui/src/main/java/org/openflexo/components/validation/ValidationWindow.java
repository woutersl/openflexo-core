package org.openflexo.components.validation;

import javax.swing.JDialog;
import javax.swing.JFrame;

import org.openflexo.editor.SelectAndFocusObjectTask;
import org.openflexo.fib.editor.ComponentValidationWindow;
import org.openflexo.fib.model.FIBComponent;
import org.openflexo.fib.swing.validation.ValidationPanel;
import org.openflexo.foundation.FlexoObject;
import org.openflexo.localization.FlexoLocalization;
import org.openflexo.localization.LocalizedDelegate;
import org.openflexo.model.validation.Validable;
import org.openflexo.model.validation.ValidationIssue;
import org.openflexo.model.validation.ValidationModel;
import org.openflexo.model.validation.ValidationReport;
import org.openflexo.selection.SelectionManager;
import org.openflexo.view.controller.FlexoController;

/**
 * Non-modal window displaying a {@link ValidationPanel} for a {@link FlexoObject}<br>
 * Selection of issues is synchronized with the {@link FlexoController}'s {@link SelectionManager}
 * 
 * {@link ValidationModel} must be given to validate {@link FlexoObject}
 * 
 * @author sylvain
 * 
 */
@SuppressWarnings("serial")
public class ValidationWindow extends JDialog {

	private final FlexoValidationPanel validationPanel;
	private final FlexoController controller;
	private boolean isDisposed = false;

	public ValidationWindow(JFrame frame, FlexoController controller) {
		super(frame, FlexoLocalization.localizedForKey(FlexoLocalization.getMainLocalizer(), "validation"), ModalityType.MODELESS);
		this.controller = controller;
		validationPanel = new FlexoValidationPanel(null, FlexoLocalization.getMainLocalizer());
		getContentPane().add(validationPanel);
		pack();
	}

	public FlexoController getController() {
		return controller;
	}

	protected void performSelect(ValidationIssue<?, ?> validationIssue) {

		if (validationIssue != null && validationIssue.getValidable() instanceof FlexoObject) {
			SelectAndFocusObjectTask task = new SelectAndFocusObjectTask(getController(), (FlexoObject) validationIssue.getValidable());
			getController().getApplicationContext().getTaskManager().scheduleExecution(task);
		}
	}

	// private ValidationProgressListener validationProgressListener;

	/**
	 * Once the {@link ComponentValidationWindow} is instantiated, this is the way to launch validation for a given {@link FlexoObject} and
	 * a supplied {@link ValidationModel}
	 * 
	 * @param object
	 * @param validationModel
	 */
	public void validateAndDisplayReportForObject(FlexoObject object, ValidationModel validationModel) {
		setVisible(true);
		validationPanel.validate(validationModel, object);
	}

	@Override
	public void dispose() {
		super.dispose();
		isDisposed = true;
	}

	public boolean isDisposed() {
		return isDisposed;
	}

	protected class FlexoValidationPanel extends ValidationPanel {

		public FlexoValidationPanel(ValidationReport validationReport, LocalizedDelegate parentLocalizer) {
			super(validationReport, parentLocalizer);
		}

		@Override
		protected void performSelect(ValidationIssue<?, ?> validationIssue) {
			ValidationWindow.this.performSelect(validationIssue);
		}

		@Override
		protected FlexoFIBValidationController makeFIBController(FIBComponent fibComponent, LocalizedDelegate parentLocalizer) {
			FlexoFIBValidationController returned = new FlexoFIBValidationController(fibComponent) {
				@Override
				protected void performSelect(ValidationIssue<?, ?> validationIssue) {
					FlexoValidationPanel.this.performSelect(validationIssue);
				}
			};
			returned.setController(ValidationWindow.this.controller);
			return returned;
		}

		@Override
		public FlexoFIBValidationController getController() {
			return (FlexoFIBValidationController) super.getController();
		}

		/**
		 * Override parent implementation by launching validation in a dedicated task
		 */
		@Override
		public void validate(ValidationModel validationModel, Validable objectToValidate) {
			ValidationTask validationTask = new ValidationTask(validationModel, objectToValidate, getController());
			ValidationWindow.this.controller.getApplicationContext().getTaskManager().scheduleExecution(validationTask);
		}

	}
}