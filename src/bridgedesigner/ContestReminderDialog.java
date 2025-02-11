/*
 * ContestReminderDialog.java  
 * 
 * Self-closing dialog that serves as a teaser ad for the bridge contest.
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;

public class ContestReminderDialog extends JDialog {

    private final ResourceMap resourceMap = BDApp.getApplication().getContext().getResourceMap(ContestReminderDialog.class);
    private final Timer countdownToCloseTimer;
    private static final int startingCountdownSeconds = 15;
    private int countdown = startingCountdownSeconds;
    
    /**
     * Creates new form ContestReminderDialog
     */
    public ContestReminderDialog(Frame parent) {
        super(parent, true);
        initComponents();
        countdownToCloseTimer = new Timer(1000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                countdownToCloseTick();
            }
        });
        
        // When the close label becomes visible, start the countdown timer.
        closingLabel.addAncestorListener(new AncestorListener() {

            @Override
            public void ancestorAdded(AncestorEvent event) {
                
                // These undo the hiding done if the user aborts the countdown.
                // Really unecessary because we never show the dialog again!
                closingLabel.setVisible(true);
                dontCloseButton.setVisible(true);
                
                // Start counting down.
                updateCountdown(startingCountdownSeconds);
                countdownToCloseTimer.start();
            }

            @Override
            public void ancestorRemoved(AncestorEvent event) {
            }

            @Override
            public void ancestorMoved(AncestorEvent event) {
            }
        });
    }

    /**
     * Set the value of the countdown and update the countdown label to match.
     * 
     * @param val new countdown value
     */
    private void updateCountdown(int val) {
        countdown = val;
        closingLabel.setText(resourceMap.getString("closingLabelFormat.text", countdown));
    }
    
    /**
     * Abort the countdown in progress and hide it.
     */
    private void stopCountdown() {
        countdownToCloseTimer.stop();
        closingLabel.setVisible(false);
        dontCloseButton.setVisible(false);
        countdown = startingCountdownSeconds;
    }
    
    /**
     * Decrement the countdown by one and return the new value.
     * 
     * @return decremented countdown value
     */
    private int decrementCountdown() {
        updateCountdown(countdown - 1);
        return countdown;
    }

    /**
     * Handle the 1-second ticks of the countdown timer.
     */
    private void countdownToCloseTick() {
        if (decrementCountdown() <= 0) {
            countdownToCloseTimer.stop();
            setVisible(false);
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        headerPanel = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();
        logoLabel = new javax.swing.JLabel();
        messageTextPanel = new javax.swing.JPanel();
        messageText = new TipTextPane();
        bdcButton = new javax.swing.JButton();
        closeButton = new javax.swing.JButton();
        dontCloseButton = new javax.swing.JButton();
        closingLabel = new javax.swing.JLabel();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(bridgedesigner.BDApp.class).getContext().getResourceMap(ContestReminderDialog.class);
        setTitle(resourceMap.getString("ContestReminderDialogForm.title")); // NOI18N
        setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        setName("ContestReminderDialogForm"); // NOI18N
        setResizable(false);

        headerPanel.setBackground(resourceMap.getColor("headerPanel.background")); // NOI18N
        headerPanel.setName("headerPanel"); // NOI18N

        titleLabel.setFont(resourceMap.getFont("titleLabel.font")); // NOI18N
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        titleLabel.setText(resourceMap.getString("titleLabel.text")); // NOI18N
        titleLabel.setName("titleLabel"); // NOI18N

        logoLabel.setIcon(resourceMap.getIcon("logoLabel.icon")); // NOI18N
        logoLabel.setText(resourceMap.getString("logoLabel.text")); // NOI18N
        logoLabel.setName("logoLabel"); // NOI18N

        javax.swing.GroupLayout headerPanelLayout = new javax.swing.GroupLayout(headerPanel);
        headerPanel.setLayout(headerPanelLayout);
        headerPanelLayout.setHorizontalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(logoLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
                .addContainerGap())
        );
        headerPanelLayout.setVerticalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(logoLabel)
                    .addComponent(titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE))
                .addContainerGap())
        );

        messageTextPanel.setName("messageTextPanel"); // NOI18N

        messageText.setText(resourceMap.getString("messageText.text")); // NOI18N
        messageText.setName("messageText"); // NOI18N
        messageText.setOpaque(false);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(bridgedesigner.BDApp.class).getContext().getActionMap(ContestReminderDialog.class, this);
        bdcButton.setAction(actionMap.get("showContestWebSite")); // NOI18N
        bdcButton.setFont(resourceMap.getFont("bdcButton.font")); // NOI18N
        bdcButton.setText(resourceMap.getString("bdcButton.text")); // NOI18N
        bdcButton.setName("bdcButton"); // NOI18N

        closeButton.setAction(actionMap.get("closeContestReminder")); // NOI18N
        closeButton.setText(resourceMap.getString("closeButton.text")); // NOI18N
        closeButton.setName("closeButton"); // NOI18N

        dontCloseButton.setAction(actionMap.get("stopCountdownToClose")); // NOI18N
        dontCloseButton.setText(resourceMap.getString("dontCloseButton.text")); // NOI18N
        dontCloseButton.setName("dontCloseButton"); // NOI18N

        closingLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        closingLabel.setText("");
        closingLabel.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        closingLabel.setName("closingLabel"); // NOI18N

        javax.swing.GroupLayout messageTextPanelLayout = new javax.swing.GroupLayout(messageTextPanel);
        messageTextPanel.setLayout(messageTextPanelLayout);
        messageTextPanelLayout.setHorizontalGroup(
            messageTextPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(messageTextPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(messageTextPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(messageTextPanelLayout.createSequentialGroup()
                        .addComponent(messageText, javax.swing.GroupLayout.PREFERRED_SIZE, 610, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(bdcButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 610, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, messageTextPanelLayout.createSequentialGroup()
                        .addGap(0, 6, Short.MAX_VALUE)
                        .addComponent(closingLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dontCloseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        messageTextPanelLayout.setVerticalGroup(
            messageTextPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(messageTextPanelLayout.createSequentialGroup()
                .addComponent(messageText, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bdcButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(messageTextPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(closeButton)
                    .addComponent(dontCloseButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(closingLabel)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(headerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(messageTextPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(headerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(messageTextPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    @Action
    public void closeContestReminder() {
        setVisible(false);
    }

    @Action
    public void stopCountdownToClose() {
        stopCountdown();
    }

    @Action
    public void showContestWebSite() {
        stopCountdown();
        try {
            Browser.openUrl("http://bridgecontest.org");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                resourceMap.getString("browserOpenFail.text"),
                resourceMap.getString("messageBox.title"),
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bdcButton;
    private javax.swing.JButton closeButton;
    private javax.swing.JLabel closingLabel;
    private javax.swing.JButton dontCloseButton;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JLabel logoLabel;
    private javax.swing.JTextPane messageText;
    private javax.swing.JPanel messageTextPanel;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables
}