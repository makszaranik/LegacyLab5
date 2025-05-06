package org.example.lab5.view;

import lombok.NoArgsConstructor;
import org.example.lab5.Annotation.Autowired;
import org.example.lab5.Annotation.Component;
import org.example.lab5.Annotation.PostConstruct;
import org.example.lab5.controller.AudienceController;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;

@Component
@NoArgsConstructor
public class SimulationPanel extends JPanel {

    @Autowired
    private AudienceController audienceController;

    @Autowired
    private VisualizationCanvas canvas;

    private Timer timer;
    private JSlider speedSlider;

    @PostConstruct
    private void initUI() {
        setLayout(new BorderLayout(5, 5));

        add(new JScrollPane(canvas), BorderLayout.CENTER);

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        JButton startBtn = new JButton("Start");
        JButton pauseBtn = new JButton("Pause");
        JButton stopBtn  = new JButton("Stop");
        pauseBtn.setEnabled(false);
        stopBtn.setEnabled(false);

        speedSlider = new JSlider(500, 2000, 1000);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
        speedSlider.setMajorTickSpacing(500);
        speedSlider.setMinorTickSpacing(250);

        controls.add(startBtn);
        controls.add(pauseBtn);
        controls.add(stopBtn);
        controls.add(new JLabel("Speed (ms):"));
        controls.add(speedSlider);
        add(controls, BorderLayout.SOUTH);

        timer = new Timer(speedSlider.getValue(), e -> canvas.nextStep());

        startBtn.addActionListener(e -> {
            if (timer.isRunning()) return;
            timer.setDelay(speedSlider.getValue());
            timer.start();
            startBtn.setEnabled(false);
            pauseBtn.setEnabled(true);
            stopBtn.setEnabled(true);
        });
        pauseBtn.addActionListener(e -> {
            timer.stop();
            startBtn.setEnabled(true);
            pauseBtn.setEnabled(false);
        });
        stopBtn.addActionListener(e -> {
            timer.stop();
            canvas.reset();
            startBtn.setEnabled(true);
            pauseBtn.setEnabled(false);
            stopBtn.setEnabled(false);
        });

        speedSlider.addChangeListener((ChangeEvent e) -> {
            if (!speedSlider.getValueIsAdjusting()) {
                timer.setDelay(speedSlider.getValue());
            }
        });
    }
}
