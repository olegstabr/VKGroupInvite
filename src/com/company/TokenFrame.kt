package com.company

import java.awt.*
import javax.swing.*
import java.awt.Dimension


class TokenFrame : JFrame() {
    private val label = JLabel()
    private val textField = JTextField()
    private val button = JButton()

    init {

        size = Dimension(500, 100)
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        layout = BoxLayout(contentPane, BoxLayout.Y_AXIS)
        setLocationRelativeTo(null)
        isVisible = true

        label.text = "Введите токен"
        label.size = Dimension(400, 20)
        label.alignmentX = Component.CENTER_ALIGNMENT
        label.font = Font("SansSerif", Font.BOLD, 16)

        textField.size = Dimension(400, 20)
        textField.alignmentX = Component.CENTER_ALIGNMENT
        textField.font = Font("SansSerif", Font.PLAIN, 20)

        button.text = "ОК"
        button.size = Dimension(100, 20)
        button.alignmentX = Component.CENTER_ALIGNMENT
        button.font = Font("SansSerif", Font.PLAIN, 14)
        button.addActionListener({
            println(textField.text)
        })

        contentPane.add(Box.createRigidArea(Dimension(0, 5)))
        add(label)
        contentPane.add(Box.createRigidArea(Dimension(0, 5)))
        add(textField)
        contentPane.add(Box.createRigidArea(Dimension(0, 5)))
        add(button)
        contentPane.add(Box.createRigidArea(Dimension(0, 5)))
    }
}