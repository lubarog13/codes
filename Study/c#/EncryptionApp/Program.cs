using System;
using Pact.CS7;
using static System.Console;

namespace EncryptionApp
{
    class Program
    {
        static void Main(string[] args)
        {
            //WriteLine("Введите сообщение для кодирования");
            //string message = ReadLine();
            //WriteLine("Введите пароль");
            //string password = ReadLine();
            //string cryptoText = Protector.Encrypt(message, password);
            //WriteLine($"Закодированный текст: {cryptoText}");
            //WriteLine("Введите пароль");
            //string password2 = ReadLine();
            //try
            //{
            //    string clearText = Protector.Decrypt(cryptoText, password2);
            //    WriteLine($"Раскодированный текст: {clearText}");
            //}
            //catch
            //{
            //    WriteLine("Неправильный пароль");
            //}
            WriteLine("A user named Alice has been registered whith Pa$$w0rd as har password. ");
            var alice = Protector.Register("Alice", "Pa$$w0rd");
            WriteLine($"Name: {alice.Name}");
            WriteLine($"Salt: {alice.Salt}");
            WriteLine($"Salted and hashed password: {alice.SaledHashedPassword}");
            WriteLine();
            WriteLine("Enter a different name to register: ");
            string username = ReadLine();
            WriteLine("Enter a password ");
            string password = ReadLine();
            var user = Protector.Register(username, password);
            WriteLine($"Name: {user.Name}");
            WriteLine($"Salt: {user.Salt}");
            WriteLine($"Salted and hashed password: {user.SaledHashedPassword}");
            bool correctPassword = false;
            while(!correctPassword)
            {
                WriteLine("Enter a name to enter: ");
                username = ReadLine();
                WriteLine("Enter a password to enter: ");
                password = ReadLine();
                correctPassword = Protector.CheckPassword(username, password);
                if (!correctPassword)
                {
                    WriteLine("Try again");
                }
            }
            WriteLine("Success!");
        }
    }
}
