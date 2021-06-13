using System;

namespace FigureClass
{
    public abstract class Figure
    {
        protected float heigh { get; set; }
        protected float width{ get; set; }
        public float area;
        public Figure(float heigh, float width)
        {
            this.heigh = heigh;
            this.width = width;
            this.area = SearchArea();
        }

        public abstract float SearchArea();

        public override string ToString()
        {
            return "Heigh = " +this.heigh + " Width="+this.width+" Area="+this.area;
        }
    }
    public class Rectangle : Figure
    {
        public Rectangle(float heigh, float width) : base(heigh, width) { }
        public override float SearchArea()
        {
            return this.width * this.heigh * 1/2;
        }
    }
    public class Square : Figure
    {

        public Square(float width) : base(0, width) { }
        public override float SearchArea()
        {
            return this.width * this.width;
        }
        public override string ToString()
        {
            return "Width=" + this.width + " Area=" + this.area;
        }
    }
    public class Circle : Figure
    {
        public Circle( float width) : base(0, width) { }
        public override float SearchArea()
        {
            return this.width * this.width * (float)Math.PI;
        }
        public override string ToString()
        {
            return "Radius=" + this.width + " Area=" + this.area;
        }
    }

}

