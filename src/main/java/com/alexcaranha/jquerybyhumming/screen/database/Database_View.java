package com.alexcaranha.jquerybyhumming.screen.database;

import com.alexcaranha.jquerybyhumming.App;
import com.alexcaranha.jquerybyhumming.model.Util;
import com.alexcaranha.jquerybyhumming.model.wave.WavFileException;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author alexcaranha
 */
public final class Database_View extends javax.swing.JPanel{

    private Database_Presenter presenter;

    public Database_View(Database_Presenter presenter) throws IOException {
        this.presenter = presenter;

        initComponents();
        initComponentsMannually();
    }

    public void initComponentsMannually() throws IOException {
        this.jButtonSearch.setIcon(Util.getImageIcon(ImageIO.read(App.getContext().getResource("classpath:figures/lupa.png").getInputStream()), 50));
        this.jButtonDetail.setIcon(Util.getImageIcon(ImageIO.read(App.getContext().getResource("classpath:figures/music.png").getInputStream()), 50));
        this.jButtonInsert.setIcon(Util.getImageIcon(ImageIO.read(App.getContext().getResource("classpath:figures/insert.png").getInputStream()), 50));
        //this.jButtonEdit.setIcon(Util.getImageIcon(ImageIO.read(App.getContext().getResource("classpath:figures/edit.png").getInputStream()), 50));
        this.jButtonDelete.setIcon(Util.getImageIcon(ImageIO.read(App.getContext().getResource("classpath:figures/delete.png").getInputStream()), 50));

        this.jPanelButtons.setLayout(new GridLayout(1, 5, 10, 10));
    }

    public void update() {
        this.jLabelTitle.setText("DATABASE" + " - " + presenter.getCurrentOption().getName());

        this.jButtonSearch.setEnabled(presenter.isOptionEnabled(Database_Model.OPTIONS.SEARCH));
        this.jButtonDetail.setEnabled(presenter.isOptionEnabled(Database_Model.OPTIONS.DETAIL));
        //this.jButtonEdit.setEnabled(presenter.isOptionEnabled(Database_Model.OPTIONS.EDIT));
        this.jButtonInsert.setEnabled(presenter.isOptionEnabled(Database_Model.OPTIONS.INSERT));
        this.jButtonDelete.setEnabled(presenter.isOptionEnabled(Database_Model.OPTIONS.DELETE));

        this.jPanelScreens.removeAll();
        this.jPanelScreens.add(presenter.getCurrentOption(), BorderLayout.CENTER);

        this.repaint();
        this.revalidate();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelScreens = new javax.swing.JPanel();
        jPanelButtons = new javax.swing.JPanel();
        jButtonInsert = new javax.swing.JButton();
        jButtonSearch = new javax.swing.JButton();
        jButtonDelete = new javax.swing.JButton();
        jButtonDetail = new javax.swing.JButton();
        jPanelScreens = new javax.swing.JPanel();
        jLabelTitle = new javax.swing.JLabel();

        setMaximumSize(null);

        panelScreens.setBackground(new java.awt.Color(227, 234, 242));
        panelScreens.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanelButtons.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanelButtons.setMinimumSize(null);
        jPanelButtons.setName("jPanelButtons"); // NOI18N

        jButtonInsert.setText("Insert");
        jButtonInsert.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonInsert.setName("jButtonInsert"); // NOI18N
        jButtonInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInsertActionPerformed(evt);
            }
        });

        jButtonSearch.setText("Search");
        jButtonSearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonSearch.setName("jButtonSearch"); // NOI18N
        jButtonSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSearchActionPerformed(evt);
            }
        });

        jButtonDelete.setText("Delete");
        jButtonDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonDelete.setName("jButtonEdit"); // NOI18N
        jButtonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteActionPerformed(evt);
            }
        });

        jButtonDetail.setText("Detail");
        jButtonDetail.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonDetail.setName("jButtonEdit"); // NOI18N
        jButtonDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDetailActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelButtonsLayout = new javax.swing.GroupLayout(jPanelButtons);
        jPanelButtons.setLayout(jPanelButtonsLayout);
        jPanelButtonsLayout.setHorizontalGroup(
            jPanelButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelButtonsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonInsert)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonDelete)
                .addContainerGap(49, Short.MAX_VALUE))
        );
        jPanelButtonsLayout.setVerticalGroup(
            jPanelButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelButtonsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButtonDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelButtonsLayout.createSequentialGroup()
                        .addComponent(jButtonInsert, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jButtonSearch, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonDetail, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanelScreens.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanelScreens.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout panelScreensLayout = new javax.swing.GroupLayout(panelScreens);
        panelScreens.setLayout(panelScreensLayout);
        panelScreensLayout.setHorizontalGroup(
            panelScreensLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelScreensLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelScreensLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelButtons, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelScreens, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelScreensLayout.setVerticalGroup(
            panelScreensLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelScreensLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanelScreens, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabelTitle.setBackground(new java.awt.Color(227, 234, 242));
        jLabelTitle.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        jLabelTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitle.setText("DATABASE");
        jLabelTitle.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabelTitle.setOpaque(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelScreens, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelScreens, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSearchActionPerformed
        try {
            presenter.setCurrentOption_Search();
        } catch (IOException ex) {
            Logger.getLogger(Database_View.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database_View.class.getName()).log(Level.SEVERE, null, ex);
        } catch (WavFileException ex) {
            Logger.getLogger(Database_View.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(Database_View.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonSearchActionPerformed

    private void jButtonDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDetailActionPerformed
        try {
            presenter.setCurrentOption_Detail();
        } catch (IOException ex) {
            Logger.getLogger(Database_View.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database_View.class.getName()).log(Level.SEVERE, null, ex);
        } catch (WavFileException ex) {
            Logger.getLogger(Database_View.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(Database_View.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonDetailActionPerformed

    private void jButtonInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonInsertActionPerformed
        try {
            presenter.setCurrentOption_Insert();
        } catch (IOException ex) {
            Logger.getLogger(Database_View.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database_View.class.getName()).log(Level.SEVERE, null, ex);
        } catch (WavFileException ex) {
            Logger.getLogger(Database_View.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(Database_View.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonInsertActionPerformed

    private void jButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteActionPerformed
        try {
            presenter.setCurrentOption_Delete();
        } catch (IOException ex) {
            Logger.getLogger(Database_View.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database_View.class.getName()).log(Level.SEVERE, null, ex);
        } catch (WavFileException ex) {
            Logger.getLogger(Database_View.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(Database_View.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonDeleteActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonDelete;
    private javax.swing.JButton jButtonDetail;
    private javax.swing.JButton jButtonInsert;
    private javax.swing.JButton jButtonSearch;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JPanel jPanelButtons;
    private javax.swing.JPanel jPanelScreens;
    private javax.swing.JPanel panelScreens;
    // End of variables declaration//GEN-END:variables

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        try {
            if (source == jButtonDetail) {
                presenter.setCurrentOption_Detail();
            } else
            if (source == jButtonInsert) {
                presenter.setCurrentOption_Insert();
            } else
            if (source == jButtonSearch) {
                presenter.setCurrentOption_Search();
            } else /*
            if (source == jButtonEdit) {
                presenter.setCurrentOption_Edit();
            } else */
            if (source == jButtonDelete) {
                presenter.setCurrentOption_Delete();
            }
        } catch (IOException ex) {
            Logger.getLogger(Database_View.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database_View.class.getName()).log(Level.SEVERE, null, ex);
        } catch (WavFileException ex) {
            Logger.getLogger(Database_View.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(Database_View.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}