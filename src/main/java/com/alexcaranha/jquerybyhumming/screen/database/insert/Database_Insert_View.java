package com.alexcaranha.jquerybyhumming.screen.database.insert;

import com.alexcaranha.jquerybyhumming.screen.database.detail.*;
import com.alexcaranha.jquerybyhumming.App;
import com.alexcaranha.jquerybyhumming.components.XYGraphSignal;
import com.alexcaranha.jquerybyhumming.model.Convert;
import com.alexcaranha.jquerybyhumming.model.KeyValue;
import com.alexcaranha.jquerybyhumming.model.Util;
import com.alexcaranha.jquerybyhumming.model.wave.WavFileException;
import com.alexcaranha.jquerybyhumming.view.FilterFileChooser;
import java.awt.BorderLayout;
import java.io.IOException;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.index.IndexResponse;

/**
 *
 * @author alexcaranha
 */
public class Database_Insert_View extends javax.swing.JPanel implements Observer, ActionListener<IndexResponse> {

    private Database_Insert_Presenter presenter;

    /**
     * Creates new form Database_Insert_View
     */
    public Database_Insert_View(Database_Insert_Presenter presenter) throws IOException {
        this.setName("INSERT");
        this.presenter = presenter;

        initComponents();
        initComponentsMannually();
    }

    private void initComponentsMannually() throws IOException {
        this.jButtonBack.setIcon(Util.getImageIcon(ImageIO.read(App.getContext().getResource("classpath:figures/back.png").getInputStream()), 25));
        this.jButtonSave.setIcon(Util.getImageIcon(ImageIO.read(App.getContext().getResource("classpath:figures/save.png").getInputStream()), 25));

        this.jButtonPlayStop.setText("Play");
        this.jButtonPlayStop.setIcon(Util.getImageIcon(ImageIO.read(App.getContext().getResource("classpath:figures/play.png").getInputStream()), 25));
        this.jButtonPlayStop.setEnabled(false);

        this.fileChooser = new JFileChooser();
        this.fileChooser.addChoosableFileFilter(new FilterFileChooser("MID", "MIDI File"));

        this.jPanelXYGraph = null;
    }

    public void update(Map<String, Object> fields) {
        this.jTextFieldTitle.setText(Convert.toString(fields.get("title")));
        this.jTextFieldAuthor.setText(Convert.toString(fields.get("title")));
        this.jTextAreaObs.setText(Convert.toString(fields.get("obs")));
        this.setEnabledPlayStopButton(Convert.toBoolean(fields.get("midiFileSimplified")));

        this.jPanelFigure.removeAll();
        this.jPanelXYGraph = new XYGraphSignal("", "time (in seconds)", "MIDI notes", Database_Insert_View.class.getSimpleName(), false);
        this.jPanelFigure.add(jPanelXYGraph, BorderLayout.CENTER);
    }

    public String getTitle() {
        return this.jTextFieldTitle.getText();
    }

    public String getAuthor() {
        return this.jTextFieldAuthor.getText();
    }

    public String getObs() {
        return this.jTextAreaObs.getText();
    }

    public XYGraphSignal getJPanelGraphSignal() {
        return jPanelXYGraph;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelPathMidiFile = new javax.swing.JLabel();
        jLabelPathMidiFileValue = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaObs = new javax.swing.JTextArea();
        jLabelTitle = new javax.swing.JLabel();
        jTextFieldTitle = new javax.swing.JTextField();
        jLabelAuthor = new javax.swing.JLabel();
        jLabelObs = new javax.swing.JLabel();
        jTextFieldAuthor = new javax.swing.JTextField();
        jButtonBack = new javax.swing.JButton();
        jButtonSave = new javax.swing.JButton();
        jPanelFigure = new javax.swing.JPanel();
        jButtonPlayStop = new javax.swing.JButton();

        setMinimumSize(new java.awt.Dimension(375, 250));

        jLabelPathMidiFile.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelPathMidiFile.setText("Path MIDI File:");

        jLabelPathMidiFileValue.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelPathMidiFileValue.setText("Click here to open a MIDI file.");
        jLabelPathMidiFileValue.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabelPathMidiFileValue.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabelPathMidiFileValue.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelPathMidiFileValueMouseClicked(evt);
            }
        });

        jTextAreaObs.setColumns(20);
        jTextAreaObs.setRows(5);
        jTextAreaObs.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPane1.setViewportView(jTextAreaObs);

        jLabelTitle.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelTitle.setText("Title:");

        jTextFieldTitle.setBackground(java.awt.Color.white);
        jTextFieldTitle.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabelAuthor.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelAuthor.setText("Author:");

        jLabelObs.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelObs.setText("Obs:");

        jTextFieldAuthor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jButtonBack.setText("Back");
        jButtonBack.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBackActionPerformed(evt);
            }
        });

        jButtonSave.setText("Save");
        jButtonSave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveActionPerformed(evt);
            }
        });

        jPanelFigure.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanelFigure.setLayout(new java.awt.BorderLayout());

        jButtonPlayStop.setText("Play/Stop");
        jButtonPlayStop.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonPlayStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPlayStopActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButtonBack, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonPlayStop, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonSave, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabelPathMidiFile, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabelPathMidiFileValue, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabelObs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabelAuthor, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabelTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextFieldTitle, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldAuthor)
                            .addComponent(jScrollPane1)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanelFigure, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelPathMidiFileValue, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelPathMidiFile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelFigure, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelAuthor, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldAuthor, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelObs, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonBack, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonSave, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonPlayStop, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jLabelPathMidiFileValueMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelPathMidiFileValueMouseClicked
        int retorno = fileChooser.showOpenDialog(this);

        if (retorno == JFileChooser.APPROVE_OPTION) {
            String pathFile = fileChooser.getSelectedFile().getAbsolutePath();

            try {
                presenter.associateMidiFile(pathFile);
            } catch (IOException ex) {
                Logger.getLogger(Database_Detail_View.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvalidMidiDataException ex) {
                Logger.getLogger(Database_Detail_View.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MidiUnavailableException ex) {
                Logger.getLogger(Database_Detail_View.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Database_Detail_View.class.getName()).log(Level.SEVERE, null, ex);
            } catch (WavFileException ex) {
                Logger.getLogger(Database_Insert_View.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedAudioFileException ex) {
                Logger.getLogger(Database_Insert_View.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jLabelPathMidiFileValueMouseClicked

    private void jButtonBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBackActionPerformed
        try {
            this.presenter.back();
        } catch (IOException ex) {
            Logger.getLogger(Database_Insert_View.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database_Insert_View.class.getName()).log(Level.SEVERE, null, ex);
        } catch (WavFileException ex) {
            Logger.getLogger(Database_Insert_View.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(Database_Insert_View.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonBackActionPerformed

    private void jButtonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveActionPerformed
        try {
            this.presenter.save();
        } catch (IOException ex) {
            Logger.getLogger(Database_Detail_View.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database_Detail_View.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonSaveActionPerformed

    private void jButtonPlayStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPlayStopActionPerformed
        try {
            if (this.jButtonPlayStop.getText().equalsIgnoreCase("Play")) {
                this.presenter.play();
            } else {
                this.presenter.stop();
            }
        } catch (IOException ex) {
            Logger.getLogger(Database_Insert_View.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonPlayStopActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBack;
    private javax.swing.JButton jButtonPlayStop;
    private javax.swing.JButton jButtonSave;
    private javax.swing.JLabel jLabelAuthor;
    private javax.swing.JLabel jLabelObs;
    private javax.swing.JLabel jLabelPathMidiFile;
    private javax.swing.JLabel jLabelPathMidiFileValue;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JPanel jPanelFigure;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextAreaObs;
    private javax.swing.JTextField jTextFieldAuthor;
    private javax.swing.JTextField jTextFieldTitle;
    // End of variables declaration//GEN-END:variables
    private XYGraphSignal jPanelXYGraph;
    private JFileChooser fileChooser;

    public void onResponse(IndexResponse response) {
        String message[] = {"Ok"};
        Util.showMessage("Requisition succeded!",
                         "Requisition",
                         JOptionPane.YES_OPTION,
                         JOptionPane.QUESTION_MESSAGE, null, message, message[0]);
        try {
            presenter.back();
        } catch (IOException ex) {
            Logger.getLogger(Database_Insert_View.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database_Insert_View.class.getName()).log(Level.SEVERE, null, ex);
        } catch (WavFileException ex) {
            Logger.getLogger(Database_Insert_View.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(Database_Insert_View.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void onFailure(Throwable e) {
        String message[] = {"Ok"};
        Util.showMessage(e.getCause().getLocalizedMessage(),
                         "Requisition",
                         JOptionPane.YES_OPTION,
                         JOptionPane.QUESTION_MESSAGE, null, message, message[0]);
    }

    public void dataInvalidated() {
        String message[] = {"Ok"};
        Util.showMessage("Fill all fields to save.",
                         "Requisition",
                         JOptionPane.YES_OPTION,
                         JOptionPane.QUESTION_MESSAGE, null, message, message[0]);
    }

    public void setEnabledPlayStopButton(boolean hasMidiFileInformed) {
        this.jButtonPlayStop.setEnabled(hasMidiFileInformed);
        this.repaint();
        this.revalidate();
    }

    public void updatePlayStopButton(boolean playing) throws IOException {

        if (playing) {
            this.jButtonPlayStop.setText("Stop");
            this.jButtonPlayStop.setIcon(Util.getImageIcon(ImageIO.read(App.getContext().getResource("classpath:figures/stop.png").getInputStream()), 25));
        } else {
            this.jButtonPlayStop.setText("Play");
            this.jButtonPlayStop.setIcon(Util.getImageIcon(ImageIO.read(App.getContext().getResource("classpath:figures/play.png").getInputStream()), 25));
        }

        this.repaint();
        this.revalidate();
    }

    public void update(Observable subject, Object argument) {
        //----------------------------------------------------------------------
        if (subject == null) return;
        if (!(argument instanceof KeyValue[])) return;
        //----------------------------------------------------------------------
        KeyValue[] keys = (KeyValue[]) argument;

        for(KeyValue keyValue : keys) {
            //System.out.println(String.format("key: %s", keyValue.getKey().toString()));
            if (keyValue.getKey().toString().equalsIgnoreCase("CLASS")) {
                Class classe = (Class) keyValue.getValue();
                if (classe != null) break;
            }
            else if (keyValue.getKey().toString().equalsIgnoreCase("ALIAS")) {
                String aliasArgument = (String) keyValue.getValue();
                if (!aliasArgument.equalsIgnoreCase(Database_Insert_View.class.getSimpleName())) break;
            }
            else if (keyValue.getKey().toString().equalsIgnoreCase("STATE")) {
                boolean enabled = Convert.toBoolean(keyValue.getValue());
                try {
                    updatePlayStopButton(enabled);
                } catch (IOException ex) {
                    Logger.getLogger(Database_Insert_View.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}