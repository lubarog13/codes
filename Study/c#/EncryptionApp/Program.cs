using System;
using Pact.CS7;
using static System.Console;

namespace EncryptionApp
{
    class Program
    {
        static void Main(string[] args)
        {
            WriteLine("Введите сообщение для кодирования");
            string message = ReadLine();
            WriteLine("Введите пароль");
            string password = ReadLine();
            string cryptoText = Protector.Encrypt(message, password);
            WriteLine($"Закодированный текст: {cryptoText}");
            WriteLine("Введите пароль");
            string password2 = ReadLine();
            try
            {
                string clearText = Protector.Decrypt(cryptoText, password2);
                WriteLine($"Раскодированный текст: {clearText}");
            }
            catch
            {
                WriteLine("Неправильный пароль");
            }
    }
    }
}
