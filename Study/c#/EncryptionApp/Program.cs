using System;
using Pact.CS7;
using static System.Console;
using System.Xml.Serialization;
using System.Collections.Generic;
using System.IO;
using static System.Environment;
using static System.IO.Path;
using System.Collections;

namespace EncryptionApp
{
    public class Customer
    {
        public string name { get; set; }
        public string creditcard { get; set; }
        public string password { get; set; }
        public Customer () { }
        public Customer(string name, string creditcard, string password)
        {
            this.name = name;
            this.creditcard = creditcard;
            this.password = password;
        }
    }
    [XmlRoot("customers")]
    [XmlType("NewTypeName")]
    public class Customers 
    {
        [XmlElement (ElementName = "customer")]
        public List<Customer> customers;

        public Customers()
        {
        }

        public Customers(List<Customer> customers)
        {
            this.customers = customers;
        }
    }
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
            //WriteLine("A user named Alice has been registered whith Pa$$w0rd as har password. ");
            //var alice = Protector.Register("Alice", "Pa$$w0rd");
            //WriteLine($"Name: {alice.Name}");
            //WriteLine($"Salt: {alice.Salt}");
            //WriteLine($"Salted and hashed password: {alice.SaledHashedPassword}");
            //WriteLine();
            //WriteLine("Enter a different name to register: ");
            //string username = ReadLine();
            //WriteLine("Enter a password ");
            //string password = ReadLine();
            //var user = Protector.Register(username, password);
            //WriteLine($"Name: {user.Name}");
            //WriteLine($"Salt: {user.Salt}");
            //WriteLine($"Salted and hashed password: {user.SaledHashedPassword}");
            //bool correctPassword = false;
            //while(!correctPassword)
            //{
            //    WriteLine("Enter a name to enter: ");
            //    username = ReadLine();
            //    WriteLine("Enter a password to enter: ");
            //    password = ReadLine();
            //    correctPassword = Protector.CheckPassword(username, password);
            //    if (!correctPassword)
            //    {
            //        WriteLine("Try again");
            //    }
            //}
            //WriteLine("Success!");
            var customers = new Customers ( new List<Customer> { new Customer("Ivan", "1234-5678-9012-3456", "1234"), new Customer("Alina", "215363", "163762") } );
            string path = Combine("C:\\Users\\DNS\\Desktop\\VS_code\\Study\\c#\\EncryptionApp\\", "customers.xml");
            FileStream xmlFile;
            WriteLine(path);
            if (File.Exists(path))
            {
                xmlFile = File.OpenRead(path);
            }
            else
            {
                xmlFile = File.Create(path);
                return;
            }
            try
            {
                var xs = new XmlSerializer(typeof(Customers));
                var customer = (Customers) xs.Deserialize(xmlFile);
                foreach (var custome in customer.customers)
                {
                    Write("{0} {1} {2}\n", custome.name, custome.creditcard, custome.password);
                    var c = Protector.SaltedHashedPassword(custome.name, custome.creditcard, custome.password);
                    var ecc = Protector.Encrypt(c.creditcard, c.SaledHashedPassword);
                    custome.creditcard = ecc;
                    custome.password = c.SaledHashedPassword;
                }
                path = Combine("C:\\Users\\DNS\\Desktop\\VS_code\\Study\\c#\\EncryptionApp\\", "new.xml");
                xmlFile = File.OpenWrite(path);
                xs.Serialize(xmlFile, customer);
                xmlFile.Close();
                foreach (var custome in customer.customers)
                {
                    Write("{0} {1} {2}\n", custome.name, custome.creditcard, custome.password);
                    try
                    {
                        var dcc = Protector.Decrypt(custome.creditcard, custome.password);
                    custome.creditcard = dcc;
                    Write("{0} {1} {2}\n", custome.name, custome.creditcard, custome.password);
                    }
                    catch
                    {
                        WriteLine("Something wrong");
                    }
                }
                xmlFile.Close();
            }

            catch (Exception e)
            {
                Write(e.ToString());
                return;
            }
        }
    }
}
