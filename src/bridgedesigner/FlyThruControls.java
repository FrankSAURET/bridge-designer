/*
 * FlyThruControls.java
 *
 * 

            @Override
            public boolean verify(JComponent input) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        }
            @Override
            public boolean verify(JComponent input) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        }reated on Jun 20, 2011, 10:17:44 PM
 */
package bridgedesigner;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ItemEvent;
import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JDialog;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SwingUtilities;
import org.jdesktop.application.Action;

/**
 *
 * Controls dialog for the fly through animation.
 * @author Eugene K. Ressler
 */
public final class FlyThruControls extends JDialog implements AnimationControls {
   
    // A supprimer Frank SAURET
    private EditableBridgeModel bridge = new EditableBridgeModel();
    
        
        
    private final FlyThruAnimation animation;
    /**
     * State variables describing appearance and dependent visibility.
     */
    private boolean dropped;
    private int panelHeight;
    private boolean visibleState = true;
    private boolean initialized = false;
    private Icon playIcon;
    private Icon pauseIcon;
    private static final String animationDialogStorage = "flyThruAnimationControlsState.xml";

    /** Creates new FlyThruControls non-modal floating dialog with dropdown. */
    public FlyThruControls(Frame parent, FlyThruAnimation animation) {
        super(parent);
        this.animation = animation;
        initComponents();
        playIcon = BDApp.getApplication().getIconResource("play.png");
        pauseIcon = BDApp.getApplication().getIconResource("pause.png");
    }

    /**
     * Return the dialog that implements the controls.
     * 
     * @return dialog implementing the controls
     */
    public Dialog getDialog() {
        return this;
    }

    public boolean getVisibleState() {
        return visibleState;
    }
    
    public void saveVisibilityAndHide() {
        saveState();
        visibleState = isVisible();
        setVisible(false);
    }
 
    public void startAnimation() {
        playButton.setIcon(pauseIcon);
        animation.getConfig().paused = false;
        if (visibleState) {
            if (!initialized) {
                setLocation(animation.getCanvas().getLocationOnScreen());
                panelHeight = animationControlsPanel.getHeight();
                setSize(getWidth(), getHeight()-panelHeight);
                dropped = false;
                visibleState = true;
                restoreState();
                initialized = true;
            }
            // Make visible and start animation later so that JOGL initializes first.
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    restoreState();
                    setVisible(true);
                    animation.start();
                    animation.getCanvas().requestFocusInWindow();
                }
            });
            toggleAnimationDrop();
        } else {
            // Just start the animation.
            animation.start();
            animation.getCanvas().requestFocusInWindow();
        }
    }

    /**
     * Return a list of all the animation controls check boxes.
     *
     * @return checkboxes of the animation controls
     */
    private JCheckBox [] getAnimationControlsCheckBoxes() {
        return new JCheckBox[] {
            shadowsCheckBox,
            skyCheckBox,
            terrainCheckBox,
            abutmentsCheckBox,
            colorsCheckBox,
            erosionCheckbox,
            exaggerationCheckBox,
            truckCheckBox
        };
    }
    /**
     * Return selected type charge
     *
     * @return RadioButton Value
     */
    private JRadioButton [] getChargeType(){
        return new JRadioButton []{
        chargeFreeRadioButton,
        chargeHeavyRadioButton,
        chargeLightRadioButton
        };
    }

    public void restoreState() {
        ComponentStateLocalStorable s = ComponentStateLocalStorable.load(animationDialogStorage);
        if (s != null) {
            s.apply(getAnimationControlsCheckBoxes());
            s.apply(getChargeType());
            s.apply(new JSlider [] { speedSlider, brightnessSlider } );
            s.apply(new JSpinner [] { chargeSpinner } );
        }
        // If hardware can't do shadows, turn off and disable the check.
        if (!animation.getConfig().canShowShadows) {
            shadowsCheckBox.setSelected(false);
            shadowsCheckBox.setEnabled(false);
        }
    }

    // Capture animation controls state for next time the animation panel is shown.
    public void saveState() {
        ComponentStateLocalStorable s = new ComponentStateLocalStorable();
        s.add(getAnimationControlsCheckBoxes());
        s.add(getChargeType());
        s.add(new JSlider [] { speedSlider, brightnessSlider } );
        s.add(new JSpinner [] { chargeSpinner} );
        s.save(animationDialogStorage);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        chargebuttonGroup = new javax.swing.ButtonGroup();
        animationControlsToolbar = new javax.swing.JToolBar();
        resetButton = new javax.swing.JButton();
        playButton = new javax.swing.JToggleButton();
        sep100 = new javax.swing.JToolBar.Separator();
        speedSlider = new javax.swing.JSlider();
        animationControlsSep02 = new javax.swing.JToolBar.Separator();
        animationControlsDialogDropButton = new javax.swing.JButton();
        animationControlsPanel = new javax.swing.JPanel();
        shadowsCheckBox = new javax.swing.JCheckBox();
        terrainCheckBox = new javax.swing.JCheckBox();
        skyCheckBox = new javax.swing.JCheckBox();
        speedLabel = new javax.swing.JLabel();
        colorsCheckBox = new javax.swing.JCheckBox();
        exaggerationCheckBox = new javax.swing.JCheckBox();
        abutmentsCheckBox = new javax.swing.JCheckBox();
        truckCheckBox = new javax.swing.JCheckBox();
        erosionCheckbox = new javax.swing.JCheckBox();
        brightnessSlider = new JSlider(JSlider.VERTICAL);
        lightLabel = new javax.swing.JLabel();
        dimLabel = new javax.swing.JLabel();
        brightLabel = new javax.swing.JLabel();
        chargePanel = new javax.swing.JPanel();
        chargeSpinner = new javax.swing.JSpinner();
        chargeFreeRadioButton = new javax.swing.JRadioButton();
        chargeHeavyRadioButton = new javax.swing.JRadioButton();
        chargeLightRadioButton = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(bridgedesigner.BDApp.class).getContext().getResourceMap(FlyThruControls.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setIconImage(bridgedesigner.BDApp.getApplication().getImageResource("animate.png"));
        setName("Form"); // NOI18N
        setResizable(false);

        animationControlsToolbar.setFloatable(false);
        animationControlsToolbar.setRollover(true);
        animationControlsToolbar.setName("animationControlsToolbar"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(bridgedesigner.BDApp.class).getContext().getActionMap(FlyThruControls.class, this);
        resetButton.setAction(actionMap.get("reset")); // NOI18N
        resetButton.setIcon(resourceMap.getIcon("resetButton.icon")); // NOI18N
        resetButton.setFocusable(false);
        resetButton.setHideActionText(true);
        resetButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        resetButton.setName("resetButton"); // NOI18N
        resetButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        animationControlsToolbar.add(resetButton);

        playButton.setAction(actionMap.get("playAnimation")); // NOI18N
        playButton.setToolTipText(resourceMap.getString("playButton.toolTipText")); // NOI18N
        playButton.setFocusable(false);
        playButton.setHideActionText(true);
        playButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        playButton.setName("playButton"); // NOI18N
        playButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        animationControlsToolbar.add(playButton);

        sep100.setMaximumSize(new java.awt.Dimension(8, 32767));
        sep100.setMinimumSize(new java.awt.Dimension(8, 0));
        sep100.setName("sep100"); // NOI18N
        sep100.setPreferredSize(new java.awt.Dimension(8, 0));
        animationControlsToolbar.add(sep100);

        speedSlider.setMajorTickSpacing(5);
        speedSlider.setMaximum(30);
        speedSlider.setMinimum(5);
        speedSlider.setMinorTickSpacing(5);
        speedSlider.setPaintTicks(true);
        speedSlider.setSnapToTicks(true);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("bridgedesigner/resources/BDView"); // NOI18N
        speedSlider.setToolTipText(bundle.getString("animationSpeedTip")); // NOI18N
        speedSlider.setMaximumSize(new java.awt.Dimension(220, 34));
        speedSlider.setMinimumSize(new java.awt.Dimension(120, 34));
        speedSlider.setName("speedSlider"); // NOI18N
        speedSlider.setPreferredSize(new java.awt.Dimension(120, 34));
        speedSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                speedSliderStateChanged(evt);
            }
        });
        animationControlsToolbar.add(speedSlider);

        animationControlsSep02.setMaximumSize(new java.awt.Dimension(8, 32767));
        animationControlsSep02.setMinimumSize(new java.awt.Dimension(8, 0));
        animationControlsSep02.setName("animationControlsSep02"); // NOI18N
        animationControlsSep02.setPreferredSize(new java.awt.Dimension(8, 0));
        animationControlsToolbar.add(animationControlsSep02);

        animationControlsDialogDropButton.setAction(actionMap.get("toggleAnimationDrop")); // NOI18N
        animationControlsDialogDropButton.setHideActionText(true);
        animationControlsDialogDropButton.setMaximumSize(new java.awt.Dimension(37, 37));
        animationControlsDialogDropButton.setMinimumSize(new java.awt.Dimension(37, 37));
        animationControlsDialogDropButton.setName("animationControlsDialogDropButton"); // NOI18N
        animationControlsDialogDropButton.setPreferredSize(new java.awt.Dimension(37, 37));
        animationControlsToolbar.add(animationControlsDialogDropButton);

        animationControlsPanel.setName("animationControlsPanel"); // NOI18N

        shadowsCheckBox.setSelected(true);
        shadowsCheckBox.setText(resourceMap.getString("shadowsCheckBox.text")); // NOI18N
        shadowsCheckBox.setName("shadowsCheckBox"); // NOI18N
        shadowsCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                shadowsCheckBoxItemStateChanged(evt);
            }
        });

        terrainCheckBox.setSelected(true);
        terrainCheckBox.setText(resourceMap.getString("terrainCheckBox.text")); // NOI18N
        terrainCheckBox.setName("terrainCheckBox"); // NOI18N
        terrainCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                terrainCheckBoxItemStateChanged(evt);
            }
        });

        skyCheckBox.setSelected(true);
        skyCheckBox.setText(resourceMap.getString("skyCheckBox.text")); // NOI18N
        skyCheckBox.setName("skyCheckBox"); // NOI18N
        skyCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                skyCheckBoxItemStateChanged(evt);
            }
        });

        speedLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        speedLabel.setText(resourceMap.getString("speedLabel.text")); // NOI18N
        speedLabel.setMaximumSize(new java.awt.Dimension(64, 16));
        speedLabel.setMinimumSize(new java.awt.Dimension(64, 16));
        speedLabel.setName("speedLabel"); // NOI18N
        speedLabel.setPreferredSize(new java.awt.Dimension(64, 16));

        colorsCheckBox.setSelected(true);
        colorsCheckBox.setText(resourceMap.getString("colorsCheckBox.text")); // NOI18N
        colorsCheckBox.setName("colorsCheckBox"); // NOI18N
        colorsCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                colorsCheckBoxItemStateChanged(evt);
            }
        });

        exaggerationCheckBox.setSelected(true);
        exaggerationCheckBox.setText(resourceMap.getString("exaggerationCheckBox.text")); // NOI18N
        exaggerationCheckBox.setName("exaggerationCheckBox"); // NOI18N
        exaggerationCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                exaggerationCheckBoxItemStateChanged(evt);
            }
        });

        abutmentsCheckBox.setSelected(true);
        abutmentsCheckBox.setText(resourceMap.getString("abutmentsCheckBox.text")); // NOI18N
        abutmentsCheckBox.setName("abutmentsCheckBox"); // NOI18N
        abutmentsCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                abutmentsCheckBoxItemStateChanged(evt);
            }
        });

        truckCheckBox.setSelected(true);
        truckCheckBox.setText(resourceMap.getString("truckCheckBox.text")); // NOI18N
        truckCheckBox.setName("truckCheckBox"); // NOI18N
        truckCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                truckCheckBoxItemStateChanged(evt);
            }
        });

        erosionCheckbox.setText(resourceMap.getString("erosionCheckbox.text")); // NOI18N
        erosionCheckbox.setName("erosionCheckbox"); // NOI18N
        erosionCheckbox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                erosionCheckboxItemStateChanged(evt);
            }
        });

        brightnessSlider.setOrientation(javax.swing.JSlider.VERTICAL);
        brightnessSlider.setValue(100);
        brightnessSlider.setName("brightnessSlider"); // NOI18N
        brightnessSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                brightnessSliderStateChanged(evt);
            }
        });

        lightLabel.setText(resourceMap.getString("lightLabel.text")); // NOI18N
        lightLabel.setName("lightLabel"); // NOI18N

        dimLabel.setIcon(resourceMap.getIcon("dimLabel.icon")); // NOI18N
        dimLabel.setName("dimLabel"); // NOI18N

        brightLabel.setIcon(resourceMap.getIcon("brightLabel.icon")); // NOI18N
        brightLabel.setName("brightLabel"); // NOI18N

        javax.swing.GroupLayout animationControlsPanelLayout = new javax.swing.GroupLayout(animationControlsPanel);
        animationControlsPanel.setLayout(animationControlsPanelLayout);
        animationControlsPanelLayout.setHorizontalGroup(
            animationControlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(animationControlsPanelLayout.createSequentialGroup()
                .addGroup(animationControlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(animationControlsPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(animationControlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(truckCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(shadowsCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(skyCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(terrainCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(erosionCheckbox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(abutmentsCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(animationControlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(colorsCheckBox)
                            .addComponent(exaggerationCheckBox)
                            .addGroup(animationControlsPanelLayout.createSequentialGroup()
                                .addComponent(brightnessSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(animationControlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(dimLabel)
                                    .addComponent(lightLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(brightLabel)))))
                    .addGroup(animationControlsPanelLayout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addComponent(speedLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(72, Short.MAX_VALUE))
        );
        animationControlsPanelLayout.setVerticalGroup(
            animationControlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, animationControlsPanelLayout.createSequentialGroup()
                .addComponent(speedLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addGroup(animationControlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(animationControlsPanelLayout.createSequentialGroup()
                        .addComponent(shadowsCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(animationControlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(skyCheckBox)
                            .addComponent(exaggerationCheckBox)))
                    .addComponent(colorsCheckBox))
                .addGroup(animationControlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(animationControlsPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(terrainCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(erosionCheckbox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(abutmentsCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(truckCheckBox))
                    .addGroup(animationControlsPanelLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(brightLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lightLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dimLabel))
                    .addComponent(brightnessSlider, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
                .addContainerGap())
        );

        chargePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("chargePanel.border.title"))); // NOI18N
        chargePanel.setName("chargePanel"); // NOI18N

        chargeSpinner.setModel(new javax.swing.SpinnerNumberModel(480,0,99999,10));
        chargeSpinner.setToolTipText(resourceMap.getString("chargeSpinner.toolTipText")); // NOI18N
        chargeSpinner.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        chargeSpinner.setFocusTraversalPolicyProvider(true);
        chargeSpinner.setName("chargeSpinner"); // NOI18N
        chargeSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chargeSpinnerStateChanged(evt);
            }
        });

        chargebuttonGroup.add(chargeFreeRadioButton);
        chargeFreeRadioButton.setText(resourceMap.getString("chargeFreeRadioButton.text")); // NOI18N
        chargeFreeRadioButton.setActionCommand(resourceMap.getString("chargeFreeRadioButton.actionCommand")); // NOI18N
        chargeFreeRadioButton.setName("chargeFreeRadioButton"); // NOI18N
        chargeFreeRadioButton.setRolloverEnabled(false);
        chargeFreeRadioButton.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chargeFreeRadioButtonItemStateChanged(evt);
            }
        });

        chargebuttonGroup.add(chargeHeavyRadioButton);
        chargeHeavyRadioButton.setText(resourceMap.getString("chargeHeavyRadioButton.text")); // NOI18N
        chargeHeavyRadioButton.setAlignmentX(0.5F);
        chargeHeavyRadioButton.setName("chargeHeavyRadioButton"); // NOI18N
        chargeHeavyRadioButton.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chargeHeavyRadioButtonItemStateChanged(evt);
            }
        });

        chargebuttonGroup.add(chargeLightRadioButton);
        chargeLightRadioButton.setText(resourceMap.getString("chargeLightRadioButton.text")); // NOI18N
        chargeLightRadioButton.setAlignmentX(0.5F);
        chargeLightRadioButton.setName("chargeLightRadioButton"); // NOI18N
        chargeLightRadioButton.setOpaque(false);
        chargeLightRadioButton.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chargeLightRadioButtonItemStateChanged(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel1.setName("jLabel1"); // NOI18N

        javax.swing.GroupLayout chargePanelLayout = new javax.swing.GroupLayout(chargePanel);
        chargePanel.setLayout(chargePanelLayout);
        chargePanelLayout.setHorizontalGroup(
            chargePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(chargePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(chargePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chargeFreeRadioButton, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                    .addComponent(chargeHeavyRadioButton, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                    .addComponent(chargeLightRadioButton)
                    .addGroup(chargePanelLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(chargeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE))
                .addContainerGap())
        );
        chargePanelLayout.setVerticalGroup(
            chargePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(chargePanelLayout.createSequentialGroup()
                .addComponent(chargeLightRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chargeHeavyRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(chargeFreeRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chargeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );

        chargeSpinner.getAccessibleContext().setAccessibleName(resourceMap.getString("chargeSpinner.AccessibleContext.accessibleName")); // NOI18N
        chargeLightRadioButton.getAccessibleContext().setAccessibleName(resourceMap.getString("chargeLightRadioButton.AccessibleContext.accessibleName")); // NOI18N
        jLabel1.getAccessibleContext().setAccessibleName(resourceMap.getString("jLabel1.AccessibleContext.accessibleName")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(animationControlsToolbar, javax.swing.GroupLayout.DEFAULT_SIZE, 575, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(animationControlsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chargePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(animationControlsToolbar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(animationControlsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(chargePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        chargePanel.getAccessibleContext().setAccessibleName(resourceMap.getString("chargePanel.AccessibleContext.accessibleName")); // NOI18N

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void speedSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_speedSliderStateChanged
        int speed = speedSlider.getValue();
        speedLabel.setText(Integer.toString(speed) + " km/h");
        animation.getConfig().truckSpeed = speed;
}//GEN-LAST:event_speedSliderStateChanged

    private void shadowsCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_shadowsCheckBoxItemStateChanged
        animation.getConfig().showShadows = (evt.getStateChange() == ItemEvent.SELECTED);
}//GEN-LAST:event_shadowsCheckBoxItemStateChanged

    private void terrainCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_terrainCheckBoxItemStateChanged
        animation.getConfig().showTerrain = (evt.getStateChange() == ItemEvent.SELECTED);
}//GEN-LAST:event_terrainCheckBoxItemStateChanged

    private void skyCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_skyCheckBoxItemStateChanged
        animation.getConfig().showSky = (evt.getStateChange() == ItemEvent.SELECTED);
}//GEN-LAST:event_skyCheckBoxItemStateChanged

    private void colorsCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_colorsCheckBoxItemStateChanged
        animation.getConfig().showForcesAsColors = (evt.getStateChange() == ItemEvent.SELECTED);
}//GEN-LAST:event_colorsCheckBoxItemStateChanged

    private void exaggerationCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_exaggerationCheckBoxItemStateChanged
        animation.getConfig().displacementExaggeration = (evt.getStateChange() == ItemEvent.SELECTED) ? Animation.standardExaggeration : 1;
}//GEN-LAST:event_exaggerationCheckBoxItemStateChanged

    private void abutmentsCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_abutmentsCheckBoxItemStateChanged
        animation.getConfig().showAbutments = (evt.getStateChange() == ItemEvent.SELECTED);
}//GEN-LAST:event_abutmentsCheckBoxItemStateChanged

    private void truckCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_truckCheckBoxItemStateChanged
        animation.getConfig().showTruck = (evt.getStateChange() == ItemEvent.SELECTED);
}//GEN-LAST:event_truckCheckBoxItemStateChanged

    private void erosionCheckboxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_erosionCheckboxItemStateChanged
        animation.getConfig().showErrosion = (evt.getStateChange() == ItemEvent.SELECTED);
}//GEN-LAST:event_erosionCheckboxItemStateChanged

    private void brightnessSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_brightnessSliderStateChanged
        int brightness = brightnessSlider.getValue();
        final float[] lightBrightness = animation.getConfig().lightBrightness;
        lightBrightness[0] = lightBrightness[1] = lightBrightness[2] = 0.2f + (brightness * .01f) * 0.6f;
}//GEN-LAST:event_brightnessSliderStateChanged

    private void chargeLightRadioButtonItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chargeLightRadioButtonItemStateChanged
       saveState();
       animation.resetState();
    }//GEN-LAST:event_chargeLightRadioButtonItemStateChanged

    private void chargeHeavyRadioButtonItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chargeHeavyRadioButtonItemStateChanged
        saveState();
        animation.resetState();
    }//GEN-LAST:event_chargeHeavyRadioButtonItemStateChanged

    private void chargeFreeRadioButtonItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chargeFreeRadioButtonItemStateChanged
        saveState();
        animation.resetState();
    }//GEN-LAST:event_chargeFreeRadioButtonItemStateChanged

    private void chargeSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chargeSpinnerStateChanged
        saveState();
        animation.resetState();
    }//GEN-LAST:event_chargeSpinnerStateChanged

    @Action
    public void toggleAnimationDrop() {
        Dimension size = getSize();
        if (dropped) {
            size.height -= panelHeight;
            animationControlsPanel.setVisible(false);
            setSize(size);
            animationControlsDialogDropButton.setIcon(BDApp.getApplication().getIconResource("drop.png"));
            dropped = false;
        }
        else {
            size.height += panelHeight;
            setSize(size);
            animationControlsPanel.setVisible(true);
            animationControlsDialogDropButton.setIcon(BDApp.getApplication().getIconResource("undrop.png"));
            dropped = true;
        }
    }

    @Action
    public void playAnimation() {
        if (playButton.getIcon() == playIcon) {
            playButton.setIcon(pauseIcon);
            animation.getConfig().paused = false;
        }
        else {
            playButton.setIcon(playIcon);
            animation.getConfig().paused = true;
        }
    }
// Appuie sur reset animation
    @Action
    public void reset() {

        animation.reset();


        
 
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox abutmentsCheckBox;
    private javax.swing.JButton animationControlsDialogDropButton;
    private javax.swing.JPanel animationControlsPanel;
    private javax.swing.JToolBar.Separator animationControlsSep02;
    private javax.swing.JToolBar animationControlsToolbar;
    private javax.swing.JLabel brightLabel;
    private javax.swing.JSlider brightnessSlider;
    private javax.swing.JRadioButton chargeFreeRadioButton;
    private javax.swing.JRadioButton chargeHeavyRadioButton;
    private javax.swing.JRadioButton chargeLightRadioButton;
    private javax.swing.JPanel chargePanel;
    private javax.swing.JSpinner chargeSpinner;
    private javax.swing.ButtonGroup chargebuttonGroup;
    private javax.swing.JCheckBox colorsCheckBox;
    private javax.swing.JLabel dimLabel;
    private javax.swing.JCheckBox erosionCheckbox;
    private javax.swing.JCheckBox exaggerationCheckBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lightLabel;
    private javax.swing.JToggleButton playButton;
    private javax.swing.JButton resetButton;
    private javax.swing.JToolBar.Separator sep100;
    private javax.swing.JCheckBox shadowsCheckBox;
    private javax.swing.JCheckBox skyCheckBox;
    private javax.swing.JLabel speedLabel;
    private javax.swing.JSlider speedSlider;
    private javax.swing.JCheckBox terrainCheckBox;
    private javax.swing.JCheckBox truckCheckBox;
    // End of variables declaration//GEN-END:variables
}
