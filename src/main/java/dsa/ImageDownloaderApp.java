package dsa;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class ImageDownloaderApp extends JFrame {
    private static final String DOWNLOAD_DIRECTORY = "downloaded_images/";
    private JTextField urlField;
    private JButton downloadButton, pauseButton, resumeButton, cancelButton;
    private JPanel downloadPanel; // Panel to hold download components
    private ExecutorService executorService;
    private List<Pair<Future<?>, DownloadTask>> downloadTasks; // Pair of Future and DownloadTask
    private Map<DownloadTask, DownloadInfo> downloadInfoMap; // Map to store download progress and URL for each task

    public ImageDownloaderApp() {
        setTitle("Image Downloader");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        executorService = Executors.newFixedThreadPool(5);
        downloadTasks = new CopyOnWriteArrayList<>();
        downloadInfoMap = new ConcurrentHashMap<>();
    }

    private void initComponents() {
        urlField = new JTextField(30);
        downloadButton = new JButton("Download");
        pauseButton = new JButton("Pause All");
        resumeButton = new JButton("Resume All");
        cancelButton = new JButton("Cancel All");
        downloadPanel = new JPanel(new GridLayout(0, 1)); // Using GridLayout to stack components vertically

        downloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String urlsText = urlField.getText();
                String[] urls = urlsText.split("[,\\s]+"); // Split the text by commas or whitespace
                for (String url : urls) {
                    if (!url.isEmpty()) {
                        downloadImage(url);
                    }
                }
            }
        });

        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pauseDownloads();
            }
        });

        resumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resumeDownloads();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelDownloads();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(downloadButton);
        buttonPanel.add(pauseButton);
        buttonPanel.add(resumeButton);
        buttonPanel.add(cancelButton);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(urlField, BorderLayout.NORTH);
        getContentPane().add(downloadPanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    private void downloadImage(String urlString) { // Remove all download panels from the UI
        JPanel imagePanel = new JPanel(new BorderLayout());
        JLabel urlLabel = new JLabel(urlString);
        JProgressBar progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        imagePanel.add(urlLabel, BorderLayout.NORTH);
        imagePanel.add(progressBar, BorderLayout.CENTER);
        downloadPanel.add(imagePanel);
        pack(); // Adjust frame size to accommodate the new components

        DownloadTask downloadTask = new DownloadTask(urlString, progressBar);
        Future<?> future = executorService.submit(downloadTask);
        downloadTasks.add(new Pair<>(future, downloadTask));
    }

    private void pauseDownloads() {
        for (Pair<Future<?>, DownloadTask> pair : downloadTasks) {
            DownloadTask downloadTask = pair.getValue();
            downloadTask.pauseDownload();
        }
    }

    private void resumeDownloads() {
        for (Pair<Future<?>, DownloadTask> pair : downloadTasks) {
            DownloadTask downloadTask = pair.getValue();
            downloadTask.resumeDownload();
        }
    }

    private void cancelDownloads() {
        for (Pair<Future<?>, DownloadTask> pair : downloadTasks) {
            Future<?> future = pair.getKey();
            future.cancel(true);
        }
        downloadPanel.removeAll(); // Remove all download panels from the UI
        pack(); // Adjust frame size after removing components
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ImageDownloaderApp();
            }
        });
    }

    private class DownloadInfo {
        private String url;
        private volatile int progress;

    }

    private class Pair<K, V> {
        private K key;
        private V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }

    private class DownloadTask implements Runnable {
        private String urlString;
        private JProgressBar progressBar;
        private AtomicInteger progress;
        private AtomicBoolean paused;
        private boolean firstTime;

        public DownloadTask(String urlString, JProgressBar progressBar) {
            this.urlString = urlString;
            this.progressBar = progressBar;
            this.progress = new AtomicInteger(0);
            this.paused = new AtomicBoolean(false);
            this.firstTime = true;
        }

        public void pauseDownload() {
            paused.set(true);
        }

        public void resumeDownload() {
            paused.set(false);
            if (firstTime) {
                firstTime = false;
                executorService.submit(this);
            }
        }

        @Override
        public void run() {
            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("User-Agent", "Mozilla/5.0");

                if (progress.get() > 0) {
                    connection.setRequestProperty("Range", "bytes=" + progress.get() + "-");
                }

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_PARTIAL) {
                    int contentLength = connection.getContentLength();
                    InputStream inputStream = connection.getInputStream();
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    int totalBytesRead = progress.get(); // Initialize totalBytesRead with the progress

                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        if (Thread.currentThread().isInterrupted()) {
                            throw new InterruptedException("Download interrupted");
                        }

                        if (paused.get()) {
                            progress.set(totalBytesRead);
                            inputStream.close();
                            outputStream.close();
                            return;
                        }

                        outputStream.write(buffer, 0, bytesRead);
                        totalBytesRead += bytesRead; // Increment totalBytesRead with the bytes read in this iteration
                        int currentProgress = (int) (((double) totalBytesRead / contentLength) * 100); // Calculate
                                                                                                       // current
                                                                                                       // progress
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setValue(currentProgress);
                            }
                        });

                        Thread.sleep(50);
                    }

                    // Save the image after the download is complete
                    String fileName = "image_" + System.currentTimeMillis() + ".jpg";
                    saveImage(outputStream.toByteArray(), fileName);

                    inputStream.close();
                } else {
                    throw new IOException("Failed to download image. Response code: " + responseCode);
                }
            } catch (IOException | InterruptedException e) {
                if (e instanceof InterruptedException) {
                    Thread.currentThread().interrupt();
                }
                if (!(e instanceof InterruptedException)) {
                    e.printStackTrace();
                    // Show error message
                }
            }
        }

        private void saveImage(byte[] imageData, String fileName) {
            File directory = new File(DOWNLOAD_DIRECTORY);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String fullPath = DOWNLOAD_DIRECTORY + fileName;

            try {
                FileOutputStream outputStream = new FileOutputStream(fullPath);
                outputStream.write(imageData);
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
