/*
 * ExistingClassAsSubclass.java
 *
 * Created on 2 ������� 2006, 4:21 ��
 */

package gr.demokritos.iit.eleon.ui;

/**
 *
 * @author  dbilid
 */
public class ExistingClassAsSubclass extends javax.swing.JDialog {
    
    /** Creates new form ExistingClassAsSubclass */
    public ExistingClassAsSubclass(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        pack();
    }
    // </editor-fold>//GEN-END:initComponents
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ExistingClassAsSubclass(new javax.swing.JFrame(), true).setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    
}
