package io.github.askmeagain.macromagic.listener;

import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.ui.awt.RelativePoint;
import lombok.RequiredArgsConstructor;

import javax.swing.SwingUtilities;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

@RequiredArgsConstructor
public class RightClickContextListener implements MouseListener {

  private final ActionGroup actionGroup;

  @Override
  public void mouseClicked(MouseEvent mouseEvent) {
    if (SwingUtilities.isRightMouseButton(mouseEvent)) {
      JBPopupFactory.getInstance().createActionGroupPopup(
          actionGroup.getTemplateText(),
          actionGroup,
          DataManager.getInstance().getDataContext(mouseEvent.getComponent()),
          JBPopupFactory.ActionSelectionAid.SPEEDSEARCH,
          true
      ).show(new RelativePoint(mouseEvent));
    }
  }

  @Override
  public void mousePressed(MouseEvent mouseEvent) {

  }

  @Override
  public void mouseReleased(MouseEvent mouseEvent) {

  }

  @Override
  public void mouseEntered(MouseEvent mouseEvent) {

  }

  @Override
  public void mouseExited(MouseEvent mouseEvent) {

  }
}
