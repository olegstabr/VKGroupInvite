package com.company

import java.awt.Color
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JLabel

class LabelHoverAdapter : MouseAdapter() {

    override fun mouseEntered(e: MouseEvent) {
        val lbl = e.getComponent() as JLabel
        lbl.foreground = Color(255, 127, 80)
    }

    override fun mouseExited(e: MouseEvent) {
        val lbl = e.getComponent() as JLabel
        lbl.foreground = Color.BLACK
    }
}