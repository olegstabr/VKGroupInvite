package com.company

import java.awt.*
import javax.swing.*
import java.awt.Dimension


class TokenFrame : JFrame() {
    private val label = JLabel()
    private val textField = JTextField()
    private val button = JButton()

    init {
        title = "Авторизация"
        size = Dimension(450, 100)
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        layout = GridLayout(3, 1)
        setLocationRelativeTo(null)
        isVisible = true

        label.text = "Введите токен"
        label.size = Dimension(400, 20)
        label.font = Font("SansSerif", Font.BOLD, 16)

        textField.size = Dimension(400, 20)
        textField.font = Font("SansSerif", Font.PLAIN, 20)

        button.text = "ОК"
        button.size = Dimension(100, 20)
        button.font = Font("SansSerif", Font.PLAIN, 14)
        button.addActionListener {
            MainFrame(textField.text)
            dispose()
        }

        contentPane.add(label)
        contentPane.add(textField)
        contentPane.add(button)
    }
}