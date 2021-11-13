using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices.WindowsRuntime;
using Windows.Foundation;
using Windows.Foundation.Collections;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Controls.Primitives;
using Windows.UI.Xaml.Data;
using Windows.UI.Xaml.Input;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Navigation;

// Документацию по шаблону элемента "Пустая страница" см. по адресу https://go.microsoft.com/fwlink/?LinkId=402352&clcid=0x419

namespace NETApp
{
    /// <summary>
    /// Пустая страница, которую можно использовать саму по себе или для перехода внутри фрейма.
    /// </summary>
    public sealed partial class MainPage : Page
    {
        public MainPage()
        {
            this.InitializeComponent();
        }

        private void clickMeButton_Click(object sender, RoutedEventArgs e)
        {
            clickMeButton.Content = DateTime.Now.ToString("hh:mm:ss");
        }

        private void Page_Loaded(object sender, RoutedEventArgs e)
        {
            markdownSource.Text = "# Welcome\n";
            foreach(Button b in gridCalculator.Children.OfType<Button>())
            {
                b.FontSize = 24;
                b.Width = 54;
                b.Height = 54;
                b.Click += childButton_Click;
                b.Style = Resources.ThemeDictionaries["ButtonRevealStyle"] as Style;
            }
        }
        private void childButton_Click(object sender, RoutedEventArgs e)
        {
            markdownSource.Text += ((Button)sender).Content.ToString();
        }

        private void TextBox_TextChanged(object sender, TextChangedEventArgs e)
        {
            markdownSource.Text = "\n# Hello, " + ((TextBox)sender).Text + "\n";
        }

        private void enableAcrylic_Checked(object sender, RoutedEventArgs e)
        {
            
        }
    }
}
