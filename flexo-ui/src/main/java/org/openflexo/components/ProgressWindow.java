/**
 * 
 * Copyright (c) 2013-2014, Openflexo
 * Copyright (c) 2011-2012, AgileBirds
 * 
 * This file is part of Flexo-ui, a component of the software infrastructure 
 * developed at Openflexo.
 * 
 * 
 * Openflexo is dual-licensed under the European Union Public License (EUPL, either 
 * version 1.1 of the License, or any later version ), which is available at 
 * https://joinup.ec.europa.eu/software/page/eupl/licence-eupl
 * and the GNU General Public License (GPL, either version 3 of the License, or any 
 * later version), which is available at http://www.gnu.org/licenses/gpl.html .
 * 
 * You can redistribute it and/or modify under the terms of either of these licenses
 * 
 * If you choose to redistribute it and/or modify under the terms of the GNU GPL, you
 * must include the following additional permission.
 *
 *          Additional permission under GNU GPL version 3 section 7
 *
 *          If you modify this Program, or any covered work, by linking or 
 *          combining it with software containing parts covered by the terms 
 *          of EPL 1.0, the licensors of this Program grant you additional permission
 *          to convey the resulting work. * 
 * 
 * This software is distributed in the hope that it will be useful, but WITHOUT ANY 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A 
 * PARTICULAR PURPOSE. 
 *
 * See http://www.openflexo.org/license.html for details.
 * 
 * 
 * Please contact Openflexo (openflexo-contacts@openflexo.org)
 * or visit www.openflexo.org if you need additional information.
 * 
 */

package org.openflexo.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import org.openflexo.FlexoCst;
import org.openflexo.foundation.utils.FlexoProgress;
import org.openflexo.icon.IconLibrary;
import org.openflexo.view.FlexoFrame;

/**
 * Default progress window
 * 
 * @author sguerin
 */
@Deprecated
public class ProgressWindow extends JDialog implements FlexoProgress {

	private static class ProgressBarLabel extends JLabel {

		private static final Color LABEL_COLOR = FlexoCst.OPEN_BLUE_COLOR;

		public ProgressBarLabel() {
			super();
			setForeground(LABEL_COLOR);
		}

		public ProgressBarLabel(Icon image, int horizontalAlignment) {
			super(image, horizontalAlignment);
			setForeground(LABEL_COLOR);
		}

		public ProgressBarLabel(Icon image) {
			super(image);
			setForeground(LABEL_COLOR);
		}

		public ProgressBarLabel(String text, Icon icon, int horizontalAlignment) {
			super(text, icon, horizontalAlignment);
			setForeground(LABEL_COLOR);
		}

		public ProgressBarLabel(String text, int horizontalAlignment) {
			super(text, horizontalAlignment);
			setForeground(LABEL_COLOR);
		}

		public ProgressBarLabel(String text) {
			super(text);
			setForeground(LABEL_COLOR);
		}

	}

	private static final Logger logger = Logger.getLogger(ProgressWindow.class.getPackage().getName());

	private static ProgressWindow _instance;

	// protected WhiteLabel flexoLogo;

	protected ProgressBarLabel label;

	protected ProgressBarLabel mainProgressBarLabel;

	protected ProgressBarLabel secondaryProgressBarLabel;

	protected JProgressBar mainProgressBar;

	protected JProgressBar secondaryProgressBar;

	protected int mainProgress = 0;

	protected int secondaryProgress = 0;

	// protected boolean backgroundIsPainted = false;

	protected boolean isSecondaryProgressIndeterminate = true;
	ImageIcon icon;
	protected Window initOwner;

	protected JPanel mainPane;

	private final MouseAdapter mouseListener = new MouseAdapter() {

	};

	private final FocusListener focusListener = new FocusListener() {

		@Override
		public void focusLost(FocusEvent e) {
			if (getOwner() instanceof JFrame) {
				Component glassPane = ((JFrame) getOwner()).getGlassPane();
				if (glassPane.isVisible()) {
					glassPane.requestFocusInWindow();
				}
			}
		}

		@Override
		public void focusGained(FocusEvent e) {

		}
	};

	public synchronized static ProgressWindow makeProgressWindow(String title, int steps) {
		if (_instance != null) {
			logger.warning("Invoke creation of new progress window while an other one is displayed. Using old one.");
		} else {
			_instance = new ProgressWindow(getActiveModuleFrame(), title, steps);
		}
		return _instance;
	}

	private ProgressWindow(Window window, String title, int steps) {
		super(window);
		setUndecorated(true);
		initOwner = window;
		if (initOwner != null) {
			initOwner.addComponentListener(new ComponentAdapter() {

				@Override
				public void componentMoved(ComponentEvent e) {
					center();
				}

				@Override
				public void componentResized(ComponentEvent e) {
					center();
				}
			});
		}
		// logger.info("Build progress max="+steps);
		setFocusable(false);
		setAlwaysOnTop(false);
		mainProgress = 0;
		secondaryProgress = 0;
		mainProgressBar = new JProgressBar(0, Math.max(1, steps));
		mainProgressBar.setIndeterminate(steps <= 0);
		mainProgressBar.setStringPainted(false);
		mainProgressBar.setValue(mainProgress);
		secondaryProgressBar = new JProgressBar();
		secondaryProgressBar.setIndeterminate(isSecondaryProgressIndeterminate);
		secondaryProgressBar.setStringPainted(false);
		// flexoLogo = new WhiteLabel(IconLibrary.LOGIN_IMAGE);
		label = new ProgressBarLabel(title, SwingConstants.CENTER);
		label.setFont(FlexoCst.BIG_FONT);
		mainProgressBarLabel = new ProgressBarLabel("", SwingConstants.LEFT);
		mainProgressBarLabel.setFont(FlexoCst.NORMAL_FONT);

		secondaryProgressBarLabel = new ProgressBarLabel("", SwingConstants.LEFT);
		secondaryProgressBarLabel.setFont(FlexoCst.NORMAL_FONT);
		secondaryProgressBarLabel.setForeground(Color.DARK_GRAY);

		icon = IconLibrary.PROGRESS_BACKGROUND;
		mainPane = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				// Dispaly image at at full size
				g.drawImage(icon.getImage(), 0, 0, null);

				// Scale image to size of component
				// Dimension d = getSize();
				// g.drawImage(icon.getImage(), 0, 0, d.width, d.height, null);

				// Fix the image position in the scroll pane
				// Point p = scrollPane.getViewport().getViewPosition();
				// g.drawImage(icon.getImage(), p.x, p.y, null);

				super.paintComponent(g);
			}
		};
		mainPane.setLayout(null);
		mainPane.setOpaque(false);
		// panel.add(flexoLogo);
		mainPane.setBorder(BorderFactory.createLineBorder(FlexoCst.UNDECORATED_DIALOG_BORDER_COLOR));
		mainPane.add(label);
		mainPane.add(mainProgressBarLabel);
		mainPane.add(mainProgressBar);
		mainPane.add(secondaryProgressBarLabel);
		mainPane.add(secondaryProgressBar);
		// flexoLogo.setBounds(135, 15, 230, 80);
		label.setBounds(50, /* 115 */180, 510, 20);
		mainProgressBarLabel.setBounds(25, /* 150 */205, 560, 15);
		mainProgressBar.setBounds(25, /* 165 */220, 560, 15);
		secondaryProgressBarLabel.setBounds(25, /* 200 */245, 560, 15);
		secondaryProgressBar.setBounds(25, /* 215 */260, 560, 15);
		mainPane.setPreferredSize(new Dimension(600, 300));
		getContentPane().add(mainPane);
		setSize(600, 300);
		pack();
		center();
		setVisible(true);
		paintImmediately();
		if (logger.isLoggable(Level.FINE)) {
			logger.fine("Displaying progress window");
		}
	}

	protected static FlexoFrame getActiveModuleFrame() {
		return FlexoFrame.getActiveFrame();
	}

	private void paintImmediately() {
		if (!SwingUtilities.isEventDispatchThread()) {
			repaint();
			return;
		}
		mainPane.paintImmediately(mainPane.getBounds());
	}

	@Override
	public void setVisible(boolean b) {
		if (getOwner() instanceof JFrame) {
			((JFrame) getOwner()).getGlassPane().setVisible(b);
			if (b) {
				((JFrame) getOwner()).getGlassPane().addMouseListener(mouseListener);
				((JFrame) getOwner()).getGlassPane().addMouseMotionListener(mouseListener);
				((JFrame) getOwner()).getGlassPane().addFocusListener(focusListener);
				((JFrame) getOwner()).getGlassPane().requestFocusInWindow();
			} else {
				((JFrame) getOwner()).getGlassPane().removeMouseListener(mouseListener);
				((JFrame) getOwner()).getGlassPane().removeMouseMotionListener(mouseListener);
				((JFrame) getOwner()).getGlassPane().removeFocusListener(focusListener);
			}
		}
		super.setVisible(b);
		if (b) {
			_instance = this;
		}
	}

	public static void showProgressWindow(String title, int steps) {
		showProgressWindow(getActiveModuleFrame(), title, steps);
	}

	public synchronized static void showProgressWindow(Window owner, String title, int steps) {
		if (_instance != null) {
			logger.warning("Try to open another ProgressWindow !!!!");
		} else {
			_instance = new ProgressWindow(owner, title, steps);
		}
	}

	public synchronized static void hideProgressWindow() {
		if (_instance != null) {
			_instance.hideWindow();
			_instance = null;
		}
	}

	@Override
	public void hideWindow() {
		if (!SwingUtilities.isEventDispatchThread()) {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					hideWindow();
				}
			});
			return;
		}
		setVisible(false);
		dispose();
		if (initOwner != null) {
			initOwner.repaint();
		}
		if (_instance == this) {
			_instance = null;
		}
	}

	public static ProgressWindow instance() {
		return _instance;
	}

	public static boolean hasInstance() {
		return _instance != null;
	}

	@Override
	public void setProgress(final String stepName) {
		if (!SwingUtilities.isEventDispatchThread()) {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					setProgress(stepName);
				}
			});
			return;
		}
		// logger.info("Progress "+mainProgress+"/"+mainProgressBar.getMaximum());
		if (!isVisible()) {
			if (logger.isLoggable(Level.WARNING)) {
				logger.warning("@@@@@@@@@@@@@ Trying to set stepName " + stepName
						+ " but progress window is not visible. Eventually, this behaviour will be removed.");
			}
			setVisible(true);
		}
		if (logger.isLoggable(Level.FINE)) {
			logger.fine("Progress: " + stepName);
		}
		mainProgress++;
		mainProgressBar.setValue(mainProgress);
		mainProgressBarLabel.setText(stepName);
		isSecondaryProgressIndeterminate = true;
		secondaryProgressBar.setIndeterminate(true);
		secondaryProgressBarLabel.setText("");
		paintImmediately();
	}

	@Override
	public void resetSecondaryProgress(final int steps) {
		if (!SwingUtilities.isEventDispatchThread()) {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					resetSecondaryProgress(steps);
				}
			});
			return;
		}
		if (steps > 0) {
			isSecondaryProgressIndeterminate = false;
			secondaryProgressBar.setIndeterminate(false);
			secondaryProgressBar.setMinimum(0);
			secondaryProgressBar.setMaximum(steps);
			secondaryProgress = 1;
			secondaryProgressBar.setValue(secondaryProgress);
		} else {
			isSecondaryProgressIndeterminate = true;
			secondaryProgressBar.setIndeterminate(true);
		}
		paintImmediately();
	}

	@Override
	public void setSecondaryProgress(final String stepName) {
		if (!SwingUtilities.isEventDispatchThread()) {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					setSecondaryProgress(stepName);
				}
			});
			return;
		}
		secondaryProgress++;
		secondaryProgressBar.setValue(secondaryProgress);
		secondaryProgressBarLabel.setText(stepName);
		if (secondaryProgress == secondaryProgressBar.getMaximum()) {
			secondaryProgressBar.setIndeterminate(true);
			secondaryProgressBarLabel.setText("");
		}
		paintImmediately();
	}

	public synchronized static void setProgressInstance(String stepName) {
		if (instance() != null) {
			if (!instance().isVisible()) {
				instance().setVisible(true);
			}
			instance().setProgress(stepName);
		}
	}

	public synchronized static void resetSecondaryProgressInstance(int steps) {
		if (instance() != null) {
			instance().resetSecondaryProgress(steps);
		}
	}

	public synchronized static void setSecondaryProgressInstance(String stepName) {
		if (instance() != null) {
			instance().setSecondaryProgress(stepName);
		}
	}

	/**
	 * @param flexoFrame
	 */
	public void center() {
		Dimension dim;
		if (initOwner != null && initOwner.isVisible()) {
			dim = new Dimension(initOwner.getLocationOnScreen().x + initOwner.getWidth() / 2, initOwner.getLocationOnScreen().y
					+ initOwner.getHeight() / 2);
		} else {
			dim = Toolkit.getDefaultToolkit().getScreenSize();
		}
		setLocation(dim.width - getSize().width / 2, dim.height - getSize().height / 2);
	}
}
