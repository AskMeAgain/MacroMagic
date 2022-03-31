package io.github.askmeagain.macromagic.windows;

import com.intellij.openapi.actionSystem.Presentation;

import javax.swing.*;
import java.awt.*;

public class ToolUtils {

  public static Presentation getPresentation(Icon icon) {
    var presentation = new Presentation(icon.toString());
    presentation.setIcon(icon);
    return presentation;
  }

  public static JLabel getTitleLabel(String title) {
    var label = new JLabel(title, SwingConstants.LEFT);
    label.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
    label.setMinimumSize(new Dimension(Integer.MAX_VALUE, 30));
    label.setOpaque(true);
    return label;
  }
}
