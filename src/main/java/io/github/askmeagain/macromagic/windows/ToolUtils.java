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
}
