#include "mainwindow.h"
#include "ui_mainwindow.h"

MainWindow::MainWindow(QWidget *parent): QMainWindow(parent) , ui(new Ui::MainWindow)
{
    ui->setupUi(this);
    btnLogin = ui->btnLogin;
    btnSignup = ui->btnSignup;

    ui->auth->setCurrentWidget(ui->pageLogin);

    connect(btnLogin, &QPushButton::clicked, this, &MainWindow::Login);
    connect(btnSignup, &QPushButton::clicked, this, &MainWindow::Signup);
}

MainWindow::~MainWindow()
{
    delete ui;
}

void MainWindow::Login() {
    ui->auth->setCurrentWidget(ui->pageLogin);
}

void MainWindow::Signup() {
    ui->auth->setCurrentWidget(ui->pageSignup);
}
