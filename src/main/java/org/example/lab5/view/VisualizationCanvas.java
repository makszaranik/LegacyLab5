package org.example.lab5.view;

import lombok.NoArgsConstructor;
import org.example.lab5.Annotation.Autowired;
import org.example.lab5.Annotation.Component;
import org.example.lab5.Annotation.PostConstruct;
import org.example.lab5.controller.AudienceController;
import org.example.lab5.model.Audience;
import org.example.lab5.model.Student;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@NoArgsConstructor
public class VisualizationCanvas extends JPanel {

    @Autowired
    private AudienceController audienceController;

    private boolean blinkOn = false;

    private final Map<Audience.AudienceType, Color> palette = Map.of(
            Audience.AudienceType.LECTURE,    new Color(180, 225, 245),
            Audience.AudienceType.CONFERENCE, new Color(255, 230, 170),
            Audience.AudienceType.LABORATORY, new Color(220, 190, 200)
    );


    @PostConstruct
    private void initCanvas() {
        setPreferredSize(new Dimension(800, 500));
        setBackground(Color.white);
    }

    public void nextStep() {
        List<Audience> list = audienceController.getAllAudiences();
        if (list.isEmpty()) return;
        blinkOn = !blinkOn;
        repaint();
    }

    public void reset() {
        blinkOn = false;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics gRaw) {
        super.paintComponent(gRaw);
        Graphics2D g = (Graphics2D) gRaw;

        List<Audience> auds = audienceController.getAllAudiences();
        int maxStudents = auds.stream()
                .mapToInt(a -> a.getStudents().size())
                .max()
                .orElse(0);

        List<Integer> maxIndexes = new ArrayList<>();
        for (int i = 0; i < auds.size(); i++) {
            if (auds.get(i).getStudents().size() == maxStudents && maxStudents > 0) {
                maxIndexes.add(i);
            }
        }

        int w = 160, h = 90, gap = 25;
        int x = 70, y = 40;

        for (int i = 0; i < auds.size(); i++) {
            Audience a = auds.get(i);
            int rectY = y + i * (h + gap);

            g.setColor(palette.get(a.getAudienceType()));
            g.fillRect(x, rectY, w, h);

            if (maxIndexes.contains(i) && blinkOn) {
                g.setColor(Color.RED);
                g.setStroke(new BasicStroke(3));
            } else {
                g.setColor(Color.BLACK);
                g.setStroke(new BasicStroke(1));
            }
            g.drawRect(x, rectY, w, h);

            g.drawString(a.getName(),                x + 10, rectY + 18);
            g.drawString("Capacity: " + a.getCapacity(),  x + 10, rectY + 34);
            g.drawString("Students: " + a.getStudents().size(), x + 10, rectY + 50);

            int sx = x + 10, sy = rectY + 60;
            for (Student s : a.getStudents()) {
                g.setColor(Color.BLUE);
                g.fillRect(sx, sy, 10, 10);
                sx += 14;
                if (sx + 10 > x + w - 10) break;
            }
        }
    }
}
