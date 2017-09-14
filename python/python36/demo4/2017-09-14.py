#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2017/9/14 11:02
# @Author  : boneix
import tkinter.messagebox as messagebox
from tkinter import *


class Application(Frame):
    def __init__(self, master=None):
        Frame.__init__(self, master)
        self.pack()
        self.create_widgets()

    def create_widgets(self):
        self.helloLabel = Label(self, text='Hello world!')
        self.helloLabel.pack()
        self.nameInput = Entry(self)
        self.nameInput.pack()
        self.alertButton = Button(self, text='Hello', command=self.hello)
        self.alertButton.pack()
        self.quitButton = Button(self, text='Quit', command=self.quit)
        self.quitButton.pack()

    def hello(self):
        name = self.nameInput.get() or 'world'
        messagebox.showinfo('Message', 'this is a message,%s' % name)


app = Application()
app.master.title("Hello world app")
app.mainloop()
