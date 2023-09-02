package org.lab6.collection.data;

import org.lab6.exception.IllegalFieldException;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Location {

    public static class From implements Serializable {

        @Serial
        private static final long serialVersionUID = 7697087902293545684L;

        private Float x; //Поле не может быть null
        private Integer y; //Поле не может быть null
        private String name; //Поле не может быть null

        public From() {}

        public From(Float x, Integer y, String name) {
            this.setX(x);
            this.setY(y);
            this.setName(name);
        }

        public void setX(Float x) {
            if (x == null) {
                throw new IllegalFieldException("x", this);
            }
            this.x = x;
        }

        public void setY(Integer y) {
            if (y == null) {
                throw new IllegalFieldException("y", this);
            }
            this.y = y;
        }

        public void setName(String name) {
            if (name == null) {
                throw new IllegalFieldException("name", this);
            }
            this.name = name;
        }

        public Float getX() {
            return x;
        }

        public Integer getY() {
            return y;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "Location.From[" +
                    "x=" + x +
                    ", y=" + y +
                    ", name='" + name + '\'' +
                    ']';
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof From from)) return false;
            return getX().equals(from.getX())
                    && getY().equals(from.getY())
                    && getName().equals(from.getName());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getX(), getY(), getName());
        }
    }

    public static class To implements Serializable {

        @Serial
        private static final long serialVersionUID = 1913890712619460979L;

        private long x;
        private Float y; //Поле не может быть null
        private String name; //Поле не может быть null

        public To() {}

        public To(long x, Float y, String name) {
            this.setX(x);
            this.setY(y);
            this.setName(name);
        }

        public void setX(long x) {
            this.x = x;
        }

        public void setY(Float y) {
            if (y == null) {
                throw new IllegalFieldException("y", this);
            }
            this.y = y;
        }

        public void setName(String name) {
            if (name == null) {
                throw new IllegalFieldException("name", this);
            }
            this.name = name;
        }

        public long getX() {
            return x;
        }

        public Float getY() {
            return y;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "Location.To[" +
                    "x=" + x +
                    ", y=" + y +
                    ", name='" + name + '\'' +
                    ']';
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof To to)) return false;
            return getX() == to.getX()
                    && getY().equals(to.getY())
                    && getName().equals(to.getName());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getX(), getY(), getName());
        }
    }
}
