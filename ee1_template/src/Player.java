import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import javazoom.jl.decoder.*;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.FactoryRegistry;
import support.PlayerWindow;
import support.Song;

import java.awt.event.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Player {

    /**
     * The MPEG audio bitstream.
     */
    private Bitstream bitstream;
    /**
     * The MPEG audio decoder.
     */
    private Decoder decoder;
    /**
     * The AudioDevice the audio samples are written to.
     */
    private AudioDevice device;

    private PlayerWindow window;

    private ArrayList<Song> musica =  new ArrayList <Song>();
    private boolean repeat = false;
    private boolean shuffle = false;
    private boolean playerEnabled = false;
    private boolean playerPaused = true;
    private Song currentSong = null;
    private int currentFrame = 0;
    private int newFrame;
    private int current_i = 0;

    String[][] fila = {};
    public Player() {
        ActionListener buttonListenerPlayNow = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread t_playnow = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        String playnow = window.getSelectedSong();
                        System.out.println(playnow);
                        for(int i = 0;i < musica.size();i++){
                            if (musica.get(i).getFilePath() == playnow){
                               update(i);

                            }
                        }
                        playerPaused = false;
                        window.updatePlayPauseButtonIcon(playerPaused);
                        playerEnabled = true;
                        window.setEnabledScrubberArea(playerEnabled);
                        try {
                            File file = new File(currentSong.getFilePath());
                            int maxFrames = new Mp3File(file).getFrameCount();
                            device = FactoryRegistry.systemRegistry().createAudioDevice();
                            device.open(decoder = new Decoder());
                            bitstream = new Bitstream(new BufferedInputStream(new FileInputStream(file)));
                        } catch (JavaLayerException | InvalidDataException | UnsupportedTagException | IOException ex) {
                            ex.printStackTrace();
                        }
                        var playerThread = new Thread(new DemoTask());



                    }
                });
                t_playnow.start();
            }
        };
        ActionListener buttonListenerRemove = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread t_RemovedSong = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String removedSong = window.getSelectedSong();
                        for(int i = 0;i < musica.size();i++){
                            if (musica.get(i).getFilePath() == removedSong){
                                musica.remove(i);
                            }
                        }
                        fila = getQueueAsArray();
                        window.updateQueueList(fila);

                    }

                });
                t_RemovedSong.start();
            }
        };
        ActionListener buttonListenerAddSong = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread t_AddSong = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Song n_music = window.getNewSong();
                            addToQueue(n_music);
                        } catch (Exception ex) {
                        }
                    }
                });
                t_AddSong.start();
            }
        };
        ActionListener buttonListenerShuffle = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread t_buttonShuffle = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try{
                            String teste = "";
                            if(current_i != -1){

                            }

                        }catch  (Exception ex) {
                        }


                    }
                });t_buttonShuffle.start();

            }
        };
        ActionListener buttonListenerPrevious = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread t_buttonPrevious = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try{
                            if(current_i > 0){
                                current_i--;
                                update(current_i);
                            }
                        }catch  (Exception ex) {
                        }


                    }
                });t_buttonPrevious.start();

            }
        };
        ActionListener buttonListenerPlayPause = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread t_PlayPause = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try{
                            playerPaused = !playerPaused;
                            window.updatePlayPauseButtonIcon(playerPaused);
                        }catch  (Exception ex) {
                        }


                    }
                });t_PlayPause.start();


            }
        };
        ActionListener buttonListenerStop = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        };
        ActionListener buttonListenerNext = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread t_buttonNext = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try{
                            if(current_i < musica.size()-1){
                                current_i++;
                                update(current_i);
                            }
                        }catch  (Exception ex) {
                        }


                    }
                });t_buttonNext.start();


            }
        };
        ActionListener buttonListenerRepeat = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread t_buttonRepeat = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try{
                            repeat = !repeat;
                            window.setEnabledRepeatButton(!repeat);

                    }catch  (Exception ex) {
                    }


                }
            });t_buttonRepeat.start();


            }
        };
        MouseListener scrubberListenerClick = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        };
        MouseMotionListener scrubberListenerMotion =  new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        };
        window = new PlayerWindow("song simulator", fila, buttonListenerPlayNow,
                buttonListenerRemove,
                buttonListenerAddSong,
                buttonListenerShuffle,
                buttonListenerPrevious,
                buttonListenerPlayPause,
                buttonListenerStop,
                buttonListenerNext,
                buttonListenerRepeat,
                scrubberListenerClick,
                scrubberListenerMotion);

    }

    //<editor-fold desc="Essential">

    /**
     * @return False if there are no more frames to play.
     */
    private boolean playNextFrame() throws JavaLayerException {
        // TODO Is this thread safe?
        if (device != null) {
            Header h = bitstream.readFrame();
            if (h == null) return false;

            SampleBuffer output = (SampleBuffer) decoder.decodeFrame(h, bitstream);
            device.write(output.getBuffer(), 0, output.getBufferLength());
            bitstream.closeFrame();
        }
        return true;
    }

    /**
     * @return False if there are no more frames to skip.
     */
    private boolean skipNextFrame() throws BitstreamException {
        // TODO Is this thread safe?
        Header h = bitstream.readFrame();
        if (h == null) return false;
        bitstream.closeFrame();
        currentFrame++;
        return true;
    }

    /**
     * Skips bitstream to the target frame if the new frame is higher than the current one.
     *
     * @param newFrame Frame to skip to.
     * @throws BitstreamException
     */
    private void skipToFrame(int newFrame) throws BitstreamException {
        // TODO Is this thread safe?
        if (newFrame > currentFrame) {
            int framesToSkip = newFrame - currentFrame;
            boolean condition = true;
            while (framesToSkip-- > 0 && condition) condition = skipNextFrame();
        }
    }
    //</editor-fold>

    //<editor-fold desc="Queue Utilities">
    public void addToQueue(Song song) {
        musica.add(song);
        fila = getQueueAsArray();
        window.updateQueueList(fila);
    }

    public void removeFromQueue(String filePath) {
    }

    public String[][] getQueueAsArray() {
        String[][] novafila = new String[musica.size()][6];
        for(int i = 0; i < musica.size(); i++){
            novafila[i] = musica.get(i).getDisplayInfo();

        }
        return novafila;
    }
    class DemoTask implements Runnable {
        @Override
        public void run() {
            if (device != null) {
                try {

                    do {

                    } while (!playNextFrame());
                } catch (JavaLayerException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //</editor-fold>

    //<editor-fold desc="Controls">
    public void start(String filePath) {
    }

    public void stop() {
    }

    public void pause() {
    }

    public void resume() {
    }

    public void next() {
    }

    public void previous() {
    }
    public void update(int i) {
        Song play = musica.get(i);
        currentSong = play;
        current_i = i;
        window.updatePlayingSongInfo(play.getTitle(),play.getAlbum(),play.getArtist());
        if (i == 0){
            window.setEnabledPreviousButton(false);

        }
        else{
            window.setEnabledPreviousButton(true);
        }
        if (i == musica.size()-1){
            window.setEnabledNextButton(false);

        }
        else{
            window.setEnabledNextButton(true);
        }
        }
        //</editor-fold>
    //<editor-fold desc="Getters and Setters">

    //</editor-fold>
}
