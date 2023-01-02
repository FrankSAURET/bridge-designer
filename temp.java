import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

class Downloader extends JFrame {
    JProgressBar progress;
    int maxLabel = 15;
    JTextField speed, sizeL;
    int bytesRead;
    boolean running = false;
    private File destDir;
    private String prefsFile = "prefs";

    private void setSpeed(float sp) {
        setSpeed(sp + " kb/s");
    }

    private void setSize(int i) {
        String s;
        if (i < 1024) {
            s = i + " bytes";
        } else if (i < 1048576) {
            s = ((float) i / 1024) + " kilobytes";
        } else {
            s = ((float) i / 1048576) + " magabytes";
        }
        sizeL.setText(s);
    }

    private void setSpeed(String sp) {
        speed.setText(sp);
    }

    public int getChosen(JFileChooser chooser) {
        return chooser.showOpenDialog(this);
    }

    public void download(final URL location) {
        new Thread(
                new Runnable() {
                    public void run() {
                        try {
                            URLConnection connection = location.openConnection();
                            int sourceLen = connection.getContentLength();

                            File dest = selectFileName(location);
                            if (dest != null) {
                                progress.setMinimum(0);
                                int max = 100;
                                if (sourceLen != -1) {
                                    max = sourceLen;
                                }
                                progress.setMaximum(max);

                                progress.setValue(0);
                                progress.setStringPainted(true);
                                progress.setToolTipText("Downloading " + location);
                                InputStream is = connection.getInputStream();

                                FileOutputStream destFile = new FileOutputStream(dest);
                                bytesRead = 0;
                                int b;
                                running = true;
                                new ByteTimer().start();
                                while ((b = is.read()) != -1) {
                                    destFile.write(b);
                                    if (sourceLen != -1) {
                                        progress.setValue(++bytesRead);
                                    }
                                }
                                running = false;
                                if (sourceLen != -1) {
                                    progress.setValue(max);
                                }
                                destFile.close();
                                is.close();
                            }
                        }

                catch (Exception e) {
                            error("Error", "An error has occurred: " + e);
                        }
                    }
                }).start();

    }

    public static void main(String[] args) {
        new Downloader();
    }

    public void error(String title, String message) {
        JOptionPane.showMessageDialog(this,
                message,
                title, JOptionPane.INFORMATION_MESSAGE);
    }

    public Downloader() {
        loadPrefs();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Andrew's little downloader");
        setResizable(false);

        getContentPane().add(getContent());
        pack();
        show();
    }

    private JPanel getContent() {
        JPanel content = new JPanel(new BorderLayout(5, 5));
        final JTextField urlField = new JTextField("", 40);
        content.add(urlField, BorderLayout.CENTER);
        class DownloadListener implements ActionListener {
            public void actionPerformed(ActionEvent event) {
                String urlLoc = urlField.getText();
                try {
                    setSpeed("Not downloading");
                    download(new URL(urlLoc));
                } catch (MalformedURLException e) {
                    error("Invalid URL", "Sorry but the specified URL is invalid : " + urlLoc + "\n" + e);
                }
            }
        }
        DownloadListener dl = new DownloadListener();
        JButton get = new JButton("Get");
        urlField.addActionListener(dl);
        get.addActionListener(dl);
        JButton paste = new JButton("Paste");
        paste.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        urlField.setText("");
                        urlField.paste();
                    }
                });
        content.add(getDirPanel(), BorderLayout.NORTH);
        content.add(getProgPanel(), BorderLayout.SOUTH);
        content.add(get, BorderLayout.EAST);
        content.add(paste, BorderLayout.WEST);
        return content;
    }

    private JPanel getProgPanel() {
        speed = new JTextField("", maxLabel);
        sizeL = new JTextField("", maxLabel);

        progress = new JProgressBar();
        speed.setEditable(false);
        sizeL.setEditable(false);
        setSpeed("Not downloading");
        setSize(0);

        JPanel progPanel = new JPanel(new BorderLayout());
        progPanel.add(sizeL, BorderLayout.WEST);
        progPanel.add(progress, BorderLayout.CENTER);
        progPanel.add(speed, BorderLayout.EAST);

        return progPanel;
    }

    private JPanel getDirPanel() {
        JPanel dirPanel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Destination dir:");
        dirPanel.add(label, BorderLayout.WEST);
        final JTextField dirField = new JTextField(destDir.toString());
        dirField.setEditable(false);
        dirPanel.add(dirField, BorderLayout.CENTER);
        JButton change = new JButton("Change");
        change.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        File dir = selectDir();
                        if (dir != null) {
                            destDir = dir;
                            dirField.setText(destDir.toString());
                            savePrefs();
                        }
                    }
                });
        dirPanel.add(change, BorderLayout.EAST);
        return dirPanel;
    }

    public class ByteTimer extends Thread {
        public int lastSize = 0;

        public void run() {
            lastSize = bytesRead;
            try {
                while (running) {
                    sleep(950);
                    int amount = bytesRead - lastSize;
                    setSpeed(((float) amount / 1024));
                    setSize(bytesRead);
                    lastSize = bytesRead;
                }
                setSpeed("Complete");
            } catch (Exception e) {
                setSpeed("An error occurred");
            }
        }
    }

    private void loadPrefs() {
        try {
            FileInputStream in = new FileInputStream(prefsFile);
            ObjectInputStream s = new ObjectInputStream(in);
            destDir = (File) s.readObject();
        } catch (Exception e) {
            destDir = new File(System.getProperty("user.home"));
        }
    }

    private void savePrefs() {
        try {
            FileOutputStream out = new FileOutputStream(prefsFile);
            ObjectOutputStream s = new ObjectOutputStream(out);
            s.writeObject(destDir);
            s.flush();
        } catch (Exception e) {

        }
    }

    private File selectDir() {
        JFileChooser chooser = new JFileChooser(destDir);
        chooser.setApproveButtonMnemonic('s');
        chooser.setApproveButtonText("Select Directory");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        File dir = null;
        int returnVal = getChosen(chooser);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            dir = chooser.getSelectedFile();
        }
        return dir;
    }

    private File selectFileName(URL location) {
        String name = location.getFile().toString();
        int pos = name.lastIndexOf("/");
        if (pos != -1) {
            name = name.substring(pos + 1, name.length());
        }
        String s = (String) JOptionPane.showInputDialog(this, "What do you want to save the file as?", name);
        File dest = null;
        if (s != null) {
            dest = new File(destDir, s);
            if (dest.exists()) {
                if (!ask("Save over existing file")) {
                    selectFileName(location);
                }
            }
        }
        return dest;
    }

    private boolean ask(String action) {
        boolean confirmed = false;
        String title = action.substring(0, 1).toUpperCase() +
                action.substring(1) + "?";
        int option = JOptionPane.showConfirmDialog(
                this, "Do you want to " + action + "?",
                title,
                JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            confirmed = true;
        }

        return confirmed;
    }
}