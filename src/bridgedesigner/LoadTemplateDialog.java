/*
 * LoadTemplateDialog.java
 *   
 * Copyright (C) 2009 Eugene K. Ressler
 *   
 * This program is distributed in the hope that it will be useful,  
 * but WITHOUT ANY WARRANTY; without even the implied warranty of  
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  
 * GNU General Public License for more details.  
 *   
 * You should have received a copy of the GNU General Public License  
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.  
 */

package bridgedesigner;

import java.awt.Frame;
import javax.swing.JDialog;

/**
 * Load template dialog for the Bridge Designer.
 * 
 * @author  Eugene K. Ressler
 */
public class LoadTemplateDialog extends JDialog {

    private boolean ok;
    private final BridgeCartoonView bridgeCartoonView = new BridgeCartoonView();
    
    /**
     * Construct a new load template dialog.
     * 
     * @param parent parent frame of the dialog
     */
    public LoadTemplateDialog(Frame parent) {
        super(parent, true);
        initComponents();
        getRootPane().setDefaultButton(okButton);
    }

    /**
     * Set the visibility of the dialog.
     * 
     * @param visible whether the dialog should be made visible or invisible
     */
    @Override
    public void setVisible(boolean visible) {
        if (visible) {
            ok = false;
            templateList.requestFocusInWindow();
        }
        super.setVisible(visible);
    }

    /**
     * Return true iff the Ok button was pressed to close the dialog the last time it was visible.
     * 
     * @return true iff the Ok button was pressed to close the dialog
     */
    public boolean isOk() {
        return ok;
    }

    /**
     * Return the model of the template sketch selected in the dialog.
     * 
     * @return template sketch model
     */
    public BridgeSketchModel getSketchModel() {
       return bridgeCartoonView.getBridgeSketchView().getModel(); 
    }
    
    /**
     * Initialize the dialog with given design conditions and a model to preselect.
     * 
     * @param conditions design conditions
     * @param toSelect template sketch model to preselect or null if none
     */
    public void initialize(DesignConditions conditions, BridgeSketchModel toSelect) {
        templateList.setListData(BridgeSketchModel.getList(conditions));
        if (toSelect == null) {
            templateList.setSelectedIndex(0);
        }
        else {
            templateList.setSelectedValue(toSelect, true);
        }
        bridgeCartoonView.initialize(conditions);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        templateListLabel = new javax.swing.JLabel();
        templateScroll = new javax.swing.JScrollPane();
        templateList = new javax.swing.JList();
        previewLabel = new javax.swing.JLabel();
        cartoonLabel = bridgeCartoonView.getDrawing(1);
        tipTextPanel = new javax.swing.JPanel();
        tipTextPane = new TipTextPane();
        cancelButton = new javax.swing.JButton();
        okButton = new javax.swing.JButton();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(bridgedesigner.BDApp.class).getContext().getResourceMap(LoadTemplateDialog.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        setResizable(false);

        templateListLabel.setText(resourceMap.getString("templateListLabel.text")); // NOI18N
        templateListLabel.setName("templateListLabel"); // NOI18N

        templateScroll.setName("templateScroll"); // NOI18N

        templateList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        templateList.setName("templateList"); // NOI18N
        templateList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                templateListMouseClicked(evt);
            }
        });
        templateList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                templateListValueChanged(evt);
            }
        });
        templateScroll.setViewportView(templateList);

        previewLabel.setText(resourceMap.getString("previewLabel.text")); // NOI18N
        previewLabel.setName("previewLabel"); // NOI18N

        cartoonLabel.setBackground(resourceMap.getColor("cartoonLabel.background")); // NOI18N
        cartoonLabel.setForeground(resourceMap.getColor("cartoonLabel.foreground")); // NOI18N
        cartoonLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cartoonLabel.setText(resourceMap.getString("cartoonLabel.text")); // NOI18N
        cartoonLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        cartoonLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        cartoonLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cartoonLabel.setName("cartoonLabel"); // NOI18N
        cartoonLabel.setOpaque(true);
        cartoonLabel.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        tipTextPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("tipTextPanel.border.title"))); // NOI18N
        tipTextPanel.setName("tipTextPanel"); // NOI18N

        tipTextPane.setBorder(null);
        tipTextPane.setText(resourceMap.getString("tipTextPane.text")); // NOI18N
        tipTextPane.setName("tipTextPane"); // NOI18N

        javax.swing.GroupLayout tipTextPanelLayout = new javax.swing.GroupLayout(tipTextPanel);
        tipTextPanel.setLayout(tipTextPanelLayout);
        tipTextPanelLayout.setHorizontalGroup(
            tipTextPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tipTextPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tipTextPane, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                .addContainerGap())
        );
        tipTextPanelLayout.setVerticalGroup(
            tipTextPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tipTextPanelLayout.createSequentialGroup()
                .addComponent(tipTextPane, javax.swing.GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE)
                .addContainerGap())
        );

        cancelButton.setText(resourceMap.getString("cancelButton.text")); // NOI18N
        cancelButton.setName("cancelButton"); // NOI18N
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        okButton.setText(resourceMap.getString("okButton.text")); // NOI18N
        okButton.setName("okButton"); // NOI18N
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cartoonLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 431, Short.MAX_VALUE)
                    .addComponent(templateListLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(previewLabel)
                    .addComponent(templateScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 431, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tipTextPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(cancelButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(okButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(templateListLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(templateScroll, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(previewLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cartoonLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(tipTextPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(okButton)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
    setVisible(false);
}//GEN-LAST:event_cancelButtonActionPerformed

private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
    ok = true;
    setVisible(false);
}//GEN-LAST:event_okButtonActionPerformed

private void templateListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_templateListValueChanged
    bridgeCartoonView.getBridgeSketchView().setModel(templateList.getSelectedIndex() == 0 ? null : (BridgeSketchModel) templateList.getSelectedValue());
    cartoonLabel.repaint();
}//GEN-LAST:event_templateListValueChanged

private void templateListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_templateListMouseClicked
   if (evt.getClickCount() == 2) {
       ok = true;
       setVisible(false);
   }
}//GEN-LAST:event_templateListMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel cartoonLabel;
    private javax.swing.JButton okButton;
    private javax.swing.JLabel previewLabel;
    private javax.swing.JList templateList;
    private javax.swing.JLabel templateListLabel;
    private javax.swing.JScrollPane templateScroll;
    private javax.swing.JTextPane tipTextPane;
    private javax.swing.JPanel tipTextPanel;
    // End of variables declaration//GEN-END:variables
}
