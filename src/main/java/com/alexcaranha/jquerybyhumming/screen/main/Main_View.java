package com.alexcaranha.jquerybyhumming.screen.main;

import com.alexcaranha.jquerybyhumming.App;
import com.alexcaranha.jquerybyhumming.database.ElasticSearchDB;
import com.alexcaranha.jquerybyhumming.model.Util;
import com.alexcaranha.jquerybyhumming.model.wave.WavFileException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import javax.swing.*;

/**
 *
 * @author alexcaranha
 */
public final class Main_View extends javax.swing.JFrame implements ActionListener, Runnable {
    private enum STATUS { OPERATING, CLOSING };
    private STATUS status;
    
    private static final Logger logger = Logger.getLogger(Main_Presenter.class.getName());
    private final boolean DEBUG = false;
    
    private Thread thread;
    private Main_Presenter presenter;

    public Main_View(Main_Presenter presenter) throws IOException, InvalidMidiDataException, MidiUnavailableException, InterruptedException, ExecutionException {
        this.presenter = presenter;

        initComponents();
        initComponentsManually();
    }

    private void initComponentsManually() throws IOException, InvalidMidiDataException, MidiUnavailableException, InterruptedException, ExecutionException {
        
        this.status = STATUS.OPERATING;
        
        this.jButtonSearchSong.setIcon(Util.getImageIcon(ImageIO.read(App.getContext().getResource("classpath:figures/lupa.png").getInputStream()), Math.min(jButtonSearchSong.getWidth(), jButtonSearchSong.getHeight())));
        this.jButtonDatabase.setIcon(Util.getImageIcon(ImageIO.read(App.getContext().getResource("classpath:figures/database.png").getInputStream()), Math.min(jButtonDatabase.getWidth(), jButtonDatabase.getHeight())));
        this.jButtonConfiguration.setIcon(Util.getImageIcon(ImageIO.read(App.getContext().getResource("classpath:figures/configuration.png").getInputStream()), Math.min(jButtonConfiguration.getWidth(), jButtonConfiguration.getHeight())));
        this.jButtonAbout.setIcon(Util.getImageIcon(ImageIO.read(App.getContext().getResource("classpath:figures/info.png").getInputStream()), Math.min(jButtonAbout.getWidth(), jButtonAbout.getHeight())));
        this.jButtonExit.setIcon(Util.getImageIcon(ImageIO.read(App.getContext().getResource("classpath:figures/exit.png").getInputStream()), Math.min(jButtonExit.getWidth(), jButtonExit.getHeight())));

        this.jButtonSearchSong.addActionListener(this);
        this.jButtonDatabase.addActionListener(this);
        this.jButtonConfiguration.addActionListener(this);
        this.jButtonAbout.addActionListener(this);
        this.jButtonExit.addActionListener(this);

        this.jPanelButtons.setLayout(new GridLayout(5, 1, 10, 10));        
        this.thread = new Thread(this);
        this.thread.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelScreens = new javax.swing.JPanel();
        jPanelButtons = new javax.swing.JPanel();
        jButtonSearchSong = new javax.swing.JButton();
        jButtonDatabase = new javax.swing.JButton();
        jButtonConfiguration = new javax.swing.JButton();
        jButtonAbout = new javax.swing.JButton();
        jButtonExit = new javax.swing.JButton();
        jlDBStatus = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("jQBHS - Java Query By Humming System");
        setMaximumSize(null);
        setMinimumSize(new java.awt.Dimension(800, 550));
        setName("jFMain"); // NOI18N
        setPreferredSize(new java.awt.Dimension(850, 550));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanelScreens.setBackground(new java.awt.Color(227, 234, 242));
        jPanelScreens.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanelScreens.setLayout(new java.awt.BorderLayout());

        jPanelButtons.setBackground(new java.awt.Color(227, 234, 242));
        jPanelButtons.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanelButtons.setMaximumSize(null);

        jButtonSearchSong.setText("Search a Song");
        jButtonSearchSong.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonSearchSong.setMaximumSize(null);
        jButtonSearchSong.setMinimumSize(null);
        jButtonSearchSong.setName("jButtonSearchSong"); // NOI18N
        jButtonSearchSong.setPreferredSize(null);

        jButtonDatabase.setText("Database");
        jButtonDatabase.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonDatabase.setName("jButtonDatabase"); // NOI18N
        jButtonDatabase.setPreferredSize(null);

        jButtonConfiguration.setText("Configuration");
        jButtonConfiguration.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonConfiguration.setName("jButtonConfiguration"); // NOI18N
        jButtonConfiguration.setPreferredSize(null);

        jButtonAbout.setText("About");
        jButtonAbout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonAbout.setName("jButtonAbout"); // NOI18N
        jButtonAbout.setPreferredSize(null);

        jButtonExit.setText("Exit");
        jButtonExit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonExit.setName("jButtonExit"); // NOI18N
        jButtonExit.setPreferredSize(null);

        javax.swing.GroupLayout jPanelButtonsLayout = new javax.swing.GroupLayout(jPanelButtons);
        jPanelButtons.setLayout(jPanelButtonsLayout);
        jPanelButtonsLayout.setHorizontalGroup(
            jPanelButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelButtonsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonSearchSong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonDatabase, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonConfiguration, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonAbout, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonExit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelButtonsLayout.setVerticalGroup(
            jPanelButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelButtonsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonSearchSong, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonDatabase, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonConfiguration, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonAbout, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonExit, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(148, Short.MAX_VALUE))
        );

        jlDBStatus.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jlDBStatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlDBStatus.setText("DATABASE: STATUS");
        jlDBStatus.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jlDBStatus.setMaximumSize(null);
        jlDBStatus.setMinimumSize(null);
        jlDBStatus.setOpaque(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlDBStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 776, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanelButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanelScreens, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelButtons, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelScreens, javax.swing.GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlDBStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getAccessibleContext().setAccessibleName("jQBHS - Java Query By Humming");
        getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        Util.centering(this);
        update();
    }//GEN-LAST:event_formWindowOpened

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        exitApplication();
    }//GEN-LAST:event_formWindowClosing

    public void update() {
        jPanelScreens.removeAll();
        jPanelScreens.add(presenter.getCurrentScreen(), BorderLayout.CENTER);

        this.repaint();
        this.revalidate();
    }

    private void exitApplication() {
        //----------------------------------------------------------------------
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        String message[] = {"Yes", "No"};
        int i = Util.showMessage("Do you really want to exit?",
                                 "Exit",
                                 JOptionPane.YES_NO_OPTION,
                                 JOptionPane.QUESTION_MESSAGE, null, message, message[1]);
        //----------------------------------------------------------------------
        if (i == JOptionPane.YES_OPTION) {
            this.status = STATUS.CLOSING;
            presenter.endApplication();
            App.endApplication();
            dispose();
        }
        //----------------------------------------------------------------------
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAbout;
    private javax.swing.JButton jButtonConfiguration;
    private javax.swing.JButton jButtonDatabase;
    private javax.swing.JButton jButtonExit;
    private javax.swing.JButton jButtonSearchSong;
    private javax.swing.JPanel jPanelButtons;
    private javax.swing.JPanel jPanelScreens;
    private javax.swing.JLabel jlDBStatus;
    // End of variables declaration//GEN-END:variables

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source instanceof JButton) {
            JButton button = (JButton) source;

            if (DEBUG) logger.log(Level.INFO, "Botao acionado: {0}", button.getName());

            if (button.getName().equalsIgnoreCase("jButtonSearchSong")) {
                presenter.setCurrentOption_SearchSong();
            }else
            if (button.getName().equalsIgnoreCase("jButtonDatabase")) {
                try {
                    presenter.setCurrentOption_Database();
                } catch (IOException ex) {
                    Logger.getLogger(Main_View.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Main_View.class.getName()).log(Level.SEVERE, null, ex);
                } catch (WavFileException ex) {
                    Logger.getLogger(Main_View.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedAudioFileException ex) {
                    Logger.getLogger(Main_View.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else
            if (button.getName().equalsIgnoreCase("jButtonConfiguration")) {
                presenter.setCurrentOption_Configuration();
            }else
            if (button.getName().equalsIgnoreCase("jButtonAbout")) {
                presenter.setCurrentOption_About();
            }else
            if (button.getName().equalsIgnoreCase("jButtonExit")) {
                exitApplication();
            }
        }
    }

    public void run() {
        while(this.status == STATUS.OPERATING) {
            if (App.getDB().getStatus() == ElasticSearchDB.STATUS.OFFLINE) {
                this.jlDBStatus.setText("DATABASE: OFFLINE");
                this.jlDBStatus.setForeground(Color.WHITE);
                this.jlDBStatus.setBackground(Color.RED);
            } else {
                this.jlDBStatus.setText("DATABASE: ONLINE");
                this.jlDBStatus.setForeground(Color.WHITE);
                this.jlDBStatus.setBackground(Color.BLUE);
            }

            this.repaint();
            this.revalidate();
            
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Main_View.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}