using FigureClass;
using System;
using System.Collections.Generic;
using System.Xml.Serialization;
using System.IO;
using static System.Console;
using static System.Environment;
using static System.IO.Path;

namespace Figures
{
    public class Serializer
    {
        public List<Rectangle> rectangles = new List<Rectangle>
            {
                new Rectangle(3, 4), new Rectangle(5, 12)
         };
        public List<Square> squares = new List<Square>
        {
            new Square(5), new Square(10)
        };
        public List<Circle> circles = new List<Circle>
        {
             new Circle(3), new Circle(9)
        };
    }
    class Program
    {
        static void Main(string[] args)
        {
            var figure = new Serializer();
            WriteLine(typeof(Serializer));
            string path = Combine(CurrentDirectory, "figure.xml");
            FileStream xmlFile;
            if (File.Exists(path))
            {
                xmlFile = File.OpenWrite(path);
            }
            else
            {
                xmlFile = File.Create(path);
            }
            /*using (GZipStream compressor = new GZipStream(gzipFile, CompressionMode.Compress))
            {
                using (XmlWriter xml = X)
            }*/
            var xs = new XmlSerializer(typeof(Serializer));
            xs.Serialize(xmlFile, figure);
            xmlFile.Close();
            WriteLine(File.ReadAllText(path));
            xmlFile = File.Open(path, FileMode.Open);
            var loadedFigures = (Serializer)xs.Deserialize(xmlFile);
            foreach(var f in loadedFigures.rectangles)
             {
                WriteLine(f.ToString());
            }
            foreach (var f in loadedFigures.squares)
            {
                WriteLine(f.ToString());
            }
            foreach (var f in loadedFigures.circles)
            {
                WriteLine(f.ToString());
            }
            xmlFile.Close();

        }
    }
}
